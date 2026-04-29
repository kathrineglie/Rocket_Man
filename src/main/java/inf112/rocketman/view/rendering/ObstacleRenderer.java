package inf112.rocketman.view.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.rocketman.model.obstacles.flames.Flame;
import inf112.rocketman.model.obstacles.IObstacle;
import inf112.rocketman.model.obstacles.lazers.Lazer;
import inf112.rocketman.model.obstacles.rockets.Rocket;
import inf112.rocketman.view.TextureProvider;
import inf112.rocketman.view.ViewableRocketManModel;

/**
 * Renders obstacle objects in the game world.
 *
 * <p>This renderer draws different obstacle types using the correct texture
 * and visual representation.</p>
 */
public class ObstacleRenderer {
    private final TextureProvider textures;

    /**
     * Creates a new obstacle renderer.
     *
     * @param textures the texture provider used to access obstacle textures
     */
    public ObstacleRenderer(TextureProvider textures){
        this.textures = textures;
    }

    /**
     * Renders all obstacles currently visible in the model.
     *
     * @param batch the sprite batch used for drawing
     * @param model the viewable game model containing the obstacles
     */
    public void render(SpriteBatch batch, ViewableRocketManModel model) {
        for (IObstacle obstacle : model.getObstacles()) {
            if (obstacle instanceof Rocket rocket) {
                renderRocket(batch, rocket);
            } else if (obstacle instanceof Lazer lazer) {
                renderLazer(batch, lazer);
            } else if (obstacle instanceof Flame flame) {
                renderFlame(batch, flame);
            }
        }
    }

    private void renderRocket(SpriteBatch batch, Rocket rocket) {
        Texture texture = rocket.isActive()
                ? textures.getTexture("obstacles/Rocket.png")
                : textures.getTexture("obstacles/warning.png");

        batch.draw(texture, rocket.getX(), rocket.getY(), rocket.getWidth(), rocket.getHeight());
    }

    private void renderLazer(SpriteBatch batch, Lazer lazer) {
        Texture texture = getLazerTexture(lazer);
        batch.draw(texture, lazer.getX(), lazer.getY(), lazer.getWidth(), lazer.getHeight());
    }

    private Texture getLazerTexture(Lazer lazer) {
        if (lazer.getProgressionLevel() == 1) {
            return textures.getTexture("obstacles/inactiveLazer.png");
        }

        if (lazer.getProgressionLevel() == 2) {
            return textures.getTexture("obstacles/harmlessLazer.png");
        }

        return textures.getTexture("obstacles/activeLazer.png");
    }

    private void renderFlame(SpriteBatch batch, Flame flame) {
        Texture texture = textures.getTexture("obstacles/flame.png");

        batch.draw(texture,
                flame.getX() - flame.getWidth() / 2f,
                flame.getY() - flame.getHeight() / 2f,
                flame.getWidth() / 2f,
                flame.getHeight() / 2f,
                flame.getWidth(),
                flame.getHeight(),
                1, 1,
                flame.getAngle(),
                0, 0,
                texture.getWidth(),
                texture.getHeight(),
                false, false
        );
    }

}


