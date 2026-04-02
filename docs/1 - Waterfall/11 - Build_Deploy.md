# 11 - Build Deploy

## 11.1 Objetivo do Documento
Este documento define a estratégia oficial de build, empacotamento, validação, distribuição técnica e preparação de release do projeto VitahAcre na Waterfall V2.

Seu propósito é garantir que o projeto:
- possa ser compilado de forma reprodutível;
- gere artefatos coerentes com a documentação oficial;
- preserve integridade entre ambiente de desenvolvimento, teste e release;
- mantenha rastreabilidade de versão;
- esteja preparado para testes internos, homologação técnica e futura publicação.

---

## 11.2 Escopo do Documento
Este documento cobre:
- preparação do ambiente de build;
- organização das variantes de compilação;
- geração de APK e AAB;
- versionamento;
- validações pré-build;
- validações pós-build;
- assinatura de release;
- distribuição técnica controlada;
- preparação para publicação futura.

Este documento não cobre:
- estratégia comercial de lançamento;
- monetização;
- marketing;
- operação de backend online, inexistente no núcleo base do projeto.

---

## 11.3 Plataforma-Alvo
A plataforma principal de distribuição do VitahAcre é:

- **Sistema operacional:** Android
- **Formato de entrega principal:** APK
- **Formato de entrega para publicação futura:** AAB

---

## 11.4 Ferramentas Oficiais de Build
As ferramentas oficiais de build do projeto são:

- Android Studio
- Gradle
- Kotlin
- Android SDK compatível com a versão-alvo do projeto
- JDK compatível com a versão do projeto

Essas ferramentas compõem a base oficial do processo de empacotamento.

---

## 11.5 Tipos de Build

### 11.5.1 Build de Desenvolvimento
Build voltada para:
- implementação;
- depuração;
- testes locais;
- evolução incremental do sistema.

**Características**
- logging de debug permitido;
- foco em iteração rápida;
- não destinada à distribuição pública.

### 11.5.2 Build de Teste Interno
Build voltada para:
- validação funcional em dispositivos reais;
- testes internos;
- homologação técnica controlada;
- verificação do fluxo principal da partida.

**Características**
- maior estabilidade esperada que a build de desenvolvimento;
- menos dependência de debug bruto;
- usada para testes reais do produto.

### 11.5.3 Build de Release
Build voltada para:
- entrega formal;
- preparação para publicação;
- distribuição técnica controlada final;
- validação do comportamento mais próximo da versão definitiva.

**Características**
- assinatura oficial;
- otimização habilitada conforme apropriado;
- sem dependências próprias de debug;
- comportamento final mais estável.

---

## 11.6 Artefatos Oficiais
Os artefatos oficiais do projeto são:

- APK de desenvolvimento
- APK de teste interno
- APK de release
- AAB de release

---

## 11.7 Convenção de Versionamento
A convenção oficial de versionamento deve seguir o padrão:

```text id="t4j6xp"
MAJOR.MINOR.PATCH
````

### Significado

* **MAJOR:** alteração estrutural ou marco importante do produto
* **MINOR:** adição funcional compatível com a base existente
* **PATCH:** correção pontual sem alteração relevante de escopo

### Exemplos

* `0.1.0` — primeira versão minimamente jogável
* `0.2.0` — expansão funcional compatível
* `0.2.1` — correção de bug
* `1.0.0` — primeira versão considerada estável para release formal

---

## 11.8 Identificação de Versão

Toda build gerada deve possuir, no mínimo:

* `versionCode`
* `versionName`

### Regras obrigatórias

* nenhuma build distribuída pode existir sem identificação explícita;
* toda entrega técnica deve ser rastreável por versão;
* a mudança de versão deve acompanhar mudanças reais do projeto.

---

## 11.9 Pré-Requisitos para Build

Uma build só deve ser gerada quando as condições abaixo forem satisfeitas:

### BD-01

O projeto compila sem erros.

### BD-02

Os documentos centrais da Waterfall V2 permanecem coerentes com o estado atual do código.

### BD-03

Os testes críticos definidos no plano de testes estão aprovados.

### BD-04

O fluxo principal do jogo está funcional:

* iniciar;
* selecionar;
* validar match;
* remover;
* detectar vitória ou bloqueio;
* reiniciar.

### BD-05

Não existem falhas críticas abertas que inviabilizem a validação da build.

---

## 11.10 Checklist Pré-Build

Antes de gerar uma build oficial, deve-se verificar:

* versionCode atualizado;
* versionName atualizado;
* nome do aplicativo correto;
* identificador do aplicativo coerente;
* dependências de debug revisadas;
* logs excessivos revisados;
* geração de tabuleiro funcional;
* seleção funcional;
* match funcional;
* remoção funcional;
* detecção de vitória funcional;
* detecção de bloqueio funcional;
* reinício funcional;
* testes críticos aprovados.

---

## 11.11 Processo Oficial de Build

O processo oficial de build deve seguir a ordem abaixo:

1. revisar a versão;
2. revisar o estado do código;
3. executar limpeza do projeto;
4. compilar o projeto;
5. executar os testes críticos;
6. gerar o artefato desejado;
7. instalar em ambiente de validação;
8. verificar funcionamento mínimo;
9. registrar evidência da build.

---

## 11.12 Build de APK

O APK deve ser o principal formato usado para:

* testes locais;
* testes em dispositivo real;
* homologação técnica controlada;
* distribuição interna de validação.

### Requisitos mínimos

* instalação sem erro;
* abertura do app;
* exibição do tabuleiro;
* fluxo de toque funcional;
* reinício funcional;
* ausência de crash imediato.

---

## 11.13 Build de AAB

O AAB deve ser gerado quando houver necessidade de:

* preparação de publicação;
* validação do empacotamento orientado a loja;
* consolidação da versão release em formato de distribuição formal.

### Requisitos mínimos

* build release estável;
* assinatura configurada;
* testes críticos aprovados;
* versionamento coerente.

---

## 11.14 Assinatura de Release

Builds de release devem utilizar assinatura oficial controlada.

### Regras obrigatórias

* a chave de assinatura deve ser preservada com segurança;
* credenciais não devem ficar expostas no código-fonte;
* builds de release sem assinatura adequada não devem ser tratadas como builds finais;
* a política de assinatura deve ser reproduzível e controlada.

### Risco crítico

A perda da chave oficial compromete a continuidade da linha formal de releases.

---

## 11.15 Otimizações de Release

A build de release deve considerar otimizações compatíveis com:

* redução de código desnecessário;
* remoção de dependências de debug;
* redução de peso do artefato;
* preservação do desempenho.

### Regra obrigatória

Toda otimização aplicada deve ser validada funcionalmente depois da geração da build.

Não é permitido otimizar e quebrar o fluxo principal do jogo.

---

## 11.16 Validação Pós-Build

Toda build gerada deve passar por validação mínima pós-build.

### Verificações obrigatórias

* instalação bem-sucedida;
* abertura do aplicativo;
* geração de tabuleiro;
* seleção de peça válida;
* rejeição de peça inelegível;
* remoção de par válido;
* detecção de vitória;
* detecção de ausência de jogadas, quando reproduzível;
* reinício funcional.

### Verificações complementares

* responsividade;
* legibilidade visual;
* ausência de travamento grave;
* desempenho aceitável;
* consumo de memória compatível com a meta do projeto.

---

## 11.17 Critérios de Aprovação da Build

Uma build será considerada aprovada quando:

* compilar sem erro;
* instalar corretamente;
* executar o fluxo principal de ponta a ponta;
* não apresentar falha crítica;
* respeitar o comportamento documentado;
* estar corretamente identificada por versão;
* passar nas verificações mínimas pós-build.

---

## 11.18 Critérios de Reprovação da Build

Uma build será considerada reprovada quando ocorrer qualquer uma das situações abaixo:

* falha de compilação;
* falha de instalação;
* crash na abertura;
* tabuleiro não gerado corretamente;
* seleção quebrada;
* match quebrado;
* estado inconsistente;
* reinício defeituoso;
* detecção incorreta de vitória ou bloqueio;
* degradação grave de desempenho;
* divergência relevante em relação à documentação oficial.

---

## 11.19 Estratégia de Distribuição Técnica

A distribuição técnica do projeto deve seguir camadas controladas de validação:

### Etapa 1

Desenvolvimento local.

### Etapa 2

Teste em emulador Android.

### Etapa 3

Teste em dispositivo físico principal.

### Etapa 4

Teste em dispositivo físico alternativo.

### Etapa 5

Homologação técnica controlada.

Somente depois dessas etapas o projeto deve ser considerado apto à preparação de publicação.

---

## 11.20 Evidências de Build

Cada build relevante deve registrar, no mínimo:

* versão;
* data;
* tipo de build;
* ambiente utilizado;
* resultado dos testes críticos;
* observações de falhas conhecidas;
* decisão final: aprovada ou reprovada.

### Objetivo

Garantir rastreabilidade técnica real da evolução do projeto.

---

## 11.21 Preparação para Publicação

Quando o projeto atingir maturidade suficiente, a preparação para publicação deverá incluir:

* nome final do aplicativo;
* ícone final;
* build release estável;
* assinatura oficial válida;
* revisão de desempenho;
* revisão de falhas críticas;
* revisão de texto e apresentação visual;
* eventual documentação mínima exigida pela plataforma de distribuição.

### Observação

A publicação só deve ocorrer após estabilidade real do núcleo do jogo.

---

## 11.22 Riscos de Build e Deploy a Evitar

Os seguintes riscos devem ser evitados:

### RBD-01

Gerar build sem rodar testes críticos.

### RBD-02

Distribuir build sem versionamento claro.

### RBD-03

Confundir build de debug com build de release.

### RBD-04

Publicar sem validação em dispositivo real.

### RBD-05

Assinar release de maneira insegura.

### RBD-06

Aplicar otimização que quebre o comportamento documentado.

### RBD-07

Perder rastreabilidade entre build gerada e estado do projeto.

---

## 11.23 Compatibilidade com o Método Caracol

Embora este documento pertença à Waterfall V2, ele deve sustentar a evolução incremental posterior.

Cada ciclo caracol pode produzir builds intermediárias, desde que:

* o bloco implementado esteja testado;
* a integração esteja validada;
* a build seja identificável;
* a regressão mínima obrigatória continue funcional.

O crescimento do projeto sem disciplina de build compromete a integridade do método.

---

## 11.24 Critério de Pronto para Entrega Técnica

O VitahAcre estará pronto para entrega técnica controlada quando:

* o fluxo principal estiver estável;
* a build release compilar corretamente;
* os testes críticos estiverem aprovados;
* a versão estiver identificada;
* a validação em dispositivo real tiver sido realizada;
* não existirem falhas críticas abertas.

---

## 11.25 Relação com os Demais Documentos

Este documento:

* materializa a etapa de empacotamento prevista pela arquitetura do projeto;
* depende da Implementação Técnica;
* é condicionado pelo Plano de Testes e pelos Casos de Teste;
* sustenta a transição do projeto entre construção e validação real;
* prepara o produto para distribuição técnica e futura publicação.

Nenhum processo de build ou deploy deve contradizer a base técnica oficial sem revisão explícita desta documentação.

---

## 11.26 Declaração Oficial de Build e Deploy

O presente documento estabelece a política oficial de build e deploy do VitahAcre na Waterfall V2.

Ele define os tipos de build, o processo de geração dos artefatos, os critérios de validação, a disciplina de versionamento, as regras de assinatura e os requisitos mínimos de entrega técnica, ficando congelado como referência formal do empacotamento e da distribuição do projeto, salvo revisão explícita em versão posterior da documentação.

