package inf112.rocketman.model;

import com.badlogic.gdx.Game;
import inf112.rocketman.model.Coins.Coin;
import inf112.rocketman.model.Coins.RandomCoinFactory;
import inf112.rocketman.model.Obstacles.Rockets.RandomRocketFactory;
import inf112.rocketman.model.Obstacles.Rockets.Rocket;
import inf112.rocketman.model.PowerUps.PowerUpType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {
    @Test
    public void testPauseGameChangesState() {
        GameModel model = new GameModel(1000, 800);
        model.startGame();

        model.pauseGame();

        assertEquals(GameState.PAUSE, model.getGameState());
    }

    @Test
    public void testResumeGameChangesStateBackToPlaying() {
        GameModel model = new GameModel(1000, 800);
        model.pauseGame();

        model.resumeGame();

        assertEquals(GameState.PLAYING, model.getGameState());
    }

    @Test
    public void testGoToHomeScreen() {
        GameModel model = new GameModel(1000, 800);
        model.startGame();
        model.goToHomescreen();
        assertEquals(GameState.HOME_SCREEN, model.getGameState());
    }

    @Test
    public void testShowInstructions() {
        GameModel model = new GameModel(1000, 800);
        model.showInstructions();
        assertEquals(GameState.INSTRUCTIONS, model.getGameState());
    }

    @Test
    public void testPositionDoesNotChangeWhenPaused() {
        GameModel model = new GameModel(1000, 800);
        model.startGame();
        model.pauseGame();

        float initialY = model.getPlayer().getY();

        model.update(0.01f, true);

        assertEquals(initialY, model.getPlayer().getY());
    }

    @Test
    public void testObstaclesClearedOnStartGame() {
        GameModel model = new GameModel(1000, 800);
        model.startGame();

        model.update(2.0f, false);

        model.startGame();

        assertEquals(0, model.getObstacles().size(), "Obstacle list should be empty on restart");
    }

    @Test
    public void testInitialStateIsHomeScreen() {
        GameModel model = new GameModel(1000, 800);
        assertEquals(GameState.HOME_SCREEN, model.getGameState());
    }

    @Test
    public void testTogglePauseMultipleTimes() {
        GameModel model = new GameModel(1000, 800);
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
        GameModel model = new GameModel(1000, 800);
        model.startGame();

        int initialScore = model.getGameScore();

        model.update(1.0f, false);
        model.update(0.01f, false);

        int scoreAfterTime =model.getGameScore();

        assertTrue(scoreAfterTime > initialScore, "Score should increase after 1 second");
    }

    @Test
    public void testBackgroundScrolling() {
        GameModel model = new GameModel(1000, 800);
        model.startGame();

        float initialScroll = model.getBackgroundScrollX();

        model.update(0.1f, false);

        assertNotEquals(initialScroll, model.getBackgroundScrollX(), "Background should move when game is updates");

        assertTrue(model.getBackgroundScrollX() < initialScroll, "Background should scroll to the left (negative direction)");
    }

    @Test
    public void testUpdateDoesNothingWhenGameIsNotPlaying() {
        GameModel model = new GameModel(1000, 800);

        float initialScroll = model.getBackgroundScrollX();
        model.update(1.0f, false);

        assertEquals(initialScroll, model.getBackgroundScrollX(), "Background should not scroll in Home Screen");
    }

    @Test
    public void testPlayerMovesWhenMovingUpward() {
        GameModel model = new GameModel(1000, 800);
        model.startGame();

        float initialY = model.getPlayer().getY();

        model.update(0.1f, true);

        assertTrue(model.getPlayer().getY() > initialY, "Player should have moved up from initial position");
    }

    @Test
    public void testScoreResetsOnStartGame() {
        GameModel model = new GameModel(1000, 800);
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
        GameModel model = new GameModel(1000, 800);
        model.startGame();

        assertFalse(model.hasBirdPowerUp(), "Should not have bird power-up at start");

        model.getPlayer().setPowerUp(PowerUpType.BIRD);

        assertTrue(model.hasBirdPowerUp(), "hasBirdPowerUp should return true when player has BIRD type");
    }

    @Test
    public void testNoUpdateDuringInstruction() {
        GameModel model = new GameModel(1000, 800);
        model.startGame();
        model.showInstructions();

        float initialScroll = model.getBackgroundScrollX();
        model.update(1.0f, false);

        assertEquals(initialScroll, model.getBackgroundScrollX(), "Background should not move while showing instructions");
    }

    @Test
    public void testStartGameResetsPlayerPosition() {
        GameModel model = new GameModel(1000, 800);
        model.startGame();

        model.getPlayer().setX(400);
        model.getPlayer().setY(300);
        model.getPlayer().setVy(50);

        model.startGame();

        assertEquals(150f, model.getPlayer().getX());
        assertEquals(100f, model.getPlayer().getY());
        assertEquals(0f, model.getPlayer().getVY());
    }

    @Test
    public void testStartGameChangesStateToPlaying() {
        GameModel model = new GameModel(1000, 800);

        model.startGame();

        assertEquals(GameState.PLAYING, model.getGameState());
    }

    @Test
    public void testGoToHomeScreenRemovesPowerUp() {
        GameModel model = new GameModel(1000, 800);
        model.startGame();

        model.getPlayer().setPowerUp(PowerUpType.BIRD);
        model.goToHomescreen();

        assertEquals(GameState.HOME_SCREEN, model.getGameState());
        assertNull(model.getPowerUp());
    }

    @Test
    public void testIsMovingUpReflectsUpdateInput() {
        GameModel model = new GameModel(1000, 800);
        model.startGame();

        model.update(0.1f, true);
        assertTrue(model.isMovingUp());

        model.update(0.1f, false);
        assertFalse(model.isMovingUp());
    }

    @Test
    public void testSCoreDoesNotIncreaseWhenPaused() {
        GameModel model = new GameModel(1000, 800);
        model.startGame();
        model.pauseGame();

        int initialScore = model.getGameScore();

        model.update(1.0f, false);
        model.update(1.0f, false);

        assertEquals(initialScore, model.getGameScore());
    }

}
