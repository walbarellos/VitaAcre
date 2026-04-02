# 01 - Visao

## 1.1 Objetivo do Documento
Este documento define a visão oficial do projeto VitahAcre, estabelecendo sua finalidade, seu escopo macro, seu público-alvo, suas restrições fundamentais e os princípios que orientam sua construção.

A função deste documento é servir como porta de entrada conceitual de todo o projeto, oferecendo uma leitura clara e estável do que o produto é, do que ele pretende entregar e do que não faz parte de sua proposta oficial.

Este documento deve orientar todos os demais documentos da pasta Waterfall V2.

---

## 1.2 Nome do Projeto
**VitahAcre**

---

## 1.3 Natureza do Produto
O VitahAcre é um jogo digital para dispositivos Android, inspirado na lógica de pareamento de peças em tabuleiro estruturado em camadas, com foco em raciocínio, leitura visual do espaço e remoção progressiva de pares válidos até o esvaziamento completo do tabuleiro.

O projeto foi concebido para operar com renderização procedural, arquitetura modular e regras determinísticas, evitando dependência estrutural de assets externos como base do núcleo da experiência.

---

## 1.4 Proposta Central do Produto
A proposta central do VitahAcre é oferecer uma experiência de jogo:
- leve;
- lógica;
- responsiva;
- totalmente jogável em ambiente mobile;
- organizada de forma tecnicamente limpa;
- apta a ser construída de modo incremental com base documental rígida.

O jogo deve ser compreensível para o usuário final, porém tecnicamente sólido o suficiente para permitir evolução estruturada em ciclos posteriores de implementação.

---

## 1.5 Problema que o Projeto Resolve
O projeto busca responder à ausência de uma base de jogo desse tipo construída com as seguintes características reunidas em conjunto:
- foco em dispositivos Android;
- estrutura documental forte antes da codificação;
- regras claramente formalizadas;
- renderização baseada em código;
- possibilidade de evolução modular em estilo LEGO;
- independência de uma base pesada de assets externos para o núcleo funcional.

Além do produto em si, o projeto também resolve um problema metodológico: criar uma fundação documental suficientemente clara para permitir geração e encaixe progressivo de código com previsibilidade.

---

## 1.6 Objetivo do Produto
O objetivo do VitahAcre é permitir que o jogador remova todas as peças do tabuleiro por meio da seleção de pares válidos, obedecendo às regras de elegibilidade, pareamento e bloqueio definidas pelo sistema.

Do ponto de vista do produto, o objetivo é entregar:
- uma partida iniciável;
- um tabuleiro válido;
- interação por toque;
- seleção controlada;
- validação de pares;
- remoção de peças;
- detecção de vitória;
- detecção de bloqueio;
- reinício de partida.

Do ponto de vista de engenharia, o objetivo é entregar uma base clara para desenvolvimento progressivo, testável e sustentável.

---

## 1.7 Público-Alvo
O público-alvo principal do VitahAcre é composto por usuários que:
- utilizam dispositivos Android;
- apreciam jogos de raciocínio e atenção;
- preferem experiências claras e de baixa complexidade operacional;
- valorizam partidas rápidas ou de duração moderada;
- podem utilizar aparelhos de entrada ou intermediários.

O projeto também possui utilidade indireta para:
- testadores;
- desenvolvedores;
- documentadores;
- participantes do projeto que precisem compreender o sistema de forma organizada.

---

## 1.8 Plataforma-Alvo
A plataforma principal do VitahAcre é:

- **Sistema operacional:** Android
- **Formato de interação:** toque em tela
- **Execução principal:** dispositivos móveis

O projeto será estruturado para ambiente Android moderno, com base técnica orientada a Kotlin, Jetpack Compose e Canvas procedural, conforme definido nos documentos técnicos posteriores.

---

## 1.9 Experiência Pretendida
A experiência pretendida do VitahAcre deve ser:

### 1.9.1 Clara
O jogador deve conseguir compreender o que está acontecendo no tabuleiro sem ambiguidade estrutural.

### 1.9.2 Responsiva
Os toques devem produzir resposta perceptível com rapidez suficiente para transmitir controle.

### 1.9.3 Lógica
O jogo não deve parecer arbitrário. As regras devem produzir comportamento consistente.

### 1.9.4 Leve
O produto deve evitar peso desnecessário em memória, processamento e estrutura visual.

### 1.9.5 Rejogável
Mesmo mantendo núcleo simples, o jogo deve permitir repetição por meio de nova geração de partidas e variações estruturais futuras.

---

## 1.10 Escopo Funcional Macro
O escopo macro do VitahAcre inclui, no mínimo, os seguintes comportamentos:

- iniciar uma partida;
- gerar um tabuleiro válido;
- exibir o tabuleiro ao usuário;
- permitir toque em peças;
- validar elegibilidade de seleção;
- registrar primeira seleção;
- registrar segunda seleção;
- validar match;
- remover pares válidos;
- atualizar o estado da partida;
- detectar vitória;
- detectar estado sem jogadas;
- permitir reinício.

Esses comportamentos serão detalhados formalmente no documento de requisitos.

---

## 1.11 Fora de Escopo
Neste momento, o projeto não possui como escopo obrigatório:

- modo multiplayer;
- login de usuário;
- ranking online;
- sincronização em nuvem;
- monetização obrigatória;
- anúncios;
- loja interna;
- dependência funcional de servidor remoto;
- dependência estrutural de assets externos como base do núcleo do jogo;
- editor de fases para usuário final;
- sistema social;
- chat;
- integração com backend online.

Esses pontos não fazem parte do núcleo obrigatório da versão base do projeto.

---

## 1.12 Diferenciais do Projeto
Os diferenciais pretendidos do VitahAcre são:

### 1.12.1 Base documental forte
O projeto nasce com documentação estruturada antes da codificação pesada.

### 1.12.2 Núcleo duro de regras
As regras do jogo serão descritas de forma determinística e testável.

### 1.12.3 Arquitetura modular
O sistema será pensado para crescimento por encaixe progressivo.

### 1.12.4 Renderização procedural
O núcleo visual do jogo deve ser produzido por código, reduzindo dependência externa.

### 1.12.5 Compatibilidade metodológica com o método caracol
Embora a base esteja organizada em Waterfall, a evolução futura deve ocorrer por ciclos controlados de refinamento incremental.

---

## 1.13 Restrições Fundamentais
O projeto deve obedecer às seguintes restrições fundamentais:

### RF-01
O núcleo do sistema deve ser jogável em Android.

### RF-02
As regras devem ser determinísticas.

### RF-03
A renderização principal deve ser compatível com abordagem procedural.

### RF-04
A arquitetura não deve misturar regra de negócio com camada visual.

### RF-05
O estado do jogo deve ser tratável como fonte única da verdade.

### RF-06
O projeto deve permitir crescimento incremental posterior sem colapso estrutural.

### RF-07
O núcleo funcional não deve depender de conectividade de rede para operar.

---

## 1.14 Princípios de Construção
O projeto será guiado pelos seguintes princípios:

- clareza antes de velocidade;
- estrutura antes de improviso;
- separação antes de acoplamento;
- previsibilidade antes de complexidade desnecessária;
- testabilidade antes de expansão;
- documentação confiável antes de automação de código;
- evolução incremental depois de fundação estável.

---

## 1.15 Critérios Gerais de Sucesso do Produto
O produto será considerado bem direcionado se conseguir atingir os seguintes resultados gerais:

- o usuário entende o objetivo da partida;
- a partida pode ser iniciada sem confusão;
- as regras produzem comportamento coerente;
- o fluxo de seleção e remoção funciona;
- a vitória pode ser alcançada corretamente;
- o bloqueio pode ser detectado corretamente;
- o reinício funciona de forma limpa;
- a base técnica permite continuidade organizada do projeto.

---

## 1.16 Riscos Estratégicos a Evitar
Os seguintes riscos estratégicos devem ser evitados desde o início:

### RE-01
Começar a codificação pesada antes de consolidar a base documental.

### RE-02
Misturar decisões de interface com regras centrais do jogo.

### RE-03
Criar dependência excessiva de elementos externos para funções centrais.

### RE-04
Permitir inconsistência entre documentos fundamentais.

### RE-05
Perder a capacidade de expansão modular por decisões apressadas de implementação.

---

## 1.17 Posicionamento Metodológico
A pasta Waterfall V2 representa a fundação formal e congelável do projeto.

Após sua consolidação, a implementação deverá evoluir de forma compatível com o método caracol, permitindo:
- ciclos pequenos;
- validação progressiva;
- encaixe modular;
- refinamento contínuo;
- redução de retrabalho estrutural.

Assim, o Waterfall V2 não é o fim do método, mas a base de verdade sobre a qual o crescimento incremental poderá ocorrer com segurança.

---

## 1.18 Declaracao Oficial de Visao
O VitahAcre é oficialmente definido, nesta fase do projeto, como um jogo mobile Android de pareamento lógico em tabuleiro por camadas, com renderização procedural, regras determinísticas, arquitetura modular e base documental rígida, orientado a uma implementação incremental futura sustentada por núcleo duro de especificação.

Este documento fica estabelecido como a visão oficial do projeto, salvo revisão explícita em versão posterior da documentação.
