package inf112.rocketman.model.Character;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.PowerUps.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TPowahTest {
    TPowah player;

    @BeforeEach
    void setup() {
        player = new TPowah(100, 30, 50, 50, 30);
    }

    @Test
    public void testGravityPullsPlayerDown() {
        assertFalse(player.hasPowerUp());
        float initialY = 40;
        player.setY(initialY);

        player.update(0.1f, false, 800);

        assertTrue(player.isGoingDown());
        assertTrue(player.getY() < initialY, "Player should have fallen down due to gravity");
    }

    @Test
    public void testHitboxIsSmallerThanBounds() {
        Rectangle bounds = player.getBounds();
        Rectangle hitBox = player.getHitBox();

        assertTrue(hitBox.width < bounds.width, "Hitbox width should be smaller than bounds width");
        assertTrue(hitBox.height < bounds.height, "Hitbox height should be smaller than bounds height");
        assertEquals(bounds.x + 10, hitBox.x, 0.01);
    }

    @Test
    public void testBirdPowerUpFlap() {
        player.setPowerUp(PowerUpType.BIRD);

        player.update(0.1f, true, 800);

        assertEquals(PowerUpType.BIRD, player.getActivePowerUp());

        assertTrue(player.isGoingUp());

        assertTrue(player.getY() > 30);
    }

    @Test
    public void testUpdateBird() {
        player.setPowerUp(PowerUpType.BIRD);
        player.setY(800);
        player.setVy(-1000);
        player.update(0.1f, false, 1000);

        assertEquals(-900, player.getVY());
    }

    @Test
    public void testPlayerCannotGoBelowGround() {

        for (int i = 0; i < 100; i++) {
            player.update(0.1f, false, 800);
        }

        assertEquals(30f, player.getY());
    }

    @Test
    public void testPlayerCannotGoAboveCeiling() {
        float worldHeight = 800f;

        for (int i = 0; i < 50; i ++) {
            player.update(0.1f, true, worldHeight);
        }

        float expectedTop = worldHeight - player.getHeight();
        assertEquals(expectedTop, player.getY(), 0.01f, "Player should stop at the top of the world");
    }

    @Test
    public void testTerminalVelocity() {
        for (int i = 0; i < 100; i++) {
            player.update(0.1f, true, 800);
        }

        float y1 = player.getY();
        player.update(0.1f, true, 800);
        float y2 = player.getY();

        float distancedMoved = y2 - y1;
        assertTrue(distancedMoved <= 700f * 0.1f + 0.01f, "Player velocity exceeded NORMAL_MAX_VY");
    }

    @Test
    public void testVelocityResetsOnGround() {
        player.update(1.0f, false, 800);
        player.update(0.01f, true, 800);

        assertTrue(player.getY() > 30f, "Player should lift off immediately if velocity was reset on ground");
    }

    @Test
    public void testCollisionDetection() {
        Rectangle obstacle = new Rectangle(100, 30, 50, 50);

        assertTrue(player.getHitBox().overlaps(obstacle), "Hitbox should overlap with the obstacle");
    }

    @Test
    public void testXPositionIsConstant() {
        player.update(0.1f, true, 800);
        player.update(0.1f, false, 800);

        assertEquals(100f, player.getX(), "X position should never change during update");
    }

    @Test
    public void testPowerUpSwitching() {
        player.setPowerUp(PowerUpType.BIRD);
        assertEquals(PowerUpType.BIRD, player.getActivePowerUp());

        player.setPowerUp(PowerUpType.NORMAL);
        assertEquals(PowerUpType.NORMAL, player.getActivePowerUp());
    }

    @Test
    public void testPolyHitBoxFollowsPlayer() {
        player.setY(200);

        float polyY = player.getPolyHitBox().getY();
        assertEquals(200f, polyY, "Polygon hitbox should update its position to match the player");
    }

    @Test
    public void testMovementScaleWithDeltaTime() {
        TPowah player1 = new TPowah(100, 400, 50, 50, 50);
        TPowah player2 = new TPowah(100, 400, 50, 50, 50);

        player1.update(0.01f, true, 800);
        player2.update(0.02f, true, 800);

        float dist1 = player1.getY()-400;
        float dist2 = player2.getY()-400;

        assertTrue(dist2 > dist1, "Larger delta time should result in larger movement");
    }

    @Test
    public void testPlayerSizeIsConstantDuringUpdate() {
        player.update(0.1f, true, 800);

        assertEquals(50, player.getWidth(), "Width should not change during update");
        assertEquals(50, player.getHeight(), "Height should not change during update");
    }

    @Test
    public void testUpdateRobotOnGroundNoMovementInput() {
        player.setPowerUp(PowerUpType.ROBOT);

        assertFalse(player.getRobotIsJumping());
        assertFalse(player.getRobotGoingDown());
        assertTrue(player.onGround());

        player.update(0.1f, false, 1000);

        assertFalse(player.getRobotIsJumping());
        assertFalse(player.getRobotGoingDown());

        float expectedY = 30f;
        float expectedVY = 0f;

        assertEquals(expectedY, player.getY());
        assertEquals(expectedVY, player.getVY());
    }

    @Test
    public void testRobotIsJumping() {
        player.setPowerUp(PowerUpType.ROBOT);
        player.update(0.1f, true, 1000);

        assertEquals(120, player.getVY());
        assertTrue(player.getRobotIsJumping());

        player.setY(50);

        assertFalse(player.onGround());
        assertTrue(player.getRobotIsJumping());

        player.update(0.1f, true, 800);
        float expectedVY = 60;
        float actualVY = player.getVY();

        assertEquals(expectedVY, actualVY);
    }

    @Test
    public void testRobotGoingDown() {
        player.setPowerUp(PowerUpType.ROBOT);
        player.update(0.1f, true, 1000);

        player.update(0.1f, false, 1000);

        assertTrue(player.getRobotGoingDown());
        assertTrue(player.getRobotIsJumping());

        assertEquals(40, player.getVY());

        player.update(0.1f, true, 1000);

        assertTrue(player.getRobotGoingDown());
        assertTrue(player.getRobotIsJumping());
        assertFalse(player.onGround());

        float expectedVY = 30;
        float actualVY = player.getVY();

        assertEquals(expectedVY, actualVY);
    }

    @Test
    public void testGravitySuitOnGround() {
        player.setPowerUp(PowerUpType.GRAVITY_SUIT);
        player.setY(400);

        assertFalse(player.isGravityUp());
        player.update(0.1f, false, 1000);

        float expectedCeiling = 950;
        float actualCeiling = player.getCeiling();

        assertEquals(expectedCeiling, actualCeiling);
        assertFalse(player.isGravityUp());

        assertEquals(-150f, player.getVY());
        assertEquals(385f, player.getY());
    }

    @Test
    public void testGravitySuitOnCeiling() {
        player.setPowerUp(PowerUpType.GRAVITY_SUIT);

        assertFalse(player.isGravityUp());
        player.update(0.1f, true, 1000);

        assertTrue(player.isGravityUp());
        assertEquals(0, player.getVY(), 0.001f);

        player.update(0.1f, false, 1000);

        float expectedVY = 150;
        float actualVY = player.getVY();

        assertEquals(expectedVY, actualVY, 0.001f);
    }

    @Test
    public void testGravitySuitWithinBoundsGround() {
        player.setPowerUp(PowerUpType.GRAVITY_SUIT);
        assertTrue(player.hasPowerUp());

        player.setY(20);
        player.update(0.1f, false, 1000);

        assertEquals(0, player.getVY());
        assertEquals(30, player.getY());
    }

    @Test
    public void testGetMovementInput() {
        assertFalse(player.getMovementInput());
    }

    @Test
    public void testGetRobotGravity() {
        assertEquals(-800f, player.getRobotGravity());
    }

    @Test
    public void testGetRobotMinJump() {
        assertEquals(200f, player.getRobotMinJump());
    }

    @Test
    public void testGetRobotBoost() {
        assertEquals(20f, player.getRobotBoost());
    }

    @Test
    public void testOnCeiling() {
        assertFalse(player.onCeiling());
    }

    @Test
    public void testSetX() {
        player.setX(100);
        assertEquals(100, player.getX());
    }
}
