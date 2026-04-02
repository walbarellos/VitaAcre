package com.vitahacre.presentation.canvas

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.vitahacre.domain.model.Tile

/**
 * DefaultTileRenderer — Implementação padrão de TileRenderer.
 *
 * Desenha peças individuais com cores derivadas de pairKey.
 * Aplica destaque visual quando isSelected = true.
 *
 * Constantes visuais do módulo:
 *  TILE_PADDING_PX       = 2f      (margem interna)
 *  TILE_CORNER_RADIUS_PX = 8f      (arredondamento)
 *  BORDER_WIDTH_NORMAL   = 3f     (borda normal)
 *  BORDER_WIDTH_SELECTED = 6f     (borda quando selecionado)
 */
class DefaultTileRenderer : TileRenderer {

    companion object {
        private const val TILE_PADDING_PX = 2f
        private const val TILE_CORNER_RADIUS_PX = 8f
        private const val BORDER_WIDTH_NORMAL = 3f
        private const val BORDER_WIDTH_SELECTED = 6f
        private const val TEXT_SIZE_MULTIPLIER = 0.6f
    }

    private val tilePaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val borderPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = BORDER_WIDTH_NORMAL
        isAntiAlias = true
    }

    private val selectedBorderPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = BORDER_WIDTH_SELECTED
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 32f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val colorPalette = listOf(
        Color.parseColor("#E53935"),
        Color.parseColor("#1E88E5"),
        Color.parseColor("#43A047"),
        Color.parseColor("#FDD835"),
        Color.parseColor("#8E24AA"),
        Color.parseColor("#00ACC1"),
        Color.parseColor("#FF7043"),
        Color.parseColor("#5E35B1"),
        Color.parseColor("#00897B"),
        Color.parseColor("#D81B60")
    )

    override fun render(
        tile: Tile,
        x: Float,
        y: Float,
        cellSize: Float,
        isSelected: Boolean,
        canvas: Canvas
    ) {
        val tileWidth = cellSize * 2f - TILE_PADDING_PX * 2f
        val tileHeight = cellSize * 2f - TILE_PADDING_PX * 2f
        val offsetX = x + TILE_PADDING_PX
        val offsetY = y + TILE_PADDING_PX

        val colorIndex = tile.pairKey % colorPalette.size
        tilePaint.color = colorPalette[colorIndex]
        borderPaint.color = Color.parseColor("#212121")

        val rect = RectF(offsetX, offsetY, offsetX + tileWidth, offsetY + tileHeight)
        canvas.drawRoundRect(rect, TILE_CORNER_RADIUS_PX, TILE_CORNER_RADIUS_PX, tilePaint)
        canvas.drawRoundRect(rect, TILE_CORNER_RADIUS_PX, TILE_CORNER_RADIUS_PX, borderPaint)

        if (isSelected) {
            selectedBorderPaint.color = Color.parseColor("#FFD700")
            canvas.drawRoundRect(rect, TILE_CORNER_RADIUS_PX, TILE_CORNER_RADIUS_PX, selectedBorderPaint)
        }

        textPaint.textSize = cellSize * TEXT_SIZE_MULTIPLIER
        val textY = offsetY + tileHeight / 2f + textPaint.textSize / 3f
        canvas.drawText(tile.pairKey.toString(), offsetX + tileWidth / 2f, textY, textPaint)
    }
}
