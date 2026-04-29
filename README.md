# README If-me-and-the-gang-pull-up* 

Rocket Man is a 2D endless runner game inspired by *Jetpack Joyride* where you play as *TPowah* and try to survive as 
long as possible in a dangerous world full of obstacles. The player collects coins, uses powerups, and survive as long 
as possible while the game gradually becomes more difficult.

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
* The player can also achieve a hat after collecting coins.
* the game contains a leaderboard, and the goal is to be on top of the leaderboard.
* The first part of the game is easier, and new obstacle types are introduced gradually:

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
* Unlockable hat

### Other functionality
* Coins
* Score system
* Leaderboard
* Name input
* Saved progression connected to player name
* Pause screen
* Game over screen
* Instruction screen

## Controls / How to play

### Home screen
* Click the same field to enter your player name
* Press `SPACE` to start the game
* Click `?` to open the instruction screen

### In game
* `SPACE`- use jetpack / move upwards
* `P`- pause and resume the game

## How to run the game

### Requirements
- Java 21
- Maven

### Clone the repository
Clone the repository from Git:

```bash
git clone git@git.app.uib.no:inf112/26v/proj/if-me-and-the-gang-pull-up.git
cd if-me-and-the-gang-pull-up 
```

### Compile and run:
* Then you can either run the Main class in a compatible IDE or compile the project using maven commands:
  * mvn clean compile
  * mvn exec:java

## How to run tests
Run all tests with Maven:

```bash
mvn test
```

Run Spotbugs with:

```bash
mvn clean compile spotbugs:check
```

## Documentation

Project documentation can be found here:

- [Møte referat](doc/møtereferat.md)
- [Report 1](doc/report-1.md)
- [Report 2](doc/report-2.md)
- [Report 3](doc/report-3.md)
- [Report 4](doc/report-4.md)

## Architecture
The project is structured using a model-view-controller inspired design.

### Class diagram
The class diagrams for the project can be found here:

- [Model class diagram](doc/class_diagrams/model_diagram.png)
- [View class diagram](doc/class_diagrams/view_diagram.png)
- [Controller class diagram](doc/class_diagrams/controller_diagram.png)

### Model
The model contains the main game state and game logic. This includes:
* player state
* game score
* coins
* obstacles
* powerups
* difficulty progression
* game state transitions

The central model class is GameModel, which implements both:
* ViewableRocketManModel
* ControllableRocketManModel

This allows the controller and view to depend on interfaces instead of directly on the full model
implementation.

### View
The view is responsible for rendering the game world and presenting information to the player.

RocketManView manages:
* viewport and camera
* graphical assets
* renderers for background, player, obstacles, powerups, coins and HUD
* Rendering responsibility is further split into smaller renderer classes.

### Controller
The controller handles:
* player input
* game flow
* communication between model and view
* sound triggering

RocketManController reads keyboard and mouse input, updates the model each frame, tells the view to render,
and coordinates sound effects and music through RocketManAudio.

### Screens
The game uses separate screen classes to handle different user-facing states:
* HomeScreen
* GameScreen
* InstructionScreen
* PauseScreen
* GameOverScreen

These screens are switched in Main based on the current GameState.

Menu-related screens inherit from AbstractMenuScreen, which provides shared setup for:
* fonts
* viewport
* shape renderer

This reduces duplication between screens.

## Important classes

### `Main`
`Main` is the entry point of the application. It creates the model, view, controller, audio manager, and all screens.
It also switches between screens depending on the current `GameState`.

This class mainly acts as the overall application coordinator.

### `RocketManController`
`RocketManController` connects the model and the view. It handles user input, updates the model each frame, tells the 
view to render the current game state, and coordinates sound effects and music through `RocketManAudio`.

It also manages transitions between different states such as pause, home screen, game over, and instruction screen.

### `GameModel`
`GameModel` is the central game logic class. It stores and updates the most important parts of the game state, 
including the player, score, coins, obstacles, powerups, difficulty progression, and game state transitions.

The class delegates specialized responsibilities to helper and manager classes such as:
- `DifficultyController`
- `ObstacleManager`
- `CoinManager`
- `PowerUpManager`
- `ObstacleCollisionHandler`
- `PlayerProgressManager`

Because of this, `GameModel` works as the main logic hub rather than storing every detail in one large class.

### `RocketManView`
`RocketManView` is responsible for rendering the gameplay view. It creates the viewport, loads graphical assets, 
and delegates drawing to dedicated renderer classes.

It also provides helper methods such as converting mouse position into world coordinates.

### Screen classes
The game is divided into separate screen classes that represent the different states the player can move between 
during the game. These include `HomeScreen`, `GameScreen`, `InstructionScreen`, `PauseScreen`, and `GameOverScreen`.

The active screen is selected in `Main`, which checks the current `GameState` and switches screen when the state 
changes. This makes it easier to separate gameplay from menus and other interface-related states.

The menu-oriented screens share common setup through `AbstractMenuScreen`, which provides shared handling of fonts, 
viewport, camera, and rendering utilities. This reduces duplicated code and makes the menu screen structure more 
consistent.

### Asset and audio handling
Classes responsible for loading and managing textures, fonts, and audio resources are separated from the main gameplay 
logic in order to keep responsibilities cleaner.

## Reflections 

### Structure
We tried to organize the project according to MVC principles.
The separation is clearest in the following way:
* game logic is primarily placed in the model
* rendering is handled by the view and renderer classes
* input and game flow are handled by the controller
* different UI/game states are represented through separate screen classes

At the same time, some responsibilities are naturally connected. For example, the controller handles both input and 
some sound-related coordination, while Main is responsible for switching between screens based on game state.

### Spotbugs
The SpotBugs report contains several warnings of the types `EI_EXPOSE_REP` and `EI_EXPOSE_REP2`. We are aware of these 
warnings, but in our case they mainly come from intentional sharing of mutable objects between the model, view, 
controller, and helper classes.

In this project, such sharing is part of how the game architecture works. For example, the controller needs direct 
access to the model and view, and rendering classes need access to shared assets such as fonts and textures. 
We therefore do not consider these warnings to be critical issues that should necessarily be fixed in this project.

### 
When running tests, the output may show a Mockito/ByteBuddy warning about dynamic Java agent loading. 
This warning is related to how Mockito creates mocks when using newer Java versions. It does not mean that 
the tests have failed.

When running some tests directly from IntelliJ, the test output may show a Mockito/ByteBuddy warning about dynamic Java
agent loading. This warning is related to how Mockito creates mocks when tests are started from IntelliJ with newer 
Java versions.
The tests are successful as long as Maven reports `BUILD SUCCESS`, or IntelliJ ends with exit code `0`.

### Sonarqube
We have gone through Sonarqube issues and implemented changes which made our code more secure, clean and effective. 
This tool helped us clean up code to remove unnecessary public modifiers, public field variables, imports and 
field overwrites. We have refactored methods with too much cognitive complexity to improve code readability.

## Contributors
* We have used ChatGPT as a support tool during development, mainly for discussing code structure and 
graphics-related decisions

## Sound and Graphics
* `src/main/resources/*` - All illustrations in this folder created by Kathrine Lie

### Sounds
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

### Fonts
* `src/main/resources/Fonts`
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


