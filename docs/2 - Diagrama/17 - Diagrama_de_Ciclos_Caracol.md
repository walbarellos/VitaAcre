# 17 - Diagrama de Ciclos Caracol

## 17.1 Objetivo
Este diagrama apresenta a execução incremental oficial do projeto VitahAcre após o fechamento da Waterfall V2, mostrando como a implementação deve evoluir por ciclos caracol.

Ele serve para:
- unir Waterfall e execução real;
- organizar a ordem de construção do sistema;
- reduzir retrabalho;
- permitir crescimento modular em estilo LEGO;
- disciplinar implementação, testes e validação.

---

## 17.2 Leitura do Diagrama
A Waterfall V2 define a base rígida do projeto.

Depois dela, a implementação não deve acontecer de forma caótica nem em “big bang”.  
Ela deve acontecer por ciclos incrementais, cada um com escopo claro, validação e congelamento parcial.

A lógica do caracol é:
- construir um bloco;
- validar o bloco;
- integrar o bloco;
- testar o bloco;
- congelar o avanço;
- então abrir o próximo ciclo.

O diagrama abaixo representa essa progressão.

---

## 17.3 Diagrama Mermaid

```mermaid
flowchart TD
    A[Waterfall V2 congelada] --> B[Ciclo 01 - Dominio e Estado]
    B --> C[Validacao do Ciclo 01]
    C --> D[Ciclo 02 - Gerador Procedural]
    D --> E[Validacao do Ciclo 02]
    E --> F[Ciclo 03 - Controller e Fluxo]
    F --> G[Validacao do Ciclo 03]
    G --> H[Ciclo 04 - Renderizacao Procedural Basica]
    H --> I[Validacao do Ciclo 04]
    I --> J[Ciclo 05 - Testes e Regressao]
    J --> K[Validacao do Ciclo 05]
    K --> L[Ciclo 06 - Build Android Real]
    L --> M[Validacao do Ciclo 06]
    M --> N[Base pronta para evolucoes futuras]
````

---

## 17.4 Interpretação dos Ciclos

### Waterfall V2 congelada

A execução caracol só começa depois que a base documental estiver fechada.

Isso significa:

* visão estabilizada;
* requisitos estabilizados;
* regras estabilizadas;
* arquitetura definida;
* modelo de dados definido;
* testes e build planejados.

Sem isso, o caracol vira improviso.

---

### Ciclo 01 - Domínio e Estado

Primeiro ciclo da implementação.

Escopo principal:

* `Tile`
* `GameStatus`
* `SelectionState`
* `GameState`
* `BoardConfiguration`
* `MatchResult`
* funções puras de regra

Objetivo:

* construir o núcleo lógico e estrutural do sistema;
* garantir base estável para tudo que vem depois.

---

### Validação do Ciclo 01

Após o primeiro ciclo, validar:

* integridade do modelo;
* regras puras;
* consistência do estado;
* aderência à documentação.

Sem isso, não se avança.

---

### Ciclo 02 - Gerador Procedural

Segundo ciclo da implementação.

Escopo principal:

* `BoardGenerationResult`
* generator
* distribuição de peças
* validação estrutural
* jogabilidade mínima inicial

Objetivo:

* fazer nascer um tabuleiro válido;
* permitir criação de sessão jogável.

---

### Validação do Ciclo 02

Após o segundo ciclo, validar:

* número par de peças;
* ausência de duplicidade de posição;
* coerência de pareamento;
* jogabilidade inicial;
* compatibilidade com o estado.

---

### Ciclo 03 - Controller e Fluxo

Terceiro ciclo da implementação.

Escopo principal:

* `InputEvent`
* resolução de toque
* primeira seleção
* segunda seleção
* match
* remoção
* transição de estado
* reinício

Objetivo:

* transformar os blocos anteriores em fluxo real de jogada.

---

### Validação do Ciclo 03

Após o terceiro ciclo, validar:

* sequência correta da jogada;
* integridade do estado após tentativa válida e inválida;
* vitória;
* bloqueio;
* reinício.

---

### Ciclo 04 - Renderização Procedural Básica

Quarto ciclo da implementação.

Escopo principal:

* desenho do tabuleiro;
* desenho de peças;
* destaque de seleção;
* neutralização visual da remoção;
* estados finais;
* legibilidade da sessão.

Objetivo:

* materializar visualmente a lógica já construída.

---

### Validação do Ciclo 04

Após o quarto ciclo, validar:

* coerência entre estado e render;
* legibilidade;
* clareza da seleção;
* clareza de vitória e bloqueio;
* ausência de contradição entre UI e domínio.

---

### Ciclo 05 - Testes e Regressão

Quinto ciclo da implementação.

Escopo principal:

* automação mínima de regras;
* integração essencial;
* regressão mínima obrigatória;
* cobertura crítica.

Objetivo:

* blindar o sistema contra retrocesso funcional.

---

### Validação do Ciclo 05

Após o quinto ciclo, validar:

* regressão mínima operacional;
* testes centrais aprovados;
* estabilidade razoável do fluxo principal.

---

### Ciclo 06 - Build Android Real

Sexto ciclo da implementação.

Escopo principal:

* build debug;
* build teste interno;
* empacotamento;
* instalação em dispositivo real;
* validação técnica.

Objetivo:

* sair do ambiente conceitual e entrar na execução real do produto.

---

### Validação do Ciclo 06

Após o sexto ciclo, validar:

* compilação;
* instalação;
* funcionamento do fluxo principal;
* responsividade;
* ausência de falha crítica aberta.

---

### Base pronta para evoluções futuras

Depois desses seis ciclos, o projeto passa a ter:

* núcleo jogável;
* arquitetura materializada;
* testes mínimos;
* build real;
* base pronta para novos incrementos.

A partir daí, ciclos futuros podem tratar:

* refinamento visual;
* UX avançada;
* estatísticas;
* pistas;
* modos adicionais;
* persistência local.

---

## 17.5 Lógica do Método Caracol

O caracol do VitahAcre deve obedecer à seguinte lógica:

1. não abrir múltiplos fronts sem necessidade;
2. não pular do domínio direto para acabamento visual;
3. não crescer sem validar;
4. não integrar sem proteger;
5. não avançar sem congelar parcialmente o ciclo anterior.

A disciplina do método é parte da arquitetura do projeto.

---

## 17.6 Regras Estruturais Implicadas pelo Diagrama

### RCC-01

A Waterfall vem antes do caracol.

### RCC-02

Cada ciclo precisa ter escopo pequeno e objetivo claro.

### RCC-03

Cada ciclo precisa terminar com validação.

### RCC-04

Nenhum bloco deve crescer sem teste proporcional.

### RCC-05

O projeto deve crescer do núcleo para a periferia.

### RCC-06

O fluxo real da implementação precisa respeitar a ordem lógica da arquitetura.

---

## 17.7 Falhas que Este Diagrama Ajuda a Evitar

Este diagrama protege contra:

* tentativa de fazer o jogo inteiro de uma vez;
* começar pela UI antes do domínio;
* crescer sem testes;
* misturar múltiplas responsabilidades cedo demais;
* caos de implementação;
* retrabalho estrutural desnecessário.

---

## 17.8 Papel Estratégico do Diagrama

Este é um dos diagramas mais importantes para a execução real do projeto.

Ele é importante porque:

* transforma a documentação em roadmap técnico;
* protege a implementação contra desordem;
* ajuda a manter o método caracol fiel ao projeto;
* organiza o tempo e a energia da construção;
* prepara a base para trabalho modular disciplinado.

Ele é especialmente importante para:

* implementação real;
* planejamento técnico;
* controle de avanço;
* revisão de prontidão de cada ciclo.

---

## 17.9 Compatibilidade com os Demais Documentos

Este diagrama depende e concretiza especialmente:

* `01 - Visao.md`
* `04 - Arquitetura.md`
* `08 - Implementacao_Tecnica.md`
* `09 - Plano_de_Testes.md`
* `11 - Build_Deploy.md`

Ele também pressupõe a estabilidade de toda a Waterfall V2.

---

## 17.10 Declaração Oficial

Este documento estabelece o Diagrama de Ciclos Caracol do projeto VitahAcre e deve ser lido como a representação oficial da ordem incremental de execução do sistema nesta fase do projeto.
