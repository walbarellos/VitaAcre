package com.vitahacre.presentation.canvas

import android.graphics.Canvas
import com.vitahacre.domain.model.BoardConfiguration

/**
 * BoardRenderer — Responsável por desenhar a malha espacial do tabuleiro.
 *
 * Contrato: RND-01 a RND-06
 * Referência: 01 - Contrato_Tecnico_do_Ciclo.md
 *
 * Responsabilidades:
 *  - Desenhar a estrutura base do tabuleiro baseada em TILE_UNIT
 *  - Renderizar linhas de grade ou estrutura visual
 *  - Zero lógica de negócio — apenas leitura de BoardConfiguration
 *
 * PROIBIDO: importar domain/rules/, chamar funções de domínio
 */
interface BoardRenderer {

    /**
     * Renderiza o tabuleiro com base na configuração fornecida.
     *
     * @param boardConfiguration Configuração estrutural do tabuleiro
     * @param cellSize Tamanho de cada célula em pixels
     * @param canvas Canvas Android para desenho
     */
    fun render(
        boardConfiguration: BoardConfiguration,
        cellSize: Float,
        canvas: Canvas
    )
}
