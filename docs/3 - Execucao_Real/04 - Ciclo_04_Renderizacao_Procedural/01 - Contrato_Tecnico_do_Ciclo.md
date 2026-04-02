# 01 - Contrato Técnico do Ciclo 04 — Renderização Procedural
## Projeto: VitahAcre

---

## 1. Definição de Classes e Responsabilidades

| Classe | Responsabilidade | Pacote |
|--------|------------------|--------|
| `BoardRenderer` | Desenha a malha espacial do tabuleiro baseada em TILE_UNIT | `presentation/canvas` |
| `TileRenderer` | Desenha uma peça individual (cor, borda, identificador) | `presentation/canvas` |
| `SelectionHighlightRenderer` | Renderiza o destaque visual da peça selecionada | `presentation/canvas` |
| `StateOverlayRenderer` | Renderiza overlays de estados finais (vitória/sem jogadas) | `presentation/canvas` |
| `GameScreenStateAdapter` | Adapta GameState para os renderers, coordenando chamadas | `presentation/screen` |

---

## 2. Assinaturas Mínimas

### BoardRenderer

```kotlin
interface BoardRenderer {
    fun render(boardConfiguration: BoardConfiguration, canvas: Canvas)
}
```

- Lê `boardConfiguration.columns`, `boardConfiguration.rows`, `boardConfiguration.layers`
- Calcula posições com base em `TILE_UNIT` (constante importada de domínio)
- Desenha linhas de grade ou estrutura visual do tabuleiro

---

### TileRenderer

```kotlin
interface TileRenderer {
    fun render(tile: Tile, position: Offset, isSelected: Boolean, canvas: Canvas)
}
```

- Recebe `Tile` pronta para render
- `position`: coordenadas (x, y) já calculadas como múltiplo de TILE_UNIT
- `isSelected`: flag booleana para aplicar destaque visual
- Desenha: cor baseada em `tile.pairKey`, borda, identificador textual (opcional)

---

### SelectionHighlightRenderer

```kotlin
interface SelectionHighlightRenderer {
    fun render(selectedTileId: String?, tiles: List<Tile>, canvas: Canvas)
}
```

- Recebe ID da peça selecionada (nullable)
- Consulta em `tiles` para obter posição e renderiza destaque
- O destaque pode ser: borda grossa, sombra, cor alterada, ou combinação

---

### StateOverlayRenderer

```kotlin
interface StateOverlayRenderer {
    fun render(gameStatus: GameStatus, canvas: Canvas)
}
```

- Se `gameStatus == VITORIA`: renderiza overlay de celebração
- Se `gameStatus == SEM_JOGADAS`: renderiza overlay de "bloqueado"
- Para outros estados: não rendering nada (NOOP)

---

### GameScreenStateAdapter

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

- Orquestra a顺序 de rendering
- 1. Renderiza tabuleiro (boardRenderer)
- 2. Renderiza peças não-removidas (tileRenderer)
- 3. Aplica destaque de seleção se houver (selectionHighlightRenderer)
- 4. Renderiza overlay de estado final se aplicável (stateOverlayRenderer)

---

## 3. Fluxo de Dados

```
GameState (fonte da verdade)
    │
    ├── boardConfiguration → BoardRenderer
    │       └── desenha malha
    │
    ├── tiles → TileRenderer
    │       ├── filtra isRemoved == false
    │       └── desenha cada peça
    │
    ├── selection.firstSelectedTileId → SelectionHighlightRenderer
    │       └── aplica destaque visual
    │
    └── status → StateOverlayRenderer
            └── renderiza overlay se aplicável
```

---

## 4. Regras de Isolamento (Guardrails)

### RND-01: Zero Lógica de Negócio no Renderer

O renderer **nunca** deve:
- Importar `domain/rules/`
- Chamar `DomainRules.isEligible()`
- Chamar `DomainRules.canMatch()`
- Chamar `DomainRules.evaluateMatch()`

**Justificativa:** O renderer lê, não decide. A lógica de negócio pertence ao domínio.

---

### RND-02: Zero Sprite Externo no Núcleo

Renderização é **procedural**:
- Peças desenhadas com formas geométricas (rect, circle)
- Cores derivadas de `pairKey` via hash ou paleta fixa
- Zero imagens, bitmaps ou assets externos

---

### RND-03: Coordenadas Derivadas de TILE_UNIT

Todas as coordenadas de desenho devem ser múltiplas de `TILE_UNIT = 2`:
- `x = tile.x * cellSize`
- `y = tile.y * cellSize`
- Onde `cellSize` é derivado da tela real (largura / colunas)

---

### RND-04: Peças Removidas Não São Renderizadas

```kotlin
tiles.filter { !it.isRemoved }.forEach { tile ->
    tileRenderer.render(tile, position, isSelected, canvas)
}
```

Peças com `isRemoved = true` são omitidas do rendering.

---

### RND-05: Destaque Visível e Imediato

Quando `selection.firstSelectedTileId` não é nulo:
- A peça correspondente deve ter destaque visual **iminentemente perceptível**
- Opções: borda grossa, cor de fundo alterada, outline, sombra

---

### RND-06: Overlays de Estados Finais

- **VITORIA**: overlay com texto "Vitória!", cor festiva, sem bloquearinteração completamente
- **SEM_JOGADAS**: overlay com texto "Sem Jogadas", cor neutra, com opção de reinício

---

## 5. Dependências Externas

| Dependência | Origem | Uso |
|-------------|--------|-----|
| `GameState` | Ciclo 03 | Fonte da verdade |
| `BoardConfiguration` | Ciclo 01 | Dimensões do tabuleiro |
| `Tile` | Ciclo 01 | Entidade da peça |
| `SelectionState` | Ciclo 01 | Estado de seleção |
| `GameStatus` | Ciclo 01 | Status do jogo |
| `TILE_UNIT` | Ciclo 02 | Constante geométrica |
| `Canvas` | Android Framework | API de desenho |
| `Color` | Android Framework | Cores |
| `Offset` | Android Framework | Coordenadas |

**Nenhuma dependência de domínio além de constantes.**

---

## 6. Interface com Ciclo 03

O Ciclo 04 depende do `GameState` retornado pelo `GameController` do Ciclo 03.

```kotlin
// Input: GameState imutável
val gameState: GameState = controller.processInput(inputEvent)

// Output: nada — render é efeito colateral sobre Canvas
adapter.render(gameState, cellSize, canvas)
```

---

## 7. Critérios de Aceitação Técnica

| Critério | Definição |
|----------|-----------|
| Compilação | `./gradlew compileDebugKotlin` sem erro |
|Zero imports de domínio exceto constantes | Verificação por auditoria |
| Tabuleiro visível | Renderizado com geometria correta |
| Peças identificáveis | Cores distinguem pairKey |
| Seleção destacada | Destaque perceptível |
| Overlay vitória | Texto + cor quando status == VITORIA |
| Overlay sem jogadas | Texto + cor quando status == SEM_JOGADAS |
| Responsividade | Render < 16ms por frame |

---

## 8. Contrato de Integração com UI

### GameScreen (Jetpack Compose)

```kotlin
@Composable
fun GameScreen(
    controller: GameController,
    gameState: StateFlow<GameState>
) {
    val state by gameState.collectAsState()

    Canvas(modifier = Modifier.fillMaxSize()) { canvas ->
        val cellSize = calculateCellSize(size.width, state.boardConfiguration.columns)
        
        val adapter = GameScreenStateAdapter(
            boardRenderer = DefaultBoardRenderer(),
            tileRenderer = DefaultTileRenderer(),
            selectionHighlightRenderer = DefaultSelectionHighlightRenderer(),
            stateOverlayRenderer = DefaultStateOverlayRenderer()
        )
        adapter.render(state, cellSize, canvas)
    }
}
```

O adapter recebe `GameState` via StateFlow e renderiza no Canvas do Compose.

---

## 9. Estrutura de Arquivos

```
app/src/main/kotlin/com/vitahacre/
├── presentation/
│   ├── canvas/
│   │   ├── BoardRenderer.kt
│   │   ├── TileRenderer.kt
│   │   ├── SelectionHighlightRenderer.kt
│   │   └── StateOverlayRenderer.kt
│   └── screen/
│       └── GameScreen.kt
```

---

## 10. Assinatura Final do Contrato

Este contrato define a arquitetura de renderização procedural do Ciclo 04.

| Item | Status |
|------|--------|
| Contrato definido | ✅ |
| Classes especificadas | ✅ |
| Assinaturas definidas | ✅ |
| Guardrails estabelecidos | ✅ |
| Integração com Ciclo 03 definida | ✅ |
| Critérios de aceitação definidos | ✅ |

---

*Contrato Técnico — Ciclo 04 — VitahAcre — Método Caracol de Tolentino*
