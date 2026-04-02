# 07 - Diagrama de Sequencia do Reinicio

## 7.1 Objetivo
Este diagrama apresenta a sequência oficial do reinício de partida no projeto VitahAcre, mostrando a ordem temporal das interações entre os principais componentes do sistema quando o jogador solicita uma nova sessão.

Ele serve para:
- formalizar o fluxo de reinício;
- impedir reinícios mal implementados;
- garantir descarte correto da sessão anterior;
- orientar controller, generator e state manager;
- apoiar testes de integração e testes funcionais.

---

## 7.2 Leitura do Diagrama
O reinício no VitahAcre não é continuação da sessão anterior.

Ele deve representar:
- captura do comando de reinício;
- descarte lógico da sessão atual;
- geração de novo tabuleiro;
- construção de novo estado inicial;
- retorno do jogo ao estado `PRONTO`;
- renderização limpa da nova partida.

O fluxo abaixo mostra a ordem oficial dessa operação.

---

## 7.3 Diagrama Mermaid

```mermaid
sequenceDiagram
    actor J as Jogador
    participant I as Input Layer
    participant C as Controller
    participant U as Use Case
    participant G as Generator
    participant S as State Manager
    participant GS as GameState
    participant R as Renderer / Canvas

    J->>I: aciona reinicio
    I->>C: envia InputEvent RESTART
    C->>U: processa reinicio
    U->>C: solicita nova sessao
    C->>S: encerra sessao atual
    S->>GS: descarta selecao e status anteriores
    C->>G: solicita novo tabuleiro
    G-->>C: BoardGenerationResult
    C->>S: cria novo estado inicial
    S->>GS: grava novas pecas
    S->>GS: define status PRONTO
    S->>R: renderiza nova sessao
    R-->>J: exibe novo tabuleiro
````

---

## 7.4 Participantes do Fluxo

### Jogador

Origina o reinício da sessão por meio de comando explícito na interface.

### Input Layer

Captura o comando de reinício e o transforma em evento interno.

### Controller

Coordena toda a operação de reinício.

### Use Case

Organiza a operação de nova sessão em nível mais alto.

### Generator

Constrói um novo tabuleiro válido.

### State Manager

Cria o novo estado formal da sessão.

### GameState

Passa a representar a nova partida.

### Renderer / Canvas

Apresenta visualmente o novo tabuleiro ao jogador.

---

## 7.5 Interpretação do Fluxo

### Etapa 1 - Comando do Jogador

O reinício começa quando o jogador aciona o comando correspondente na interface.

### Etapa 2 - Captura e Interpretação

A camada de input transforma a ação em um `InputEvent` do tipo `RESTART`.

### Etapa 3 - Encerramento da Sessão Atual

O controller e o state manager devem encerrar logicamente a sessão anterior.

Isso significa:

* não reutilizar seleção antiga;
* não reutilizar status terminal antigo;
* não reutilizar remoções da sessão anterior.

### Etapa 4 - Geração de Novo Tabuleiro

O controller solicita ao generator uma nova configuração jogável.

### Etapa 5 - Construção do Novo Estado

O state manager recebe o resultado da geração e constrói um novo `GameState`.

### Etapa 6 - Retorno ao Estado PRONTO

A sessão volta ao ponto de partida jogável.

### Etapa 7 - Nova Renderização

O renderer exibe a nova sessão ao jogador.

---

## 7.6 Garantias Obrigatórias do Reinício

O fluxo de reinício deve garantir:

### RR-01

A sessão antiga não persiste logicamente.

### RR-02

A nova sessão possui estado próprio e independente.

### RR-03

A seleção anterior não é reaproveitada.

### RR-04

As peças removidas da sessão anterior não reaparecem por mutação parcial; a nova sessão deve nascer de novo conjunto válido.

### RR-05

O status final anterior não pode contaminar a nova sessão.

### RR-06

A interface deve mostrar o novo tabuleiro, e não uma versão parcialmente reciclada da sessão anterior.

---

## 7.7 Restrições Visíveis no Diagrama

Este diagrama implica as seguintes restrições:

### RSR-01

O reinício não é simples limpeza visual.

### RSR-02

O renderer não pode “inventar” nova sessão por conta própria.

### RSR-03

O generator deve participar do reinício.

### RSR-04

O state manager deve reconstruir a sessão formal.

### RSR-05

O controller continua sendo o coordenador da operação.

---

## 7.8 Casos que o Diagrama Protege

Este fluxo ajuda a prevenir:

* reaproveitamento indevido de seleção;
* reaproveitamento indevido de status terminal;
* contaminação da nova sessão por estado anterior;
* reinício apenas aparente;
* renderização inconsistente após reinício.

---

## 7.9 Papel Estratégico do Diagrama

Este é um diagrama muito importante porque o reinício é uma operação que costuma gerar bugs silenciosos em jogos simples.

Ele ajuda a:

* definir a fronteira entre sessão antiga e nova sessão;
* organizar a responsabilidade entre controller, generator e state manager;
* apoiar testes de reinício;
* garantir previsibilidade na retomada da experiência.

---

## 7.10 Compatibilidade com os Demais Documentos

Este diagrama concretiza visualmente o conteúdo dos seguintes documentos:

* `02 - Requisitos_SRS.md`
* `03 - Regras_de_Negocio.md`
* `04 - Arquitetura.md`
* `05 - Modelo_de_Dados.md`
* `08 - Implementacao_Tecnica.md`
* `09 - Plano_de_Testes.md`
* `10 - Casos_de_Teste.md`
* `11 - Build_Deploy.md`

Ele deve permanecer coerente com todos eles.

---

## 7.11 Declaração Oficial

Este documento estabelece o Diagrama de Sequência do Reinício do projeto VitahAcre e deve ser lido como a representação temporal oficial da operação de reinício da sessão nesta fase do projeto.
