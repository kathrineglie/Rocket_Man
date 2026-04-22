package inf112.rocketman.model.obstacles;

import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    @Test
    void update() {
        IObstacle obstacle = new Obstacle (100, 80, 50, 80, -50, 0);
        obstacle.update(50);

        float off_set = obstacle.getOffSet();

        assertEquals(-2400, obstacle.getX());
        assertEquals(80, obstacle.getY());

        Rectangle hitbox = obstacle.getHitBox();

        float excpectedX = -2400f + off_set;
        float excpectedY = 80f + off_set;
        float excpectedWidth = 50f - 2* off_set;
        float excpectedHeight = 80f - 2* off_set;

        assertEquals(excpectedX, hitbox.x);
        assertEquals(excpectedY, hitbox.y);
        assertEquals(excpectedWidth, hitbox.getWidth());
        assertEquals(excpectedHeight, hitbox.getHeight());
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