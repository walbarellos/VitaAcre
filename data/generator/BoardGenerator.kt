package com.vitahacre.data.generator

import com.vitahacre.domain.model.BoardConfiguration
import com.vitahacre.domain.model.BoardGenerationResult

/**
 * BoardGenerator — Contrato formal do gerador procedural do VitahAcre.
 *
 * Contrato: Contrato Técnico Ciclo 02 § 4
 * Referência: 08 - Diagrama_de_Fluxo_do_Gerador_Procedural.md
 *
 * Implementação padrão: [DefaultBoardGenerator]
 *
 * Responsabilidade exclusiva:
 *  Receber uma [BoardConfiguration] e retornar um [BoardGenerationResult]
 *  com tabuleiro estruturalmente válido e jogável.
 *
 * Garantias do contrato:
 *  - Nunca lança exceção silenciosa.
 *  - Falha retorna BoardGenerationResult(isPlayable = false). (GEN-06)
 *  - Nunca modifica entidades do Ciclo 01.
 *  - Zero dependência de android.*, Canvas, Compose ou View.
 */
interface BoardGenerator {

    /**
     * Gera um tabuleiro completo a partir da [configuration] fornecida.
     *
     * Fluxo obrigatório conforme diagrama:
     *  validar → gerar pairKeys → distribuir → verificar unicidade →
     *  validar estrutura → verificar jogabilidade → retornar resultado
     *
     * @param configuration Parâmetros estruturais do tabuleiro.
     * @return [BoardGenerationResult] com tiles, configuração e indicadores de validade.
     */
    fun generate(configuration: BoardConfiguration): BoardGenerationResult
}
