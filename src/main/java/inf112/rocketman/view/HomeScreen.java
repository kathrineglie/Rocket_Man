package inf112.rocketman.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

public class HomeScreen implements Screen {

    private final Main game;
    private RocketManController controller;
    private SpriteBatch batch;
    private BitmapFont font;

    public HomeScreen(Main game, RocketManController controller) {
        this.game = game;
        this.controller = controller;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);

        batch.begin();
        font.draw(batch, "ROCKET MAN", 400, 500);
        font.draw(batch, "Press ENTER to start", 350, 450);
        batch.end();

        controller.handleInput();
    }

    @Override
    public void resize(int width, int height) {

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
        batch.dispose();
        font.dispose();
    }
}
