package inf112.rocketman.model.coins;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinsTest {
    private Coin coin;

    @BeforeEach
    void setUp() {
        final float startX = 500f;
        final float startY = 300f;
        final float width = 20f;
        final float height = 20f;
        final float velocity = -100f;
        coin = new Coin(startX, startY, width, height, velocity);
    }

    @Test
    void testCoinMovement() {
        float dt = 1.0f;
        coin.update(dt);

        assertEquals(400f, coin.getX(), "Coin should move left based on velocity and dt");
        assertEquals(400f, coin.getHitbox().x, "Hitbox must follow the coin position");
    }

    @Test
    void testIsOffScreen() {
        assertFalse(coin.isOfScreen(1000f, 50f), "Should be on screen at start");

        Coin offLeft = new Coin(-100f, 300f, 20f, 20f, -100f);
        assertTrue(offLeft.isOfScreen(1000, 50f), "Should be off screen when x + width < 0");

        Coin belowGround = new Coin(500f, 10f, 20f, 20f, -100f);
        assertTrue(belowGround.isOfScreen(1000f, 50f), "Should be off screen when below GROUND");
    }

    @Test
    void testRandomCoinFactory() {
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
    void testGetters() {
        final float startY = 300f;
        final float width = 20f;
        final float height = 20f;
        assertEquals(width, coin.getWidth());
        assertEquals(height, coin.getHeight());
        assertEquals(startY, coin.getY());
    }

    @Test
    void testIsOffScreenBoundaries() {
        Coin aboveCeiling = new Coin(500f, 980f, 20f, 20f, -100f);
        assertTrue(aboveCeiling.isOfScreen(1000f, 50f), "Should be off screen when y > worldHeight");

        Coin onEdge = new Coin(30f, 200f, 20f, 20f, -100f);
        assertFalse(onEdge.isOfScreen(1000f, 50f), "Should not be off screen when x is exactly 0");
    }
}
