# 01 - Contrato Técnico do Ciclo 02 — Gerador Procedural
## Projeto: VitahAcre

---

## 1. Pré-requisito

Ciclo 01 concluído, checklist aprovado, evidência registrada.

---

## 2. Módulo de Destino

`data/generator/`

---

## 3. Entidade a ser implementada

### `BoardGenerationResult`

```kotlin
data class BoardGenerationResult(
    val tiles: List<Tile>,
    val configuration: BoardConfiguration,
    val isPlayable: Boolean,
    val hasAtLeastOneInitialMove: Boolean
)
```

- Representa o resultado completo da geração
- `isPlayable` deve ser `true` apenas se `tiles.size % 2 == 0` e a estrutura for íntegra
- `hasAtLeastOneInitialMove` deve ser `true` apenas se existir ao menos um par elegível no estado inicial

---

## 4. Interface do Gerador

```kotlin
interface BoardGenerator {
    fun generate(configuration: BoardConfiguration): BoardGenerationResult
}
```

Implementação padrão: `DefaultBoardGenerator`

---

## 5. Invariantes da Geração

| Regra | Descrição |
|-------|-----------|
| GEN-01 | `totalTiles` deve ser par |
| GEN-02 | Cada `pairKey` deve aparecer exatamente 2 vezes |
| GEN-03 | Combinação `x + y + layer` única por peça (RN-03) |
| GEN-04 | Ao menos um par elegível no estado gerado |
| GEN-05 | `seed` não nulo → geração determinística e reproduzível |
| GEN-06 | Tabuleiro inválido nunca é retornado como `isPlayable = true` |

---

## 6. Restrições Absolutas

- Zero dependência de `android.*`, `Context`, `Canvas` ou qualquer API Android
- O gerador não pode modificar entidades do Ciclo 01 — apenas criá-las
- Falhas de geração devem retornar `BoardGenerationResult(isPlayable = false, ...)`, nunca lançar exceção silenciosa

---

*Contrato Técnico — Ciclo 02 — VitahAcre*
