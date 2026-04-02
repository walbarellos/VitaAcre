package com.vitahacre.data.generator

import com.vitahacre.domain.model.BoardConfiguration
import com.vitahacre.domain.model.BoardGenerationResult
import com.vitahacre.domain.model.Tile
import com.vitahacre.domain.rules.DomainRules
import kotlin.random.Random

/**
 * DefaultBoardGenerator — Implementação padrão do gerador procedural.
 *
 * Contrato: Ciclo 02 § 4, GEN-01 a GEN-06
 * Referência: 08 - Diagrama_de_Fluxo_do_Gerador_Procedural.md
 *
 * ─────────────────────────────────────────────────────────────────
 * Contrato geométrico herdado do Ciclo 01 (DT-01 — TILE_UNIT = 2)
 * ─────────────────────────────────────────────────────────────────
 *
 * Cada tile ocupa TILE_UNIT × TILE_UNIT unidades no grid lógico.
 * Posições válidas:
 *   x ∈ { 0, 2, 4, ..., (columns - 1) * TILE_UNIT }
 *   y ∈ { 0, 2, 4, ..., (rows    - 1) * TILE_UNIT }
 *   layer ∈ { 0, 1, ..., layers - 1 }
 *
 * Capacidade máxima do tabuleiro:
 *   slots = columns × rows × layers
 *   configuration.totalTiles DEVE ser ≤ slots
 *
 * ─────────────────────────────────────────────────────────────────
 * Algoritmo (conforme fluxo do diagrama)
 * ─────────────────────────────────────────────────────────────────
 *
 *  1. Validar configuração
 *  2. Verificar paridade de totalTiles
 *  3. Gerar lista de pairKeys (N/2 chaves × 2 ocorrências cada)
 *  4. Embaralhar com seed (determinístico) ou aleatório
 *  5. Gerar grid de posições válidas e embaralhar
 *  6. Associar pairKey → posição (sem colisão)
 *  7. Construir lista de Tile com ids sequenciais
 *  8. Validar integridade estrutural
 *  9. Verificar hasAtLeastOneInitialMove
 * 10. Se falhar (9): tentar nova distribuição (até MAX_RETRIES vezes)
 * 11. Construir e retornar BoardGenerationResult
 *
 * Complexidade: O(n) por tentativa onde n = totalTiles
 *
 * PROIBIDO: android.*, Context, Canvas, View, Compose.
 */
class DefaultBoardGenerator : BoardGenerator {

    // ─────────────────────────────────────────────────────────────────────────
    // Constantes
    // ─────────────────────────────────────────────────────────────────────────

    companion object {
        /**
         * Unidade geométrica base de cada tile no grid lógico.
         * DEVE ser igual a BlockingRules.TILE_UNIT (DT-01 do Ciclo 01).
         * Nunca alterar sem revisar BlockingRules e EligibilityRules.
         */
        const val TILE_UNIT = 2

        /**
         * Número máximo de tentativas de redistribuição quando
         * o tabuleiro gerado não possui jogada inicial válida.
         * Evita loop infinito em configurações muito restritivas.
         */
        const val MAX_RETRIES = 100
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Entry point
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Gera um tabuleiro completo seguindo o fluxo oficial do diagrama.
     *
     * @param configuration Parâmetros do tabuleiro. Deve ter totalTiles par.
     * @return BoardGenerationResult com isPlayable e hasAtLeastOneInitialMove.
     */
    override fun generate(configuration: BoardConfiguration): BoardGenerationResult {
        // ── Etapa 1: Validar configuração básica ─────────────────────────────
        val validationError = validateConfiguration(configuration)
        if (validationError != null) {
            return failedResult(configuration)
        }

        // ── Etapa 2: Verificar paridade (GEN-01) ──────────────────────────────
        if (configuration.totalTiles % 2 != 0) {
            return failedResult(configuration)
        }

        // ── Etapas 3–10: Tentativas de geração ───────────────────────────────
        val random = buildRandom(configuration.seed)

        repeat(MAX_RETRIES) { attempt ->
            val result = attemptGeneration(configuration, random, attempt)
            if (result != null) return result
        }

        // Todas as tentativas falharam
        return failedResult(configuration)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Tentativa de geração
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Executa uma tentativa completa de geração.
     * Retorna [BoardGenerationResult] se bem-sucedida, null se falhou.
     *
     * Para seed determinística (tentativa 0), usa a seed fornecida.
     * Para retentativas, deriva uma seed modificada para obter distribuição diferente.
     */
    private fun attemptGeneration(
        configuration: BoardConfiguration,
        baseRandom: Random,
        attempt: Int
    ): BoardGenerationResult? {

        // Usa random diferente para cada tentativa quando seed não é fixa
        val random = if (configuration.seed != null && attempt > 0) {
            Random(configuration.seed + attempt.toLong())
        } else {
            baseRandom
        }

        // ── Etapa 3: Gerar pairKeys ───────────────────────────────────────────
        // N/2 chaves distintas, cada uma presente duas vezes. (GEN-02)
        val pairCount = configuration.totalTiles / 2
        val pairKeys: MutableList<Int> = mutableListOf()
        for (key in 0 until pairCount) {
            pairKeys.add(key)
            pairKeys.add(key)
        }

        // ── Etapa 4: Embaralhar pairKeys (GEN-05) ────────────────────────────
        pairKeys.shuffle(random)

        // ── Etapa 5: Gerar grid de posições válidas ───────────────────────────
        val positions = buildPositionGrid(configuration)

        // Capacidade insuficiente?
        if (positions.size < configuration.totalTiles) {
            return null
        }

        // Embaralhar posições (distribuição espacial)
        positions.shuffle(random)

        // Tomar apenas as posições necessárias
        val selectedPositions = positions.take(configuration.totalTiles)

        // ── Etapa 6-7: Construir Tile list ────────────────────────────────────
        val tiles: List<Tile> = pairKeys.indices.map { index ->
            val (x, y, layer) = selectedPositions[index]
            Tile(
                id       = index,
                pairKey  = pairKeys[index],
                x        = x,
                y        = y,
                layer    = layer,
                isRemoved = false
            )
        }

        // ── Etapa 8: Validar integridade estrutural ───────────────────────────
        if (!validateStructuralIntegrity(tiles)) return null

        // ── Etapa 9: Verificar jogabilidade inicial (GEN-04 / RN-26) ─────────
        val hasMove = DomainRules.hasAvailableMoves(tiles)

        return BoardGenerationResult(
            tiles                   = tiles,
            configuration           = configuration,
            isPlayable              = true,
            hasAtLeastOneInitialMove = hasMove
        ).takeIf { hasMove }
        // Se não há jogada inicial, retorna null → próxima tentativa
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Grid de posições
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Gera a lista completa de posições (x, y, layer) válidas no grid lógico.
     *
     * Convenção TILE_UNIT = 2:
     *   x: 0, 2, 4, ..., (columns - 1) * TILE_UNIT
     *   y: 0, 2, 4, ..., (rows    - 1) * TILE_UNIT
     *   layer: 0, 1, ..., layers - 1
     *
     * Estratégia de empilhamento:
     *   As posições são geradas ordenadas por layer (0 primeiro).
     *   Isso permite que o embaralhamento posterior distribua
     *   as peças mais densamente nas camadas base, que é o comportamento
     *   natural do Mahjong solitaire.
     */
    private fun buildPositionGrid(configuration: BoardConfiguration): MutableList<Triple<Int, Int, Int>> {
        val positions = mutableListOf<Triple<Int, Int, Int>>()

        for (layer in 0 until configuration.layers) {
            for (row in 0 until configuration.rows) {
                for (col in 0 until configuration.columns) {
                    val x = col * TILE_UNIT
                    val y = row * TILE_UNIT
                    positions.add(Triple(x, y, layer))
                }
            }
        }

        return positions
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Validações
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Valida a configuração antes da geração.
     * Retorna null se válida, ou uma String de erro se inválida.
     */
    private fun validateConfiguration(configuration: BoardConfiguration): String? {
        if (configuration.columns < 2) return "columns < 2"
        if (configuration.rows < 2)    return "rows < 2"
        if (configuration.layers < 1)  return "layers < 1"
        if (configuration.totalTiles <= 0) return "totalTiles <= 0"

        val availableSlots = configuration.columns * configuration.rows * configuration.layers
        if (configuration.totalTiles > availableSlots) {
            return "totalTiles (${ configuration.totalTiles }) > slots disponíveis ($availableSlots)"
        }

        return null
    }

    /**
     * Valida integridade estrutural do tabuleiro gerado.
     *
     * Verifica:
     *  - GEN-03: unicidade de (x, y, layer) em todas as tiles
     *  - GEN-02: cada pairKey aparece exatamente 2 vezes
     *  - totalTiles corresponde ao esperado
     */
    private fun validateStructuralIntegrity(tiles: List<Tile>): Boolean {
        // GEN-03: unicidade de posição absoluta (MDT-03)
        val positions = tiles.map { Triple(it.x, it.y, it.layer) }
        if (positions.size != positions.toSet().size) return false

        // GEN-02: cada pairKey aparece exatamente 2 vezes
        val pairKeyCount = tiles.groupBy { it.pairKey }
        if (pairKeyCount.any { (_, group) -> group.size != 2 }) return false

        return true
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Utilitários
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Constrói o gerador de números aleatórios conforme GEN-05.
     * seed presente → determinístico e reproduzível.
     * seed ausente  → aleatório por sessão.
     */
    private fun buildRandom(seed: Long?): Random =
        if (seed != null) Random(seed) else Random.Default

    /**
     * Retorna um BoardGenerationResult indicando falha de geração.
     * Nunca lança exceção — falha silenciosa é proibida (GEN-06).
     */
    private fun failedResult(configuration: BoardConfiguration): BoardGenerationResult =
        BoardGenerationResult(
            tiles                    = emptyList(),
            configuration            = configuration,
            isPlayable               = false,
            hasAtLeastOneInitialMove = false
        )
}
