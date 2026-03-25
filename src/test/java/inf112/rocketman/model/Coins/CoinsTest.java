package inf112.rocketman.model.Coins;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoinsTest {
    private Coin coin;
    private final float START_X = 500f;
    private final float START_Y = 300f;
    private final float WIDTH = 20f;
    private final float HEIGHT = 20f;
    private final float VELOCITY = -100f;

    @BeforeEach
    void setUp() {
        coin = new Coin(START_X, START_Y, WIDTH, HEIGHT, VELOCITY);
    }

    @Test
    public void testCoinMovement() {
        float dt = 1.0f;
        coin.update(dt);

        assertEquals(400f, coin.getX(), "Coin should move left based on velocity and dt");
        assertEquals(400f, coin.getHitbox().x, "Hitbox must follow the coin position");
    }

    @Test
    public void testIsOffScreen() {
        assertFalse(coin.isOfScreen(1000f, 50f), "Should be on screen at start");

        Coin offLeft = new Coin(-100f, 300f, 20f, 20f, -100f);
        assertTrue(offLeft.isOfScreen(1000, 50f), "Should be off screen when x + width < 0");

        Coin belowGround = new Coin(500f, 10f, 20f, 20f, -100f);
        assertTrue(belowGround.isOfScreen(1000f, 50f), "Should be off screen when below GROUND");
    }

    @Test
    public void testRandomCoinFactory() {
        RandomCoinFactory factory = new RandomCoinFactory();
        float worldW = 800f;
        float worldH = 600f;
        float ground = 0f;
        float margin = 50f;
        float backGroundSpeed = -100f;

        Coin spawnedCoin = factory.newCoin(worldW, worldH, ground, margin, backGroundSpeed);

        assertNotNull(spawnedCoin);

        assertEquals(worldW - margin, spawnedCoin.getX(), 0.1f);

        assertTrue(spawnedCoin.getY() >= margin && spawnedCoin.getY() <= worldH -margin);
    }

    @Test
    public void testGetters() {
        assertEquals(WIDTH, coin.getWidth());
        assertEquals(HEIGHT, coin.getHeight());
        assertEquals(START_Y, coin.getY());
    }

    @Test
    public void testIsOffSCreenBoundaries() {
        Coin aboveCeiling = new Coin(500f, 980f, 20f, 20f, -100f);
        assertTrue(aboveCeiling.isOfScreen(1000f, 50f), "Should be off screen when y > worldHeight");

        Coin onEdge = new Coin(30f, 200f, 20f, 20f, -100f);
        assertFalse(onEdge.isOfScreen(1000f, 50f), "Should not be off screen when x is exactly 0");
    }
}
