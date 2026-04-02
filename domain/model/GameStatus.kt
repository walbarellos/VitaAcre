package com.vitahacre.domain.model

/**
 * GameStatus — Estado formal oficial da partida.
 *
 * Contrato: MDGS-01, MDGS-02, MDGS-03
 * Referência: 05 - Modelo_de_Dados.md § 5.5
 *
 * Conjunto fechado — nenhum estado textual livre é permitido.
 * Toda transição deve ser coerente com o fluxo do jogo.
 *
 * Diagrama de transições oficiais:
 *
 *  INICIALIZANDO → PRONTO
 *  PRONTO        → SELECIONANDO_1
 *  SELECIONANDO_1 → SELECIONANDO_2
 *  SELECIONANDO_2 → PROCESSANDO_MATCH
 *  PROCESSANDO_MATCH → PRONTO | VITORIA | SEM_JOGADAS
 *  VITORIA       → (terminal — somente reinício externo)
 *  SEM_JOGADAS   → (terminal — somente reinício externo)
 */
enum class GameStatus {

    /**
     * O sistema está preparando a partida.
     * Nenhuma interação do usuário é aceita neste estado.
     */
    INICIALIZANDO,

    /**
     * O tabuleiro está pronto e aguarda a primeira seleção do usuário.
     * Estado de entrada do fluxo normal de jogada.
     */
    PRONTO,

    /**
     * Uma peça foi selecionada como primeira.
     * O sistema aguarda a seleção da segunda peça.
     */
    SELECIONANDO_1,

    /**
     * Uma segunda peça foi indicada.
     * O sistema aguarda a avaliação de compatibilidade.
     */
    SELECIONANDO_2,

    /**
     * O sistema está avaliando a tentativa de match entre as duas peças selecionadas.
     * Nenhuma interação adicional do usuário deve ser processada durante este estado.
     */
    PROCESSANDO_MATCH,

    /**
     * Todas as peças foram removidas do tabuleiro.
     * Estado terminal positivo. Só sai por reinício completo.
     * Condição formal: RN-19 e RN-37
     */
    VITORIA,

    /**
     * Ainda existem peças ativas, mas nenhum par válido disponível.
     * Estado terminal negativo. Só sai por reinício completo.
     * Condição formal: RN-20 e RN-38
     */
    SEM_JOGADAS
}
