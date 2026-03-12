package inf112.rocketman.model.Coins;

import java.util.Random;

public class RandomCoinFactory implements  CoinFactory {
    Random random = new Random();

    @Override
    public Coin newCoin(float worldWidth, float worldHeight, float margin) {
        float randY = random.nextFloat(0 + margin, worldHeight - margin);

        float coinHeight = worldHeight / 20;
        float coinWidth = worldHeight / 20;
        float vx = -120f;
        return new Coin(worldWidth - margin, randY, coinWidth, coinHeight, vx);
    }
}
