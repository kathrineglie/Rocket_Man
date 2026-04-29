package inf112.rocketman.view.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.rocketman.view.ViewableRocketManModel;
import inf112.rocketman.view.assets.RocketManAssets;

/**
 * Renders the heads-up display (HUD) for the game.
 *
 * <p>The HUD shows information such as score, coin count, and the pause symbol.</p>
 */
public class HudRenderer {
    private final BitmapFont font;
    private final GlyphLayout layout;

    /**
     * Creates a new HUD renderer.
     *
     * @param assets the asset manager used to access the font
     */
    public HudRenderer(RocketManAssets assets) {
        this.font = assets.getFont();
        this.layout = new GlyphLayout();
    }

    /**
     * Renders the HUD.
     *
     * @param batch the sprite batch used for drawing
     * @param viewport the viewport defining the visible world size
     * @param model the viewable game model providing HUD values.
     */
    public void render(SpriteBatch batch, Viewport viewport, ViewableRocketManModel model) {
        drawHud(batch, viewport, model);
    }

    private void drawHud(SpriteBatch batch, Viewport viewport, ViewableRocketManModel model) {
        font.getData().setScale(1.25f);

        float screenMargin = model.getMargin();
        float padding = 15f;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        font.setColor(Color.WHITE);
        String scoreText = "Score: " + model.getGameScore() + "M";
        layout.setText(font, scoreText);
        float scoreX = worldWidth - screenMargin - layout.width - padding;
        float scoreY = worldHeight - screenMargin - padding;
        font.draw(batch, layout, scoreX, scoreY);

        font.setColor(Color.ORANGE);
        String coinText = "Coins: " + model.getCoinCount();
        layout.setText(font, coinText);
        float coinX = worldWidth - screenMargin - layout.width - padding;
        float coinY = scoreY - 40f;
        font.draw(batch, layout, coinX, coinY);

        font.setColor(Color.WHITE);
        float pauseX = worldWidth - screenMargin - 60f;
        float pauseY = coinY - 50f;
        font.draw(batch, "||", pauseX, pauseY);

        font.getData().setScale(1.0f);
    }
}
