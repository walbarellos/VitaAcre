# 00 - Visão do Ciclo 06 — Build Android Real
## Projeto: VitahAcre

---

## 1. Objetivo do Ciclo

Sair do ambiente de desenvolvimento e colocar o jogo num aparelho Android real.

---

## 2. Pré-requisito

Ciclos 01 a 05 concluídos, congelados e com regressão mínima passando.

---

## 3. Escopo Oficial

| Etapa                   | Descrição                                              |
|-------------------------|--------------------------------------------------------|
| Build debug             | Compilação e execução no emulador sem erros            |
| Build de teste interno  | APK gerado e instalável em dispositivo real            |
| Empacotamento           | APK ou AAB válido para distribuição interna            |
| Validação em aparelho   | Jogo iniciando, tabuleiro aparecendo, jogada ocorrendo |
| Checklist técnico       | Compilação limpa, sem warnings críticos abertos        |

---

## 4. Contrato Técnico do Ciclo

- minSdk e targetSdk conforme definido em `1 - Waterfall/11 - Build_Deploy.md`
- Build deve completar sem erros no Gradle
- APK deve instalar sem falha em pelo menos um dispositivo real
- O fluxo principal (início → jogada → vitória ou bloqueio) deve funcionar no aparelho

---

## 5. Checklist de Conclusão

- [ ] Build debug compilando sem erros
- [ ] Emulador executando o jogo sem crash
- [ ] APK de teste gerado
- [ ] APK instalado em dispositivo real
- [ ] Fluxo principal testado no aparelho
- [ ] Nenhuma falha crítica aberta sem resolução
- [ ] Print ou vídeo do jogo no aparelho como evidência
- [ ] Evidência registrada em `07 - Evidencias_e_Validacoes/`

---

## 6. Estado Atual do Ciclo

| Item                   | Estado              |
|------------------------|---------------------|
| Visão do ciclo criada  | ✅                  |
| Implementação iniciada | ⏳ aguarda Ciclo 05 |
| Checklist concluído    | ⏳ pendente         |
| Ciclo validado         | ⏳ pendente         |
| Ciclo congelado        | ⏳ pendente         |

---

*Ciclo 06 — VitahAcre — Método Caracol de Tolentino*
