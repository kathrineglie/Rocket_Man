package inf112.rocketman.model.movement;

import inf112.rocketman.model.character.TPowah;
import inf112.rocketman.model.powerups.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GravitySuitMovementTest {
    private TPowah player;
    private GravitySuitMovement gravity;

    @BeforeEach
    void setup() {
        player = new TPowah(100, 30, 50, 50, 30);
        player.setPowerUp(PowerUpType.GRAVITY_SUIT);
        gravity = (GravitySuitMovement) player.getMovementBehavior();
    }


    @Test
    public void testGravitySuitOnGround() {
        assertFalse(gravity.isGravityUp());

        player.setY(400);
        player.update(0.1f, false, 1000);

        assertEquals(-150f, player.getVY());
        assertEquals(385f, player.getY());
    }

    @Test
    public void testGravitySuitOnCeiling() {
        assertFalse(gravity.isGravityUp());
        player.update(0.1f, true, 1000);

        assertTrue(gravity.isGravityUp());
        assertEquals(0, player.getVY(), 0.001f);

        player.update(0.1f, false, 1000);

        float expectedVY = 150;
        float actualVY = player.getVY();

        assertEquals(expectedVY, actualVY, 0.001f);
    }

    @Test
    public void testGravitySuitWithinBoundsGround() {
        assertTrue(player.hasPowerUp());

        player.setY(20);
        player.update(0.1f, false, 1000);

        assertEquals(0, player.getVY());
        assertEquals(30, player.getY());
    }
}