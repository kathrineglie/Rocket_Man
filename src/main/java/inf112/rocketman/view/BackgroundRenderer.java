package inf112.rocketman.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BackgroundRenderer {

    private final TextureProvider textures;

    public BackgroundRenderer(TextureProvider textures){
        this.textures = textures;
    }

    public void render(SpriteBatch batch, Viewport viewPort, ViewableRocketManModel model){
        float worldW = viewPort.getWorldWidth();
        float worldH = viewPort.getWorldHeight();

        float x = model.getBackgroundScrollX();
        x = x % worldW;

        Texture bg = textures.getTexture("background.png");
        batch.draw(bg, -x,0, worldW, worldH);
        batch.draw(bg, -x+worldW, 0, worldW, worldH);
    }
}
