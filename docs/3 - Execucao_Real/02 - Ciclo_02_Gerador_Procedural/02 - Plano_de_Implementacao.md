# 02 - Plano de Implementação — Ciclo 02 — Gerador Procedural
## Projeto: VitahAcre

---

## Ordem de Implementação

### Passo 1 — Implementar `BoardGenerationResult`
Arquivo: `domain/model/BoardGenerationResult.kt`

---

### Passo 2 — Definir interface `BoardGenerator`
Arquivo: `data/generator/BoardGenerator.kt`

---

### Passo 3 — Implementar `DefaultBoardGenerator`
Arquivo: `data/generator/DefaultBoardGenerator.kt`

Lógica interna:
1. Calcular quantidade de pares a partir de `configuration.totalTiles`
2. Gerar lista de `pairKey` com cada valor duplicado
3. Embaralhar usando `configuration.seed` (se presente) ou `Random.Default`
4. Distribuir peças por posições válidas (camada, linha, coluna)
5. Validar invariantes GEN-01 a GEN-04
6. Verificar `hasAtLeastOneInitialMove` usando `isEligible()` do Ciclo 01
7. Retornar `BoardGenerationResult`

---

### Passo 4 — Validação manual do gerador
- Instanciar com `BoardConfiguration` de tabuleiro pequeno (ex: 4 peças, 1 camada)
- Verificar output: todas as invariantes satisfeitas
- Testar com `seed` fixo: resultado deve ser idêntico em duas chamadas

---

*Plano de Implementação — Ciclo 02 — VitahAcre*
