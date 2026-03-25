package inf112.rocketman.model.Obstacles.Lazers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RandomLazerFactoryTest {

    @Test
    void newLazerIsNotNull() {
        RandomLazerFactory lazerFactory = new RandomLazerFactory();
        Lazer lazer = lazerFactory.newLazer(800, 600, 50, 5);
        assertNotNull(lazer);
    }

    @Test
    void newLazerHasYWithinMargin() {
        RandomLazerFactory factory = new RandomLazerFactory();
        float worldWidth = 800;
        float worldHeight = 600;
        float ground = 100;
        float margin = 50;

        Lazer lazer = factory.newLazer(worldWidth, worldHeight, ground, margin);

        assertTrue(lazer.getY() >= margin);
        assertTrue(lazer.getY() <= worldHeight - margin);
    }

    @Test
    void newLazerCorrectHeight() {
        RandomLazerFactory factory = new RandomLazerFactory();
        float worldHeight = 600;

        Lazer lazer = factory.newLazer(800, worldHeight, 100, 50);

        assertEquals(worldHeight / 15, lazer.getHeight());
    }

    @Test
    void newLazerCorrectWidth() {
        RandomLazerFactory factory = new RandomLazerFactory();
        float worldWidth = 800;

        Lazer lazer = factory.newLazer(worldWidth, 600, 100, 50);

        assertEquals(worldWidth, lazer.getWidth());
    }
}
