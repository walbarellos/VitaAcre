Lista oficial de diagramas para a pasta:

`/home/walbarellos/VitaGame/2 - Diagrama/`

1. `01 - Diagrama_de_Contexto_do_Sistema.md`
2. `02 - Diagrama_de_Arquitetura_em_Camadas.md`
3. `03 - Diagrama_de_Componentes.md`
4. `04 - Diagrama_de_Entidades_do_Dominio.md`
5. `05 - Diagrama_de_Maquina_de_Estados_da_Partida.md`
6. `06 - Diagrama_de_Sequencia_da_Jogada.md`
7. `07 - Diagrama_de_Sequencia_do_Reinicio.md`
8. `08 - Diagrama_de_Fluxo_do_Gerador_Procedural.md`
9. `09 - Diagrama_de_Fluxo_de_Testes.md`
10. `10 - Diagrama_de_Pacotes.md`
11. `11 - Diagrama_de_Fluxo_de_Input_Mapping.md`
12. `12 - Diagrama_de_Regras_de_Elegibilidade.md`
13. `13 - Diagrama_de_Match_Decision_Flow.md`
14. `14 - Diagrama_de_Jornada_da_Partida.md`
15. `15 - Diagrama_de_Rastreabilidade.md`
16. `16 - Diagrama_de_Dependencias_Permitidas_vs_Proibidas.md`
17. `17 - Diagrama_de_Ciclos_Caracol.md`
18. `18 - Diagrama_de_Build_Pipeline.md`
19. `19 - Diagrama_de_Performance_Budget.md`

Primeiro arquivo:

**`01 - Diagrama_de_Contexto_do_Sistema.md`**

````md
# 01 - Diagrama de Contexto do Sistema

## 1.1 Objetivo
Este diagrama apresenta a visão mais externa do sistema VitahAcre, delimitando suas fronteiras, seus atores principais, suas entradas, suas saídas e seus subsistemas centrais.

Ele serve para:
- mostrar o jogo como sistema único;
- deixar claro o que está dentro e o que está fora do escopo;
- organizar a visão macro do projeto;
- preparar a leitura dos diagramas seguintes.

---

## 1.2 Leitura do Diagrama
O ator principal é o **Jogador**, que interage com o **App VitahAcre** por meio de toque em tela.

O App VitahAcre contém, em nível macro:
- captura de input;
- controle da partida;
- regras do domínio;
- estado da partida;
- geração procedural;
- renderização.

Há ainda a possibilidade de expansão futura para armazenamento local de preferências, estatísticas ou progresso, sem alterar o núcleo base do jogo.

---

## 1.3 Diagrama Mermaid

```mermaid
flowchart LR
    J[Jogador]

    subgraph V[VitahAcre App Android]
        I[Camada de Input]
        C[Controller da Partida]
        R[Rules Engine]
        S[Game State]
        G[Gerador Procedural]
        N[Renderizacao / UI]
    end

    L[(Armazenamento Local Futuro)]

    J -->|toque / comando| I
    I --> C
    C --> R
    R --> C
    C --> S
    G --> S
    S --> N
    C --> G
    N -->|feedback visual| J
    S -. opcional futuro .-> L
    L -. opcional futuro .-> S
````

---

## 1.4 Interpretação Estrutural

### Jogador

É o ator externo principal do sistema.

### Camada de Input

Recebe toques e comandos vindos do jogador.

### Controller da Partida

Coordena o fluxo da jogada, reinício e transições principais.

### Rules Engine

Executa as validações formais do domínio:

* elegibilidade;
* match;
* vitória;
* bloqueio.

### Game State

É a fonte única da verdade da sessão atual.

### Gerador Procedural

Cria o tabuleiro inicial e fornece estrutura válida para a partida.

### Renderização / UI

Converte o estado atual da partida em interface visível e jogável.

### Armazenamento Local Futuro

Bloco opcional de expansão para configurações, estatísticas ou dados persistentes.

---

## 1.5 Fronteira Oficial do Sistema

Está dentro do sistema:

* input;
* controller;
* regras;
* estado;
* geração;
* renderização.

Está fora do sistema:

* jogador;
* ambiente físico do dispositivo;
* backend remoto;
* serviços externos online.

---

## 1.6 Observações de Projeto

Este diagrama não detalha dependências internas finas nem fluxo temporal completo.
Sua função é contextual, não operacional.

Os próximos diagramas detalharão:

* arquitetura em camadas;
* componentes;
* entidades;
* estados;
* sequências;
* fluxos lógicos e técnicos.

---

## 1.7 Declaração Oficial

Este documento estabelece o Diagrama de Contexto do Sistema do projeto VitahAcre e deve ser lido como a representação macro oficial da fronteira do sistema nesta fase do projeto.
