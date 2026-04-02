# 04 - Arquitetura

## 4.1 Objetivo do Documento
Este documento define a arquitetura oficial do projeto VitahAcre na Waterfall V2. Seu propósito é estabelecer, de forma clara e estável, a organização estrutural do sistema, a distribuição de responsabilidades entre os módulos, o fluxo principal de dados e as restrições arquiteturais que devem ser respeitadas pela implementação.

A arquitetura aqui definida deve:
- materializar os requisitos do SRS;
- respeitar as regras de negócio do domínio;
- preservar separação entre lógica, estado e interface;
- permitir evolução incremental posterior pelo método caracol;
- servir como referência principal para implementação técnica, testes e revisão estrutural.

---

## 4.2 Papel da Arquitetura no Projeto
A arquitetura do VitahAcre possui função central de mediação entre:
- a visão do produto;
- os requisitos funcionais e não funcionais;
- as regras de negócio;
- o modelo de dados;
- a implementação em código;
- os testes;
- a renderização final na plataforma Android.

Ela existe para impedir:
- mistura de responsabilidades;
- duplicação indevida de lógica;
- acoplamento excessivo;
- improvisação estrutural;
- crescimento desordenado do projeto.

---

## 4.3 Princípios Arquiteturais Oficiais

### ARQ-01 - Separação de Responsabilidades
Cada camada e cada módulo devem possuir responsabilidade específica, identificável e limitada.

**Implicação:** nenhum módulo deve acumular funções de domínio, renderização, persistência e controle ao mesmo tempo.

### ARQ-02 - Estado como Fonte Única da Verdade
O estado da partida deve ser suficiente para representar a situação real do jogo em qualquer instante relevante.

**Implicação:** a interface deve refletir o estado, e não inventá-lo paralelamente.

### ARQ-03 - Regras de Domínio Desacopladas da Interface
As regras do jogo devem existir em camada própria, independente de Canvas, Compose, animações, cores ou efeitos visuais.

**Implicação:** a mesma lógica central deve poder ser testada sem a interface gráfica.

### ARQ-04 - Fluxo de Controle Explícito
A passagem de um toque do usuário até a atualização do estado deve seguir fluxo claro, previsível e rastreável.

**Implicação:** não deve haver mutação arbitrária do estado a partir de qualquer componente visual.

### ARQ-05 - Baixo Acoplamento
Os módulos devem depender o mínimo possível uns dos outros.

**Implicação:** mudanças em uma camada não devem exigir reescrita ampla das demais.

### ARQ-06 - Alta Coesão
Cada módulo deve concentrar funcionalidades da mesma natureza.

**Implicação:** o módulo de regras deve conter regras; o módulo de renderização deve conter renderização; o módulo de estado deve conter estado.

### ARQ-07 - Testabilidade
A arquitetura deve permitir testes:
- unitários;
- de integração;
- funcionais;
- de desempenho.

**Implicação:** o sistema não pode nascer estruturalmente hostil à validação.

### ARQ-08 - Evolução Incremental Compatível
A arquitetura deve permitir que o projeto cresça por ciclos posteriores sem colapso estrutural.

**Implicação:** a fundação Waterfall deve ser rígida o suficiente para orientar, mas modular o suficiente para acomodar refinamentos caracol.

---

## 4.4 Visão Arquitetural Geral
A visão geral oficial da arquitetura do VitahAcre é a de um sistema modular orientado a estado, composto por camadas com fluxo principal unidirecional de processamento.

Fluxo macro oficial:

```text
Entrada do usuário
→ Interpretação do evento
→ Controle da jogada
→ Avaliação das regras do domínio
→ Atualização do estado
→ Renderização da interface
````

Em formulação mais técnica:

```text
Input Layer
→ Controller
→ Rules Engine
→ State Manager
→ Renderer
```

Esta ordem representa o fluxo estrutural canônico do sistema.

---

## 4.5 Camadas Arquiteturais Oficiais

### 4.5.1 Camada de Entrada

Responsável por capturar a interação do usuário com a interface.

**Responsabilidades**

* receber toques;
* converter toques em eventos compreensíveis pelo sistema;
* encaminhar eventos ao controlador;
* não decidir regra de jogo.

**Entradas**

* coordenadas de toque;
* acionamento de comandos da interface, como reinício.

**Saídas**

* eventos de entrada padronizados para processamento interno.

**Proibições**

* não pode validar match;
* não pode remover peças;
* não pode alterar diretamente o GameState sem mediação adequada.

### 4.5.2 Camada de Controle

Responsável por coordenar o fluxo operacional do jogo.

**Responsabilidades**

* receber eventos da camada de entrada;
* identificar a peça correspondente ao toque;
* solicitar validações à camada de regras;
* atualizar o estado da partida;
* disparar verificações de vitória, bloqueio ou continuidade;
* coordenar reinício.

**Entradas**

* eventos da interface;
* estado atual da partida.

**Saídas**

* novo estado da partida;
* comandos coerentes de atualização do fluxo.

**Proibições**

* não deve conter lógica visual;
* não deve incorporar renderização;
* não deve duplicar regras centrais já pertencentes ao domínio.

### 4.5.3 Camada de Regras do Domínio

Responsável pela lógica pura do jogo.

**Responsabilidades**

* verificar peça livre;
* verificar elegibilidade;
* verificar match;
* verificar vitória;
* verificar ausência de jogadas;
* validar integridade lógica da jogada.

**Entradas**

* peças;
* estado da partida;
* estruturas auxiliares do domínio.

**Saídas**

* resultados lógicos;
* respostas determinísticas;
* critérios formais para o controlador agir.

**Características obrigatórias**

* deve ser testável isoladamente;
* deve ser independente da UI;
* deve ser independente de detalhes Android;
* deve operar sobre o domínio e não sobre aparência.

### 4.5.4 Camada de Estado

Responsável pela representação oficial da partida.

**Responsabilidades**

* armazenar a lista de peças;
* armazenar peças selecionadas;
* armazenar status formal da partida;
* oferecer base suficiente para reconstrução visual;
* servir como referência única do momento atual do jogo.

**Características obrigatórias**

* deve ser coerente;
* deve ser íntegra;
* deve ser suficiente para a UI;
* deve ser controlada pelo fluxo arquitetural.

**Proibições**

* não pode ser alterada livremente por qualquer componente;
* não pode divergir da realidade lógica da partida.

### 4.5.5 Camada de Geração de Tabuleiro

Responsável pela criação do tabuleiro inicial e de novos estados iniciais em reinício.

**Responsabilidades**

* gerar peças em quantidade compatível;
* organizar peças em estrutura válida;
* respeitar política de camadas;
* respeitar política de pareamento;
* produzir estado inicial jogável;
* permitir uso de seed, quando aplicável.

**Características obrigatórias**

* deve obedecer às regras de negócio do projeto;
* não deve desenhar a interface;
* não deve controlar a partida após sua criação.

### 4.5.6 Camada de Renderização

Responsável pela apresentação visual do jogo.

**Responsabilidades**

* desenhar o tabuleiro;
* desenhar peças;
* destacar seleção;
* ocultar ou tratar peças removidas;
* refletir estados finais;
* apresentar visual coerente com o estado.

**Entradas**

* estado atual da partida;
* dimensões da tela;
* parâmetros visuais.

**Saídas**

* representação visual ao jogador.

**Proibições**

* não pode decidir regras;
* não pode inventar estado;
* não pode remover peças por conta própria;
* não pode declarar vitória ou bloqueio autonomamente.

---

## 4.6 Fluxo Arquitetural Principal

### 4.6.1 Fluxo de Jogada

O fluxo arquitetural mínimo de uma jogada deve seguir esta ordem:

1. o usuário toca a interface;
2. a camada de entrada captura o evento;
3. o controlador interpreta a ação;
4. o sistema identifica a peça alvo;
5. a camada de regras avalia a elegibilidade;
6. o controlador atualiza a seleção;
7. quando houver duas seleções válidas, a camada de regras avalia o match;
8. se houver match válido, o controlador atualiza o estado marcando remoção;
9. o controlador verifica vitória ou bloqueio;
10. a interface é re-renderizada a partir do novo estado.

### 4.6.2 Fluxo de Toque Inválido

Quando o usuário tocar em área sem peça ou em peça inelegível:

1. a entrada é capturada;
2. o controlador tenta resolver a peça alvo;
3. a inexistência ou inelegibilidade é reconhecida;
4. o estado permanece íntegro;
5. a renderização não pode sugerir alteração lógica inexistente.

### 4.6.3 Fluxo de Reinício

Quando o usuário acionar reinício:

1. o evento de reinício é capturado;
2. o controlador solicita novo tabuleiro;
3. a camada de geração produz novo conjunto válido;
4. novo estado inicial é construído;
5. a partida retorna ao estado pronto;
6. a renderização apresenta a nova sessão.

---

## 4.7 Estrutura Modular Recomendada

A estrutura modular recomendada para o projeto é a seguinte:

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
```

Esta estrutura é a recomendação arquitetural oficial da Waterfall V2.

---

## 4.8 Função Arquitetural de Cada Bloco

### 4.8.1 app

Contém a entrada principal da aplicação, configuração global e integração com o ciclo de vida Android.

### 4.8.2 domain/model

Contém as estruturas centrais do domínio, como peças, estado, status e demais entidades nucleares.

### 4.8.3 domain/rules

Contém as regras puras do jogo, derivadas do documento de Regras de Negócio.

### 4.8.4 domain/state

Contém contratos e estruturas relacionadas ao estado oficial da partida.

### 4.8.5 domain/usecase

Contém operações de domínio e fluxos reutilizáveis de maior nível, quando a implementação exigir esse agrupamento.

### 4.8.6 data/generator

Contém a geração procedural do tabuleiro e da configuração inicial da sessão.

### 4.8.7 data/repository

Reservado para futuras persistências locais, configurações salvas ou dados auxiliares, caso a evolução do projeto exija.

### 4.8.8 presentation/input

Contém a tradução da interação do usuário em eventos compreensíveis para o controlador.

### 4.8.9 presentation/controller

Contém o coordenador operacional da jogada.

### 4.8.10 presentation/screen

Contém a composição das telas e pontos de integração visível da interface.

### 4.8.11 presentation/component

Contém componentes visuais reutilizáveis.

### 4.8.12 presentation/canvas

Contém a renderização procedural do tabuleiro e dos elementos gráficos centrais do jogo.

### 4.8.13 util

Contém utilitários matemáticos, geométricos ou auxiliares que não pertencem diretamente ao núcleo semântico do domínio.

### 4.8.14 tests

Contém testes unitários, de integração, funcionais e de desempenho.

---

## 4.9 Dependências Permitidas

As dependências arquiteturais devem seguir esta direção preferencial:

```text
presentation/input → presentation/controller
presentation/controller → domain/rules
presentation/controller → domain/state
presentation/controller → data/generator
domain/rules → domain/model
domain/state → domain/model
data/generator → domain/model
presentation/canvas → domain/state
presentation/screen → domain/state
```

**Regra central:** as dependências devem apontar para o núcleo lógico e nunca transformar a camada visual em dona do domínio.

---

## 4.10 Dependências Proibidas

### DP-01

A camada de renderização não pode depender da camada de regras para tomar decisões de negócio por conta própria.

### DP-02

A camada de entrada não pode mutar diretamente o estado.

### DP-03

A camada visual não pode se tornar fonte paralela de verdade da partida.

### DP-04

A camada de regras não pode depender de Canvas, Compose, widgets ou animações.

### DP-05

Dependências cíclicas entre módulos são proibidas.

### DP-06

O gerador de tabuleiro não pode assumir papel de controlador da partida em andamento.

---

## 4.11 Arquitetura de Estado

O GameState ocupa posição central na arquitetura do projeto.

**Funções arquiteturais do estado**

* representar a partida;
* concentrar a verdade da sessão;
* informar a renderização;
* permitir avaliação pelas regras;
* suportar reinício limpo;
* fornecer base para testes.

**Consequência estrutural:** toda camada que precise conhecer a situação real da partida deve se orientar pelo estado oficial, e não por inferências visuais.

---

## 4.12 Arquitetura de Controle

O controlador possui função de orquestração, não de domínio absoluto.

**O que o controlador deve fazer**

* coordenar o fluxo;
* chamar a camada certa no momento certo;
* preservar a ordem das validações;
* atualizar o estado de forma controlada.

**O que o controlador não deve fazer**

* desenhar interface;
* substituir o motor de regras;
* se tornar depósito desorganizado de toda a lógica do jogo.

---

## 4.13 Arquitetura da Renderização

A renderização do VitahAcre deve ser:

* procedural;
* orientada a estado;
* desacoplada da regra;
* leve;
* coerente com a sessão atual.

**Consequência:** o tabuleiro mostrado ao usuário deve ser sempre consequência do GameState.

---

## 4.14 Arquitetura da Geração Procedural

A geração procedural deve ser considerada subsistema especializado.

**Funções**

* criar o conjunto inicial de peças;
* organizar as camadas;
* respeitar pareamento;
* garantir condições mínimas de jogabilidade;
* fornecer base lógica ao estado inicial.

**Limites**

* não controla a partida em andamento;
* não substitui o motor de regras;
* não pertence à camada visual.

---

## 4.15 Estados Arquiteturais Oficiais da Partida

Os estados arquiteturais mínimos da partida são:

```text
INICIALIZANDO
PRONTO
SELECIONANDO_1
SELECIONANDO_2
PROCESSANDO_MATCH
VITORIA
SEM_JOGADAS
```

Esses estados devem existir de forma formal e coerente com o SRS e com o modelo de dados.

---

## 4.16 Restrições Arquiteturais Obrigatórias

A implementação será considerada arquiteturalmente inválida se ocorrer qualquer uma das situações abaixo:

* lógica de match implementada na camada visual;
* mutação direta do estado a partir da interface sem mediação controlada;
* duplicação de regras centrais em múltiplas camadas;
* renderização produzindo estado paralelo;
* dependência cíclica entre módulos;
* mistura indevida entre geração procedural e fluxo da partida em andamento.

---

## 4.17 Critérios de Conformidade Arquitetural

A arquitetura será considerada corretamente respeitada quando:

* a lógica de domínio estiver isolada;
* o estado for a fonte única da verdade;
* a renderização depender do estado;
* o controlador coordenar o fluxo sem absorver toda a semântica do domínio;
* a geração de tabuleiro estiver separada da jogabilidade corrente;
* os testes puderem alcançar regras e fluxo sem depender da interface gráfica.

---

## 4.18 Relação com os Demais Documentos

Este documento:

* concretiza estruturalmente a Visão;
* organiza a execução dos Requisitos do SRS;
* dá morada formal às Regras de Negócio;
* prepara o encaixe do Modelo de Dados;
* fundamenta a Implementação Técnica;
* condiciona o Plano de Testes e os Casos de Teste.

Nenhum documento posterior deve contrariar esta arquitetura sem revisão explícita desta base.

---

## 4.19 Declaração Oficial da Arquitetura

A arquitetura oficial do VitahAcre na Waterfall V2 é modular, orientada a estado, com regras de domínio desacopladas da interface, controle explícito do fluxo de jogada, geração procedural separada da jogabilidade corrente e renderização subordinada ao estado oficial da partida.

Este documento fica congelado como referência arquitetural oficial do projeto, salvo revisão explícita em versão posterior da documentação.
