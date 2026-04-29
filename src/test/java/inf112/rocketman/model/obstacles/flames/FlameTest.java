package inf112.rocketman.model.obstacles.flames;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Velocity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlameTest {
    private Flame flame;

    @BeforeEach
    void setup() {
        flame = new Flame(new Rectangle(100, 200, 20, 30), new Velocity(0, 0),50, 45);
    }

    @Test
    void flameUpdate() {
        flame.update(1f);

        assertEquals(100, flame.getX());
        assertEquals(200, flame.getY());
    }

    @Test
    void rectangleNull() {
        assertNull(flame.getHitBox());
    }

    @Test
    void testPolygonNotNull() {
        assertNotNull(flame.getPolygon());
    }

    @Test
    void getAngleTest() {
        assertEquals(45, flame.getAngle());
    }
}
