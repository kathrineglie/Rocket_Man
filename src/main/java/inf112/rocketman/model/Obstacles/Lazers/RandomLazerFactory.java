package inf112.rocketman.model.Obstacles.Lazers;

import java.util.Random;

public class RandomLazerFactory implements LazerFactory {
    Random random = new Random();

    @Override
    public Lazer newLazer(float worldWidth, float worldHeight, float margin) {
        float randY = random.nextFloat(0 + margin, worldHeight - margin);
        float lazerHeight = worldHeight / 15;
        return new Lazer(worldWidth - margin - worldWidth, randY, worldWidth, lazerHeight, 0, 0f);
    }
}