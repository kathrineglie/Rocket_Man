package inf112.rocketman.model.powerups;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.character.TPowah;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PowerUpManagerTest {

    @Test
    void testPlayerCollectsPowerUpOnCollision() {
        PowerUpManager powerUpManager = new PowerUpManager(
                (worldWidth, worldHeight, ground, margin, speed) -> null
        );

        TPowah player = new TPowah(100, 100, 50, 50, 120f);
        player.setPowerUp(PowerUpType.NORMAL);

        Rectangle playerHitbox = player.getHitBox();

        PowerUp overlappingPowerUp = new PowerUp(
                playerHitbox.x,
                playerHitbox.y,
                30f,
                30f,
                0f,
                PowerUpType.BIRD
        );

        powerUpManager.setPowerUpForTesting(overlappingPowerUp);

        boolean collided = powerUpManager.checkCollision(player);

        assertTrue(collided);
        assertEquals(PowerUpType.BIRD, player.getActivePowerUp());
        assertTrue(powerUpManager.didCollectPowerUpThisFrame());
        assertNull(powerUpManager.getPowerUp());
    }
}