package com.vitahacre.presentation.controller

import com.vitahacre.domain.model.GameState
import com.vitahacre.domain.model.InputEvent

/**
 * GameController — Orquestrador de Fluxo.
 * 
 * Contrato Principal: [onInput] processa eventos e evolui o estado.
 * Helper: [restart] atalho para regeneração completa da sessão.
 */
interface GameController {
    /**
     * O estado atual mantido pelo controlador.
     */
    val state: GameState

    /** 
     * Porta única de entrada para interações. 
     * @param event O evento a ser processado (TOUCH ou RESTART).
     * @return O novo GameState resultante. 
     */
    fun onInput(event: InputEvent): GameState
    
    /** 
     * Atalho para regeneração via GeneratorFactory. 
     * @return Um novo estado inicial PRONTO.
     */
    fun restart(): GameState
}
