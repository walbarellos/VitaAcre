package com.vitahacre.domain.model

/**
 * SelectionState — Estado atual da seleção de peças na partida.
 *
 * Contrato: MDS-01, MDS-02, MDS-03
 * Referência: 05 - Modelo_de_Dados.md § 5.6
 *
 * Restrições:
 *  MDS-01: não pode haver segunda seleção sem possibilidade lógica de primeira seleção.
 *  MDS-02: a mesma peça NÃO pode ocupar os dois campos simultaneamente.
 *  MDS-03: peça removida NÃO pode permanecer como seleção ativa.
 *
 * Estados possíveis:
 *  (null, null)      → nenhuma peça selecionada — partida em estado PRONTO
 *  (id, null)        → primeira peça registrada — aguardando segunda
 *  (id, id)          → par formado — aguardando avaliação de match
 *
 * Valores null indicam ausência de seleção, nunca estado inválido.
 */
data class SelectionState(
    val firstSelectedTileId: Int? = null,
    val secondSelectedTileId: Int? = null
) {
    companion object {
        /** Estado sem seleção alguma — ponto de partida e pós-reset. */
        val EMPTY = SelectionState(null, null)
    }
}
