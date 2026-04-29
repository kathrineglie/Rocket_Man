package inf112.rocketman.model.character;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.powerups.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TPowahTest {
    private TPowah player;

    @BeforeEach
    void setup() {
        player = new TPowah(100, 30, 50, 50, 30);
    }

    @Test
    void testHitboxIsSmallerThanBounds() {
        Rectangle bounds = player.getBounds();
        Rectangle hitBox = player.getHitBox();

        assertTrue(hitBox.width < bounds.width, "Hitbox width should be smaller than bounds width");
        assertTrue(hitBox.height < bounds.height, "Hitbox height should be smaller than bounds height");
        assertEquals(bounds.x + 10, hitBox.x, 0.01);
    }

    @Test
    void testPlayerCannotGoBelowGround() {

        for (int i = 0; i < 100; i++) {
            player.update(0.1f, false, 800);
        }

        assertEquals(30f, player.getY());
    }

    @Test
    void testPlayerCannotGoAboveCeiling() {
        float worldHeight = 800f;

        for (int i = 0; i < 50; i ++) {
            player.update(0.1f, true, worldHeight);
        }

        float expectedTop = worldHeight - player.getHeight();
        assertEquals(expectedTop, player.getY(), 0.01f, "Player should stop at the top of the world");
    }

    @Test
    void testVelocityResetsOnGround() {
        player.update(1.0f, false, 800);
        player.update(0.01f, true, 800);

        assertTrue(player.getY() > 30f, "Player should lift off immediately if velocity was reset on ground");
    }

    @Test
    void testCollisionDetection() {
        Rectangle obstacle = new Rectangle(100, 30, 50, 50);

        assertTrue(player.getHitBox().overlaps(obstacle), "Hitbox should overlap with the obstacle");
    }

    @Test
    void testPowerUpSwitching() {
        player.setPowerUp(PowerUpType.BIRD);
        assertEquals(PowerUpType.BIRD, player.getActivePowerUp());

        player.setPowerUp(PowerUpType.NORMAL);
        assertEquals(PowerUpType.NORMAL, player.getActivePowerUp());
    }

    @Test
    void testPolyHitBoxFollowsPlayer() {
        player.setY(200);

        float polyY = player.getPolyHitBox().getY();
        assertEquals(200f, polyY, "Polygon hitbox should update its position to match the player");
    }

    @Test
    void testPlayerSizeIsConstantDuringUpdate() {
        player.update(0.1f, true, 800);

        assertEquals(50, player.getWidth(), "Width should not change during update");
        assertEquals(50, player.getHeight(), "Height should not change during update");
    }

    @Test
    void testGetMovementInput() {
        assertFalse(player.getMovementInput());
    }

    @Test
    void testSetX() {
        player.setX(100);
        assertEquals(100, player.getX());
    }

    @Test
    void testOnCeiling() {
        float expected = 950;

        player.update(0f, true, 1000);
        player.setY(expected);

        float actual = player.getY();

        assertEquals(expected, actual, 0.001f);
        assertTrue(player.onCeiling(1000));

    }
}
