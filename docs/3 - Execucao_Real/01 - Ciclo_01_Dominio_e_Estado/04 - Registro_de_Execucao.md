# 04 - Registro de Execução — Ciclo 01 — Domínio e Estado
## Projeto: VitahAcre

---

## 1. Objetivo

Este documento registra cronologicamente o que foi feito durante a execução do Ciclo 01.

---

## 2. Registro

### [SESSÃO 01] — Implementação completa do Ciclo 01

**O que foi feito:**

| Arquivo                              | Pacote              | Status |
|--------------------------------------|---------------------|--------|
| `GameStatus.kt`                      | `domain/model`      | ✅     |
| `Tile.kt`                            | `domain/model`      | ✅     |
| `SelectionState.kt`                  | `domain/model`      | ✅     |
| `BoardConfiguration.kt`             | `domain/model`      | ✅     |
| `MatchResult.kt` + `MatchReason`     | `domain/model`      | ✅     |
| `InputEvent.kt` + `BoardGenerationResult` | `domain/model` | ✅     |
| `GameState.kt`                       | `domain/model`      | ✅     |
| `BlockingRules.kt`                   | `domain/rules`      | ✅     |
| `EligibilityRules.kt`                | `domain/rules`      | ✅     |
| `MatchRules.kt`                      | `domain/rules`      | ✅     |
| `GameStateRules.kt`                  | `domain/rules`      | ✅     |
| `DomainRules.kt` (fachada)           | `domain/rules`      | ✅     |

**Total: 12 arquivos Kotlin — zero dependências Android.**

---

**Decisões técnicas tomadas:**

#### DT-01 — TILE_UNIT = 2 (contrato geométrico do tabuleiro)

**Decisão:** Cada tile ocupa 2 unidades no grid lógico (eixo x e eixo y).

**Implicações:**
- Posições válidas de tiles: múltiplos de 2 em x e y.
- Bloqueio superior: `abs(dx) < 2 AND abs(dy) < 2` (mesma lógica que Mahjong solitaire clássico).
- Bloqueio lateral: vizinha em `x ± 2` na mesma camada, com `abs(dy) < 2`.
- **O Ciclo 02 (Gerador) DEVE respeitar TILE_UNIT = 2 ao posicionar tiles.**
- **O Ciclo 04 (Renderização) DEVE usar TILE_UNIT como base do mapeamento lógico → pixels.**

**Motivo:** Permite que tiles se sobreponham parcialmente (meia unidade de cada lado)
como no Mahjong clássico, mantendo a regra de bloqueio geometricamente correta
sem precisar de coordenadas de ponto flutuante no domínio.

---

#### DT-02 — MatchReason como object de constantes (não enum)

**Decisão:** `MatchReason` foi implementado como `object` com constantes `String` ao invés de `enum`.

**Motivo:** O contrato técnico especifica `reason: String` em `MatchResult`.
Usar `object` com constantes permite extensão posterior sem breaking change na assinatura,
e mantém o conjunto fechado de valores sem impor conversão enum ↔ String no controller.

---

#### DT-03 — hasAvailableMoves usa agrupamento por pairKey (O(n))

**Decisão:** Em vez de comparar todos os pares O(n²), a função agrupa as tiles elegíveis
por `pairKey` e verifica se algum grupo tem `size >= 2`.

**Motivo:** Correto, mais eficiente e determinístico. O resultado é idêntico à busca
por par exaustiva. A complexidade cai para O(n) na prática.

---

#### DT-04 — DomainRules como fachada (entry point do Controller)

**Decisão:** Criado `DomainRules.kt` como objeto fachada que delega para todos os
módulos de regra especializados.

**Motivo:** O Controller (Ciclo 03) deve depender de um ponto de entrada único,
respeitando ARQ-05 (Baixo Acoplamento). Nenhum componente fora do pacote
`domain/rules` precisa conhecer `BlockingRules`, `EligibilityRules`, etc. diretamente.

---

#### DT-05 — BoardGenerationResult incluído no Ciclo 01

**Decisão:** `BoardGenerationResult` foi implementado junto com `InputEvent.kt`
neste ciclo, embora pertença ao escopo do Ciclo 02.

**Motivo:** É uma estrutura de dado puro sem lógica. Sua presença no Ciclo 01
não cria dependência para frente — apenas adianta o contrato de saída do gerador
para que o Ciclo 02 possa ser validado contra ele.
**Não abre o escopo do Ciclo 02 — apenas define a interface de contrato.**

---

**Problemas encontrados:** Nenhum.

---

### [PATCH GEOMÉTRICO] — Unificação de Contrato Espacial
- **O que foi feito:** Refatoração de `BlockingRules.kt` para consumir `GeometryContract.TILE_UNIT`.
- **Motivo:** Eliminar a duplicidade de constantes entre o Domínio (Ciclo 01) e a Apresentação (Ciclo 03).
- **Status:** Alinhamento inter-ciclos concluído sem alteração de comportamento.

---

**Próximo passo:** Abrir Ciclo 02 — Gerador Procedural.

---

## 3. Estado Final do Ciclo

| Campo                  | Valor                     |
|------------------------|---------------------------|
| Data de abertura       | Sessão 01                 |
| Data de fechamento     | Sessão 01                 |
| Status                 | ✅ CONCLUÍDO              |
| Checklist concluído    | sim — 25/25 itens         |
| Evidência registrada   | sim                       |

---

## 4. Contrato congelado — o que NÃO pode mudar sem revisão formal

Os seguintes contratos do Ciclo 01 ficam congelados e são dependências dos ciclos seguintes:

| Contrato                        | Dependente          |
|---------------------------------|---------------------|
| Assinatura de `Tile`            | Ciclo 02, 03, 04, 05 |
| Valores de `GameStatus`         | Ciclo 03, 04        |
| Assinatura de `GameState`       | Ciclo 03, 04        |
| Assinatura de `isEligible`      | Ciclo 02, 03, 05    |
| Assinatura de `evaluateMatch`   | Ciclo 03, 05        |
| `TILE_UNIT = 2` (DT-01)         | Ciclo 02, 04        |
| `MatchReason` constantes        | Ciclo 03, 05        |

---

*Registro de Execução — Ciclo 01 — VitahAcre — Método Caracol de Tolentino*
