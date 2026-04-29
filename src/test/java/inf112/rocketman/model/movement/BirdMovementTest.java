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
        player.update(0.1f, true, 800);

        assertEquals(PowerUpType.BIRD, player.getActivePowerUp());
        assertTrue(player.isGoingUp());
        assertTrue(player.getY() > 30);
    }

    @Test
    void testUpdateBird() {
        player.setY(800);
        player.setVy(-1000);
        player.update(0.1f, false, 1000);

        assertEquals(-900, player.getVY());
    }
}