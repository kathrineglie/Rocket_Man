package inf112.rocketman.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import inf112.rocketman.model.Coins.Coin;
import inf112.rocketman.model.Coins.RandomCoinFactory;
import inf112.rocketman.model.Obstacles.Rockets.RandomRocketFactory;
import inf112.rocketman.model.Obstacles.Rockets.Rocket;
import inf112.rocketman.model.PowerUps.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameModelTest {
    private Preferences highscores;

    /**
     * Sets up a mocked Preferences object before each test.
     *
     * The mock is configured to behave like an empty highscore storage:
     * - get() returns an empty map
     * - getInteger(key, defaultValue) returns the provided default value
     * - getInteger(key) returns 0
     *
     * This allows the tests to run without requiring a real libGDX Application
     * or actual saved preferences.
     */
    @BeforeEach
    public void setUp() {
        highscores = mock(Preferences.class);

        when(highscores.get()).thenReturn(new java.util.HashMap<>());
        when(highscores.getInteger(anyString(), anyInt())).thenAnswer(invocation -> invocation.getArgument(1));
        when(highscores.getInteger(anyString())).thenReturn(0);
    }

    @Test
    public void testHighscoresAreSorted(){}

    @Test
    public void testSameNameOnHighscoreBoard(){}

    @Test
    public void testPauseGameChangesState() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.startGame();

        model.pauseGame();

        assertEquals(GameState.PAUSE, model.getGameState());
    }

    @Test
    public void testResumeGameChangesStateBackToPlaying() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.pauseGame();

        model.resumeGame();

        assertEquals(GameState.PLAYING, model.getGameState());
    }

    @Test
    public void testGoToHomeScreen() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.startGame();
        model.goToHomescreen();
        assertEquals(GameState.HOME_SCREEN, model.getGameState());
    }

    @Test
    public void testShowInstructions() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.showInstructions();
        assertEquals(GameState.INSTRUCTIONS, model.getGameState());
    }

    @Test
    public void testPositionDoesNotChangeWhenPaused() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.startGame();
        model.pauseGame();

        float initialY = model.getPlayer().getY();

        model.update(0.01f, true);

        assertEquals(initialY, model.getPlayer().getY());
    }

    @Test
    public void testObstaclesClearedOnStartGame() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.startGame();

        model.update(2.0f, false);

        model.startGame();

        assertEquals(0, model.getObstacles().size(), "Obstacle list should be empty on restart");
    }

    @Test
    public void testInitialStateIsHomeScreen() {
        GameModel model = new GameModel(1000, 800, highscores);
        assertEquals(GameState.HOME_SCREEN, model.getGameState());
    }

    @Test
    public void testTogglePauseMultipleTimes() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.startGame();

        model.pauseGame();
        assertEquals(GameState.PAUSE, model.getGameState());

        model.resumeGame();
        assertEquals(GameState.PLAYING, model.getGameState());

        model.pauseGame();
        assertEquals(GameState.PAUSE, model.getGameState());
    }

    @Test
    public void testScoreIncreaseOverTime() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.startGame();

        int initialScore = model.getGameScore();

        model.update(1.0f, false);
        model.update(0.01f, false);

        int scoreAfterTime =model.getGameScore();

        assertTrue(scoreAfterTime > initialScore, "Score should increase after 1 second");
    }

    @Test
    public void testBackgroundScrolling() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.startGame();

        float initialScroll = model.getBackgroundScrollX();

        model.update(0.1f, false);

        assertNotEquals(initialScroll, model.getBackgroundScrollX(), "Background should move when game is updates");

        assertTrue(model.getBackgroundScrollX() < initialScroll, "Background should scroll to the left (negative direction)");
    }

    @Test
    public void testUpdateDoesNothingWhenGameIsNotPlaying() {
        GameModel model = new GameModel(1000, 800, highscores);

        float initialScroll = model.getBackgroundScrollX();
        model.update(1.0f, false);

        assertEquals(initialScroll, model.getBackgroundScrollX(), "Background should not scroll in Home Screen");
    }

    @Test
    public void testPlayerMovesWhenMovingUpward() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.startGame();

        float initialY = model.getPlayer().getY();

        model.update(0.1f, true);

        assertTrue(model.getPlayer().getY() > initialY, "Player should have moved up from initial position");
    }

    @Test
    public void testScoreResetsOnStartGame() {
        GameModel model = new GameModel(1000, 800, highscores);
        model. startGame();

        for(int i = 0; i < 5; i++) {
            model.update(0.5f, false);
        }

        assertTrue(model.getGameScore() > 0, "Score should have increased by now");

        model.startGame();

        assertEquals(0, model.getGameScore(), "Score should reset to 0 when starting a new game");
    }

    @Test
    public void testBirdPowerUpState() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.startGame();

        assertFalse(model.hasBirdPowerUp(), "Should not have bird power-up at start");

        model.getPlayer().setPowerUp(PowerUpType.BIRD);

        assertTrue(model.hasBirdPowerUp(), "hasBirdPowerUp should return true when player has BIRD type");
    }

    @Test
    public void testNoUpdateDuringInstruction() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.startGame();
        model.showInstructions();

        float initialScroll = model.getBackgroundScrollX();
        model.update(1.0f, false);

        assertEquals(initialScroll, model.getBackgroundScrollX(), "Background should not move while showing instructions");
    }

    @Test
    public void testStartGameChangesStateToPlaying() {
        GameModel model = new GameModel(1000, 800, highscores);

        model.startGame();

        assertEquals(GameState.PLAYING, model.getGameState());
    }

    @Test
    public void testGoToHomeScreenRemovesPowerUp() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.startGame();

        model.getPlayer().setPowerUp(PowerUpType.BIRD);
        model.goToHomescreen();

        assertEquals(GameState.HOME_SCREEN, model.getGameState());
        assertNull(model.getPowerUp());
    }

    @Test
    public void testIsMovingUpReflectsUpdateInput() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.startGame();

        model.update(0.1f, true);
        assertTrue(model.isMovingUp());

        model.update(0.1f, false);
        assertFalse(model.isMovingUp());
    }

    @Test
    public void testSCoreDoesNotIncreaseWhenPaused() {
        GameModel model = new GameModel(1000, 800, highscores);
        model.startGame();
        model.pauseGame();

        int initialScore = model.getGameScore();

        model.update(1.0f, false);
        model.update(1.0f, false);

        assertEquals(initialScore, model.getGameScore());
    }

}
