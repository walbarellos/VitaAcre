# 03 - Regras de Negocio

## 3.1 Objetivo do Documento
Este documento define as regras de negócio oficiais do VitahAcre. Sua função é formalizar a lógica pura do jogo, separando-a de qualquer decisão de interface, animação, renderização ou detalhe de implementação visual.

As regras aqui descritas devem servir como:
- base determinística do comportamento do jogo;
- referência para implementação das funções centrais;
- referência para os testes unitários e funcionais;
- proteção contra ambiguidade lógica entre documentos.

Este documento deve ser lido em conjunto com o SRS, a Arquitetura e o Modelo de Dados, mas possui foco exclusivo na lógica do domínio.

---

## 3.2 Natureza das Regras
As regras de negócio do VitahAcre devem obedecer aos seguintes princípios:

### RN-P01
Devem ser determinísticas.

### RN-P02
Devem ser independentes da interface.

### RN-P03
Devem ser independentes de efeitos visuais.

### RN-P04
Devem ser testáveis isoladamente.

### RN-P05
Devem operar sobre estruturas de estado e peças de forma lógica e previsível.

---

## 3.3 Conceitos Fundamentais
Para efeito deste documento, os conceitos abaixo possuem significado oficial.

### 3.3.1 Peça
Elemento individual do tabuleiro, dotado de identidade lógica, posição e estado.

### 3.3.2 Peça ativa
Peça que ainda participa da partida e pode, em tese, ser avaliada pelo sistema.

### 3.3.3 Peça removida
Peça que já foi retirada do fluxo jogável da partida.

### 3.3.4 Camada
Nível vertical lógico do tabuleiro, usado para representar sobreposição entre peças.

### 3.3.5 Lado livre
Condição em que ao menos um dos lados laterais relevantes da peça não está bloqueado por outra peça ativa.

### 3.3.6 Peça elegível
Peça que satisfaz todas as condições formais para ser selecionada.

### 3.3.7 Par válido
Conjunto de duas peças distintas que obedecem à regra oficial de match.

---

## 3.4 Regras Fundamentais do Dominio

## RN-01 - Existencia valida da peça
Uma peça só pode participar de qualquer avaliação lógica se existir no estado atual da partida.

### Implicação
Peças inexistentes não podem ser selecionadas, comparadas, removidas ou consideradas em validações de match.

---

## RN-02 - Estado de remoção
Uma peça marcada como removida deixa de participar do fluxo jogável da partida.

### Implicações
- não pode ser selecionada;
- não pode formar match;
- não pode bloquear outra peça como peça ativa;
- não pode voltar ao estado ativo na mesma partida, salvo reinicialização completa da sessão.

---

## RN-03 - Unicidade da posição absoluta
Não podem existir duas peças distintas ocupando exatamente a mesma posição lógica absoluta do tabuleiro.

### Posição absoluta
A posição absoluta é definida pela combinação da coordenada horizontal, coordenada vertical e camada da peça.

### Implicação
A integridade estrutural do tabuleiro exige unicidade dessa combinação.

---

## RN-04 - Integridade de pareamento
As peças do tabuleiro devem obedecer à política oficial de pareamento adotada pela geração da partida.

### Implicação
Toda peça ativa deve pertencer a um conjunto logicamente compatível com a regra de formação de pares.

---

## RN-05 - Bloqueio superior
Uma peça não é considerada livre se houver peça ativa posicionada acima dela em condição estrutural de bloqueio.

### Interpretação oficial
Se a geometria lógica do tabuleiro indicar que existe peça superior ativa ocupando a região que bloqueia a peça avaliada, a peça inferior não pode ser selecionada.

### Implicação
A existência de bloqueio superior é suficiente para impedir a elegibilidade, independentemente dos lados laterais.

---

## RN-06 - Bloqueio lateral total
Uma peça não é considerada livre se estiver simultaneamente bloqueada nos dois lados laterais relevantes pela presença de peças ativas.

### Interpretação oficial
Para ser elegível lateralmente, a peça precisa ter pelo menos um lado livre.

### Implicação
Se os dois lados estiverem bloqueados, a peça é inelegível, ainda que não exista bloqueio superior.

---

## RN-07 - Definicao de peça livre
Uma peça é considerada livre se, e somente se, satisfizer simultaneamente:

- não estiver removida;
- não estiver bloqueada superiormente;
- possuir ao menos um lado lateral livre.

Esta é a definição central de elegibilidade espacial do jogo.

---

## RN-08 - Elegibilidade para seleção
Uma peça pode ser selecionada se, e somente se:

- existir no estado atual;
- estiver ativa;
- satisfizer a definição de peça livre;
- não houver impedimento lógico adicional definido pelo fluxo atual da partida.

### Observação
A regra de elegibilidade pertence ao domínio e deve ser respeitada por qualquer interface.

---

## RN-09 - Primeira seleção valida
Quando nenhuma peça estiver previamente registrada como primeira seleção, uma peça elegível pode ser registrada como primeira peça selecionada.

### Implicação
A partida passa ao estado correspondente à existência de uma primeira seleção ativa.

---

## RN-10 - Segunda seleção valida
Quando já existir primeira seleção ativa, uma segunda peça elegível pode ser registrada para avaliação de match.

### Implicação
A partida passa ao estado de comparação lógica entre duas peças.

---

## RN-11 - Distincao entre peças
Duas referências que apontam para a mesma peça não podem formar um par válido.

### Implicação
Uma peça nunca pode casar com ela mesma.

---

## RN-12 - Regra de match
Duas peças formam um match válido se, e somente se, satisfizerem simultaneamente:

- ambas existirem no estado atual;
- ambas estiverem ativas;
- ambas forem elegíveis no momento da avaliação;
- forem peças distintas;
- obedecerem ao critério oficial de equivalência lógica de pareamento.

---

## RN-13 - Equivalencia logica de pareamento
A equivalência lógica de pareamento é a regra que define se duas peças pertencem ao mesmo tipo compatível de par.

### Regra oficial inicial
Na versão base do projeto, duas peças são compatíveis para match quando compartilham o mesmo identificador lógico de pareamento.

### Observação
Se futuramente houver categorias especiais de equivalência, isso deverá ser documentado explicitamente em revisão posterior.

---

## RN-14 - Match invalido
Há match inválido quando qualquer uma das condições exigidas em RN-12 não for satisfeita.

### Implicações
- nenhuma peça deve ser removida;
- o estado da partida deve permanecer íntegro;
- a lógica de seleção pós-tentativa deve obedecer à política oficial do fluxo.

---

## RN-15 - Remocao por match valido
Quando duas peças formarem match válido, ambas devem ser marcadas como removidas.

### Implicações
- deixam de participar da partida;
- deixam de poder ser selecionadas;
- deixam de influenciar o bloqueio de outras peças como peças ativas;
- o tabuleiro passa a um novo estado lógico.

---

## RN-16 - Persistencia da remoção
Uma vez removida em uma partida, a peça permanece removida até o reinício completo da sessão.

### Implicação
Não existe “desremoção” espontânea no fluxo padrão da versão base.

---

## RN-17 - Integridade da seleção
O sistema não pode permanecer com seleção logicamente impossível após o processamento de uma jogada.

### Exemplos de impossibilidade
- seleção contendo peça removida como se ainda estivesse ativa;
- seleção dupla mantida indefinidamente após match já processado;
- seleção inconsistente com o estado atual do jogo.

---

## RN-18 - Politica de seleção após tentativa de match
Após a tentativa de avaliação entre duas peças, o sistema deve resolver a seleção para um estado coerente, conforme a política oficial de fluxo adotada pelo projeto.

### Regra oficial inicial
Na versão base, após a tentativa de match:
- se o par for válido, ambas as peças são removidas e a seleção ativa deve ser esvaziada;
- se o par for inválido, nenhuma peça é removida e a seleção deve ser resolvida sem deixar o estado inconsistente.

### Observação
A estratégia exata de reset ou retenção parcial deve ser coerente com o controller e explicitada sem contrariar esta regra.

---

## RN-19 - Vitoria
A partida entra em estado de vitória quando todas as peças jogáveis tiverem sido removidas.

### Implicação
Não basta remover algumas peças ou atingir determinado percentual; a condição de vitória é o esvaziamento lógico completo do tabuleiro.

---

## RN-20 - Ausencia de jogadas
A partida entra em estado de ausência de jogadas quando:

- ainda existirem peças ativas no tabuleiro;
- não existir nenhum par válido disponível entre as peças elegíveis.

### Implicação
O jogo não pode continuar logicamente, salvo reinício.

---

## RN-21 - Continuidade da partida
A partida continua em fluxo normal quando:

- ainda existirem peças ativas;
- existir ao menos um par válido possível;
- a vitória não tiver sido alcançada;
- o estado sem jogadas não tiver sido detectado.

---

## RN-22 - Reinicio da partida
O reinício descarta integralmente a sessão atual e cria uma nova partida com novo estado inicial válido.

### Implicações
- nenhuma remoção anterior persiste;
- nenhuma seleção anterior persiste;
- nenhum estado terminal anterior persiste;
- a nova sessão passa a obedecer ao novo tabuleiro gerado.

---

## 3.5 Regras Estruturais do Tabuleiro

## RN-23 - Quantidade valida de peças
O tabuleiro deve possuir quantidade de peças compatível com a lógica de pareamento.

### Regra oficial inicial
A quantidade total de peças deve ser par.

---

## RN-24 - Coerencia de distribuicao
A distribuição das peças no tabuleiro deve respeitar a política estrutural adotada para geração da partida.

### Implicação
Não é permitido criar distribuição aleatória que torne o tabuleiro estruturalmente inconsistente.

---

## RN-25 - Integridade de camada
A relação entre peças de camadas diferentes deve ser suficiente para permitir a avaliação correta de bloqueio superior.

### Implicação
A estrutura do tabuleiro deve preservar informação espacial bastante para que a regra de peça livre seja corretamente avaliada.

---

## RN-26 - Jogabilidade inicial minima
O tabuleiro inicial não deve nascer em condição imediatamente inválida para o jogo.

### Regra oficial inicial
Ao iniciar a partida, deve existir ao menos uma jogada válida possível.

---

## RN-27 - Solucionabilidade pretendida
A geração do tabuleiro deve buscar produzir uma partida solucionável segundo a política oficial da versão base.

### Observação
A estratégia usada para garantir solucionabilidade pertence ao domínio da geração, mas a exigência lógica pertence à regra de negócio do produto.

---

## 3.6 Regras de Integridade do Estado

## RN-28 - Fonte única da verdade
O estado oficial da partida deve ser suficiente para determinar:

- quais peças estão ativas;
- quais peças estão removidas;
- quais peças estão selecionadas;
- qual é o estado formal da partida.

---

## RN-29 - Impossibilidade de remoção indevida
Nenhuma peça pode ser marcada como removida sem que um match válido tenha sido formalmente reconhecido.

---

## RN-30 - Impossibilidade de vitória indevida
A vitória não pode ser declarada enquanto ainda houver peça ativa no tabuleiro.

---

## RN-31 - Impossibilidade de bloqueio indevido
O estado SEM_JOGADAS não pode ser declarado enquanto existir ao menos um par válido disponível entre peças elegíveis.

---

## RN-32 - Consistencia após toque invalido
Um toque inválido, seja por ausência de peça ou por inelegibilidade da peça alvo, não pode corromper o estado da partida.

---

## RN-33 - Consistencia após reinicio
Após reinício, o novo estado deve ser logicamente independente da sessão anterior.

---

## 3.7 Regras Derivadas de Avaliacao
As seguintes regras derivadas podem ser implementadas como funções puras de apoio.

## RN-34 - Existe peça ativa
Existe peça ativa quando ao menos uma peça do tabuleiro não estiver removida.

---

## RN-35 - Existem duas peças comparaveis
Existem duas peças comparáveis quando o estado contém pelo menos duas peças distintas ainda ativas e elegíveis para avaliação de pareamento.

---

## RN-36 - Existe jogada valida
Existe jogada válida quando há pelo menos um par de peças distintas, elegíveis e logicamente equivalentes segundo a regra de match.

---

## RN-37 - Estado terminal de vitoria
O estado terminal de vitória é atingido quando não existem peças ativas.

---

## RN-38 - Estado terminal de bloqueio
O estado terminal de bloqueio é atingido quando existem peças ativas, mas não existe jogada válida.

---

## 3.8 Prioridade entre Regras
Quando múltiplas verificações forem necessárias, a prioridade lógica recomendada é:

1. verificar existência da peça;
2. verificar se a peça está removida;
3. verificar bloqueio superior;
4. verificar bloqueio lateral;
5. concluir elegibilidade;
6. registrar seleção;
7. validar match;
8. remover peças, se cabível;
9. verificar vitória;
10. verificar ausência de jogadas.

Essa ordem reduz inconsistências e favorece previsibilidade.

---

## 3.9 Casos de Interpretacao Oficial

## Caso IO-01 - Peça sem bloqueio superior, mas com dois lados bloqueados
Resultado: peça inelegível.

---

## Caso IO-02 - Peça com um lado livre, mas bloqueada superiormente
Resultado: peça inelegível.

---

## Caso IO-03 - Duas peças iguais, porém uma removida
Resultado: não formam match válido.

---

## Caso IO-04 - Duas peças elegíveis, distintas e com mesmo identificador lógico
Resultado: formam match válido.

---

## Caso IO-05 - Todas as peças removidas
Resultado: vitória.

---

## Caso IO-06 - Ainda existem peças, mas nenhum par elegível válido
Resultado: estado SEM_JOGADAS.

---

## 3.10 Criterios de Conformidade das Regras
A implementação será considerada conforme a este documento quando:

- respeitar a definição formal de peça livre;
- impedir seleção de peça inelegível;
- impedir auto-pareamento;
- remover apenas pares válidos;
- preservar o estado após falhas de seleção ou match inválido;
- detectar corretamente vitória;
- detectar corretamente ausência de jogadas;
- reiniciar a sessão de forma limpa.

---

## 3.11 Relacao com os Demais Documentos
Este documento:
- complementa o SRS no nível lógico;
- será materializado na Arquitetura como motor de regras;
- será suportado pelo Modelo de Dados;
- fundamentará o Plano de Testes e os Casos de Teste;
- condicionará a Implementação Técnica.

Nenhum documento posterior pode contradizer estas regras sem revisão explícita desta base.

---

## 3.12 Declaracao Oficial das Regras de Negocio
As regras deste documento constituem o núcleo lógico oficial do VitahAcre na Waterfall V2.

Elas ficam congeladas como referência formal de domínio do projeto, salvo revisão explícita em versão posterior da documentação.
