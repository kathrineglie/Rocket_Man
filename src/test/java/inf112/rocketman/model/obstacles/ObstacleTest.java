package inf112.rocketman.model.obstacles;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Velocity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {
    private IObstacle obstacle;

    @BeforeEach
    void setup() {
        obstacle = new Obstacle (new Rectangle(100, 80, 50, 80), new Velocity(-50, 0), 0);
    }

    @Test
    void update() {
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
        assertNotNull(obstacle);
    }
}