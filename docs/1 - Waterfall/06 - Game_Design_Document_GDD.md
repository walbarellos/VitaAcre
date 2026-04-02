# 06 - Game Design Document GDD

## 6.1 Objetivo do Documento
Este documento define o Game Design Document oficial do projeto VitahAcre na Waterfall V2. Seu propósito é formalizar a experiência de jogo, as mecânicas centrais, a estrutura da partida, a progressão interna da sessão, a lógica de apresentação do tabuleiro e os princípios de design que orientam a interação do jogador com o sistema.

Este documento deve:
- traduzir a visão do produto em experiência jogável;
- conectar regras de negócio à experiência percebida pelo jogador;
- orientar decisões de layout, feedback e fluxo;
- servir de ponte entre domínio, interface e implementação;
- sustentar coerência entre jogabilidade, arquitetura e testes.

---

## 6.2 Natureza do Jogo
VitahAcre é um jogo de raciocínio e leitura espacial baseado em:
- seleção de peças;
- análise de bloqueios;
- identificação de pares válidos;
- remoção progressiva do tabuleiro;
- busca pelo esvaziamento completo da estrutura.

O jogo é centrado em atenção, lógica e observação do espaço, e não em reflexos rápidos, combate, narrativa ou exploração aberta.

---

## 6.3 Objetivo da Partida
O objetivo de cada partida é remover todas as peças ativas do tabuleiro por meio da formação de pares válidos, obedecendo às regras de elegibilidade e pareamento definidas pelo domínio do sistema.

A vitória acontece quando:
- não restam peças ativas.

O bloqueio acontece quando:
- ainda restam peças ativas;
- não existe mais jogada válida disponível.

---

## 6.4 Fantasia Funcional do Jogador
A fantasia funcional do jogador no VitahAcre não é a de dominar personagens, territórios ou narrativa, mas a de:
- compreender a estrutura do tabuleiro;
- descobrir padrões válidos;
- destravar peças bloqueadas;
- limpar a composição progressivamente;
- resolver uma configuração espacial por meio de decisões corretas.

O prazer central do jogo deve nascer da sensação de ordem extraída do aparente emaranhado visual.

---

## 6.5 Loop Principal de Jogo
O loop principal oficial do VitahAcre é o seguinte:

1. observar o tabuleiro;
2. identificar peças potencialmente livres;
3. selecionar a primeira peça válida;
4. localizar uma segunda peça compatível;
5. formar ou tentar formar um par;
6. remover peças quando o match for válido;
7. atualizar a leitura espacial do tabuleiro;
8. repetir o processo até vitória, bloqueio ou reinício.

Este loop é o coração da experiência.

---

## 6.6 Mecânicas Centrais

### 6.6.1 Mecânica de Observação
O jogador deve analisar:
- quais peças estão livres;
- quais peças estão bloqueadas;
- quais pares são possíveis;
- quais remoções abrirão novas possibilidades.

### 6.6.2 Mecânica de Seleção
O jogador interage com o sistema tocando em peças elegíveis.

A seleção é o mecanismo de entrada principal da partida.

### 6.6.3 Mecânica de Pareamento
O progresso só ocorre quando duas peças compatíveis e elegíveis são selecionadas de forma válida.

### 6.6.4 Mecânica de Desbloqueio Indireto
Ao remover um par, o jogador altera a estrutura do tabuleiro e pode liberar novas peças antes bloqueadas.

### 6.6.5 Mecânica de Resolução
A partida evolui como resolução gradual de uma estrutura espacial, e não como acúmulo de recursos, níveis ou combate.

---

## 6.7 Estrutura da Partida

## 6.7.1 Início da Partida
A partida começa quando:
- o sistema conclui a geração de um tabuleiro válido;
- o estado entra em condição jogável;
- o tabuleiro é apresentado ao usuário.

## 6.7.2 Meio da Partida
O meio da partida consiste na alternância contínua entre:
- leitura do espaço;
- seleção;
- pareamento;
- remoção;
- reconfiguração da leitura do tabuleiro.

## 6.7.3 Final da Partida
A partida termina quando:
- todas as peças forem removidas; ou
- não existirem mais jogadas válidas; ou
- o usuário acionar reinício e iniciar nova sessão.

---

## 6.8 Estado Inicial da Experiência
A experiência de cada sessão deve começar com:
- tabuleiro visualmente legível;
- partida formalmente pronta;
- pelo menos uma jogada válida inicial;
- ausência de confusão sobre a possibilidade de interação.

O jogador deve sentir que a sessão já nasceu jogável.

---

## 6.9 Estrutura Espacial do Tabuleiro
O tabuleiro do VitahAcre deve ser compreendido como uma composição espacial por camadas, onde:
- peças possuem posição horizontal;
- peças possuem posição vertical;
- peças podem estar em níveis superiores;
- a remoção de peças altera o espaço de decisão do jogador.

A estrutura não é apenas decorativa. Ela é o próprio problema lógico que o jogador precisa resolver.

---

## 6.10 Papel das Camadas
As camadas existem para:
- criar bloqueio superior;
- adicionar profundidade espacial;
- dificultar a leitura simples do tabuleiro;
- tornar a remoção de peças relevante para o desbloqueio futuro.

Sem camadas, a experiência perderia parte significativa de sua identidade estratégica.

---

## 6.11 Papel dos Lados Livres
A noção de lado livre é uma das bases do design do jogo.

Ela existe para:
- limitar a seleção;
- forçar leitura de bordas e vizinhança;
- impedir escolha arbitrária de qualquer peça;
- criar decisões sobre ordem de remoção.

O jogador não deve apenas procurar pares iguais; deve procurar pares possíveis.

---

## 6.12 Natureza da Dificuldade
A dificuldade do VitahAcre deve emergir principalmente de:
- densidade do tabuleiro;
- distribuição espacial das peças;
- quantidade de bloqueios;
- previsibilidade ou não dos desbloqueios;
- exigência de leitura antecipada.

A dificuldade não deve depender de:
- velocidade extrema;
- reflexos;
- punição temporal agressiva;
- interface confusa.

---

## 6.13 Dificuldade Base da Versão Inicial
Na versão base do projeto, a dificuldade deve priorizar:
- clareza do sistema;
- boa compreensão do loop principal;
- legibilidade das regras;
- espaço para o jogador aprender a lógica do tabuleiro.

O jogo pode ser desafiador, mas não deve ser obscuro.

---

## 6.14 Curva de Compreensão
A curva de compreensão ideal da experiência inicial é:

### Etapa 1
O jogador entende que nem toda peça pode ser tocada.

### Etapa 2
O jogador percebe que precisa encontrar peças livres.

### Etapa 3
O jogador entende que precisa formar pares compatíveis.

### Etapa 4
O jogador percebe que remover pares altera a estrutura e abre novas opções.

### Etapa 5
O jogador internaliza o ciclo estratégico da partida.

---

## 6.15 Clareza de Regras na Experiência
Mesmo que as regras internas sejam formais e técnicas, a experiência do jogador deve comunicar, de maneira intuitiva, pelo menos o seguinte:
- peças bloqueadas não são selecionáveis;
- peças livres respondem ao toque;
- pares válidos desaparecem;
- pares inválidos não avançam o jogo;
- vitória e bloqueio são estados distintos.

A experiência deve ensinar sem depender de texto excessivo.

---

## 6.16 Feedback de Jogabilidade
O design do jogo depende fortemente de feedback perceptível.

O jogador deve perceber:
- quando uma peça foi selecionada;
- quando uma tentativa falhou;
- quando um par foi aceito;
- quando peças foram removidas;
- quando a partida terminou em vitória;
- quando a partida terminou em ausência de jogadas.

Sem feedback claro, a lógica do jogo se torna opaca.

---

## 6.17 Ritmo da Partida
O ritmo ideal da partida deve ser:
- contínuo;
- contemplativo;
- responsivo;
- não frenético.

O tempo de decisão deve ser dominado pela análise do jogador, não por urgência imposta pelo sistema.

---

## 6.18 Relação entre Jogador e Erro
O erro no VitahAcre não deve ser tratado como falha moral, mas como parte da exploração lógica da estrutura.

Tentativas inválidas devem:
- preservar integridade da partida;
- comunicar rejeição;
- permitir continuação clara do raciocínio.

O jogo não deve humilhar o jogador por explorar hipóteses.

---

## 6.19 Estado de Vitória como Resolução
A vitória deve ser percebida como resolução completa da estrutura.

Ela representa:
- esvaziamento do tabuleiro;
- sucesso de leitura;
- domínio da ordem de remoção;
- conclusão lógica da sessão.

---

## 6.20 Estado Sem Jogadas como Impasse
O estado sem jogadas deve ser percebido como impasse estrutural.

Ele representa:
- existência de peças remanescentes;
- ausência de pares elegíveis válidos;
- interrupção lógica da partida.

O jogo deve comunicar que não se trata de vitória parcial, mas de bloqueio real.

---

## 6.21 Reinício como Renovação de Sessão
O reinício não é continuação da partida anterior.

Ele representa:
- abandono da configuração atual;
- criação de nova estrutura;
- reinício limpo da experiência.

O jogador deve compreender o reinício como abertura de nova tentativa, e não como retorno a um ponto intermediário da sessão anterior.

---

## 6.22 Princípios de Legibilidade do Tabuleiro
O tabuleiro deve ser desenhado e organizado de forma que:
- o jogador consiga distinguir peças individuais;
- a noção de camada seja perceptível;
- a seleção seja visualmente clara;
- peças removidas não permaneçam ambíguas;
- o espaço jogável seja interpretável.

A dificuldade deve vir da lógica do problema, não da confusão visual.

---

## 6.23 Princípios de Justiça do Design
O jogo deve ser justo em sua lógica.

Isso significa que:
- regras válidas devem produzir resultados previsíveis;
- regras inválidas devem ser rejeitadas com consistência;
- o sistema não deve parecer arbitrário;
- peças com comportamento equivalente devem obedecer ao mesmo critério.

---

## 6.24 Rejogabilidade
A rejogabilidade do VitahAcre deve nascer principalmente de:
- novas configurações de tabuleiro;
- novas distribuições espaciais;
- novas sequências de desbloqueio;
- possibilidade de reiniciar sessões com nova estrutura.

A repetição da experiência deve se sustentar por variação estrutural, não por narrativa emergente complexa.

---

## 6.25 Potenciais Vetores de Evolução Futura
Sem alterar o núcleo lógico, o design pode evoluir futuramente com:
- variações de layout;
- modos com diferentes densidades;
- modos com parâmetros de dificuldade;
- temas visuais procedurais;
- estatísticas de sessão;
- pistas opcionais;
- contadores ou metas auxiliares.

Essas evoluções não pertencem ao núcleo obrigatório da versão base, mas são compatíveis com o design.

---

## 6.26 Condições de Falha de Design
O GDD será considerado violado se ocorrer qualquer uma das seguintes situações:
- o jogo deixar de depender de leitura espacial;
- qualquer peça puder ser escolhida sem restrição lógica;
- a camada visual confundir o jogador a ponto de ocultar a estrutura;
- a dificuldade depender principalmente de pressa ou reflexo;
- a experiência deixar de comunicar com clareza vitória, bloqueio e seleção.

---

## 6.27 Critérios de Conformidade do Design
O design será considerado coerente com este documento quando:
- o loop principal estiver claro;
- a experiência reforçar as regras do domínio;
- o tabuleiro servir como problema lógico real;
- a remoção alterar significativamente a leitura espacial;
- a seleção depender da estrutura do tabuleiro;
- a vitória representar resolução completa;
- o bloqueio representar impasse lógico real;
- a experiência permanecer clara, justa e responsiva.

---

## 6.28 Relação com os Demais Documentos
Este documento:
- traduz a Visão em experiência jogável;
- depende dos Requisitos do SRS;
- se ancora nas Regras de Negócio;
- se apoia na Arquitetura e no Modelo de Dados;
- prepara decisões de UI/UX;
- fundamenta partes importantes da Implementação Técnica e dos Testes.

Nenhum documento posterior deve contradizer a lógica experiencial aqui definida sem revisão explícita desta base.

---

## 6.29 Declaração Oficial do GDD
O presente documento estabelece o Game Design Document oficial do VitahAcre na Waterfall V2.

Ele define a forma oficial da experiência de jogo, suas mecânicas centrais, sua estrutura de sessão e seus princípios de design, ficando congelado como referência formal do projeto, salvo revisão explícita em versão posterior da documentação.
