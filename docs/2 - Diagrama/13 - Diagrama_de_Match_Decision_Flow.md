# 13 - Diagrama de Match Decision Flow

## 13.1 Objetivo
Este diagrama apresenta a lógica oficial de decisão do match no projeto VitahAcre, mostrando como o sistema determina se duas peças selecionadas formam ou não um par válido.

Ele serve para:
- formalizar a regra de pareamento;
- orientar a implementação da validação de match;
- reduzir remoções indevidas;
- alinhar regras, testes e código;
- dar previsibilidade à segunda etapa mais sensível da jogabilidade, depois da elegibilidade.

---

## 13.2 Leitura do Diagrama
No VitahAcre, duas peças não formam match apenas por terem sido tocadas.

Antes de remover um par, o sistema precisa validar, em ordem:

- existe primeira peça?
- existe segunda peça?
- ambas existem no estado atual?
- ambas estão ativas?
- são peças distintas?
- ambas são elegíveis no momento?
- possuem `pairKey` compatível?
- então o match é válido.

O diagrama abaixo representa essa árvore de decisão.

---

## 13.3 Diagrama Mermaid

```mermaid
flowchart TD
    A[Receber primeira e segunda Tile] --> B{Primeira Tile existe?}

    B -- nao --> X[Resultado: match invalido]
    B -- sim --> C{Segunda Tile existe?}

    C -- nao --> X
    C -- sim --> D{Ambas existem no estado atual?}

    D -- nao --> X
    D -- sim --> E{Alguma esta removida?}

    E -- sim --> X
    E -- nao --> F{As duas referencias apontam para a mesma Tile?}

    F -- sim --> X
    F -- nao --> G{Ambas sao elegiveis no momento?}

    G -- nao --> X
    G -- sim --> H{pairKey e compativel?}

    H -- nao --> X
    H -- sim --> Y[Resultado: match valido]
````

---

## 13.4 Interpretação da Árvore de Decisão

### Etapa 1 - Verificar existência da primeira Tile

A validação só pode começar se a primeira peça existir como entidade válida no contexto da sessão.

---

### Etapa 2 - Verificar existência da segunda Tile

Sem segunda peça válida, não existe par a ser avaliado.

---

### Etapa 3 - Confirmar presença das duas no estado atual

Mesmo que ambas existam como objetos, elas precisam existir de forma válida no `GameState` atual.

Isso evita:

* match com referências antigas;
* match com peça fora da sessão;
* inconsistência com a verdade atual da partida.

---

### Etapa 4 - Verificar se alguma está removida

Se qualquer uma das peças já estiver removida:

* não pode participar de novo match;
* a operação deve falhar imediatamente.

---

### Etapa 5 - Verificar se são peças distintas

Uma peça nunca pode formar par com ela mesma.

Se as duas referências apontarem para a mesma `Tile`:

* o resultado deve ser inválido.

---

### Etapa 6 - Verificar elegibilidade atual

Mesmo que a peça exista e esteja ativa, ela ainda precisa ser elegível no momento da avaliação.

Isso significa:

* não estar bloqueada superiormente;
* não estar bloqueada nos dois lados;
* continuar obedecendo à regra formal de seleção.

---

### Etapa 7 - Verificar compatibilidade de pairKey

Se ambas forem válidas até aqui, o sistema precisa então verificar se pertencem ao mesmo tipo de par.

Na versão base do projeto:

* o pareamento é válido quando ambas possuem o mesmo `pairKey`.

---

### Etapa 8 - Resultado final

O match só é válido se:

* ambas as peças existirem;
* ambas estiverem ativas;
* ambas existirem no estado atual;
* forem distintas;
* forem elegíveis;
* tiverem `pairKey` compatível.

---

## 13.5 Regra Formal Consolidada

A regra consolidada expressa por este diagrama é:

Duas `Tile` formam um match válido se, e somente se:

* existem no estado atual;
* não estão removidas;
* são distintas;
* são elegíveis no momento da avaliação;
* possuem compatibilidade lógica de pareamento.

---

## 13.6 Relação com as Regras de Negócio

Este diagrama concretiza especialmente as regras de:

* distinção entre peças;
* regra de match;
* equivalência lógica de pareamento;
* match inválido;
* remoção por match válido.

Ele é a tradução visual da lógica oficial de pareamento do jogo.

---

## 13.7 Casos Típicos Cobertos pelo Diagrama

### Caso 1

Primeira peça inexistente.
**Resultado:** match inválido.

### Caso 2

Segunda peça inexistente.
**Resultado:** match inválido.

### Caso 3

Uma ou ambas as peças removidas.
**Resultado:** match inválido.

### Caso 4

As duas referências apontam para a mesma peça.
**Resultado:** match inválido.

### Caso 5

Duas peças distintas, mas inelegíveis.
**Resultado:** match inválido.

### Caso 6

Duas peças elegíveis, distintas, mas com `pairKey` diferente.
**Resultado:** match inválido.

### Caso 7

Duas peças distintas, ativas, elegíveis e com `pairKey` igual.
**Resultado:** match válido.

---

## 13.8 Regras Estruturais Implicadas pelo Diagrama

### RMD-01

O match não pode ser avaliado sem duas peças formalmente válidas.

### RMD-02

Peça removida nunca participa de match.

### RMD-03

Auto-pareamento é proibido.

### RMD-04

Elegibilidade continua valendo no momento da comparação.

### RMD-05

Compatibilidade de `pairKey` é requisito formal da versão base.

### RMD-06

O resultado de match deve ser determinístico.

---

## 13.9 Falhas que Este Diagrama Ajuda a Evitar

Este fluxo protege contra:

* remoção de par inválido;
* remoção de peça removida;
* match com a própria peça;
* comparação de peças fora do estado atual;
* aceitação de par com `pairKey` incompatível;
* mistura de regra visual com regra de domínio.

---

## 13.10 Papel Estratégico do Diagrama

Este é um dos diagramas mais importantes do projeto porque o match é a operação que decide o progresso real da partida.

Ele ajuda a:

* consolidar a lógica de remoção;
* orientar implementação de `validateMatch`;
* orientar testes unitários e funcionais;
* impedir bugs silenciosos de pareamento;
* alinhar controller, rules, testes e estado.

Ele é especialmente importante para:

* `domain/rules`
* `domain/usecase`
* `presentation/controller`
* `tests/unit`
* `tests/functional`

---

## 13.11 Compatibilidade com os Demais Documentos

Este diagrama concretiza visualmente o conteúdo dos seguintes documentos:

* `02 - Requisitos_SRS.md`
* `03 - Regras_de_Negocio.md`
* `05 - Modelo_de_Dados.md`
* `08 - Implementacao_Tecnica.md`
* `09 - Plano_de_Testes.md`
* `10 - Casos_de_Teste.md`

Ele deve permanecer coerente com todos eles.

---

## 13.12 Declaração Oficial

Este documento estabelece o Diagrama de Match Decision Flow do projeto VitahAcre e deve ser lido como a representação oficial da lógica de decisão do pareamento nesta fase do projeto.
