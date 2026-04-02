# 00 - Visão do Ciclo 03 — Controller e Fluxo
## Projeto: VitahAcre

---

## 1. Pré-condições de Entrada

| Pré-condição                                        | Status |
|-----------------------------------------------------|--------|
| Ciclo 01 — Domínio e Estado concluído e congelado   | ✅     |
| Ciclo 02 — Gerador Procedural concluído e congelado | ✅     |
| `DomainRules` estável e acessível                   | ✅     |
| `GeneratorFactory.createInitialGameState()` disponível | ✅  |
| `GameStatus` com todos os estados estáveis          | ✅     |

---

## 2. Objetivo do Ciclo

Transformar o estado lógico em **fluxo real de jogada**.

Este ciclo conecta o toque do usuário → lógica do domínio → atualização de GameState.

Ele NÃO renderiza. Não desenha. Não usa Canvas.
Ele processa eventos e produz estados.

---

## 3. Escopo Oficial

| Elemento                    | Descrição                                             |
|-----------------------------|-------------------------------------------------------|
| `GameController`            | Orquestrador central do fluxo de jogada               |
| `InputEvent` → resolução    | Mapeamento de toque em ação de domínio                |
| Seleção de primeira peça    | `PRONTO` → `SELECIONANDO_1`                          |
| Seleção de segunda peça     | `SELECIONANDO_1` → `SELECIONANDO_2`                  |
| Avaliação de match          | `SELECIONANDO_2` → `PROCESSANDO_MATCH`               |
| Remoção de peças            | Atualização de tiles via `.copy(isRemoved = true)`   |
| Transição pós-match         | `PROCESSANDO_MATCH` → PRONTO / VITORIA / SEM_JOGADAS |
| Reinício de partida         | Qualquer estado → novo `GameState` via `GeneratorFactory` |

---

## 4. Contratos herdados obrigatórios

### Do Ciclo 01
- `DomainRules.isEligible()` — chamado antes de aceitar qualquer seleção
- `DomainRules.evaluateMatch()` — único ponto de decisão de match válido
- `DomainRules.checkTerminalState()` — chamado após toda remoção
- `GameState` como fonte única da verdade — controller nunca mantém estado próprio

### Do Ciclo 02
- `GeneratorFactory.createInitialGameState()` — usado no reinício
- `GameState.status = PRONTO` ao receber estado inicial do gerador

---

## 5. Assinatura esperada do Controller

```kotlin
class GameController(
    private val configuration: BoardConfiguration,
    private val generator: BoardGenerator = GeneratorFactory.defaultGenerator()
) {
    var state: GameState
        private set

    fun onInput(event: InputEvent): GameState
    fun restart(): GameState
}
```

**Invariante crítico:**
`onInput()` nunca modifica `state` diretamente — sempre retorna novo `GameState`
e atualiza `state` via reassignment. Imutabilidade preservada.

---

## 6. Fluxo de transições de estado

```
[PRONTO]
  │ toque em tile elegível
  ▼
[SELECIONANDO_1]  — firstSelectedTileId preenchido
  │ toque em tile elegível diferente
  ▼
[SELECIONANDO_2]  — secondSelectedTileId preenchido
  │ (automático)
  ▼
[PROCESSANDO_MATCH]
  │ evaluateMatch()
  ├─ isValid = true  → remover tiles → checkTerminalState()
  │     ├─ VICTORY  → [VITORIA]
  │     ├─ NO_MOVES → [SEM_JOGADAS]
  │     └─ CONTINUE → [PRONTO]
  └─ isValid = false → limpar seleção → [PRONTO]
```

**Política de seleção após match inválido (RN-18):**
Seleção é zerada. Nenhuma peça fica "presa".
O estado retorna a `PRONTO` com `SelectionState.EMPTY`.

---

## 7. Fora de escopo neste ciclo

- Renderização via Canvas
- Coordenadas de pixel para mapeamento de toque
- Testes automatizados formais
- Build Android

---

## 8. Estado atual do ciclo

| Item                      | Estado      |
|---------------------------|-------------|
| Pré-condições satisfeitas | ✅          |
| Visão do ciclo criada     | ✅          |
| Implementação iniciada    | ✅          |
| Checklist concluído       | ✅          |
| Ciclo congelado           | ✅ CONGELADO |

---

**Ciclo 03 congelado parcialmente.**
Não reabrir para refatoração estrutural.

---

*Ciclo 03 — VitahAcre — Método Caracol de Tolentino*
