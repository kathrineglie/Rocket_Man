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
        TPowah player = new TPowah(100, 500, 50, 50);
        float initialY = player.getY();

        player.update(0.1f, false, 800);

        assertTrue(player.getY() < initialY, "Player should have fallen down due to gravity");
    }

    @Test
    public void testHitboxIsSmallerThanBounds() {
        TPowah player = new TPowah(100, 100, 50, 50);
        Rectangle bounds = player.getBounds();
        Rectangle hitBox = player.getHitBox();

        assertTrue(hitBox.width < bounds.width, "Hitbox width should be smaller than bounds width");
        assertTrue(hitBox.height < bounds.height, "Hitbox height should be smaller than bounds height");
        assertEquals(bounds.x + 10, hitBox.x, 0.01);
    }

    @Test
    public void testBirdPowerUpFlap() {
        TPowah player = new TPowah(100, 300, 50, 50);
        player.setPowerUp(PowerUpType.BIRD);

        player.update(0.1f, true, 800);

        assertEquals(PowerUpType.BIRD, player.getActivePowerUp());

        assertTrue(player.getY() > 300);
    }

    @Test
    public void testPlayerCannotGoBelowGround() {
        TPowah player = new TPowah(100, 125, 50, 50);

        for (int i = 0; i < 100; i++) {
            player.update(0.1f, false, 800);
        }

        assertEquals(120f, player.getY(), 0.01f, "Player should stop at GROUND_Y (120");
    }
}
