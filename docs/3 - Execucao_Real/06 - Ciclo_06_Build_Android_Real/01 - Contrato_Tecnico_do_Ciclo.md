# 01 - Contrato Técnico do Ciclo 06 — Build Android Real
## Projeto: VitahAcre

---

## 1. Pré-requisito

Ciclos 01 a 05 concluídos, checklists aprovados, regressão mínima 20/20 passando.

---

## 2. Configuração de Build

Conforme `1 - Waterfall/11 - Build_Deploy.md`:

- `compileSdk`: conforme definido na Waterfall
- `minSdk`: conforme definido na Waterfall
- `targetSdk`: conforme definido na Waterfall
- `versionCode` e `versionName`: definir na primeira build

---

## 3. Contratos do Ciclo

| Contrato | Descrição |
|----------|-----------|
| BLD-01 | `./gradlew assembleDebug` deve completar sem erros |
| BLD-02 | APK gerado deve ser instalável em dispositivo real |
| BLD-03 | Fluxo principal deve funcionar no aparelho: início → jogada → vitória/bloqueio |
| BLD-04 | Nenhuma falha crítica (crash) sem resolução conhecida |
| BLD-05 | Responsividade aceitável — sem travamento perceptível na jogada |

---

## 4. Artefatos Esperados

| Artefato | Localização |
|----------|-------------|
| APK debug | `app/build/outputs/apk/debug/` |
| Log de build limpo | `07 - Evidencias_e_Validacoes/` |
| Print/vídeo do jogo no aparelho | `07 - Evidencias_e_Validacoes/` |

---

*Contrato Técnico — Ciclo 06 — VitahAcre*
