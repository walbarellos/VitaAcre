# 02 - Plano de Implementação — Ciclo 04 — Renderização Procedural
## Projeto: VitahAcre

---

## ordem de Implementação

### Passo 1 — Criar diretório de renderização

Diretório: `app/src/main/kotlin/com/vitaacare/vitahacre/presentation/canvas/`

---

### Passo 2 — Implementar BoardRenderer

Arquivo: `presentation/canvas/BoardRenderer.kt`

Interface:
```kotlin
interface BoardRenderer {
    fun render(boardConfiguration: BoardConfiguration, cellSize: Float, canvas: Canvas)
}
```

Implementação: `DefaultBoardRenderer`
- Desenha grade baseada em `columns`, `rows`, `layers`
- Usa `TILE_UNIT` para espaçamento

---

### Passo 3 — Implementar TileRenderer

Arquivo: `presentation/canvas/TileRenderer.kt`

Interface:
```kotlin
interface TileRenderer {
    fun render(tile: Tile, position: Offset, isSelected: Boolean, canvas: Canvas)
}
```

Implementação: `DefaultTileRenderer`
- Desenha retângulo com cor derivada de `tile.pairKey`
- Desenha borda
- Se `isSelected`: aplica destaque visual

---

### Passo 4 — Implementar SelectionHighlightRenderer

Arquivo: `presentation/canvas/SelectionHighlightRenderer.kt`

Interface:
```kotlin
interface SelectionHighlightRenderer {
    fun render(selectedTileId: String?, tiles: List<Tile>, cellSize: Float, canvas: Canvas)
}
```

Implementação: `DefaultSelectionHighlightRenderer`
- Recebe `selectedTileId` (pode ser null)
- Busca posição em `tiles`
- Aplica destaque (borda grossa ou sombra)

---

### Passo 5 — Implementar StateOverlayRenderer

Arquivo: `presentation/canvas/StateOverlayRenderer.kt`

Interface:
```kotlin
interface StateOverlayRenderer {
    fun render(gameStatus: GameStatus, canvas: Canvas)
}
```

Implementação: `DefaultStateOverlayRenderer`
- Se `VITORIA`: overlay verde com texto
- Se `SEM_JOGADAS`: overlay vermelho com texto
- Demais estados: NOOP

---

### Passo 6 — Criar GameScreenStateAdapter

Arquivo: `presentation/screen/GameScreenStateAdapter.kt`

```kotlin
class GameScreenStateAdapter(
    private val boardRenderer: BoardRenderer,
    private val tileRenderer: TileRenderer,
    private val selectionHighlightRenderer: SelectionHighlightRenderer,
    private val stateOverlayRenderer: StateOverlayRenderer
) {
    fun render(gameState: GameState, cellSize: Float, canvas: Canvas)
}
```

Ordem de chamadas:
1. `boardRenderer.render(boardConfiguration, cellSize, canvas)`
2. `tiles.filter { !it.isRemoved }.forEach { tile -> tileRenderer.render(...) }`
3. `selectionHighlightRenderer.render(selection.firstSelectedTileId, tiles, cellSize, canvas)`
4. `stateOverlayRenderer.render(gameState.status, canvas)`

---

### Passo 7 — Integrar com GameScreen

Arquivo: `presentation/screen/GameScreen.kt`

- Criar `Canvas` do Compose
- Calcular `cellSize` a partir de dimensões da tela
- Instanciar `GameScreenStateAdapter`
- Chamar `adapter.render(gameState, cellSize, canvas)`

---

### Passo 8 — Validação Visual

| Item | Validação |
|------|-----------|
| Tabuleiro visível | Grid renderizado com geometria correta |
| Peças identificáveis | Cores distinguem `pairKey` |
| Seleção destacada | Destaque perceptível ao selecionar |
| Peça removida some | `isRemoved = true` → não renderizado |
| Overlay vitória | Texto + cor quando `status == VITORIA` |
| Overlay sem jogadas | Texto + cor quando `status == SEM_JOGADAS` |

---

## Dependências entre Passos

```
Passo 1 (diretorio)
    └── Passo 2 (BoardRenderer) ──────────────┐
    └── Passo 3 (TileRenderer) ───────────────┼──> Passo 6 (Adapter) ──> Passo 7 (GameScreen) ──> Passo 8 (Validação)
    └── Passo 4 (SelectionHighlightRenderer) ─┤
    └── Passo 5 (StateOverlayRenderer) ───────┘
```

---

## Critério de Progressão

Cada passo deve compilar individualmente antes de prosseguir.

---

*Plano de Implementação — Ciclo 04 — VitahAcre — Método Caracol de Tolentino*
