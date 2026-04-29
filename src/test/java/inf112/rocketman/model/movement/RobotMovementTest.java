package inf112.rocketman.model.movement;

import inf112.rocketman.model.character.TPowah;
import inf112.rocketman.model.powerups.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotMovementTest {
    private TPowah player;
    private RobotMovement robot;

    @BeforeEach
    void setup() {
        player = new TPowah(100, 30, 50, 50, 30);
        player.setPowerUp(PowerUpType.ROBOT);
        robot = (RobotMovement) player.getMovementBehavior();
    }

    @Test
    void testUpdateRobotOnGroundNoMovementInput() {

        assertFalse(robot.getRobotIsJumping());
        assertFalse(robot.getRobotIsGoingDown());

        assertTrue(player.onGround());

        player.update(0.1f, false, 1000);

        assertFalse(robot.getRobotIsJumping());
        assertFalse(robot.getRobotIsGoingDown());

        float expectedY = 30f;
        float expectedVY = 0f;

        assertEquals(expectedY, player.getY());
        assertEquals(expectedVY, player.getVY());
    }

    @Test
    void testRobotIsJumping() {
        player.update(0.1f, true, 1000);

        assertEquals(120, player.getVY());
        assertTrue(robot.getRobotIsJumping());

        player.setY(50);

        assertFalse(player.onGround());
        assertTrue(robot.getRobotIsJumping());

        player.update(0.1f, true, 800);
        float expectedVY = 60;
        float actualVY = player.getVY();

        assertEquals(expectedVY, actualVY);
    }

    @Test
    void testRobotGoingDown() {
        player.update(0.1f, true, 1000);

        player.update(0.1f, false, 1000);

        assertTrue(robot.getRobotIsGoingDown());
        assertTrue(robot.getRobotIsJumping());

        assertEquals(40, player.getVY());

        player.update(0.1f, true, 1000);

        assertTrue(robot.getRobotIsGoingDown());
        assertTrue(robot.getRobotIsJumping());
        assertFalse(player.onGround());

        float expectedVY = 30;
        float actualVY = player.getVY();

        assertEquals(expectedVY, actualVY);
    }
}