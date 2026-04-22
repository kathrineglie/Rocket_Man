package inf112.rocketman.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.rocketman.model.GameState;
import inf112.rocketman.model.powerups.PowerUpType;
import inf112.rocketman.view.RocketManView;
import inf112.rocketman.view.ViewableRocketManModel;
import inf112.rocketman.view.assets.RocketManAudio;

/**
 * Controls game flow, input handling, sound triggering, and communication
 * between the model and the view in RocketMan.
 *
 * <p> The controller reads player input, updates the model each frame,
 * tells the views to render the current state, and uses the audio manager
 * to play sound effects and music when needed.</p>
 */
public class RocketManController {

    private final RocketManView view;
    private final RocketManAudio audio;
    private final ControllableRocketManModel controllableModel;
    private final ViewableRocketManModel viewableModel;

    private boolean jetpackPlaying = false;
    private long jetpackSoundId = -1;
    private boolean gameOverSoundPlayed = false;

    private boolean robotPlaying = false;
    private long robotSoundId = -1;

    private static final String MUSIC = "sounds/music.mp3";
    private static final String JETPACK_SOUND = "sounds/jetpack.mp3";
    private static final String COIN_SOUND = "sounds/coin.mp3";
    private static final String POWERUP_SOUND = "sounds/powerup.mp3";
    private static final String BIRD_SOUND = "sounds/bird.mp3";
    private static final String GAME_OVER = "sounds/game_over.mp3";
    private static final String MEOW_END_SONG = "sounds/MeowMeow.mp3";
    private static final String ROBOT_SOUND = "sounds/robot.mp3";
    private static final String GRAVITY_SUIT_SOUND = "sounds/gravity_suit.mp3";

    /**
     * Creates a new controller for the RocketMan game
     * @param controllableRocketManModel the model interface used to update and control the game state
     * @param viewableModel the model interface used to read game state for rendering and input decisions
     * @param view the view responsible for rendering the game
     * @param audio the audio manager responsible for music and sound effects
     */
    public RocketManController(ControllableRocketManModel controllableRocketManModel, ViewableRocketManModel viewableModel, RocketManView view, RocketManAudio audio) {
        this.controllableModel = controllableRocketManModel;
        this.viewableModel = viewableModel;
        this.view = view;
        this.audio = audio;
    }

    /**
     * Starts the controller by playing the main background music.
     */
    public void create() {
        audio.playExclusiveMusic(MUSIC);
    }

    /**
     * Updates the game for current frame, handles sounds, and renders the view.
     */
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        boolean input = getMovementInput();

        controllableModel.update(dt, input);

        handleFrameSounds();

        view.render(viewableModel);
    }

    private boolean getMovementInput() {
        if (viewableModel.hasBirdPowerUp() || viewableModel.hasGravitySuitPowerUp()) {
            return Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
        }
        return Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }


    private void handleFrameSounds() {
        GameState state = controllableModel.getGameState();

        if (state == GameState.GAME_OVER) {
            handleGameOverSounds();
            return;
        }

        handleCollectibleSounds();
    }

    private void handleGameOverSounds() {
        stopJetpackIfPlaying();
        stopRobotIfPlaying();

        if (!gameOverSoundPlayed) {
            audio.stopAllMusic();
            audio.playSound(GAME_OVER);
            audio.playExclusiveMusic(MEOW_END_SONG);
            gameOverSoundPlayed = true;
        }
    }

    private void handleCollectibleSounds() {
        if (viewableModel.didCollectPowerUpThisFrame()) {
            audio.playSound(POWERUP_SOUND);
        }

        if (viewableModel.didCollectCoinThisFrame()) {
            audio.playSound(COIN_SOUND);
        }
    }

    private void stopJetpackIfPlaying() {
        if (jetpackPlaying) {
            audio.stopSound(JETPACK_SOUND, jetpackSoundId);
            jetpackPlaying = false;
            jetpackSoundId = -1;
        }
    }

    private void stopRobotIfPlaying() {
        if (robotPlaying) {
            audio.stopSound(ROBOT_SOUND, robotSoundId);
            robotPlaying = false;
            robotSoundId = -1;
        }
    }

    /**
     * Resizes the game view to match the new window dimensions.
     *
     * @param width the new window width
     * @param height the new window height
     */
    public void resize(int width, int height) {
        view.resize(width, height);
    }

    /**
     * Disposes resources used by the controller, including the view and audio manager.
     */
    public void dispose() {
        view.dispose();
        audio.dispose();
    }

    /**
     * Handles input based on the current game state.
     */
    public  void handleInput() {
        GameState currentState = controllableModel.getGameState();

        switch (currentState) {
            case HOME_SCREEN -> handleHomeScreenInput();
            case PLAYING -> handlePlayingInput();
            case PAUSE -> handlePauseInput();
            case GAME_OVER -> handleGameOverInput();
            case INSTRUCTIONS -> handleInstructionInput();
            default -> throw new IllegalStateException("Unexpected game state: " + currentState);
        }

    }

    /**
     * Starts a new game for the given player name.
     *
     * @param playerName the name of the player starting the game
     */
    public void startGame(String playerName){
        audio.stopAllMusic();
        audio.playExclusiveMusic(MUSIC);
        resetSoundState();
        controllableModel.setPlayerName(playerName);
        controllableModel.startNewGame();
    }

    private void handleHomeScreenInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            audio.stopAllMusic();
            audio.playExclusiveMusic(MUSIC);
            resetSoundState();
            controllableModel.startNewGame();
        }

    }

    private void handlePlayingInput() {
        handlePauseButtonInput();
        handleJetpackInput();
        handleBirdInput();
        handleRobotInput();
        handleGravitySuitInput();
    }

    private void handleGravitySuitInput(){
        boolean spaceClicked = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
        PowerUpType powerUpType = viewableModel.getPlayer().getActivePowerUp();

        if (powerUpType == PowerUpType.GRAVITY_SUIT && spaceClicked) {
            audio.playSound(GRAVITY_SUIT_SOUND);
        }
    }

    private void handlePauseButtonInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            controllableModel.pauseGame();
        }

        if (Gdx.input.justTouched()) {
            var mousePos = view.getMouseWorldPosition();

            float pauseX = controllableModel.getWorldWidth() - 60;
            float pauseY = controllableModel.getWorldHeight() - 100;

            if (Math.abs(mousePos.x - pauseX) < 50 && Math.abs(mousePos.y - pauseY) < 50) {
                controllableModel.pauseGame();
            }
        }
    }


    private void handleJetpackInput() {
        boolean spaceHeld = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        PowerUpType powerUpType = viewableModel.getPlayer().getActivePowerUp();

        if (spaceHeld && !jetpackPlaying && powerUpType == PowerUpType.NORMAL) {
            jetpackSoundId = audio.loopSound(JETPACK_SOUND);
            jetpackPlaying = true;
        } else if ((!spaceHeld || powerUpType != PowerUpType.NORMAL) && jetpackPlaying) {
            stopJetpackIfPlaying();
        }
    }

    private void handleBirdInput() {
        boolean spaceClicked = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
        PowerUpType powerUpType = viewableModel.getPlayer().getActivePowerUp();

        if (powerUpType == PowerUpType.BIRD && spaceClicked) {
            audio.playSound(BIRD_SOUND);
        }
    }

    private void handleRobotInput() {
        boolean spaceHeld = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        PowerUpType powerUpType = viewableModel.getPlayer().getActivePowerUp();

        if (spaceHeld && !robotPlaying && powerUpType == PowerUpType.ROBOT) {
            robotSoundId = audio.loopSound(ROBOT_SOUND);
            robotPlaying = true;
        } else if ((!spaceHeld || powerUpType != PowerUpType.ROBOT) && robotPlaying) {
            audio.stopSound(ROBOT_SOUND, robotSoundId);
            robotPlaying = false;
            robotSoundId = -1;
        }
    }

    private void resetSoundState() {
        gameOverSoundPlayed = false;
        jetpackPlaying = false;
        jetpackSoundId = -1;
        robotPlaying = false;
        robotSoundId = -1;
    }


    private void handlePauseInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            controllableModel.resumeGame();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            audio.stopMusic(MEOW_END_SONG);
            audio.playMusic(MUSIC);
            gameOverSoundPlayed = false;
            controllableModel.goToHomescreen();
        }
    }

    private void handleGameOverInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            audio.stopAllMusic();
            audio.playExclusiveMusic(MUSIC);
            gameOverSoundPlayed = false;
            controllableModel.goToHomescreen();
        }
    }

    private void handleInstructionInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            controllableModel.goToHomescreen();
        }
    }

    /**
     * Returns the game view used by this controller.
     *
     * @return the RocketMan view
     */
    public RocketManView getView() {
        return view;
    }

    /**
     * Returns the current game state.
     *
     * @return the current game state
     */
    public GameState getState() {
        return controllableModel.getGameState();
    }

    /**
     * Switches the game to the instruction screen.
     */
    public void showInstruction() {
        controllableModel.showInstructions();
    }

    /**
     * Returns the viewable model used by the controller.
     *
     * @return the viewable game model
     */
    public ViewableRocketManModel getViewableModel() {
        return viewableModel;
    }
}