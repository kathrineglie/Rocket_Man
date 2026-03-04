package inf112.rocketman.view.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.rocketman.model.Obstacles.IObstacle;
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
                    texture = textures.getTexture("Obstacles/warning_rocket.png");
                }
                batch.draw(texture, rocket.getX(), rocket.getY(), rocket.getWidth(), rocket.getHeight());
            }
        }
    }
}

