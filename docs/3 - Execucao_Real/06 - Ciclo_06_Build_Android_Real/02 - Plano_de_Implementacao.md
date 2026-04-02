# 02 - Plano de Implementação — Ciclo 06 — Build Android Real
## Projeto: VitahAcre

---

## Ordem de Execução

### Passo 1 — Confirmar configuração do `build.gradle`
- `compileSdk`, `minSdk`, `targetSdk`, `versionCode`, `versionName`
- Dependências: Jetpack Compose, JUnit

---

### Passo 2 — Executar build debug
```
./gradlew assembleDebug
```
Esperado: `BUILD SUCCESSFUL`

---

### Passo 3 — Instalar no emulador
```
adb install app/build/outputs/apk/debug/app-debug.apk
```
Confirmar: app abre, tabuleiro aparece, toque funciona.

---

### Passo 4 — Instalar em dispositivo real
Transferir APK via USB ou ADB.
Confirmar: instalação sem erro, app abre.

---

### Passo 5 — Executar fluxo completo no aparelho
- Iniciar partida
- Executar ao menos um match válido
- Continuar até vitória ou bloqueio
- Testar reinício

---

### Passo 6 — Registrar evidências
- Log de build bem-sucedido
- Print ou vídeo do jogo no aparelho real

---

*Plano de Implementação — Ciclo 06 — VitahAcre*
