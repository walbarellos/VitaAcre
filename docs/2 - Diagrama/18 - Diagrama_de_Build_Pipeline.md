# 18 - Diagrama de Build Pipeline

## 18.1 Objetivo
Este diagrama apresenta o pipeline oficial de build do projeto VitahAcre, mostrando a cadeia técnica desde o código-fonte até a build pronta para validação real.

Ele serve para:
- formalizar o processo de empacotamento;
- conectar implementação, testes e distribuição técnica;
- orientar o fluxo de geração de APK e AAB;
- reduzir builds inconsistentes;
- apoiar disciplina de release e validação.

---

## 18.2 Leitura do Diagrama
No VitahAcre, uma build não deve nascer diretamente do código sem verificação.

O fluxo correto precisa passar por:
- revisão da versão;
- compilação;
- testes;
- empacotamento;
- instalação;
- validação pós-build;
- decisão de aprovação ou reprovação.

O diagrama abaixo representa essa cadeia oficial.

---

## 18.3 Diagrama Mermaid

```mermaid
flowchart TD
    A[Codigo-fonte atualizado] --> B[Revisar versionCode e versionName]
    B --> C[Executar limpeza do projeto]
    C --> D[Compilar projeto]
    D --> E{Compilacao sem erro?}

    E -- nao --> X[Build reprovada]
    E -- sim --> F[Executar testes criticos]
    F --> G{Testes criticos aprovados?}

    G -- nao --> X
    G -- sim --> H[Gerar artefato]
    H --> I{Tipo de build?}

    I -- debug --> J[Gerar APK de desenvolvimento]
    I -- teste interno --> K[Gerar APK de teste interno]
    I -- release --> L[Gerar APK ou AAB de release]

    J --> M[Instalar em ambiente de validacao]
    K --> M
    L --> M

    M --> N[Validacao pos-build]
    N --> O{Fluxo principal funcional?}

    O -- nao --> X
    O -- sim --> P[Registrar evidencia da build]
    P --> Q[Build aprovada para etapa seguinte]
````

---

## 18.4 Interpretação das Etapas

### Código-fonte atualizado

O pipeline começa com o estado atual do projeto no repositório ou ambiente de desenvolvimento.

Esse código precisa já refletir:

* o escopo do ciclo atual;
* a documentação oficial;
* o estado real da implementação.

---

### Revisar versionCode e versionName

Toda build precisa ser rastreável.

Antes de compilar, a equipe deve garantir:

* numeração correta;
* versão coerente com a mudança realizada;
* compatibilidade com o estágio do projeto.

---

### Executar limpeza do projeto

Antes da compilação, o ambiente deve ser limpo para evitar resíduos de builds anteriores.

Essa etapa ajuda a:

* reduzir inconsistências;
* evitar artefatos contaminados;
* melhorar reprodutibilidade.

---

### Compilar projeto

O sistema executa a compilação da aplicação.

Se a compilação falhar:

* a build não deve prosseguir;
* o pipeline termina em reprovação.

---

### Executar testes críticos

Se a compilação passar, o próximo gate é a execução dos testes críticos.

Esses testes devem cobrir, no mínimo:

* elegibilidade;
* match;
* remoção;
* vitória;
* bloqueio;
* reinício;
* geração inicial jogável.

---

### Gerar artefato

Com código compilado e testes críticos aprovados, o sistema gera o artefato de build.

Os artefatos possíveis nesta fase são:

* APK de desenvolvimento;
* APK de teste interno;
* APK ou AAB de release.

---

### Decidir o tipo de build

O pipeline varia de acordo com o objetivo da geração:

#### Build de desenvolvimento

Voltada para:

* iteração rápida;
* depuração local;
* testes imediatos.

#### Build de teste interno

Voltada para:

* validação funcional em ambiente controlado;
* uso em dispositivo real;
* homologação técnica.

#### Build de release

Voltada para:

* entrega formal;
* validação final;
* publicação futura.

---

### Instalar em ambiente de validação

Nenhuma build relevante deve ser tratada como confiável sem instalação em ambiente realista.

A validação deve ocorrer em:

* emulador, quando útil;
* dispositivo físico principal;
* dispositivo físico alternativo, quando possível.

---

### Validação pós-build

Depois da instalação, a build precisa ser validada funcionalmente.

No mínimo, deve-se verificar:

* abertura do aplicativo;
* geração de tabuleiro;
* toque e seleção;
* match;
* remoção;
* vitória;
* bloqueio;
* reinício;
* ausência de falha crítica imediata.

---

### Registrar evidência da build

Toda build relevante deve gerar registro, com:

* versão;
* data;
* tipo de build;
* ambiente usado;
* status dos testes;
* decisão final.

Essa etapa é importante para rastreabilidade.

---

### Build aprovada

A build só é considerada aprovada quando:

* compilou;
* passou nos testes críticos;
* foi instalada com sucesso;
* executou o fluxo principal corretamente;
* teve evidência registrada.

---

## 18.5 Regras Estruturais Implicadas pelo Diagrama

### RBP-01

Não existe build válida sem versionamento explícito.

### RBP-02

Falha de compilação encerra o pipeline.

### RBP-03

Falha em teste crítico encerra o pipeline.

### RBP-04

Build gerada sem validação pós-build não deve ser tratada como confiável.

### RBP-05

Toda build relevante deve deixar rastro documental.

### RBP-06

Release não pode pular a disciplina das etapas anteriores.

---

## 18.6 Falhas que Este Diagrama Ajuda a Evitar

Este fluxo protege contra:

* build sem rastreabilidade;
* build compilada, mas não testada;
* artefato gerado sem instalação real;
* release tratada como válida sem verificação funcional;
* confusão entre debug e release;
* ausência de evidência técnica da evolução do projeto.

---

## 18.7 Papel Estratégico do Diagrama

Este diagrama é importante porque:

* conecta implementação e distribuição técnica;
* organiza o momento em que o software vira artefato real;
* reduz improviso em build e release;
* ajuda a tornar o processo reprodutível;
* prepara o projeto para publicação futura.

Ele é especialmente importante para:

* `11 - Build_Deploy.md`
* testes críticos;
* validação em dispositivo;
* disciplina de release.

---

## 18.8 Compatibilidade com os Demais Documentos

Este diagrama concretiza visualmente o conteúdo dos seguintes documentos:

* `08 - Implementacao_Tecnica.md`
* `09 - Plano_de_Testes.md`
* `10 - Casos_de_Teste.md`
* `11 - Build_Deploy.md`

Ele também depende da coerência de:

* `04 - Arquitetura.md`
* `17 - Diagrama_de_Ciclos_Caracol.md`

---

## 18.9 Declaração Oficial

Este documento estabelece o Diagrama de Build Pipeline do projeto VitahAcre e deve ser lido como a representação oficial da cadeia de geração e validação de builds nesta fase do projeto.
