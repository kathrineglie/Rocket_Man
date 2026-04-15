package inf112.rocketman.model.movement;

import inf112.rocketman.model.Character.TPowah;
import inf112.rocketman.model.PowerUps.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotMovementTest {
    private TPowah player;

    @BeforeEach
    void setup() {
        player = new TPowah(100, 30, 50, 50, 30);
    }


    @Test
    public void testUpdateRobotOnGroundNoMovementInput() {
        player.setPowerUp(PowerUpType.ROBOT);

//        assertFalse(player.getRobotIsJumping());
//        assertFalse(player.getRobotGoingDown());

        assertTrue(player.onGround());

        player.update(0.1f, false, 1000);

//        assertFalse(player.getRobotIsJumping());
//        assertFalse(player.getRobotGoingDown());

        float expectedY = 30f;
        float expectedVY = 0f;

        assertEquals(expectedY, player.getY());
        assertEquals(expectedVY, player.getVY());
    }
}