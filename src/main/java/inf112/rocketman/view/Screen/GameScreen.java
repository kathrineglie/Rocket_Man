package inf112.rocketman.view.Screen;

import com.badlogic.gdx.Screen;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;
import inf112.rocketman.model.GameState;
import inf112.rocketman.view.ViewableRocketManModel;

public class GameScreen implements Screen {
    private final Main game;
    private RocketManController controller;

    public GameScreen(Main game, RocketManController controller) {
        this.game = game;
        this.controller = controller;
    }

    @Override
    public void show() {
        //controller.create();
    }

    @Override
    public void render(float delta) {
        controller.handleInput();
        if (controller.getState() == GameState.PAUSE) {
            controller.getView().render(controller.getViewableModel());

            game.getPauseScreen().render(delta);
        } else {
            controller.render();
        }
    }

    @Override public void resize(int width, int height) { controller.resize(width, height); }
    @Override public void dispose() { controller.dispose(); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
