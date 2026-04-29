package inf112.rocketman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.rocketman.controller.RocketManController;
import inf112.rocketman.model.GameModel;
import inf112.rocketman.model.GameState;
import inf112.rocketman.view.RocketManView;
import inf112.rocketman.view.assets.RocketManAudio;
import inf112.rocketman.view.screen.*;
import inf112.rocketman.model.WorldDimensions;

/**
 * Main entry point for the RocketMan game.
 *
 * <p>This class sets up the model, view, controller, and screens,
 * and switches between screens based on the current game state.</p>
 */
public class Main extends Game {
    private RocketManController controller;

    private HomeScreen homeScreen;
    private GameScreen gameScreen;
    private InstructionScreen instructionScreen;
    private PauseScreen pauseScreen;
    private GameOverScreen gameOverScreen;

    private GameState lastState = null;

    private static final float WORLD_WIDTH = 1200;
    private static final float WORLD_HEIGHT = 800;
    private static final float MARGIN = 50;

    /**
     * Initializes the game, including the model, view, audio manager,
     * controller, and all screens.
     */
    @Override
    public void create() {
        SpriteBatch batch = new SpriteBatch();

        Preferences highscores = Gdx.app.getPreferences("Highscore");
        Preferences coins = Gdx.app.getPreferences("Coins");

        GameModel model = new GameModel(new WorldDimensions(WORLD_WIDTH, WORLD_HEIGHT), MARGIN,highscores, coins);
        RocketManView view = new RocketManView();
        view.create(model.getWorldDimensions().worldWidth(), model.getWorldDimensions().worldHeight());

        RocketManAudio audio = new RocketManAudio();
        audio.create();

        controller = new RocketManController(model, model, view, audio);
        controller.create();

        homeScreen = new HomeScreen(this, controller, batch);
        gameScreen = new GameScreen(this.controller);
        instructionScreen = new InstructionScreen(this, controller, batch);
        pauseScreen= new PauseScreen(this, controller, batch );
        gameOverScreen = new GameOverScreen(this, controller, batch);

        setScreen(homeScreen);
        lastState = GameState.HOME_SCREEN;

    }

    /**
     * Renders the active screen and switches screen when the game state changes.
     */
    @Override
    public void render() {

        GameState current = controller.getState();

        if (current != lastState) {
            switch (controller.getState()) {
                case HOME_SCREEN -> setScreen(homeScreen);
                case PLAYING -> setScreen(gameScreen);
                case GAME_OVER -> setScreen(gameOverScreen);
                case INSTRUCTIONS -> setScreen(instructionScreen);
                case PAUSE -> setScreen(pauseScreen);
            }
            lastState = current;
        }
        super.render();
    }

    /**
     * Returns the pause screen.
     *
     * @return the pause screen
     */
    public PauseScreen getPauseScreen() {
        return pauseScreen;
    }

    /**
     * Launches the RocketMan desktop application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("RocketMan");
        config.setWindowedMode((int) WORLD_WIDTH, (int) WORLD_HEIGHT);

        new Lwjgl3Application(new Main(), config);
    }
}