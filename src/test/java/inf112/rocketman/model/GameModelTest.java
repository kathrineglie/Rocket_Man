package inf112.rocketman.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
