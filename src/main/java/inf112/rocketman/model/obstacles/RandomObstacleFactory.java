package inf112.rocketman.model.obstacles;

import com.badlogic.gdx.math.MathUtils;
import inf112.rocketman.model.obstacles.flames.Flame;
import inf112.rocketman.model.obstacles.lazers.Lazer;
import inf112.rocketman.model.obstacles.rockets.Rocket;

import java.util.Random;

public class RandomObstacleFactory implements IRandomObstacleFactory {
    private final Random random = new Random();

    @Override
    public Obstacle newObstacle(ObstacleType type, float worldWidth, float worldHeight, float ground, float margin, float vx) {
        if (type == ObstacleType.ROCKET) {
            float randY = random.nextFloat(0 + margin, worldHeight - margin);
            float rocketHeight = worldHeight / 15;
            float rocketWidth = worldWidth / 10;
            return new Rocket(worldWidth - margin - rocketWidth, randY - rocketHeight, rocketWidth, rocketHeight, vx, 0f);
        } else if (type == ObstacleType.LAZER) {
            float lazerHeight = worldHeight / 15;
            float randY = random.nextFloat(ground + margin + lazerHeight, worldHeight - margin - lazerHeight);
            return new Lazer(worldWidth - margin - worldWidth, randY, worldWidth, lazerHeight, 0, 0f);
        } else {
            // Calculates random length of the lazer
            float minNum = Math.min(worldWidth, worldHeight);
            float length = random.nextInt((int) minNum/7, (int) minNum/3);
            float thickness = minNum / 20f;
            float randAngle = random.nextFloat(360f);
            float centerX = worldWidth + margin + length; // Spawns the flame outside of the screen
            float centerY = MathUtils.random(margin + length, worldHeight - margin - length);
            return new Flame(centerX, centerY, thickness, length, vx, 0, randAngle);
        }
    }
}
