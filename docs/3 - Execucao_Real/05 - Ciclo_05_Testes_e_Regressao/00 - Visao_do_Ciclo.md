# 00 - Visão do Ciclo 05 — Testes e Regressão
## Projeto: VitahAcre

---

## 1. Objetivo do Ciclo

Blindar o sistema contra retrocesso funcional. O que funciona agora não pode quebrar depois.

---

## 2. Pré-requisito

Ciclos 01 a 04 concluídos e congelados.

---

## 3. Escopo Oficial

| Tipo de Teste       | Cobertura esperada                                          |
|---------------------|-------------------------------------------------------------|
| Unitário            | Regras puras do Ciclo 01 (elegibilidade, par, bloqueio)     |
| Unitário            | Gerador do Ciclo 02 (par de peças, posição, jogabilidade)   |
| Integração          | Fluxo completo de jogada do Ciclo 03                        |
| Funcional           | Partida do início ao fim: vitória e bloqueio                |
| Regressão mínima    | Suite obrigatória que deve passar sempre                    |

---

## 4. Contrato Técnico do Ciclo

- Usar JUnit 4 ou JUnit 5 conforme configuração do projeto
- Nenhum teste pode depender de emulador ou device (exceto testes de UI instrumentados opcionais)
- A regressão mínima deve ser executável com um único comando de build
- Falha em qualquer teste da regressão mínima bloqueia avanço ao Ciclo 06

---

## 5. Checklist de Conclusão

- [ ] Testes unitários das regras puras escritos e passando
- [ ] Testes unitários do gerador escritos e passando
- [ ] Testes de integração do controller escritos e passando
- [ ] Teste funcional de partida completa (vitória) passando
- [ ] Teste funcional de partida com bloqueio passando
- [ ] Suite de regressão mínima definida e documentada
- [ ] Suite de regressão mínima passando 100%
- [ ] Evidência registrada em `07 - Evidencias_e_Validacoes/`

---

## 6. Estado Atual do Ciclo

| Item                   | Estado              |
|------------------------|---------------------|
| Visão do ciclo criada  | ✅                  |
| Implementação iniciada | ⏳ aguarda Ciclo 04 |
| Checklist concluído    | ⏳ pendente         |
| Ciclo validado         | ⏳ pendente         |
| Ciclo congelado        | ⏳ pendente         |

---

*Ciclo 05 — VitahAcre — Método Caracol de Tolentino*
