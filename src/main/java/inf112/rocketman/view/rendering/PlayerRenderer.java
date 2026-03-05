package inf112.rocketman.view.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.rocketman.model.Character.TPowah;
import inf112.rocketman.view.TextureProvider;
import inf112.rocketman.view.ViewableRocketManModel;

public class PlayerRenderer {
    private final TextureProvider textures;

    public PlayerRenderer(TextureProvider textures){
        this.textures = textures;
    }

    public void render(SpriteBatch batch, ViewableRocketManModel model){

        TPowah player = model.getPlayer();

        String playerImg;

        if (model.isThrusting()){
            playerImg = "TPowah/TPowahFlames.png";
        } else{
            playerImg = "TPowah/TPowah.png";
        }

        batch.draw(textures.getTexture(playerImg), player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }
}
