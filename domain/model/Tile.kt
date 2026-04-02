package com.vitahacre.domain.model

/**
 * Tile — Peça individual do tabuleiro.
 *
 * Contrato: MDT-01 a MDT-05
 * Referência: 05 - Modelo_de_Dados.md § 5.4
 *
 * Restrições de integridade:
 *  MDT-01: [id] deve ser único dentro da partida.
 *  MDT-02: [pairKey] deve ser coerente com a política de pareamento do tabuleiro.
 *  MDT-03: a combinação (x + y + layer) deve ser única no tabuleiro.
 *  MDT-04: [isRemoved] = true indica que a peça não participa mais do fluxo jogável.
 *  MDT-05: a entidade Tile NÃO armazena regra de negócio nem lógica visual.
 *
 * Coordenadas espaciais:
 *  O espaço lógico do tabuleiro usa unidades inteiras.
 *  Cada tile ocupa 2 unidades de largura e 2 de altura no grid lógico.
 *  Isso implica que posições válidas são múltiplos pares (0, 2, 4, ...).
 *  A camada (layer) cresce de baixo para cima: 0 = base, N = topo.
 *
 * PROIBIDO: qualquer import de android.*, Canvas, Compose ou coordenada de tela.
 */
data class Tile(

    /**
     * Identificador único desta instância de peça dentro da partida atual.
     * Não deve ser reutilizado entre peças distintas na mesma sessão.
     */
    val id: Int,

    /**
     * Identificador lógico de pareamento.
     * Duas peças com o mesmo pairKey são compatíveis para match (RN-13).
     * O número total de ocorrências de cada pairKey deve ser par (RN-23).
     */
    val pairKey: Int,

    /**
     * Coordenada horizontal lógica no tabuleiro.
     * Unidade: posições inteiras do grid lógico.
     */
    val x: Int,

    /**
     * Coordenada vertical lógica no tabuleiro.
     * Unidade: posições inteiras do grid lógico.
     */
    val y: Int,

    /**
     * Camada lógica vertical (profundidade de empilhamento).
     * 0 = camada base. Valores crescentes indicam peças sobre outras.
     * Peças em camada superior podem bloquear peças inferiores (RN-05).
     */
    val layer: Int,

    /**
     * Indica se a peça foi removida do fluxo jogável desta partida.
     * false = ativa (padrão).
     * true  = removida — invisível, sem influência no bloqueio, sem participação em match.
     *
     * Uma vez removida, permanece assim até reinício completo (RN-16).
     */
    val isRemoved: Boolean = false
)
