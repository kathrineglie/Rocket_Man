package inf112.rocketman.model;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.controller.ControllableRocketManModel;
import inf112.rocketman.model.Obstacles.IObstacle;
import inf112.rocketman.model.Obstacles.Rockets.RandomRocketFactory;
import inf112.rocketman.model.Obstacles.Rockets.RocketFactory;
import inf112.rocketman.model.Character.TPowah;
import inf112.rocketman.view.ViewableRocketManModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameModel implements ViewableRocketManModel, ControllableRocketManModel {
    private final TPowah player;

    private final float THRUST = 4000f;
    private final float MAX_VY = 700f;
    private final float GRAVITY = -1000f;
    private final float PLAYER_X = 150f;

    private final float worldHeight;
    private final float worldWidth;
    private final float margin = 0;

    private GameState gameState = GameState.HOME_SCREEN;

    private float bgScrollX = 0f;

    // obstacles
    List<IObstacle> obstacles = new ArrayList<>();
    private RocketFactory rocketFactory = new RandomRocketFactory();
    private float rocketTimer = 0f;
    private float rocketSpawnInteval = 1.5f;

    public GameModel(float worldWidth, float worldHeight) {
        float pWidth = worldWidth/13;
        float pHeight= worldHeight/7;
        player = new TPowah(PLAYER_X, 0, pWidth, pHeight);
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    @Override
    public float getWorldHeight(){
        return worldHeight;
    }

    @Override
    public float getWorldWidth(){
        return worldWidth;
    }

    public  void update (float dt, boolean thrusting) {
        if (gameState != GameState.PLAYING){
            return;
        }

        player.update(dt, thrusting, worldHeight, THRUST, GRAVITY, MAX_VY);
        updateBackground(dt);
        updateRocket(dt);
        if (checkCollisions()) {
            gameState = GameState.GAME_OVER;
        }
    }

    private void updateRocket(float dt) {
        rocketTimer -= dt;
        if (rocketTimer <= 0) {
            obstacles.add(rocketFactory.newRocket(worldWidth, worldHeight, margin));
            rocketTimer = rocketSpawnInteval;
        }

        Iterator<IObstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            IObstacle obstacle = iterator.next();
            obstacle.update(dt);
            if (obstacle.isOfScreen(worldWidth, worldHeight)) {
                iterator.remove();
            }
        }
     }

     public List<IObstacle> getObstacles() {
        return obstacles;
     }

    private boolean checkCollisions() {
        Rectangle playerHitbox = getPlayerHitbox();
        for (IObstacle obstacle : obstacles) {
            Rectangle obstacleHitbox = obstacle.getHitBox();
            if (obstacleHitbox == null) {
                continue;
            }
            if (playerHitbox.overlaps(obstacleHitbox)) {
                return true;
            }
        }
        return false;
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
    public float getBackgroundScrollX() {
        return bgScrollX;
    }

    public Rectangle getPlayerHitbox() {
        return new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }

    @Override
    public TPowah getPlayer() {
        return player;
    }

    @Override
    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void startGame(){
        gameState = GameState.PLAYING;
    }

    @Override
    public void goToHomescreen(){
        gameState = gameState.HOME_SCREEN;
    }

    @Override
    public void pauseGame(){}
    @Override
    public void resumeGame(){}

    @Override
    public void endGame(){}

}
