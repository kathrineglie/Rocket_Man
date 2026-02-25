package inf112.rocketman.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerRenderer {
    private final TextureProvider textures;

    public PlayerRenderer(TextureProvider textures){
        this.textures = textures;
    }

    public void render(SpriteBatch batch, ViewableRocketManModel model){
        Texture player = textures.getTexture("tevje.png");

        batch.draw(player, model.getPlayerX(), model.getPlayerY(), model.getPlayerWidth(), model.getPlayerHeight());
    }
}
