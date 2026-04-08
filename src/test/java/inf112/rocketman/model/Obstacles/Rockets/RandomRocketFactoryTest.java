package inf112.rocketman.model.Obstacles.Rockets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomRocketFactoryTest {
    private RandomRocketFactory factory;

    @BeforeEach
    void setUp() {
        factory = new RandomRocketFactory();
    }

    @Test
    void newRocketReturnsRocketWithExpectedSizeAndVelocity() {
        float worldWidth = 1000f;
        float worldHeight = 600f;
        float ground = 50f;
        float margin = 20f;
        float vx = -8f;

        Rocket rocket = factory.newRocket(worldWidth, worldHeight, ground, margin, vx);

        assertNotNull(rocket);

        float expectedHeight = worldHeight / 15f;
        float expectedWidth = worldWidth / 10f;
        float expectedX = worldWidth - margin - expectedWidth;

        assertEquals(expectedX, rocket.getX(), 0.0001f);
        assertEquals(expectedWidth, rocket.getWidth(), 0.0001f);
        assertEquals(expectedHeight, rocket.getHeight(), 0.0001f);
        assertEquals(vx, rocket.getVX(), 0.0001);

    }

    @Test
    void newRocketPlacesRocketWithinValidYRange() {
        float worldWidth = 1000f;
        float worldHeight = 600f;
        float ground = 50f;
        float margin = 20f;
        float vx = -8f;

        Rocket rocket = factory.newRocket(worldWidth, worldHeight, ground, margin, vx);

        float rocketHeight = worldHeight / 15f;
        float minY = margin - rocketHeight;
        float maxY = worldHeight - margin- rocketHeight;

        assertTrue(rocket.getY() >= minY);
        assertTrue(rocket.getY() <= maxY);
    }

    @Test
    void newRocketReturnsRocket() {
        Rocket rocket = factory.newRocket(1000f, 600f, 50f, 20f, -8f);
        assertNotNull(rocket);
    }

    @Test
    void newRocketAlwaysPlacesRocketWithinValidRange() {
        float worldWidth = 1000f;
        float worldHeight = 600f;
        float ground = 50f;
        float margin = 20f;
        float vx = -8f;

        float rocketHeight = worldHeight / 15f;
        float minY = margin - rocketHeight;
        float maxY = worldHeight - margin- rocketHeight;

        for (int i = 0; i < 20; i++) {
            Rocket rocket = factory.newRocket(worldWidth, worldHeight, ground, margin, vx);
            assertTrue(rocket.getY() >= minY);
            assertTrue(rocket.getY() <= maxY);
        }

    }
}
