# Rapport – innlevering 2
**Team:** If-Me-And-The-Gang-Pull-Up
* Kathrine Lie
* Vegard Slagstad
* Sunniva Bratland
* Elise Blekken 

**Hva som har endret seg siden sist**
Siden forrige innlevering har vi gått fra konsept- og strukturarbeid til en fungerende spillprototype. 
Vi har implementert en grunnleggende gameplay-loop med jetpack- mekanikk, scrolling- bakrgunn og kollisjonssystem.

Prosjektet har blitt videre strukturert etter MVC-prinsippet. vi har tydelig separasjon mellom:
* Model (GameMode, GameBoard, GameState)
* View (GameScreen, HomeScreen, RocketManView, render-klasser)
* Controller (RocketManController)

Vi har også refaktorert skjermhåndtering slik at spilltilstand styres gjennom en GameState enum (HOME_SCREEN, PLAYING,
GAME_OVER), noe som gjør arkitekturen mer robust og lettere og utvide. 

Vi har begynt å bruke factory-pattern for generering av hindringer, noe som gjør det enklere å legge til nye typer
obstacles senere uten å endre GameModel direkte. 

**Hva som nå fungerer**
Per nå har vi implementert:
* spillbar karakter med jetpack (Tpowah)
  * Holder inne SPACE -> flyr opp
  * slipper -> påvirkes av tyngdekraft
* Endeløs bevegelse fremover
* bakgrunn og world-rendering
* startskjerm
`* Overgang mellom HomeScreen og GameScreen
* lagt til raketter, som en hindring

spillet er nå spillbart i sin grunnform

**Refaktorering og arkitektur**
Vi har brukt tid på å forbedre struktur og ansvarsfordelig
* Har logikk for skjermbytte i Main og Controller
* introdusert GameState for tydeligere state management
* Har modulær oppdeling av rendering (PlayerRenderer, ObstacleRenderer, osv.)
* Har en god branch-struktur og bruker feature-branches konsekvent

**Testing**
Vi har begynt å strukturere kode slik at model-logikk kan testes uavhengig av rendering.
Neste steg er å skrive tester for:
* Jetpack-bevegelse
* Kollisjon
* GameState-overgander

**Erfaringer så langt**
Vi har erfart at:
* God struktur tidlig gjør videre utvikling enklere
* Screen-håndtering i LibGDX krever tydelig kontroll på lifecycle
* State-basert styring er mer robust enn å bytte screens direkte fra view
* har fått mer kontroll på hvordan Git fungerer
* har erfart viktigheten av kommuniaksjon i et gruppeprosjekt

**Neste steg er:**
* Forberede UI
* implementere score-system
* Legge til økende vanskelighetsgrad
* Lage GameOver-Screen
* legge til flere hindringer
* glattere gravity, og sjekke ulike hastigheter på player og på hindringer
* Eventuelt powerups