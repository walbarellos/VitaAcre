# 00 - Visão do Ciclo 03 — Controller e Fluxo
## Projeto: VitahAcre

---

## 1. Objetivo do Ciclo

Dar vida à jogada. Transformar os blocos de domínio e geração em um fluxo real de interação.

---

## 2. Pré-requisito

Ciclos 01 e 02 concluídos e congelados.

---

## 3. Escopo Oficial

| Elemento            | Descrição                                              |
|---------------------|--------------------------------------------------------|
| `InputEvent`        | Evento de entrada normalizado (toque → coordenada)     |
| `GameController`    | Orquestrador do fluxo de jogada                        |
| Resolução de toque  | Mapeamento de coordenada de tela para posição de peça  |
| Primeira seleção    | Seleção da primeira peça elegível                      |
| Segunda seleção     | Seleção da segunda peça e tentativa de par             |
| Validação de par    | Verificação de compatibilidade entre as duas peças     |
| Remoção             | Remoção do par válido do tabuleiro                     |
| Transição de estado | Atualização do `GameState` após cada jogada            |
| Reinício            | Reset completo da partida                              |

---

## 4. Fora de Escopo neste Ciclo

- renderização
- testes automatizados formais
- build Android

---

## 5. Contrato Técnico do Ciclo

- O controller não pode chamar `Canvas` nem qualquer componente de UI diretamente
- O fluxo de jogada deve ser rastreável passo a passo
- Toda transição de estado deve passar pelo `GameState`
- O reinício deve restaurar o estado inicial sem resíduo

---

## 6. Checklist de Conclusão

- [ ] `InputEvent` implementado
- [ ] `GameController` implementado
- [ ] Resolução de toque para posição de peça implementada
- [ ] Fluxo de primeira seleção implementado
- [ ] Fluxo de segunda seleção implementado
- [ ] Validação de par integrada com regras do Ciclo 01
- [ ] Remoção de peças implementada
- [ ] Transição de `GameStatus` implementada (em curso → vitória / bloqueio)
- [ ] Reinício implementado
- [ ] Evidência registrada em `07 - Evidencias_e_Validacoes/`

---

## 7. Estado Atual do Ciclo

| Item                   | Estado              |
|------------------------|---------------------|
| Visão do ciclo criada  | ✅                  |
| Implementação iniciada | ⏳ aguarda Ciclo 02 |
| Checklist concluído    | ⏳ pendente         |
| Ciclo validado         | ⏳ pendente         |
| Ciclo congelado        | ⏳ pendente         |

---

*Ciclo 03 — VitahAcre — Método Caracol de Tolentino*
