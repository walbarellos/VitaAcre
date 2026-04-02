# 10 - Diagrama de Pacotes

## 10.1 Objetivo
Este diagrama apresenta a organização lógica por pacotes do projeto VitahAcre, traduzindo a arquitetura e os componentes para a estrutura concreta que deverá existir no código-fonte.

Ele serve para:
- organizar o projeto em blocos previsíveis;
- preparar a estrutura real do repositório;
- orientar implementação e manutenção;
- mostrar relações entre pacotes;
- reduzir acoplamento e bagunça estrutural.

---

## 10.2 Leitura do Diagrama
O projeto é organizado em cinco zonas principais:

- `app`
- `domain`
- `data`
- `presentation`
- `util`
- `tests`

Cada uma possui responsabilidade específica.

A estrutura proposta acompanha a arquitetura em camadas e sustenta o método de crescimento modular do projeto.

---

## 10.3 Diagrama Mermaid

```mermaid
flowchart LR
    subgraph APP[app]
        A1[entrypoint]
    end

    subgraph DOMAIN[domain]
        D1[model]
        D2[rules]
        D3[state]
        D4[usecase]
    end

    subgraph DATA[data]
        DA1[generator]
        DA2[repository]
    end

    subgraph PRESENTATION[presentation]
        P1[input]
        P2[controller]
        P3[screen]
        P4[component]
        P5[canvas]
    end

    subgraph UTIL[util]
        U1[helpers]
        U2[math]
        U3[geometry]
        U4[seed]
    end

    subgraph TESTS[tests]
        T1[unit]
        T2[integration]
        T3[functional]
        T4[performance]
    end

    A1 --> P3
    A1 --> P2

    P1 --> P2
    P2 --> D4
    P2 --> D3
    P2 --> DA1

    D4 --> D2
    D4 --> D3
    D4 --> D1

    D2 --> D1
    D3 --> D1
    DA1 --> D1
    DA1 --> D3

    P3 --> P4
    P3 --> P5
    P3 --> D3
    P5 --> D3
    P4 --> D3

    U1 -. suporte .-> P1
    U1 -. suporte .-> P2
    U2 -. suporte .-> D2
    U3 -. suporte .-> P5
    U4 -. suporte .-> DA1

    T1 -. testa .-> D1
    T1 -. testa .-> D2
    T2 -. testa .-> P2
    T2 -. testa .-> D3
    T2 -. testa .-> DA1
    T3 -. testa .-> P3
    T4 -. testa .-> P5
````

---

## 10.4 Interpretação dos Pacotes

### app

Contém o ponto de entrada da aplicação.

Responsável por:

* bootstrap do app;
* integração inicial com Android;
* carregamento da tela principal;
* ligação entre runtime e camada de apresentação.

---

### domain/model

Contém as entidades centrais do domínio, como:

* Tile;
* GameState;
* GameStatus;
* SelectionState;
* BoardConfiguration;
* MatchResult;
* InputEvent;
* BoardGenerationResult.

É o núcleo semântico do sistema.

---

### domain/rules

Contém as regras puras do jogo.

Responsável por:

* peça livre;
* elegibilidade;
* match;
* vitória;
* ausência de jogadas;
* verificações derivadas do domínio.

---

### domain/state

Contém o estado formal da sessão.

Responsável por:

* representar a partida;
* manter status;
* manter seleção;
* sustentar a verdade oficial da sessão.

---

### domain/usecase

Contém operações organizadas de domínio.

Responsável por:

* compor regras e estado;
* encapsular fluxos reutilizáveis;
* reduzir lógica espalhada pelo controller.

---

### data/generator

Contém o gerador procedural do tabuleiro.

Responsável por:

* criar peças;
* distribuir pares;
* respeitar camadas;
* validar estrutura inicial;
* devolver resultado formal de geração.

---

### data/repository

Bloco reservado para futuras necessidades de persistência.

Possível uso futuro:

* estatísticas;
* preferências;
* dados locais persistentes.

Na versão base, ele não é o centro do projeto.

---

### presentation/input

Contém a tradução de toque em evento interno.

Responsável por:

* capturar ação do jogador;
* transformar interação em `InputEvent`;
* encaminhar o fluxo ao controller.

---

### presentation/controller

Contém o coordenador operacional da sessão.

Responsável por:

* processar toque;
* controlar seleção;
* acionar regras;
* atualizar estado;
* solicitar geração em reinício.

---

### presentation/screen

Contém a organização das telas.

Responsável por:

* compor a UI principal;
* estruturar o layout visível;
* observar estado e integrar componentes.

---

### presentation/component

Contém componentes visuais auxiliares.

Responsável por:

* botões;
* overlays;
* mensagens;
* elementos reutilizáveis de interface.

---

### presentation/canvas

Contém a renderização procedural do tabuleiro.

Responsável por:

* desenhar peças;
* desenhar seleção;
* representar remoção;
* mostrar profundidade por camada;
* refletir o estado real da sessão.

---

### util

Contém suporte técnico transversal.

Possíveis grupos:

* helpers gerais;
* matemática;
* geometria;
* seed;
* utilidades auxiliares não centrais ao domínio.

---

### tests

Contém os testes do projeto.

Subdivisão sugerida:

* unitários;
* integração;
* funcionais;
* performance.

---

## 10.5 Relações Principais Entre Pacotes

### RP-01

`app` inicia e conecta a camada de apresentação.

### RP-02

`presentation/input` envia eventos para `presentation/controller`.

### RP-03

`presentation/controller` coordena `domain/usecase`, `domain/state` e `data/generator`.

### RP-04

`domain/usecase` organiza o uso de `domain/rules`, `domain/state` e `domain/model`.

### RP-05

`data/generator` depende de `domain/model` e alimenta `domain/state`.

### RP-06

`presentation/screen`, `presentation/component` e `presentation/canvas` observam `domain/state`.

### RP-07

`util` oferece suporte transversal sem sequestrar responsabilidade semântica.

### RP-08

`tests` validam os pacotes centrais do projeto.

---

## 10.6 Regras Estruturais Implicadas pelo Diagrama

### RDP-01

`domain/model` deve permanecer o núcleo compartilhado do sistema.

### RDP-02

`presentation/canvas` não pode depender de `domain/rules` para decidir regras por conta própria.

### RDP-03

`presentation/input` não pode mutar diretamente o estado.

### RDP-04

`data/generator` não substitui o controller.

### RDP-05

`util` não deve virar depósito informal de lógica de domínio.

### RDP-06

`tests` devem alcançar diretamente os pacotes centrais.

---

## 10.7 Benefícios da Estrutura em Pacotes

Esta organização por pacotes ajuda a:

* manter clareza estrutural;
* facilitar onboarding;
* acelerar navegação no código;
* reduzir acoplamento;
* apoiar crescimento incremental;
* facilitar revisão técnica e testes.

Ela também favorece a execução por ciclos caracol.

---

## 10.8 Papel Estratégico do Diagrama

Este diagrama é importante porque:

* transforma a arquitetura abstrata em estrutura concreta;
* prepara a criação real do projeto Kotlin/Android;
* ajuda o agente e o desenvolvedor a saber “onde cada coisa mora”;
* reduz risco de implementação desorganizada.

Ele é especialmente útil antes do início da codificação real.

---

## 10.9 Compatibilidade com os Demais Documentos

Este diagrama concretiza visualmente o conteúdo dos seguintes documentos:

* `04 - Arquitetura.md`
* `05 - Modelo_de_Dados.md`
* `08 - Implementacao_Tecnica.md`

Também deve permanecer coerente com:

* `03 - Regras_de_Negocio.md`
* `09 - Plano_de_Testes.md`
* `10 - Casos_de_Teste.md`

---

## 10.10 Declaração Oficial

Este documento estabelece o Diagrama de Pacotes do projeto VitahAcre e deve ser lido como a representação oficial da organização lógica dos pacotes do sistema nesta fase do projeto.
