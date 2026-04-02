# 12 - Diagrama de Regras de Elegibilidade

## 12.1 Objetivo
Este diagrama apresenta a lógica oficial de elegibilidade de uma peça no projeto VitahAcre, mostrando como o sistema decide se uma `Tile` pode ou não ser selecionada.

Ele serve para:
- formalizar a regra mais sensível do jogo;
- orientar a implementação da função de elegibilidade;
- apoiar a implementação de `isTileFree` e `canSelectTile`;
- reduzir ambiguidade entre regras, testes e código;
- evitar seleção indevida de peças bloqueadas ou removidas.

---

## 12.2 Leitura do Diagrama
No VitahAcre, uma peça não pode ser escolhida apenas porque foi tocada.

Antes de permitir a seleção, o sistema precisa verificar uma cadeia de condições lógicas, em ordem:

- a peça existe?
- a peça está ativa?
- a peça está bloqueada superiormente?
- a peça está bloqueada nos dois lados?
- então ela é elegível?

O diagrama abaixo representa essa árvore de decisão.

---

## 12.3 Diagrama Mermaid

```mermaid
flowchart TD
    A[Receber Tile candidata] --> B{Tile existe no estado atual?}

    B -- nao --> X[Resultado: inelegivel]
    B -- sim --> C{Tile esta removida?}

    C -- sim --> X
    C -- nao --> D{Existe bloqueio superior ativo?}

    D -- sim --> X
    D -- nao --> E{Os dois lados laterais relevantes estao bloqueados?}

    E -- sim --> X
    E -- nao --> F[Resultado: elegivel]
````

---

## 12.4 Interpretação da Árvore de Decisão

### Etapa 1 - Verificar existência da Tile

A peça candidata precisa existir formalmente no `GameState`.

Se a peça:

* não existir;
* tiver sido resolvida fora do estado atual;
* ou for uma referência inválida,

então ela não pode seguir na avaliação.

---

### Etapa 2 - Verificar se a Tile está removida

Mesmo que a peça exista estruturalmente, ela não pode ser selecionada se já estiver marcada como removida.

Uma peça removida:

* não participa mais da jogabilidade;
* não pode ser reaproveitada como seleção;
* não deve voltar ao fluxo ativo da sessão corrente.

---

### Etapa 3 - Verificar bloqueio superior

Se houver uma peça ativa posicionada acima da peça candidata em condição de bloqueio, a peça analisada não é livre.

Essa etapa é decisiva porque:

* o bloqueio superior por si só já invalida a elegibilidade;
* não importa se os lados laterais estão livres, se há bloqueio superior ativo.

---

### Etapa 4 - Verificar bloqueio lateral total

Se a peça não estiver bloqueada superiormente, o sistema precisa então verificar os lados laterais relevantes.

Se os dois lados estiverem bloqueados por peças ativas:

* a peça continua inelegível.

A peça só pode ser considerada livre lateralmente quando existir ao menos um lado livre.

---

### Etapa 5 - Resultado final

A peça é elegível apenas se:

* existir no estado atual;
* não estiver removida;
* não estiver bloqueada superiormente;
* não estiver bloqueada nos dois lados.

Essa é a definição operacional da elegibilidade da peça.

---

## 12.5 Regra Formal Consolidada

A regra consolidada que o diagrama expressa é:

Uma `Tile` é elegível se, e somente se:

* existe no estado atual;
* está ativa;
* não possui bloqueio superior ativo;
* possui ao menos um lado lateral livre.

---

## 12.6 Relação com as Regras de Negócio

Este diagrama concretiza especialmente estas regras do domínio:

* existência válida da peça;
* estado de remoção;
* bloqueio superior;
* bloqueio lateral total;
* definição de peça livre;
* elegibilidade para seleção.

Ele é a tradução visual da regra central de seleção do jogo.

---

## 12.7 Casos Típicos Cobertos pelo Diagrama

### Caso 1

Peça inexistente no estado atual.
**Resultado:** inelegível.

### Caso 2

Peça existente, mas removida.
**Resultado:** inelegível.

### Caso 3

Peça ativa, mas com bloqueio superior.
**Resultado:** inelegível.

### Caso 4

Peça sem bloqueio superior, porém com os dois lados bloqueados.
**Resultado:** inelegível.

### Caso 5

Peça ativa, sem bloqueio superior e com ao menos um lado livre.
**Resultado:** elegível.

---

## 12.8 Regras Estruturais Implicadas pelo Diagrama

### RDE-01

A elegibilidade depende primeiro da existência da peça no estado atual.

### RDE-02

Peça removida nunca pode ser tratada como elegível.

### RDE-03

Bloqueio superior tem precedência sobre análise lateral.

### RDE-04

Dois lados laterais bloqueados tornam a peça inelegível.

### RDE-05

A elegibilidade não depende da interface, mas do domínio.

### RDE-06

A decisão final de elegibilidade deve ser determinística.

---

## 12.9 Falhas que Este Diagrama Ajuda a Evitar

Este fluxo protege contra:

* seleção de peça removida;
* seleção de peça inexistente;
* seleção de peça bloqueada superiormente;
* seleção de peça travada dos dois lados;
* mistura de regra visual com regra lógica;
* implementações ambíguas de `isTileFree`.

---

## 12.10 Papel Estratégico do Diagrama

Este é um dos diagramas mais importantes do projeto porque a elegibilidade é a regra mais sensível da jogabilidade.

Ele ajuda a:

* consolidar a lógica de seleção;
* orientar testes unitários do domínio;
* orientar testes funcionais;
* reduzir bugs silenciosos de seleção;
* alinhar SRS, Regras de Negócio, Implementação Técnica e Casos de Teste.

Ele é especialmente importante para:

* `domain/rules`
* `presentation/controller`
* `tests/unit`
* `tests/functional`

---

## 12.11 Compatibilidade com os Demais Documentos

Este diagrama concretiza visualmente o conteúdo dos seguintes documentos:

* `02 - Requisitos_SRS.md`
* `03 - Regras_de_Negocio.md`
* `05 - Modelo_de_Dados.md`
* `08 - Implementacao_Tecnica.md`
* `09 - Plano_de_Testes.md`
* `10 - Casos_de_Teste.md`

Ele deve permanecer coerente com todos eles.

---

## 12.12 Declaração Oficial

Este documento estabelece o Diagrama de Regras de Elegibilidade do projeto VitahAcre e deve ser lido como a representação oficial da lógica de decisão da seleção de peças nesta fase do projeto.
