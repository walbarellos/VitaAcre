# 01 - Contrato Técnico do Ciclo 01 — Domínio e Estado
## Projeto: VitahAcre

---

## 1. Objetivo

Este documento define os contratos técnicos obrigatórios da implementação do Ciclo 01.

Todo código produzido neste ciclo deve obedecer a estes contratos sem exceção.

---

## 2. Plataforma e Linguagem

- Linguagem: Kotlin
- Plataforma-alvo: Android (mas as entidades deste ciclo **não podem depender de nenhuma API Android**)
- Build: Gradle
- Módulo de destino: `domain/model/` e `domain/rules/`

---

## 3. Contratos por Entidade

### `Tile`

```kotlin
data class Tile(
    val id: Int,
    val pairKey: Int,
    val x: Int,
    val y: Int,
    val layer: Int,
    val isRemoved: Boolean = false
)
```

- `id` → único por instância na partida (MDT-01)
- `pairKey` → coerente com política de pareamento (MDT-02)
- combinação `x + y + layer` → única no tabuleiro (MDT-03)
- `isRemoved = true` → peça fora do fluxo jogável (MDT-04)
- **proibido**: lógica de regra ou visual dentro da entidade (MDT-05)

---

### `GameStatus`

```kotlin
enum class GameStatus {
    INICIALIZANDO,
    PRONTO,
    SELECIONANDO_1,
    SELECIONANDO_2,
    PROCESSANDO_MATCH,
    VITORIA,
    SEM_JOGADAS
}
```

- Conjunto fechado — nenhum estado textual livre (MDGS-02)
- Toda transição deve ser coerente com o fluxo do jogo (MDGS-03)

---

### `SelectionState`

```kotlin
data class SelectionState(
    val firstSelectedTileId: Int? = null,
    val secondSelectedTileId: Int? = null
)
```

- Segunda seleção só existe logicamente se houver possibilidade de primeira (MDS-01)
- A mesma peça não pode ocupar os dois campos (MDS-02)
- Peça removida não pode permanecer como seleção ativa (MDS-03)

---

### `GameState`

```kotlin
data class GameState(
    val tiles: List<Tile>,
    val selection: SelectionState,
    val status: GameStatus,
    val boardConfiguration: BoardConfiguration
)
```

- Deve ser suficiente para reconstruir a partida logicamente (MDG-01)
- Deve ser suficiente para orientar renderização (MDG-02)
- Toda peça referenciada na seleção deve existir em `tiles` ou ser nula (MDG-03)
- É a **fonte única da verdade** da sessão (MDG-05)

---

### `BoardConfiguration`

```kotlin
data class BoardConfiguration(
    val columns: Int,
    val rows: Int,
    val layers: Int,
    val totalTiles: Int,
    val seed: Long? = null
)
```

- `totalTiles` deve ser par (MDB-01)
- `layers` compatível com estratégia de empilhamento (MDB-02)

---

### `MatchResult`

```kotlin
data class MatchResult(
    val isValid: Boolean,
    val firstTileId: Int?,
    val secondTileId: Int?,
    val reason: String
)
```

Valores permitidos para `reason`:
- `"VALID_MATCH"`
- `"DIFFERENT_PAIR_KEY"`
- `"SAME_TILE"`
- `"TILE_NOT_ELIGIBLE"`
- `"TILE_REMOVED"`

---

## 4. Contratos das Regras Puras

As funções de regra devem residir em `domain/rules/` e obedecer:

- **sem efeito colateral** — dado o mesmo estado e a mesma entrada, produzem sempre a mesma saída (IT-06)
- **sem dependência de `Context`, `Canvas`, `View` ou qualquer API Android**
- **sem mutação direta do estado** — retornam novos valores ou resultados

### Funções obrigatórias do ciclo

```kotlin
fun isEligible(tile: Tile, allTiles: List<Tile>): Boolean
fun canMatch(tileA: Tile, tileB: Tile): Boolean
fun evaluateMatch(tileA: Tile, tileB: Tile, allTiles: List<Tile>): MatchResult
fun hasBlockedTop(tile: Tile, allTiles: List<Tile>): Boolean
fun hasFreeSide(tile: Tile, allTiles: List<Tile>): Boolean
fun hasAvailableMoves(tiles: List<Tile>): Boolean
fun isVictory(tiles: List<Tile>): Boolean
```

---

## 5. Restrições Absolutas

| Restrição | Descrição |
|-----------|-----------|
| Zero Android | Nenhuma entidade ou regra deste ciclo importa `android.*` |
| Zero UI | Nenhuma referência a `Canvas`, `Compose`, `View`, cores ou coordenadas de tela |
| Zero mutação | Estado imutável — copiar com `.copy()`, nunca mutar diretamente |
| Zero improvisação | Campos adicionais só com justificativa formal |

---

*Contrato Técnico — Ciclo 01 — VitahAcre*
