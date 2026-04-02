# 02 - Requisitos SRS

## 2.1 Objetivo do Documento
Este documento define a Especificação de Requisitos de Software do projeto VitahAcre. Sua função é transformar a visão geral do produto em contratos objetivos de comportamento, limitando ambiguidades e estabelecendo o que o sistema deve fazer, em quais condições deve fazer, quais restrições deve respeitar e quais critérios permitirão validar sua conformidade.

Este documento é a referência central para:
- implementação;
- testes;
- validação funcional;
- coerência entre arquitetura, regras de negócio e interface.

Tudo o que for implementado no sistema deve poder ser relacionado, direta ou indiretamente, a este documento.

---

## 2.2 Escopo do Documento
Este SRS cobre os requisitos do núcleo funcional do VitahAcre em sua versão base, incluindo:
- fluxo principal de partida;
- seleção de peças;
- validação de elegibilidade;
- formação de pares;
- remoção de peças;
- estados de vitória e bloqueio;
- reinício de partida;
- requisitos não funcionais fundamentais;
- estados do sistema;
- critérios gerais de aceitação.

Este documento não detalha a implementação interna em código, nem substitui os documentos de arquitetura, modelo de dados, regras de negócio, design, testes ou build.

---

## 2.3 Definições Operacionais
Para evitar ambiguidade, os termos abaixo possuem significado oficial no projeto.

### 2.3.1 Peça
Elemento jogável do tabuleiro, identificável logicamente, posicionado em coordenadas e camada específicas, podendo estar ativa ou removida.

### 2.3.2 Tabuleiro
Conjunto organizado de peças que compõe a partida atual.

### 2.3.3 Peça livre
Peça elegível para seleção segundo as regras formais do jogo.

### 2.3.4 Seleção
Ato de o sistema registrar uma peça escolhida pelo usuário por meio de toque válido.

### 2.3.5 Match
Formação de um par válido entre duas peças compatíveis.

### 2.3.6 Partida
Sessão jogável iniciada a partir da geração de um tabuleiro válido e encerrada por vitória, bloqueio ou reinício.

### 2.3.7 Estado do jogo
Situação lógica atual da partida, representada por valores formais controlados pelo sistema.

### 2.3.8 Reinício
Ação que descarta a partida atual e cria uma nova partida válida.

---

## 2.4 Premissas Gerais
As seguintes premissas são assumidas como válidas nesta fase do projeto:

### PG-01
O jogo será executado em dispositivo Android com interação por toque.

### PG-02
O núcleo funcional do jogo não depende de conexão com rede para operar.

### PG-03
O tabuleiro será gerado de forma a permitir início válido de partida.

### PG-04
A renderização deverá refletir o estado lógico da partida.

### PG-05
A lógica do jogo será determinística para uma mesma entrada aplicada a um mesmo estado.

---

## 2.5 Restrições Gerais
As seguintes restrições devem ser obedecidas por qualquer implementação compatível com este SRS:

### RG-01
O sistema não deve permitir remoção de peças fora das regras definidas.

### RG-02
O sistema não deve permitir seleção válida de peça inelegível.

### RG-03
O sistema não deve depender de backend online para o fluxo principal.

### RG-04
O sistema não deve misturar regra de negócio com mera apresentação visual.

### RG-05
O estado do jogo deve permanecer íntegro após qualquer ação do usuário.

---

## 2.6 Requisitos Funcionais

## RF-01 - Inicialização do sistema
### Descrição
O sistema deve ser capaz de iniciar o jogo e preparar uma partida jogável.

### Entrada
- abertura do aplicativo;
- acionamento do fluxo inicial da tela principal.

### Processamento esperado
- inicializar estruturas mínimas do sistema;
- preparar configuração da partida;
- gerar ou carregar um tabuleiro válido;
- construir o estado inicial da sessão.

### Saída
- partida pronta para interação.

### Critério de aceitação
Ao abrir o jogo, o usuário deve visualizar um tabuleiro válido e um estado apto ao início da partida.

---

## RF-02 - Geração de tabuleiro
### Descrição
O sistema deve gerar um tabuleiro estruturalmente válido para a partida.

### Entrada
- configuração de tabuleiro;
- parâmetros internos de geração;
- seed, quando aplicável.

### Processamento esperado
- definir quantidade de peças;
- garantir coerência de pareamento;
- distribuir as peças no tabuleiro;
- respeitar estrutura por camadas;
- produzir um conjunto jogável.

### Saída
- conjunto de peças válido para criação da partida.

### Critério de aceitação
O tabuleiro gerado deve conter apenas posições válidas, quantidade compatível de peças e condição inicial de jogo utilizável.

---

## RF-03 - Exibição do tabuleiro
### Descrição
O sistema deve apresentar visualmente ao usuário o tabuleiro correspondente ao estado atual da partida.

### Entrada
- GameState atual.

### Processamento esperado
- ler o estado;
- representar as peças ativas;
- representar seleção atual;
- ocultar ou tratar adequadamente peças removidas;
- refletir estados especiais da partida.

### Saída
- interface visual coerente com o estado lógico.

### Critério de aceitação
O usuário deve ver um tabuleiro compatível com a situação real do jogo.

---

## RF-04 - Captura de toque
### Descrição
O sistema deve capturar a interação do usuário por toque na tela.

### Entrada
- coordenadas de toque;
- evento de interação da interface.

### Processamento esperado
- identificar a intenção do toque;
- localizar a peça correspondente, se houver;
- encaminhar a ação ao fluxo de controle da partida.

### Saída
- evento processável pelo sistema.

### Critério de aceitação
Um toque em área relevante deve poder ser interpretado corretamente pelo sistema.

---

## RF-05 - Identificação da peça tocada
### Descrição
O sistema deve determinar se o toque do usuário corresponde a uma peça válida do tabuleiro.

### Entrada
- coordenadas do toque;
- estado atual do tabuleiro.

### Processamento esperado
- verificar a região tocada;
- localizar a peça correspondente;
- retornar a peça alvo ou ausência de alvo.

### Saída
- referência da peça tocada;
- ou valor nulo, quando não houver peça correspondente.

### Critério de aceitação
Toques sobre peças devem identificar corretamente a peça alvo; toques fora das peças não devem inventar seleção.

---

## RF-06 - Validação de elegibilidade da peça
### Descrição
O sistema deve verificar se a peça tocada pode ser selecionada.

### Entrada
- peça alvo;
- estado atual da partida.

### Processamento esperado
- verificar se a peça existe;
- verificar se não está removida;
- verificar se satisfaz as condições formais de elegibilidade.

### Saída
- resultado válido ou inválido para seleção.

### Critério de aceitação
Peças livres devem poder ser selecionadas; peças bloqueadas ou removidas devem ser rejeitadas.

---

## RF-07 - Registro da primeira seleção
### Descrição
O sistema deve registrar a primeira peça válida escolhida pelo usuário.

### Entrada
- peça elegível;
- estado atual sem primeira seleção ativa.

### Processamento esperado
- armazenar a peça como selecionada;
- atualizar o estado da partida;
- refletir a seleção na interface.

### Saída
- partida com uma peça selecionada.

### Critério de aceitação
Após tocar uma peça válida, o sistema deve registrá-la como primeira seleção.

---

## RF-08 - Registro da segunda seleção
### Descrição
O sistema deve registrar a segunda peça válida quando houver uma primeira seleção ativa.

### Entrada
- peça elegível;
- estado atual com primeira seleção registrada.

### Processamento esperado
- registrar a segunda seleção;
- preparar a verificação de compatibilidade entre as duas peças.

### Saída
- estado com duas peças em processo de avaliação.

### Critério de aceitação
Após uma primeira seleção válida, o toque em uma segunda peça elegível deve ser registrado corretamente.

---

## RF-09 - Validação de match
### Descrição
O sistema deve verificar se as duas peças selecionadas formam um par válido.

### Entrada
- primeira peça selecionada;
- segunda peça selecionada.

### Processamento esperado
- confirmar que são peças distintas;
- confirmar que ambas são elegíveis;
- confirmar que obedecem à regra de pareamento do jogo.

### Saída
- resultado de match válido ou inválido.

### Critério de aceitação
Somente pares compatíveis devem ser aceitos como match.

---

## RF-10 - Remoção de peças após match válido
### Descrição
O sistema deve remover do fluxo jogável as duas peças que formaram um par válido.

### Entrada
- duas peças validadas como match.

### Processamento esperado
- marcar ambas como removidas;
- retirar as peças da condição de seleção futura;
- atualizar o estado da partida.

### Saída
- novo estado do jogo com ambas removidas.

### Critério de aceitação
Após um match válido, as duas peças não devem permanecer ativas no tabuleiro.

---

## RF-11 - Tratamento de match inválido
### Descrição
O sistema deve tratar corretamente a tentativa de pareamento inválido.

### Entrada
- duas peças selecionadas que não formam par válido.

### Processamento esperado
- não remover peças;
- preservar integridade do estado;
- redefinir a seleção conforme a lógica oficial adotada pelo projeto.

### Saída
- estado consistente após tentativa inválida.

### Critério de aceitação
Um par inválido não pode causar remoção incorreta nem corromper a partida.

---

## RF-12 - Atualização de estado após jogada
### Descrição
O sistema deve atualizar o estado global da partida após cada ação relevante do jogador.

### Entrada
- evento de toque processado;
- resultado de seleção ou pareamento.

### Processamento esperado
- refletir seleção atual;
- refletir remoções;
- refletir transição entre estados formais do jogo;
- preservar integridade da sessão.

### Saída
- novo GameState coerente.

### Critério de aceitação
Toda ação válida ou inválida deve resultar em estado consistente e rastreável.

---

## RF-13 - Detecção de vitória
### Descrição
O sistema deve detectar quando a partida foi vencida.

### Entrada
- estado do jogo após remoção de peças.

### Processamento esperado
- verificar se todas as peças jogáveis foram removidas.

### Saída
- estado de vitória, quando aplicável.

### Critério de aceitação
A vitória só deve ser declarada quando nenhuma peça ativa restante impedir essa conclusão.

---

## RF-14 - Detecção de ausência de jogadas
### Descrição
O sistema deve detectar quando ainda existem peças no tabuleiro, mas não há mais pares válidos disponíveis.

### Entrada
- estado do jogo em andamento.

### Processamento esperado
- analisar a existência de pares válidos possíveis entre peças elegíveis.

### Saída
- estado de bloqueio ou continuidade.

### Critério de aceitação
Quando não houver jogadas válidas restantes, o sistema deve reconhecer formalmente esse estado.

---

## RF-15 - Reinício de partida
### Descrição
O sistema deve permitir o reinício da sessão de jogo.

### Entrada
- comando de reinício emitido pelo usuário.

### Processamento esperado
- descartar o estado atual;
- gerar novo tabuleiro válido;
- criar novo estado inicial;
- retornar o jogo à condição de partida pronta.

### Saída
- nova partida íntegra.

### Critério de aceitação
Após reiniciar, não deve haver resíduos lógicos indevidos da partida anterior.

---

## RF-16 - Feedback visual de seleção
### Descrição
O sistema deve indicar visualmente ao usuário quando uma peça tiver sido selecionada com sucesso.

### Entrada
- estado contendo seleção ativa.

### Processamento esperado
- representar visualmente a seleção.

### Saída
- destaque perceptível da peça selecionada.

### Critério de aceitação
O usuário deve conseguir perceber qual peça está selecionada.

---

## RF-17 - Feedback visual de remoção
### Descrição
O sistema deve refletir visualmente a remoção de peças após match válido.

### Entrada
- estado com peças removidas.

### Processamento esperado
- atualizar a apresentação visual do tabuleiro.

### Saída
- peças removidas deixam de se apresentar como ativas.

### Critério de aceitação
Peças removidas não podem continuar aparentando estar disponíveis para jogo.

---

## RF-18 - Feedback de estados finais
### Descrição
O sistema deve indicar visualmente os estados finais principais da partida.

### Entrada
- estado em VITORIA ou SEM_JOGADAS.

### Processamento esperado
- apresentar indicação correspondente ao encerramento da sessão atual.

### Saída
- feedback visual de vitória ou bloqueio.

### Critério de aceitação
O usuário deve distinguir claramente quando venceu ou quando ficou sem jogadas.

---

## 2.7 Requisitos Não Funcionais

## RNF-01 - Determinismo lógico
A lógica central do jogo deve produzir o mesmo resultado para a mesma entrada aplicada ao mesmo estado.

---

## RNF-02 - Integridade de estado
O sistema não deve corromper o GameState em resposta a interações válidas ou inválidas do usuário.

---

## RNF-03 - Responsividade
O sistema deve responder ao toque do usuário com rapidez suficiente para transmitir controle perceptível da ação.

Meta inicial de referência:
- resposta visual percebida em até 50 ms, quando tecnicamente alcançável no dispositivo-alvo.

---

## RNF-04 - Desempenho
O jogo deve manter fluidez visual compatível com uso em dispositivos Android alvo.

Meta inicial de referência:
- aproximadamente 60 FPS em condições normais de gameplay, quando compatível com o dispositivo-alvo.

---

## RNF-05 - Inicialização
O sistema deve abrir e preparar uma partida jogável em tempo compatível com uma experiência leve.

Meta inicial de referência:
- até 2 segundos em ambiente alvo compatível, sob condições normais.

---

## RNF-06 - Baixa dependência externa
O núcleo funcional do jogo não deve depender de serviços online para iniciar, processar jogadas ou concluir partidas.

---

## RNF-07 - Testabilidade
As regras centrais do jogo devem ser implementáveis de maneira compatível com testes unitários e de integração.

---

## RNF-08 - Modularidade
A estrutura do sistema deve permitir separação entre:
- lógica do domínio;
- estado;
- controle;
- renderização.

---

## RNF-09 - Coerência visual
A interface deve refletir fielmente o estado lógico da partida, sem inventar comportamento inexistente.

---

## RNF-10 - Evolutividade
A base do sistema deve permitir evolução incremental posterior sem exigir destruição da fundação estrutural.

---

## 2.8 Estados Oficiais do Sistema
Os estados formais mínimos do sistema nesta fase são:

- INICIALIZANDO
- PRONTO
- SELECIONANDO_1
- SELECIONANDO_2
- PROCESSANDO_MATCH
- VITORIA
- SEM_JOGADAS

Esses estados poderão ser refinados em documentos posteriores, mas qualquer refinamento deverá preservar coerência com o significado definido aqui.

---

## 2.9 Fluxo Funcional Principal
O fluxo funcional principal esperado é o seguinte:

1. o sistema inicia;
2. um tabuleiro válido é gerado;
3. a partida entra em estado pronto;
4. o usuário toca uma peça;
5. o sistema identifica a peça;
6. o sistema valida a elegibilidade;
7. a primeira seleção é registrada;
8. o usuário toca uma segunda peça;
9. o sistema valida a elegibilidade da segunda;
10. o sistema avalia o match;
11. se o match for válido, remove as peças;
12. o sistema verifica vitória ou continuidade;
13. o sistema verifica bloqueio quando aplicável;
14. a partida continua até vitória, bloqueio ou reinício.

---

## 2.10 Fluxo Alternativo de Toque Inválido
Quando o usuário tocar em uma área sem peça ou em peça inelegível:

1. o sistema recebe o toque;
2. identifica ausência de alvo ou rejeição de elegibilidade;
3. não realiza remoção indevida;
4. preserva a integridade do estado;
5. mantém o jogo operável.

---

## 2.11 Fluxo de Reinício
Quando o usuário acionar reinício:

1. o comando é capturado;
2. o estado atual é descartado;
3. um novo tabuleiro válido é gerado;
4. um novo GameState é criado;
5. o sistema retorna ao estado PRONTO.

---

## 2.12 Requisitos de Integridade
Os seguintes requisitos de integridade devem ser preservados durante toda a execução:

### RI-01
Nenhuma peça removida pode continuar sendo tratada como peça ativa.

### RI-02
Uma peça não pode formar par com ela mesma.

### RI-03
Uma tentativa de match inválido não pode remover peças.

### RI-04
A seleção atual não pode permanecer em estado impossível após processamento de jogada.

### RI-05
A partida não pode declarar vitória enquanto ainda houver condição lógica incompatível com o esvaziamento completo do tabuleiro.

### RI-06
A partida não pode declarar bloqueio enquanto houver ao menos um par válido restante.

---

## 2.13 Critérios Gerais de Aceitação do Sistema
O sistema será considerado compatível com este SRS quando, no mínimo:

- iniciar corretamente;
- gerar uma partida válida;
- permitir seleção apenas de peças elegíveis;
- validar corretamente o match;
- remover apenas pares válidos;
- detectar corretamente vitória;
- detectar corretamente ausência de jogadas;
- reiniciar corretamente a sessão;
- preservar a integridade do estado;
- apresentar comportamento coerente com os requisitos aqui definidos.

---

## 2.14 Critérios de Não Conformidade
A implementação será considerada não conforme com este SRS se ocorrer qualquer uma das situações abaixo:

- peça bloqueada for aceita como seleção válida;
- peça removida continuar selecionável;
- par inválido for removido;
- vitória for declarada fora da condição correta;
- bloqueio for declarado indevidamente;
- reinício herdar resíduos lógicos indevidos da sessão anterior;
- interface contradizer o estado lógico da partida;
- regras documentadas forem ignoradas pelo comportamento real do sistema.

---

## 2.15 Relacao com os Demais Documentos
Este documento deve ser lido em conjunto com:
- a Visão, que define o propósito do produto;
- as Regras de Negócio, que detalham a lógica formal;
- a Arquitetura, que define a distribuição das responsabilidades;
- o Modelo de Dados, que define as estruturas do domínio;
- o Plano de Testes e os Casos de Teste, que validarão esta especificação.

Nenhum desses documentos substitui este SRS, e este SRS não substitui o detalhamento lógico e estrutural dos demais.

---

## 2.16 Declaracao Oficial do SRS
O presente documento estabelece os requisitos oficiais do núcleo funcional do VitahAcre na Waterfall V2, servindo como contrato formal entre visão de produto, implementação técnica e validação.

Este documento fica congelado como referência oficial de requisitos do projeto, salvo revisão explícita em versão posterior da documentação.
