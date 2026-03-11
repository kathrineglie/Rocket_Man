package inf112.rocketman.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.rocketman.model.GameState;
import inf112.rocketman.view.RocketManView;
import inf112.rocketman.view.ViewableRocketManModel;

public class RocketManController {

    private RocketManView view;
    private ControllableRocketManModel controllableModel;
    private ViewableRocketManModel viewableModel;

    private boolean jetpackPlaying = false;
    private long jetpackSoundId = -1;
    private static final String JETPACK_SOUND = "Sounds/Teleport/MP3/Teleport.mp3";

    public RocketManController(ControllableRocketManModel controllableRocketManModel, ViewableRocketManModel viewableModel) {
        this.controllableModel = controllableRocketManModel;
        this.viewableModel = viewableModel;
    }

    public void create() {
        view = new RocketManView();
        view.create(controllableModel.getWorldWidth(), controllableModel.getWorldHeight());
    }


    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        boolean space = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        boolean input;
        if (viewableModel.hasBirdPowerUp()) {
            input = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
        } else {
            input = Gdx.input.isKeyPressed(Input.Keys.SPACE);
        }

        controllableModel.update(dt, input);

        view.render(viewableModel);
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
            controllableModel.startGame();
        }
    }

    private void handlePlayingInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            controllableModel.pauseGame();
        }
        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            float pauseX = controllableModel.getWorldWidth() - 60;
            float pauseY = controllableModel.getWorldHeight() - 60;

            if (Math.abs(mouseX - pauseX) < 50 && Math.abs(mouseY - pauseY) < 50) {
                controllableModel.pauseGame();
            }

        }

        boolean spaceHeld = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        if (spaceHeld && !jetpackPlaying){
            jetpackSoundId = view.playSound(JETPACK_SOUND);
            jetpackPlaying = true;
        } else if (!spaceHeld && jetpackPlaying){
            view.stopSound(JETPACK_SOUND, jetpackSoundId);
            jetpackPlaying = false;
            jetpackSoundId =-1;
        }
    }

    private void handlePauseInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            controllableModel.resumeGame();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            controllableModel.goToHomescreen();
        }
    }

    private void handleGameOverInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
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
}