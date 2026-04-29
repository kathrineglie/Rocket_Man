package inf112.rocketman.model.powerups;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerUpTest {
    @Test
    void testNewPowerUpReturnsPowerUp() {
        RandomPowerUpFactory factory = new RandomPowerUpFactory();
        PowerUp powerUp = factory.newPowerUp(200, 100, 10, 10, 1f);
        assertNotNull(powerUp);
    }

    @Test
    void testNewPowerUpXPosition() {
        RandomPowerUpFactory factory = new RandomPowerUpFactory();
        float worldHeight = 100;
        float worldWidth = 200;
        PowerUp powerUp = factory.newPowerUp(worldWidth, worldHeight, 10, 10, 1f);
        float expectedSize = worldHeight / 12;
        float expectedX = expectedSize + worldWidth;
        assertEquals(expectedX, powerUp.getX());
    }

    @Test
    void testPowerUpHasCorrectSize() {
        RandomPowerUpFactory factory = new RandomPowerUpFactory();
        float worldHeight = 100;
        PowerUp powerUp = factory.newPowerUp(200, worldHeight, 10, 10, 1f);
        float expectedSize = worldHeight / 12;
        assertEquals(expectedSize, powerUp.getHeight());
        assertEquals(expectedSize, powerUp.getWidth());
    }

    @Test
    void testNewPowerUpYIsWithinBounds() {
        RandomPowerUpFactory factory = new RandomPowerUpFactory();
        float worldWidth = 200;
        float worldHeight = 100;
        float ground = 10;
        float margin = 10;
        PowerUp powerUp = factory.newPowerUp(worldWidth, worldHeight, ground, margin, 1f);
        float size = worldHeight / 12;
        float minY = margin + ground;
        float maxY = worldHeight - margin - size;
        assertTrue(powerUp.getY() >= minY);
        assertTrue(powerUp.getY() <= maxY);
    }

    @Test
    void testPowerUpTypeIsNotNormal() {
        RandomPowerUpFactory factory = new RandomPowerUpFactory();
        for (int i = 0; i < 100; i++) {
            PowerUp powerUp = factory.newPowerUp(200, 100, 10, 10, 1f);
            assertNotEquals(PowerUpType.NORMAL, powerUp.getType());
        }
    }

    @Test
    void testNewPowerUpHasCorrectSpeed() {
        RandomPowerUpFactory factory = new RandomPowerUpFactory();
        PowerUp powerUp = factory.newPowerUp(200, 100, 10, 10, 1f);
        assertEquals(1f, powerUp.getVX());
    }
}
