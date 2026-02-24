package inf112.rocketman.model;

import inf112.rocketman.controller.ControllableRocketManModel;
import inf112.rocketman.view.ViewableRocketManModel;

public class GameModel implements ViewableRocketManModel, ControllableRocketManModel {
    private float playerX = 100f;
    private float playerY = 300f;
    private float playerVY = 0f;

    private final float GRAVITY = -1000f;
    private final float THRUST = 3000f;
    private final float MAX_VY = 700f;

    private final float PLAYER_H = 64;

    private float worldHeight;

    public GameModel(float worldHeight) {
        this.worldHeight = worldHeight;
    }

    public  void update (float dt, boolean thrusting) {
        float ay = GRAVITY + (thrusting ? THRUST : 0f);
        playerVY += ay * dt;

        if (playerVY > MAX_VY) playerVY = MAX_VY;
        if (playerVY < -MAX_VY) playerVY = -MAX_VY;

        playerY += playerVY * dt;

        if (playerY < 0) {
            playerY = 0;
            playerVY = 0;
        }

        if (playerY > worldHeight - PLAYER_H) {
            playerY = worldHeight - PLAYER_H;
            playerVY = 0;
        }
    }

    public  float getPlayerX() {return playerX; }
    public float getPlayerY() {return playerY; }
}
