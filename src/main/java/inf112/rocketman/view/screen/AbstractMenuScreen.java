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

/**
 * Abstract base class for menu-related screens in the game.
 *
 * <p>This class provides shared setup for fonts, camera, viewport,
 * and shape rendering used by menu screens.</p>
 */
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

    /**
     * Creates a new abstract menu screen.
     *
     * @param game the main game instance
     * @param controller the game controller
     * @param batch the sprite batch used for drawing
     */
    protected AbstractMenuScreen(Main game, RocketManController controller, SpriteBatch batch) {
        this.game = game;
        this.controller = controller;
        this.batch = batch;
    }

    /**
     * Initializes shared menu resources such as fonts, camera, viewport, and shape renderer.
     */
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

    /**
     * Updates the viewport to match the new window size.
     *
     * @param width the new window width
     * @param height the new window height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    /**
     * Called when the screen is paused.
     */
    @Override
    public void pause() {}

    /**
     * Called when the screen resumes after being paused.
     */
    @Override
    public void resume() {}

    /**
     * Called when this screen is no longer the current screen.
     */
    @Override
    public void hide() {}

    /**
     * Disposes resources owned by this menu screen.
     */
    @Override
    public void dispose() {
        if (shapeRenderer != null){
            shapeRenderer.dispose();
            shapeRenderer = null;
        }
    }
}
