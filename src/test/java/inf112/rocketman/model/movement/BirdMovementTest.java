package inf112.rocketman.model.movement;

import inf112.rocketman.model.character.TPowah;
import inf112.rocketman.model.powerups.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BirdMovementTest {
    TPowah player;

    @BeforeEach
    void setup() {
        player = new TPowah(100, 30, 50, 50, 30);
        player.setPowerUp(PowerUpType.BIRD);
    }


    @Test
    void testBirdPowerUpFlap() {
        float worldHeight = 800f;
        float margin = 50f;

        player.update(0f, false, worldHeight, margin);
        float initialY = player.getY();

        player.update(0.1f, true, worldHeight, margin);

        assertEquals(PowerUpType.BIRD, player.getActivePowerUp());
        assertTrue(player.isGoingUp());
        assertTrue(player.getY() > initialY);
    }

    @Test
    void testUpdateBird() {
        player.setY(800);
        player.setVy(-1000);
        player.update(0.1f, false, 1000, 50);

        assertEquals(-900, player.getVY());
    }
}