package inf112.rocketman.model.powerups;


import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Velocity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates power-ups with random types and spawn positions.
 */
public class RandomPowerUpFactory implements PowerUpFactory{

    private final Random random = new Random();

    @Override
    public PowerUp newPowerUp(float worldWidth, float worldHeight, float ground,  float margin, float bgSpeed){

        float size = worldHeight / 12;
        float x = worldWidth + size;
        float y = random.nextFloat(margin + ground, worldHeight - margin - size);

        PowerUpType type = getRandomType();
        Rectangle bounds = new Rectangle(x, y, size, size);

        return new PowerUp(bounds, new Velocity(bgSpeed, 0), ground, type);
    }

    private PowerUpType getRandomType(){
        PowerUpType[] types = PowerUpType.values();

        List<PowerUpType> availableTypes = new ArrayList<>();

        for (PowerUpType type : types) {
            if (type != PowerUpType.NORMAL) {
                availableTypes.add(type);
            }
        }

        return availableTypes.get(random.nextInt(availableTypes.size()));

    }
}
