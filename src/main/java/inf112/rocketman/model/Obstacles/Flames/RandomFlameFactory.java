package inf112.rocketman.model.Obstacles.Flames;

import com.badlogic.gdx.math.MathUtils;

import java.util.Random;

public class RandomFlameFactory implements FlameFactory {
    private final Random rand = new Random();

    @Override
    public Flame newFlame(float worldWidth, float worldHeight, float ground, float margin, float vx) {
        // Calculates random length of the lazer
        float minNum = Math.min(worldWidth, worldHeight);

        float length = rand.nextInt((int) minNum/7, (int) minNum/3);
        float thickness = minNum / 20f;

        float randAngle = rand.nextFloat(360f);

        float centerX = worldWidth + margin + length; // Spawns the flame outside of the screen
        float centerY = MathUtils.random(margin + length, worldHeight - margin - length);

        return new Flame(centerX, centerY, thickness, length, vx, 0, randAngle);
    }
}
