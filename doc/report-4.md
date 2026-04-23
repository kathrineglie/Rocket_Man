# Rapport – innlevering 4
**Team:** If-Me-And-The-Gang-Pull-Up
* Kathrine Lie
* Vegard Slagstad
* Sunniva Bratland
* Elise Blekken

**Hva som har endrte seg siden sist**

Siden forrige innlevering har vi ferdigstilt prosjektet og jobbet videre med å forbedre helheten i spillet.
Der vi i forrige rapport fokuserte på å få på plass mer funksjonalitet og flere spillsystemer, har vi i denne
perioden lagt vekt på å fullføre det som gjensto, rydde opp i prosjektet og sikre at de delene vi allerede hadde
laget fungerte godt sammen.

Spillet fremstår nå som et mer komplett produkt enn tidligere. Vi har fortsatt å forbedre flyten mellom
skjermene, videreutviklet strukturen i prosjektet og jobbet med å gjøre kodebasen lettere å forstå og vedlikeholde.
Vi har også brukt tid på testing, feilretting og kvalitetssikring, slik at prosjektet skulle være mest mulig
ferdig og stabilt til siste innlevering.

**Hva som nå fungerer**
Per nå har vi implementert og ferdigstilt:
* coins som kan samles inn i spillet
* score-system der spilleren får høyere score jo lenger man overlever
* Hindringer:
    * raketter
    * flammer
    * lasere
* gradvis introduksjon av hindringer
    * kun flammer de første 100 meterne
    * raketter etter 100 meter
    * lasere etter 200 meter
* powerups:
    * Bird
    * Robot
    * Gravity suit
* game over screen
* pause screen
* instruction screen
* klikkbar pauseknapp
* lydeffekter og musikk
    * jetpack-lyd når spilleren flyr opp
    * lyd når coins samles inn
    * lyd ved power-up
    * game over-lyd
    * bakgrunnsmusikk
* navn som kan skrives inn på startskjermen
* leaderboard / high scores der navnet til spilleren vises
* lagring av penger og progresjon på navn, slik at spilleren kan åpne spillet på nytt og
  beholde fremgang dersom samme navn brukes igjen
* hatt som låses opp etter at man har samlet nok coins
* vanskelighetsgrad som øker jo lenger man overlever

Vi vurderer prosjektet som ferdigstilt slik vi nå leverer det. De sentrale kravene til gameplay,
progresjon, brukeropplevelse, og struktur er oppfylt, og spillet fungerer som en helhetlig løsning.

**Team og prosjekt**

***Roller i teamet***

Rollene i teamet har fungert bra. Teamleder for prosjektet har vært Sunniva Bratland som har fulgt
med fremdrift og passer på at oppgaver blri fordelt. Kundekontakt har vært Vegard Slagstad, som har
hatt ekstra ansvar for å ta videre til gruppen det kunden ønsker.Alle har jobbet sammen som utviklere,
alle har dermed jobebt med implementasjon, testing, feilretting og dokumentasjon. Samtidig har alle i
gruppen har kunnet bidra på tvers av rollene når det har vært behov for det.

***Erfaring med team og prosjektmetodikk***

Vi opplever at arbeidsmåten vår har fungert godt gjennom prosjektet. Det som særlig har fungert bra er
at vi har jobbet jevnlig sammen, vi har møttes hver onsdag og fordelt oppgaver på en måte som har gjort det mulig
å jobbe parallelt. Vi har også brukt branches aktivt, noe som har gjort det lettere å holde arbeidet ryddig
og avgrenset.

***Gruppedynamikk****

Gruppedynamikken i gruppen har vært god gjennom hele prosjektet. Vi har samarbeidet godt, hatt lav terskel for å
stille spørsmål og hjulpet hverandre når noen har stått fast. Om det har vært noen diskusjoner om priotitering
eller valg av løsninger underveis, har vi sammen diskutert hva som er den beste løsningen.

***Kommunikasjon***

Kommunikasjonen har fungert godt i gruppen. Vi møtes fast hver onsdag og jobber sammen, og i tillegg har vi kunnet
møtes flere dager ved behov. Vi opplever også at kommunikasjonen rundt oppgaver, branches, merge requests og
feilretting har blitt bedre utover i prosjektet.

***Kort retrospektiv***

I denne siste innleveringen gjør vi et retrospektiv av hele prosjektet. Vi vurderer at prosjektet totalt sett har
gått bra, og at vi har klart å utvikle spillet fra en enkel prototype til et ferdig og sammenhengende produkt.
Hvis vi hadde begynt prosjektet på nytt, ville vi trolig ha startet enda tidligere med mer systematisk testing, og vi
ville også ha vært enda mer bevisste på opprydding og refaktorering underveis. Selv om dette ble tatt tak i senere,
ser vi at det kunne spart oss for noe arbeid mot slutten

***Commit-fordeling***

Det kan være noe forskjell i antall commits mellom gruppemedlemmene, men dette henger også sammen med at ulike personer
har hatt litt forskjellige typer oppgaver i ulike perioder, for eksempel kode, dokumentasjon og testing  Vi opplever
likevel at alle har bidratt til prosjektets fremdrift og til sluttresultatet.

**Krav og spesifikasjon**

***Krav og prioritering***

Siden sist har vi prioritert å ferdigstille prosjektet og gjøre de viktigste funksjonene stabile og komplette.
Vi har prioritert ny funksjonalitet ut fra hva som gir mest verdi for brukeren og hva som bidrar mest til at spillet
oppleves som sammenhengende og ferdig

Vi har prioritert:
* Obstacles for å gjøre spillet variert og utfordrende
* score og coins for tydelig progresjon og belønning
* power-ups for mer variasjon i gameplay
* stigende vanskelighetsgrad for å gjøre spillet mer spennende over tid
* gradvis introduksjon av hindringer for bedre flyt i spillopplevelse
* lyd og UI for å gjøre spillet mer levende og helhetlig.

***Krav og spesifikasjon***

Vi har jobbet med flere krav i prosjektet. Et viktig krav har vært at spilleren skal kunne samle coins i spillet,
slik at man får en belønning og kan låse opp innhold. For at dette skulle fungere, måtte coins forsvinne ved kollisjon,
coin-antallet måtte øke, og samme coin måtte ikke kunne samles flere ganger. Dette krevde at vi laget coin-objekter i
modellen, implementerte kollisjon mellom spiller og coin, oppdaterte HUD og lagring, og testet at systemet fungerte
riktig.

Et annet sentralt krav har vært at spilleren skal møte ulike hindringer underveis, slik at spillet blir mer utfordrende
og variert. Her måtte hindringer kunne spawne i spillet, introduseres gradvis etter hvor langt spilleren har kommet,
spilleren dør ved kollisjon og fjernes når de ikke lenger er relevante. For å oppnå dette implementerte vi flammer,
raketter og lasere, laget spawn-logikk og progresjon, utviklet kollisjonshåndtering og testet obstacle -logikken.

Vi har også jobbet med et krav om at spilleren skal kunne samle power-ups, slik at gameplayet blir mer variert og
spilleren får midlertidige fordeler. Dette innebar at power-ups måtte kunne dukke opp i spillet og gi riktig effekt ved
kollisjon. Arbeidet bestod i å implementere Bird, Robot og Gravity suit, lage logikk for effekt og varighet og
koble dette til spilleren og gameplayet.

Videre har vi hatt som krav at spilleren skal kunne lagre progresjon og score på navn. Dette betyr at spilleren skal
kunne skrive inn navn og få lagret high score, coins og progresjon, slik at man kan fortsette senere og se
resultatene sine

Til slutt har vi også hatt et krav om at spilleren skal ha en helhetlig brukeropplevelse med flere skjermer.
Dette handler om at det skal være lett å forstå hvordan spillet startes, pauses, avsluttes og hvordan kontrollene
fungerer. Arbeidet bestod i å implementere og forbedre de ulike skjermene, koble dem til game states, lage pauseknapp
og navigasjon og forbedre den visuelle flyten i spillet.

***Bugs***

Vi har forsøkt å levere funksjonalitet med høy kvalitet, og i sluttfasen har vi brukt mye tid på feilretting og
kvalitetssikring. Det finnes fortsatt mindre bugs eller ting som kunne vært forbedret videre, men disse ville krevd
mye refaktorering av kode, men vi vurderer fortsatt de sentrale kravene som ferdige.

**Kode**

***Refkatorering***

Vi har gjort og vurdert refaktorering underveis i prosjektet, særlig etter hvert som kodebasen ble større. Prosjektet
bygger også fortsatt på MVC-strukturen.

***Arkitektur***

Arkitekturen og designvalgene har fungert godt for prosjektet. Vi opplever at det i hovedsak er lett å få ting til å
henge sammen, nettopp fordi strukturen er tydlig. Nye features som hindringer, power-ups, skjermer, UI og
progresjonssystemer har kunnet legges til gradvis uten at prosjektet har blitt uoversiktlig.

***Kodekvalitet***

Vi opplever at alle i gruppen kan forstå og jobbe med de fleste delene av prosjektet, noe som har vært viktig for
samarbeid og fleksibilitet gjennom hele utviklingsperioden.

***Testing***

Vi har laget tester til:
* GameModel
* obstacles
* coins
* rockets
* lasers

***Erfaringer fra hele prosjektet***

Vi har erfart at:

* god struktur tidlig gjør det mye enklere å bygge videre
* MVC og tydelig state-håndtering gjør prosjektet lettere å forstå
* små detaljer som lyd, UI og skjermflyt har stor betydning for helheten
* testing av model-logikk krever at koden er godt strukturert
* Git og branches fungerer godt når endringene holdes avgrensede
* jevn kommunikasjon og faste møtepunkter gjør gruppearbeidet enklere
