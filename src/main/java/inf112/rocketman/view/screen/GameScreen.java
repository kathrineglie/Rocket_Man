package inf112.rocketman.view.screen;

import com.badlogic.gdx.Screen;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

/**
 * Main gameplay screen for RocketMan.
 *
 * <p>This screens forwards input handling and rendering to the game controller.</p>
 */
public class GameScreen implements Screen {
    private final RocketManController controller;

    /**
     * Creates a new game screen.
     *
     * @param controller the game controller
     */
    public GameScreen(RocketManController controller) {
        this.controller = controller;
    }

    /**
     * Called when this screen becomes the current screen.
     */
    @Override
    public void show() {
    }

    /**
     * Renders the game screen and handles player input.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        controller.handleInput();
        controller.render();
    }

    /**
     * Resizes the game screen.
     *
     * @param width the new window width
     * @param height the new window height
     */
    @Override public void resize(int width, int height) { controller.resize(width, height); }

    /**
     * Disposes resources used by the game screen.
     */
    @Override public void dispose() {
    }

    /**
     * Called when the game screen is paused
     */
    @Override public void pause() {}

    /**
     * Called when the game screen resumes after being paused.
     */
    @Override public void resume() {}

    /**
     * Called when this screen is no longer the current screen.
     */
    @Override public void hide() {}
}
