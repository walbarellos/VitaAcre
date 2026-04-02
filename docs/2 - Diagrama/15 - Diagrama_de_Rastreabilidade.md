# 15 - Diagrama de Rastreabilidade

## 15.1 Objetivo
Este diagrama apresenta a rastreabilidade oficial do projeto VitahAcre, conectando os principais elementos da documentação e da implementação.

Ele serve para:
- mostrar como os requisitos se ligam às regras;
- mostrar como as regras se ligam aos componentes;
- mostrar como os componentes se ligam aos testes;
- reduzir lacunas entre documentação e execução;
- elevar a governança técnica do projeto.

---

## 15.2 Leitura do Diagrama
A rastreabilidade do VitahAcre deve permitir responder perguntas como:
- qual requisito gera qual regra?
- qual regra mora em qual componente?
- qual componente é validado por quais testes?
- quais casos de teste protegem cada comportamento crítico?

O diagrama abaixo representa a cadeia mínima de rastreabilidade do projeto.

---

## 15.3 Diagrama Mermaid

```mermaid
flowchart LR
    subgraph REQ[Requisitos]
        R1[SRS]
    end

    subgraph BUS[Regras de Negocio]
        B1[Elegibilidade]
        B2[Match]
        B3[Vitoria]
        B4[Sem Jogadas]
        B5[Reinicio]
        B6[Geracao Valida]
    end

    subgraph COMP[Componentes]
        C1[domain/model]
        C2[domain/rules]
        C3[domain/state]
        C4[domain/usecase]
        C5[data/generator]
        C6[presentation/controller]
        C7[presentation/canvas]
    end

    subgraph TEST[Tipos de Teste]
        T1[Testes Unitarios]
        T2[Testes de Integracao]
        T3[Testes Funcionais]
        T4[Testes Visuais]
        T5[Testes de Performance]
    end

    subgraph CASE[Casos de Teste]
        K1[CT-RN]
        K2[CT-ST]
        K3[CT-CT]
        K4[CT-GE]
        K5[CT-RD]
        K6[CT-FL]
        K7[CT-PF]
    end

    R1 --> B1
    R1 --> B2
    R1 --> B3
    R1 --> B4
    R1 --> B5
    R1 --> B6

    B1 --> C2
    B1 --> C6
    B2 --> C2
    B2 --> C4
    B2 --> C6
    B3 --> C2
    B3 --> C3
    B4 --> C2
    B4 --> C3
    B5 --> C5
    B5 --> C3
    B5 --> C6
    B6 --> C5
    B6 --> C1

    C1 --> T1
    C2 --> T1
    C3 --> T1
    C5 --> T1

    C2 --> T2
    C3 --> T2
    C5 --> T2
    C6 --> T2

    C6 --> T3
    C3 --> T3
    C5 --> T3

    C7 --> T4
    C3 --> T4

    C7 --> T5
    C6 --> T5

    T1 --> K1
    T1 --> K2
    T1 --> K4

    T2 --> K2
    T2 --> K3
    T2 --> K4

    T3 --> K6
    T3 --> K3

    T4 --> K5

    T5 --> K7
````

---

## 15.4 Interpretação da Cadeia de Rastreabilidade

### Requisitos

O ponto de partida da rastreabilidade é o SRS.

Ele define:

* o que o sistema deve fazer;
* quais comportamentos são obrigatórios;
* quais estados a sessão deve possuir;
* quais condições precisam ser detectadas.

---

### Regras de Negócio

Os requisitos se refinam em regras formais do domínio.

No núcleo do jogo, as regras mais críticas são:

* elegibilidade;
* match;
* vitória;
* ausência de jogadas;
* reinício;
* geração válida do tabuleiro.

---

### Componentes

As regras não ficam soltas.
Elas precisam morar em componentes reais do sistema.

Exemplos:

* elegibilidade → `domain/rules`
* match → `domain/rules`, `domain/usecase`, `presentation/controller`
* vitória e bloqueio → `domain/rules`, `domain/state`
* reinício → `data/generator`, `domain/state`, `presentation/controller`
* geração válida → `data/generator`, `domain/model`

---

### Tipos de Teste

Cada componente precisa ser protegido por um ou mais níveis de validação:

* unitário;
* integração;
* funcional;
* visual;
* performance.

---

### Casos de Teste

Os tipos de teste se concretizam em famílias de casos:

* `CT-RN`
* `CT-ST`
* `CT-CT`
* `CT-GE`
* `CT-RD`
* `CT-FL`
* `CT-PF`

Essa é a ponta operacional da rastreabilidade.

---

## 15.5 Cadeias Críticas de Exemplo

### Cadeia 1 - Elegibilidade

SRS
→ Regra de elegibilidade
→ `domain/rules`
→ testes unitários e integração
→ `CT-RN`, `CT-ST`

---

### Cadeia 2 - Match

SRS
→ Regra de match
→ `domain/rules`, `domain/usecase`, `presentation/controller`
→ testes unitários, integração e funcionais
→ `CT-RN`, `CT-CT`, `CT-FL`

---

### Cadeia 3 - Vitória

SRS
→ Regra de vitória
→ `domain/rules`, `domain/state`
→ testes unitários, integração e funcionais
→ `CT-ST`, `CT-FL`

---

### Cadeia 4 - Geração Procedural

SRS
→ Regra de geração válida
→ `data/generator`, `domain/model`
→ testes unitários e integração
→ `CT-GE`

---

### Cadeia 5 - Renderização

SRS / UI-UX
→ representação visual coerente
→ `presentation/canvas`, `domain/state`
→ testes visuais e performance
→ `CT-RD`, `CT-PF`

---

## 15.6 Regras Estruturais Implicadas pelo Diagrama

### RRT-01

Todo requisito relevante deve se conectar a uma ou mais regras formais.

### RRT-02

Toda regra crítica deve ter morada em componente real.

### RRT-03

Todo componente central deve ser coberto por algum tipo de teste.

### RRT-04

Todo tipo de teste precisa se concretizar em casos operacionais.

### RRT-05

Não deve existir regra crítica sem proteção de teste.

### RRT-06

Não deve existir caso de teste importante sem vínculo claro com regra ou componente.

---

## 15.7 Falhas que Este Diagrama Ajuda a Evitar

Este fluxo ajuda a evitar:

* requisito sem implementação;
* regra sem morada técnica;
* componente sem validação;
* teste solto sem relação com o domínio;
* lacuna entre documentação e execução;
* falsa sensação de completude.

---

## 15.8 Papel Estratégico do Diagrama

Este é um diagrama de governança técnica.

Ele é importante porque:

* aumenta a maturidade documental do projeto;
* facilita auditoria interna;
* ajuda a revisar cobertura real;
* organiza a passagem da Waterfall para a execução técnica;
* mostra se o projeto está “fechado em si mesmo”.

Ele é especialmente importante para:

* revisão arquitetural;
* revisão de QA;
* congelamento da Waterfall;
* início do ciclo de implementação.

---

## 15.9 Compatibilidade com os Demais Documentos

Este diagrama depende diretamente de:

* `02 - Requisitos_SRS.md`
* `03 - Regras_de_Negocio.md`
* `04 - Arquitetura.md`
* `08 - Implementacao_Tecnica.md`
* `09 - Plano_de_Testes.md`
* `10 - Casos_de_Teste.md`

Ele também se apoia implicitamente em:

* `05 - Modelo_de_Dados.md`
* `06 - Game_Design_Document_GDD.md`
* `07 - UI_UX_Design.md`

---

## 15.10 Declaração Oficial

Este documento estabelece o Diagrama de Rastreabilidade do projeto VitahAcre e deve ser lido como a representação oficial da cadeia de ligação entre requisitos, regras, componentes, tipos de teste e casos de teste nesta fase do projeto.
