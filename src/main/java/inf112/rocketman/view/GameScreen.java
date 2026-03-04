package inf112.rocketman.view;

import com.badlogic.gdx.Screen;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

public class GameScreen implements Screen {
    private RocketManController controller;

    public  GameScreen(Main game) {
        controller = new RocketManController();
    }
    @Override
    public void show() {
        controller.create();
    }

    @Override
    public void render(float delta) {
        controller.render();
    }

    @Override
    public void resize(int width, int height) {
        controller.resize(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        controller.dispose();
    }
}
