package inf112.rocketman.model.obstacles.flames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlameTest {

    @Test
    void flameUpdate() {
        Flame flame = new Flame(100, 200, 20, 30, 50, 0, 0);

        flame.update(1f);

        assertEquals(150, flame.getX());
        assertEquals(200, flame.getY());
    }

    @Test
    void rectangleNull() {
        Flame flame = new Flame(100, 200, 20, 30, 50, 0, 0);

        assertNull(flame.getHitBox());
    }

    @Test
    void testPolygonNotNull() {
        Flame flame = new Flame(100, 200, 20, 30, -50, 0, 0);

        assertNotNull(flame.getPolygon());
    }

    @Test
    void getAngleTest() {
        Flame flame = new Flame(100, 200, 20, 30, -50, 0, 45);

        assertEquals(45, flame.getAngle());
    }
}
