# 19 - Diagrama de Performance Budget

## 19.1 Objetivo
Este diagrama apresenta o orçamento oficial de performance do projeto VitahAcre, transformando metas não funcionais em limites técnicos visíveis e controláveis.

Ele serve para:
- formalizar metas mínimas de desempenho;
- orientar decisões de implementação;
- prevenir degradação silenciosa;
- apoiar testes de performance;
- conectar requisitos não funcionais com engenharia real.

---

## 19.2 Leitura do Diagrama
No VitahAcre, performance não deve ser tratada como ajuste tardio.

Desde a base do projeto, existem metas que precisam ser respeitadas para que a experiência continue:
- responsiva;
- leve;
- estável;
- jogável em ambiente Android alvo.

O diagrama abaixo representa os principais eixos de orçamento técnico do sistema.

---

## 19.3 Diagrama Mermaid

```mermaid
flowchart TD
    A[Performance Budget do VitahAcre] --> B[Tempo de Resposta ao Toque]
    A --> C[FPS em Gameplay]
    A --> D[Tempo de Inicializacao]
    A --> E[Consumo de Memoria]
    A --> F[Complexidade de Renderizacao]
    A --> G[Estabilidade Geral]

    B --> B1[Meta: feedback perceptivel ~ ate 50 ms]
    B --> B2[Risco: atraso de controle]
    B --> B3[Afeta: input, controller, render]

    C --> C1[Meta: ~ 60 FPS em gameplay normal]
    C --> C2[Risco: engasgo visual]
    C --> C3[Afeta: canvas, redraw, hit-testing]

    D --> D1[Meta: ~ ate 2 s em ambiente compativel]
    D --> D2[Risco: entrada lenta na experiencia]
    D --> D3[Afeta: bootstrap, generator, estado inicial]

    E --> E1[Meta: manter uso dentro do teto do projeto]
    E --> E2[Risco: travamento / encerramento]
    E --> E3[Afeta: listas de tiles, render, recomposicao]

    F --> F1[Meta: desenho simples e previsivel]
    F --> F2[Risco: custo excessivo por frame]
    F --> F3[Afeta: canvas, profundidade, destaque visual]

    G --> G1[Meta: sessao sem falha critica]
    G --> G2[Risco: crash, corrupcao ou congelamento]
    G --> G3[Afeta: sistema como um todo]
````

---

## 19.4 Interpretação dos Eixos de Performance

### Tempo de Resposta ao Toque

Este eixo mede o intervalo entre:

* o toque do jogador;
* o reconhecimento do input;
* o primeiro feedback perceptível do sistema.

### Meta de referência

* feedback perceptível em torno de até **50 ms**, quando tecnicamente compatível com o dispositivo-alvo.

### Problema que busca evitar

* sensação de atraso;
* perda de controle;
* dúvida sobre se o toque foi reconhecido.

### Componentes mais afetados

* `presentation/input`
* `presentation/controller`
* `presentation/canvas`

---

### FPS em Gameplay

Este eixo mede a fluidez visual da partida em andamento.

### Meta de referência

* aproximadamente **60 FPS** em gameplay normal, quando compatível com o dispositivo-alvo.

### Problema que busca evitar

* engasgo visual;
* stutter;
* animação irregular;
* perda de legibilidade dinâmica.

### Componentes mais afetados

* `presentation/canvas`
* renderização procedural;
* atualização do tabuleiro;
* feedback visual.

---

### Tempo de Inicialização

Este eixo mede quanto tempo o app leva para:

* abrir;
* preparar a sessão;
* gerar o tabuleiro;
* exibir a partida em estado jogável.

### Meta de referência

* aproximadamente **até 2 segundos** em ambiente compatível.

### Problema que busca evitar

* sensação de lentidão logo no início;
* demora excessiva até a primeira interação real.

### Componentes mais afetados

* bootstrap do app;
* generator;
* state manager;
* montagem inicial da UI.

---

### Consumo de Memória

Este eixo mede a pressão do jogo sobre os recursos do dispositivo.

### Meta de referência

* manter o consumo dentro do teto previsto pelo projeto e compatível com aparelhos Android alvo.

### Problema que busca evitar

* encerramento pelo sistema operacional;
* travamentos;
* degradação progressiva da sessão;
* vazamentos ou retenção excessiva de estado.

### Componentes mais afetados

* listas de `Tile`;
* estado da sessão;
* estruturas auxiliares;
* renderização e recomposição.

---

### Complexidade de Renderização

Este eixo mede o custo computacional do desenho visual da partida.

### Meta de referência

* renderização simples, previsível e compatível com gameplay contínuo.

### Problema que busca evitar

* custo excessivo por frame;
* overdraw;
* desenho desnecessariamente complexo;
* queda de desempenho causada por efeitos supérfluos.

### Componentes mais afetados

* `presentation/canvas`
* profundidade visual;
* destaques de seleção;
* efeitos de remoção;
* estados finais.

---

### Estabilidade Geral

Este eixo mede a capacidade do sistema de sustentar sessões jogáveis sem falhas críticas.

### Meta de referência

* sessão utilizável sem crash, congelamento ou corrupção de estado.

### Problema que busca evitar

* crash;
* inconsistência do estado;
* congelamento após jogadas;
* perda da sessão em fluxo normal.

### Componentes mais afetados

* sistema como um todo;
* integração entre camadas;
* atualização de estado;
* build real em dispositivo.

---

## 19.5 Tabela Resumida do Budget

| Eixo              | Meta de Referência        | Risco Principal           | Áreas Críticas            |
| ----------------- | ------------------------- | ------------------------- | ------------------------- |
| Resposta ao toque | ~ até 50 ms               | atraso de controle        | input, controller, render |
| FPS               | ~ 60 FPS                  | engasgo visual            | canvas, redraw            |
| Inicialização     | ~ até 2 s                 | entrada lenta             | app, generator, state     |
| Memória           | dentro do teto do projeto | travamento / encerramento | estado, tiles, render     |
| Renderização      | custo simples por frame   | frame pesado              | canvas, efeitos           |
| Estabilidade      | sem falha crítica         | crash / corrupção         | integração geral          |

---

## 19.6 Decisões Técnicas Implicadas pelo Budget

### RPB-01

A lógica de domínio deve permanecer fora da UI para não pesar a renderização.

### RPB-02

O canvas deve desenhar apenas o necessário.

### RPB-03

O hit-testing deve ser eficiente e previsível.

### RPB-04

O generator não deve produzir estruturas desnecessariamente pesadas.

### RPB-05

O estado da sessão deve ser claro e controlado, evitando retenções supérfluas.

### RPB-06

Toda otimização precisa preservar correção funcional.

---

## 19.7 Falhas que Este Diagrama Ajuda a Evitar

Este budget ajuda a evitar:

* input “pesado”;
* UI lenta e pouco responsiva;
* queda de FPS causada por render exagerado;
* tempo de boot excessivo;
* aumento descontrolado de consumo de memória;
* builds que funcionam logicamente, mas fracassam em experiência real.

---

## 19.8 Papel Estratégico do Diagrama

Este diagrama é importante porque:

* transforma requisitos não funcionais em governança visual;
* ajuda a equipe a saber onde o projeto não pode ultrapassar limites;
* apoia testes de performance;
* protege a experiência do usuário;
* evita que performance vire assunto apenas no fim do projeto.

Ele é especialmente importante para:

* `08 - Implementacao_Tecnica.md`
* `09 - Plano_de_Testes.md`
* `10 - Casos_de_Teste.md`
* `11 - Build_Deploy.md`

---

## 19.9 Compatibilidade com os Demais Documentos

Este diagrama concretiza visualmente o conteúdo dos seguintes documentos:

* `02 - Requisitos_SRS.md`
* `07 - UI_UX_Design.md`
* `08 - Implementacao_Tecnica.md`
* `09 - Plano_de_Testes.md`
* `10 - Casos_de_Teste.md`
* `11 - Build_Deploy.md`

Ele deve permanecer coerente com todos eles.

---

## 19.10 Declaração Oficial

Este documento estabelece o Diagrama de Performance Budget do projeto VitahAcre e deve ser lido como a representação oficial dos limites de desempenho esperados do sistema nesta fase do projeto.
