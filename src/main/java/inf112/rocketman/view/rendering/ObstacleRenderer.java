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
    public void render(SpriteBatch batch, ViewableRocketManModel model){
        for (IObstacle obstacle : model.getObstacles()) {
            if (obstacle instanceof Rocket rocket) {
                Texture texture;
                if (rocket.isActive()) {
                    texture = textures.getTexture("obstacles/Rocket.png");
                } else {
                    texture = textures.getTexture("obstacles/warning.png");
                }
                batch.draw(texture, rocket.getX(), rocket.getY(), rocket.getWidth(), rocket.getHeight());
            }

            if (obstacle instanceof Lazer lazer) {
                Texture texture;
                if (lazer.getProgressionLevel() == 1) {
                    texture = textures.getTexture("obstacles/inactiveLazer.png");
                } else if (lazer.getProgressionLevel() == 2) {
                    texture = textures.getTexture("obstacles/harmlessLazer.png");
                } else {
                    texture = textures.getTexture("obstacles/activeLazer.png");
                }
                batch.draw(texture, lazer.getX(), lazer.getY(), lazer.getWidth(), lazer.getHeight());
            }
            if (obstacle instanceof Flame flame) {
                Texture texture;
                texture = textures.getTexture("obstacles/flame.png");

                batch.draw(texture,
                        flame.getX() - flame.getWidth()/2f,
                        flame.getY() - flame.getHeight()/2f,
                        flame.getWidth()/2f,
                        flame.getHeight()/2f,
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
    }
}

