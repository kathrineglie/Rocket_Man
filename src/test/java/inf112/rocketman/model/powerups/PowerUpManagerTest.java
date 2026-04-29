package inf112.rocketman.model.powerups;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.character.TPowah;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PowerUpManagerTest {
    private TPowah player;
    private PowerUpFactory factory;
    private PowerUpManager manager;

    @BeforeEach
    void setUp() {
        player = mock(TPowah.class);
        factory = mock(PowerUpFactory.class);
        manager = new PowerUpManager(factory);
    }

    @Test
    void testUpdateReturnsEarlyWhenPlayerAlreadyHasPowerUp() {
        when(player.getActivePowerUp()).thenReturn(PowerUpType.BIRD);

        manager.update(1.0f, player, 1000f, 800f, 120f, 5f, -250f);

        verify(factory, never()).newPowerUp(anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    void testCheckCollisionReturnsFalseWhenPowerUpDoesNotOverlap() {
        PowerUp powerUp = mock(PowerUp.class);
        when(player.getHitBox()).thenReturn(new Rectangle(0, 0, 50, 50));
        when(powerUp.getHitBox()).thenReturn(new Rectangle(200, 200, 50, 50));

        manager.setPowerUpForTesting(powerUp);

        assertFalse(manager.checkCollision(player));
    }

    @Test
    void testUpdateRemovesPowerUpWhenOffScreen() {
        PowerUp powerUp = mock(PowerUp.class);

        when(player.getActivePowerUp()).thenReturn(PowerUpType.NORMAL);
        when(powerUp.isOfScreen(1000f, 800f)).thenReturn(true);

        manager.setPowerUpForTesting(powerUp);
        manager.update(0.1f, player, 1000f, 800f, 120f, 5f, -350f);

        assertNull(manager.getPowerUp());
    }
}