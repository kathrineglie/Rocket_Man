package inf112.rocketman.model.Obstacles.Flames;

import com.badlogic.gdx.math.MathUtils;

import java.util.Random;

public class RandomFlameFactory implements FlameFactory {
    private final Random rand = new Random();
    private float[] angles = {0f, 90f, 45f, 135f};

    @Override
    public Flame newFlame(float worldWidth, float worldHeight, float margin, float vx) {
        // Calculates random length of the lazer
        float minNum = Math.min(worldWidth, worldHeight);
        int minLength = (int) minNum/7;
        int maxLength = (int) minNum/3;
        int length = rand.nextInt(minLength, maxLength);

        // Calculates random angle of the lazer
        int width = (int) minNum/20;
        float randAngle = rand.nextInt(361);

        float halfLength = length/2f;
        float halfWidth = width/2f;

        float radius = (float) Math.sqrt(halfLength * halfLength + halfWidth * halfWidth);

        float centerY = MathUtils.random(margin + radius, worldHeight - margin - radius);
        float centerX = worldWidth + margin + radius;

        return new Flame(centerX, centerY, width, length, vx, 0, randAngle);
    }
}
