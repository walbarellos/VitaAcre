package com.vitahacre.presentation.controller

import com.vitahacre.data.generator.GeneratorFactory
import com.vitahacre.domain.model.*
import com.vitahacre.domain.rules.DomainRules
import com.vitahacre.domain.rules.TerminalCheckResult
import com.vitahacre.presentation.input.TouchMapper

/**
 * DefaultGameController — Implementação oficial do fluxo de jogada (Ciclo 03).
 * 
 * Segue a Política A: onInput é o único ponto de atribuição de [state].
 * Materializa a máquina de estados oficial PRONTO -> S1 -> S2 -> PROCESSANDO.
 */
class DefaultGameController(
    initialState: GameState,
    private val cellSize: Float = 100f
) : GameController {

    override var state: GameState = initialState
        private set

    override fun onInput(event: InputEvent): GameState {
        // Redireciona para o helper restart() se o tipo for RESTART
        state = if (event.type == InputEventType.RESTART) {
            restart()
        } else {
            handleTouch(event, state)
        }
        return state
    }

    override fun restart(): GameState {
        // Usa a Factory para garantir que a nova sessão nasça PRONTA
        val (newState, _) = GeneratorFactory.createInitialGameState(state.boardConfiguration)
        return newState
    }

    private fun handleTouch(event: InputEvent, currentState: GameState): GameState {
        if (event.x == null || event.y == null) return currentState
        
        // Proteção Terminal (§ 2.8)
        if (currentState.status == GameStatus.VITORIA || currentState.status == GameStatus.SEM_JOGADAS) {
            return currentState
        }

        val touchedTile = TouchMapper.mapToTile(event.x, event.y, currentState.tiles, cellSize) 
            ?: return currentState
        
        // RN-08: Validação de Elegibilidade
        if (!DomainRules.isEligible(touchedTile, currentState.tiles)) return currentState

        return processStateTransition(touchedTile, currentState)
    }

    private fun processStateTransition(tile: Tile, currentState: GameState): GameState {
        return when (currentState.status) {
            GameStatus.PRONTO -> {
                // Transição: PRONTO -> SELECIONANDO_1
                currentState.copy(
                    selection = SelectionState(firstSelectedTileId = tile.id),
                    status = GameStatus.SELECIONANDO_1
                )
            }
            GameStatus.SELECIONANDO_1 -> {
                if (currentState.selection.firstSelectedTileId == tile.id) return currentState
                
                // Transição: SELECIONANDO_1 -> SELECIONANDO_2
                val stage2 = currentState.copy(
                    selection = currentState.selection.copy(secondSelectedTileId = tile.id),
                    status = GameStatus.SELECIONANDO_2
                )
                
                // Transição imediata: SELECIONANDO_2 -> PROCESSANDO_MATCH
                evaluateSelection(tile, stage2)
            }
            else -> currentState
        }
    }

    private fun evaluateSelection(secondTile: Tile, incomingState: GameState): GameState {
        // Materialização do estado PROCESSANDO_MATCH (Diagrama 05)
        val matchState = incomingState.copy(status = GameStatus.PROCESSANDO_MATCH)
        
        val firstTileId = matchState.selection.firstSelectedTileId 
            ?: return matchState.copy(selection = SelectionState.EMPTY, status = GameStatus.PRONTO)
            
        val firstTile = matchState.tiles.find { it.id == firstTileId }
            ?: return matchState.copy(selection = SelectionState.EMPTY, status = GameStatus.PRONTO)

        val result = DomainRules.evaluateMatch(firstTile, secondTile, matchState.tiles)

        return if (result.isValid) {
            // Sucesso: Remoção conforme RN-15 e avaliação de terminalidade
            val updatedTiles = matchState.tiles.map {
                if (it.id == firstTile.id || it.id == secondTile.id) it.copy(isRemoved = true) else it
            }
            
            matchState.copy(
                tiles = updatedTiles,
                selection = SelectionState.EMPTY,
                status = resolveTerminalStatus(updatedTiles)
            )
        } else {
            // Falha: Política RN-18 (Limpa e volta para PRONTO)
            matchState.copy(
                selection = SelectionState.EMPTY,
                status = GameStatus.PRONTO
            )
        }
    }

    private fun resolveTerminalStatus(tiles: List<Tile>): GameStatus {
        return when (DomainRules.checkTerminalState(tiles)) {
            TerminalCheckResult.VICTORY -> GameStatus.VITORIA
            TerminalCheckResult.NO_MOVES -> GameStatus.SEM_JOGADAS
            TerminalCheckResult.CONTINUE -> GameStatus.PRONTO
        }
    }
}
