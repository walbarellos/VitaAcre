# 09 - Plano de Testes

## 9.1 Objetivo do Documento
Este documento define o Plano de Testes oficial do projeto VitahAcre na Waterfall V2. Seu propósito é estabelecer a estratégia de validação do sistema, cobrindo regras de negócio, integridade do estado, geração do tabuleiro, fluxo da jogada, renderização, desempenho e estabilidade geral.

Este documento deve:
- proteger o núcleo lógico do jogo;
- orientar a criação dos casos de teste;
- reduzir regressões;
- garantir aderência entre implementação e documentação;
- sustentar a evolução incremental do projeto com segurança.

---

## 9.2 Escopo do Plano de Testes
Este plano cobre, no mínimo, os seguintes blocos do sistema:

- regras de negócio;
- modelo de dados;
- transições de estado;
- controller e fluxo de jogada;
- geração procedural do tabuleiro;
- renderização procedural;
- interação por toque;
- vitória, bloqueio e reinício;
- desempenho e estabilidade.

---

## 9.3 Objetivos Gerais de Qualidade
O plano de testes deve comprovar que:

- peças elegíveis podem ser selecionadas corretamente;
- peças inelegíveis são rejeitadas corretamente;
- pares válidos são aceitos apenas quando obedecem às regras;
- pares inválidos não removem peças;
- o estado da partida permanece íntegro;
- a vitória é detectada corretamente;
- o bloqueio é detectado corretamente;
- o reinício reconstrói a sessão sem resíduos lógicos;
- a interface reflete o estado real da partida;
- o produto mantém desempenho aceitável no hardware-alvo.

---

## 9.4 Princípios de Teste

### PT-01 - Determinismo
Os testes devem ser reproduzíveis. A mesma entrada deve gerar o mesmo resultado esperado no mesmo contexto.

### PT-02 - Cobertura do Núcleo
A prioridade do teste é proteger o núcleo do domínio antes de aspectos cosméticos.

### PT-03 - Integridade do Estado
Toda validação deve considerar não apenas a resposta imediata da jogada, mas também o estado resultante.

### PT-04 - Independência de Camadas
Sempre que possível, regras devem ser testadas isoladamente antes de testes integrados.

### PT-05 - Regressão Controlada
Toda evolução relevante do sistema deve preservar a malha mínima de proteção já validada.

---

## 9.5 Estratégia Geral de Testes
A estratégia oficial do projeto será composta por cinco camadas:

### 9.5.1 Testes Unitários
Validam funções puras e regras de negócio isoladas.

### 9.5.2 Testes de Integração
Validam a comunicação entre módulos, especialmente:
- input;
- controller;
- regras;
- estado;
- gerador;
- renderização.

### 9.5.3 Testes Funcionais
Validam o comportamento real do jogo do ponto de vista da experiência da partida.

### 9.5.4 Testes Visuais e de Interface
Validam a correspondência entre GameState e experiência visual percebida.

### 9.5.5 Testes de Performance e Estabilidade
Validam responsividade, FPS, tempo de inicialização, consumo de memória e robustez geral.

---

## 9.6 Tipos de Teste

## 9.6.1 Testes Unitários

### Objetivo
Garantir que as funções puras do domínio retornem resultados corretos para entradas controladas.

### Itens prioritários
- verificação de peça livre;
- verificação de elegibilidade;
- validação de match;
- verificação de vitória;
- verificação de ausência de jogadas;
- integridade estrutural básica do modelo.

### Critério
Todo comportamento lógico central deve poder ser verificado sem depender da UI.

---

## 9.6.2 Testes de Integração

### Objetivo
Garantir que módulos separados funcionem corretamente quando conectados.

### Integrações prioritárias
- input + controller;
- controller + rules;
- controller + state;
- generator + state;
- state + renderer.

### Critério
O fluxo entre módulos deve respeitar a arquitetura oficial e preservar a integridade do estado.

---

## 9.6.3 Testes Funcionais

### Objetivo
Validar o comportamento completo da partida em cenários reais de uso.

### Fluxos prioritários
- iniciar partida;
- selecionar primeira peça;
- selecionar segunda peça;
- validar match;
- remover peças;
- detectar vitória;
- detectar bloqueio;
- reiniciar.

### Critério
O comportamento funcional deve coincidir com o SRS e com as regras de negócio.

---

## 9.6.4 Testes Visuais

### Objetivo
Garantir que a interface represente corretamente o estado lógico da sessão.

### Itens prioritários
- destaque de peça selecionada;
- desaparecimento ou neutralização visual de peça removida;
- clareza dos estados finais;
- reconstrução correta da sessão após reinício;
- manutenção da legibilidade do tabuleiro.

### Critério
A interface não pode contradizer o GameState.

---

## 9.6.5 Testes de Performance

### Objetivo
Garantir que o jogo mantenha desempenho aceitável nos dispositivos alvo.

### Indicadores prioritários
- FPS;
- tempo de resposta ao toque;
- tempo de inicialização;
- consumo de memória;
- estabilidade sob uso repetido.

### Metas de referência
- resposta visual ao toque em torno de até 50 ms, quando tecnicamente compatível;
- cerca de 60 FPS em gameplay normal, quando compatível com o dispositivo-alvo;
- inicialização em torno de até 2 segundos em ambiente compatível;
- consumo de memória dentro do teto definido pela visão do produto.

---

## 9.7 Componentes Sob Teste
Os seguintes componentes devem ser considerados dentro do escopo de validação:

- Tile;
- SelectionState;
- GameStatus;
- GameState;
- BoardConfiguration;
- InputEvent;
- MatchResult;
- BoardGenerationResult;
- regras do domínio;
- controller;
- gerador procedural;
- renderização principal;
- fluxo de reinício.

---

## 9.8 Matriz Geral de Cobertura

| Componente | Unitário | Integração | Funcional | Visual | Performance |
|---|---|---|---|---|---|
| Regras de negócio | Sim | Sim | Sim | Não | Não |
| Modelo de dados | Sim | Sim | Sim | Não | Não |
| Controller | Não | Sim | Sim | Não | Não |
| Generator | Sim | Sim | Sim | Não | Parcial |
| Renderer | Não | Sim | Sim | Sim | Sim |
| Fluxo da partida | Não | Sim | Sim | Sim | Parcial |
| Reinício | Não | Sim | Sim | Sim | Não |

---

## 9.9 Cenários Prioritários de Validação
Os seguintes cenários são obrigatórios na camada inicial de proteção do projeto:

- seleção de peça livre;
- rejeição de peça bloqueada;
- rejeição de peça removida;
- aceitação de match válido;
- rejeição de match inválido;
- remoção correta após par válido;
- vitória ao remover todas as peças;
- bloqueio quando não houver mais pares possíveis;
- reconstrução correta da sessão após reinício;
- geração de tabuleiro inicialmente jogável.

---

## 9.10 Critérios de Entrada para Teste
Um bloco do sistema só deve entrar em teste quando:

### CET-01
Seu comportamento esperado estiver documentado.

### CET-02
Suas dependências mínimas estiverem implementadas.

### CET-03
O cenário de teste puder ser reproduzido.

### CET-04
O resultado esperado estiver claramente definido.

---

## 9.11 Critérios de Saída para Teste
Um bloco só pode ser considerado validado quando:

### CST-01
Todos os testes críticos associados a ele estiverem aprovados.

### CST-02
Não houver falha bloqueante aberta naquele escopo.

### CST-03
O comportamento observado coincidir com o comportamento documentado.

### CST-04
O estado resultante permanecer íntegro.

### CST-05
A arquitetura não tiver sido violada para “forçar” aprovação.

---

## 9.12 Severidade de Falhas

### S-01 - Crítica
Falha que impede:
- iniciar a partida;
- jogar corretamente;
- concluir a sessão;
- reiniciar a sessão;
- preservar a integridade do estado.

### S-02 - Alta
Falha que viola:
- regra central de elegibilidade;
- regra central de match;
- detecção de vitória;
- detecção de bloqueio;
- integridade do fluxo principal.

### S-03 - Média
Falha que afeta comportamento parcial ou clareza da experiência, sem impedir o uso total do sistema.

### S-04 - Baixa
Falha cosmética ou periférica sem impacto central na lógica da partida.

---

## 9.13 Ambiente de Testes
Os testes devem ser executados, sempre que possível, em três ambientes complementares:

### Ambiente Lógico
Execução de testes unitários locais e verificações determinísticas de domínio.

### Ambiente Funcional
Execução do aplicativo em emulador Android e ambiente de desenvolvimento.

### Ambiente Real
Execução em dispositivo físico Android, com verificação de toque, legibilidade e desempenho.

---

## 9.14 Estratégia de Automação
A automação deve priorizar o que é mais crítico e estável do ponto de vista lógico.

### Prioridade 1
Regras puras.

### Prioridade 2
Integridade do estado.

### Prioridade 3
Geração de tabuleiro.

### Prioridade 4
Fluxo do controller.

### Prioridade 5
Partes reproduzíveis da interface.

### Observação
Nem toda verificação visual precisa ser automatizada no início, mas toda regra crítica deve ser protegida por automação assim que possível.

---

## 9.15 Estratégia Manual Complementar
Além da automação, deve haver validação manual para:

- percepção visual de seleção;
- legibilidade das camadas;
- clareza de vitória e bloqueio;
- sensação de responsividade;
- clareza do comando de reinício;
- comportamento geral em múltiplos tamanhos de tela.

---

## 9.16 Frequência Recomendada de Execução

### Durante desenvolvimento
- testes unitários a cada alteração relevante no domínio;
- testes de integração a cada nova conexão entre módulos.

### Antes de congelar um ciclo incremental
- execução de todos os testes críticos daquele escopo;
- verificação manual mínima da sessão.

### Antes de gerar build relevante
- regressão mínima obrigatória;
- validação do fluxo principal;
- verificação de desempenho essencial;
- verificação de estabilidade básica.

---

## 9.17 Riscos que o Plano de Testes Deve Mitigar
Este plano existe para reduzir os seguintes riscos:

- regra de peça livre implementada incorretamente;
- match aceito em situação inválida;
- remoção indevida de peça;
- GameState corrompido após jogada;
- tabuleiro gerado sem condição mínima de jogabilidade;
- vitória ou bloqueio detectados indevidamente;
- divergência entre lógica e interface;
- reinício defeituoso;
- queda de desempenho em ambiente real.

---

## 9.18 Indicadores de Qualidade
Os seguintes indicadores devem ser acompanhados ao longo da evolução do projeto:

- taxa de aprovação dos testes críticos;
- número de falhas por severidade;
- tempo médio de resposta do toque;
- FPS médio durante gameplay;
- estabilidade do fluxo principal;
- taxa de repetibilidade dos cenários de teste.

---

## 9.19 Ordem Recomendada de Validação
A ordem recomendada de validação no projeto é:

### Etapa 1
Regras puras do domínio.

### Etapa 2
Modelo de dados e integridade estrutural.

### Etapa 3
Geração do tabuleiro.

### Etapa 4
Controller e fluxo de seleção.

### Etapa 5
Match, remoção, vitória e bloqueio.

### Etapa 6
Renderização e feedback visual.

### Etapa 7
Performance e estabilidade.

---

## 9.20 Regressão Mínima Obrigatória
Toda nova versão minimamente relevante do projeto deve reexecutar, no mínimo, a seguinte malha de proteção:

- seleção de peça livre;
- rejeição de peça bloqueada;
- validação de match válido;
- rejeição de match inválido;
- remoção correta de par;
- detecção de vitória;
- detecção de ausência de jogadas;
- reinício funcional;
- geração inicial jogável.

Esta regressão mínima é obrigatória em toda build de teste interno ou superior.

---

## 9.21 Compatibilidade com o Método Caracol
O plano de testes deve acompanhar a evolução incremental do projeto.

Cada ciclo caracol deve conter:
- implementação do bloco;
- validação unitária do bloco;
- integração com o restante;
- correção de falhas;
- reteste;
- congelamento parcial do avanço.

O crescimento do sistema sem crescimento correspondente da malha de testes é considerado inadequado.

---

## 9.22 Critérios de Conformidade do Plano de Testes
O plano será considerado corretamente aplicado quando:

- os cenários críticos estiverem claramente cobertos;
- a malha mínima de regressão estiver operacional;
- regras centrais estiverem protegidas;
- integridade do estado estiver sendo verificada;
- a validação visual não contradizer o domínio;
- o fluxo principal da partida estiver comprovadamente funcional.

---

## 9.23 Relação com os Demais Documentos
Este documento:
- valida os Requisitos do SRS;
- protege as Regras de Negócio;
- verifica o respeito à Arquitetura;
- confirma a coerência do Modelo de Dados;
- apoia a Implementação Técnica;
- sustenta a qualidade das Builds e Releases;
- serve de base para o documento de Casos de Teste.

Nenhum processo de validação do projeto deve contrariar este plano sem revisão explícita da documentação oficial.

---

## 9.24 Declaração Oficial do Plano de Testes
O presente documento estabelece o Plano de Testes oficial do VitahAcre na Waterfall V2.

Ele traduz operacionalmente o Plano de Testes em cenários concretos de validação e fica congelado como referência formal da verificação prática do sistema, salvo revisão explícita em versão posterior da documentação.
