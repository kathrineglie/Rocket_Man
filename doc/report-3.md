# Rapport – innlevering 3
**Team:** If-Me-And-The-Gang-Pull-Up
* Kathrine Lie
* Vegard Slagstad
* Sunniva Bratland
* Elise Blekken 

**Hva som har endret seg siden sist**
Siden forrige innlevering har vi videreutviklet spillet med mer funkjsonalitet. Spillet er mer dynamisk med 
stigende vanskelighetsgrad og to ulike powerups som hjelper spilleren. 

De ulike skjermene, som game over skjermen, start skjermen og pause skjermen er utviklet, og en toppliste med 
scores vises før spilleren starter et nytt spill. 

Helheten i spillet er nå bedre enn ved forrige innlevering. 

**Hva som nå fungerer**
per nå har vi implementert:

* coins som kan samles inn i spillet
* score-system der spilleren får høyere score jo lenger man overlever
* flere hindringer:
  * raketter
  * flammer
  * lasere
* power up i form av fugl
* game over screen
* pause screen
* instruction screen
* fobedret UI på flere skjermer
* klikkbar pauseknapp
* lydeffekter og musikk:
  * jetpack-lyd når spilleren flyr opp
  * lyd når coins samles inn
  * lyd ved power-up
  * game over-lyd
  * bakgrunnsmusikk
* Vanskelighetsgrad som øker jo lengre man overlever
* Pause knapp
* To powerups
  * Robot som har eget bevegelsesmønster med raketter
  * Bird som har eget bevegelsesmønster med vinger
* Navn og high scores som listes opp i startskjermen

**Krav og prioritering** 
Siden sist har vi prioritert å gå videre fra en enkel prototype til å få på plass flere sentrale spill elementer. 
Vi har jobbet videre med det som ligger nær MVP, spesielt funksjonalitet som gjør at spillet føles komplett og 
sammenhengende.

vi har prioritert:
* flere obstacles for å gjøre spillet mer variert
* score og coins for tydeligere progresjon og belønning
* power-ups for mer variasjon i gameplay
* flere skjermer for bedre flyt og brukeropplevelse
* lyd og UI for å gjøre spillet mer levende

Vi har i denne perioden før innleveringen prioritert funksjonalitet som forbedrer selve spillopplevelsen og gjør 
prosjektet mer presentabelt.

**Refaktorering og arkitektur** 
vi har fortsatt å bygge videre på MVC-strukturen, og prosjektet er fremdeles tydelig delt mellom model, view og 
conteoller. Denne oppdelingen har gjort det lettere å utvide spillet med nye features uten at det blir tett koblet
sammen.

UI og design er blitt forbedret. Vi har designet det visuelle uttrykket selv, og i illegg lagt inn en pause skjerm, 
hjelpeskjerm, startskjerm og game over skjerm.

**Testing**
Vi har laget tester, knyttet til:
* GameModel
* obstacles
* coins

Vi har erfart at testing blir enklere når logikken er godt skilt fra view-koden, og dette har også bekreftet at det er 
nyttig å holde modellen mest mulig uavhengig av rendering.

**Erfaringer så langt**
Vi har erfart at:

* God struktur tidlig gjør det enklere å bygge videre
* MVC og tydelig state-håndtering gjør prosjektet lettere å forstå
* små detaljer som lyd, UI og skjermflyt har stor betydning for helheten
* testing av model-logikk krever at koden er godt strukturert
* Git og branches fungerer godt når vi holder endringene avgrenset

Vi har også erfart at det ofte oppstår små bugs når nye skjermer og states legges til, men at disse er lettere å 
håndtere når screen-håndteringen og game states er tydelig strukturert.

**Gruppedynamikk og samarbeid**
Gruppen har fortsatt og samarbeide godt, vi møtes hver onsdag og jobber sammen hele dagen, og kan også møtes flere 
dager når det passer oss. Vi opplever at kommunikasjonen fungerer godt, og alle har god oversikt over hele koden. 

Vi bruker branches aktivt og prøver å holde endringer ryddige og avgrensede. Dette gjør det lettere å samarbeide uten å 
komme i konflikt med hverandres arbeid.

Vi har også fått mer erfaring med merge request, pipeline-feil og testing, noe som har gjort oss mer bevisste på både 
commit-meldinger, branches og kvalitetssikring før merging.

**Neste steg**
Videre ønsker vi å:

* Lage flere tester
* Gjøre coins brukbare
* Kanskje endre litt på hitboxene for en bedre spillopplevelse
