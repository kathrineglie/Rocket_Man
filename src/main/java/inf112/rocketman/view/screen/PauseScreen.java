package inf112.rocketman.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

/**
 * Screen shown when the game is paused.
 *
 * <p>This screen renders the current game view with a dark overlay
 * and displays pause-related information and controls.</p>
 */
public class PauseScreen extends AbstractMenuScreen {

    /**
     * Creates a new pause screen.
     *
     * @param game the main game instance
     * @param controller the game controller
     * @param batch the sprite batch used for drawing
     */
    public PauseScreen(Main game, RocketManController controller, SpriteBatch batch) {
        super(game, controller, batch);
    }

    /**
     * Renders the pause screen.
     *
     * @param v The time in seconds since the last render.
     */
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.43f, 0.16f, 0.32f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        controller.handleInput();
        controller.getView().render(controller.getViewableModel());

        viewport.apply();
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        batch.setProjectionMatrix(viewport.getCamera().combined);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,0.5f);
        shapeRenderer.rect(0,0,viewport.getWorldWidth(), viewport.getWorldHeight());
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();

        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();
        float centerX = width / 2f;

        int score = controller.getViewableModel().getGameScore();
        int coins = controller.getViewableModel().getCoinCount();

        font.setColor(Color.WHITE);
        drawCentered(font, "PAUSED", centerX, height / 2f + 100);

        smallFont.getData().setScale(1.25f);
        smallFont.setColor(Color.WHITE);
        drawCentered(smallFont, "Press P to resume", centerX, height / 2f);
        drawCentered(smallFont, "Press ESC to QUIT to Menu", centerX, height / 2f - 80);
        drawCentered(smallFont, "Score: " + score + "M", centerX, height / 2f - 140);
        drawCentered(smallFont, "Coins: " + coins, centerX, height / 2f - 190);
        batch.end();

    }

    private void drawCentered(BitmapFont font, String text, float centerX, float y) {
        GlyphLayout layout = new GlyphLayout(font, text);
        font.draw(batch, layout, centerX - layout.width / 2f, y);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        controller.resize(width, height);
    }
}
