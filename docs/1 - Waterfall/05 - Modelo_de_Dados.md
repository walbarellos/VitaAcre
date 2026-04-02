# 05 - Modelo de Dados

## 5.1 Objetivo do Documento
Este documento define o modelo de dados oficial do projeto VitahAcre na Waterfall V2. Seu propósito é estabelecer as estruturas centrais do domínio, seus atributos, suas relações e suas restrições de integridade, de modo que a implementação técnica, a arquitetura, as regras de negócio e os testes operem sobre uma base semântica única e coerente.

Este documento deve:
- formalizar as entidades nucleares do jogo;
- padronizar nomes e significados;
- reduzir ambiguidade entre documentos;
- sustentar a implementação das regras do domínio;
- sustentar o fluxo arquitetural da partida;
- servir de referência para testes e evolução incremental.

---

## 5.2 Princípios do Modelo de Dados

### MD-01 - Clareza Semântica
Cada estrutura deve possuir significado inequívoco dentro do domínio.

### MD-02 - Coerência com o Domínio
O modelo de dados deve refletir diretamente as regras do jogo e os requisitos do sistema.

### MD-03 - Suficiência Estrutural
As estruturas devem conter informação suficiente para permitir:
- validação das regras;
- atualização do estado;
- reconstrução visual da partida;
- execução de testes.

### MD-04 - Neutralidade Visual
As estruturas do domínio não devem depender de detalhes de aparência, animação ou efeitos gráficos.

### MD-05 - Compatibilidade com Testes
Os dados devem ser organizados de modo a permitir inspeção clara, comparação e validação automatizada.

---

## 5.3 Entidades Centrais do Domínio
O núcleo do modelo de dados do VitahAcre é composto, no mínimo, pelas seguintes estruturas:

- Tile
- GameState
- GameStatus
- BoardConfiguration
- InputEvent
- SelectionState
- MatchResult
- BoardGenerationResult

Essas estruturas constituem a base oficial do domínio nesta fase do projeto.

---

## 5.4 Entidade Tile

### 5.4.1 Definição
Tile representa uma peça individual do tabuleiro.

Cada Tile corresponde a uma unidade jogável ou avaliável do domínio.

### 5.4.2 Finalidade
A entidade Tile existe para representar:
- identidade lógica da peça;
- posição espacial no tabuleiro;
- camada;
- estado de remoção;
- informações mínimas necessárias para pareamento e bloqueio.

### 5.4.3 Campos Obrigatórios
A entidade Tile deve possuir, no mínimo, os seguintes campos:
- `id`
- `pairKey`
- `x`
- `y`
- `layer`
- `isRemoved`

### 5.4.4 Descrição dos Campos

#### id
Identificador único da instância da peça dentro da partida atual.

**Função:** distinguir uma peça específica de todas as demais.

#### pairKey
Identificador lógico de pareamento.

**Função:** determinar compatibilidade de match entre duas peças.

#### x
Coordenada horizontal lógica da peça no tabuleiro.

#### y
Coordenada vertical lógica da peça no tabuleiro.

#### layer
Camada lógica vertical da peça.

**Função:** permitir avaliação de bloqueio superior.

#### isRemoved
Indicador booleano de remoção da peça.

**Função:** informar se a peça ainda participa do fluxo jogável.

### 5.4.5 Regras de Integridade da Tile

#### MDT-01
`id` deve ser único dentro da partida.

#### MDT-02
`pairKey` deve ser coerente com a política de pareamento do tabuleiro.

#### MDT-03
A combinação `x + y + layer` deve ser única no tabuleiro.

#### MDT-04
`isRemoved = true` indica que a peça não pode mais ser tratada como ativa.

#### MDT-05
A entidade Tile não deve armazenar regra de negócio nem lógica visual.

### 5.4.6 Representação Estrutural de Referência
A forma estrutural de referência da Tile pode ser representada assim:

```kotlin
data class Tile(
    val id: Int,
    val pairKey: Int,
    val x: Int,
    val y: Int,
    val layer: Int,
    val isRemoved: Boolean = false
)
````

Esta representação é referencial. O nome exato dos tipos pode variar na implementação, desde que o significado estrutural seja preservado.

---

## 5.5 Entidade GameStatus

### 5.5.1 Definição

GameStatus representa o estado formal atual da partida.

### 5.5.2 Finalidade

A entidade GameStatus existe para:

* padronizar o fluxo da partida;
* orientar controller e renderização;
* permitir testes de transição;
* impedir estados implícitos e ambíguos.

### 5.5.3 Estados Oficiais

Os estados formais mínimos são:

* `INICIALIZANDO`
* `PRONTO`
* `SELECIONANDO_1`
* `SELECIONANDO_2`
* `PROCESSANDO_MATCH`
* `VITORIA`
* `SEM_JOGADAS`

### 5.5.4 Regras de Integridade do GameStatus

#### MDGS-01

O status da partida deve pertencer a um conjunto fechado de valores oficiais.

#### MDGS-02

Não é permitido estado textual livre não documentado.

#### MDGS-03

Toda transição de estado deve ser coerente com o fluxo do jogo.

### 5.5.5 Representação Estrutural de Referência

```kotlin
enum class GameStatus {
    INICIALIZANDO,
    PRONTO,
    SELECIONANDO_1,
    SELECIONANDO_2,
    PROCESSANDO_MATCH,
    VITORIA,
    SEM_JOGADAS
}
```

---

## 5.6 Entidade SelectionState

### 5.6.1 Definição

SelectionState representa a situação atual da seleção de peças na partida.

### 5.6.2 Finalidade

A entidade existe para formalizar:

* ausência de seleção;
* primeira peça selecionada;
* segunda peça em avaliação;
* coerência da seleção atual.

### 5.6.3 Campos Obrigatórios

SelectionState deve possuir, no mínimo:

* `firstSelectedTileId`
* `secondSelectedTileId`

Esses campos podem ser nulos conforme o estágio da seleção.

### 5.6.4 Regras de Integridade da Seleção

#### MDS-01

Não pode haver segunda seleção sem possibilidade lógica de primeira seleção.

#### MDS-02

A mesma peça não pode ocupar simultaneamente os dois campos.

#### MDS-03

Peça removida não pode permanecer como seleção ativa válida após processamento coerente do fluxo.

### 5.6.5 Representação Estrutural de Referência

```kotlin
data class SelectionState(
    val firstSelectedTileId: Int? = null,
    val secondSelectedTileId: Int? = null
)
```

---

## 5.7 Entidade GameState

### 5.7.1 Definição

GameState representa o estado global oficial da partida.

### 5.7.2 Finalidade

A entidade GameState existe para concentrar:

* o conjunto de peças da sessão atual;
* a seleção atual;
* o status da partida;
* a configuração do tabuleiro;
* eventuais metadados úteis à sessão.

### 5.7.3 Campos Obrigatórios

GameState deve possuir, no mínimo:

* `tiles`
* `selection`
* `status`
* `boardConfiguration`

### 5.7.4 Descrição dos Campos

#### tiles

Coleção das peças da partida atual.

#### selection

Estrutura que representa a seleção atual.

#### status

Estado formal da partida.

#### boardConfiguration

Configuração estrutural associada ao tabuleiro atual.

### 5.7.5 Regras de Integridade do GameState

#### MDG-01

O GameState deve ser suficiente para reconstruir a partida logicamente.

#### MDG-02

O GameState deve ser suficiente para orientar a renderização.

#### MDG-03

Toda peça referenciada na seleção deve existir no conjunto de peças ou ser nula.

#### MDG-04

Não pode haver divergência entre `status`, `selection` e realidade lógica da partida.

#### MDG-05

O GameState deve ser a fonte única da verdade da sessão.

### 5.7.6 Representação Estrutural de Referência

```kotlin
data class GameState(
    val tiles: List<Tile>,
    val selection: SelectionState,
    val status: GameStatus,
    val boardConfiguration: BoardConfiguration
)
```

---

## 5.8 Entidade BoardConfiguration

### 5.8.1 Definição

BoardConfiguration representa os parâmetros estruturais utilizados para gerar ou caracterizar o tabuleiro da partida.

### 5.8.2 Finalidade

A entidade existe para:

* formalizar a configuração do tabuleiro;
* permitir geração reproduzível quando necessário;
* registrar a estrutura-base da sessão atual.

### 5.8.3 Campos Obrigatórios

BoardConfiguration deve possuir, no mínimo:

* `columns`
* `rows`
* `layers`
* `totalTiles`
* `seed`

### 5.8.4 Descrição dos Campos

#### columns

Quantidade lógica de colunas do tabuleiro.

#### rows

Quantidade lógica de linhas do tabuleiro.

#### layers

Quantidade lógica máxima de camadas.

#### totalTiles

Quantidade total de peças prevista para o tabuleiro.

#### seed

Semente opcional para geração determinística reproduzível.

### 5.8.5 Regras de Integridade da Configuração

#### MDB-01

`totalTiles` deve ser compatível com a política de pareamento.

#### MDB-02

`layers` deve ser compatível com a estratégia de empilhamento do tabuleiro.

#### MDB-03

Os campos devem ser suficientes para permitir reconstrução ou rastreabilidade da geração.

### 5.8.6 Representação Estrutural de Referência

```kotlin
data class BoardConfiguration(
    val columns: Int,
    val rows: Int,
    val layers: Int,
    val totalTiles: Int,
    val seed: Long? = null
)
```

---

## 5.9 Entidade InputEvent

### 5.9.1 Definição

InputEvent representa uma entrada do usuário relevante para o fluxo da partida.

### 5.9.2 Finalidade

A entidade existe para traduzir a interação do usuário em dado processável pela arquitetura.

### 5.9.3 Campos Obrigatórios

InputEvent deve possuir, no mínimo:

* `x`
* `y`
* `type`

### 5.9.4 Descrição dos Campos

#### x

Posição horizontal do toque.

#### y

Posição vertical do toque.

#### type

Tipo de evento de entrada.

Exemplos possíveis:

* `TOUCH`
* `RESTART`

### 5.9.5 Representação Estrutural de Referência

```kotlin
data class InputEvent(
    val x: Float? = null,
    val y: Float? = null,
    val type: InputEventType
)
```

```kotlin
enum class InputEventType {
    TOUCH,
    RESTART
}
```

---

## 5.10 Entidade MatchResult

### 5.10.1 Definição

MatchResult representa o resultado formal da tentativa de pareamento entre duas peças.

### 5.10.2 Finalidade

A entidade existe para padronizar o retorno da avaliação de match.

### 5.10.3 Campos Obrigatórios

MatchResult deve possuir, no mínimo:

* `isValid`
* `firstTileId`
* `secondTileId`
* `reason`

### 5.10.4 Descrição dos Campos

#### isValid

Booleano que indica se o par foi aceito como válido.

#### firstTileId

Identificador da primeira peça avaliada.

#### secondTileId

Identificador da segunda peça avaliada.

#### reason

Motivo textual controlado do resultado.

Exemplos:

* `VALID_MATCH`
* `DIFFERENT_PAIR_KEY`
* `SAME_TILE`
* `TILE_NOT_ELIGIBLE`

### 5.10.5 Representação Estrutural de Referência

```kotlin
data class MatchResult(
    val isValid: Boolean,
    val firstTileId: Int?,
    val secondTileId: Int?,
    val reason: String
)
```

---

## 5.11 Entidade BoardGenerationResult

### 5.11.1 Definição

BoardGenerationResult representa o resultado da geração do tabuleiro.

### 5.11.2 Finalidade

A entidade existe para padronizar a saída do gerador procedural.

### 5.11.3 Campos Obrigatórios

BoardGenerationResult deve possuir, no mínimo:

* `tiles`
* `configuration`
* `isPlayable`
* `hasAtLeastOneInitialMove`

### 5.11.4 Representação Estrutural de Referência

```kotlin
data class BoardGenerationResult(
    val tiles: List<Tile>,
    val configuration: BoardConfiguration,
    val isPlayable: Boolean,
    val hasAtLeastOneInitialMove: Boolean
)
```

---

## 5.12 Relações Entre as Entidades

As relações estruturais principais do modelo são as seguintes:

* GameState contém múltiplas Tile;
* GameState contém um SelectionState;
* GameState contém um GameStatus;
* GameState contém uma BoardConfiguration;
* BoardGenerationResult produz Tile e BoardConfiguration;
* MatchResult referencia Tile por identificadores;
* InputEvent aciona fluxo que pode alterar GameState.

---

## 5.13 Diagrama Conceitual Simplificado

```text
BoardConfiguration
        ↓
BoardGenerationResult
        ↓
      GameState
   ┌─────┼─────┐
   ↓     ↓     ↓
Tiles Selection Status
```

---

## 5.14 Regras Gerais de Integridade do Modelo

### MDI-01

Toda estrutura de domínio deve ter significado formal claro.

### MDI-02

Nenhum campo do modelo deve existir apenas por conveniência visual.

### MDI-03

As entidades devem refletir os conceitos oficiais do domínio.

### MDI-04

As relações entre as estruturas devem ser coerentes com o fluxo arquitetural.

### MDI-05

O modelo não deve depender de detalhes de interface gráfica.

---

## 5.15 Critérios de Conformidade do Modelo de Dados

O modelo será considerado corretamente implementado quando:

* as entidades centrais existirem com significado equivalente ao definido aqui;
* a identidade e o pareamento das peças estiverem corretamente representados;
* o GameState for suficiente para representar a sessão;
* a seleção estiver formalizada de modo coerente;
* o status da partida estiver modelado por conjunto fechado;
* a configuração do tabuleiro estiver documentada no estado;
* os testes puderem inspecionar claramente o comportamento do domínio a partir dessas estruturas.

---

## 5.16 Relação com os Demais Documentos

Este documento:

* concretiza estruturalmente a Visão do produto;
* oferece suporte formal ao SRS;
* fornece base material para as Regras de Negócio;
* sustenta a Arquitetura do sistema;
* prepara a Implementação Técnica;
* serve de base para o Plano de Testes e para os Casos de Teste.

Nenhum documento posterior deve redefinir essas estruturas sem revisão explícita desta base.

---

## 5.17 Declaração Oficial do Modelo de Dados

O presente documento estabelece o modelo de dados oficial do VitahAcre na Waterfall V2.

As estruturas aqui definidas formam a base semântica do domínio do projeto e ficam congeladas como referência oficial, salvo revisão explícita em versão posterior da documentação.
