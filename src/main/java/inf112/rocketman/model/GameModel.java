package inf112.rocketman.model;

import com.badlogic.gdx.Game;
import inf112.rocketman.controller.ControllableRocketManModel;
import inf112.rocketman.grid.IGrid;
import inf112.rocketman.view.ViewableRocketManModel;

public class GameModel implements ViewableRocketManModel, ControllableRocketManModel {
    private float playerX = 50f;
    private float playerY = 0f;
    private float playerVY = 0f;

    private final float THRUST = 4000f;
    private final float MAX_VY = 700f;
    private final float PLAYER_H = 64;
    private final float PLAYER_W = 64;

    private final float worldHeight;
    private final float worldWidth;

    private float bgScrollX = 0f;

    public GameModel(float worldHeight, float worldWidth) {
        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;
    }

    public  void update (float dt, boolean thrusting) {
        updatePlayerGravity(dt, thrusting);
        updateBackground(dt);
    }

    private void updatePlayerGravity(float dt, boolean thrusting){
        float GRAVITY = -1000f;
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

    private void updateBackground(float dt) {
        float BG_SPEED = 120f;
        bgScrollX += BG_SPEED * dt;
        if (worldWidth > 0) {
            bgScrollX = bgScrollX % worldWidth;
        } else {
            bgScrollX = 0;
        }
    }

    @Override
    public  float getPlayerX() {return playerX; }

    @Override
    public float getPlayerY() {return playerY; }

    @Override
    public float getBackgroundScrollX() {
        return bgScrollX;
    }

    @Override
    public float getPlayerWidth() {return PLAYER_W; }

    @Override
    public float getPlayerHeight() {return PLAYER_H; }
}
