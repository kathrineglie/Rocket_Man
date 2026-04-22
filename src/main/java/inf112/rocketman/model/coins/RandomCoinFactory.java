package inf112.rocketman.model.coins;

import java.util.Random;

/**
 * A coin factory that creates coins with random vertical positions.
 */
public class RandomCoinFactory implements  CoinFactory {
    Random random = new Random();

    @Override
    public Coin newCoin(float worldWidth, float worldHeight,float ground, float margin, float backgroundSpeed) {
        float coinWidth = worldHeight / 12;
        float randY = random.nextFloat(margin + ground, worldHeight - margin - coinWidth);
        float vx = backgroundSpeed;
        return new Coin(worldWidth - margin, randY, coinWidth, coinWidth, vx);
    }
}
