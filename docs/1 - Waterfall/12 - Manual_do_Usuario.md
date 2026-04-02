# 12 - Manual do Usuario

## 12.1 Objetivo do Documento
Este documento apresenta o Manual do Usuário oficial do projeto VitahAcre na Waterfall V2. Seu propósito é explicar, de forma clara e direta, como utilizar o jogo, compreender sua lógica básica, interagir corretamente com a interface e reconhecer os estados principais da partida.

Este manual deve permitir que qualquer usuário:
- abra o jogo e compreenda sua finalidade;
- entenda como selecionar peças;
- saiba o que caracteriza uma jogada válida;
- reconheça vitória, bloqueio e reinício;
- utilize o sistema sem depender de conhecimento técnico prévio.

---

## 12.2 O que e o VitahAcre
VitahAcre é um jogo de raciocínio baseado em pareamento lógico de peças organizadas em um tabuleiro por camadas.

O objetivo principal do jogador é remover todas as peças do tabuleiro formando pares válidos, obedecendo às regras de seleção definidas pelo sistema.

O jogo foi projetado para:
- funcionar em dispositivos Android;
- oferecer jogabilidade leve;
- utilizar renderização procedural;
- operar de forma clara, responsiva e objetiva.

---

## 12.3 Objetivo do Jogo
O objetivo da partida é remover todas as peças do tabuleiro até que nenhuma peça permaneça ativa.

Para isso, o jogador deve:
1. encontrar peças que possam ser selecionadas;
2. escolher uma primeira peça válida;
3. escolher uma segunda peça válida que forme par com a primeira;
4. repetir o processo até esvaziar o tabuleiro.

Quando todas as peças forem removidas, o jogador vence a partida.

---

## 12.4 Como Funciona a Logica Basica
Nem toda peça pode ser selecionada a qualquer momento.

Uma peça só pode ser escolhida quando estiver livre de acordo com a lógica do jogo.

De forma geral, uma peça precisa obedecer às seguintes condições para estar livre:
- não pode estar bloqueada por peça acima;
- precisa ter ao menos um lado livre.

Isso significa que o jogador deve observar o tabuleiro com atenção antes de tocar em uma peça.

---

## 12.5 Como Iniciar uma Partida
Ao abrir o VitahAcre:
1. o jogo inicializa;
2. o sistema gera um tabuleiro válido;
3. a partida entra em estado pronto;
4. o tabuleiro é exibido na tela.

A partir desse momento, o jogador já pode começar a interagir com as peças.

---

## 12.6 Como Selecionar Pecas
Para selecionar uma peça:
1. toque diretamente sobre a peça desejada;
2. o sistema verificará se ela é elegível;
3. se a peça estiver livre, ela será marcada visualmente como selecionada.

Se a peça não puder ser selecionada:
- nada útil acontecerá em termos de jogada;
- o jogo poderá apenas ignorar o toque ou indicar visualmente que a seleção não foi aceita, conforme a implementação final da interface.

---

## 12.7 Como Formar um Par
Depois de selecionar a primeira peça:
1. procure uma segunda peça que também esteja livre;
2. a segunda peça deve ser compatível com a primeira segundo a regra de pareamento do jogo;
3. toque na segunda peça.

Se o par for válido:
- as duas peças serão removidas do tabuleiro.

Se o par não for válido:
- as peças não serão removidas;
- a seleção será tratada conforme a lógica definida pelo sistema.

---

## 12.8 O que e uma Jogada Valida
Uma jogada válida acontece quando:
- a primeira peça pode ser selecionada;
- a segunda peça pode ser selecionada;
- ambas formam um par válido segundo as regras do jogo.

Somente quando essas condições forem atendidas a remoção ocorrerá.

---

## 12.9 O que e uma Jogada Invalida
Uma jogada é inválida quando ocorre qualquer uma das situações abaixo:
- tentativa de selecionar peça bloqueada;
- tentativa de selecionar peça já removida;
- tentativa de formar par entre peças incompatíveis;
- tentativa de usar a mesma peça duas vezes como se fosse um par.

Em jogadas inválidas, o sistema não deve remover peças de forma incorreta.

---

## 12.10 Feedback Visual Esperado
Durante a partida, o jogador poderá observar sinais visuais importantes, como:
- destaque de peça selecionada;
- desaparecimento de peças removidas;
- indicação de vitória;
- indicação de bloqueio;
- reconstrução visual do tabuleiro após reinício.

A forma exata desses efeitos pode variar conforme a evolução visual do projeto, mas a lógica funcional permanece a mesma.

---

## 12.11 Estado de Vitoria
A vitória acontece quando todas as peças do tabuleiro foram removidas com sucesso.

Quando isso ocorrer:
- a partida será encerrada como vencida;
- o sistema exibirá o estado visual correspondente;
- o jogador poderá reiniciar para jogar novamente.

---

## 12.12 Estado Sem Jogadas
Pode haver situação em que ainda existam peças no tabuleiro, mas nenhuma jogada válida esteja disponível.

Quando isso ocorrer:
- o jogo entra em estado de bloqueio;
- esse estado indica que não há mais pares livres jogáveis;
- o jogador deverá reiniciar a partida para continuar em um novo tabuleiro.

---

## 12.13 Como Reiniciar a Partida
Quando desejar começar uma nova partida:
1. utilize o comando de reinício disponível na interface;
2. o jogo descartará o estado atual;
3. um novo tabuleiro será gerado;
4. a partida retornará ao estado pronto.

O reinício pode ser usado:
- após vitória;
- após bloqueio;
- ou a qualquer momento permitido pela interface final.

---

## 12.14 Comportamentos Esperados do Sistema
O usuário deve esperar que o jogo:
- responda ao toque de forma rápida;
- permita apenas jogadas válidas;
- impeça remoções incorretas;
- mantenha consistência visual com o estado lógico da partida;
- permita reinício limpo da sessão.

---

## 12.15 Dicas de Uso para o Jogador
Para jogar melhor:
- observe primeiro as peças mais expostas;
- procure peças sem bloqueio superior;
- verifique se ao menos um lado está livre;
- evite tocar aleatoriamente sem analisar o tabuleiro;
- planeje pares possíveis antes de executar a seleção.

Jogadas conscientes ajudam a evitar estados de bloqueio.

---

## 12.16 Problemas que Nao Devem Ocorrer
Em funcionamento correto, o usuário não deve encontrar:
- peça bloqueada sendo aceita como livre;
- peça removida continuando ativa;
- vitória declarada antes da hora;
- travamento ao reiniciar;
- desaparecimento incorreto de peças não pareadas.

Se algum desses comportamentos ocorrer, trata-se de falha de implementação e não de funcionamento esperado.

---

## 12.17 Publico-Alvo do Manual
Este manual foi escrito para:
- jogadores finais;
- testadores;
- validadores funcionais;
- participantes do projeto que precisem compreender o uso do jogo sem entrar no código.

---

## 12.18 Limites deste Manual
Este documento não explica:
- detalhes internos de arquitetura;
- implementação técnica em Kotlin;
- estrutura do código-fonte;
- estratégia de testes automatizados;
- estratégia de build e deploy.

Esses assuntos pertencem aos demais documentos da pasta Waterfall V2.

---

## 12.19 Relacao com os Demais Documentos
Este manual representa a visão do sistema do ponto de vista do usuário final.

Ele se relaciona com:
- a Visão, ao refletir o propósito do produto;
- o SRS, ao refletir os comportamentos esperados;
- as Regras de Negócio, ao refletir a lógica do jogo;
- o GDD e a UI/UX, ao refletir a experiência percebida;
- o Plano de Testes e os Casos de Teste, ao apoiar validação funcional.

---

## 12.20 Conclusao
O VitahAcre foi concebido para oferecer uma experiência clara, lógica e acessível de pareamento de peças em tabuleiro por camadas.

O usuário deve interagir com o jogo observando o estado das peças, formando pares válidos e removendo gradualmente todo o tabuleiro até alcançar a vitória.

Quando não houver mais jogadas possíveis, o sistema deve sinalizar o bloqueio e permitir reinício da experiência.

Este manual fica congelado como referência oficial de uso do VitahAcre, salvo revisão explícita em versão posterior da documentação.
