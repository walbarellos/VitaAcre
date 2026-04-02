package com.vitahacre.domain.rules

import com.vitahacre.domain.model.MatchReason
import com.vitahacre.domain.model.MatchResult
import com.vitahacre.domain.model.Tile

/**
 * MatchRules — Regras puras de pareamento entre tiles.
 *
 * Contrato: RN-11 (distinção), RN-12 (regra de match), RN-13 (equivalência lógica),
 *           RN-14 (match inválido), RN-15 (remoção por match válido)
 * Referência: 03 - Regras_de_Negocio.md § 3.4
 *
 * TODAS as funções são puras: sem efeito colateral, sem mutação. (IT-06)
 * PROIBIDO: qualquer import de android.*, Canvas, View, Compose.
 */
object MatchRules {

    /**
     * Verifica se [tileA] e [tileB] são candidatos compatíveis para match
     * baseado APENAS em identidade e pairKey — sem verificar elegibilidade.
     *
     * Use [evaluateMatch] para a validação completa com elegibilidade.
     *
     * Regras aplicadas:
     *  - RN-11: as duas peças não podem ser a mesma instância (mesmo id).
     *  - RN-13: devem ter o mesmo pairKey.
     *
     * @return true se o pairKey é igual e as peças são distintas.
     */
    fun canMatch(tileA: Tile, tileB: Tile): Boolean {
        // RN-11: distinção — mesma peça nunca forma par consigo mesma
        if (tileA.id == tileB.id) return false

        // RN-13: equivalência lógica de pareamento
        return tileA.pairKey == tileB.pairKey
    }

    /**
     * RN-12 — Avaliação completa de um par de tiles.
     *
     * Valida TODAS as condições obrigatórias para match:
     *  1. Peças distintas (RN-11)
     *  2. Ambas ativas — não removidas (RN-02)
     *  3. Ambas elegíveis no momento da avaliação (RN-07/08)
     *  4. Mesmo pairKey (RN-13)
     *
     * A verificação segue a ordem de prioridade de § 3.8 (Regras_de_Negocio.md).
     *
     * @param tileA    Primeira peça selecionada.
     * @param tileB    Segunda peça selecionada.
     * @param allTiles Lista completa de todas as peças da partida.
     * @return [MatchResult] com isValid = true se todas as condições forem satisfeitas,
     *         ou isValid = false com [reason] indicando a primeira falha encontrada.
     *
     * Casos de interpretação:
     *  IO-03: duas peças iguais → SAME_TILE
     *  IO-03: uma ou ambas removidas → TILE_REMOVED
     *  IO-04: duas elegíveis com mesmo pairKey → VALID_MATCH
     */
    fun evaluateMatch(tileA: Tile, tileB: Tile, allTiles: List<Tile>): MatchResult {
        // 1. Auto-pareamento proibido (RN-11)
        if (tileA.id == tileB.id) {
            return MatchResult.invalid(tileA.id, tileB.id, MatchReason.SAME_TILE)
        }

        // 2. Verificação de remoção (RN-02)
        if (tileA.isRemoved || tileB.isRemoved) {
            return MatchResult.invalid(tileA.id, tileB.id, MatchReason.TILE_REMOVED)
        }

        // 3. Verificação de elegibilidade (RN-07/08) — ambas devem ser elegíveis
        val aEligible = EligibilityRules.isEligible(tileA, allTiles)
        val bEligible = EligibilityRules.isEligible(tileB, allTiles)
        if (!aEligible || !bEligible) {
            return MatchResult.invalid(tileA.id, tileB.id, MatchReason.TILE_NOT_ELIGIBLE)
        }

        // 4. Equivalência lógica de pareamento (RN-13)
        if (tileA.pairKey != tileB.pairKey) {
            return MatchResult.invalid(tileA.id, tileB.id, MatchReason.DIFFERENT_PAIR_KEY)
        }

        // Todas as condições satisfeitas → match válido (RN-12)
        return MatchResult.valid(tileA.id, tileB.id)
    }
}
