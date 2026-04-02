# 05 - Diagrama de Maquina de Estados da Partida

## 5.1 Objetivo
Este diagrama apresenta a máquina de estados oficial da partida do projeto VitahAcre.

Ele serve para:
- formalizar os estados possíveis da sessão;
- mostrar as transições válidas entre estados;
- impedir estados ilegais;
- orientar a implementação do controller;
- apoiar os testes de fluxo e integridade da partida.

---

## 5.2 Leitura do Diagrama
A partida do VitahAcre não é tratada como uma sequência solta de eventos, mas como uma sessão formal com estados definidos.

Os estados mínimos oficiais são:
- `INICIALIZANDO`
- `PRONTO`
- `SELECIONANDO_1`
- `SELECIONANDO_2`
- `PROCESSANDO_MATCH`
- `VITORIA`
- `SEM_JOGADAS`

Cada transição deve obedecer ao fluxo lógico do jogo e ao resultado das validações do domínio.

---

## 5.3 Diagrama Mermaid

```mermaid
stateDiagram-v2
    [*] --> INICIALIZANDO
    INICIALIZANDO --> PRONTO: tabuleiro valido gerado

    PRONTO --> SELECIONANDO_1: primeira peca elegivel selecionada
    PRONTO --> PRONTO: toque invalido / area vazia / peca inelegivel

    SELECIONANDO_1 --> SELECIONANDO_2: segunda peca elegivel selecionada
    SELECIONANDO_1 --> SELECIONANDO_1: toque invalido / area vazia / peca inelegivel
    SELECIONANDO_1 --> PRONTO: selecao resolvida / cancelada

    SELECIONANDO_2 --> PROCESSANDO_MATCH: iniciar avaliacao do par

    PROCESSANDO_MATCH --> PRONTO: match invalido e partida continua
    PROCESSANDO_MATCH --> PRONTO: match valido sem estado terminal
    PROCESSANDO_MATCH --> VITORIA: todas as pecas removidas
    PROCESSANDO_MATCH --> SEM_JOGADAS: sem pares validos restantes

    VITORIA --> PRONTO: reinicio da partida
    SEM_JOGADAS --> PRONTO: reinicio da partida
````

---

## 5.4 Interpretação dos Estados

### INICIALIZANDO

Estado de preparação da sessão.

Neste estado o sistema:

* prepara a estrutura da partida;
* gera ou monta o tabuleiro;
* constrói o estado inicial.

A sessão ainda não está disponível para jogada normal.

---

### PRONTO

Estado base da partida jogável.

Neste estado:

* o tabuleiro está ativo;
* não há duas peças em processamento;
* o sistema aguarda ação válida do jogador;
* a sessão pode seguir para primeira seleção.

---

### SELECIONANDO_1

Estado em que a primeira peça válida já foi registrada.

Neste estado:

* a seleção parcial existe;
* o sistema aguarda a segunda seleção válida;
* toques inválidos não devem corromper a sessão.

---

### SELECIONANDO_2

Estado transitório em que duas peças já foram registradas para comparação.

Neste estado:

* o sistema já possui um par candidato;
* a próxima etapa lógica é a validação formal do match.

---

### PROCESSANDO_MATCH

Estado de avaliação do par selecionado.

Neste estado:

* o sistema verifica se o par é válido;
* decide se remove ou não remove peças;
* resolve a seleção;
* verifica vitória;
* verifica ausência de jogadas.

---

### VITORIA

Estado terminal de sucesso.

Neste estado:

* não existem peças ativas restantes;
* a sessão foi resolvida;
* o jogador pode reiniciar.

---

### SEM_JOGADAS

Estado terminal de impasse.

Neste estado:

* ainda existem peças;
* não há pares válidos disponíveis;
* a sessão não pode continuar logicamente;
* o jogador pode reiniciar.

---

## 5.5 Regras de Transição

### TR-01

A sessão só pode sair de `INICIALIZANDO` para `PRONTO` quando houver tabuleiro válido.

### TR-02

A sessão só pode sair de `PRONTO` para `SELECIONANDO_1` após seleção válida da primeira peça.

### TR-03

A sessão só pode sair de `SELECIONANDO_1` para `SELECIONANDO_2` após seleção válida da segunda peça.

### TR-04

`SELECIONANDO_2` deve evoluir para `PROCESSANDO_MATCH`.

### TR-05

`PROCESSANDO_MATCH` só pode sair para:

* `PRONTO`
* `VITORIA`
* `SEM_JOGADAS`

### TR-06

`VITORIA` e `SEM_JOGADAS` só retornam ao fluxo jogável por reinício.

---

## 5.6 Transições Inválidas

As seguintes transições devem ser consideradas inválidas:

* `INICIALIZANDO` → `VITORIA`
* `INICIALIZANDO` → `SEM_JOGADAS`
* `PRONTO` → `PROCESSANDO_MATCH` sem seleção prévia
* `PRONTO` → `SELECIONANDO_2` diretamente
* `SELECIONANDO_1` → `VITORIA` diretamente
* `SELECIONANDO_1` → `SEM_JOGADAS` diretamente
* `VITORIA` → `SELECIONANDO_1` sem reinício
* `SEM_JOGADAS` → `SELECIONANDO_1` sem reinício

---

## 5.7 Papel Estratégico do Diagrama

Este diagrama é um dos mais importantes do projeto porque:

* trava o comportamento oficial da sessão;
* impede estados ambíguos;
* ajuda a implementar o controller corretamente;
* facilita testes de transição;
* evita bugs de fluxo.

Ele é especialmente importante para:

* controller;
* state manager;
* testes funcionais;
* testes de regressão.

---

## 5.8 Compatibilidade com os Demais Documentos

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

## 5.9 Declaração Oficial

Este documento estabelece o Diagrama de Máquina de Estados da Partida do projeto VitahAcre e deve ser lido como a representação oficial dos estados formais da sessão nesta fase do projeto.
