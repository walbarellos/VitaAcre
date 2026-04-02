# 02 - Plano de Implementação — Ciclo 03 — Controller e Fluxo
## Projeto: VitahAcre

---

## Ordem de Implementação

### Passo 1 — Implementar `InputEventType` e `InputEvent`
Arquivo: `presentation/input/InputEvent.kt`

---

### Passo 2 — Implementar função de mapeamento de coordenadas
Arquivo: `presentation/input/TouchMapper.kt`

Função: `fun mapToTile(x: Float, y: Float, tiles: List<Tile>, cellSize: Float): Tile?`

---

### Passo 3 — Definir interface `GameController`
Arquivo: `presentation/controller/GameController.kt`

---

### Passo 4 — Implementar `DefaultGameController`
Arquivo: `presentation/controller/DefaultGameController.kt`

Implementar os ramos do fluxo oficial na ordem:
1. `RESTART` → delegação ao gerador
2. `TOUCH` + elegibilidade
3. Primeira seleção
4. Segunda seleção + match
5. Pós-match: vitória / sem jogadas

---

### Passo 5 — Validação manual do fluxo completo

Simular via teste manual ou função de entrypoint simples:
- Partida com tabuleiro pequeno (4 peças)
- Executar jogada válida → confirmar remoção
- Executar jogada inválida → confirmar não remoção
- Executar jogadas até vitória → confirmar status `VITORIA`
- Chamar `RESTART` → confirmar novo estado limpo

---

*Plano de Implementação — Ciclo 03 — VitahAcre*
