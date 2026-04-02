package com.vitahacre.domain.model

/**
 * BoardConfiguration — Parâmetros estruturais do tabuleiro da partida.
 *
 * Contrato: MDB-01, MDB-02, MDB-03
 * Referência: 05 - Modelo_de_Dados.md § 5.8
 *
 * Restrições de integridade:
 *  MDB-01: [totalTiles] deve ser par — exigência de pareamento (RN-23).
 *  MDB-02: [layers] deve ser compatível com a estratégia de empilhamento.
 *  MDB-03: os campos devem ser suficientes para reconstrução/rastreabilidade.
 *
 * Convenção de coordenadas do tabuleiro:
 *  - Cada tile ocupa 2 unidades no eixo x e 2 no eixo y.
 *  - Grid resultante: x ∈ [0, columns * 2), y ∈ [0, rows * 2).
 *  - Layers: 0 (base) até layers - 1 (topo).
 */
data class BoardConfiguration(

    /**
     * Quantidade lógica de colunas de tiles no tabuleiro.
     * Valor mínimo aceitável: 2.
     */
    val columns: Int,

    /**
     * Quantidade lógica de linhas de tiles no tabuleiro.
     * Valor mínimo aceitável: 2.
     */
    val rows: Int,

    /**
     * Quantidade máxima de camadas lógicas verticais.
     * Valor mínimo aceitável: 1.
     */
    val layers: Int,

    /**
     * Quantidade total de peças previstas para o tabuleiro.
     * DEVE ser par (MDB-01 / RN-23).
     */
    val totalTiles: Int,

    /**
     * Semente para geração determinística e reproduzível.
     * null = geração com semente aleatória na sessão atual.
     * Valor fixo = permite replay exato da mesma partida.
     */
    val seed: Long? = null
) {
    init {
        require(columns >= 2) { "BoardConfiguration: columns deve ser >= 2" }
        require(rows >= 2) { "BoardConfiguration: rows deve ser >= 2" }
        require(layers >= 1) { "BoardConfiguration: layers deve ser >= 1" }
        require(totalTiles > 0 && totalTiles % 2 == 0) {
            "BoardConfiguration: totalTiles deve ser positivo e par (MDB-01)"
        }
    }
}
