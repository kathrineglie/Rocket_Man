package inf112.rocketman.model.obstacles;

import inf112.rocketman.model.obstacles.flames.Flame;
import inf112.rocketman.model.obstacles.lazers.Lazer;
import inf112.rocketman.model.obstacles.rockets.Rocket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomObstacleFactoryTest {
    private RandomObstacleFactory factory;

    @BeforeEach
    void setup() {
        factory = new RandomObstacleFactory();
    }

    @Test
    void newRocketReturnsRocketWithExpectedSizeAndVelocity() {
        float worldWidth = 1000f;
        float worldHeight = 600f;
        float ground = 50f;
        float margin = 20f;
        float vx = -8f;

        Obstacle rocket = factory.newObstacle(ObstacleType.ROCKET, worldWidth, worldHeight, ground, margin, vx);

        assertNotNull(rocket);

        float expectedHeight = worldHeight / 15f;
        float expectedWidth = worldWidth / 10f;
        float expectedX = worldWidth - margin - expectedWidth;

        assertEquals(expectedX, rocket.getX(), 0.0001f);
        assertEquals(expectedWidth, rocket.getWidth(), 0.0001f);
        assertEquals(expectedHeight, rocket.getHeight(), 0.0001f);
        assertEquals(vx, rocket.getVX(), 0.0001);

    }

    @Test
    void newRocketPlacesRocketWithinValidYRange() {
        float worldWidth = 1000f;
        float worldHeight = 600f;
        float ground = 50f;
        float margin = 20f;
        float vx = -8f;

        Obstacle rocket = factory.newObstacle(ObstacleType.ROCKET, worldWidth, worldHeight, ground, margin, vx);

        float rocketHeight = worldHeight / 15f;
        float minY = margin - rocketHeight;
        float maxY = worldHeight - margin- rocketHeight;

        assertTrue(rocket.getY() >= minY);
        assertTrue(rocket.getY() <= maxY);
    }

    @Test
    void newRocketReturnsRocket() {
        Obstacle rocket = factory.newObstacle(ObstacleType.ROCKET,1000f, 600f, 50f, 20f, -8f);
        assertNotNull(rocket);
        assertInstanceOf(Rocket.class, rocket);
    }

    @Test
    void newRocketAlwaysPlacesRocketWithinValidRange() {
        float worldWidth = 1000f;
        float worldHeight = 600f;
        float ground = 50f;
        float margin = 20f;
        float vx = -8f;

        float rocketHeight = worldHeight / 15f;
        float minY = margin - rocketHeight;
        float maxY = worldHeight - margin- rocketHeight;

        for (int i = 0; i < 20; i++) {
            Obstacle rocket = factory.newObstacle(ObstacleType.ROCKET ,worldWidth, worldHeight, ground, margin, vx);
            assertTrue(rocket.getY() >= minY);
            assertTrue(rocket.getY() <= maxY);
        }
    }

    @Test
    void newLazerIsNotNull() {
        Obstacle lazer = factory.newObstacle(ObstacleType.LAZER,800, 600, 50, 5, 0);
        assertNotNull(lazer);
        assertInstanceOf(Lazer.class, lazer);
    }

    @Test
    void newLazerHasYWithinMargin() {
        float worldWidth = 800;
        float worldHeight = 600;
        float ground = 100;
        float margin = 50;

        Obstacle lazer = factory.newObstacle(ObstacleType.LAZER, worldWidth, worldHeight, ground, margin, 0);

        assertTrue(lazer.getY() >= margin);
        assertTrue(lazer.getY() <= worldHeight - margin);
    }

    @Test
    void newLazerCorrectHeight() {
        float worldHeight = 600;

        Obstacle lazer = factory.newObstacle(ObstacleType.LAZER,800, worldHeight, 100, 50, 0);

        assertEquals(worldHeight / 15, lazer.getHeight());
    }

    @Test
    void newLazerCorrectWidth() {
        float worldWidth = 800;

        Obstacle lazer = factory.newObstacle(ObstacleType.LAZER, worldWidth, 600, 100, 50, 0);

        assertEquals(worldWidth, lazer.getWidth());
    }

    @Test
    void validFlame() {
        float worldWidth = 1000f;
        float worldHeight = 800f;
        float margin = 20f;
        float ground = 120;
        float vx = 0.1f;

        for (int i = 0; i < 1000; i++) {
            Flame flame = (Flame) factory.newObstacle(ObstacleType.FLAME,worldWidth, worldHeight, ground, margin, vx);

            assertNotNull(flame); // Checks that the flame is not null

            float angle = flame.getAngle();
            // Checks that the flame has one of the chosen angles.
            assertTrue(angle >= 0 && angle <= 360);

            // Checks that the polygon for the flame is not null
            assertNotNull(flame.getPolygon());

            float[] vertices = flame.getPolygon().getTransformedVertices();

            //Checks that the object does not spawn outside of the screen.
            for (int j = 0; j < vertices.length; j += 2) {
                //float px = vertices[j];
                float py = vertices[j + 1];

                assertTrue(py >= margin, "py too low: " + py + " margin: " + margin + " worldheight: " + worldHeight);
                assertTrue(py <= worldHeight - margin, "py too high: " + py + " margin: " + margin + " worldheight: " +worldHeight);

            }
        }
    }
}
