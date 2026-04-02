package com.vitahacre.presentation.screen

import android.graphics.Canvas
import com.vitahacre.domain.model.GameState
import com.vitahacre.presentation.canvas.BoardRenderer
import com.vitahacre.presentation.canvas.SelectionHighlightRenderer
import com.vitahacre.presentation.canvas.StateOverlayRenderer
import com.vitahacre.presentation.canvas.TileRenderer

/**
 * GameScreenStateAdapter — Orquestrador de renderização.
 *
 * Traduz um [GameState] imutável em instruções de desenho procedural sequencial,
 * unindo múltiplos renderers modulares sem permitir mutação de regras.
 */
class GameScreenStateAdapter(
    private val boardRenderer: BoardRenderer,
    private val tileRenderer: TileRenderer,
    private val selectionHighlightRenderer: SelectionHighlightRenderer,
    private val stateOverlayRenderer: StateOverlayRenderer
) {
    fun render(
        gameState: GameState,
        cellSize: Float,
        canvas: Canvas,
        viewWidth: Float,
        viewHeight: Float
    ) {
        // 1. Renderiza o grid base
        boardRenderer.render(gameState.boardConfiguration, cellSize, canvas)

        // 2. Renderiza tiles que não foram removidos (RND-04)
        val visibleTiles = gameState.tiles.filter { !it.isRemoved }
        visibleTiles.forEach { tile ->
            val isSelected = gameState.selection.firstSelectedTileId == tile.id ||
                             gameState.selection.secondSelectedTileId == tile.id

            val x = tile.x * cellSize
            val y = tile.y * cellSize

            tileRenderer.render(tile, x, y, cellSize, isSelected, canvas)
        }

        // 3. Destaca o primeiro selecionado, se aplicável
        selectionHighlightRenderer.render(
            gameState.selection.firstSelectedTileId,
            gameState.tiles,
            cellSize,
            canvas
        )

        // 4. Se aplicável, renderiza feedback de finais (Vitória ou Sem Jogadas)
        stateOverlayRenderer.render(gameState.status, canvas, viewWidth, viewHeight)
    }
}
