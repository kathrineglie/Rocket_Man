package inf112.rocketman.model;

import com.badlogic.gdx.Preferences;
import inf112.rocketman.model.coins.Coin;
import inf112.rocketman.model.powerups.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameModelTest {
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
    void setUp() {
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
    void testPauseGameChangesState() {
        model.startNewGame();

        model.pauseGame();

        assertEquals(GameState.PAUSE, model.getGameState());
    }

    @Test
    void testResumeGameChangesStateBackToPlaying() {
        model.pauseGame();

        model.resumeGame();

        assertEquals(GameState.PLAYING, model.getGameState());
    }

    @Test
    void testGoToHomeScreen() {
        model.startNewGame();
        model.goToHomescreen();
        assertEquals(GameState.HOME_SCREEN, model.getGameState());
    }

    @Test
    void testShowInstructions() {
        model.showInstructions();
        assertEquals(GameState.INSTRUCTIONS, model.getGameState());
    }

    @Test
    void testPositionDoesNotChangeWhenPaused() {
        model.startNewGame();
        model.pauseGame();

        float initialY = model.getPlayer().getY();

        model.update(0.01f, true);

        assertEquals(initialY, model.getPlayer().getY());
    }

    @Test
    void testObstaclesClearedOnStartGame() {
        model.startNewGame();

        model.update(2.0f, false);

        model.startNewGame();

        assertEquals(0, model.getObstacles().size(), "Obstacle list should be empty on restart");
    }

    @Test
    void testInitialStateIsHomeScreen() {
        assertEquals(GameState.HOME_SCREEN, model.getGameState());
    }

    @Test
    void testTogglePauseMultipleTimes() {
        model.startNewGame();

        model.pauseGame();
        assertEquals(GameState.PAUSE, model.getGameState());

        model.resumeGame();
        assertEquals(GameState.PLAYING, model.getGameState());

        model.pauseGame();
        assertEquals(GameState.PAUSE, model.getGameState());
    }

    @Test
    void testScoreIncreaseOverTime() {
        model.startNewGame();

        int initialScore = model.getGameScore();

        model.update(1.0f, false);
        model.update(0.01f, false);

        int scoreAfterTime =model.getGameScore();

        assertTrue(scoreAfterTime > initialScore, "Score should increase after 1 second");
    }

    @Test
    void testBackgroundScrolling() {
        model.startNewGame();

        float initialScroll = model.getBackgroundScrollX();

        model.update(0.1f, false);

        assertNotEquals(initialScroll, model.getBackgroundScrollX(), "Background should move when game is updates");

        assertTrue(model.getBackgroundScrollX() < initialScroll, "Background should scroll to the left (negative direction)");
    }

    @Test
    void testUpdateDoesNothingWhenGameIsNotPlaying() {

        float initialScroll = model.getBackgroundScrollX();
        model.update(1.0f, false);

        assertEquals(initialScroll, model.getBackgroundScrollX(), "Background should not scroll in Home Screen");
    }

    @Test
    void testPlayerMovesWhenMovingUpward() {
        model.startNewGame();

        float initialY = model.getPlayer().getY();

        model.update(0.1f, true);

        assertTrue(model.getPlayer().getY() > initialY, "Player should have moved up from initial position");
    }

    @Test
    void testScoreResetsOnStartGame() {
        model.startNewGame();

        for(int i = 0; i < 5; i++) {
            model.update(0.5f, false);
        }

        assertTrue(model.getGameScore() > 0, "Score should have increased by now");

        model.startNewGame();

        assertEquals(0, model.getGameScore(), "Score should reset to 0 when starting a new game");
    }

    @Test
    void testBirdPowerUpState() {
        model.startNewGame();

        assertFalse(model.hasBirdPowerUp(), "Should not have bird power-up at start");

        model.setPlayerPowerUp(PowerUpType.BIRD);

        assertTrue(model.hasBirdPowerUp(), "hasBirdPowerUp should return true when player has BIRD type");
    }

    @Test
    void testNoUpdateDuringInstruction() {
        model.startNewGame();
        model.showInstructions();

        float initialScroll = model.getBackgroundScrollX();
        model.update(1.0f, false);

        assertEquals(initialScroll, model.getBackgroundScrollX(), "Background should not move while showing instructions");
    }

    @Test
    void testStartGameChangesStateToPlaying() {

        model.startNewGame();

        assertEquals(GameState.PLAYING, model.getGameState());
    }

    @Test
    void testGoToHomeScreenRemovesPowerUp() {
        model.startNewGame();

        model.setPlayerPowerUp(PowerUpType.BIRD);
        model.goToHomescreen();

        assertEquals(GameState.HOME_SCREEN, model.getGameState());
        assertNull(model.getPowerUp());
    }

    @Test
    void testIsMovingUpReflectsUpdateInput() {
        model.startNewGame();

        model.update(0.1f, true);
        assertTrue(model.isMovingUp());

        model.update(0.1f, false);
        assertFalse(model.isMovingUp());
    }

    @Test
    void testSCoreDoesNotIncreaseWhenPaused() {
        model.startNewGame();
        model.pauseGame();

        int initialScore = model.getGameScore();

        model.update(1.0f, false);
        model.update(1.0f, false);

        assertEquals(initialScore, model.getGameScore());
    }
    @Test
    void testHasGravitySuitPowerUp() {
        model.startNewGame();

        assertFalse(model.hasGravitySuitPowerUp());
        model.setPlayerPowerUp(PowerUpType.GRAVITY_SUIT);

        assertTrue(model.hasGravitySuitPowerUp());

    }

    @Test
    void onGround() {

        assertTrue(model.onGround());
    }

    @Test
    void testOnGroundBecomesFalseWhenPlayerMovesUp() {
        model.startNewGame();

        model.update(0.1f, true);

        assertFalse(model.onGround());
    }

    @Test
    void testGetWorldDimensions() {

        assertEquals(1000f, model.getWorldWidth());
        assertEquals(800f, model.getWorldHeight());
    }

    @Test
    void testInitialCoinCountIsZero() {

        assertEquals(0, model.getCoinCount());
    }

    @Test
    void testGetPlayerName() {

        model.setPlayerName("Bob");

        assertEquals("Bob", model.getPlayerName());
    }

    @Test
    void testInitialCollectFalgsAreFalse() {

        assertFalse(model.didCollectCoinThisFrame());
        assertFalse(model.didCollectPowerUpThisFrame());
    }



    @Test
    void testGoToHomeScreenClearsObstaclesAndResetsScore() {
        model.startNewGame();

        model.update(3.0f, false);
        model.setGameScore(50);

        model.goToHomescreen();

        assertEquals(GameState.HOME_SCREEN, model.getGameState());
        assertEquals(0, model.getGameScore());
        assertEquals(0, model.getObstacles().size());
    }




    @Test
    void testGetSavedCoinsForPlayer() {
        when(coins.getInteger("Bob", 0)).thenReturn(7);

        GameModel model = new GameModel(1000, 800, 5, highscores, coins);

        int savedCoins = model.getSavedCoinsForPlayer("Bob");

        assertEquals(7, savedCoins);
    }


    @Test
    void testGetCoinListIsEmptyAtStart() {
        assertTrue(model.getCoinList().isEmpty());
    }

    @Test
    void testGetCoinListReturnsNewList() {

        List<Coin> coins1 = model.getCoinList();
        List<Coin> coins2 = model.getCoinList();

        assertNotSame(coins1, coins2);
    }

    @Test
    void testUsingJetpackBecomesTrueMovingUp() {
        model.startNewGame();

        model.update(0.1f, true);

        assertTrue(model.usingJetpack());
    }

    @Test
    void testUsingJetpackBecomesFalseWhenNotMovingUp() {
        model.startNewGame();

        model.update(0.1f, true);
        model.update(0.1f, false);

        assertFalse(model.usingJetpack());
    }

    @Test
    void testPirateHat() {
        when(coins.getInteger("Bob", 0)).thenReturn(50);

        model = new GameModel(1000, 800, 5, highscores, coins);
        model.setPlayerName("Bob");
        model.startNewGame();
        model.update(0.1f, true);
        assertTrue(model.hasPirateHat());
    }
}
