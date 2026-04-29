package inf112.rocketman.model.obstacles;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Velocity;
import inf112.rocketman.model.WorldDimensions;
import inf112.rocketman.model.obstacles.flames.Flame;
import inf112.rocketman.model.obstacles.lazers.Lazer;
import inf112.rocketman.model.obstacles.rockets.Rocket;

import java.util.Random;

public class RandomObstacleFactory implements IRandomObstacleFactory {
    private final Random random = new Random();

    @Override
    public Obstacle newObstacle(ObstacleType type, WorldDimensions dimensions, float ground, float margin, float vx) {
        return switch (type) {
            case ObstacleType.ROCKET ->
                    newRocket(dimensions.worldWidth(), dimensions.worldHeight(), ground, margin, vx);

            case ObstacleType.LAZER ->
                    newLazer(dimensions.worldWidth(), dimensions.worldHeight(), ground, margin);

            case ObstacleType.FLAME ->
                    newFlame(dimensions.worldWidth(), dimensions.worldHeight(), ground, margin, vx);
        };
    }

    private Rocket newRocket (float worldWidth, float worldHeight, float ground, float margin, float vx) {
        float rocketHeight = worldHeight / 15;
        float rocketWidth = worldWidth / 10;
        float randY = random.nextFloat (margin + ground + rocketHeight/2, worldHeight - margin - rocketHeight/2);

        Rectangle bounds = new Rectangle(
                worldWidth - margin - rocketWidth,
                randY - rocketHeight,
                rocketWidth,
                rocketHeight
        );

        return new Rocket(bounds, new Velocity(vx, 0f), ground);
    }

    private Lazer newLazer(float worldWidth, float worldHeight, float ground, float margin) {
        float lazerHeight = worldHeight / 15;
        float randY = random.nextFloat(ground + margin + lazerHeight/2, worldHeight - margin - lazerHeight/2);

        Rectangle bounds = new Rectangle(
                margin,
                randY,
                worldWidth - 2 * margin,
                lazerHeight
        );

        return new Lazer(bounds, new Velocity(0f, 0f), ground);
    }

    private Flame newFlame(float worldWidth, float worldHeight, float ground, float margin, float vx) {
        float minNum = Math.min(worldWidth, worldHeight);
        float length = random.nextInt((int) minNum/7, (int) minNum/3);
        float thickness = minNum / 20f;

        float randAngle = random.nextFloat(360f);
        float centerX = worldWidth + margin + length; // Spawns the flame outside of the screen so it float naturally onto the screen
        float centerY = MathUtils.random(margin + length, worldHeight - margin - length);

        Rectangle bounds = new Rectangle(
                centerX,
                centerY,
                thickness,
                length
        );

        return new Flame(bounds, new Velocity(vx, 0), ground, randAngle);
    }
}
