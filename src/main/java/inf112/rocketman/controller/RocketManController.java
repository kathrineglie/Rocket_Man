package inf112.rocketman.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.rocketman.Main;
import inf112.rocketman.model.GameModel;
import inf112.rocketman.model.GameState;
import inf112.rocketman.view.RocketManView;
import inf112.rocketman.view.ViewableRocketManModel;

public class RocketManController {

    private RocketManView view;
    private ControllableRocketManModel controllableModel;
    private ViewableRocketManModel viewableModel;
    private Main game;

    public RocketManController(Main game) {
        game = game;
    }

    public void create() {
        view = new RocketManView();
        view.create(1000, 800);

        GameModel gameModel = new GameModel(view.getWorldHeight(), view.getWorldWidth());

        controllableModel = gameModel;
        viewableModel = gameModel;
    }


    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        boolean space = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        controllableModel.update(dt, space);

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
        }

    }

    public GameState getState() {
        return controllableModel.getGameState();
    }
}