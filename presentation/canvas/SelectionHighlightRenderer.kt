package com.vitahacre.presentation.canvas

import android.graphics.Canvas
import com.vitahacre.domain.model.Tile

/**
 * SelectionHighlightRenderer — Interface responsável pelo destaque visual da seleção.
 */
interface SelectionHighlightRenderer {
    fun render(selectedTileId: Int?, tiles: List<Tile>, cellSize: Float, canvas: Canvas)
}
