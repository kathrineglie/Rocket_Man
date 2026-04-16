package inf112.rocketman.model.Obstacles.Lazers;

import java.util.Random;

public class RandomLazerFactory implements LazerFactory {
    Random random = new Random();

    @Override
    public Lazer newLazer(float worldWidth, float worldHeight, float ground, float margin) {
        float lazerHeight = worldHeight / 15;
        float randY = random.nextFloat(ground + margin + lazerHeight, worldHeight - margin - lazerHeight);
        return new Lazer(worldWidth - margin - worldWidth, randY, worldWidth, lazerHeight, 0, 0f);
    }
}