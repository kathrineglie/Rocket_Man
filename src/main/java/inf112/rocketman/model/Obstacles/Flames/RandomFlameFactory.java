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
        int minLength = (int) minNum/10;
        int maxLength = (int) minNum/5;
        int length = rand.nextInt(minLength, maxLength);

        // Calculates random angle of the lazer
        int width = (int) minNum/50;
        int randNum = rand.nextInt(4);
        float randAngle = angles[randNum];

        float x = worldWidth + length; // Sets starting point for the object outside the board.
        float y = MathUtils.random(margin + length, worldHeight - margin - length); // Makes sure the object is not outside the board on the y-axis

        return new Flame(x, y, width, length, vx, 0, randAngle);
    }
}
