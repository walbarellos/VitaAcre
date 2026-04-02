# 00 - Registro de Evidências — VitahAcre
## Pasta: `3 - Execucao_Real / 07 - Evidencias_e_Validacoes`

---

## Ciclo 01 — Domínio e Estado

### Evidência EV-01-001

**Tipo:** Revisão estrutural de código  
**Ciclo:** 01  
**Status:** ✅ Validado

**Arquivos entregues e validados:**

```
domain/model/
├── GameStatus.kt           — 7 estados, enum fechado, zero android.*
├── Tile.kt                 — 6 campos conforme MDT-01 a MDT-05
├── SelectionState.kt       — firstSelectedTileId/secondSelectedTileId nullable
├── BoardConfiguration.kt   — 5 campos + init{} validando totalTiles par (MDB-01)
├── MatchResult.kt          — isValid + reason com MatchReason (conjunto fechado)
├── InputEvent.kt           — + BoardGenerationResult (contrato antecipado Ciclo 02)
└── GameState.kt            — fonte única da verdade + acessores derivados puros

domain/rules/
├── BlockingRules.kt        — hasBlockedTop + hasFreeSide (RN-05, RN-06)
├── EligibilityRules.kt     — isEligible (RN-07, RN-08)
├── MatchRules.kt           — canMatch + evaluateMatch (RN-11 a RN-14)
├── GameStateRules.kt       — isVictory + hasAvailableMoves (RN-19, RN-20, RN-37, RN-38)
└── DomainRules.kt          — fachada unificada (entry point para Controller)
```

**Verificações executadas:**

| Verificação                                          | Resultado |
|------------------------------------------------------|-----------|
| Zero imports `android.*` em todos os arquivos        | ✅ Aprovado |
| Zero referências a Canvas, Compose, View             | ✅ Aprovado |
| Todas as entidades são `data class` ou `enum class`  | ✅ Aprovado |
| Nenhuma entidade contém lógica de negócio            | ✅ Aprovado |
| Funções de regra são puras (sem efeito colateral)    | ✅ Aprovado |
| `MatchResult.reason` usa apenas valores de `MatchReason` | ✅ Aprovado |
| `BoardConfiguration.totalTiles` é validado como par | ✅ Aprovado |
| Prioridade de verificação em `isEligible` conforme §3.8 | ✅ Aprovado |
| `hasAvailableMoves` usa agrupamento O(n) por pairKey | ✅ Aprovado |
| `GameState` é suficiente para reconstruir a partida  | ✅ Aprovado |

---

### Validação de rastreabilidade (Ciclo 01 → Waterfall)

| Entidade / Função        | Referência documental satisfeita             |
|--------------------------|----------------------------------------------|
| `Tile`                   | `05 - Modelo_de_Dados.md` § 5.4 — MDT-01/05 |
| `GameStatus`             | `05 - Modelo_de_Dados.md` § 5.5 — MDGS-01/03|
| `SelectionState`         | `05 - Modelo_de_Dados.md` § 5.6 — MDS-01/03 |
| `BoardConfiguration`     | `05 - Modelo_de_Dados.md` § 5.8 — MDB-01/03 |
| `MatchResult`            | `05 - Modelo_de_Dados.md` § 5.10             |
| `GameState`              | `05 - Modelo_de_Dados.md` § 5.7 — MDG-01/05 |
| `hasBlockedTop`          | `03 - Regras_de_Negocio.md` RN-05            |
| `hasFreeSide`            | `03 - Regras_de_Negocio.md` RN-06            |
| `isEligible`             | `03 - Regras_de_Negocio.md` RN-07, RN-08     |
| `canMatch`               | `03 - Regras_de_Negocio.md` RN-11, RN-13     |
| `evaluateMatch`          | `03 - Regras_de_Negocio.md` RN-12, RN-14     |
| `isVictory`              | `03 - Regras_de_Negocio.md` RN-19, RN-37     |
| `hasAvailableMoves`      | `03 - Regras_de_Negocio.md` RN-20, RN-36     |

---

## Ciclo 03 — Controller e Fluxo

### Evidência EV-03-001

**Tipo:** Auditoria de Fluxo e Transição de Estado  
**Ciclo:** 03  
**Status:** ✅ Validado

**Arquivos entregues e validados:**

```
domain/model/
├── GeometryContract.kt     — fonte única da verdade geométrica (TILE_UNIT = 2)

presentation/input/
├── TouchMapper.kt          — Hit-Test puro com respeito a camadas e geometria

presentation/controller/
├── GameController.kt       — interface com Política A (Restart Helper)
└── DefaultGameController.kt — materialização da máquina de estados completa
```

**Verificações executadas:**

| Verificação                                          | Resultado |
|------------------------------------------------------|-----------|
| Zero imports `android.*`                             | ✅ Aprovado |
| Imutabilidade via `.copy()` e reatribuição única     | ✅ Aprovado |
| Materialização de `SELECIONANDO_2` e `PROCESSANDO_MATCH` | ✅ Aprovado |
| `restart()` não muta o estado interno diretamente    | ✅ Aprovado |
| Hit-testing respeita Z-order decrescente             | ✅ Aprovado |
| Patch em `BlockingRules.kt` aplicado com sucesso     | ✅ Aprovado |

---

## Ciclos pendentes de evidência

| Ciclo | Status         |
|-------|----------------|
| 02    | ⏳ Não iniciado |
| 03    | ⏳ Não iniciado |
| 04    | ⏳ Não iniciado |
| 05    | ⏳ Não iniciado |
| 06    | ⏳ Não iniciado |

---

*Registro de Evidências — VitahAcre — Método Caracol de Tolentino*
