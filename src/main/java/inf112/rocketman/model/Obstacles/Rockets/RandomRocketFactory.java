package inf112.rocketman.model.Obstacles.Rockets;

import java.util.Random;

public class RandomRocketFactory implements RocketFactory {

    Random random = new Random();

    @Override
    public Rocket newRocket(float worldWidth, float worldHeight, float margin) {
        float randY = random.nextFloat(0 + margin, worldHeight - margin);
        float rocketHeight = worldHeight / 10;
        float rocketWidth = worldWidth / 15;
        return new Rocket(worldWidth - margin - rocketWidth, randY - rocketHeight, rocketWidth, rocketHeight, -240f, 0f);
    }
}
