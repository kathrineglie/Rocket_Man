package inf112.rocketman.model.powerups;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.WorldDimensions;
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

        manager.update(1.0f, player, new WorldDimensions(1000f, 800f), 120f, 5f, -250f);

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
        when(powerUp.isOfScreen(new WorldDimensions(1000f, 800f))).thenReturn(true);

        manager.setPowerUpForTesting(powerUp);
        manager.update(0.1f, player, new WorldDimensions(1000f, 800f), 120f, 5f, -350f);

        assertNull(manager.getPowerUp());
    }

    @Test
    void testUpdateSpawnsPowerUpWhenTimerIsFinished() {
        PowerUp powerUp = mock(PowerUp.class);
        WorldDimensions dimensions = new WorldDimensions(1000f, 800f);

        when(player.getActivePowerUp()).thenReturn(PowerUpType.NORMAL);
        when(factory.newPowerUp(anyFloat(), anyFloat(), anyFloat(), anyFloat(), anyFloat()))
                .thenReturn(powerUp);
        when(powerUp.isOfScreen(dimensions)).thenReturn(false);

        manager.update(1000f, player, dimensions, 120f, 5f, -350f);

        assertEquals(powerUp, manager.getPowerUp());
        verify(factory).newPowerUp(1000f, 800f, 120f, 5f, -350f);
        verify(powerUp).update(1000f);
        verify(powerUp).setVX(-350f);
    }

    @Test
    void testCheckCollisionCollectsPowerUpWhenPlayerOverlaps() {
        PowerUp powerUp = mock(PowerUp.class);

        when(player.getHitBox()).thenReturn(new Rectangle(0, 0, 50, 50));
        when(powerUp.getHitBox()).thenReturn(new Rectangle(25, 25, 50, 50));
        when(powerUp.getType()).thenReturn(PowerUpType.ROBOT);

        manager.setPowerUpForTesting(powerUp);

        assertTrue(manager.checkCollision(player));

        verify(player).setPowerUp(PowerUpType.ROBOT);
        verify(player).setVy(0);
        assertTrue(manager.didCollectPowerUpThisFrame());
        assertNull(manager.getPowerUp());
    }
}