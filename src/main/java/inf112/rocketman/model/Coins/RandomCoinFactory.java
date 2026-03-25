package inf112.rocketman.model.Coins;

import java.util.Random;

public class RandomCoinFactory implements  CoinFactory {
    Random random = new Random();

    @Override
    public Coin newCoin(float worldWidth, float worldHeight, float margin, float backgroundSpeed) {
        float randY = random.nextFloat(0 + margin, worldHeight - margin);

        float coinWidth = worldHeight / 12;
        float vx = backgroundSpeed;
        return new Coin(worldWidth - margin, randY, coinWidth, coinWidth, vx);
    }
}
