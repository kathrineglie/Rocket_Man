package inf112.rocketman.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.rocketman.Main;
import inf112.rocketman.model.GameModel;
import inf112.rocketman.view.GameScreen;
import inf112.rocketman.view.RocketManView;
import inf112.rocketman.view.ViewableRocketManModel;

public class RocketManController {

    private RocketManView view;
    private ControllableRocketManModel model;
    private ViewableRocketManModel viewModel;
    private Main game;

    public RocketManController(Main game) {
        this.game = game;
    }

    public void create() {
        view = new RocketManView();
        view.create(1000, 800);

        GameModel gameModel = new GameModel(view.getWorldHeight(), view.getWorldWidth());

        model = gameModel;
        viewModel = gameModel;

    }

    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        boolean space = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        model.update(dt, space);

        view.render(viewModel);

    }

    public void resize(int width, int height) {
        view.resize(width, height);
    }

    public void dispose() {
        view.dispose();
    }

    public  void homeInput() {
        if (Gdx.input.isKeyJustPressed((Input.Keys.SPACE))) {
            game.setScreen(new GameScreen(game));
        }
    }
}