# 14 - Diagrama de Jornada da Partida

## 14.1 Objetivo
Este diagrama apresenta a jornada oficial da partida no projeto VitahAcre, conectando a experiência do jogador com o fluxo funcional do sistema.

Ele serve para:
- unir game design, UX e comportamento funcional;
- mostrar a progressão natural da sessão;
- representar a experiência da partida do ponto de vista do uso;
- apoiar testes funcionais e decisões de interface;
- reforçar a coerência entre GDD, SRS e regras do domínio.

---

## 14.2 Leitura do Diagrama
A partida do VitahAcre pode ser compreendida como uma jornada composta por ciclos de:

- início da sessão;
- leitura do tabuleiro;
- tentativa de seleção;
- formação ou falha de pareamento;
- reorganização mental do espaço;
- continuidade até resolução ou bloqueio;
- reinício quando necessário.

O diagrama abaixo representa essa jornada como fluxo de experiência e decisão.

---

## 14.3 Diagrama Mermaid

```mermaid
flowchart TD
    A[Iniciar partida] --> B[Tabuleiro gerado e exibido]
    B --> C[Observar estrutura do tabuleiro]
    C --> D[Identificar peca potencialmente livre]
    D --> E[Selecionar primeira peca]

    E --> F{Primeira peca e valida?}

    F -- nao --> C
    F -- sim --> G[Buscar segunda peca compativel]
    G --> H[Selecionar segunda peca]

    H --> I{Segunda peca e valida?}

    I -- nao --> G
    I -- sim --> J{As duas formam match valido?}

    J -- nao --> K[Feedback de tentativa invalida]
    K --> C

    J -- sim --> L[Remover par do tabuleiro]
    L --> M[Atualizar leitura espacial]
    M --> N{Todas as pecas foram removidas?}

    N -- sim --> O[Vitoria]
    N -- nao --> P{Ainda existe jogada valida?}

    P -- sim --> C
    P -- nao --> Q[Sem jogadas / bloqueio]

    O --> R[Reiniciar nova partida]
    Q --> R
    R --> B
````

---

## 14.4 Interpretação da Jornada

### Iniciar partida

A sessão começa quando o sistema cria um novo tabuleiro válido e o apresenta ao jogador.

Neste momento:

* a sessão está pronta;
* o tabuleiro já deve ser jogável;
* a experiência começa imediatamente.

---

### Observar estrutura do tabuleiro

Antes de agir, o jogador precisa ler o espaço.

Essa etapa envolve:

* perceber camadas;
* perceber bloqueios;
* localizar peças expostas;
* antecipar possíveis pares.

É uma etapa central da experiência, não um simples intervalo entre ações.

---

### Identificar peça potencialmente livre

O jogador passa da observação geral para a hipótese concreta de jogada.

Ele tenta localizar:

* uma peça sem bloqueio superior;
* com pelo menos um lado livre;
* com chance de formar par posteriormente.

---

### Selecionar primeira peça

A primeira seleção é a entrada ativa na jogada.

Se a peça for inelegível:

* o sistema rejeita;
* a experiência retorna à observação e tentativa.

Se for válida:

* a jornada avança para a busca do par.

---

### Buscar segunda peça compatível

Com a primeira peça registrada, o jogador precisa:

* identificar outra peça elegível;
* verificar compatibilidade lógica;
* avaliar se o pareamento é possível naquele momento.

---

### Selecionar segunda peça

A segunda seleção leva a partida ao ponto decisivo.

A partir daí, o sistema precisa responder:

* se a peça é válida;
* se o par é compatível;
* se haverá avanço real da sessão.

---

### Feedback de tentativa inválida

Quando o par não é válido:

* não há remoção;
* a sessão continua;
* o jogador aprende algo sobre a estrutura atual;
* a jornada retorna à leitura do tabuleiro.

Esse retorno é parte natural da experiência.

---

### Remover par do tabuleiro

Quando o par é válido:

* as duas peças são removidas;
* o espaço do tabuleiro muda;
* novas possibilidades podem surgir;
* a leitura do jogador precisa ser refeita.

A remoção é o principal mecanismo de transformação da sessão.

---

### Atualizar leitura espacial

Cada remoção muda a configuração do problema.

O jogador precisa reinterpretar:

* o que foi liberado;
* o que continua bloqueado;
* quais pares passaram a ser possíveis;
* que ordem de remoção se tornou mais promissora.

---

### Verificar vitória

Se todas as peças tiverem sido removidas:

* a jornada termina em resolução completa;
* a sessão alcança seu objetivo.

---

### Verificar continuidade

Se ainda restarem peças, o sistema precisa decidir:

* ainda há jogadas válidas?
* ou a sessão entrou em impasse?

---

### Vitória

A vitória é o encerramento bem-sucedido da jornada.

Ela representa:

* resolução da estrutura;
* esvaziamento do tabuleiro;
* domínio suficiente da ordem de remoção.

---

### Sem jogadas / bloqueio

Quando existem peças remanescentes, mas nenhum par válido:

* a sessão entra em impasse;
* a jornada não continua naturalmente;
* o reinício passa a ser a saída funcional.

---

### Reiniciar nova partida

Após vitória ou bloqueio:

* o jogador pode iniciar nova sessão;
* a jornada recomeça em novo tabuleiro;
* a estrutura do problema é renovada.

---

## 14.5 Lógica Experiencial Representada

Este diagrama expressa que a experiência do VitahAcre não é linear simples.

Ela é um ciclo contínuo entre:

* leitura;
* tentativa;
* confirmação ou rejeição;
* reinterpretação do espaço.

A partida é resolvida como problema lógico iterativo.

---

## 14.6 Regras de Experiência Implicadas pelo Diagrama

### RJP-01

A jornada gira em torno do tabuleiro como problema lógico.

### RJP-02

Toda jogada começa por leitura espacial.

### RJP-03

Tentativas inválidas fazem parte natural da experiência.

### RJP-04

Remoções alteram a estrutura e exigem nova leitura.

### RJP-05

Vitória e bloqueio são finais distintos e não podem ser confundidos.

### RJP-06

O reinício abre nova jornada, não continua a anterior.

---

## 14.7 Falhas de Experiência que Este Diagrama Ajuda a Evitar

Este fluxo ajuda a evitar:

* UX que ignore a importância da leitura do tabuleiro;
* feedback confuso entre erro e sucesso;
* sessão percebida como arbitrária;
* vitória mal comunicada;
* bloqueio tratado como simples atraso;
* reinício tratado como extensão da sessão anterior.

---

## 14.8 Papel Estratégico do Diagrama

Este diagrama é importante porque:

* conecta experiência real do jogador com a lógica do sistema;
* traduz o GDD em fluxo visual de uso;
* ajuda a alinhar interface, feedback e funcionalidade;
* serve como ponte entre domínio e UX.

Ele é especialmente importante para:

* GDD;
* UI/UX;
* testes funcionais;
* validação do fluxo da sessão.

---

## 14.9 Compatibilidade com os Demais Documentos

Este diagrama concretiza visualmente o conteúdo dos seguintes documentos:

* `02 - Requisitos_SRS.md`
* `03 - Regras_de_Negocio.md`
* `06 - Game_Design_Document_GDD.md`
* `07 - UI_UX_Design.md`
* `09 - Plano_de_Testes.md`
* `10 - Casos_de_Teste.md`
* `12 - Manual_do_Usuario.md`

Ele deve permanecer coerente com todos eles.

---

## 14.10 Declaração Oficial

Este documento estabelece o Diagrama de Jornada da Partida do projeto VitahAcre e deve ser lido como a representação oficial da progressão experiencial da sessão nesta fase do projeto.
