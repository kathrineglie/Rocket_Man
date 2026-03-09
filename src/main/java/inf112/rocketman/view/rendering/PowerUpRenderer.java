package inf112.rocketman.view.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.rocketman.model.PowerUps.PowerUp;
import inf112.rocketman.view.TextureProvider;
import inf112.rocketman.view.ViewableRocketManModel;

public class PowerUpRenderer {
    private final TextureProvider textures;

    public PowerUpRenderer(TextureProvider textures){
        this.textures = textures;
    }

    public void render(SpriteBatch batch, ViewableRocketManModel model){
        PowerUp powerUp = model.getPowerUp();
        if (powerUp == null){
            return;
        }

        Texture texture = textures.getTexture("PowerUps/Box.png");
        batch.draw(texture, powerUp.getX(), powerUp.getY(), powerUp.getWidth(),  powerUp.getHeight());
    }

}
