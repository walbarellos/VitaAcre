# 01 - Contrato Técnico do Ciclo 05 — Testes e Regressão
## Projeto: VitahAcre

---

## 1. Pré-requisito

Ciclos 01 a 04 concluídos, checklists aprovados, evidências registradas.

---

## 2. Stack de Testes

- Unitários e integração: JUnit 4 ou JUnit 5
- Nenhum teste desta suite pode exigir emulador ou device para rodar
- Testes de UI instrumentados são opcionais e não bloqueiam a regressão mínima

---

## 3. Suite de Regressão Mínima

A regressão mínima é o conjunto de testes que **deve passar sempre**, antes de qualquer avanço.

### Testes obrigatórios da regressão mínima

| ID | Escopo | Cenário |
|----|--------|---------|
| T-01 | `isEligible` | Peça sem bloqueio → elegível |
| T-02 | `isEligible` | Peça com bloqueio superior → inelegível |
| T-03 | `isEligible` | Peça com dois lados bloqueados → inelegível |
| T-04 | `isEligible` | Peça removida → inelegível |
| T-05 | `evaluateMatch` | Duas peças com mesmo `pairKey` e elegíveis → `VALID_MATCH` |
| T-06 | `evaluateMatch` | Duas peças com `pairKey` diferente → `DIFFERENT_PAIR_KEY` |
| T-07 | `evaluateMatch` | Mesma peça duas vezes → `SAME_TILE` |
| T-08 | `evaluateMatch` | Peça inelegível → `TILE_NOT_ELIGIBLE` |
| T-09 | `isVictory` | Todas as peças removidas → `true` |
| T-10 | `isVictory` | Ao menos uma peça ativa → `false` |
| T-11 | `hasAvailableMoves` | Tabuleiro com par elegível → `true` |
| T-12 | `hasAvailableMoves` | Nenhum par elegível disponível → `false` |
| T-13 | Gerador | Tabuleiro gerado: `totalTiles` par |
| T-14 | Gerador | Tabuleiro gerado: cada `pairKey` aparece 2 vezes |
| T-15 | Gerador | Seed fixo: dois generates idênticos |
| T-16 | Controller | Match válido → peças marcadas `isRemoved = true` |
| T-17 | Controller | Match inválido → estado inalterado |
| T-18 | Controller | Toque fora de Tile → estado inalterado |
| T-19 | Controller | Todas peças removidas → `status == VITORIA` |
| T-20 | Controller | Sem movimentos disponíveis → `status == SEM_JOGADAS` |

---

## 4. Contrato da Regressão

- A suite de regressão mínima (T-01 a T-20) deve passar com 100% de aprovação
- Falha em qualquer item da regressão **bloqueia** o avanço ao Ciclo 06
- A suite deve ser executável com `./gradlew test`

---

*Contrato Técnico — Ciclo 05 — VitahAcre*
