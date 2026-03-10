package inf112.rocketman.model.Obstacles.Flames;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomFlameFactoryTest {

    @Test
    void validFlame() {
        RandomFlameFactory factory = new RandomFlameFactory();
        float worldWidth = 1000f;
        float worldHeight = 800f;
        float margin = 20f;
        float vx = 0.1f;

        for (int i = 0; i < 1000; i++) {
            Flame flame = factory.newFlame(worldWidth, worldHeight, margin, vx);

            assertNotNull(flame); // Checks that the flame is not null

            float angle = flame.getAngle();
            // Checks that the flame has one of the chosen angles.
            assertTrue(angle >= 0 && angle <= 360);

            // Checks that the polygon for the flame is not null
            assertNotNull(flame.getPolygon());

            float[] vertices = flame.getPolygon().getTransformedVertices();

            //Checks that the object does not spawn outside of the screen.
            for (int j = 0; j < vertices.length; j += 2) {
                float px = vertices[j];
                float py = vertices[j + 1];

                assertTrue(py >= margin, "py too low: " + py + " margin: " + margin + " worldheight: " + worldHeight);
                assertTrue(py <= worldHeight - margin, "py too high: " + py + " margin: " + margin + " worldheight: " +worldHeight);
            }
        }
    }
}
