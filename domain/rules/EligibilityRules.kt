package com.vitahacre.domain.rules

import com.vitahacre.domain.model.Tile

/**
 * EligibilityRules — Regra pura de elegibilidade de seleção.
 *
 * Contrato: RN-07 (definição de peça livre), RN-08 (elegibilidade para seleção)
 * Referência: 03 - Regras_de_Negocio.md § 3.4
 *
 * TODAS as funções são puras: sem efeito colateral, sem mutação. (IT-06)
 * PROIBIDO: qualquer import de android.*, Canvas, View, Compose.
 */
object EligibilityRules {

    /**
     * RN-07 / RN-08 — Verifica se uma tile está elegível para seleção.
     *
     * Uma tile é elegível se, e somente se, satisfizer SIMULTANEAMENTE:
     *
     *  1. Não está removida              (RN-02)
     *  2. Não está bloqueada superiormente (RN-05)
     *  3. Possui ao menos um lado livre  (RN-06)
     *
     * Prioridade de verificação conforme § 3.8 (Regras_de_Negocio.md):
     *   removida → bloqueio superior → bloqueio lateral → elegível
     *
     * @param tile     A peça a ser avaliada.
     * @param allTiles Lista completa de todas as peças da partida.
     * @return true se a peça pode ser selecionada; false caso contrário.
     *
     * Casos de interpretação oficial:
     *   IO-01: sem bloqueio superior + dois lados bloqueados → false
     *   IO-02: lado livre + bloqueada superiormente          → false
     *   IO-03: ativa, sem bloqueio superior, lado livre      → true
     */
    fun isEligible(tile: Tile, allTiles: List<Tile>): Boolean {
        // Passo 1 — RN-02: peça removida nunca é elegível
        if (tile.isRemoved) return false

        // Passo 2 — RN-05: bloqueio superior impede elegibilidade
        if (BlockingRules.hasBlockedTop(tile, allTiles)) return false

        // Passo 3 — RN-06: ambos os lados bloqueados impede elegibilidade
        if (!BlockingRules.hasFreeSide(tile, allTiles)) return false

        return true
    }
}
