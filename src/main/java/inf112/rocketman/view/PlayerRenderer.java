package inf112.rocketman.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.rocketman.model.Character.TPowah;

public class PlayerRenderer {
    private final TextureProvider textures;

    public PlayerRenderer(TextureProvider textures){
        this.textures = textures;
    }

    public void render(SpriteBatch batch, ViewableRocketManModel model){
        Texture player_img = textures.getTexture("tevje.png");
        TPowah player = model.getPlayer();

        batch.draw(player_img, player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }
}
