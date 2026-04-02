# 06 - Diagrama de Sequencia da Jogada

## 6.1 Objetivo
Este diagrama apresenta a sequência oficial de uma jogada no projeto VitahAcre, mostrando a ordem temporal das interações entre os principais componentes do sistema.

Ele serve para:
- formalizar o fluxo de uma jogada real;
- orientar a implementação do controller;
- mostrar a ordem das chamadas entre camadas;
- evitar inversões de responsabilidade;
- apoiar testes de integração e revisão técnica.

---

## 6.2 Leitura do Diagrama
A jogada no VitahAcre começa com a ação do jogador e atravessa o sistema de forma ordenada.

O fluxo mínimo esperado é:

- toque do jogador;
- captura do input;
- resolução da peça tocada;
- validação de elegibilidade;
- atualização da seleção;
- validação de match, quando houver duas peças;
- remoção de peças, quando o match for válido;
- verificação de vitória ou bloqueio;
- atualização do estado;
- renderização do novo quadro da sessão.

O diagrama abaixo mostra esse fluxo temporal entre os participantes centrais.

---

## 6.3 Diagrama Mermaid

```mermaid
sequenceDiagram
    actor J as Jogador
    participant I as Input Layer
    participant C as Controller
    participant U as Use Case
    participant R as Rules Engine
    participant S as State Manager
    participant G as GameState
    participant N as Renderer / Canvas

    J->>I: toca a tela
    I->>C: envia InputEvent
    C->>U: processa evento
    U->>C: solicita resolucao da jogada
    C->>S: consulta estado atual
    S->>G: fornece sessao atual
    C->>R: resolve peca tocada / valida elegibilidade
    R-->>C: resultado de elegibilidade

    alt toque invalido ou peca inelegivel
        C->>S: mantem estado coerente
        S->>N: renderiza sem alteracao estrutural relevante
        N-->>J: feedback visual coerente
    else primeira selecao valida
        C->>S: atualiza SelectionState
        S->>G: grava primeira selecao
        S->>N: renderiza destaque da selecao
        N-->>J: feedback visual da primeira peca
    else segunda selecao valida
        C->>S: atualiza SelectionState
        S->>G: grava segunda selecao
        C->>R: valida match
        R-->>C: MatchResult

        alt match invalido
            C->>S: resolve selecao sem remover pecas
            S->>G: preserva integridade da sessao
            S->>N: renderiza estado atualizado
            N-->>J: feedback de tentativa invalida
        else match valido
            C->>S: remove pecas do estado
            S->>G: marca pecas como removidas
            C->>R: verifica vitoria
            R-->>C: resultado de vitoria
            C->>R: verifica ausencia de jogadas
            R-->>C: resultado de bloqueio
            C->>S: define status final ou continuidade
            S->>N: renderiza nova situacao do tabuleiro
            N-->>J: feedback visual da jogada
        end
    end
````

---

## 6.4 Participantes do Fluxo

### Jogador

Origina a jogada por meio de toque na tela.

### Input Layer

Capta o toque e o traduz em evento processável.

### Controller

Coordena a jogada e decide a ordem das operações.

### Use Case

Organiza o fluxo de alto nível da ação.

### Rules Engine

Executa as validações formais do domínio:

* elegibilidade;
* match;
* vitória;
* bloqueio.

### State Manager

Controla a atualização do estado oficial da sessão.

### GameState

Representa a verdade atual da partida.

### Renderer / Canvas

Converte o estado resultante em feedback visual ao jogador.

---

## 6.5 Interpretação por Cenário

### Cenário 1 - Toque inválido

Quando o toque:

* ocorre em área vazia; ou
* atinge peça inelegível,

o sistema:

* não deve remover peças;
* não deve corromper a seleção;
* deve preservar integridade do estado;
* pode emitir feedback discreto.

---

### Cenário 2 - Primeira seleção válida

Quando o jogador toca uma peça elegível e não existe seleção anterior:

* a peça é registrada como primeira seleção;
* o estado muda para seleção parcial;
* a interface destaca a peça.

---

### Cenário 3 - Segunda seleção válida com match inválido

Quando o jogador escolhe uma segunda peça elegível, mas o par não é válido:

* o sistema não remove peças;
* o estado deve continuar coerente;
* a seleção deve ser resolvida conforme a política oficial do fluxo;
* a interface deve refletir a rejeição sem quebrar a sessão.

---

### Cenário 4 - Segunda seleção válida com match válido

Quando o par é válido:

* ambas as peças são removidas;
* o GameState é atualizado;
* o sistema verifica vitória;
* o sistema verifica bloqueio;
* o renderer apresenta a nova situação da sessão.

---

## 6.6 Ordem Crítica das Operações

A sequência da jogada deve respeitar a seguinte prioridade:

1. capturar o evento;
2. localizar ou resolver a peça alvo;
3. validar elegibilidade;
4. atualizar seleção;
5. validar match, quando houver duas seleções;
6. remover peças se o match for válido;
7. verificar vitória;
8. verificar ausência de jogadas;
9. atualizar status;
10. renderizar.

Essa ordem é essencial para previsibilidade e integridade.

---

## 6.7 Restrições Visíveis no Diagrama

Este diagrama implica as seguintes restrições:

### RSJ-01

O Renderer não valida regras.

### RSJ-02

A Input Layer não decide match.

### RSJ-03

O Controller coordena, mas não substitui o Rules Engine.

### RSJ-04

O GameState é atualizado antes da nova renderização.

### RSJ-05

A verificação de vitória e bloqueio ocorre após processamento do par.

### RSJ-06

Toque inválido não pode quebrar a sessão.

---

## 6.8 Papel Estratégico do Diagrama

Este é um dos diagramas mais úteis para a implementação, porque:

* traduz arquitetura em ordem operacional;
* ajuda a construir o controller corretamente;
* mostra como domínio e estado se encadeiam;
* facilita testes de integração;
* reduz ambiguidade no fluxo da jogada.

Ele é especialmente importante para:

* `presentation/controller`
* `domain/usecase`
* `domain/rules`
* `domain/state`
* testes de jogada ponta a ponta

---

## 6.9 Compatibilidade com os Demais Documentos

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

## 6.10 Declaração Oficial

Este documento estabelece o Diagrama de Sequência da Jogada do projeto VitahAcre e deve ser lido como a representação temporal oficial do fluxo de uma jogada nesta fase do projeto.
