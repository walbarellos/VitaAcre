package com.vitahacre.presentation.canvas

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.vitahacre.domain.model.BoardConfiguration
import com.vitahacre.domain.model.GeometryContract

/**
 * DefaultBoardRenderer — Implementação padrão de BoardRenderer.
 *
 * Desenha a malha espacial do tabuleiro usando TILE_UNIT como base.
 * Renderiza uma grade plana simples (visualização 2D do tabuleiro 3D).
 */
class DefaultBoardRenderer : BoardRenderer {

    companion object {
        private const val LAYER_OFFSET_PX = 0.5f
    }

    private val gridPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#444444")
        strokeWidth = 1f
        isAntiAlias = true
    }

    private val backgroundPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#1A1A1A")
        isAntiAlias = true
    }

    private val layerIndicatorPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#555555")
        strokeWidth = 0.5f
        isAntiAlias = true
    }

    override fun render(
        boardConfiguration: BoardConfiguration,
        cellSize: Float,
        canvas: Canvas
    ) {
        val columns = boardConfiguration.columns
        val rows = boardConfiguration.rows
        val layers = boardConfiguration.layers

        val boardWidth = columns * GeometryContract.TILE_UNIT * cellSize
        val boardHeight = rows * GeometryContract.TILE_UNIT * cellSize

        canvas.drawRect(0f, 0f, boardWidth, boardHeight, backgroundPaint)

        for (col in 0..columns) {
            val x = col * GeometryContract.TILE_UNIT * cellSize
            canvas.drawLine(x, 0f, x, boardHeight, gridPaint)
        }

        for (row in 0..rows) {
            val y = row * GeometryContract.TILE_UNIT * cellSize
            canvas.drawLine(0f, y, boardWidth, y, gridPaint)
        }

        if (layers > 1) {
            for (layer in 1 until layers) {
                val layerScale = 1f - (layer * LAYER_OFFSET_PX)
                val layerWidth = boardWidth * layerScale
                val layerHeight = boardHeight * layerScale
                val offsetX = (boardWidth - layerWidth) / 2f
                val offsetY = (boardHeight - layerHeight) / 2f

                canvas.drawRect(
                    offsetX, offsetY,
                    offsetX + layerWidth, offsetY + layerHeight,
                    layerIndicatorPaint
                )
            }
        }
    }
}
