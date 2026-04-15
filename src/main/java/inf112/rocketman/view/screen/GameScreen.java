package inf112.rocketman.view.screen;

import com.badlogic.gdx.Screen;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

public class GameScreen implements Screen {
    private final RocketManController controller;

    public GameScreen(RocketManController controller) {
        this.controller = controller;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        controller.handleInput();
        controller.render();
    }

    @Override public void resize(int width, int height) { controller.resize(width, height); }
    @Override public void dispose() {
        //controller.dispose();
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
