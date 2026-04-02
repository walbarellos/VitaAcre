package com.vitahacre.domain.model

/**
 * GameState — Estado global oficial da partida.
 * FONTE ÚNICA DA VERDADE da sessão (MDG-05 / RF-05).
 *
 * Contrato: MDG-01 a MDG-05
 * Referência: 05 - Modelo_de_Dados.md § 5.7
 *
 * Restrições de integridade:
 *  MDG-01: deve ser suficiente para reconstruir a partida logicamente.
 *  MDG-02: deve ser suficiente para orientar a renderização.
 *  MDG-03: toda peça referenciada na seleção deve existir em [tiles] ou ser null.
 *  MDG-04: não pode haver divergência entre [status], [selection] e a realidade lógica.
 *  MDG-05: é a fonte única da verdade — nenhum estado paralelo é aceito.
 *
 * Imutabilidade:
 *  GameState é imutável por definição. Toda atualização produz uma nova cópia.
 *  Uso: gameState.copy(status = GameStatus.VITORIA)
 *  Nunca mutar campos diretamente.
 *
 * PROIBIDO: qualquer import de android.*, Canvas, Compose, View ou coordenada de tela.
 */
data class GameState(

    /**
     * Coleção completa de peças da partida atual.
     * Inclui peças ativas e removidas — o campo [Tile.isRemoved] distingue o estado.
     * Nunca filtrar esta lista permanentemente; manter todas as peças para rastreabilidade.
     */
    val tiles: List<Tile>,

    /**
     * Estado atual da seleção de peças.
     * Representa quais tiles estão na fila de avaliação de match.
     */
    val selection: SelectionState,

    /**
     * Estado formal da partida.
     * Controla quais ações são permitidas em cada momento.
     */
    val status: GameStatus,

    /**
     * Configuração estrutural do tabuleiro desta sessão.
     * Necessária para renderização, geração e rastreabilidade.
     */
    val boardConfiguration: BoardConfiguration

) {
    companion object {

        /**
         * Cria o estado inicial de uma nova partida antes da geração do tabuleiro.
         * tiles = vazio enquanto o gerador não for executado.
         */
        fun initial(boardConfiguration: BoardConfiguration): GameState =
            GameState(
                tiles = emptyList(),
                selection = SelectionState.EMPTY,
                status = GameStatus.INICIALIZANDO,
                boardConfiguration = boardConfiguration
            )
    }

    // -------------------------------------------------------------------------
    // Acessores derivados — conveniências sem efeito colateral
    // NÃO são regras de negócio — apenas projeções do estado atual.
    // -------------------------------------------------------------------------

    /** Peças ainda ativas nesta partida (não removidas). */
    val activeTiles: List<Tile>
        get() = tiles.filter { !it.isRemoved }

    /** true se não houver nenhuma peça ativa no tabuleiro. */
    val isBoardEmpty: Boolean
        get() = activeTiles.isEmpty()

    /** Retorna a peça pelo id, ou null se não existir. */
    fun tileById(id: Int): Tile? = tiles.firstOrNull { it.id == id }

    /** Retorna a primeira peça selecionada, se existir. */
    val firstSelectedTile: Tile?
        get() = selection.firstSelectedTileId?.let { tileById(it) }

    /** Retorna a segunda peça selecionada, se existir. */
    val secondSelectedTile: Tile?
        get() = selection.secondSelectedTileId?.let { tileById(it) }
}
