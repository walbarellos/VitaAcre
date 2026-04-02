package com.vitahacre.domain.rules

import com.vitahacre.domain.model.MatchResult
import com.vitahacre.domain.model.Tile

/**
 * DomainRules — Fachada unificada do motor de regras do VitahAcre.
 *
 * Ponto de entrada único para o Controller (Ciclo 03) e para os testes (Ciclo 05).
 * Delega para os módulos especializados sem reimplementar lógica.
 *
 * Vantagem arquitetural:
 *  O Controller não precisa conhecer BlockingRules, EligibilityRules, MatchRules
 *  ou GameStateRules individualmente. Ele só conhece DomainRules.
 *  Isso isola o domínio da apresentação e respeita ARQ-05 (Baixo Acoplamento).
 *
 * PROIBIDO: qualquer import de android.*, Canvas, View, Compose.
 */
object DomainRules {

    // ─────────────────────────────────────────────────────────────────────────
    // Bloqueio
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Verifica se a [tile] está bloqueada por peça acima (RN-05).
     * @see BlockingRules.hasBlockedTop
     */
    fun hasBlockedTop(tile: Tile, allTiles: List<Tile>): Boolean =
        BlockingRules.hasBlockedTop(tile, allTiles)

    /**
     * Verifica se a [tile] possui ao menos um lado lateral livre (RN-06).
     * @see BlockingRules.hasFreeSide
     */
    fun hasFreeSide(tile: Tile, allTiles: List<Tile>): Boolean =
        BlockingRules.hasFreeSide(tile, allTiles)

    // ─────────────────────────────────────────────────────────────────────────
    // Elegibilidade
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Verifica se a [tile] está elegível para seleção (RN-07 / RN-08).
     * @see EligibilityRules.isEligible
     */
    fun isEligible(tile: Tile, allTiles: List<Tile>): Boolean =
        EligibilityRules.isEligible(tile, allTiles)

    // ─────────────────────────────────────────────────────────────────────────
    // Match
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Verificação rápida de compatibilidade por pairKey e distinção (RN-11 / RN-13).
     * Não verifica elegibilidade — use [evaluateMatch] para validação completa.
     * @see MatchRules.canMatch
     */
    fun canMatch(tileA: Tile, tileB: Tile): Boolean =
        MatchRules.canMatch(tileA, tileB)

    /**
     * Avaliação completa de match com todas as condições de RN-12.
     * Retorna [MatchResult] com isValid e reason.
     * @see MatchRules.evaluateMatch
     */
    fun evaluateMatch(tileA: Tile, tileB: Tile, allTiles: List<Tile>): MatchResult =
        MatchRules.evaluateMatch(tileA, tileB, allTiles)

    // ─────────────────────────────────────────────────────────────────────────
    // Estado terminal
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Verifica se todas as peças foram removidas → vitória (RN-19 / RN-37).
     * @see GameStateRules.isVictory
     */
    fun isVictory(tiles: List<Tile>): Boolean =
        GameStateRules.isVictory(tiles)

    /**
     * Verifica se ainda existem jogadas válidas disponíveis (RN-20 / RN-36).
     * @see GameStateRules.hasAvailableMoves
     */
    fun hasAvailableMoves(tiles: List<Tile>): Boolean =
        GameStateRules.hasAvailableMoves(tiles)

    /**
     * Determina o próximo estado terminal da partida em ordem de prioridade.
     * Ordem: VITORIA → SEM_JOGADAS → CONTINUE (§ 3.8)
     * @see GameStateRules.checkTerminalState
     */
    fun checkTerminalState(tiles: List<Tile>): TerminalCheckResult =
        GameStateRules.checkTerminalState(tiles)
}
