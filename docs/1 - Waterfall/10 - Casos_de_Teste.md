# 10 - Casos de Teste

## 10.1 Objetivo do Documento
Este documento define os Casos de Teste oficiais do projeto VitahAcre na Waterfall V2. Seu propósito é transformar o Plano de Testes em cenários concretos, reproduzíveis e verificáveis, permitindo validar o comportamento real do sistema em condições controladas.

Este documento deve:
- materializar a estratégia de testes em execuções práticas;
- proteger o núcleo lógico contra regressões;
- verificar aderência ao SRS;
- verificar aderência às Regras de Negócio;
- verificar aderência à Arquitetura e ao Modelo de Dados;
- apoiar a evolução incremental segura do projeto.

---

## 10.2 Estrutura Padrão dos Casos de Teste
Cada caso de teste deste documento deve conter, no mínimo:

- identificador único;
- nome do caso;
- objetivo;
- pré-condições;
- dados de entrada;
- passos de execução;
- resultado esperado;
- severidade em caso de falha.

---

## 10.3 Convenções de Identificação
Os casos de teste são agrupados pelos seguintes prefixos:

- `CT-RN` — Regras de Negócio
- `CT-ST` — Estado
- `CT-CT` — Controller
- `CT-GE` — Geração de Tabuleiro
- `CT-RD` — Renderização
- `CT-FL` — Fluxo Funcional
- `CT-PF` — Performance

---

## 10.4 Casos de Teste de Regras de Negócio

### CT-RN-01 - Peça livre sem bloqueio superior e com lado livre
**Objetivo**  
Validar que uma peça seja considerada livre quando não houver peça acima e houver ao menos um lado livre.

**Pré-condições**  
- tabuleiro carregado;
- peça alvo existente e não removida.

**Dados de entrada**  
- peça sem peça acima;
- sem peça à esquerda ou sem peça à direita.

**Passos**
1. selecionar a peça alvo para avaliação lógica;
2. executar a função de verificação de peça livre.

**Resultado esperado**  
A função deve retornar verdadeiro.

**Severidade**  
Alta.

---

### CT-RN-02 - Peça bloqueada por peça superior
**Objetivo**  
Validar que uma peça não seja considerada livre quando houver bloqueio em camada superior.

**Pré-condições**  
- tabuleiro carregado;
- peça inferior existente;
- peça superior ativa em posição de bloqueio.

**Dados de entrada**  
- peça alvo com peça acima.

**Passos**
1. selecionar a peça alvo;
2. executar a verificação de peça livre.

**Resultado esperado**  
A função deve retornar falso.

**Severidade**  
Alta.

---

### CT-RN-03 - Peça bloqueada lateralmente
**Objetivo**  
Validar que uma peça sem peça acima, mas bloqueada dos dois lados, não seja elegível.

**Pré-condições**  
- peça alvo não removida;
- peça à esquerda ativa;
- peça à direita ativa;
- ausência de peça acima.

**Dados de entrada**  
- peça alvo central bloqueada lateralmente.

**Passos**
1. localizar a peça alvo;
2. executar a verificação de peça livre.

**Resultado esperado**  
A função deve retornar falso.

**Severidade**  
Alta.

---

### CT-RN-04 - Seleção de peça removida
**Objetivo**  
Validar que uma peça já removida não possa ser selecionada.

**Pré-condições**  
- peça marcada como removida.

**Dados de entrada**  
- referência da peça removida.

**Passos**
1. solicitar seleção da peça;
2. executar validação de elegibilidade.

**Resultado esperado**  
A seleção deve ser rejeitada.

**Severidade**  
Alta.

---

### CT-RN-05 - Match válido entre duas peças livres iguais
**Objetivo**  
Validar que duas peças distintas, livres e com mesmo identificador lógico formem par válido.

**Pré-condições**  
- duas peças livres;
- ambas não removidas;
- mesmo `pairKey`.

**Dados de entrada**  
- primeira peça;
- segunda peça.

**Passos**
1. selecionar a primeira peça;
2. selecionar a segunda peça;
3. executar a validação de match.

**Resultado esperado**  
A função deve retornar match válido.

**Severidade**  
Crítica.

---

### CT-RN-06 - Match inválido entre peças diferentes
**Objetivo**  
Validar que duas peças com identificadores lógicos diferentes não formem match.

**Pré-condições**  
- duas peças livres;
- `pairKey` diferente.

**Dados de entrada**  
- primeira peça;
- segunda peça.

**Passos**
1. selecionar as duas peças;
2. executar a validação de match.

**Resultado esperado**  
A função deve retornar match inválido.

**Severidade**  
Alta.

---

### CT-RN-07 - Match inválido com a mesma peça referenciada duas vezes
**Objetivo**  
Validar que uma peça não possa formar par com ela mesma.

**Pré-condições**  
- peça livre e não removida.

**Dados de entrada**  
- mesma referência passada como primeiro e segundo elemento.

**Passos**
1. enviar a mesma peça duas vezes para validação;
2. executar a verificação de match.

**Resultado esperado**  
A função deve retornar match inválido.

**Severidade**  
Alta.

---

## 10.5 Casos de Teste de Estado

### CT-ST-01 - Atualização de estado após primeira seleção
**Objetivo**  
Validar a transição correta de estado ao selecionar a primeira peça válida.

**Pré-condições**  
- GameState em `PRONTO`;
- peça livre disponível.

**Dados de entrada**  
- evento de toque correspondente à peça.

**Passos**
1. enviar o toque ao controller;
2. processar a seleção.

**Resultado esperado**  
- a peça deve entrar em `selection.firstSelectedTileId`;
- o status deve refletir primeira seleção;
- nenhuma remoção deve ocorrer.

**Severidade**  
Alta.

---

### CT-ST-02 - Atualização de estado após match válido
**Objetivo**  
Validar que o estado seja atualizado corretamente após um par válido.

**Pré-condições**  
- duas peças livres e iguais;
- primeira peça já selecionada.

**Dados de entrada**  
- evento de toque da segunda peça.

**Passos**
1. processar a segunda seleção;
2. validar match;
3. remover peças;
4. atualizar o estado.

**Resultado esperado**  
- ambas as peças devem ficar marcadas como removidas;
- a seleção deve ser esvaziada;
- o status deve sair do processamento para continuidade, vitória ou bloqueio.

**Severidade**  
Crítica.

---

### CT-ST-03 - Estado preservado após match inválido
**Objetivo**  
Validar o comportamento do estado quando duas peças não formam par.

**Pré-condições**  
- primeira peça válida selecionada;
- segunda peça válida, mas incompatível.

**Dados de entrada**  
- segunda peça com `pairKey` diferente.

**Passos**
1. selecionar a segunda peça;
2. validar o match.

**Resultado esperado**  
- nenhuma peça deve ser removida;
- a seleção deve ser resolvida conforme política do fluxo;
- o GameState deve permanecer íntegro.

**Severidade**  
Alta.

---

### CT-ST-04 - Vitória ao remover todas as peças
**Objetivo**  
Validar transição para estado de vitória.

**Pré-condições**  
- resta apenas um último par válido no tabuleiro.

**Dados de entrada**  
- seleção das duas últimas peças.

**Passos**
1. formar o match final;
2. processar a remoção;
3. verificar condição de vitória.

**Resultado esperado**  
O GameState deve entrar em `VITORIA`.

**Severidade**  
Crítica.

---

### CT-ST-05 - Estado sem jogadas
**Objetivo**  
Validar a detecção de bloqueio quando ainda existem peças, mas nenhum par válido.

**Pré-condições**  
- tabuleiro com peças restantes;
- inexistência de pares livres válidos.

**Dados de entrada**  
- GameState nessa configuração.

**Passos**
1. executar a verificação de jogadas disponíveis.

**Resultado esperado**  
O GameState deve entrar em `SEM_JOGADAS`.

**Severidade**  
Crítica.

---

## 10.6 Casos de Teste do Controller

### CT-CT-01 - Toque em área sem peça
**Objetivo**  
Validar o comportamento do controller ao receber toque que não corresponde a nenhuma peça.

**Pré-condições**  
- tabuleiro carregado.

**Dados de entrada**  
- coordenada vazia.

**Passos**
1. enviar InputEvent para área sem tile;
2. processar o evento.

**Resultado esperado**  
- nenhuma peça deve ser selecionada;
- o estado não deve ser corrompido;
- o sistema não deve falhar.

**Severidade**  
Média.

---

### CT-CT-02 - Toque em peça bloqueada
**Objetivo**  
Validar rejeição de toque sobre peça inelegível.

**Pré-condições**  
- peça bloqueada presente no tabuleiro.

**Dados de entrada**  
- toque na coordenada da peça bloqueada.

**Passos**
1. capturar toque;
2. localizar peça;
3. validar elegibilidade.

**Resultado esperado**  
- a peça não deve ser selecionada;
- o estado deve permanecer coerente.

**Severidade**  
Alta.

---

### CT-CT-03 - Reinício de partida
**Objetivo**  
Validar fluxo completo de reinício.

**Pré-condições**  
- partida em andamento ou encerrada.

**Dados de entrada**  
- comando de reinício.

**Passos**
1. enviar evento de reinício;
2. gerar novo tabuleiro;
3. criar novo GameState;
4. renderizar nova partida.

**Resultado esperado**  
- novo estado íntegro;
- sem resíduos da partida anterior;
- status em `PRONTO`.

**Severidade**  
Crítica.

---

## 10.7 Casos de Teste do Gerador de Tabuleiro

### CT-GE-01 - Geração com número par de peças
**Objetivo**  
Validar que o gerador nunca crie quantidade ímpar de peças.

**Pré-condições**  
- configuração válida de tabuleiro.

**Dados de entrada**  
- BoardConfiguration padrão.

**Passos**
1. executar a geração;
2. contar o total de peças.

**Resultado esperado**  
O número total de peças deve ser par.

**Severidade**  
Crítica.

---

### CT-GE-02 - Geração sem duplicidade de posição absoluta
**Objetivo**  
Validar que duas peças não ocupem a mesma posição absoluta.

**Pré-condições**  
- tabuleiro recém-gerado.

**Dados de entrada**  
- lista de peças geradas.

**Passos**
1. percorrer todas as peças;
2. verificar combinações `x`, `y`, `layer`.

**Resultado esperado**  
Não deve haver duplicidade de posição absoluta.

**Severidade**  
Crítica.

---

### CT-GE-03 - Geração com pares coerentes
**Objetivo**  
Validar que os identificadores lógicos sejam distribuídos em pares válidos.

**Pré-condições**  
- tabuleiro recém-gerado.

**Dados de entrada**  
- lista de peças do tabuleiro.

**Passos**
1. agrupar peças por `pairKey`;
2. contar ocorrências por grupo.

**Resultado esperado**  
Cada `pairKey` deve obedecer à política de pareamento definida.

**Severidade**  
Alta.

---

### CT-GE-04 - Tabuleiro inicial jogável
**Objetivo**  
Validar que o tabuleiro gerado possua jogadas iniciais possíveis.

**Pré-condições**  
- geração concluída.

**Dados de entrada**  
- GameState inicial.

**Passos**
1. executar a verificação de jogadas disponíveis logo após a geração.

**Resultado esperado**  
O estado inicial não pode nascer em `SEM_JOGADAS`.

**Severidade**  
Crítica.

---

### CT-GE-05 - Reprodutibilidade com seed
**Objetivo**  
Validar que a mesma seed gere o mesmo tabuleiro, quando esse recurso estiver habilitado.

**Pré-condições**  
- mecanismo de seed implementado.

**Dados de entrada**  
- mesma configuração;
- mesma seed.

**Passos**
1. gerar tabuleiro A;
2. gerar tabuleiro B com os mesmos parâmetros;
3. comparar as saídas.

**Resultado esperado**  
Ambos os tabuleiros devem ser equivalentes.

**Severidade**  
Média.

---

## 10.8 Casos de Teste de Renderização

### CT-RD-01 - Destaque visual da primeira seleção
**Objetivo**  
Validar que a UI represente a seleção corretamente.

**Pré-condições**  
- uma peça válida selecionada.

**Dados de entrada**  
- GameState com seleção preenchida.

**Passos**
1. renderizar a tela;
2. observar a peça selecionada.

**Resultado esperado**  
A peça selecionada deve aparecer destacada visualmente.

**Severidade**  
Média.

---

### CT-RD-02 - Peça removida não exibida como ativa
**Objetivo**  
Validar representação visual das peças removidas.

**Pré-condições**  
- peça marcada como removida no estado.

**Dados de entrada**  
- GameState atualizado após match.

**Passos**
1. renderizar o tabuleiro;
2. observar a posição da peça removida.

**Resultado esperado**  
A peça não deve ser exibida como peça ativa selecionável.

**Severidade**  
Alta.

---

### CT-RD-03 - Tela de vitória
**Objetivo**  
Validar feedback visual de vitória.

**Pré-condições**  
- GameState em `VITORIA`.

**Dados de entrada**  
- estado final vencedor.

**Passos**
1. renderizar a tela;
2. observar feedback de conclusão.

**Resultado esperado**  
A interface deve exibir claramente o estado de vitória.

**Severidade**  
Média.

---

### CT-RD-04 - Tela de bloqueio
**Objetivo**  
Validar feedback visual de ausência de jogadas.

**Pré-condições**  
- GameState em `SEM_JOGADAS`.

**Dados de entrada**  
- estado bloqueado.

**Passos**
1. renderizar a tela;
2. observar feedback ao jogador.

**Resultado esperado**  
A interface deve exibir claramente o estado de bloqueio.

**Severidade**  
Média.

---

## 10.9 Casos de Teste de Fluxo Funcional

### CT-FL-01 - Fluxo completo de partida com conclusão em vitória
**Objetivo**  
Validar a execução ponta a ponta de uma partida vencedora.

**Pré-condições**  
- tabuleiro válido e solucionável.

**Dados de entrada**  
- sequência de jogadas válidas.

**Passos**
1. iniciar partida;
2. selecionar pares válidos sucessivamente;
3. remover peças até o final;
4. verificar encerramento.

**Resultado esperado**  
A partida deve terminar em `VITORIA` sem corrupção do estado.

**Severidade**  
Crítica.

---

### CT-FL-02 - Fluxo completo até bloqueio
**Objetivo**  
Validar o comportamento do sistema ao chegar a um estado sem jogadas.

**Pré-condições**  
- tabuleiro ou sequência que leve ao bloqueio.

**Dados de entrada**  
- sequência de jogadas válida até o impasse.

**Passos**
1. iniciar partida;
2. executar jogadas;
3. atingir estado sem pares livres restantes.

**Resultado esperado**  
O sistema deve entrar em `SEM_JOGADAS` e comunicar isso corretamente.

**Severidade**  
Crítica.

---

### CT-FL-03 - Fluxo de reinício após sessão encerrada
**Objetivo**  
Validar a retomada do jogo após encerramento de sessão.

**Pré-condições**  
- partida finalizada em vitória ou bloqueio.

**Dados de entrada**  
- comando de reinício.

**Passos**
1. acionar reinício;
2. reconstruir o tabuleiro;
3. iniciar nova partida.

**Resultado esperado**  
A nova partida deve começar limpa e jogável.

**Severidade**  
Alta.

---

## 10.10 Casos de Teste de Performance

### CT-PF-01 - Tempo de resposta ao toque
**Objetivo**  
Validar que o tempo de resposta visual do toque esteja dentro da meta.

**Pré-condições**  
- build de teste executável.

**Dados de entrada**  
- toque em peça válida.

**Passos**
1. medir intervalo entre toque e feedback visual.

**Resultado esperado**  
O tempo deve permanecer dentro do limite definido no SRS.

**Severidade**  
Alta.

---

### CT-PF-02 - FPS durante gameplay
**Objetivo**  
Validar fluidez da renderização durante uma partida.

**Pré-condições**  
- partida em andamento.

**Dados de entrada**  
- gameplay contínuo com várias seleções e remoções.

**Passos**
1. monitorar FPS durante uso real.

**Resultado esperado**  
O FPS deve permanecer em patamar aceitável conforme metas do projeto.

**Severidade**  
Alta.

---

### CT-PF-03 - Tempo de inicialização
**Objetivo**  
Validar o tempo necessário para abrir o jogo até estado jogável.

**Pré-condições**  
- aplicativo fechado.

**Dados de entrada**  
- inicialização completa do app.

**Passos**
1. iniciar o aplicativo;
2. medir até o estado de prontidão.

**Resultado esperado**  
O tempo de inicialização deve respeitar a meta definida.

**Severidade**  
Alta.

---

### CT-PF-04 - Consumo de memória
**Objetivo**  
Validar que o consumo de memória permaneça dentro do teto previsto.

**Pré-condições**  
- partida em execução.

**Dados de entrada**  
- uso contínuo do jogo com reinícios e múltiplas partidas.

**Passos**
1. monitorar consumo de memória durante execução.

**Resultado esperado**  
O consumo deve permanecer dentro do limite documentado.

**Severidade**  
Alta.

---

## 10.11 Regressão Mínima Obrigatória
Toda nova versão do projeto deve, no mínimo, reexecutar estes testes:

- CT-RN-01
- CT-RN-02
- CT-RN-05
- CT-ST-02
- CT-ST-04
- CT-ST-05
- CT-CT-03
- CT-GE-04
- CT-FL-01
- CT-PF-01

Esses casos formam a malha mínima de proteção contra regressão funcional grave.

---

## 10.12 Critério de Aprovação dos Casos de Teste
Um caso de teste será considerado aprovado quando:
- os passos forem executados conforme descrito;
- o resultado observado coincidir integralmente com o resultado esperado;
- não houver efeitos colaterais incompatíveis no GameState;
- não houver violação de arquitetura para produzir o resultado;
- não houver comportamento não documentado que comprometa o núcleo do sistema.

---

## 10.13 Critério de Reprovação
Um caso de teste será considerado reprovado quando ocorrer qualquer uma das situações abaixo:
- resultado diferente do esperado;
- estado corrompido;
- exceção não tratada;
- inconsistência entre lógica e UI;
- quebra de fluxo principal;
- degradação de performance além dos limites aceitos.

---

## 10.14 Compatibilidade com o Método Caracol
Os casos de teste devem crescer junto com os ciclos do projeto.

A cada novo ciclo caracol:
- novos comportamentos devem gerar novos casos de teste;
- comportamentos alterados devem atualizar os casos existentes;
- a regressão mínima obrigatória deve continuar passando;
- a evolução do código sem evolução correspondente da malha de testes é inadequada.

---

## 10.15 Declaração Oficial dos Casos de Teste
O presente documento estabelece os Casos de Teste oficiais do VitahAcre na Waterfall V2.

Ele traduz operacionalmente o Plano de Testes em cenários concretos de validação e fica congelado como referência formal da verificação prática do sistema, salvo revisão explícita em versão posterior da documentação.
