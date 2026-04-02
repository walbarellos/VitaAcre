# 09 - Diagrama de Fluxo de Testes

## 9.1 Objetivo
Este diagrama apresenta o fluxo oficial de validação do projeto VitahAcre, mostrando como os testes se distribuem ao longo do ciclo técnico do sistema.

Ele serve para:
- formalizar a estratégia de testes;
- organizar a sequência lógica de validação;
- conectar implementação, verificação e regressão;
- orientar QA técnico e evolução incremental;
- impedir crescimento do sistema sem proteção correspondente.

---

## 9.2 Leitura do Diagrama
Os testes do VitahAcre não devem ser tratados como etapa decorativa no fim do projeto.

Eles precisam acompanhar a construção do sistema em camadas.

A ordem correta de validação parte do núcleo mais estável e menos dependente de interface:
- regras puras;
- integridade do estado;
- geração procedural;
- controller;
- renderização;
- performance;
- regressão mínima obrigatória.

O diagrama abaixo representa essa progressão.

---

## 9.3 Diagrama Mermaid

```mermaid
flowchart TD
    A[Implementacao de bloco tecnico] --> B[Testes Unitarios]
    B --> C{Regras e modelos aprovados?}

    C -- nao --> A
    C -- sim --> D[Testes de Estado]
    D --> E{Estado integra apos transicoes?}

    E -- nao --> A
    E -- sim --> F[Testes do Gerador Procedural]
    F --> G{Tabuleiro estruturalmente valido e jogavel?}

    G -- nao --> A
    G -- sim --> H[Testes de Integracao]
    H --> I{Controller, regras e estado conversam corretamente?}

    I -- nao --> A
    I -- sim --> J[Testes Funcionais]
    J --> K{Fluxo principal da partida esta correto?}

    K -- nao --> A
    K -- sim --> L[Testes Visuais]
    L --> M{UI reflete o estado corretamente?}

    M -- nao --> A
    M -- sim --> N[Testes de Performance]
    N --> O{Desempenho aceitavel?}

    O -- nao --> A
    O -- sim --> P[Regressao Minima Obrigatoria]
    P --> Q{Todos os casos criticos seguem aprovados?}

    Q -- nao --> A
    Q -- sim --> R[Build apta para validacao tecnica]
````

---

## 9.4 Interpretação das Etapas

### Implementação de Bloco Técnico

O fluxo de testes começa sempre após a implementação de um bloco real do sistema.

Exemplos:

* regra pura;
* entidade de domínio;
* generator;
* controller;
* renderer.

Nenhum bloco relevante deve avançar sem validação.

---

### Testes Unitários

Primeira camada de proteção.

Devem validar:

* regras puras;
* funções determinísticas;
* entidades simples;
* verificações isoladas.

São os testes mais importantes para o núcleo lógico.

---

### Testes de Estado

Segunda camada de proteção.

Devem validar:

* coerência do GameState;
* transições formais;
* consistência da seleção;
* consistência do status;
* integridade após ações válidas e inválidas.

---

### Testes do Gerador Procedural

Terceira camada de proteção.

Devem validar:

* número par de peças;
* ausência de duplicidade de posição;
* coerência de pareamento;
* jogabilidade inicial mínima;
* estrutura válida do tabuleiro.

---

### Testes de Integração

Quarta camada de proteção.

Devem validar:

* input com controller;
* controller com regras;
* controller com estado;
* generator com estado;
* estado com renderização.

Esses testes verificam se os blocos já funcionam em conjunto.

---

### Testes Funcionais

Quinta camada de proteção.

Devem validar o comportamento completo da sessão:

* iniciar partida;
* selecionar peças;
* validar match;
* remover;
* detectar vitória;
* detectar bloqueio;
* reiniciar.

Aqui a pergunta é:
“o jogo realmente funciona do ponto de vista da sessão?”

---

### Testes Visuais

Sexta camada de proteção.

Devem validar:

* destaque da seleção;
* remoção coerente;
* legibilidade do tabuleiro;
* distinção entre vitória e bloqueio;
* coerência da UI com o estado.

A interface não pode mentir sobre a sessão.

---

### Testes de Performance

Sétima camada de proteção.

Devem validar:

* tempo de resposta ao toque;
* FPS durante gameplay;
* tempo de inicialização;
* consumo de memória;
* estabilidade geral.

Esses testes garantem que o produto não apenas funciona, mas funciona bem o suficiente.

---

### Regressão Mínima Obrigatória

Camada final de proteção antes de considerar a build tecnicamente confiável.

Deve reexecutar os cenários críticos:

* elegibilidade;
* match válido;
* match inválido;
* vitória;
* bloqueio;
* reinício;
* geração inicial jogável.

Se a regressão falhar, o ciclo deve voltar à correção.

---

## 9.5 Lógica do Fluxo

A lógica oficial do fluxo de testes é esta:

1. implementar um bloco;
2. validar seu núcleo lógico;
3. validar o estado;
4. validar integração;
5. validar comportamento funcional;
6. validar interface;
7. validar desempenho;
8. reexecutar malha crítica de regressão;
9. só então considerar o avanço do projeto estável.

---

## 9.6 Critérios de Decisão Visíveis no Diagrama

Este fluxo implica as seguintes decisões:

### RFT-01

Falha em qualquer etapa crítica interrompe o avanço e força retorno à implementação.

### RFT-02

Não se deve pular de regra pura direto para build sem passar por integração e funcional.

### RFT-03

O crescimento do sistema precisa ser acompanhado por crescimento da validação.

### RFT-04

Performance só deve ser validada depois que o fluxo funcional já estiver coerente.

### RFT-05

A regressão mínima obrigatória é gate final antes de considerar a build confiável.

---

## 9.7 Riscos que o Fluxo Ajuda a Evitar

Este diagrama protege contra:

* regras erradas passando despercebidas;
* estado inconsistente;
* geração estruturalmente defeituosa;
* controller quebrado por integração mal feita;
* UI mentindo sobre o estado;
* perda silenciosa de desempenho;
* regressão funcional após novos ciclos.

---

## 9.8 Papel Estratégico do Diagrama

Este diagrama é importante porque:

* conecta o Plano de Testes à prática real;
* mostra que o teste não é uma única etapa, mas uma cadeia;
* disciplina a evolução do projeto;
* prepara o projeto para ciclos caracol sem caos;
* ajuda a decidir quando uma build merece confiança.

Ele é especialmente importante para:

* QA técnico;
* implementação incremental;
* regressão;
* decisão de prontidão de build.

---

## 9.9 Compatibilidade com os Demais Documentos

Este diagrama concretiza visualmente o conteúdo dos seguintes documentos:

* `08 - Implementacao_Tecnica.md`
* `09 - Plano_de_Testes.md`
* `10 - Casos_de_Teste.md`
* `11 - Build_Deploy.md`

Também depende da coerência de:

* `02 - Requisitos_SRS.md`
* `03 - Regras_de_Negocio.md`
* `04 - Arquitetura.md`
* `05 - Modelo_de_Dados.md`

---

## 9.10 Declaração Oficial

Este documento estabelece o Diagrama de Fluxo de Testes do projeto VitahAcre e deve ser lido como a representação oficial da cadeia de validação do sistema nesta fase do projeto.
