package inf112.rocketman.view.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.rocketman.view.TextureProvider;
import inf112.rocketman.view.ViewableRocketManModel;

/**
 * Renders the scrolling background of the game.
 */
public class BackgroundRenderer {

    private final TextureProvider textures;

    /**
     * Creates a new background renderer.
     *
     * @param textures the texture provider used to access background textures.
     */
    public BackgroundRenderer(TextureProvider textures){
        this.textures = textures;
    }

    /**
     * Renders the game background.
     *
     * <p>The background is drawn twice to create a continuous scrolling effect.</p>
     *
     * @param batch the sprite batch used for drawing
     * @param viewPort the viewport defining the visible world size
     * @param model the viewable game model providing background scroll data
     */
    public void render(SpriteBatch batch, Viewport viewPort, ViewableRocketManModel model) {
        float worldW = viewPort.getWorldWidth();
        float worldH = viewPort.getWorldHeight();
        float margin = model.getMargin();

        float playableW = worldW - 2 * margin;
        float playableH = worldH - 2 * margin;

        float x = model.getBackgroundScrollX();

        while (x <= -playableW) {
            x += playableW;
        }

        while (x >= playableW) {
            x -= playableW;
        }

        Texture bg = textures.getTexture("background/background.png");

        batch.draw(bg, margin + x - playableW, margin, playableW, playableH);
        batch.draw(bg, margin + x, margin, playableW, playableH);
        batch.draw(bg, margin + x + playableW, margin, playableW, playableH);
    }
}
