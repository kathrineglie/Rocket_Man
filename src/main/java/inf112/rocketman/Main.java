package inf112.rocketman;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.rocketman.controller.RocketManController;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("RocketMan");
        config.setWindowedMode(1000, 800);

        new Lwjgl3Application(new RocketManController(), config);
    }
}