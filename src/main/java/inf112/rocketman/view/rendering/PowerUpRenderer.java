package inf112.rocketman.view.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.rocketman.model.PowerUps.PowerUp;
import inf112.rocketman.view.TextureProvider;
import inf112.rocketman.view.ViewableRocketManModel;

/**
 * Renders power-up objects in the game world
 */
public class PowerUpRenderer {
    private final TextureProvider textures;

    /**
     * Creates a new power-up renderer.
     *
     * @param textures the texture provider used to access power-up textures
     */
    public PowerUpRenderer(TextureProvider textures){
        this.textures = textures;
    }

    /**
     * Renders the current power-up if one exists in the model.
     *
     * @param batch the sprite batch used for drawing
     * @param model the viewable game model containing the power-up
     */
    public void render(SpriteBatch batch, ViewableRocketManModel model){
        PowerUp powerUp = model.getPowerUp();
        if (powerUp == null){
            return;
        }

        Texture texture = textures.getTexture("PowerUps/box.png");
        batch.draw(texture, powerUp.getX(), powerUp.getY(), powerUp.getWidth(),  powerUp.getHeight());
    }

}
