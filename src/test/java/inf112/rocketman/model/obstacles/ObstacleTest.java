package inf112.rocketman.model.obstacles;

import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    @Test
    void update() {
        IObstacle obstacle = new Obstacle (100, 80, 50, 80, -50, 0);
        obstacle.update(50);

        float offset = obstacle.getOffSet();

        assertEquals(-2400, obstacle.getX());
        assertEquals(80, obstacle.getY());

        Rectangle hitbox = obstacle.getHitBox();

        float expectedX = -2400f + offset;
        float expectedY = 80f + offset;
        float expectedWidth = 50f - 2* offset;
        float expectedHeight = 80f - 2* offset;

        assertEquals(expectedX, hitbox.x);
        assertEquals(expectedY, hitbox.y);
        assertEquals(expectedWidth, hitbox.getWidth());
        assertEquals(expectedHeight, hitbox.getHeight());
    }

    @Test
    void getHitBoxNotNull() {
        IObstacle obstacle = new Obstacle (100, 80, 50, 80, -50, 0);
        assertNotNull(obstacle);
    }

    @Test
    void isOfScreenLeft() {
        IObstacle obstacle = new Obstacle (-50, 80, 50, 80, -50, 0);
        assertTrue(obstacle.isOfScreen(1000, 800));
    }

    @Test
    void isOfScreenTop() {
        IObstacle obstacle = new Obstacle (50, 850, 50, 80, -50, 0);
        assertTrue(obstacle.isOfScreen(1000, 800));
    }

    @Test
    void isOfScreenBottom() {
        IObstacle obstacle = new Obstacle (50, 0, 50, 80, -50, 0);
        assertTrue(obstacle.isOfScreen(1000, 800));
    }

    @Test
    void checkFalseOnScreen() {
        IObstacle obstacle = new Obstacle (50, 200, 50, 80, -50, 0);
        assertFalse(obstacle.isOfScreen(1000, 800));
    }
}