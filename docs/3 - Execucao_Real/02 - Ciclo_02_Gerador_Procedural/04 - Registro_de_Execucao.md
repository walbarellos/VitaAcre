# 04 - Registro de Execução — Ciclo 02 — Gerador Procedural
## Projeto: VitahAcre

---

## Registro

### [SESSÃO 02] — Implementação completa do Ciclo 02

**Arquivos produzidos:**

| Arquivo                      | Pacote              | Status |
|------------------------------|---------------------|--------|
| `BoardGenerator.kt`          | `data/generator`    | ✅     |
| `DefaultBoardGenerator.kt`   | `data/generator`    | ✅     |
| `GeneratorFactory.kt`        | `data/generator`    | ✅     |

**Total: 3 arquivos Kotlin — zero dependências Android.**

---

## Decisões Técnicas

### DT-06 — Retentativa com seed derivada

**Decisão:** Quando a seed é fixa e a tentativa 0 não produz tabuleiro com jogada inicial,
as tentativas seguintes usam `Random(seed + attemptIndex)` em vez de `Random.Default`.

**Motivo:** Preserva o determinismo. Se o usuário fornece seed X, tentativa 1 usa X+1,
tentativa 2 usa X+2, etc. O resultado é sempre o mesmo para a mesma seed e configuração.
Sem isso, retentativas com seed fixa gerariam o mesmo tabuleiro inválido infinitamente.

---

### DT-07 — Grid gerado por ordem layer-row-col (camadas base primeiro)

**Decisão:** As posições são geradas na ordem `layer → row → col`, não `col → row → layer`.

**Motivo:** Após o embaralhamento, a distribuição tende a concentrar tiles
nas camadas de índice menor (base). Isso é geometricamente coerente com
o Mahjong solitaire, onde a maioria das tiles está na camada base.
Não afeta a correção, apenas favorece tabuleiros visualmente densos na base.

---

### DT-08 — GeneratorFactory como ponte domain ↔ data

**Decisão:** `GeneratorFactory.createInitialGameState()` converte `BoardGenerationResult`
diretamente em `GameState`, sem passar pelo Controller (Ciclo 03).

**Motivo:** O Controller gerencia o fluxo de jogada, não a inicialização da partida.
A responsabilidade de converter "resultado de geração" em "estado inicial" pertence
à camada de dados, não ao controlador de UI.
O Controller receberá um `GameState` já em status `PRONTO`.

---

### DT-09 — validateStructuralIntegrity verifica pairKey = 2 (não ≥ 2)

**Decisão:** A validação exige que cada pairKey apareça **exatamente** 2 vezes, nunca mais.

**Motivo:** A versão base do projeto define pareamento como "exatamente um par por pairKey"
(conforme RN-13 versão base). Futuras variantes (4 tiles por tipo) exigirão revisão
documentada neste contrato, não improvisação.

---

## Estado Final do Ciclo

| Campo                  | Valor                     |
|------------------------|---------------------------|
| Status                 | ✅ CONCLUÍDO              |
| Checklist concluído    | sim — 26/26 itens         |
| Auditoria executada    | sim — GEN-01 a GEN-06     |
| Evidência registrada   | sim                       |

---

## Contratos congelados — dependências do Ciclo 03

| Contrato                                          | Dependente   |
|---------------------------------------------------|--------------|
| `BoardGenerator.generate()` assinatura            | Ciclo 03, 05 |
| `GeneratorFactory.createInitialGameState()`       | Ciclo 03     |
| `DefaultBoardGenerator.TILE_UNIT = 2`             | Ciclo 04     |
| Estratégia de seed derivada (DT-06)               | Ciclo 05     |
| `BoardGenerationResult.isPlayable` semântica      | Ciclo 03, 05 |

---

*Registro de Execução — Ciclo 02 — VitahAcre — Método Caracol de Tolentino*
