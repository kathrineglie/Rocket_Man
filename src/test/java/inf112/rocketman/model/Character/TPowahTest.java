package inf112.rocketman.model.Character;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.PowerUps.PowerUp;
import inf112.rocketman.model.PowerUps.PowerUpType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TPowahTest {

    @Test
    public void testGravityPullsPlayerDown() {
        TPowah player = new TPowah(100, 500, 50, 50, 50);
        float initialY = player.getY();

        player.update(0.1f, false, 800);

        assertTrue(player.getY() < initialY, "Player should have fallen down due to gravity");
    }

    @Test
    public void testHitboxIsSmallerThanBounds() {
        TPowah player = new TPowah(100, 100, 50, 50, 50);
        Rectangle bounds = player.getBounds();
        Rectangle hitBox = player.getHitBox();

        assertTrue(hitBox.width < bounds.width, "Hitbox width should be smaller than bounds width");
        assertTrue(hitBox.height < bounds.height, "Hitbox height should be smaller than bounds height");
        assertEquals(bounds.x + 10, hitBox.x, 0.01);
    }

    @Test
    public void testBirdPowerUpFlap() {
        TPowah player = new TPowah(100, 300, 50, 50, 50);
        player.setPowerUp(PowerUpType.BIRD);

        player.update(0.1f, true, 800);

        assertEquals(PowerUpType.BIRD, player.getActivePowerUp());

        assertTrue(player.getY() > 300);
    }

    @Test
    public void testPlayerCannotGoBelowGround() {
        TPowah player = new TPowah(100, 125, 50, 50, 50);

        for (int i = 0; i < 100; i++) {
            player.update(0.1f, false, 800);
        }

        assertEquals(50f, player.getY(), 0.01f, "Player should stop at GROUND_Y (120");
    }

    @Test
    public void testPlayerCannotGoAboveCeiling() {
        float worldHeight = 800f;
        TPowah player = new TPowah(100, 750, 50, 50, 50);

        for (int i = 0; i < 50; i ++) {
            player.update(0.1f, true, worldHeight);
        }

        float expectedTop = worldHeight - player.getHeight();
        assertEquals(expectedTop, player.getY(), 0.01f, "Player should stop at the top of the world");
    }

    @Test
    public void testTerminalVelocity() {
        TPowah player = new TPowah(100, 500, 50, 50, 50);

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
        TPowah player = new TPowah(100, 120, 50, 50, 50);

        player.update(1.0f, false, 800);

        player.update(0.01f, true, 800);

        assertTrue(player.getY() > 50f, "Player should lift off immediately if velocity was reset on ground");
    }

    @Test
    public void testCollisionDetection() {
        TPowah player = new TPowah(100, 100, 50, 50, 50);
        Rectangle obstacle = new Rectangle(110, 110, 50, 50);

        assertTrue(player.getHitBox().overlaps(obstacle), "Hitbox should overlap with the obstacle");
    }

    @Test
    public void testXPositionIsConstant() {
        TPowah player = new TPowah(100, 400, 50, 50, 50);
        player.update(0.1f, true, 800);
        player.update(0.1f, false, 800);

        assertEquals(100f, player.getX(), "X position should never change during update");
    }

    @Test
    public void testPowerUpSwitching() {
        TPowah player = new TPowah(100, 400, 50, 50, 50);

        player.setPowerUp(PowerUpType.BIRD);
        assertEquals(PowerUpType.BIRD, player.getActivePowerUp());

        player.setPowerUp(PowerUpType.NORMAL);
        assertEquals(PowerUpType.NORMAL, player.getActivePowerUp());
    }

    @Test
    public void testPolyHitBoxFollowsPlayer() {
        TPowah player = new TPowah(100, 100, 50, 50, 50);
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
        float width = 50f;
        float height = 60f;
        TPowah player = new TPowah(100, 400, width, height, 50);

        player.update(0.1f, true, 800);

        assertEquals(width, player.getWidth(), "Width should not change during update");
        assertEquals(height, player.getHeight(), "Height should not change during update");
    }
}
