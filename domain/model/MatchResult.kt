package com.vitahacre.domain.model

/**
 * MatchResult — Resultado formal da tentativa de pareamento entre duas peças.
 *
 * Contrato: conforme Contrato Técnico do Ciclo 01 § 3 (MatchResult)
 * Referência: 05 - Modelo_de_Dados.md § 5.10
 *
 * Regras aplicáveis: RN-12 a RN-14
 *
 * [reason] deve ser um dos valores definidos em [MatchReason].
 * Nunca usar strings livres — isso quebraria a determinismo do sistema.
 */
data class MatchResult(

    /**
     * true  → par aceito como válido — ambas as peças devem ser removidas.
     * false → par rejeitado — nenhuma peça é removida.
     */
    val isValid: Boolean,

    /**
     * Identificador da primeira peça avaliada.
     * Pode ser null apenas em casos de chamada com estado inconsistente (erro de contrato).
     */
    val firstTileId: Int?,

    /**
     * Identificador da segunda peça avaliada.
     * Pode ser null apenas em casos de chamada com estado inconsistente (erro de contrato).
     */
    val secondTileId: Int?,

    /**
     * Motivo textual controlado do resultado.
     * Use exclusivamente as constantes definidas em [MatchReason].
     */
    val reason: String
) {
    companion object {
        /**
         * Cria um MatchResult de sucesso (isValid = true).
         */
        fun valid(firstTileId: Int, secondTileId: Int): MatchResult =
            MatchResult(
                isValid = true,
                firstTileId = firstTileId,
                secondTileId = secondTileId,
                reason = MatchReason.VALID_MATCH
            )

        /**
         * Cria um MatchResult de falha com motivo explícito.
         */
        fun invalid(firstTileId: Int?, secondTileId: Int?, reason: String): MatchResult =
            MatchResult(
                isValid = false,
                firstTileId = firstTileId,
                secondTileId = secondTileId,
                reason = reason
            )
    }
}

/**
 * MatchReason — Conjunto fechado de motivos para resultado de match.
 *
 * Contrato: Contrato Técnico Ciclo 01 § 3 (valores permitidos para reason).
 * PROIBIDO: adicionar motivos não documentados sem revisão formal.
 */
object MatchReason {
    /** Par válido — ambas as peças possuem mesmo pairKey e são elegíveis. */
    const val VALID_MATCH = "VALID_MATCH"

    /** Peças possuem pairKey diferentes — não são compatíveis. */
    const val DIFFERENT_PAIR_KEY = "DIFFERENT_PAIR_KEY"

    /** As duas referências apontam para a mesma peça (auto-pareamento). */
    const val SAME_TILE = "SAME_TILE"

    /** Uma ou ambas as peças não são elegíveis no momento da avaliação. */
    const val TILE_NOT_ELIGIBLE = "TILE_NOT_ELIGIBLE"

    /** Uma ou ambas as peças estão marcadas como removidas. */
    const val TILE_REMOVED = "TILE_REMOVED"
}
