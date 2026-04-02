# 08 - Diagrama de Fluxo do Gerador Procedural

## 8.1 Objetivo
Este diagrama apresenta o fluxo oficial do gerador procedural do projeto VitahAcre, mostrando como um tabuleiro nasce a partir de uma configuração formal.

Ele serve para:
- formalizar a geração do tabuleiro;
- impedir geração improvisada ou inconsistente;
- orientar a implementação do componente `data/generator`;
- apoiar os testes do gerador;
- garantir coerência com regras, modelo de dados e arquitetura.

---

## 8.2 Leitura do Diagrama
O gerador procedural não é um bloco decorativo.  
Ele é responsável por construir a estrutura inicial jogável da sessão.

O fluxo mínimo esperado é:

- receber uma `BoardConfiguration`;
- validar parâmetros básicos;
- definir a quantidade total de peças;
- gerar pares lógicos;
- distribuir peças no espaço;
- respeitar camadas;
- validar unicidade de posição;
- verificar jogabilidade mínima;
- montar `BoardGenerationResult`.

O diagrama abaixo representa esse encadeamento lógico.

---

## 8.3 Diagrama Mermaid

```mermaid
flowchart TD
    A[Receber BoardConfiguration] --> B{Configuracao valida?}

    B -- nao --> X[Retornar falha de geracao]
    B -- sim --> C[Definir total de pecas]
    C --> D{Total de pecas e par?}

    D -- nao --> X
    D -- sim --> E[Gerar pairKeys]
    E --> F[Montar pares logicos]
    F --> G[Distribuir pecas em x y layer]
    G --> H{Existe duplicidade de posicao absoluta?}

    H -- sim --> G
    H -- nao --> I[Montar lista de Tile]
    I --> J[Validar integridade estrutural]
    J --> K{Estrutura valida?}

    K -- nao --> G
    K -- sim --> L[Verificar jogabilidade inicial]
    L --> M{Existe ao menos uma jogada valida?}

    M -- nao --> G
    M -- sim --> N[Marcar tabuleiro como jogavel]
    N --> O[Construir BoardGenerationResult]
    O --> P[Retornar tabuleiro inicial]
````

---

## 8.4 Interpretação das Etapas

### Receber BoardConfiguration

O gerador começa recebendo a configuração formal do tabuleiro.

Essa configuração informa, no mínimo:

* colunas;
* linhas;
* camadas;
* total de peças;
* seed opcional.

---

### Validar Configuração

Antes de gerar qualquer peça, o sistema precisa verificar se a configuração é aceitável.

Exemplos de validação:

* valores positivos;
* número total de peças compatível;
* camadas coerentes com a estratégia de distribuição.

---

### Definir Total de Peças

O gerador consolida a quantidade real de peças que serão usadas na sessão.

Essa etapa deve respeitar a política oficial do jogo:

* o total de peças precisa ser compatível com pareamento.

---

### Verificar se o Total é Par

Como o jogo depende de pares válidos, o total de peças não pode ser ímpar.

Se for ímpar:

* a geração deve falhar ou ser corrigida antes de prosseguir.

---

### Gerar pairKeys

O gerador define os identificadores lógicos de pareamento.

Esses identificadores serão usados depois para:

* formação de par;
* validação de match;
* consistência do tabuleiro.

---

### Montar Pares Lógicos

Após gerar os `pairKeys`, o sistema monta o conjunto lógico de pares que comporá a sessão.

Essa etapa prepara a distribuição espacial posterior.

---

### Distribuir Peças em x y layer

Agora o gerador distribui cada peça em coordenadas:

* horizontais;
* verticais;
* de camada.

Essa é a etapa em que nasce a geometria do tabuleiro.

---

### Validar Duplicidade de Posição Absoluta

A combinação:

* `x`
* `y`
* `layer`

não pode se repetir para duas peças distintas.

Se houver colisão:

* a distribuição deve ser refeita ou corrigida.

---

### Montar Lista de Tile

Com as posições válidas, o gerador constrói a lista real de entidades `Tile`.

Cada peça passa a existir formalmente no domínio.

---

### Validar Integridade Estrutural

O tabuleiro gerado precisa ser estruturalmente coerente.

Exemplos:

* todas as peças possuem posição válida;
* todos os pares foram materializados corretamente;
* não há dados inconsistentes.

---

### Verificar Jogabilidade Inicial

O sistema deve verificar se o tabuleiro recém-gerado nasce com ao menos uma jogada válida possível.

Essa etapa é central para evitar sessões mortas na origem.

---

### Construir BoardGenerationResult

Depois das validações, o gerador monta a estrutura de saída formal contendo:

* peças;
* configuração;
* indicador de jogabilidade;
* indicador de existência de jogada inicial.

---

### Retornar Tabuleiro Inicial

A saída do gerador passa a alimentar a criação do `GameState` inicial da sessão.

---

## 8.5 Regras Estruturais Implicadas pelo Diagrama

Este fluxo implica as seguintes regras:

### RGP-01

O gerador não pode ignorar a `BoardConfiguration`.

### RGP-02

O total de peças deve ser compatível com a lógica de pares.

### RGP-03

Não pode haver duplicidade de posição absoluta.

### RGP-04

A geração só é válida se a estrutura final for coerente.

### RGP-05

O tabuleiro inicial deve possuir jogabilidade mínima.

### RGP-06

A saída final do gerador deve ser uma estrutura formal, e não apenas uma lista solta de peças.

---

## 8.6 Falhas que Este Diagrama Ajuda a Evitar

Este fluxo protege contra:

* geração com número ímpar de peças;
* duplicidade de coordenadas;
* pares incompletos;
* estrutura espacial inconsistente;
* sessão inicial sem jogadas;
* generator sem contrato formal de saída.

---

## 8.7 Papel Estratégico do Diagrama

Este é um dos diagramas mais importantes do projeto porque:

* protege a origem da sessão;
* sustenta a integridade do tabuleiro;
* organiza o trabalho do `generator`;
* facilita a criação de testes específicos da geração;
* impede que a partida nasça estruturalmente defeituosa.

Ele é especialmente importante para:

* `data/generator`
* `domain/model`
* `domain/state`
* testes de geração e integridade

---

## 8.8 Compatibilidade com os Demais Documentos

Este diagrama concretiza visualmente o conteúdo dos seguintes documentos:

* `02 - Requisitos_SRS.md`
* `03 - Regras_de_Negocio.md`
* `04 - Arquitetura.md`
* `05 - Modelo_de_Dados.md`
* `08 - Implementacao_Tecnica.md`
* `09 - Plano_de_Testes.md`
* `10 - Casos_de_Teste.md`

Ele deve permanecer coerente com todos eles.

---

## 8.9 Declaração Oficial

Este documento estabelece o Diagrama de Fluxo do Gerador Procedural do projeto VitahAcre e deve ser lido como a representação oficial do encadeamento lógico da geração do tabuleiro nesta fase do projeto.
