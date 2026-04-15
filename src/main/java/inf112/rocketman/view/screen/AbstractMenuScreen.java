package inf112.rocketman.view.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

public abstract class AbstractMenuScreen implements Screen {

    protected static final float WORLD_WIDTH = 1200f;
    protected static final float WORLD_HEIGHT = 800f;

    protected final Main game;
    protected final RocketManController controller;
    protected final SpriteBatch batch;
    protected BitmapFont font;
    protected BitmapFont smallFont;
    protected ShapeRenderer shapeRenderer;
    protected OrthographicCamera camera;
    protected Viewport viewport;

    protected AbstractMenuScreen(Main game, RocketManController controller, SpriteBatch batch) {
        this.game = game;
        this.controller = controller;
        this.batch = batch;
    }

    @Override
    public void show() {
        if (shapeRenderer == null){
            shapeRenderer = new ShapeRenderer();
        }

        if (camera == null){
            camera = new OrthographicCamera();
        }
        if (viewport == null){
            viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        }
        viewport.apply();

        this.font = controller.getView().getAssets().getTitleFont();
        this.smallFont = controller.getView().getAssets().getFont();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (shapeRenderer != null){
            shapeRenderer.dispose();
            shapeRenderer = null;
        }
    }
}
