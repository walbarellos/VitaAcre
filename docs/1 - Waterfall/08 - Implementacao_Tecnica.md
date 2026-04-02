# 08 - Implementacao Tecnica

## 8.1 Objetivo do Documento
Este documento define a Implementação Técnica oficial do projeto VitahAcre na Waterfall V2. Seu propósito é transformar a base documental já estabelecida em diretrizes concretas de construção do software, especificando linguagem, organização do projeto, módulos, contratos técnicos, responsabilidades de código e fluxo operacional mínimo esperado.

Este documento deve:
- traduzir visão, requisitos, regras, arquitetura, modelo de dados, GDD e UI/UX em estruturas implementáveis;
- servir como ponte entre documentação e código;
- orientar a construção incremental em estilo LEGO;
- reduzir improvisação técnica;
- permitir evolução compatível com o método caracol.

---

## 8.2 Stack Técnica Oficial
A stack técnica oficial do VitahAcre, nesta fase do projeto, é a seguinte:

- **Plataforma:** Android
- **Linguagem principal:** Kotlin
- **UI framework:** Jetpack Compose
- **Renderização principal:** Canvas procedural
- **Build system:** Gradle
- **Testes unitários:** JUnit
- **Testes de integração:** framework compatível com Android/Kotlin
- **Controle de versão:** Git

Esta definição é a base oficial da implementação, salvo revisão explícita posterior.

---

## 8.3 Diretrizes Técnicas Fundamentais

### IT-01 - Regra separada da interface
A lógica de domínio não pode depender da camada visual.

### IT-02 - Estado centralizado
A verdade da partida deve estar centralizada em uma estrutura formal de estado.

### IT-03 - Renderização subordinada ao estado
A UI deve refletir o estado e não criar lógica paralela.

### IT-04 - Modularidade real
Os módulos devem poder evoluir por encaixe progressivo.

### IT-05 - Testabilidade desde o início
As estruturas e funções principais devem nascer preparadas para teste.

### IT-06 - Determinismo operacional
As funções de domínio devem produzir saídas previsíveis para o mesmo estado e mesma entrada.

---

## 8.4 Organização Técnica Recomendada do Projeto
A organização técnica recomendada para a implementação é a seguinte:

```text
VitahAcre/
├── app/
├── domain/
│   ├── model/
│   ├── rules/
│   ├── state/
│   └── usecase/
├── data/
│   ├── generator/
│   └── repository/
├── presentation/
│   ├── input/
│   ├── controller/
│   ├── screen/
│   ├── component/
│   └── canvas/
├── util/
└── tests/
````

Esta estrutura deve ser interpretada como base técnica oficial do projeto.

---

## 8.5 Papel Técnico de Cada Diretório

### 8.5.1 app

Contém:

* ponto de entrada da aplicação;
* configuração principal;
* integração com ciclo de vida Android;
* inicialização da navegação ou tela principal, quando aplicável.

### 8.5.2 domain/model

Contém:

* entidades do domínio;
* enums;
* contratos de dados;
* estruturas nucleares como Tile, GameState e BoardConfiguration.

### 8.5.3 domain/rules

Contém:

* funções puras do domínio;
* validação de elegibilidade;
* validação de match;
* verificação de vitória;
* verificação de ausência de jogadas.

### 8.5.4 domain/state

Contém:

* contratos do estado;
* transições formais;
* estruturas auxiliares relacionadas ao fluxo da partida.

### 8.5.5 domain/usecase

Contém:

* fluxos de domínio reutilizáveis;
* operações de maior nível;
* composição controlada entre regras e estado.

### 8.5.6 data/generator

Contém:

* geração procedural do tabuleiro;
* construção da configuração inicial;
* aplicação de seed quando houver;
* validação estrutural inicial da jogabilidade.

### 8.5.7 data/repository

Reservado para:

* persistências futuras;
* estatísticas;
* preferências de usuário;
* dados auxiliares de longo prazo, se o escopo evoluir.

### 8.5.8 presentation/input

Contém:

* tradução de toques em eventos internos;
* interpretação básica da interação do usuário.

### 8.5.9 presentation/controller

Contém:

* coordenação do fluxo da jogada;
* atualização controlada do estado;
* orquestração entre input, regras, estado e renderização.

### 8.5.10 presentation/screen

Contém:

* telas principais;
* composição visual geral;
* integração entre estado observado e componentes.

### 8.5.11 presentation/component

Contém:

* elementos visuais reutilizáveis;
* botões;
* overlays;
* indicadores;
* mensagens auxiliares.

### 8.5.12 presentation/canvas

Contém:

* desenho procedural do tabuleiro;
* desenho procedural das peças;
* destaques de seleção;
* transições visuais leves;
* renderização baseada no estado.

### 8.5.13 util

Contém:

* utilitários geométricos;
* utilitários matemáticos;
* auxiliares de seed;
* auxiliares gerais sem semântica central de domínio.

### 8.5.14 tests

Contém:

* testes unitários;
* testes de integração;
* testes funcionais;
* testes de performance.

---

## 8.6 Entidades Técnicas Mínimas

A implementação técnica mínima deve materializar as seguintes entidades:

* Tile
* GameStatus
* SelectionState
* GameState
* BoardConfiguration
* InputEvent
* MatchResult
* BoardGenerationResult

Essas entidades devem ser coerentes com o documento de Modelo de Dados.

---

## 8.7 Contrato Técnico da Entidade Tile

### Campos mínimos

* `id`
* `pairKey`
* `x`
* `y`
* `layer`
* `isRemoved`

### Responsabilidades

* representar uma peça do tabuleiro;
* ser identificável individualmente;
* participar de validações de bloqueio, elegibilidade e match.

### Restrições

* não pode conter lógica visual;
* não pode conter regra de negócio embutida;
* deve ser facilmente comparável e inspecionável em testes.

### Referência estrutural

```kotlin id="hbxrtj"
data class Tile(
    val id: Int,
    val pairKey: Int,
    val x: Int,
    val y: Int,
    val layer: Int,
    val isRemoved: Boolean = false
)
```

---

## 8.8 Contrato Técnico do Estado

### Estruturas mínimas de estado

* GameStatus
* SelectionState
* GameState

### Responsabilidades do estado

* representar a sessão atual;
* manter a lista de peças;
* manter a seleção atual;
* manter o status formal da partida;
* fornecer base suficiente para a renderização.

### Referência estrutural

```kotlin id="6gxyg1"
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

```kotlin id="gsy675"
data class SelectionState(
    val firstSelectedTileId: Int? = null,
    val secondSelectedTileId: Int? = null
)
```

```kotlin id="31fh6x"
data class GameState(
    val tiles: List<Tile>,
    val selection: SelectionState,
    val status: GameStatus,
    val boardConfiguration: BoardConfiguration
)
```

---

## 8.9 Contrato Técnico de Entrada

A entrada mínima do sistema deve ser modelada de forma explícita.

### Estrutura mínima

* tipo de evento;
* coordenadas, quando aplicável.

### Referência estrutural

```kotlin id="jimgbs"
enum class InputEventType {
    TOUCH,
    RESTART
}
```

```kotlin id="9q7l6q"
data class InputEvent(
    val x: Float? = null,
    val y: Float? = null,
    val type: InputEventType
)
```

---

## 8.10 Contrato Técnico da Configuração do Tabuleiro

A configuração do tabuleiro deve ser modelada explicitamente.

### Campos mínimos

* columns
* rows
* layers
* totalTiles
* seed

### Referência estrutural

```kotlin id="wbdx1z"
data class BoardConfiguration(
    val columns: Int,
    val rows: Int,
    val layers: Int,
    val totalTiles: Int,
    val seed: Long? = null
)
```

---

## 8.11 Funções Técnicas Fundamentais

O núcleo técnico do projeto deve implementar, no mínimo, funções equivalentes às seguintes:

* verificar se uma peça está livre;
* verificar se uma peça pode ser selecionada;
* localizar a peça tocada;
* registrar primeira seleção;
* registrar segunda seleção;
* validar match;
* remover peças;
* verificar vitória;
* verificar ausência de jogadas;
* gerar tabuleiro;
* reiniciar partida.

---

## 8.12 Assinaturas de Referência

As assinaturas abaixo são referenciais. O nome exato pode variar, mas a responsabilidade funcional deve ser preservada.

```kotlin id="kh6mxs"
fun isTileFree(tile: Tile, state: GameState): Boolean
```

```kotlin id="6idw4i"
fun canSelectTile(tile: Tile, state: GameState): Boolean
```

```kotlin id="d6jp0m"
fun resolveTouchedTile(x: Float, y: Float, state: GameState): Tile?
```

```kotlin id="rpewuy"
fun selectFirstTile(tile: Tile, state: GameState): GameState
```

```kotlin id="8r4dz0"
fun selectSecondTile(tile: Tile, state: GameState): GameState
```

```kotlin id="wjk0wq"
fun validateMatch(first: Tile, second: Tile, state: GameState): MatchResult
```

```kotlin id="rc6ec4"
fun removeMatchedTiles(first: Tile, second: Tile, state: GameState): GameState
```

```kotlin id="q23rda"
fun hasVictory(state: GameState): Boolean
```

```kotlin id="hlgmyf"
fun hasAvailableMoves(state: GameState): Boolean
```

```kotlin id="cmakyy"
fun generateBoard(config: BoardConfiguration): BoardGenerationResult
```

```kotlin id="5jo4n4"
fun restartGame(config: BoardConfiguration): GameState
```

---

## 8.13 Fluxo Técnico da Jogada

O fluxo técnico mínimo de uma jogada deve obedecer à seguinte ordem:

1. receber InputEvent;
2. interpretar o tipo do evento;
3. localizar a peça alvo, quando aplicável;
4. validar elegibilidade;
5. atualizar seleção;
6. validar match quando existirem duas seleções;
7. remover peças quando o match for válido;
8. atualizar o GameState;
9. verificar vitória;
10. verificar ausência de jogadas;
11. refletir o novo estado na renderização.

---

## 8.14 Fluxo Técnico do Reinício

O fluxo técnico mínimo do reinício deve obedecer à seguinte ordem:

1. receber evento de reinício;
2. construir ou recuperar BoardConfiguration;
3. gerar novo tabuleiro;
4. criar novo GameState;
5. definir estado PRONTO;
6. refletir nova sessão na interface.

---

## 8.15 Implementação da Camada de Regras

A camada de regras deve ser implementada como conjunto de funções puras sempre que possível.

### Requisitos obrigatórios

* independência de UI;
* independência de Canvas;
* independência de Jetpack Compose;
* independência de detalhes de animação;
* compatibilidade com teste unitário.

### Conteúdo esperado

* peça livre;
* elegibilidade;
* match;
* vitória;
* ausência de jogadas;
* verificações auxiliares derivadas do domínio.

---

## 8.16 Implementação do Controller

O controller deve ser implementado como coordenador explícito do fluxo da partida.

### Deve fazer

* receber eventos;
* chamar a lógica de domínio;
* atualizar o estado;
* decidir a transição correta entre estados formais;
* solicitar nova renderização por atualização de estado.

### Não deve fazer

* conter desenho visual;
* substituir o domínio;
* espalhar mutação sem controle;
* duplicar a lógica das regras.

---

## 8.17 Implementação da Renderização Procedural

A renderização deve ser implementada em Canvas procedural.

### Deve representar

* peças ativas;
* seleção;
* remoção;
* estados finais;
* profundidade visual coerente com camadas.

### Não deve

* decidir regras;
* alterar o estado por conta própria;
* validar match;
* manter verdade paralela da sessão.

### Requisitos

* legibilidade;
* clareza de seleção;
* clareza de remoção;
* coerência com UI/UX;
* compatibilidade com desempenho mobile.

---

## 8.18 Implementação da Geração Procedural

O gerador do tabuleiro deve:

* criar quantidade válida de peças;
* respeitar política de pareamento;
* respeitar camadas;
* evitar duplicidade de posição absoluta;
* garantir condição inicial jogável;
* buscar solucionabilidade conforme política da versão base.

### Saída mínima

* conjunto de peças;
* configuração usada;
* indicação de jogabilidade inicial.

---

## 8.19 Estratégia de Mutação de Estado

A implementação deve evitar mutações arbitrárias e espalhadas.

### Princípios

* atualizações devem ser controladas;
* o fluxo deve passar pelo controller;
* o estado deve permanecer coerente a cada etapa;
* seleção e remoção devem refletir exatamente o resultado da jogada processada.

### Recomendação

Privilegiar criação de novo estado ou mutações rigidamente centralizadas, em vez de alterações livres em múltiplos pontos da aplicação.

---

## 8.20 Estratégia de Testabilidade

A implementação técnica deve nascer preparada para teste.

### Prioridades

1. regras puras;
2. integridade do estado;
3. geração de tabuleiro;
4. fluxo do controller;
5. renderização básica coerente.

### Consequência

Qualquer escolha técnica que torne o domínio opaco ou difícil de testar deve ser considerada inadequada.

---

## 8.21 Estratégia de Performance

A implementação deve respeitar as metas funcionais e não funcionais do projeto.

### Diretrizes

* evitar alocações desnecessárias no loop principal;
* evitar lógica pesada na camada visual;
* evitar recomposição excessiva sem necessidade;
* manter renderização simples e legível;
* priorizar estabilidade da experiência.

---

## 8.22 Restrições Técnicas Obrigatórias

A implementação será considerada tecnicamente inadequada se ocorrer qualquer uma das situações abaixo:

* regra de elegibilidade implementada dentro do Canvas;
* match decidido na camada visual;
* estado modificado livremente por componentes de interface;
* dependência cíclica entre módulos;
* ausência de estrutura formal para estado;
* ausência de modelagem explícita das entidades centrais;
* geração de tabuleiro acoplada indevidamente à renderização.

---

## 8.23 Ordem Recomendada de Construção Técnica

A ordem recomendada de implementação é:

### Etapa 1

Modelos centrais do domínio.

### Etapa 2

Estados formais da partida.

### Etapa 3

Regras puras.

### Etapa 4

Geração procedural do tabuleiro.

### Etapa 5

Controller do fluxo.

### Etapa 6

Renderização procedural básica.

### Etapa 7

Feedback visual de seleção, remoção e estados finais.

### Etapa 8

Testes de regressão mínima.

Esta ordem favorece construção modular e compatibilidade com o método caracol.

---

## 8.24 Compatibilidade com o Método Caracol

Embora este documento pertença à Waterfall V2, sua implementação deve ser pensada para evolução incremental posterior.

A implementação deve permitir:

* encaixe progressivo de blocos;
* expansão sem reescrita destrutiva;
* fortalecimento gradual do sistema;
* ciclos curtos de validação.

A Waterfall define a base. O caracol conduz a execução incremental.

---

## 8.25 Critérios de Conformidade da Implementação Técnica

A implementação será considerada coerente com este documento quando:

* os módulos essenciais existirem;
* as entidades do domínio estiverem materializadas;
* as regras centrais estiverem implementadas fora da UI;
* o GameState sustentar a partida;
* o controller coordenar corretamente o fluxo;
* a renderização refletir o estado;
* o gerador produzir tabuleiros válidos;
* a base permitir testes e evolução incremental.

---

## 8.26 Relação com os Demais Documentos

Este documento:

* concretiza tecnicamente a Visão;
* executa os Requisitos do SRS;
* materializa as Regras de Negócio;
* respeita a Arquitetura;
* implementa o Modelo de Dados;
* dá forma técnica ao GDD e às diretrizes de UI/UX;
* prepara o terreno para Build/Deploy e para a malha de testes.

Nenhum detalhe técnico posterior deve contradizer esta base sem revisão explícita do documento.

---

## 8.27 Declaração Oficial da Implementação Técnica

O presente documento estabelece as diretrizes oficiais de Implementação Técnica do VitahAcre na Waterfall V2.

Ele define a forma oficial de organização do código, os contratos técnicos mínimos, os módulos esperados, o fluxo técnico da partida e os princípios que devem orientar a construção real do sistema, ficando congelado como referência formal da execução técnica do projeto, salvo revisão explícita em versão posterior da documentação.
