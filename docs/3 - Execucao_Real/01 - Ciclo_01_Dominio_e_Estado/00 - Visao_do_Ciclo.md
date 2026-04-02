# 00 - Visão do Ciclo 01 — Domínio e Estado
## Projeto: VitahAcre

---

## 1. Objetivo do Ciclo

Construir o núcleo lógico e estrutural do sistema.

Este ciclo não produz tela. Não produz toque. Não produz render.

Ele produz a **verdade central do jogo**: as entidades, os estados e as regras puras que tudo mais vai usar.

---

## 2. Escopo Oficial

| Entidade / Elemento     | Descrição                                           |
|-------------------------|-----------------------------------------------------|
| `Tile`                  | Representa uma peça do tabuleiro                    |
| `TileType`              | Enum dos tipos de peça possíveis                    |
| `TilePosition`          | Posição da peça no grid (camada, linha, coluna)     |
| `TileState`             | Estado da peça: disponível, selecionada, removida   |
| `GameStatus`            | Estado da partida: em curso, vitória, bloqueio      |
| `SelectionState`        | Nenhuma, primeira peça selecionada, par formado     |
| `GameState`             | Estado global da partida (tabuleiro + status)       |
| `BoardConfiguration`    | Parâmetros de configuração do tabuleiro             |
| `MatchResult`           | Resultado de tentativa de par: válido ou inválido   |
| Regras puras            | Funções de elegibilidade, pareamento e bloqueio     |

---

## 3. Fora de Escopo neste Ciclo

- geração procedural do tabuleiro
- input do usuário
- renderização
- testes automatizados formais
- build Android

---

## 4. Contrato Técnico do Ciclo

- Todas as entidades devem ser **data classes** ou **sealed classes** em Kotlin
- Nenhuma entidade deste ciclo pode depender de `Context`, `Canvas` ou qualquer componente Android
- As regras puras devem ser funções sem efeito colateral
- O estado deve ser **imutável por padrão**, com cópias explícitas para mutação
- Aderência estrita ao modelo de dados definido em `1 - Waterfall/05 - Modelo_de_Dados.md`

---

## 5. Checklist de Conclusão

- [ ] `Tile` implementado e revisado
- [ ] `TileType` completo com todos os tipos previstos
- [ ] `TilePosition` implementado
- [ ] `TileState` implementado
- [ ] `GameStatus` implementado
- [ ] `SelectionState` implementado
- [ ] `GameState` implementado
- [ ] `BoardConfiguration` implementado
- [ ] `MatchResult` implementado
- [ ] Regras puras de elegibilidade implementadas
- [ ] Regras puras de pareamento implementadas
- [ ] Regras puras de detecção de bloqueio implementadas
- [ ] Revisão de aderência ao `04 - Arquitetura.md` feita
- [ ] Revisão de aderência ao `05 - Modelo_de_Dados.md` feita
- [ ] Evidência registrada em `07 - Evidencias_e_Validacoes/`

---

## 6. Estado Atual do Ciclo

| Item                   | Estado      |
|------------------------|-------------|
| Visão do ciclo criada  | ✅          |
| Implementação iniciada | ⏳ pendente |
| Checklist concluído    | ⏳ pendente |
| Ciclo validado         | ⏳ pendente |
| Ciclo congelado        | ⏳ pendente |

---

*Ciclo 01 — VitahAcre — Método Caracol de Tolentino*
