````md id="16p4x7"
# 16 - Diagrama de Dependencias Permitidas vs Proibidas

## 16.1 Objetivo
Este diagrama apresenta, de forma explícita, as dependências permitidas e proibidas no projeto VitahAcre.

Ele serve para:
- blindar a arquitetura contra acoplamento incorreto;
- mostrar o sentido correto das dependências;
- impedir violações estruturais silenciosas;
- orientar revisão de código;
- apoiar implementação modular em estilo LEGO.

---

## 16.2 Leitura do Diagrama
No VitahAcre, nem toda camada pode depender de qualquer outra.

A arquitetura oficial define:
- setas permitidas;
- setas proibidas;
- fronteiras claras entre domínio, apresentação, dados e suporte.

Esse diagrama é essencial porque muitos problemas de arquitetura começam não na lógica do jogo, mas em dependências erradas entre módulos.

---

## 16.3 Diagrama Mermaid — Dependências Permitidas

```mermaid
flowchart LR
    subgraph P[Presentation]
        P1[input]
        P2[controller]
        P3[screen]
        P4[component]
        P5[canvas]
    end

    subgraph D[Domain]
        D1[model]
        D2[rules]
        D3[state]
        D4[usecase]
    end

    subgraph DA[Data]
        DA1[generator]
        DA2[repository]
    end

    subgraph U[Util]
        U1[util]
    end

    P1 --> P2
    P2 --> D4
    P2 --> D3
    P2 --> DA1

    P3 --> P4
    P3 --> P5
    P3 --> D3
    P4 --> D3
    P5 --> D3

    D4 --> D2
    D4 --> D3
    D4 --> D1

    D2 --> D1
    D3 --> D1

    DA1 --> D1
    DA1 --> D3
    DA2 --> D1

    U1 -. suporte .-> P1
    U1 -. suporte .-> P2
    U1 -. suporte .-> D2
    U1 -. suporte .-> DA1
    U1 -. suporte .-> P5
````

---

## 16.4 Diagrama Mermaid — Dependências Proibidas

```mermaid
flowchart LR
    subgraph P[Presentation]
        P1[input]
        P2[controller]
        P3[screen]
        P4[component]
        P5[canvas]
    end

    subgraph D[Domain]
        D1[model]
        D2[rules]
        D3[state]
        D4[usecase]
    end

    subgraph DA[Data]
        DA1[generator]
        DA2[repository]
    end

    P5 -. proibido .-> D2
    P1 -. proibido .-> D3
    P3 -. proibido .-> D2
    P4 -. proibido .-> D2

    D2 -. proibido .-> P5
    D2 -. proibido .-> P3
    D3 -. proibido .-> P5

    DA1 -. proibido .-> P5
    DA1 -. proibido .-> P3
    DA1 -. proibido .-> D2

    DA2 -. proibido .-> P2

    D1 -. proibido .-> P5
    D1 -. proibido .-> P3
```

---

## 16.5 Interpretação das Dependências Permitidas

### input → controller

Permitido porque a captura de toque deve encaminhar eventos ao coordenador da jogada.

### controller → usecase

Permitido porque o controller coordena operações de maior nível por meio de casos de uso.

### controller → state

Permitido porque o controller precisa atualizar e consultar o estado da partida.

### controller → generator

Permitido porque o reinício e a geração inicial dependem da construção de novo tabuleiro.

### screen → component

Permitido porque a tela compõe elementos visuais reutilizáveis.

### screen → canvas

Permitido porque a tela utiliza o canvas como parte da experiência visual principal.

### screen / component / canvas → state

Permitido porque a camada visual pode observar o estado oficial da sessão.

### usecase → rules / state / model

Permitido porque os casos de uso compõem lógica e estado sem se confundirem com a UI.

### rules → model

Permitido porque as regras operam sobre entidades do domínio.

### state → model

Permitido porque o estado é composto pelas estruturas do domínio.

### generator → model / state

Permitido porque o gerador cria peças e alimenta o estado inicial.

### repository → model

Permitido em cenário de persistência futura, desde que o repositório respeite o núcleo semântico.

### util → múltiplas camadas

Permitido apenas como suporte técnico transversal, sem sequestrar responsabilidade semântica.

---

## 16.6 Interpretação das Dependências Proibidas

### canvas → rules

Proibido porque a renderização não decide elegibilidade, match, vitória ou bloqueio.

### input → state

Proibido porque input não pode mutar diretamente a sessão.

### screen / component → rules

Proibido porque composição visual não deve executar lógica de domínio.

### rules → presentation

Proibido porque o domínio não pode depender de UI, Canvas, tela ou componentes.

### state → canvas

Proibido como dependência de implementação. O canvas pode ler o estado, mas o estado não conhece o canvas.

### generator → presentation

Proibido porque o gerador não desenha interface nem conhece layout visual.

### generator → rules

Proibido como dependência estrutural direta de jogabilidade corrente. O gerador deve construir estrutura, não virar engine da partida em andamento.

### repository → controller

Proibido porque persistência futura não deve controlar fluxo da sessão.

### model → presentation

Proibido porque as entidades do domínio não carregam dependência visual.

---

## 16.7 Regras Arquiteturais Consolidadas

### RDP-01

A camada de apresentação pode observar o estado, mas não substituir as regras do domínio.

### RDP-02

A camada de domínio não pode depender da apresentação.

### RDP-03

A camada de dados não pode sequestrar a lógica da jogabilidade em andamento.

### RDP-04

O controller coordena o fluxo, mas continua respeitando o domínio e o estado.

### RDP-05

O modelo do domínio é o núcleo mais estável e não deve depender da UI.

### RDP-06

Utilitários não podem virar atalho para quebrar a arquitetura.

---

## 16.8 Falhas que Este Diagrama Ajuda a Evitar

Este diagrama protege contra:

* regra implementada no Canvas;
* input mutando estado diretamente;
* uso de componentes visuais como engine do jogo;
* domínio contaminado por Compose ou Canvas;
* generator virando controlador da sessão;
* modelos carregando dependências indevidas de interface.

---

## 16.9 Papel Estratégico do Diagrama

Este é um diagrama de blindagem arquitetural.

Ele é especialmente importante porque:

* traduz a arquitetura em política concreta de dependências;
* facilita code review;
* ajuda a manter o projeto limpo durante o crescimento;
* reduz risco de spaghetti;
* orienta agente e desenvolvedor sobre “o que não pode”.

Ele é um dos diagramas mais importantes de todo o pacote.

---

## 16.10 Uso Prático em Implementação

Durante a implementação, este diagrama deve ser usado como checklist arquitetural.

Perguntas úteis:

* este pacote deveria mesmo importar aquele?
* esta classe está puxando UI para dentro do domínio?
* esta função de regra foi parar no Canvas?
* o generator está conhecendo demais a apresentação?
* o model está ficando contaminado por detalhes visuais?

Se alguma dessas respostas for “sim”, há forte chance de violação arquitetural.

---

## 16.11 Compatibilidade com os Demais Documentos

Este diagrama concretiza visualmente o conteúdo dos seguintes documentos:

* `04 - Arquitetura.md`
* `08 - Implementacao_Tecnica.md`
* `10 - Diagrama_de_Pacotes.md`
* `03 - Regras_de_Negocio.md`

Ele também se apoia em:

* `02 - Requisitos_SRS.md`
* `05 - Modelo_de_Dados.md`

---

## 16.12 Declaração Oficial
file://VitaGame/2 - Diagrama/mermaid-diagram.png
Este documento estabelece o Diagrama de Dependências Permitidas vs Proibidas do projeto VitahAcre e deve ser lido como a política oficial de dependências do sistema nesta fase do projeto.
