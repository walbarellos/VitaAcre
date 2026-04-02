# 00 - Visão do Ciclo 02 — Gerador Procedural
## Projeto: VitahAcre

---

## 1. Pré-condição de Entrada

**O Ciclo 02 só pode ser aberto após o Ciclo 01 estar concluído e congelado.**

| Pré-condição                             | Status |
|------------------------------------------|--------|
| Ciclo 01 — Domínio e Estado concluído    | ✅     |
| `Tile` estável e congelado               | ✅     |
| `BoardConfiguration` estável e congelado | ✅     |
| `BoardGenerationResult` contrato definido | ✅    |
| `isEligible` implementado e congelado    | ✅     |
| `hasAvailableMoves` implementado e congelado | ✅  |

---

## 2. Objetivo do Ciclo

Gerar um tabuleiro válido, estruturalmente íntegro e jogável a partir
das entidades e regras estabelecidas no Ciclo 01.

Este ciclo **não produz tela**. Não produz toque. Não produz render.
Produz exclusivamente o **conjunto inicial de Tiles** que compõe uma partida.

---

## 3. Escopo Oficial

| Elemento                     | Descrição                                              |
|------------------------------|--------------------------------------------------------|
| `BoardGenerator`             | Componente que produz `BoardGenerationResult`          |
| Distribuição espacial        | Posicionamento de tiles respeitando TILE_UNIT = 2      |
| Distribuição de pairKeys     | Garantia de que todo pairKey aparece em número par     |
| Validação estrutural         | Unicidade de (x, y, layer) — MDT-03                   |
| Verificação de jogabilidade  | Ao menos um par elegível inicial — RN-26               |
| Estratégia de solucionabilidade | Garantia razoável de partida solucionável — RN-27   |

---

## 4. Contratos obrigatórios herdados do Ciclo 01

O gerador DEVE respeitar integralmente:

### C01 — TILE_UNIT = 2 (DT-01 do Ciclo 01)
Toda posição `x` e `y` atribuída a um tile deve ser múltiplo de `TILE_UNIT (2)`.

```
Posições válidas em x: 0, 2, 4, 6, ...
Posições válidas em y: 0, 2, 4, 6, ...
```

### C02 — Unicidade de (x, y, layer) — MDT-03
Dois tiles distintos não podem ocupar a mesma posição absoluta.

### C03 — totalTiles deve ser par — MDB-01 / RN-23
A geração deve produzir exatamente `boardConfiguration.totalTiles` tiles,
que obrigatoriamente é par.

### C04 — Cada pairKey deve aparecer exatamente 2 vezes (versão base)
Na versão base, todo pairKey possui exatamente um par.
Se `totalTiles = N`, então existem `N/2` pairKeys distintos,
cada um presente em exatamente 2 tiles.

### C05 — Pelo menos uma jogada válida inicial — RN-26
Ao término da geração, `hasAvailableMoves(result.tiles)` deve retornar `true`.
Se não retornar, o gerador deve tentar nova geração ou reorganizar o tabuleiro.

### C06 — Zero dependências Android
O gerador é executado na camada `data/generator/` mas não pode usar
`android.*`, `Canvas`, `Compose`, `View` ou qualquer API de UI.

---

## 5. Assinatura obrigatória de saída

```kotlin
// Entrada
data class BoardConfiguration(columns, rows, layers, totalTiles, seed)

// Saída obrigatória
data class BoardGenerationResult(
    val tiles: List<Tile>,             // todos os tiles gerados
    val configuration: BoardConfiguration,
    val isPlayable: Boolean,           // true = estrutura íntegra
    val hasAtLeastOneInitialMove: Boolean  // true = RN-26 satisfeito
)
```

---

## 6. Algoritmo de referência — Gerador Base

```
1. Calcular N = boardConfiguration.totalTiles
2. Criar lista de pairKeys = [0, 1, 2, ..., N/2 - 1] (cada um 2 vezes)
3. Embaralhar lista com boardConfiguration.seed (se presente)
4. Gerar posições válidas no grid (x múltiplo de TILE_UNIT, y múltiplo de TILE_UNIT)
   respeitando layers, columns, rows
5. Associar cada pairKey a uma posição (sem colisão de posição absoluta)
6. Construir lista de Tile com ids sequenciais (0, 1, 2, ...)
7. Verificar isPlayable (unicidade de posição)
8. Verificar hasAtLeastOneInitialMove usando DomainRules.hasAvailableMoves
9. Se (8) = false → tentar reorganizar ou nova seed
10. Retornar BoardGenerationResult
```

---

## 7. Fora de escopo neste ciclo

- Input do usuário
- Renderização
- Controller de jogada
- Testes automatizados formais (Ciclo 05)
- Build Android (Ciclo 06)

---

## 8. Estado atual do ciclo

| Item                         | Estado       |
|------------------------------|--------------|
| Pré-condições satisfeitas    | ✅           |
| Visão do ciclo criada        | ✅           |
| Implementação iniciada       | ⏳ Pendente  |
| Checklist concluído          | ⏳ Pendente  |
| Ciclo validado               | ⏳ Pendente  |
| Ciclo congelado              | ⏳ Pendente  |

---

*Ciclo 02 — VitahAcre — Método Caracol de Tolentino*
