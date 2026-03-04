package inf112.rocketman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.rocketman.controller.RocketManController;
import inf112.rocketman.view.HomeScreen;

public class Main extends Game {
    private RocketManController controller;

    @Override
    public void create() {
        controller = new RocketManController(this);
        setScreen(new HomeScreen(this, controller));

    }

    public RocketManController getController() {
        return controller;
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("RocketMan");
        config.setWindowedMode(1000, 800);

        new Lwjgl3Application(new Main(), config);
    }

}