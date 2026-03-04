package inf112.rocketman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.rocketman.controller.RocketManController;
import inf112.rocketman.model.GameState;
import inf112.rocketman.view.GameScreen;
import inf112.rocketman.view.HomeScreen;

public class Main extends Game {
    private RocketManController controller;
    private HomeScreen homeScreen;
    private GameScreen gameScreen;
    private GameState lastState;

    @Override
    public void create() {
        controller = new RocketManController(this);

        homeScreen = new HomeScreen(this, controller);
        gameScreen = new GameScreen(this,controller);

        setScreen(new HomeScreen(this, controller));
    }

    public void render() {
        controller.handleInput();

        GameState current = controller.getState();

        if (current != lastState) {
            switch (controller.getState()) {
                case HOME_SCREEN -> setScreen(homeScreen);
                case PLAYING -> setScreen(gameScreen);
            }
            lastState = current;
        }
        super.render();
    }


    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("RocketMan");
        config.setWindowedMode(1000, 800);

        new Lwjgl3Application(new Main(), config);
    }

}