package com.vitahacre.presentation.canvas

import android.graphics.Canvas
import com.vitahacre.domain.model.GameStatus

/**
 * StateOverlayRenderer — Interface responsável por renderizar overlays de estado do jogo.
 */
interface StateOverlayRenderer {
    fun render(gameStatus: GameStatus, canvas: Canvas, viewWidth: Float, viewHeight: Float)
}
