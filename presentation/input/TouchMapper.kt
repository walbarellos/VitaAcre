package com.vitahacre.presentation.input

import com.vitahacre.domain.model.Tile
import com.vitahacre.domain.model.GeometryContract

/**
 * TouchMapper — Responsável pelo Hit-Testing.
 * 
 * Traduz coordenadas espaciais (pixels) para entidades de domínio,
 * respeitando a ordem de camadas (Z-order) e o contrato geométrico.
 */
object TouchMapper {
    /**
     * Resolve qual Tile foi tocada.
     * 
     * @param x Coordenada X em pixels.
     * @param y Coordenada Y em pixels.
     * @param tiles Lista de tiles do estado atual.
     * @param cellSize Tamanho em pixels de 1 UNIT (TILE_UNIT = 2).
     * @return A Tile do topo na posição, ou null se área vazia.
     */
    fun mapToTile(x: Float, y: Float, tiles: List<Tile>, cellSize: Float): Tile? {
        val unit = GeometryContract.TILE_UNIT
        
        return tiles.filter { !it.isRemoved }
            .sortedByDescending { it.layer }
            .firstOrNull { tile ->
                val left = tile.x * cellSize
                val top = tile.y * cellSize
                val right = (tile.x + unit) * cellSize
                val bottom = (tile.y + unit) * cellSize
                
                x >= left && x <= right && y >= top && y <= bottom
            }
    }
}
