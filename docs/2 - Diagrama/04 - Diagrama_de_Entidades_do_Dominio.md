# 04 - Diagrama de Entidades do Dominio

## 4.1 Objetivo
Este diagrama apresenta as entidades centrais do domínio do projeto VitahAcre e suas relações estruturais.

Ele serve para:
- materializar visualmente o modelo de dados;
- consolidar as entidades oficiais do jogo;
- mostrar como os dados do domínio se conectam;
- preparar implementação, testes e manutenção;
- reduzir inconsistências entre documentação e código.

---

## 4.2 Leitura do Diagrama
O domínio do VitahAcre gira em torno de uma sessão de jogo formalizada por `GameState`.

O `GameState` contém:
- as peças da partida;
- a seleção atual;
- o status da sessão;
- a configuração do tabuleiro.

Além disso, existem estruturas auxiliares que representam:
- entrada do usuário;
- resultado de match;
- resultado da geração procedural.

O diagrama abaixo é conceitual e estrutural.  
Ele não representa fluxo temporal, apenas relação semântica entre entidades.

---

## 4.3 Diagrama Mermaid

```mermaid
classDiagram
    class Tile {
        +Int id
        +Int pairKey
        +Int x
        +Int y
        +Int layer
        +Boolean isRemoved
    }

    class GameStatus {
        <<enum>>
        INICIALIZANDO
        PRONTO
        SELECIONANDO_1
        SELECIONANDO_2
        PROCESSANDO_MATCH
        VITORIA
        SEM_JOGADAS
    }

    class SelectionState {
        +Int? firstSelectedTileId
        +Int? secondSelectedTileId
    }

    class BoardConfiguration {
        +Int columns
        +Int rows
        +Int layers
        +Int totalTiles
        +Long? seed
    }

    class GameState {
        +List~Tile~ tiles
        +SelectionState selection
        +GameStatus status
        +BoardConfiguration boardConfiguration
    }

    class InputEventType {
        <<enum>>
        TOUCH
        RESTART
    }

    class InputEvent {
        +Float? x
        +Float? y
        +InputEventType type
    }

    class MatchResult {
        +Boolean isValid
        +Int? firstTileId
        +Int? secondTileId
        +String reason
    }

    class BoardGenerationResult {
        +List~Tile~ tiles
        +BoardConfiguration configuration
        +Boolean isPlayable
        +Boolean hasAtLeastOneInitialMove
    }

    GameState *-- Tile : contem
    GameState *-- SelectionState : contem
    GameState *-- GameStatus : usa
    GameState *-- BoardConfiguration : contem

    InputEvent *-- InputEventType : usa

    BoardGenerationResult *-- Tile : produz
    BoardGenerationResult *-- BoardConfiguration : usa

    MatchResult --> Tile : referencia por id
    SelectionState --> Tile : referencia por id
````

---

## 4.4 Interpretação das Entidades

### Tile

Representa uma peça individual do tabuleiro.

É a menor unidade jogável do domínio.

Carrega:

* identidade única;
* chave lógica de pareamento;
* posição espacial;
* camada;
* estado de remoção.

---

### GameStatus

Representa o estado formal atual da sessão.

É um enum fechado que impede estados textuais livres e ambíguos.

---

### SelectionState

Representa a situação atual da seleção do jogador.

Mantém:

* primeira peça selecionada;
* segunda peça selecionada, quando existir.

A seleção referencia peças por identificador.

---

### BoardConfiguration

Representa a configuração estrutural do tabuleiro.

Formaliza:

* colunas;
* linhas;
* camadas;
* quantidade total de peças;
* seed opcional.

---

### GameState

É a entidade central do domínio.

Representa a sessão como um todo e concentra:

* peças;
* seleção;
* status;
* configuração.

É a fonte única da verdade da partida.

---

### InputEventType

Enum que representa o tipo da entrada do usuário.

Na base do projeto:

* `TOUCH`
* `RESTART`

---

### InputEvent

Representa um evento de entrada processável pelo sistema.

Carrega:

* coordenadas, quando aplicável;
* tipo do evento.

---

### MatchResult

Representa o resultado formal de uma tentativa de pareamento entre duas peças.

Serve para informar:

* se houve match válido;
* quais peças foram avaliadas;
* qual foi a razão do resultado.

---

### BoardGenerationResult

Representa a saída formal do gerador procedural.

Contém:

* peças geradas;
* configuração utilizada;
* indicação de jogabilidade;
* indicação de jogada inicial possível.

---

## 4.5 Relações Estruturais Principais

### Relação 1

`GameState` contém múltiplas `Tile`.

### Relação 2

`GameState` contém um `SelectionState`.

### Relação 3

`GameState` utiliza um `GameStatus`.

### Relação 4

`GameState` contém uma `BoardConfiguration`.

### Relação 5

`InputEvent` utiliza `InputEventType`.

### Relação 6

`BoardGenerationResult` produz `Tile` e usa `BoardConfiguration`.

### Relação 7

`SelectionState` e `MatchResult` referenciam `Tile` por identificador, e não por posse estrutural completa.

---

## 4.6 Papel Estratégico do Diagrama

Este diagrama é importante porque:

* consolida a linguagem do domínio;
* evita divergência entre documentos e código;
* orienta a modelagem real em Kotlin;
* ajuda na implementação de testes;
* reduz improvisação semântica durante a construção do sistema.

Ele é um dos diagramas mais importantes de toda a base do projeto.

---

## 4.7 Restrições Semânticas Visíveis no Diagrama

Este diagrama implica as seguintes restrições:

### RED-01

`GameState` é a entidade central da sessão.

### RED-02

`Tile` não carrega responsabilidade visual.

### RED-03

`GameStatus` deve permanecer enum fechado.

### RED-04

`SelectionState` não deve carregar lógica de domínio embutida.

### RED-05

`BoardGenerationResult` pertence ao ciclo de geração, não à jogabilidade corrente.

### RED-06

`InputEvent` representa entrada, não decisão de regra.

---

## 4.8 Compatibilidade com os Demais Documentos

Este diagrama concretiza visualmente o conteúdo dos seguintes documentos:

* `05 - Modelo_de_Dados.md`
* `03 - Regras_de_Negocio.md`
* `04 - Arquitetura.md`
* `08 - Implementacao_Tecnica.md`

Ele deve permanecer coerente com todos eles.

---

## 4.9 Declaração Oficial

Este documento estabelece o Diagrama de Entidades do Domínio do projeto VitahAcre e deve ser lido como a representação estrutural oficial das entidades centrais do sistema nesta fase do projeto.
