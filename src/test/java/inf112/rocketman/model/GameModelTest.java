package inf112.rocketman.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Coins.Coin;
import inf112.rocketman.model.PowerUps.PowerUp;
import inf112.rocketman.model.PowerUps.PowerUpType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameModelTest {
    private Preferences highscores;
    private Preferences coins;

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


    }

    @Test
    public void testHighscoresAreSorted(){
        Map<String, Integer> fakeData = new HashMap<>();
        fakeData.put("thirdPlace", 50);
        fakeData.put("firstPlace", 200);
        fakeData.put("secondPlace", 100);

        doReturn(fakeData).when(highscores).get();
        when(highscores.getInteger("thirdPlace")).thenReturn(50);
        when(highscores.getInteger("firstPlace")).thenReturn(200);
        when(highscores.getInteger("secondPlace")).thenReturn(100);

        GameModel model = new GameModel(1000, 800, 5, highscores, coins);
        List<Map.Entry<String, Integer>> sorted = model.getSortedHighScoreList();

        assertEquals("firstPlace", sorted.getFirst().getKey());
        assertEquals(200, sorted.getFirst().getValue());
        assertEquals("secondPlace", sorted.get(1).getKey());
        assertEquals(100, sorted.get(1).getValue());
        assertEquals("thirdPlace", sorted.get(2).getKey());
        assertEquals(50, sorted.get(2).getValue());

    }

    @Test
    public void testSameNameOnHighscoreBoard(){
        String playerName = "TestPlayer";
        GameModel model = new GameModel(1000, 800,5, highscores, coins);
        model.setPlayerName(playerName);

        Map<String, Integer> currentScores = new HashMap<>();
        currentScores.put(playerName, 100);

        doReturn(currentScores).when(highscores).get();
        when(highscores.getInteger(playerName, 0)).thenReturn(100);

        model.setGameScore(50);
        model.triggerGameOver();

        verify(highscores, never()).putInteger(eq(playerName), anyInt());

        clearInvocations(highscores);

        doReturn(currentScores).when(highscores).get();
        when(highscores.getInteger(playerName, 0)).thenReturn(100);

        model.setGameScore(150);
        model.triggerGameOver();

        verify(highscores).putInteger(playerName, 150);
        verify(highscores).flush();
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
    void testDifficultyIncreaseChangesBackgroundSpeed() {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);
        model.startNewGame();

        float initialScroll = model.getBackgroundScrollX();

        model.setGameScore(101);
        model.update(0.1f, false);

        float afterUpdate = model.getBackgroundScrollX();

        assertTrue(afterUpdate < initialScroll);
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
    void testDifficultyIncreaseMakesBackgroundScrollFaster() {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);
        model.startNewGame();

        float scrollBefore = model.getBackgroundScrollX();
        model.update(0.1f, false);
        float scrollAfterNormalUpdate = model.getBackgroundScrollX();

        float normalDelta = Math.abs(scrollAfterNormalUpdate - scrollBefore);

        model.setGameScore(101);

        float scrollBeforeDifficultyIncrease = model.getBackgroundScrollX();
        model.update(0.1f, false);
        float scrollAfterDifficultyIncrease = model.getBackgroundScrollX();

        float increasedDelta = Math.abs(scrollAfterDifficultyIncrease - scrollBeforeDifficultyIncrease);

        assertTrue(increasedDelta > normalDelta);
    }

    @Test
    void testDifficultyIncreaseChangesPrivateFields() throws Exception {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);
        model.startNewGame();

        var difficultyField = GameModel.class.getDeclaredField("difficulty");
        difficultyField.setAccessible(true);

        var bgSpeedField = GameModel.class.getDeclaredField("bgSpeed");
        bgSpeedField.setAccessible(true);

        int difficultyBefore = (int) difficultyField.get(model);
        float bgSpeedBefore = (float) bgSpeedField.get(model);

        model.setGameScore(101);
        model.update(0.1f, false);

        int difficultyAfter = (int) difficultyField.get(model);
        float bgSpeedAfter = (float) bgSpeedField.get(model);

        assertEquals(difficultyBefore + 1, difficultyAfter);
        assertTrue(bgSpeedAfter < bgSpeedBefore);
    }

    @Test
    void testPlayerCollectsPowerUpOnCollision() throws Exception {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);
        model.startNewGame();

        Rectangle playerHitbox = model.getPlayerHitbox();

        PowerUp overlappingPowerUp = new PowerUp(
                playerHitbox.x,
                playerHitbox.y,
                30f,
                30f,
                0f,
                PowerUpType.BIRD
        );

        var powerUpField = GameModel.class.getDeclaredField("powerUp");
        powerUpField.setAccessible(true);
        powerUpField.set(model, overlappingPowerUp);

        var method = GameModel.class.getDeclaredMethod("checkPowerUpCollision");
        method.setAccessible(true);
        method.invoke(model);

        assertEquals(PowerUpType.BIRD, model.getPlayer().getActivePowerUp());
        assertTrue(model.didCollectPowerUpThisFrame());
        assertNull(model.getPowerUp());
    }

    @Test
    void testHandleCoinCollisionIncreasesCoinCount() throws Exception {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);
        model.startNewGame();

        Rectangle playerHitbox = model.getPlayerHitbox();

        var constructor = Coin.class.getDeclaredConstructor(float.class, float.class, float.class, float.class, float.class);
        constructor.setAccessible(true);

        Coin overlappingCoin = constructor.newInstance(
                playerHitbox.x,
                playerHitbox.y,
                20f,
                20f,
                0f
        );

        var method = GameModel.class.getDeclaredMethod("handleCoinCollision", Coin.class);
        method.setAccessible(true);

        boolean collided = (boolean) method.invoke(model, overlappingCoin);

        assertTrue(collided);
        assertEquals(1, model.getCoinCount());
    }

    @Test
    void testHandleCoinCollisionReturnsFalseWhenNoOverlap() throws Exception {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);
        model.startNewGame();

        var constructor = Coin.class.getDeclaredConstructor(float.class, float.class, float.class, float.class, float.class);
        constructor.setAccessible(true);

        Coin nonOverlappingCoin = constructor.newInstance(900f, 900f, 20f, 20f, 0f);

        var method = GameModel.class.getDeclaredMethod("handleCoinCollision", Coin.class);
        method.setAccessible(true);

        boolean collided = (boolean) method.invoke(model, nonOverlappingCoin);

        assertFalse(collided);
        assertEquals(0, model.getCoinCount());
    }

    @Test
    public void testGetSavedCoinsForPlayer() {
        when(coins.getInteger("Bob", 0)).thenReturn(7);

        GameModel model = new GameModel(1000, 800, 5, highscores, coins);

        int savedCoins = model.getSavedCoinsForPlayer("Bob");

        assertEquals(7, savedCoins);
    }

    @Test
    void testGetNonOverlappingLazerReturnsLazerWhenNoOverlap() throws Exception {
        GameModel model = new GameModel(1000, 800, 5, highscores, coins);

        var method = GameModel.class.getDeclaredMethod("getNonOverlappingLazer");
        method.setAccessible(true);

        Object result = method.invoke(model);

        assertNotNull(result);
        assertTrue(result instanceof inf112.rocketman.model.Obstacles.Lazers.Lazer);
    }
}
