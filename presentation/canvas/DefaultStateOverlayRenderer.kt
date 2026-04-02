package com.vitahacre.presentation.canvas

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.vitahacre.domain.model.GameStatus

/**
 * DefaultStateOverlayRenderer — Implementação padrão de StateOverlayRenderer.
 */
class DefaultStateOverlayRenderer : StateOverlayRenderer {

    private val victoryBackgroundPaint = Paint().apply {
        color = Color.parseColor("#804CAF50") // Green with alpha
        style = Paint.Style.FILL
    }

    private val noMovesBackgroundPaint = Paint().apply {
        color = Color.parseColor("#80F44336") // Red with alpha
        style = Paint.Style.FILL
    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 64f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
        isFakeBoldText = true
    }

    override fun render(gameStatus: GameStatus, canvas: Canvas, viewWidth: Float, viewHeight: Float) {
        if (gameStatus == GameStatus.VITORIA) {
            val rect = RectF(0f, 0f, viewWidth, viewHeight)
            canvas.drawRect(rect, victoryBackgroundPaint)

            val textY = viewHeight / 2f - ((textPaint.descent() + textPaint.ascent()) / 2f)
            canvas.drawText("Vitória!", viewWidth / 2f, textY, textPaint)
        } else if (gameStatus == GameStatus.SEM_JOGADAS) {
            val rect = RectF(0f, 0f, viewWidth, viewHeight)
            canvas.drawRect(rect, noMovesBackgroundPaint)

            val textY = viewHeight / 2f - ((textPaint.descent() + textPaint.ascent()) / 2f)
            canvas.drawText("Sem Jogadas", viewWidth / 2f, textY, textPaint)
        }
    }
}
