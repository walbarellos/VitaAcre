# 00 - Visão do Ciclo 04 — Renderização Procedural
## Projeto: VitahAcre

---

## 1. Pré-condição de Entrada

O Ciclo 04 herda uma base técnica estável e purificada.

| Pré-condição | Status |
|--------------|--------|
| Ciclo 03 — Controller e Fluxo concluído | ✅ |
| `GeometryContract.TILE_UNIT` disponível | ✅ |
| `GameState` como fonte única da verdade | ✅ |
| `TouchMapper` operacional | ✅ |

---

## 2. Objetivo do Ciclo

Materializar a camada visual do jogo de forma **fiel e passiva**.
O Renderer deve ler o estado lógico e convertê-lo em representação gráfica procedural no Canvas, sem nunca tomar decisões de negócio.

---

## 3. Escopo Oficial

| Elemento | Descrição |
|----------|-----------|
| `BoardRenderer` | Desenho da malha do tabuleiro baseada em TILE_UNIT |
| `TileRenderer` | Desenho individual de peças (Cor, Borda, Identificador) |
| Leitura de Camada | Representação de profundidade visual |
| Destaque de Seleção | Feedback visual para a peça selecionada no estado |
| Neutralização | Peças removidas não são desenhadas (ou espaço vazio) |
| Feedback de Finais | Renderização de Vitoria / Sem Jogadas |

---

## 4. O que este ciclo NÃO faz (Gaurdrails)

- Não valida elegibilidade (responsabilidade do Domínio)
- Não avalia match (responsabilidade do Domínio)
- Não muta o `GameState` (responsabilidade do Controller)
- Não importa assets externos (Renderização Procedural Pura)

---

## 5. Integridade do Método Caracol

A regra de ouro para este ciclo é: **Render lê, não decide.**
Qualquer tentativa de "corrigir" uma regra de jogo dentro do código visual é uma violação arquitetural grave.

---

## 6. Estado Atual do Ciclo

| Item | Estado |
|------|--------|
| Visão do ciclo criada | ✅ |
| Contrato Técnico definido | ✅ |
| Implementação iniciada | 🔄 em andamento |
| Checklist concluído | ⏳ Pendente |
| Ciclo congelado | ⏳ Pendente |

---

*Ciclo 04 — VitahAcre — Método Caracol de Tolentino*
