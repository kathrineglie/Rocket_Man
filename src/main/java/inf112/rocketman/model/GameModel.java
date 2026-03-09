package inf112.rocketman.model;


import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.controller.ControllableRocketManModel;
import inf112.rocketman.model.Obstacles.IObstacle;
import inf112.rocketman.model.Obstacles.Rockets.RandomRocketFactory;
import inf112.rocketman.model.Obstacles.Rockets.RocketFactory;
import inf112.rocketman.model.Character.TPowah;
import inf112.rocketman.model.PowerUps.PowerUp;
import inf112.rocketman.view.ViewableRocketManModel;
import inf112.rocketman.model.PowerUps.Bird;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameModel implements ViewableRocketManModel, ControllableRocketManModel {
    private final TPowah player;

    private final float THRUST = 4000f;
    private final float MAX_VY = 700f;
    private final float GRAVITY = -1000f;
    private final float PLAYER_X = 100f;
    private final float PLAYER_Y = 100f;
    private boolean thrusting;

    private final float worldHeight;
    private final float worldWidth;
    private final float margin = 0;

    private GameState gameState = GameState.HOME_SCREEN;

    private float bgScrollX = 0f;

    List<IObstacle> obstacles = new ArrayList<>();
    private RocketFactory rocketFactory = new RandomRocketFactory();
    private float rocketTimer = 0f;
    private float rocketSpawnInteval = 1.5f;

    private Bird bird;
    private boolean birdActive = false;
    private PowerUp powerUp;
    private float powerUpTimer = 0f;
    private float powerUpSpawnInterval = 8f;


    public GameModel(float worldWidth, float worldHeight) {
        float pWidth = worldWidth/13;
        float pHeight= worldHeight/7;
        player = new TPowah(PLAYER_X,PLAYER_Y , pWidth, pHeight);
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

    public  void update (float dt, boolean thrustingInput) {
        if (gameState != GameState.PLAYING){
            return;
        }

        thrusting = thrustingInput;
        if (birdActive && bird != null) {
            bird.update(dt, thrustingInput, worldHeight);
        } else {
            player.update(dt, thrustingInput, worldHeight, THRUST, GRAVITY, MAX_VY);
        }

        updateBackground(dt);
        updateRocket(dt);
        updatePowerUp(dt);
        checkPowerUpCollision();

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

     private void updatePowerUp(float dt){
        if (birdActive){
            return;
        }

        powerUpTimer -= dt;

         if (powerUp == null && powerUpTimer <= 0) {
             float width = 60f;
             float height = 60f;
             float x = worldWidth;
             float y = 250f + (float)Math.random() * (worldHeight - 400f);
             float vx = -250f;

             powerUp = new PowerUp(x, y, width, height, vx);
             powerUpTimer = powerUpSpawnInterval;
         }

         if (powerUp != null) {
             powerUp.update(dt);

             if (powerUp.isOfScreen(worldWidth, worldHeight)) {
                 powerUp = null;
             }
         }
     }

    public List<IObstacle> getObstacles() {
        return obstacles;
     }

    private void checkPowerUpCollision() {
        if (powerUp == null) {
            return;
        }

        Rectangle playerHitbox = getPlayerHitbox();
        if (playerHitbox.overlaps(powerUp.getHitBox())) {
            activateBirdPowerUp();
            powerUp = null;
        }
    }

    private void activateBirdPowerUp() {
        bird = new Bird(
                player.getX(),
                player.getY(),
                player.getWidth(),
                player.getHeight(),
                170f
        );
        birdActive = true;
    }

    private boolean checkCollisions() {
        Rectangle playerHitbox = getActiveCharacterHitbox();
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

    private Rectangle getActiveCharacterHitbox() {
        if (birdActive && bird != null) {
            return new Rectangle(bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight());
        }
        return new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
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
    public boolean isThrusting(){
        return thrusting;
    }

    @Override
    public PowerUp getPowerUp() {
        return powerUp;
    }

    @Override
    public boolean hasBirdPowerUp() {
        return birdActive;
    }

    @Override
    public Bird getBird() {
        return bird;
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
