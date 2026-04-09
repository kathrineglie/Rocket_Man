# *README If-me-and-the-gang-pull-up* 

Rocket Man is a 2D endless runner game inspired by *Jeppack Joyride* where you play as *TPowah* and try to survive as 
long as possible in a dangerous world full of obstacles. The player collect coins, use powerups, and survive as long 
as possible while the game gradually becomes more difficult

## Credits
### Authors
* Kathrine Lie
* Vegard Slagstad
* Sunniva Bratland
* Elise Blekken

## Project description
* When starting the game, you load into a dangerous world as the character _TPowah_.
* The aim of the game is to progress as far as you can while collecting coins. 
* Along the way you have to avoid various obstacles, such as lazers, rockets and flames.
* PowerUps can be attained and used to assist along the dangerous journey.
* The space bar is used to activate the character's pink jetpack, which propels him upwards.
* The player can also achive a hat after collecting coins.
* the game contains leaderboard, and the goal is to be on top of the leaderboard.
* The first part of the game is easier, and new obstacles types are introduces gradually:

## Features

### Obstacles
* Flames
* Rockets
* Lazers

### Powerups
* Bird
* Robot
* Gravity Suit

### Progression
* Increasing game speed 
* Gradual introduction of obstacles
* unlockable hat

### Other functionality
* Coins
* Score system
* Leaderboard
* Name input
* Saved progression connected to player name
* Pause screen
* Game over Screen
* Instruction Screen

## Controls / How to play

### Home screen
* Click the same field to enter your player name
* Press `SPACE` to start the game
* Click `?` to open the instruction screen

### In game
* `SPACE`- use jetpack / move upwards
* `P`- pause and resume the game

## How to run the game

### requirements:
* Java 21
* Maven 

### Clone the respository
* clone the repository from git:
  * git clone <repository-url>

### compile:
* Then you can either run the Main class in a compatible IDE or compile the project using maven commands:
  * mvn clean compile
  * mvn exec:java

### Contributors
* We have used ChatGPT as a support tool during development, mainly for discussing code structure and graphics-
    related decisions

### Sound and Graphics
* `src/main/resources/*` - All illustrations in this folder created by Kathrine Lie
* `src/main/resources/Sounds` 
    * The game uses audio from Pixabay:
      - **"Football - Football Soccer Game Music 08 Second"** by **Bomb Sound**  
        Used as: `music.mp3`  
        Source: https://pixabay.com/sound-effects/musical-football-football-soccer-game-music-08-second-490554/
      - **"Rocket Launch"** by **freesound_community**  
        Used as: `jetpack.mp3`  
        Source: https://pixabay.com/sound-effects/film-special-effects-rocket-launch-76003/
      - **"coin"** by **chieuk**  
        Used as: `coin.mp3`  
        Source: https://pixabay.com/sound-effects/film-special-effects-coin-257878/
      - **"Awesome Level Up"** by **Koi Roylers**  
        Used as: `powerup.mp3`  
        Source: https://pixabay.com/sound-effects/film-special-effects-awesome-level-up-351714/
      - **"flapping"** by **freesound_community**  
        Used as: `bird.mp3`  
        Source: https://pixabay.com/sound-effects/nature-flapping-39306/
      - **"Game Over Deep Male Voice Clip"** by **Universfield**  
        Used as: `game_over.mp3`  
        Source: https://pixabay.com/sound-effects/film-special-effects-game-over-deep-male-voice-clip-352695/
      - **"Robot power off"** by **freesound_community**  
        Used as: `robot.mp3`  
        Source: https://pixabay.com/sound-effects/film-special-effects-robot-power-off-97246/
      - **"teleport"** by **freesound_community**  
          Used as: `teleport.mp3`  
          Source: https://pixabay.com/sound-effects/film-special-effects-teleport-14639/

* `src/main/resources/Sounds`
  * The game uses fonts from Google fonts:
    - This project uses the **Inter** font.
      - Copyright: Copyright 2020 The Inter Project Authors 
      - Source: Inter by Rasmus Andersson 
      - License: SIL Open Font License, Version 1.1 
      - used as font2.ttf 
      - source: https://fonts.google.com/specimen/Inter?query=inter
      
    - This project uses the **Bitcount Prop Double Ink** font. 
      - Copyright: Copyright 1980 The Bitcount Project Authors 
      - Designer: Petr van Blokland 
      - Source: Google Fonts / TYPETR Bitcount 
      - License: SIL Open Font License, Version 1.1 
      - used as font.ttf 
      - source: https://fonts.google.com/specimen/Bitcount+Prop+Double+Ink?query=Bitcount+Prop+Double+Ink&preview.script=Latn


