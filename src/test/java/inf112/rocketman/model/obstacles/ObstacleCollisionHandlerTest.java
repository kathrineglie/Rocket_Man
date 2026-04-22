package inf112.rocketman.model.obstacles;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.character.TPowah;
import inf112.rocketman.model.obstacles.lazers.Lazer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ObstacleCollisionHandlerTest {

    private TPowah player;
    private ObstacleManager obstacleManager;
    private ObstacleCollisionHandler collisionHandler;

    /**
     * Sets up a mocked player and obstacle manager before each test.
     *
     * The player mock is used to control the hitbox returned during collision checks,
     * and the obstacle manager mock is used to provide custom obstacle lists for each test.
     *
     * This allows the collision logic to be tested in isolation without depending on
     * the full game model.
     */
    @BeforeEach
    void setUp() {
        player = mock(TPowah.class);
        obstacleManager = mock(ObstacleManager.class);
        collisionHandler = new ObstacleCollisionHandler(player, obstacleManager);
    }

    @Test
    void testHandleObstacleCollisionReturnsFalseWhenNoObstacles() {
        when(player.getHitBox()).thenReturn(new Rectangle(100, 100, 50, 50));
        when(obstacleManager.getObstacleListReference()).thenReturn(new ArrayList<>());

        assertFalse(collisionHandler.handleObstacleCollision());
    }

    @Test
    void testHandleObstacleCollisionReturnsTrueWhenObstacleOverlaps() {
        Rectangle playerHitbox = new Rectangle(100, 100, 50, 50);
        TestObstacle obstacle = new TestObstacle(110, 110, 50, 50, 0, 0);

        when(player.getHitBox()).thenReturn(playerHitbox);
        when(obstacleManager.getObstacleListReference()).thenReturn(new ArrayList<>(List.of(obstacle)));

        assertTrue(collisionHandler.handleObstacleCollision());
    }

    @Test
    void testInactiveLazerIsIgnored() {
        Rectangle playerHitbox = new Rectangle(100, 100, 50, 50);
        TestLazer lazer = new TestLazer(100, 100, 50, 50, 0, 0);
        lazer.setProgressionLevel(2);

        when(player.getHitBox()).thenReturn(playerHitbox);
        when(obstacleManager.getObstacleListReference()).thenReturn(new ArrayList<>(List.of(lazer)));

        assertFalse(collisionHandler.handleObstacleCollision());
    }

    @Test
    void testActiveLazerCollisionReturnsTrue() {
        Rectangle playerHitbox = new Rectangle(100, 100, 50, 50);
        TestLazer lazer = new TestLazer(100, 100, 50, 50, 0, 0);
        lazer.setProgressionLevel(3);

        when(player.getHitBox()).thenReturn(playerHitbox);
        when(obstacleManager.getObstacleListReference()).thenReturn(new ArrayList<>(List.of(lazer)));

        assertTrue(collisionHandler.handleObstacleCollision());
    }

    private static class TestObstacle extends Obstacle {
        protected TestObstacle(float x, float y, float width, float height, float vx, float vy) {
            super(x, y, width, height, vx, vy);
        }
    }

    private static class TestLazer extends Lazer {
        protected TestLazer(float x, float y, float width, float height, float vx, float vy) {
            super(x, y, width, height, vx, vy);
        }
    }
}