package inf112.rocketman.controller;

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
        if (currentState == GameState.HOME_SCREEN) {
            if (Gdx.input.isKeyJustPressed((Input.Keys.SPACE))) {
                controllableModel.startGame();
            }
        }

        else if (currentState == GameState.PLAYING) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                controllableModel.goToHomescreen();
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

    }

    public RocketManView getView() {
        return view;
    }

    public GameState getState() {
        return controllableModel.getGameState();
    }
}