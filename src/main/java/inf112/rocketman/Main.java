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

/**
 * Main entry point for the RocketMan game.
 *
 * <p>This class sets up the model, view, controller, and screens,
 * and switches between screens based on the current game state.</p>
 */
public class Main extends Game {
    private SpriteBatch batch;
    private RocketManController controller;

    private HomeScreen homeScreen;
    private GameScreen gameScreen;
    private InstructionScreen instructionScreen;
    private PauseScreen pauseScreen;
    private GameOverScreen gameOverScreen;

    private GameState lastState = null;

    /**
     * Initializes the game, including the model, view, audio manager,
     * controller, and all screens.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();

        Preferences highscores = Gdx.app.getPreferences("Highscores");
        Preferences coins = Gdx.app.getPreferences("Coins");

        GameModel model = new GameModel(1200, 800, 5,highscores, coins);
        RocketManView view = new RocketManView();
        view.create(model.getWorldWidth(), model.getWorldHeight());

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
     * Initializes the game, including the model, view, audio manager,
     * controller, and all screens.
     */
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
     * Initializes the game, including the model, view, audio manager,
     * controller, and all screens.
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
        config.setWindowedMode(1000, 800);

        new Lwjgl3Application(new Main(), config);
    }

}