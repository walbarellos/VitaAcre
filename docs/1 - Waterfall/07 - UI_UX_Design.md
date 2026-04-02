# 07 - UI UX Design

## 7.1 Objetivo do Documento
Este documento define as diretrizes oficiais de interface e experiência do usuário do projeto VitahAcre na Waterfall V2. Seu propósito é transformar a lógica do jogo e a estrutura do tabuleiro em uma experiência visual clara, legível, responsiva e coerente com o domínio.

Este documento deve:
- orientar a apresentação visual do jogo;
- garantir coerência entre interface e estado lógico;
- definir princípios de legibilidade e interação;
- sustentar feedback claro para as ações do jogador;
- reduzir ambiguidade perceptiva;
- preparar a implementação visual sem misturar regra de negócio com aparência.

---

## 7.2 Papel da UI e da UX no Projeto
No VitahAcre, UI e UX não existem apenas para “embelezar” o produto. Elas possuem função estrutural dentro da experiência do jogo.

A interface precisa:
- tornar o tabuleiro compreensível;
- permitir identificação de peças e camadas;
- comunicar seleção, remoção, vitória e bloqueio;
- reforçar a lógica do sistema;
- evitar ruído visual que prejudique a leitura espacial.

A experiência do usuário precisa:
- ser intuitiva;
- ser estável;
- ser responsiva;
- ser justa;
- ensinar a lógica do jogo por meio da própria interação.

---

## 7.3 Princípios Gerais de UI UX

### UIX-01 - Clareza
O jogador deve conseguir compreender o que está vendo sem depender de interpretação ambígua.

### UIX-02 - Coerência
Elementos iguais devem se comportar de maneira igual em toda a experiência.

### UIX-03 - Legibilidade
As peças, suas relações e o estado da partida devem ser visualmente compreensíveis.

### UIX-04 - Responsividade
A interface deve responder ao toque com rapidez suficiente para transmitir controle real ao jogador.

### UIX-05 - Economia Visual
Não deve haver excesso de elementos competindo pela atenção do usuário.

### UIX-06 - Feedback Perceptível
Toda ação relevante do jogador deve gerar retorno perceptível e coerente.

### UIX-07 - Subordinação ao Estado
A interface deve refletir o estado da partida, e não inventar uma lógica paralela.

---

## 7.4 Estrutura Geral da Tela
A tela principal do VitahAcre deve conter, no mínimo, os seguintes blocos funcionais:

- área principal do tabuleiro;
- área de comando mínimo da sessão;
- área de feedback de estado quando necessário.

A estrutura visual deve priorizar o tabuleiro como foco central da experiência.

---

## 7.5 Hierarquia Visual da Tela

### Nível 1 - Tabuleiro
O tabuleiro é o elemento visual mais importante da interface.

### Nível 2 - Seleção e Destaques
Os sinais de seleção e resposta imediata ao toque devem ser claramente perceptíveis, mas sem esconder a estrutura do tabuleiro.

### Nível 3 - Comandos Auxiliares
Comandos como reinício devem ser visíveis, porém não devem disputar protagonismo com o tabuleiro.

### Nível 4 - Feedback de Estado Final
Mensagens de vitória ou bloqueio devem surgir com clareza quando aplicáveis, sem confundir a leitura da sessão normal.

---

## 7.6 Área do Tabuleiro
A área do tabuleiro deve ser o centro da interface.

Ela deve:
- ocupar a maior parte útil da tela;
- manter boa proporção visual com diferentes resoluções;
- preservar a legibilidade das peças;
- permitir percepção clara de camadas e bloqueios;
- suportar renderização procedural.

O tabuleiro não deve parecer um detalhe dentro da tela; ele é a experiência principal.

---

## 7.7 Representação Visual das Peças
Cada peça deve ser visualmente distinguível como unidade individual do tabuleiro.

A representação visual da peça deve permitir perceber:
- contorno individual;
- posição;
- relação com as peças vizinhas;
- eventual sobreposição por camada;
- estado de seleção;
- estado de remoção.

A peça não pode ser desenhada de forma tão abstrata que prejudique a interpretação espacial.

---

## 7.8 Legibilidade das Camadas
A interface deve comunicar a existência de camadas de maneira perceptível.

Isso pode ser obtido por meios como:
- deslocamento visual;
- sombra;
- profundidade aparente;
- sobreposição coerente;
- diferença de posicionamento.

A leitura de profundidade não precisa ser realista, mas deve ser suficientemente clara para permitir compreensão da lógica espacial.

---

## 7.9 Legibilidade dos Lados Livres
A experiência visual deve permitir ao jogador perceber, com razoável clareza, quando uma peça possui ao menos um lado potencialmente livre.

A interface não precisa rotular isso textualmente em todos os momentos, mas deve evitar um desenho tão confuso que esconda completamente essa leitura.

---

## 7.10 Estados Visuais das Peças
Cada peça pode assumir, no mínimo, os seguintes estados visuais:

- ativa e não selecionada;
- ativa e selecionada;
- removida;
- inelegível ao toque no momento;
- potencialmente destacada por feedback temporário.

Esses estados devem ser visualmente distinguíveis.

---

## 7.11 Estado Visual Padrão da Peça
No estado padrão, a peça deve:
- ser visível;
- parecer interativa;
- manter coerência com o restante do tabuleiro;
- não competir desnecessariamente com outras peças por atenção exagerada.

---

## 7.12 Estado Visual de Seleção
Quando uma peça for selecionada com sucesso, a interface deve comunicar isso imediatamente.

A comunicação visual de seleção pode envolver:
- destaque de borda;
- mudança de brilho;
- alteração sutil de contraste;
- aura ou realce visual contido.

A seleção deve ser visível, mas não pode deformar a leitura do tabuleiro ao ponto de gerar confusão.

---

## 7.13 Estado Visual de Tentativa Inválida
Quando o jogador tocar em uma peça inelegível ou em região sem ação válida, a interface pode:
- não selecionar;
- responder com um microfeedback discreto;
- manter a integridade da leitura visual.

O feedback de erro deve ser:
- claro o suficiente para informar rejeição;
- discreto o suficiente para não punir agressivamente o jogador.

---

## 7.14 Estado Visual de Remoção
Quando um par válido for formado, a remoção das peças deve ser perceptível.

O desaparecimento não deve parecer um defeito gráfico. Deve comunicar:
- confirmação do match;
- mudança real no estado da partida;
- liberação potencial do espaço ao redor.

A remoção pode ser imediata ou acompanhada por transição visual curta, desde que preserve responsividade.

---

## 7.15 Estado Visual de Vitória
Quando a partida for vencida, a interface deve comunicar de forma inequívoca que:
- o tabuleiro foi resolvido;
- a sessão chegou ao fim em condição de sucesso.

A vitória deve ser distinguível de um simples tabuleiro vazio “sem explicação”.

---

## 7.16 Estado Visual de Bloqueio
Quando a partida entrar em estado sem jogadas, a interface deve comunicar de forma inequívoca que:
- ainda existem peças;
- porém não há continuidade válida;
- a sessão chegou a um impasse lógico.

Vitória e bloqueio não podem ser visualmente confundidos.

---

## 7.17 Comando de Reinício
A interface deve oferecer um comando de reinício claramente acessível.

Esse comando deve:
- ser fácil de localizar;
- não ocupar protagonismo exagerado;
- não ser confundido com ações do tabuleiro;
- comunicar função de nova tentativa.

---

## 7.18 Fluxo de Interação por Toque
O toque é a principal forma de interação do VitahAcre.

A experiência de toque deve obedecer aos seguintes princípios:
- a área tocável da peça deve ser coerente com sua representação visual;
- o toque deve produzir resposta perceptível;
- o jogador não deve ficar em dúvida se o toque foi reconhecido;
- toques válidos e inválidos devem gerar comportamentos distintos.

---

## 7.19 Responsividade Percebida
A experiência deve transmitir sensação de resposta imediata.

A referência desejada é:
- feedback visual inicial perceptível em torno de até 50 ms, quando o dispositivo permitir.

Mesmo quando a meta exata não puder ser garantida em todo hardware, a percepção de controle não pode ser perdida.

---

## 7.20 Ritmo Visual
O ritmo visual da interface deve acompanhar a natureza lógica do jogo.

A interface não deve:
- ser frenética;
- acumular estímulos desnecessários;
- distrair do tabuleiro.

Ela deve:
- apoiar a leitura;
- respeitar o tempo de raciocínio do jogador;
- reforçar o fluxo contemplativo e estratégico da partida.

---

## 7.21 Uso de Cor
O uso de cor deve obedecer aos seguintes princípios:
- reforçar hierarquia;
- destacar estados importantes;
- preservar legibilidade;
- evitar excesso de saturação desnecessária;
- manter contraste suficiente entre fundo, peças e destaques.

A cor deve ajudar o jogador a entender a interface, não apenas decorar o ambiente.

---

## 7.22 Contraste
O contraste entre:
- fundo e tabuleiro;
- peça e fundo;
- seleção e peça padrão;
- feedback final e estado normal

deve ser suficiente para permitir leitura clara em condições razoáveis de uso.

---

## 7.23 Fundo da Interface
O fundo da interface deve:
- apoiar a legibilidade do tabuleiro;
- não competir visualmente com as peças;
- evitar excesso de textura, ruído ou distração;
- reforçar a sensação de ordem visual.

---

## 7.24 Tipografia
A tipografia do VitahAcre deve ser:
- clara;
- legível;
- simples;
- adequada a telas mobile;
- usada com parcimônia.

O jogo não deve depender de grandes blocos textuais durante a sessão normal de partida.

---

## 7.25 Uso de Texto em Tela
O texto na experiência principal deve ser mínimo e funcional.

Ele é apropriado para:
- estados finais;
- comando de reinício;
- mensagens auxiliares relevantes;
- possíveis rótulos necessários à compreensão da interface.

O texto não deve sobrecarregar o jogador nem competir com o tabuleiro como foco principal.

---

## 7.26 Animações
Animações, quando presentes, devem ser:
- curtas;
- claras;
- úteis;
- subordinadas à lógica do jogo;
- leves do ponto de vista perceptivo e técnico.

Animações não devem:
- atrasar a compreensão do estado;
- criar sensação de lentidão artificial;
- prejudicar desempenho;
- virar espetáculo mais importante que a leitura do tabuleiro.

---

## 7.27 Microinterações
Microinterações são desejáveis quando ajudam a comunicar:
- reconhecimento de toque;
- confirmação de seleção;
- rejeição de tentativa inválida;
- confirmação de remoção;
- entrada em estado final.

Elas devem ser discretas e coerentes com o caráter lógico do jogo.

---

## 7.28 Escalabilidade para Diferentes Telas
A interface deve ser compatível com múltiplas dimensões de tela mobile.

A adaptação deve preservar:
- legibilidade do tabuleiro;
- acessibilidade do comando de reinício;
- clareza dos estados visuais;
- proporção adequada dos elementos principais.

A responsividade não deve sacrificar a compreensão estrutural do jogo.

---

## 7.29 Princípios de Acessibilidade Inicial
Mesmo sem um sistema avançado de acessibilidade nesta fase, a UI deve buscar, no mínimo:
- contraste razoável;
- alvos de toque utilizáveis;
- tipografia legível;
- sinais visuais não excessivamente dependentes de nuance mínima de cor;
- estrutura previsível.

---

## 7.30 Relação Entre UX e Regras do Domínio
A experiência deve reforçar as regras do domínio e não contradizê-las.

Isso significa que:
- a UI não pode sugerir que peça bloqueada está livre;
- a UI não pode sugerir remoção onde não houve match válido;
- a UI não pode esconder o estado final da sessão;
- a UI deve comunicar, por sua lógica visual, o comportamento real do sistema.

---

## 7.31 Erros de UI UX a Evitar
As seguintes falhas devem ser evitadas:

### UIE-01
Peças visualmente confusas ou sobrepostas sem legibilidade suficiente.

### UIE-02
Seleção sem feedback claro.

### UIE-03
Diferença insuficiente entre peça ativa e peça removida.

### UIE-04
Vitória e bloqueio com aparência semelhante.

### UIE-05
Excesso de elementos competindo com o tabuleiro.

### UIE-06
Botão de reinício difícil de encontrar ou fácil de tocar por engano.

### UIE-07
Animações que atrasem ou escondam a lógica do jogo.

---

## 7.32 Critérios de Conformidade de UI UX
A interface será considerada coerente com este documento quando:
- o tabuleiro for o foco visual principal;
- o jogador conseguir distinguir peças, seleção e remoção;
- a leitura espacial for preservada;
- a resposta ao toque for perceptível;
- o jogo comunicar claramente vitória e bloqueio;
- o reinício estiver acessível;
- a experiência reforçar as regras em vez de contradizê-las;
- a apresentação permanecer clara, leve e coerente com o ritmo do jogo.

---

## 7.33 Relação com os Demais Documentos
Este documento:
- traduz visualmente a Visão do produto;
- respeita os Requisitos do SRS;
- depende das Regras de Negócio;
- se apoia na Arquitetura e no Modelo de Dados;
- concretiza a apresentação experiencial definida no GDD;
- orienta a Implementação Técnica da camada visual;
- apoia a construção do Plano de Testes e dos Casos de Teste relacionados à interface.

Nenhum documento posterior deve contradizer estas diretrizes sem revisão explícita desta base.

---

## 7.34 Declaração Oficial de UI UX
O presente documento estabelece as diretrizes oficiais de UI e UX do VitahAcre na Waterfall V2.

Ele define os princípios de apresentação visual, legibilidade, interação, feedback e clareza experiencial do projeto, ficando congelado como referência formal da camada de interface e experiência do usuário, salvo revisão explícita em versão posterior da documentação.
