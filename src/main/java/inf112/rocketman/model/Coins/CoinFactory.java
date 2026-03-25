package inf112.rocketman.model.Coins;

public interface CoinFactory {
    Coin newCoin(float worldWidth, float worldHeight, float margin, float backgroundSpeed);
}
