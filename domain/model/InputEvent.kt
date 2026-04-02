package com.vitahacre.domain.model

/**
 * InputEventType — Tipos formais de evento de entrada do usuário.
 *
 * Contrato: 05 - Modelo_de_Dados.md § 5.9
 * Conjunto fechado — nenhum tipo textual livre é permitido.
 */
enum class InputEventType {
    /** Toque em posição da tela — requer coordenadas x e y. */
    TOUCH,

    /** Solicitação de reinício de partida — não requer coordenadas. */
    RESTART
}

/**
 * InputEvent — Representação formal de uma entrada do usuário.
 *
 * Contrato: 05 - Modelo_de_Dados.md § 5.9
 *
 * Para eventos do tipo TOUCH: [x] e [y] devem ser fornecidos.
 * Para eventos do tipo RESTART: [x] e [y] podem ser null.
 *
 * Esta entidade pertence à fronteira entre a camada de apresentação e o controller.
 * NÃO depende de nenhuma API Android — é uma estrutura de dado puro.
 */
data class InputEvent(

    /**
     * Posição horizontal do toque em coordenadas de tela (pixels).
     * null para eventos que não envolvem posição espacial (ex: RESTART).
     */
    val x: Float? = null,

    /**
     * Posição vertical do toque em coordenadas de tela (pixels).
     * null para eventos que não envolvem posição espacial (ex: RESTART).
     */
    val y: Float? = null,

    /**
     * Tipo formal do evento.
     */
    val type: InputEventType
)

// =============================================================================

/**
 * BoardGenerationResult — Resultado da execução do gerador procedural.
 *
 * Contrato: 05 - Modelo_de_Dados.md § 5.11
 * Produzido por: Ciclo 02 — Gerador Procedural
 *
 * [isPlayable] deve ser false se o tabuleiro gerado violar RN-24 ou RN-25.
 * [hasAtLeastOneInitialMove] verifica RN-26 — condição obrigatória de entrada.
 */
data class BoardGenerationResult(

    /**
     * Lista de peças geradas para a partida.
     * Deve respeitar: MDT-01, MDT-02, MDT-03.
     */
    val tiles: List<Tile>,

    /**
     * Configuração estrutural usada para esta geração.
     */
    val configuration: BoardConfiguration,

    /**
     * true se a distribuição estrutural do tabuleiro é válida.
     * false indica problema de integridade — não deve iniciar a partida.
     */
    val isPlayable: Boolean,

    /**
     * true se existe ao menos uma jogada válida ao início da partida (RN-26).
     * false é estado terminal imediato — deve forçar nova geração.
     */
    val hasAtLeastOneInitialMove: Boolean
)
