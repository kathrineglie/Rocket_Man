package inf112.rocketman.model.obstacles.lazers;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Velocity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LazerTest {
    private Lazer lazer;

    @BeforeEach
    void setup() {
        lazer = new Lazer(new Rectangle(100, 200, 50, 50), new Velocity(0, 0), 0);
    }

    @Test
    void testLazerNotNull() {
        assertNotNull(lazer);
    }

    @Test
    void isFinishedTest() {
        assertFalse(lazer.isFinished());
        lazer.update(1.5f);
        lazer.update(1.5f);
        lazer.update(2.0f);
        assertTrue(lazer.isFinished());
    }

    @Test
    void progressionLevel1Test() {
        assertEquals(1, lazer.getProgressionLevel());
        lazer.update(1.4f);
        assertEquals(1, lazer.getProgressionLevel());
    }
    @Test
    void progressionLevel2Test() {
        lazer.update(1.5f);
        assertEquals(2, lazer.getProgressionLevel());
        lazer.update(1.4f);
        assertEquals(2, lazer.getProgressionLevel());
    }

    @Test
    void progressionLevel3Test() {
        lazer.update(1.5f);
        lazer.update(1.5f);
        assertEquals(3, lazer.getProgressionLevel());
        lazer.update(1.9f);
        assertEquals(3, lazer.getProgressionLevel());
    }

    @Test
    void progressionLevel4Test() {
        lazer.update(1.5f);
        lazer.update(1.5f);
        lazer.update(2.0f);
        assertEquals(4, lazer.getProgressionLevel());
        assertTrue(lazer.isFinished());
    }

    @Test
    void testGetProgressionLevel() {
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
        lazer.setProgressionLevel(3);
        assertEquals(3, lazer.getProgressionLevel());

        lazer.setProgressionLevel(4);
        assertEquals(4, lazer.getProgressionLevel());
    }

    @Test
    void updateMovesLazerWhenFinished() {
        lazer.setProgressionLevel(4);

        lazer.update(2.0f);

        assertEquals(100f, lazer.getX(), 0.0001);
        assertEquals(200f, lazer.getY(), 0.0001);
    }
}
