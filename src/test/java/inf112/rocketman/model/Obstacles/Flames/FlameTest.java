package inf112.rocketman.model.Obstacles.Flames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FlameTest {

    @Test
    void flameUpdate() {
        Flame flame = new Flame(100, 200, 20, 30, -50, 0, 0);

        flame.update(1f);

        //assertEquals(50, flame.getX());
    }

    @Test
    void testPolygonNotNull() {
        Flame flame = new Flame(100, 200, 20, 30, -50, 0, 0);

        assertNotNull(flame.getPolygon());
    }


}
