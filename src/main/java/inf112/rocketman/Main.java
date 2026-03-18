package inf112.rocketman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.rocketman.controller.RocketManController;
import inf112.rocketman.model.GameModel;
import inf112.rocketman.model.GameState;
import inf112.rocketman.view.screen.*;

public class Main extends Game {
    private RocketManController controller;
    private HomeScreen homeScreen;
    private GameScreen gameScreen;
    private InstructionScreen instructionScreen;
    private PauseScreen pauseScreen;
    private GameState lastState = null;

    @Override
    public void create() {
        GameModel model = new GameModel(1200, 800);

        controller = new RocketManController(model, model);
        controller.create();

        homeScreen = new HomeScreen(this, controller);
        gameScreen = new GameScreen(this,controller);
        instructionScreen = new InstructionScreen(this, controller);
        pauseScreen= new PauseScreen(this, controller);

        pauseScreen.show();

        setScreen(homeScreen);
        lastState = GameState.HOME_SCREEN;

    }

    public void render() {

        GameState current = controller.getState();

        if (current != lastState) {
            switch (controller.getState()) {
                case HOME_SCREEN -> setScreen(homeScreen);
                case PLAYING -> setScreen(gameScreen);
                case GAME_OVER -> setScreen(new GameOverScreen(this, controller));
                case INSTRUCTIONS -> setScreen(instructionScreen);
                //case PAUSE -> setScreen(pauseScreen);
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