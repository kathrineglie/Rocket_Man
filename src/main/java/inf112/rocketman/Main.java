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
import inf112.rocketman.view.screen.*;

public class Main extends Game {
    private SpriteBatch batch;
    private RocketManController controller;

    private HomeScreen homeScreen;
    private GameScreen gameScreen;
    private InstructionScreen instructionScreen;
    private PauseScreen pauseScreen;
    private GameOverScreen gameOverScreen;

    private GameState lastState = null;

    @Override
    public void create() {
        batch = new SpriteBatch();

        Preferences highscores = Gdx.app.getPreferences("Highscores");
        Preferences coins = Gdx.app.getPreferences("Coins");

        GameModel model = new GameModel(1200, 800, 5,highscores, coins);
        RocketManView view = new RocketManView();
        view.create(model.getWorldWidth(), model.getWorldHeight());

        controller = new RocketManController(model, model, view);
        controller.create();

        homeScreen = new HomeScreen(this, controller, batch);
        gameScreen = new GameScreen(this.controller);
        instructionScreen = new InstructionScreen(this, controller, batch);
        pauseScreen= new PauseScreen(this, controller, batch );
        gameOverScreen = new GameOverScreen(this, controller, batch);

        setScreen(homeScreen);
        lastState = GameState.HOME_SCREEN;

    }

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

    public PauseScreen getPauseScreen() {
        return pauseScreen;
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("RocketMan");
        config.setWindowedMode(1000, 800);

        new Lwjgl3Application(new Main(), config);
    }

}