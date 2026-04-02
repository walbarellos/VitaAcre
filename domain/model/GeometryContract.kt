package com.vitahacre.domain.model

/**
 * GeometryContract — Única fonte da verdade para dimensões espaciais.
 * 
 * Centraliza a constante TILE_UNIT para que o domínio, apresentação 
 * e geração operem sobre a mesma malha lógica.
 */
object GeometryContract {
    /** 
     * TILE_UNIT define o tamanho ocupado por uma peça no grid.
     * Suporta a política de bloqueio e hit-testing definida para o VitahAcre.
     */
    const val TILE_UNIT = 2
}
