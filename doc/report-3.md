# Rapport – innlevering 3
**Team:** If-Me-And-The-Gang-Pull-Up
* Kathrine Lie
* Vegard Slagstad
* Sunniva Bratland
* Elise Blekken 

**Hva som har endret seg siden sist**
Siden forrige innlevering har vi videreutviklet prototypen til et mer komplett og funksjonelt spill. Der vi tidligere   
hadde en grunnleggende gameplay-loop med jetpack og raketter, har vi nå utvidet spillet med flere mekanikker, mer 
innhold og bedre brukeropplevelse.

Vi har blant annet implementert coins, et score-system der scoren fungerer som meter/avstand, power-up, lydeffekter
og flere skjermer. I tillegg har vi forbedret UI og skrevet noen tester for model-logikk.

spillet fremstår nå som langt mer helhetlig enn i forrige innlevering, både med tanke på gameplay, struktur og presentasjon.

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

spillet har nå tydligere flyt mellom ulike states og skjermer, og opplevelsen er mer spillbar og intuitivt enn
tidligere.

**Krav og prioritering** 
Siden sist har vi prioritert å gå videre fra en enkel prototype til å få på plass flere sentrale spill elementer. 
Vi har jobbet videre med det som ligger nær MVP, spesielt funksjonalitet som gjør at spillet føles komplett og 
sammenhengende.

vi har prioritert:
* flere obstacles for å gjøre spillet mer variert
* score og coins for tydeligere progresjon og belønning
* power-up for mer variasjon i gameplay
* flere skjermer for bedre flyt og brukeropplevelse
* lyd og UI for å gjøre spillet mer levende

Vi har i denne perioden før innleveringen prioritert funksjonalitet som forbedrer selve spillopplevelsen og gjør 
prosjektet mer presentabelt.

**Refaktorering og arkitektur** 
vi har fortsatt å bygge videre på MVC-strukturen, og prosjektet er fremdeles tydelig delt mellom model, view og 
conteoller. Denne oppdelingen har gjort det lettere å utvide spillet med nye features uten at det blir tett koblet
sammen.

UI og design er blitt forbedret. Vi har designet det visuelle uttrykket selv, og i illegg lagt inn en pause skjerm, 
hjelpeskjerm og game over skjerm.

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

* justere vanskelighetsgrad og progresjon
* utvide eller forbedre power-up systemet
* lage flere tester
* rydde i kode og dokumemntasjon der det trengs
