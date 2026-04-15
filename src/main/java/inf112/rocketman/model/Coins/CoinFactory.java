package inf112.rocketman.model.Coins;

/**
 * Factory interface for creating coin objects.
 */
public interface CoinFactory {

    /**
     * Creates a new coin with properties based on the current world settings.
     *
     * @param worldWidth the width of the game world
     * @param worldHeight the height of the game world
     * @param ground the ground height or ground position in the world
     * @param margin the horizontal margin used for spawning
     * @param backgroundSpeed the movement speed of the world/background
     * @return a newly created coin
     */
    Coin newCoin(float worldWidth, float worldHeight, float ground, float margin, float backgroundSpeed);
}
