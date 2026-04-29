package inf112.rocketman.model.movement;

import inf112.rocketman.model.character.TPowah;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NormalMovementTest {
    private TPowah player;

    @BeforeEach
    void setup() {
        player = new TPowah(100, 30, 50, 50, 30);
    }

    @Test
    void testMovementScaleWithDeltaTime() {
        TPowah player1 = new TPowah(100, 400, 50, 50, 50);
        TPowah player2 = new TPowah(100, 400, 50, 50, 50);

        player1.update(0.01f, true, 800, 50);
        player2.update(0.02f, true, 800, 50);

        float dist1 = player1.getY()-400;
        float dist2 = player2.getY()-400;

        assertTrue(dist2 > dist1, "Larger delta time should result in larger movement");
    }

    @Test
    void testGravityPullsPlayerDown() {
        assertFalse(player.hasPowerUp());
        float initialY = 300;
        player.setY(initialY);

        player.update(0.1f, false, 800, 50);

        assertTrue(player.isGoingDown());
        assertTrue(player.getY() < initialY, "Player should have fallen down due to gravity");
    }

    @Test
    void testTerminalVelocity() {
        for (int i = 0; i < 100; i++) {
            player.update(0.1f, true, 800, 50);
        }

        float y1 = player.getY();
        player.update(0.1f, true, 800, 50);
        float y2 = player.getY();

        float distancedMoved = y2 - y1;
        assertTrue(distancedMoved <= 700f * 0.1f + 0.01f, "Player velocity exceeded NORMAL_MAX_VY");
    }

    @Test
    void testXPositionIsConstant() {
        player.update(0.1f, true, 800, 50);
        player.update(0.1f, false, 800, 50);

        assertEquals(100f, player.getX(), "X position should never change during update");
    }
}