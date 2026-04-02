# 11 - Diagrama de Fluxo de Input Mapping

## 11.1 Objetivo
Este diagrama apresenta o fluxo oficial de mapeamento do input no projeto VitahAcre, mostrando como um toque bruto na tela é convertido em uma decisão utilizável pelo sistema.

Ele serve para:
- formalizar a transformação de toque em ação lógica;
- orientar a implementação do hit-testing;
- reduzir bugs de seleção;
- organizar a passagem entre coordenada física e coordenada lógica;
- apoiar testes de input e controller.

---

## 11.2 Leitura do Diagrama
No VitahAcre, um toque não vira automaticamente uma peça selecionada.

Entre o gesto do usuário e a ação no domínio, existe um processo de interpretação que envolve:
- captura das coordenadas do toque;
- normalização da entrada;
- identificação da região tocada;
- resolução da peça candidata;
- validação de elegibilidade;
- entrega do resultado ao controller.

O diagrama abaixo representa esse fluxo.

---

## 11.3 Diagrama Mermaid

```mermaid
flowchart TD
    A[Toque do jogador na tela] --> B[Capturar coordenadas fisicas x y]
    B --> C[Normalizar InputEvent]
    C --> D[Mapear toque para espaco do tabuleiro]
    D --> E{Toque esta dentro da area jogavel?}

    E -- nao --> F[Retornar area vazia / sem alvo]
    E -- sim --> G[Resolver tiles candidatas na regiao]
    G --> H{Existe alguma tile candidata?}

    H -- nao --> F
    H -- sim --> I[Aplicar regra de prioridade visual]
    I --> J[Selecionar tile alvo]
    J --> K{Tile alvo existe no estado atual?}

    K -- nao --> F
    K -- sim --> L[Entregar tile ao controller]
    L --> M[Controller solicita validacao de elegibilidade]
    M --> N{Tile elegivel?}

    N -- nao --> O[Retornar toque invalido em peca inelegivel]
    N -- sim --> P[Prosseguir com fluxo de selecao]

    F --> Q[Estado permanece coerente]
    O --> Q
    P --> R[Atualizar selecao / continuar jogada]
````

---

## 11.4 Interpretação das Etapas

### Toque do jogador na tela

O fluxo começa com a ação física do usuário sobre a tela do dispositivo.

Esse toque é um evento bruto, ainda sem significado no domínio do jogo.

---

### Capturar coordenadas físicas x y

A camada de input deve capturar:

* posição horizontal;
* posição vertical;
* contexto básico do gesto, quando necessário.

Neste estágio ainda se trabalha em espaço de tela.

---

### Normalizar InputEvent

O sistema converte o evento bruto em uma estrutura formal, como `InputEvent`.

Essa estrutura deve ser compatível com a arquitetura do sistema.

---

### Mapear toque para espaço do tabuleiro

A coordenada física precisa ser interpretada dentro da área jogável do tabuleiro.

Essa etapa pode envolver:

* offset da área do tabuleiro;
* escala;
* densidade;
* adaptação à tela;
* transformação para coordenadas lógicas do jogo.

---

### Verificar se o toque está na área jogável

Nem todo ponto da tela representa uma ação válida no tabuleiro.

Se o toque cair fora da área jogável:

* o sistema não deve tentar inventar uma peça alvo.

---

### Resolver tiles candidatas na região

Se o toque caiu na área jogável, o sistema procura quais peças podem estar naquela região.

Essa etapa é importante porque:

* pode haver sobreposição visual;
* pode haver múltiplas peças próximas;
* camadas podem influenciar a decisão.

---

### Verificar se existe tile candidata

Se nenhuma peça corresponder à região tocada:

* o sistema deve tratar como área vazia.

---

### Aplicar regra de prioridade visual

Quando existir mais de uma peça candidata, o sistema precisa aplicar critério consistente para escolher o alvo.

Em geral, a prioridade deve favorecer:

* a peça visualmente superior;
* a peça que realmente aparece no topo da pilha naquela projeção visual.

---

### Selecionar tile alvo

Depois da resolução, o sistema define a peça candidata final do toque.

Essa peça ainda não está automaticamente validada para seleção.

---

### Verificar se a tile existe no estado atual

Mesmo que a resolução espacial aponte uma peça, ela precisa existir de forma válida no GameState atual.

Isso evita:

* referências fantasmas;
* inconsistência com remoção anterior;
* toque sobre peça inexistente na sessão atual.

---

### Entregar tile ao controller

Uma vez resolvida, a tile é entregue ao controller como candidata à ação.

A partir daqui, a lógica deixa de ser puramente geométrica e entra no domínio da jogada.

---

### Controller solicita validação de elegibilidade

O controller não decide sozinho se a peça pode ser usada.

Ele solicita às regras do domínio a validação de:

* remoção;
* bloqueio superior;
* bloqueio lateral;
* elegibilidade formal.

---

### Verificar se a tile é elegível

Se a peça:

* estiver removida;
* estiver bloqueada;
* ou falhar em qualquer critério do domínio,

então o toque não deve avançar como seleção válida.

---

### Retornos possíveis do fluxo

O fluxo de input mapping pode terminar em três resultados principais:

1. área vazia / sem alvo;
2. peça existente, porém inelegível;
3. peça elegível, pronta para seguir no fluxo de seleção.

---

## 11.5 Regras Estruturais Implicadas pelo Diagrama

### RIM-01

Toque fora da área jogável não pode gerar peça alvo.

### RIM-02

Toque em região sem peça não pode inventar seleção.

### RIM-03

Se houver múltiplas candidatas, deve existir regra consistente de prioridade visual.

### RIM-04

A resolução geométrica não substitui a validação lógica do domínio.

### RIM-05

Peça resolvida espacialmente ainda precisa ser confirmada no estado atual.

### RIM-06

Input mapping termina entregando uma peça candidata ou um resultado de ausência de alvo.

---

## 11.6 Falhas que Este Diagrama Ajuda a Evitar

Este fluxo protege contra:

* toque acertando peça errada;
* toque em área vazia sendo tratado como seleção;
* seleção de peça visualmente oculta por outra;
* inconsistência entre coordenada de tela e lógica do tabuleiro;
* mistura de hit-testing com regra de domínio;
* bugs de toque em diferentes tamanhos de tela.

---

## 11.7 Papel Estratégico do Diagrama

Este diagrama é muito importante porque:

* conecta UI com domínio;
* organiza a lógica de hit-testing;
* ajuda a implementar Canvas corretamente;
* reduz bugs perceptivos de toque;
* prepara testes de input mapping.

Ele é especialmente importante para:

* `presentation/input`
* `presentation/controller`
* `presentation/canvas`
* utilitários geométricos
* testes de toque

---

## 11.8 Compatibilidade com os Demais Documentos

Este diagrama concretiza visualmente o conteúdo dos seguintes documentos:

* `04 - Arquitetura.md`
* `05 - Modelo_de_Dados.md`
* `07 - UI_UX_Design.md`
* `08 - Implementacao_Tecnica.md`
* `09 - Plano_de_Testes.md`
* `10 - Casos_de_Teste.md`

Ele deve permanecer coerente com todos eles.

---

## 11.9 Declaração Oficial

Este documento estabelece o Diagrama de Fluxo de Input Mapping do projeto VitahAcre e deve ser lido como a representação oficial da transformação de toque em alvo lógico nesta fase do projeto.
