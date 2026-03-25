package inf112.rocketman.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.rocketman.model.GameState;
import inf112.rocketman.model.PowerUps.PowerUpType;
import inf112.rocketman.view.RocketManView;
import inf112.rocketman.view.ViewableRocketManModel;

public class RocketManController {

    private RocketManView view;
    private final ControllableRocketManModel controllableModel;
    private final ViewableRocketManModel viewableModel;

    private boolean jetpackPlaying = false;
    private long jetpackSoundId = -1;
    private boolean gameOverSoundPlayed = false;
    private static final String MUSIC = "Sounds/music.mp3";
    private static final String JETPACK_SOUND = "Sounds/jetpack.mp3";
    private static final String COIN_SOUND = "Sounds/coin.mp3";
    private static final String POWERUP_SOUND ="Sounds/powerup.mp3";
    private static final String BIRD_SOUND = "Sounds/bird.mp3";
    private static final String GAME_OVER = "Sounds/game_over.mp3";
    private static final String MEOW_END_SONG = "Sounds/MeowMeow.mp3";

    public RocketManController(ControllableRocketManModel controllableRocketManModel, ViewableRocketManModel viewableModel) {
        this.controllableModel = controllableRocketManModel;
        this.viewableModel = viewableModel;
    }

    public void create() {
        view = new RocketManView();
        view.create(controllableModel.getWorldWidth(), controllableModel.getWorldHeight());
        view.playExclusiveMusic(MUSIC);
    }


    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        boolean input = getMovementInput();

        controllableModel.update(dt, input);

        handleFrameSounds();

        view.render(viewableModel);
    }

    private boolean getMovementInput() {
        if (viewableModel.hasBirdPowerUp()) {
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

        if (!gameOverSoundPlayed) {
            view.stopAllMusic();
            view.playSound(GAME_OVER);
            view.playExclusiveMusic(MEOW_END_SONG);
            gameOverSoundPlayed = true;
        }
    }

    private void handleCollectibleSounds() {
        if (viewableModel.didCollectPowerUpThisFrame()) {
            view.playSound(POWERUP_SOUND);
        }

        if (viewableModel.didCollectCoinThisFrame()) {
            view.playSound(COIN_SOUND);
        }
    }

    private void stopJetpackIfPlaying() {
        if (jetpackPlaying) {
            view.stopSound(JETPACK_SOUND, jetpackSoundId);
            jetpackPlaying = false;
            jetpackSoundId = -1;
        }
    }



    public void resize(int width, int height) {
        view.resize(width, height);
    }

    public void dispose() {
        view.dispose();
    }

    public  void handleInput() {
        GameState currentState = controllableModel.getGameState();

        switch (currentState) {
            case HOME_SCREEN -> handleHomeScreenInput();
            case PLAYING -> handlePlayingInput();
            case PAUSE -> handlePauseInput();
            case GAME_OVER -> handleGameOverInput();
            case INSTRUCTIONS -> handleInstructionInput();
        }

    }

    private void handleHomeScreenInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            view.stopAllMusic();
            view.playExclusiveMusic(MUSIC);
            resetSoundState();
            controllableModel.startGame();
        }
    }

    private void handlePlayingInput() {
        handlePauseButtonInput();
        handleJetpackInput();
        handleBirdInput();
    }

    private void handlePauseButtonInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            controllableModel.pauseGame();
        }

        if (Gdx.input.justTouched()) {
            var mousePos = view.getMouseWorldPosition();

            float pauseX = controllableModel.getWorldWidth() - 60;
            float pauseY = controllableModel.getWorldHeight() - 60;

            if (Math.abs(mousePos.x - pauseX) < 50 && Math.abs(mousePos.y - pauseY) < 50) {
                controllableModel.pauseGame();
            }
        }
    }


    private void handleJetpackInput() {
        boolean spaceHeld = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        PowerUpType powerUpType = viewableModel.getPlayer().getActivePowerUp();

        if (spaceHeld && !jetpackPlaying && powerUpType == PowerUpType.NORMAL) {
            jetpackSoundId = view.loopSound(JETPACK_SOUND);
            jetpackPlaying = true;
        } else if ((!spaceHeld || powerUpType != PowerUpType.NORMAL) && jetpackPlaying) {
            stopJetpackIfPlaying();
        }
    }

    private void handleBirdInput() {
        boolean spaceClicked = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
        PowerUpType powerUpType = viewableModel.getPlayer().getActivePowerUp();

        if (powerUpType == PowerUpType.BIRD && spaceClicked) {
            view.playSound(BIRD_SOUND);
        }
    }

    private void resetSoundState() {
        gameOverSoundPlayed = false;
        jetpackPlaying = false;
        jetpackSoundId = -1;
    }


    private void handlePauseInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            controllableModel.resumeGame();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            view.stopMusic(MEOW_END_SONG);
            view.playMusic(MUSIC);
            gameOverSoundPlayed = false;
            controllableModel.goToHomescreen();
        }
    }

    private void handleGameOverInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            view.stopAllMusic();
            view.playExclusiveMusic(MUSIC);
            gameOverSoundPlayed = false;
            controllableModel.goToHomescreen();
        }
    }

    private void handleInstructionInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            controllableModel.goToHomescreen();
        }
    }

    public RocketManView getView() {
        return view;
    }

    public GameState getState() {
        return controllableModel.getGameState();
    }

    public void showInstruction() {
        controllableModel.showInstructions();
    }

    public ViewableRocketManModel getViewableModel() {
        return viewableModel;
    }
}