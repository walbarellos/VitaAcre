# 01 - Contrato Técnico do Ciclo 03 — Controller e Fluxo
## Projeto: VitahAcre

---

## 1. Pré-requisito

Ciclos 01 e 02 concluídos, checklists aprovados, evidências registradas.

---

## 2. Módulo de Destino

`presentation/controller/` e `presentation/input/`

---

## 3. Entidade a ser implementada

### `InputEvent`

```kotlin
data class InputEvent(
    val x: Float? = null,
    val y: Float? = null,
    val type: InputEventType
)

enum class InputEventType {
    TOUCH,
    RESTART
}
```

---

## 4. Interface do Controller

```kotlin
interface GameController {
    fun onInput(event: InputEvent, state: GameState): GameState
}
```

Implementação padrão: `DefaultGameController`

---

## 5. Fluxo Oficial da Jogada

O controller deve implementar exatamente este fluxo:

```
InputEvent(TOUCH, x, y)
    ↓
Mapear coordenadas → posição lógica de Tile
    ↓
Verificar elegibilidade da Tile (usar isEligible do Ciclo 01)
    ↓
Se status == PRONTO ou SELECIONANDO_1:
    → registrar como firstSelectedTileId
    → status = SELECIONANDO_1
Se status == SELECIONANDO_1:
    → registrar como secondSelectedTileId
    → status = PROCESSANDO_MATCH
    → avaliar com evaluateMatch()
    → se válido: marcar as duas Tiles como isRemoved = true, limpar seleção
    → se inválido: limpar seleção, status = SELECIONANDO_1
    ↓
Após remoção:
    → verificar isVictory() → se true: status = VITORIA
    → verificar hasAvailableMoves() → se false: status = SEM_JOGADAS

InputEvent(RESTART)
    ↓
Regenerar tabuleiro com BoardGenerator
    ↓
Retornar novo GameState com status = PRONTO
```

---

## 6. Contratos do Controller

| Contrato | Descrição |
|----------|-----------|
| CTRL-01 | O controller não chama `Canvas`, `Compose`, `View` nem qualquer API de UI |
| CTRL-02 | O controller não modifica o `GameState` recebido — retorna novo estado |
| CTRL-03 | Todo evento `TOUCH` passa por verificação de elegibilidade antes de seleção |
| CTRL-04 | O fluxo de jogada segue a ordem oficial acima sem desvio |
| CTRL-05 | Toque fora de qualquer Tile não altera o estado |
| CTRL-06 | Toque em Tile removida não altera o estado |

---

## 7. Mapeamento de Coordenadas

O controller recebe coordenadas de tela (`Float`) e deve convertê-las para posição lógica de `Tile`.

A função de mapeamento deve:
- receber `x: Float`, `y: Float` e as dimensões de célula definidas pela renderização
- retornar `Tile?` (nulo se nenhuma peça na posição)

Esta função é o único ponto de contato entre coordenadas de tela e domínio.

---

*Contrato Técnico — Ciclo 03 — VitahAcre*
