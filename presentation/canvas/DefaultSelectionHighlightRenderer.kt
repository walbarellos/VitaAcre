package com.vitahacre.presentation.canvas

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.vitahacre.domain.model.Tile

/**
 * DefaultSelectionHighlightRenderer — Implementação padrão de SelectionHighlightRenderer.
 */
class DefaultSelectionHighlightRenderer : SelectionHighlightRenderer {

    companion object {
        private const val TILE_PADDING_PX = 2f
        private const val BORDER_WIDTH_SELECTED = 8f
    }

    private val selectedHighlightPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = BORDER_WIDTH_SELECTED
        color = Color.parseColor("#FFD700") // Golden Yellow
        isAntiAlias = true
    }

    override fun render(selectedTileId: Int?, tiles: List<Tile>, cellSize: Float, canvas: Canvas) {
        if (selectedTileId == null) return

        val selectedTile = tiles.find { it.id == selectedTileId && !it.isRemoved }
        if (selectedTile != null) {
            val tileWidth = cellSize * 2f - TILE_PADDING_PX * 2f
            val tileHeight = cellSize * 2f - TILE_PADDING_PX * 2f

            // X e Y absolutos baseados na célula
            val x = selectedTile.x * cellSize
            val y = selectedTile.y * cellSize

            val offsetX = x + TILE_PADDING_PX
            val offsetY = y + TILE_PADDING_PX

            val rect = RectF(offsetX, offsetY, offsetX + tileWidth, offsetY + tileHeight)
            canvas.drawRoundRect(rect, 8f, 8f, selectedHighlightPaint)
        }
    }
}
