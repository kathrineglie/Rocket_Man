package inf112.rocketman.model;

import com.badlogic.gdx.Preferences;
import inf112.rocketman.model.coins.Coin;
import inf112.rocketman.model.powerups.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameModelTest {
    private Preferences highscores;
    private Preferences coins;
    private GameModel model;

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

        coins = mock(Preferences.class);
        when(coins.get()).thenReturn(new java.util.HashMap<>());
        when(coins.getInteger(anyString(), anyInt())).thenAnswer(invocation -> invocation.getArgument(1));
        when(coins.getInteger(anyString())).thenReturn(0);

        model = new GameModel(1000, 800, 5, highscores, coins);


    }


    @Test
    public void testPauseGameChangesState() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.startNewGame();

        model.pauseGame();

        assertEquals(GameState.PAUSE, model.getGameState());
    }

    @Test
    public void testResumeGameChangesStateBackToPlaying() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.pauseGame();

        model.resumeGame();

        assertEquals(GameState.PLAYING, model.getGameState());
    }

    @Test
    public void testGoToHomeScreen() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.startNewGame();
        model.goToHomescreen();
        assertEquals(GameState.HOME_SCREEN, model.getGameState());
    }

    @Test
    public void testShowInstructions() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.showInstructions();
        assertEquals(GameState.INSTRUCTIONS, model.getGameState());
    }

    @Test
    public void testPositionDoesNotChangeWhenPaused() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.startNewGame();
        model.pauseGame();

        float initialY = model.getPlayer().getY();

        model.update(0.01f, true);

        assertEquals(initialY, model.getPlayer().getY());
    }

    @Test
    public void testObstaclesClearedOnStartGame() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.startNewGame();

        model.update(2.0f, false);

        model.startNewGame();

        assertEquals(0, model.getObstacles().size(), "Obstacle list should be empty on restart");
    }

    @Test
    public void testInitialStateIsHomeScreen() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        assertEquals(GameState.HOME_SCREEN, model.getGameState());
    }

    @Test
    public void testTogglePauseMultipleTimes() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.startNewGame();

        model.pauseGame();
        assertEquals(GameState.PAUSE, model.getGameState());

        model.resumeGame();
        assertEquals(GameState.PLAYING, model.getGameState());

        model.pauseGame();
        assertEquals(GameState.PAUSE, model.getGameState());
    }

    @Test
    public void testScoreIncreaseOverTime() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.startNewGame();

        int initialScore = model.getGameScore();

        model.update(1.0f, false);
        model.update(0.01f, false);

        int scoreAfterTime =model.getGameScore();

        assertTrue(scoreAfterTime > initialScore, "Score should increase after 1 second");
    }

    @Test
    public void testBackgroundScrolling() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.startNewGame();

        float initialScroll = model.getBackgroundScrollX();

        model.update(0.1f, false);

        assertNotEquals(initialScroll, model.getBackgroundScrollX(), "Background should move when game is updates");

        assertTrue(model.getBackgroundScrollX() < initialScroll, "Background should scroll to the left (negative direction)");
    }

    @Test
    public void testUpdateDoesNothingWhenGameIsNotPlaying() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);

        float initialScroll = model.getBackgroundScrollX();
        model.update(1.0f, false);

        assertEquals(initialScroll, model.getBackgroundScrollX(), "Background should not scroll in Home Screen");
    }

    @Test
    public void testPlayerMovesWhenMovingUpward() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.startNewGame();

        float initialY = model.getPlayer().getY();

        model.update(0.1f, true);

        assertTrue(model.getPlayer().getY() > initialY, "Player should have moved up from initial position");
    }

    @Test
    public void testScoreResetsOnStartGame() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.startNewGame();

        for(int i = 0; i < 5; i++) {
            model.update(0.5f, false);
        }

        assertTrue(model.getGameScore() > 0, "Score should have increased by now");

        model.startNewGame();

        assertEquals(0, model.getGameScore(), "Score should reset to 0 when starting a new game");
    }

    @Test
    public void testBirdPowerUpState() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.startNewGame();

        assertFalse(model.hasBirdPowerUp(), "Should not have bird power-up at start");

        model.getPlayer().setPowerUp(PowerUpType.BIRD);

        assertTrue(model.hasBirdPowerUp(), "hasBirdPowerUp should return true when player has BIRD type");
    }

    @Test
    public void testNoUpdateDuringInstruction() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.startNewGame();
        model.showInstructions();

        float initialScroll = model.getBackgroundScrollX();
        model.update(1.0f, false);

        assertEquals(initialScroll, model.getBackgroundScrollX(), "Background should not move while showing instructions");
    }

    @Test
    public void testStartGameChangesStateToPlaying() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);

        model.startNewGame();

        assertEquals(GameState.PLAYING, model.getGameState());
    }

    @Test
    public void testGoToHomeScreenRemovesPowerUp() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.startNewGame();

        model.getPlayer().setPowerUp(PowerUpType.BIRD);
        model.goToHomescreen();

        assertEquals(GameState.HOME_SCREEN, model.getGameState());
        assertNull(model.getPowerUp());
    }

    @Test
    public void testIsMovingUpReflectsUpdateInput() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.startNewGame();

        model.update(0.1f, true);
        assertTrue(model.isMovingUp());

        model.update(0.1f, false);
        assertFalse(model.isMovingUp());
    }

    @Test
    public void testSCoreDoesNotIncreaseWhenPaused() {
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.startNewGame();
        model.pauseGame();

        int initialScore = model.getGameScore();

        model.update(1.0f, false);
        model.update(1.0f, false);

        assertEquals(initialScore, model.getGameScore());
    }
    @Test
    void testHasGravitySuitPowerUp() {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);
        model.startNewGame();

        assertFalse(model.hasGravitySuitPowerUp());

        model.getPlayer().setPowerUp(PowerUpType.GRAVITY_SUIT);

        assertTrue(model.hasGravitySuitPowerUp());

    }

    @Test
    void onGround() {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);

        assertTrue(model.onGround());
    }

    @Test
    void testOnGroundBecomesFalseWhenPlayerMovesUp() {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);
        model.startNewGame();

        model.update(0.1f, true);

        assertFalse(model.onGround());
    }

    @Test
    void testGetWorldDimensions() {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);

        assertEquals(1000f, model.getWorldWidth());
        assertEquals(800f, model.getWorldHeight());
    }

    @Test
    void testInitialCoinCountIsZero() {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);

        assertEquals(0, model.getCoinCount());
    }

    @Test
    void testGetPlayerName() {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);

        model.setPlayerName("Bob");

        assertEquals("Bob", model.getPlayerName());
    }

    @Test
    void testInitialCollectFalgsAreFalse() {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);

        assertFalse(model.didCollectCoinThisFrame());
        assertFalse(model.didCollectPowerUpThisFrame());
    }



    @Test
    void testGoToHomeScreenClearsObstaclesAndResetsScore() {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);
        model.startNewGame();

        model.update(3.0f, false);
        model.setGameScore(50);

        model.goToHomescreen();

        assertEquals(GameState.HOME_SCREEN, model.getGameState());
        assertEquals(0, model.getGameScore());
        assertEquals(0, model.getObstacles().size());
    }




    @Test
    public void testGetSavedCoinsForPlayer() {
        when(coins.getInteger("Bob", 0)).thenReturn(7);

        GameModel model = new GameModel(1000, 800, 5, highscores, coins);

        int savedCoins = model.getSavedCoinsForPlayer("Bob");

        assertEquals(7, savedCoins);
    }


    @Test
    void testGetCoinListIsEmptyAtStart() {
        GameModel model = new GameModel(1000, 800, 5,highscores, coins);

        assertTrue(model.getCoinList().isEmpty());
    }

    @Test
    void testGetCoinListReturnsNewList() {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);

        List<Coin> coins1 = model.getCoinList();
        List<Coin> coins2 = model.getCoinList();

        assertNotSame(coins1, coins2);
    }

    @Test
    void testUsingJetpackBecomesTrueMovingUp() {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);
        model.startNewGame();

        model.update(0.1f, true);

        assertTrue(model.usingJetpack());
    }

    @Test
    void testUsingJetpackBecomesFalseWhenNotMovingUp() {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);
        model.startNewGame();

        model.update(0.1f, true);
        model.update(0.1f, false);

        assertFalse(model.usingJetpack());
    }

    @Test
    public void testPirateHat() {
        when(coins.getInteger("Bob", 0)).thenReturn(50);

        model = new GameModel(1000, 800, 5, highscores, coins);
        model.setPlayerName("Bob");
        model.startNewGame();
        model.update(0.1f, true);
        assertTrue(model.hasPirateHat());
    }
}
