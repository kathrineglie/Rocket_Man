package inf112.rocketman.view.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.rocketman.model.Obstacles.Flames.Flame;
import inf112.rocketman.model.Obstacles.IObstacle;
import inf112.rocketman.model.Obstacles.Lazers.Lazer;
import inf112.rocketman.model.Obstacles.Rockets.Rocket;
import inf112.rocketman.view.TextureProvider;
import inf112.rocketman.view.ViewableRocketManModel;

public class ObstacleRenderer {
    private final TextureProvider textures;

    public ObstacleRenderer(TextureProvider textures){
        this.textures = textures;
    }

    public void render(SpriteBatch batch, ViewableRocketManModel model){
        for (IObstacle obstacle : model.getObstacles()) {
            if (obstacle instanceof Rocket rocket) {
                Texture texture;
                if (rocket.isActive()) {
                    texture = textures.getTexture("Obstacles/Rocket.png");
                } else {
                    texture = textures.getTexture("Obstacles/warning.png");
                }
                batch.draw(texture, rocket.getX(), rocket.getY(), rocket.getWidth(), rocket.getHeight());
            }

            if (obstacle instanceof Lazer lazer) {
                Texture texture;
                if (lazer.getProgressionLevel() == 1) {
                    texture = textures.getTexture("Obstacles/inactiveLazer.png");
                } else if (lazer.getProgressionLevel() == 2) {
                    texture = textures.getTexture("Obstacles/harmlessLazer.png");
                } else {
                    texture = textures.getTexture("Obstacles/activeLazer.png");
                }
                batch.draw(texture, lazer.getX(), lazer.getY(), lazer.getWidth(), lazer.getHeight());
            }
            if (obstacle instanceof Flame flame) {
                Texture texture;
                texture = textures.getTexture("Obstacles/flame.png");

                batch.draw(texture,
                        flame.getX(),
                        flame.getY(),
                        flame.getWidth()/2f,
                        flame.getHeight()/2f,
                        flame.getHeight(),
                        flame.getWidth(),
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

