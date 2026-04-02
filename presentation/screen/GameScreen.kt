package com.vitahacre.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import com.vitahacre.domain.model.GameState
import com.vitahacre.domain.model.GeometryContract
import com.vitahacre.presentation.canvas.DefaultBoardRenderer
import com.vitahacre.presentation.canvas.DefaultSelectionHighlightRenderer
import com.vitahacre.presentation.canvas.DefaultStateOverlayRenderer
import com.vitahacre.presentation.canvas.DefaultTileRenderer
import com.vitahacre.presentation.controller.GameController
import kotlinx.coroutines.flow.StateFlow

/**
 * GameScreen — Raiz visual procedural do Ciclo 04.
 *
 * Acopla o Jetpack Compose ao Canvas Android clássico.
 * Repassa eventos de toque para o Controller e extrai o GameState reativo,
 * desenhando a representação puramente baseada em TILE_UNIT via Adapter.
 */
@Composable
fun GameScreen(
    controller: GameController,
    gameStateFlow: StateFlow<GameState>
) {
    val state by gameStateFlow.collectAsState()

    val adapter = remember {
        GameScreenStateAdapter(
            boardRenderer = DefaultBoardRenderer(),
            tileRenderer = DefaultTileRenderer(),
            selectionHighlightRenderer = DefaultSelectionHighlightRenderer(),
            stateOverlayRenderer = DefaultStateOverlayRenderer()
        )
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    controller.onInput(
                        com.vitahacre.domain.model.InputEvent(
                            type = com.vitahacre.domain.model.InputEventType.TOUCH,
                            x = offset.x,
                            y = offset.y
                        )
                    )
                }
            }
    ) {
        // TILE_UNIT = 2, então cada column corresponde a TILE_UNIT lógicos em largura
        val logicalWidth = state.boardConfiguration.columns * GeometryContract.TILE_UNIT
        val logicalHeight = state.boardConfiguration.rows * GeometryContract.TILE_UNIT

        // Garante que o grid cabe na tela mantendo proporções (calcula o cellSize em pixels)
        val cellSizeX = size.width / logicalWidth
        val cellSizeY = size.height / logicalHeight
        val cellSize = minOf(cellSizeX, cellSizeY)

        drawIntoCanvas { composeCanvas ->
            val androidCanvas = composeCanvas.nativeCanvas
            adapter.render(
                gameState = state,
                cellSize = cellSize,
                canvas = androidCanvas,
                viewWidth = size.width,
                viewHeight = size.height
            )
        }
    }
}
