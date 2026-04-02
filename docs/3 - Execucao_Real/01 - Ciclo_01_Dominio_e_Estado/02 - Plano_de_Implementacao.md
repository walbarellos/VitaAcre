# 02 - Plano de Implementação — Ciclo 01 — Domínio e Estado
## Projeto: VitahAcre

---

## 1. Objetivo

Definir a ordem exata de implementação dos elementos do Ciclo 01.

Cada passo deve ser concluído antes de abrir o próximo.

---

## 2. Caminho de Implementação

### Passo 1 — Criar estrutura de pacotes

```
app/src/main/java/com/vitahacre/
├── domain/
│   ├── model/
│   └── rules/
```

---

### Passo 2 — Implementar `GameStatus`

Arquivo: `domain/model/GameStatus.kt`

Motivo: é o enum mais simples e sem dependências. Serve de base para `GameState`.

---

### Passo 3 — Implementar `Tile`

Arquivo: `domain/model/Tile.kt`

Motivo: é a entidade central do domínio. Tudo mais depende dela.

---

### Passo 4 — Implementar `SelectionState`

Arquivo: `domain/model/SelectionState.kt`

Motivo: depende apenas de `Int?`. Sem outras dependências.

---

### Passo 5 — Implementar `BoardConfiguration`

Arquivo: `domain/model/BoardConfiguration.kt`

Motivo: independente das demais entidades. Necessário para `GameState`.

---

### Passo 6 — Implementar `MatchResult`

Arquivo: `domain/model/MatchResult.kt`

Motivo: necessário para as funções de regra.

---

### Passo 7 — Implementar `GameState`

Arquivo: `domain/model/GameState.kt`

Motivo: agrega todas as entidades anteriores. Só pode ser feito depois dos passos 2–5.

---

### Passo 8 — Implementar regras puras de bloqueio

Arquivo: `domain/rules/BlockingRules.kt`

Funções:
- `hasBlockedTop(tile, allTiles)`
- `hasFreeSide(tile, allTiles)`

Lógica baseada em: RN-05, RN-06, RN-07 (`03 - Regras_de_Negocio.md`)

---

### Passo 9 — Implementar regra de elegibilidade

Arquivo: `domain/rules/EligibilityRules.kt`

Funções:
- `isEligible(tile, allTiles)`

Lógica baseada em: RN-07, RN-08

---

### Passo 10 — Implementar regras de match

Arquivo: `domain/rules/MatchRules.kt`

Funções:
- `canMatch(tileA, tileB)`
- `evaluateMatch(tileA, tileB, allTiles)`

Lógica baseada em: RN-09 a RN-13 (`03 - Regras_de_Negocio.md`)

---

### Passo 11 — Implementar regras de estado final

Arquivo: `domain/rules/GameStateRules.kt`

Funções:
- `hasAvailableMoves(tiles)`
- `isVictory(tiles)`

Lógica baseada em: RN-14, RN-15, RN-16

---

### Passo 12 — Revisão e validação manual

- Revisar cada entidade contra `05 - Modelo_de_Dados.md`
- Revisar cada função contra `03 - Regras_de_Negocio.md`
- Confirmar zero dependências Android em todos os arquivos

---

## 3. Ordem de Dependências

```
GameStatus
    ↓
Tile
    ↓
SelectionState   BoardConfiguration   MatchResult
    ↓                   ↓
         GameState
              ↓
    BlockingRules → EligibilityRules → MatchRules → GameStateRules
```

---

## 4. Estimativa

| Passo | Complexidade |
|-------|-------------|
| 1–6   | Baixa — estruturas puras |
| 7     | Média — agrega tudo |
| 8–9   | Média — lógica espacial |
| 10    | Alta — lógica de match com elegibilidade |
| 11    | Média — varredura do tabuleiro |
| 12    | Baixa — revisão |

---

*Plano de Implementação — Ciclo 01 — VitahAcre*
