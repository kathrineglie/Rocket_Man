package inf112.rocketman.model.coins;

import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

public class CoinManagerTest {

    @Test
    void testCoinCollisionIncreasesCoinCount() throws Exception {
        CoinManager coinManager = new CoinManager((worldWidth, worldHeight, ground, margin, speed) -> null);

        Rectangle playerHitbox = new Rectangle(100, 100, 50, 50);

        Constructor<Coin> constructor = Coin.class.getDeclaredConstructor(
                float.class, float.class, float.class, float.class, float.class
        );
        constructor.setAccessible(true);

        Coin coin = constructor.newInstance(100f, 100f, 20f, 20f, 0f);

        coinManager.addCoinForTesting(coin);
        coinManager.update(0f, playerHitbox, 1000, 800, 120f, 5f, -350f);

        assertEquals(1, coinManager.getCoinCount());
        assertTrue(coinManager.didCollectCoinThisFrame());
    }

    @Test
    void testCoinCollisionReturnsFalseWhenNoOverlap() throws Exception {
        CoinManager coinManager = new CoinManager((worldWidth, worldHeight, ground, margin, speed) -> null);

        Rectangle playerHitbox = new Rectangle(100, 100, 50, 50);

        Constructor<Coin> constructor = Coin.class.getDeclaredConstructor(
                float.class, float.class, float.class, float.class, float.class
        );
        constructor.setAccessible(true);

        Coin coin = constructor.newInstance(900f, 900f, 20f, 20f, 0f);

        coinManager.addCoinForTesting(coin);
        coinManager.update(0f, playerHitbox, 1000, 800, 120f, 5f, -350f);

        assertEquals(0, coinManager.getCoinCount());
        assertFalse(coinManager.didCollectCoinThisFrame());
    }
}
