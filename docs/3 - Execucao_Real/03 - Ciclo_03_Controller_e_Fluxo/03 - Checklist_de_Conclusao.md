# 03 - Checklist de Conclusão — Ciclo 03 — Controller e Fluxo
## Projeto: VitahAcre

---

## Bloco 1 — Entidades

- [x] `InputEventType` implementado com `TOUCH` e `RESTART`
- [x] `InputEvent` implementado com `x`, `y`, `type`

---

## Bloco 2 — Mapeamento

- [x] `TouchMapper` implementado
- [x] Retorna `null` para toque fora de qualquer Tile
- [x] Retorna `null` para toque em Tile removida

---

## Bloco 3 — Controller

- [x] Interface `GameController` definida
- [x] `DefaultGameController` implementado
- [x] CTRL-01: zero chamadas de UI
- [x] CTRL-02: retorna novo `GameState`, nunca muta o recebido
- [x] CTRL-03: elegibilidade verificada antes de qualquer seleção
- [x] CTRL-04: fluxo de jogada segue ordem oficial
- [x] CTRL-05: toque fora de Tile → estado inalterado
- [x] CTRL-06: toque em Tile removida → estado inalterado
- [x] Primeira seleção → `status = SELECIONANDO_1`
- [x] Segunda seleção + match válido → peças removidas, seleção limpa
- [x] Segunda seleção + match inválido → seleção limpa, `status = PRONTO`
- [x] Pós-remoção: `isVictory()` verificada → `VITORIA` se positivo
- [x] Pós-remoção: `hasAvailableMoves()` verificada → `SEM_JOGADAS` se negativo
- [x] `RESTART` → novo `GameState` com status `PRONTO`

---

## Bloco 4 — Evidência

- [x] Evidência registrada em `07 - Evidencias_e_Validacoes/`

---

## Estado do Checklist

| Bloco | Itens | Concluídos |
|-------|-------|------------|
| Entidades | 2 | 2 |
| Mapeamento | 3 | 3 |
| Controller | 14 | 14 |
| Evidência | 1 | 1 |
| **Total** | **20** | **20** |

---

## ✅ STATUS DO CICLO 03: CONCLUÍDO

---

*Checklist de Conclusão — Ciclo 03 — VitahAcre*
