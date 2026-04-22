package inf112.rocketman.model.difficulty;

import inf112.rocketman.model.obstacles.ObstacleManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class DifficultyControllerTest {

    private ObstacleManager obstacleManager;
    private DifficultyController difficultyController;

    /**
     * Sets up a mocked ObstacleManager and a DifficultyController before each test.
     *
     * The mock is configured to return the default obstacle spawn interval
     * used at the start of the game.
     *
     * This allows the tests to verify difficulty changes without needing
     * a real obstacle system.
     */
    @BeforeEach
    void setUp() {
        obstacleManager = mock(ObstacleManager.class);
        when(obstacleManager.getObstacleSpawnInterval()).thenReturn(2.5f);

        difficultyController = new DifficultyController(obstacleManager);
    }

    @Test
    void testInitialValues() {
        assertEquals(1, difficultyController.getDifficulty());
        assertEquals(-350f, difficultyController.getBgSpeed());
        assertEquals(-550f, difficultyController.getRocketSpeed());
        assertEquals(0.4f, difficultyController.getScoreInterval());
    }

    @Test
    void testDifficultyDoesNotIncreaseBelowThreshold() {
        difficultyController.updateDifficulty(100);

        assertEquals(1, difficultyController.getDifficulty());
        verify(obstacleManager, never()).setObstacleSpawnInterval(anyFloat());
        verify(obstacleManager, never()).updateObstacleSpeeds(anyFloat(), anyFloat());
    }

    @Test
    void testDifficultyIncreasesAboveThreshold() {
        difficultyController.updateDifficulty(101);

        assertEquals(2, difficultyController.getDifficulty());
        assertTrue(difficultyController.getBgSpeed() < -350f);
        assertTrue(difficultyController.getRocketSpeed() < -550f);
        assertTrue(difficultyController.getScoreInterval() <= 0.4f);

        verify(obstacleManager).setObstacleSpawnInterval(2.1f);
        verify(obstacleManager).updateObstacleSpeeds(
                difficultyController.getBgSpeed(),
                difficultyController.getRocketSpeed()
        );
    }

    @Test
    void testDifficultyIncreaseChangesBackgroundSpeed() {
        float initialBgSpeed = difficultyController.getBgSpeed();

        difficultyController.updateDifficulty(101);

        assertTrue(difficultyController.getBgSpeed() < initialBgSpeed);
    }

    @Test
    void testDifficultyIncreaseChangesRocketSpeed() {
        float initialRocketSpeed = difficultyController.getRocketSpeed();

        difficultyController.updateDifficulty(101);

        assertTrue(difficultyController.getRocketSpeed() < initialRocketSpeed);
    }

    @Test
    void testDifficultyIncreaseChangesScoreInterval() {
        float initialScoreInterval = difficultyController.getScoreInterval();

        difficultyController.updateDifficulty(101);

        assertTrue(difficultyController.getScoreInterval() < initialScoreInterval);
    }

    @Test
    void testResetDifficultyResetsValues() {
        difficultyController.updateDifficulty(101);
        difficultyController.resetDifficulty();

        assertEquals(1, difficultyController.getDifficulty());
        assertEquals(-350f, difficultyController.getBgSpeed());
        assertEquals(-550f, difficultyController.getRocketSpeed());
        assertEquals(0.4f, difficultyController.getScoreInterval());
    }
}