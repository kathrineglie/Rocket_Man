package inf112.rocketman.view.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

public abstract class AbstractMenuScreen implements Screen {

    protected final Main game;
    protected RocketManController controller;
    protected SpriteBatch batch;
    protected BitmapFont font;
    protected BitmapFont smallFont;

    protected AbstractMenuScreen(Main game, RocketManController controller) {
        this.game = game;
        this.controller = controller;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        this.font = controller.getView().getAssets().getTitleFont();
        this.smallFont = controller.getView().getAssets().getFont();
    }

    @Override
    public void resize(int i, int i1) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
    }
}
