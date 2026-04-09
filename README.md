# *README If-me-and-the-gang-pull-up* 

Rocket Man is a side-scrolling game where you play as *TPowah* and try to survive as long as possible in a dangerous world full of obstacles.
*TODO: Other useful stuff to include:*
* **Project description** – what it is, why you made it, etc
* **Installation and Running** – Explain how to install / clone+compile project, and how to run it. Also, any requirements (e.g., Java version), additional dependencies that must be installed, how to run tests, etc. See/copy text above.
* **Usage / Documentation** – Explain how to use it. E.g., which keys to use, how to control player, where to find out more etc. Examples are good!
* **Security** – any security considerations the user should be aware of (probably not applicable for INF112)
* **Bugs/issues** –  if applicable, list bugs/problems a normal user should be aware of. Also, where to file bug reports.
* **Code structure** – where to find important parts of the code (might go under *Contributing*)
* **Contributing** – how to contribute to the project (often found in a separate `CONTRIBUTING` file)
* **License** – copyright information (often found in a separate `LICENSE` file)
* **Authors / Credits** – who's responsible for the project, and who made the graphics, sounds, code copied from somewhere else, etc (attribution)
* *Use clear and concise language and good structure in the README file!*

## Credits
### Authors
* Kathrine Lie
* Vegard Slagstad
* Sunniva Bratland
* Elise Blekken

## Project 
* When starting the game, you load into a dangerous world as the character _TPowah_.
* The aim of the game is to progress as far as you can while collecting coins. 
* Along the way you have to avoid various obstacles, such as lazers, rockets and flames.
* PowerUps can be attained and used to assist along the dangerous journey.
* The space bar is used to activate the character's pink jetpack, which propels him upwards.

## How to run the game
* To run the game, clone the repository from git. 
* Then you can either run the Main class in a compatible IDE or compile the project using maven commands:
  * mvn clean compile
  * mvn exec:java

### Contributors
* *TODO: did you include code from anyone else? Did you use AI/LLMs to generate or edit the code?*

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
  * The game uses fonts from google fonts:
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


