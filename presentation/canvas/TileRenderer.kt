package com.vitahacre.presentation.canvas

import android.graphics.Canvas
import com.vitahacre.domain.model.Tile

/**
 * TileRenderer — Responsável por desenhar uma peça individual do tabuleiro.
 *
 * Contrato: RND-01 a RND-06
 * Referência: 01 - Contrato_Tecnico_do_Ciclo.md
 *
 * Responsabilidades:
 *  - Desenhar peça com cor derivada de pairKey
 *  - Desenhar borda
 *  - Aplicar destaque visual se isSelected = true
 *  - Zero lógica de negócio
 *
 * PROIBIDO: importar domain/rules/, chamar funções de domínio
 */
interface TileRenderer {

    /**
     * Renderiza uma peça individual.
     *
     * @param tile Peça a ser renderizada
     * @param x Posição X em pixels
     * @param y Posição Y em pixels
     * @param cellSize Tamanho da célula em pixels
     * @param isSelected Indica se a peça está selecionada
     * @param canvas Canvas Android para desenho
     */
    fun render(
        tile: Tile,
        x: Float,
        y: Float,
        cellSize: Float,
        isSelected: Boolean,
        canvas: Canvas
    )
}
