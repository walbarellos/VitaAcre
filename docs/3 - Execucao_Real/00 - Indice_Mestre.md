# 00 - Índice Mestre
## Pasta: `3 - Execucao_Real`
## Projeto: VitahAcre

---

## 1. Objetivo desta Pasta

A pasta `3 - Execucao_Real` é a terceira e última camada documental do projeto VitahAcre antes da entrega real.

Ela existe depois de:

- `1 - Waterfall` → fundação conceitual, contratual e técnica do projeto
- `2 - Diagrama` → representação visual e estrutural de toda a arquitetura

Agora é a vez da **materialização real**.

Esta pasta é onde o projeto sai do documento e entra no código.

Mas não de forma caótica. Aqui o projeto cresce por **ciclos controlados**, conforme o **Método Caracol de Tolentino**.

---

## 2. Estrutura Interna

```text
3 - Execucao_Real/
├── 00 - Indice_Mestre.md                  ← este arquivo
├── 01 - Ciclo_01_Dominio_e_Estado/
├── 02 - Ciclo_02_Gerador_Procedural/
├── 03 - Ciclo_03_Controller_e_Fluxo/
├── 04 - Ciclo_04_Renderizacao_Procedural/
├── 05 - Ciclo_05_Testes_e_Regressao/
├── 06 - Ciclo_06_Build_Android_Real/
└── 07 - Evidencias_e_Validacoes/
```

---

## 3. Descrição de Cada Ciclo

### `01 - Ciclo_01_Dominio_e_Estado`

**Função:** construir o núcleo lógico e estrutural do sistema.

Escopo oficial:
- `Tile`
- `GameStatus`
- `SelectionState`
- `GameState`
- `BoardConfiguration`
- `MatchResult`
- funções puras de regra de negócio

Objetivo: ter uma base estável de domínio e estado antes de qualquer tela, toque ou geração de tabuleiro.

---

### `02 - Ciclo_02_Gerador_Procedural`

**Função:** gerar um tabuleiro válido e jogável a partir das entidades do Ciclo 01.

Escopo oficial:
- `BoardGenerationResult`
- gerador procedural de peças
- distribuição espacial no tabuleiro
- validação de integridade estrutural
- verificação de jogabilidade mínima

Objetivo: fazer nascer um tabuleiro funcional sem depender de assets externos.

---

### `03 - Ciclo_03_Controller_e_Fluxo` ✅ CONCLUÍDO

**Função:** transformar os blocos anteriores em fluxo real de jogada.

Escopo oficial:
- `InputEvent`
- resolução de toque
- seleção de primeira e segunda peça
- validação de par
- remoção de peças
- transição de estado do jogo
- reinício de partida

Objetivo: ter um ciclo completo de jogada funcional, do toque ao efeito.

---

### `04 - Ciclo_04_Renderizacao_Procedural`

**Função:** materializar visualmente a lógica já construída.

Escopo oficial:
- desenho do tabuleiro via Canvas
- desenho de peças por tipo e estado
- destaque visual de seleção ativa
- neutralização visual de peças removidas
- exibição de vitória e bloqueio
- legibilidade geral da sessão

Objetivo: garantir que o que o jogador vê reflita fielmente o estado real do jogo.

---

### `05 - Ciclo_05_Testes_e_Regressao`

**Função:** blindar o sistema contra retrocesso funcional.

Escopo oficial:
- testes unitários das regras puras
- testes de integração do fluxo principal
- testes funcionais de partida completa
- regressão mínima obrigatória

Objetivo: nenhum ciclo posterior deve quebrar o que já funciona.

---

### `06 - Ciclo_06_Build_Android_Real`

**Função:** sair do ambiente de desenvolvimento e entrar no produto real.

Escopo oficial:
- build debug
- build de teste interno
- empacotamento APK/AAB
- instalação e validação em dispositivo Android real
- checklist técnico de entrega

Objetivo: o jogo precisa rodar num aparelho real, não só no emulador.

---

### `07 - Evidencias_e_Validacoes`

**Função:** provar o que foi feito.

No Método Caracol, não basta implementar. É preciso **demonstrar**.

Conteúdo esperado:
- prints de telas em funcionamento
- logs de testes aprovados
- checkpoints de progresso por ciclo
- relatórios de avanço
- provas de build gerada
- registros de validação em aparelho real

---

## 4. Ordem Oficial de Execução

A ordem dos ciclos não é sugestão. É contrato.

```
1. Domínio e Estado        → base de tudo
2. Gerador Procedural      → cria sessão jogável
3. Controller e Fluxo      → dá vida à jogada
4. Renderização Procedural → torna visível
5. Testes e Regressão      → protege o que foi construído
6. Build Android Real      → entrega real
7. Evidências              → prova contínua de progresso
```

Nunca abrir o próximo ciclo sem validar o anterior.

---

## 5. Regra do Método Caracol dentro desta Pasta

Cada ciclo deve seguir esta sequência interna:

```
implementar → validar → integrar → congelar parcialmente → avançar
```

**Congelar parcialmente** significa: o ciclo anterior não deve ser reaberto para refatoração estrutural. Correções pontuais são permitidas. Reescrita completa, não.

Isso protege contra:
- retrabalho em loop
- crescimento sem prova
- implementação de múltiplos fronts simultâneos
- caos de escopo

---

## 6. Estado Atual da Pasta

| Item                               | Estado      |
|------------------------------------|-------------|
| Pasta criada                       | ✅          |
| `00 - Indice_Mestre.md` preenchido | ✅          |
| Ciclos criados (estrutura)         | ✅          |
| Ciclo 01 iniciado                  | ⏳ pendente |
| Ciclo 02 até 06                    | ⏳ pendente |
| Evidências registradas             | ⏳ pendente |

**Próximo passo oficial:** abrir e executar `01 - Ciclo_01_Dominio_e_Estado`.

---

## 7. Continuidade entre Sessões

Qualquer nova sessão de trabalho neste projeto **deve começar lendo este arquivo**.

Este documento é a porta de entrada da fase de execução.

Depois de ler este índice, a segunda leitura deve ser o arquivo de visão do ciclo atual em andamento.

Exemplo:
```
3 - Execucao_Real/00 - Indice_Mestre.md           ← leia primeiro
3 - Execucao_Real/01 - Ciclo_01_Dominio_e_Estado/ ← então abra aqui
```

---

## 8. Referências da Base Documental

Este índice pressupõe que as seguintes pastas estão fechadas e estáveis:

| Pasta           | Estado     |
|-----------------|------------|
| `1 - Waterfall` | ✅ fechada |
| `2 - Diagrama`  | ✅ fechada |

Documentos mais relevantes para a execução real:

- `1 - Waterfall/04 - Arquitetura.md`
- `1 - Waterfall/05 - Modelo_de_Dados.md`
- `1 - Waterfall/08 - Implementacao_Tecnica.md`
- `1 - Waterfall/09 - Plano_de_Testes.md`
- `2 - Diagrama/17 - Diagrama_de_Ciclos_Caracol.md`

---

*Documento de governança da pasta `3 - Execucao_Real` — VitahAcre — Método Caracol de Tolentino*
