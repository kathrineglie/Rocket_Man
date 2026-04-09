package inf112.rocketman.model.PowerUps;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPowerUpFactory implements PowerUpFactory{

    private final Random random = new Random();

    @Override
    public PowerUp newPowerUp(float worldWidth, float worldHeight, float ground,  float margin, float bgSpeed){

        float size = worldHeight / 12;
        float x = worldWidth + size;
        float y = random.nextFloat(margin + ground, worldHeight - margin - size);

        PowerUpType type = getRandomType();
        return new PowerUp(x,y, size, size, bgSpeed ,type);
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
