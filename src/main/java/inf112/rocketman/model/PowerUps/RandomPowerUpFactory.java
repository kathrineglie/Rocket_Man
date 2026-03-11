package inf112.rocketman.model.PowerUps;

import java.util.Random;

public class RandomPowerUpFactory implements PowerUpFactory{
    private static final float MIN_Y = 250f;
    private static final float Y_MARGIN = 400f;

    private final Random random = new Random();

    @Override
    public PowerUp newPowerUp(float worldWidth, float worldHeight){
        float x = worldWidth;
        float y = MIN_Y + random.nextFloat() * (worldHeight - Y_MARGIN);

        PowerUpType type = getRandomType();
        return new PowerUp(x,y, type);
    }

    private PowerUpType getRandomType(){
        PowerUpType[] types = PowerUpType.values();
        return types[random.nextInt(types.length)];
    }
}
