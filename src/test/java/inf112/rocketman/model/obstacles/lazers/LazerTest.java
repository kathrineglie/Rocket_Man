package inf112.rocketman.model.obstacles.lazers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LazerTest {
    @Test
    void testLazerNotNull() {
        Lazer lazer = new Lazer(100, 200, 50, 50, 0, 0);
        assertNotNull(lazer);
    }

    @Test
    void isFinishedTest() {
        Lazer lazer = new Lazer(100, 200, 50, 50, 0, 0);
        assertFalse(lazer.isFinished());
        lazer.update(1.5f);
        lazer.update(1.5f);
        lazer.update(2.0f);
        assertTrue(lazer.isFinished());
    }

    @Test
    void progressionLevel1Test() {
        Lazer lazer = new Lazer(100, 200, 50, 50, 0, 0);
        assertEquals(1, lazer.getProgressionLevel());
        lazer.update(1.4f);
        assertEquals(1, lazer.getProgressionLevel());
    }
    @Test
    void progressionLevel2Test() {
        Lazer lazer = new Lazer(100, 200, 50, 50, 0, 0);
        lazer.update(1.5f);
        assertEquals(2, lazer.getProgressionLevel());
        lazer.update(1.4f);
        assertEquals(2, lazer.getProgressionLevel());
    }

    @Test
    void progressionLevel3Test() {
        Lazer lazer = new Lazer(100, 200, 50, 50, 0, 0);
        lazer.update(1.5f);
        lazer.update(1.5f);
        assertEquals(3, lazer.getProgressionLevel());
        lazer.update(1.9f);
        assertEquals(3, lazer.getProgressionLevel());
    }

    @Test
    void progressionLevel4Test() {
        Lazer lazer = new Lazer(100, 200, 50, 50, 0, 0);
        lazer.update(1.5f);
        lazer.update(1.5f);
        lazer.update(2.0f);
        assertEquals(4, lazer.getProgressionLevel());
        assertTrue(lazer.isFinished());
    }

    @Test
    void testGetProgressionLevel() {
        Lazer lazer = new Lazer(100, 200, 50, 50, 0, 0);
        assertEquals(1, lazer.getProgressionLevel());
        lazer.update(1.5f);
        assertEquals(2, lazer.getProgressionLevel());
        lazer.update(1.5f);
        assertEquals(3, lazer.getProgressionLevel());
        lazer.update(2.0f);
        assertEquals(4, lazer.getProgressionLevel());
    }

    @Test
    void setProgressionLevelTest() {
        Lazer lazer = new Lazer(100, 200, 50, 50, 0, 0);

        lazer.setProgressionLevel(3);
        assertEquals(3, lazer.getProgressionLevel());

        lazer.setProgressionLevel(4);
        assertEquals(4, lazer.getProgressionLevel());
    }

    @Test
    void updateMovesLazerWhenFinished() {
        Lazer lazer = new Lazer(100, 200, 50, 50, 10, -5);
        lazer.setProgressionLevel(4);

        lazer.update(2.0f);

        assertEquals(120f, lazer.getX(), 0.0001);
        assertEquals(190f, lazer.getY(), 0.0001);
    }
}
