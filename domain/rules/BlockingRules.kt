package com.vitahacre.domain.rules

import com.vitahacre.domain.model.GeometryContract
import com.vitahacre.domain.model.Tile
import kotlin.math.abs

/**
 * BlockingRules — Regras puras de bloqueio espacial das peças.
 *
 * Contrato: RN-05 (bloqueio superior), RN-06 (bloqueio lateral total)
 * Referência: 03 - Regras_de_Negocio.md § 3.4
 *
 * TODAS as funções aqui são puras:
 *  - sem efeito colateral
 *  - sem mutação de estado
 *  - mesmo estado + mesma entrada = sempre mesma saída (IT-06)
 *
 * PROIBIDO: qualquer import de android.*, Canvas, View, Compose ou cor.
 *
 * ─────────────────────────────────────────────────────────────────
 * Convenção de geometria lógica do tabuleiro
 * ─────────────────────────────────────────────────────────────────
 * Cada Tile ocupa uma célula de tamanho TILE_UNIT × TILE_UNIT no grid lógico.
 * TILE_UNIT = 2 (unidades inteiras por tile, lado a lado sem sobreposição).
 *
 * Sobreposição superior (bloqueio de camada):
 *   A tile superior (layer = L+1) bloqueia a tile inferior (layer = L) se:
 *   abs(superior.x - inferior.x) < TILE_UNIT AND abs(superior.y - inferior.y) < TILE_UNIT
 *
 * Bloqueio lateral:
 *   Lado esquerdo bloqueado se existe tile ativa na mesma camada com:
 *     vizinha.x == tile.x - TILE_UNIT  AND  abs(vizinha.y - tile.y) < TILE_UNIT
 *   Lado direito bloqueado se existe tile ativa na mesma camada com:
 *     vizinha.x == tile.x + TILE_UNIT  AND  abs(vizinha.y - tile.y) < TILE_UNIT
 * ─────────────────────────────────────────────────────────────────
 */
object BlockingRules {

    /** Tamanho unitário de cada tile no grid lógico. */
    private const val TILE_UNIT = GeometryContract.TILE_UNIT

    /**
     * RN-05 — Verifica se a [tile] está bloqueada por alguma peça acima.
     *
     * Uma tile está bloqueada superiormente se existir ao menos uma tile ativa
     * na camada imediatamente superior ([layer] + 1) que se sobreponha espacialmente.
     *
     * @param tile     A peça a ser avaliada.
     * @param allTiles Lista completa de todas as peças da partida (ativas e removidas).
     * @return true se existe bloqueio superior ativo; false caso contrário.
     *
     * Caso IO-02: peça com lado livre mas bloqueada superiormente → inelegível.
     */
    fun hasBlockedTop(tile: Tile, allTiles: List<Tile>): Boolean {
        return allTiles.any { other ->
            !other.isRemoved &&
            other.id != tile.id &&
            other.layer == tile.layer + 1 &&
            abs(other.x - tile.x) < TILE_UNIT &&
            abs(other.y - tile.y) < TILE_UNIT
        }
    }

    /**
     * RN-06 — Verifica se a [tile] possui ao menos um lado lateral livre.
     *
     * Um lado é considerado livre se não houver nenhuma tile ativa na mesma camada
     * adjacente naquele lado (esquerdo ou direito).
     *
     * "Livre" = NOT (lado esquerdo bloqueado AND lado direito bloqueado).
     *
     * @param tile     A peça a ser avaliada.
     * @param allTiles Lista completa de todas as peças da partida (ativas e removidas).
     * @return true se ao menos um lado lateral está livre; false se ambos estão bloqueados.
     *
     * Caso IO-01: tile sem bloqueio superior mas com ambos os lados bloqueados → inelegível.
     */
    fun hasFreeSide(tile: Tile, allTiles: List<Tile>): Boolean {
        val leftBlocked = allTiles.any { other ->
            !other.isRemoved &&
            other.id != tile.id &&
            other.layer == tile.layer &&
            other.x == tile.x - TILE_UNIT &&
            abs(other.y - tile.y) < TILE_UNIT
        }

        val rightBlocked = allTiles.any { other ->
            !other.isRemoved &&
            other.id != tile.id &&
            other.layer == tile.layer &&
            other.x == tile.x + TILE_UNIT &&
            abs(other.y - tile.y) < TILE_UNIT
        }

        // Livre se ao menos um lado não estiver bloqueado
        return !leftBlocked || !rightBlocked
    }
}
