package com.vitahacre.data.generator

import com.vitahacre.domain.model.BoardConfiguration
import com.vitahacre.domain.model.BoardGenerationResult
import com.vitahacre.domain.model.GameState
import com.vitahacre.domain.model.GameStatus
import com.vitahacre.domain.model.SelectionState

/**
 * GeneratorFactory — Ponto de entrada do subsistema de geração.
 *
 * Responsabilidade:
 *  - Instanciar o gerador correto (extensível para futuras estratégias)
 *  - Orquestrar a transição de BoardGenerationResult → GameState inicial
 *
 * Este objeto pertence à fronteira entre `data/generator` e `domain/state`.
 * Ele NÃO é o Controller (Ciclo 03) — apenas converte o resultado da geração
 * em estado inicial pronto para a partida.
 *
 * PROIBIDO: android.*, Canvas, View, Compose.
 */
object GeneratorFactory {

    /**
     * Retorna a implementação padrão do gerador.
     * Ponto de injeção para testes (substituir por FakeBoardGenerator no Ciclo 05).
     */
    fun defaultGenerator(): BoardGenerator = DefaultBoardGenerator()

    /**
     * Executa a geração completa e converte o resultado em [GameState] inicial.
     *
     * Fluxo:
     *  1. Gerar tabuleiro via [BoardGenerator.generate]
     *  2. Verificar se é jogável
     *  3. Construir GameState com status = PRONTO (se jogável) ou INICIALIZANDO (se falhou)
     *
     * Contrato de saída:
     *  - Se BoardGenerationResult.isPlayable = true AND hasAtLeastOneInitialMove = true
     *    → GameState.status = GameStatus.PRONTO
     *  - Qualquer falha
     *    → GameState.status = GameStatus.INICIALIZANDO (partida não iniciável)
     *
     * @param configuration Parâmetros do tabuleiro desejado.
     * @param generator     Implementação do gerador (padrão: [DefaultBoardGenerator]).
     * @return Par de (GameState, BoardGenerationResult) para auditoria e rastreabilidade.
     */
    fun createInitialGameState(
        configuration: BoardConfiguration,
        generator: BoardGenerator = defaultGenerator()
    ): Pair<GameState, BoardGenerationResult> {

        val result = generator.generate(configuration)

        val gameState = if (result.isPlayable && result.hasAtLeastOneInitialMove) {
            GameState(
                tiles              = result.tiles,
                selection          = SelectionState.EMPTY,
                status             = GameStatus.PRONTO,
                boardConfiguration = result.configuration
            )
        } else {
            // Falha de geração — estado de espera sem tiles válidos
            GameState.initial(configuration)
        }

        return Pair(gameState, result)
    }
}
