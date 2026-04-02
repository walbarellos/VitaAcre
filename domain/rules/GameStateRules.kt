package com.vitahacre.domain.rules

import com.vitahacre.domain.model.Tile

/**
 * GameStateRules — Regras puras de estado terminal da partida.
 *
 * Contrato: RN-19 (vitória), RN-20 (sem jogadas), RN-36 (jogada válida),
 *           RN-37 (terminal vitória), RN-38 (terminal bloqueio)
 * Referência: 03 - Regras_de_Negocio.md §§ 3.4, 3.7
 *
 * TODAS as funções são puras: sem efeito colateral, sem mutação. (IT-06)
 * PROIBIDO: qualquer import de android.*, Canvas, View, Compose.
 *
 * Estas funções são chamadas APÓS cada match bem-sucedido para verificar
 * se a partida deve transitar para VITORIA ou SEM_JOGADAS.
 *
 * Ordem de verificação recomendada (§ 3.8):
 *   1. isVictory(tiles)        → se true → GameStatus.VITORIA
 *   2. hasAvailableMoves(tiles) → se false → GameStatus.SEM_JOGADAS
 *   3. caso contrário          → GameStatus.PRONTO (continua)
 */
object GameStateRules {

    /**
     * RN-19 / RN-37 — Verifica se a condição de vitória foi atingida.
     *
     * A vitória ocorre quando TODAS as peças foram removidas.
     * Não existe vitória parcial — a condição é absoluta.
     *
     * Implicação: basta uma peça ativa para que a vitória seja false.
     *
     * @param tiles Lista completa de todas as peças da partida.
     * @return true se não existe nenhuma peça ativa (tabuleiro vazio).
     *
     * Caso IO-05: todas as peças removidas → true
     */
    fun isVictory(tiles: List<Tile>): Boolean {
        return tiles.none { !it.isRemoved }
    }

    /**
     * RN-20 / RN-36 / RN-38 — Verifica se ainda existem jogadas válidas disponíveis.
     *
     * Existe jogada válida quando há ao menos um par de tiles que satisfaça:
     *  - Ambas ativas (não removidas)
     *  - Ambas elegíveis no momento atual
     *  - Distintas entre si
     *  - Com mesmo pairKey
     *
     * Algoritmo:
     *  1. Filtra apenas tiles elegíveis no estado atual do tabuleiro.
     *  2. Para cada par distinto de tiles elegíveis, verifica canMatch.
     *  3. Retorna true ao encontrar o primeiro par válido (early exit).
     *
     * Complexidade: O(n²) onde n = número de tiles elegíveis.
     * Na prática n é pequeno (tabuleiro típico: 4×8 = 32 tiles, poucas camadas).
     *
     * @param tiles Lista completa de todas as peças da partida.
     * @return true se existe ao menos uma jogada possível; false se o jogo está bloqueado.
     *
     * Caso IO-06: ainda existem peças mas nenhum par elegível válido → false
     */
    fun hasAvailableMoves(tiles: List<Tile>): Boolean {
        // Filtra tiles elegíveis — passa a lista completa como contexto de bloqueio
        val eligibleTiles = tiles.filter { tile ->
            EligibilityRules.isEligible(tile, tiles)
        }

        // Precisa de ao menos 2 tiles elegíveis para existir qualquer jogada
        if (eligibleTiles.size < 2) return false

        // Verifica pares distintos pelo pairKey usando agrupamento (O(n) scan)
        val groupedByPairKey = eligibleTiles.groupBy { it.pairKey }
        return groupedByPairKey.any { (_, group) -> group.size >= 2 }
    }

    /**
     * Determina o próximo estado da partida após uma alteração no tabuleiro.
     *
     * Encapsula a ordem oficial de verificação (§ 3.8):
     *   VITORIA tem prioridade sobre SEM_JOGADAS.
     *
     * @param tiles Lista completa atualizada de peças.
     * @return [TerminalCheckResult] indicando o estado a transitar.
     */
    fun checkTerminalState(tiles: List<Tile>): TerminalCheckResult {
        return when {
            isVictory(tiles)           -> TerminalCheckResult.VICTORY
            !hasAvailableMoves(tiles)  -> TerminalCheckResult.NO_MOVES
            else                       -> TerminalCheckResult.CONTINUE
        }
    }
}

/**
 * TerminalCheckResult — Resultado da verificação de estado terminal.
 * Usado por [GameStateRules.checkTerminalState] para comunicar a transição
 * necessária ao controller sem acoplar com GameStatus diretamente.
 */
enum class TerminalCheckResult {
    /** Condição de vitória detectada → transitar para GameStatus.VITORIA */
    VICTORY,

    /** Sem jogadas disponíveis → transitar para GameStatus.SEM_JOGADAS */
    NO_MOVES,

    /** Partida continua normalmente → transitar para GameStatus.PRONTO */
    CONTINUE
}
