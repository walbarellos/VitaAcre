# 03 - Checklist de Conclusão — Ciclo 04 — Renderização Procedural
## Projeto: VitahAcre

---

## Instrução de Uso

Marque cada item apenas quando ele estiver **implementado, revisado e confirmado**.

Nenhum item pode ser marcado por antecipação.

O ciclo só está concluído quando **todos os itens** estiverem marcados.

---

## Bloco 1 — Interfaces e Implementações

- [x] Interface `BoardRenderer` definida em `presentation/canvas/BoardRenderer.kt`
- [x] Interface `TileRenderer` definida em `presentation/canvas/TileRenderer.kt`
- [ ] Interface `SelectionHighlightRenderer` definida em `presentation/canvas/SelectionHighlightRenderer.kt`
- [ ] Interface `StateOverlayRenderer` definida em `presentation/canvas/StateOverlayRenderer.kt`
- [x] `DefaultBoardRenderer` implementado
- [x] `DefaultTileRenderer` implementado
- [ ] `DefaultSelectionHighlightRenderer` implementado
- [ ] `DefaultStateOverlayRenderer` implementado
- [ ] `GameScreenStateAdapter` implementado em `presentation/screen/GameScreenStateAdapter.kt`

---

## Bloco 2 — Regras de Isolamento (RND)

- [x] RND-01: Zero lógica de negócio no renderer — nenhuma função de domínio chamada
- [x] RND-02: Zero importações de `domain/rules/` nos arquivos de render
- [x] RND-03: Zero sprites ou imagens externas — renderização 100% procedural
- [x] RND-04: Coordenadas derivadas de TILE_UNIT (múltiplos de 2)
- [ ] RND-05: Peças com `isRemoved = true` não são renderizadas

---

## Bloco 3 — Elementos Visuais

- [ ] Tabuleiro renderizado com grid Based em `BoardConfiguration`
- [ ] Peças renderizadas com cor derivada de `pairKey`
- [ ] Seleção destacada visualmente (borda, sombra ou cor alterada)
- [ ] Overlay de vitória implementado para `GameStatus.VITORIA`
- [ ] Overlay de sem jogadas implementado para `GameStatus.SEM_JOGADAS`

---

## Bloco 4 — Integração com GameScreen

- [ ] `GameScreen` utiliza `Canvas` do Compose
- [ ] `cellSize` calculado dinamicamente a partir das dimensões da tela
- [ ] `GameScreenStateAdapter` coordena as chamadas de render
- [ ] Recomposição ocorre corretamente após mudança de estado via StateFlow

---

## Bloco 5 — Validação Visual

- [ ] Tabuleiro visível no emulador
- [ ] Peças identificáveis por `pairKey` (cores distintas)
- [ ] Seleção visualmente destacada ao tocar
- [ ] Peça removida desaparece após match válido
- [ ] Overlay de vitória aparece quando `status == VITORIA`
- [ ] Overlay de sem jogadas aparece quando `status == SEM_JOGADAS`

---

## Bloco 6 — Conformidade Técnica

- [ ] Compilação: `./gradlew compileDebugKotlin` sem erro
- [ ] Zero dependências de `domain/rules/`; dependências de `domain/model` permitidas
- [ ] Arquitetura segue o contrato técnico integralmente

---

## Bloco 7 — Evidência

- [ ] Print do emulador com tabuleiro em funcionamento
- [ ] Evidência registrada em `07 - Evidencias_e_Validacoes/00 - Registro_de_Evidencias.md`

---

## Estado do Checklist

| Bloco | Itens | Concluídos |
|-------|-------|------------|
| Interfaces e Implementações | 9 | 4 |
| Regras de Isolamento (RND) | 5 | 4 |
| Elementos Visuais | 5 | 0 |
| Integração com GameScreen | 4 | 0 |
| Validação Visual | 6 | 0 |
| Conformidade Técnica | 3 | 0 |
| Evidência | 2 | 0 |
| **Total** | **34** | **8** |

---

## ✅ STATUS DO CICLO 04: EM PROGRESSO

**Ciclo 04 em execução.**

---

*Checklist de Conclusão — Ciclo 04 — VitahAcre — Método Caracol de Tolentino*
