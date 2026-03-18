package inf112.rocketman.model;


import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.controller.ControllableRocketManModel;
import inf112.rocketman.model.Coins.Coin;
import inf112.rocketman.model.Coins.CoinFactory;
import inf112.rocketman.model.Coins.RandomCoinFactory;
import inf112.rocketman.model.Obstacles.Flames.Flame;
import inf112.rocketman.model.Obstacles.Flames.FlameFactory;
import inf112.rocketman.model.Obstacles.Flames.RandomFlameFactory;
import inf112.rocketman.model.Obstacles.IObstacle;
import inf112.rocketman.model.Obstacles.Lazers.Lazer;
import inf112.rocketman.model.Obstacles.Lazers.LazerFactory;
import inf112.rocketman.model.Obstacles.Lazers.RandomLazerFactory;
import inf112.rocketman.model.Obstacles.Obstacle;
import inf112.rocketman.model.Obstacles.Rockets.RandomRocketFactory;
import inf112.rocketman.model.Obstacles.Rockets.Rocket;
import inf112.rocketman.model.Obstacles.Rockets.RocketFactory;
import inf112.rocketman.model.Character.TPowah;
import inf112.rocketman.model.PowerUps.PowerUp;
import inf112.rocketman.model.PowerUps.PowerUpFactory;
import inf112.rocketman.model.PowerUps.PowerUpType;
import inf112.rocketman.model.PowerUps.RandomPowerUpFactory;
import inf112.rocketman.view.ViewableRocketManModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameModel implements ViewableRocketManModel, ControllableRocketManModel {
    private final TPowah player;

    private static final float PLAYER_X = 150f;
    private static final float PLAYER_Y = 100f;
    private boolean movingUp;

    private static final float MARGIN = 5f;
    private static final float BG_SPEED = -120f;

    private final float worldHeight;
    private final float worldWidth;

    private GameState gameState = GameState.HOME_SCREEN;

    private float bgScrollX = 0f;

    private final Random random = new Random();

    private final List<IObstacle> obstacles = new ArrayList<>();
    private final RocketFactory rocketFactory = new RandomRocketFactory();
    private final LazerFactory lazerFactory = new RandomLazerFactory();
    private final FlameFactory flameFactory = new RandomFlameFactory();
    private final PowerUpFactory powerUpFactory = new RandomPowerUpFactory();
    private CoinFactory coinFactory = new RandomCoinFactory();

    private float obstacleTimer = 0f;
    private static final float OBSTACLE_SPAWN_INTERVAL = 1.5f;
    private float coinTimer = 10f;
    private float coinSpwanTimer = 10f;
    private int coinCount = 0;
    List<Coin> coinList = new ArrayList<>();
    private int gameScore = 0;
    private float gameTimer = 0.5f;
    private float gameScoreTimer = 0.5f;

    private PowerUp powerUp;
    private float powerUpTimer = 0f;
    private static final float POWER_UP_SPAWN_INTERVAL = 8f;


    public GameModel(float worldWidth, float worldHeight) {
        float pWidth = worldWidth/13;
        float pHeight= worldHeight/7;
        player = new TPowah(PLAYER_X,PLAYER_Y , pWidth, pHeight);
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    /**
     * Updates all of the characters, the background, the gamestate. Everything that has to do with the gamelogic
     *
     * @param dt The delta dime (seconds) since last update.
     * @param movingUpward True if the player is currently applying thrust to the rocket.
     */
    public  void update (float dt, boolean movingUpward) {
        if (gameState != GameState.PLAYING){
            return;
        }

        movingUp = movingUpward;
        player.update(dt, movingUp, worldHeight);

        updateBackground(dt);
        updateObstacle(dt);
        updatePowerUp(dt);
        checkPowerUpCollision();
        handleObstacleCollision();
        updateCoins(dt);
        if (gameTimer <= 0f) {
            gameScore++;
            gameTimer = gameScoreTimer;
        } else {
            gameTimer -= dt;
        }
    }

    private void checkPowerUpCollision() {
        if (powerUp == null) {
            return;
        }

        Rectangle playerHitbox = player.getHitBox();
        if (playerHitbox.overlaps(powerUp.getHitBox())) {
            player.setPowerUp(powerUp.getType());
            player.setVy(0);
            powerUp = null;
        }
    }

    /**
     * Handles collision of the differnt objects and powerups
     */

    private void handleObstacleCollision() {
        Rectangle playerHitbox = player.getHitBox();

        Iterator<IObstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            IObstacle obstacle = iterator.next();
            if (isNonActiveLazer(obstacle)) {
                continue;
            }

            if (!(obstacle instanceof Flame)) {
                Rectangle obstacleHitbox = obstacle.getHitBox();

                if (obstacleHitbox == null) {
                    continue;
                }

                if (playerHitbox.overlaps(obstacleHitbox)) {
                    if (player.getActivePowerUp() == PowerUpType.BIRD) {
                        deactivateBirdPowerUp();
                        iterator.remove();
                    } else {

                        gameState = GameState.GAME_OVER;
                        gameScore = 0;
                        coinCount = 0;
                    }
                    return;
                }
            } else {
                handleFlameCollision(obstacle);
            }
        }
    }

    /**
     * Handles collision for flame object
     * @param obstacle
     */
    private void handleFlameCollision(IObstacle obstacle) {
        if (!(obstacle instanceof Flame)) {
            return;
        }

        Polygon polyHitBox = player.getPolyHitBox();

        if (Intersector.overlapConvexPolygons(((Flame) obstacle).getPolygon(), polyHitBox)) {
            if (player.getActivePowerUp() == PowerUpType.BIRD) {
                deactivateBirdPowerUp();
            } else {
                gameState = GameState.GAME_OVER;
                gameScore = 0;
                coinCount = 0;
            }
        }
    }

    public boolean isNonActiveLazer(IObstacle obstacle) {
        return obstacle instanceof Lazer && ((Lazer) obstacle).getProgressionLevel() != 3;
    }

    private void updateCoins(float dt) {
        coinTimer -= dt;
        if (coinTimer <= 0) {
            coinList.add(coinFactory.newCoin(worldWidth, worldHeight, MARGIN));
            coinTimer = coinSpwanTimer;
        }
        Iterator<Coin> iterator = coinList.iterator();
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            coin.update(dt);
            if (getPlayerHitbox().overlaps(coin.getHitbox())) {
                iterator.remove();
                coinCount += 1;
                continue;
            }
            if (coin.isOfScreen(worldWidth, worldHeight)) {
                iterator.remove();
            }
        }
    }


    public List<Coin> getCoinList() {
        return coinList;
    }

    /**
     * Checks if the obstacles are off-screen and removes them if they are
     *
     * @param dt
     */
    private void updateObstacle(float dt) {
        obstacleTimer -= dt;

        if (obstacleTimer <= 0) {
            obstacles.add(getRandomObstacle());
            obstacleTimer = OBSTACLE_SPAWN_INTERVAL;
        }

        Iterator<IObstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            IObstacle obstacle = iterator.next();
            obstacle.update(dt);

            if (obstacle instanceof Rocket && obstacle.isOfScreen(worldWidth, worldHeight)) {
                iterator.remove();
            }
            if (obstacle instanceof Lazer && ((Lazer) obstacle).getProgressionLevel() == 4) {
                iterator.remove();
            }
            if (obstacle instanceof Flame && obstacle.isOfScreen(worldWidth, worldHeight)) {
                iterator.remove();
            }
        }
    }

    /**
     * This method gets a random obstacle of the ones listed in the cases
     *
     * @return returns a random obstacle of the specified obstacles
     */
    private Obstacle getRandomObstacle() {
        int randNum = random.nextInt(1, 4);
        return switch (randNum) {
            case 1 -> rocketFactory.newRocket(worldWidth, worldHeight, MARGIN);
            case 2 -> lazerFactory.newLazer(worldWidth, worldHeight, MARGIN);
            case 3 -> flameFactory.newFlame(worldWidth, worldHeight, MARGIN, BG_SPEED);
            default -> throw new RuntimeException("No object was chosen. The random number was: " + randNum);
        };
    }

    /**
     * Gets a list of the obstacles
     *
     * @return
     */
     public List<IObstacle> getObstacles() {
        return obstacles;
     }

    private void updatePowerUp(float dt){
        if (player.getActivePowerUp() != PowerUpType.NORMAL) {
            return;
        }

        powerUpTimer -= dt;

        if (powerUp == null && powerUpTimer <= 0){
            powerUp = powerUpFactory.newPowerUp(worldWidth, worldHeight);
            powerUpTimer = POWER_UP_SPAWN_INTERVAL;
        }

        if (powerUp != null) {
            powerUp.update(dt);

            if (powerUp.isOfScreen(worldWidth, worldHeight)) {
                powerUp = null;
            }
        }
    }


    private void deactivateBirdPowerUp(){
         player.setPowerUp(PowerUpType.NORMAL);
         player.setVy(0);
    }


    private void updateBackground(float dt) {
        bgScrollX += BG_SPEED * dt;
        if (worldWidth > 0) {
            bgScrollX = bgScrollX % worldWidth;
        } else {
            bgScrollX = 0;
        }
    }

    @Override
    public float getWorldHeight(){
        return worldHeight;
    }

    @Override
    public float getWorldWidth(){
        return worldWidth;
    }

    @Override
    public float getBackgroundScrollX() {
        return bgScrollX;
    }

    public Rectangle getPlayerHitbox() {
        return player.getHitBox();
    }

    @Override
    public boolean isMovingUp(){
        return movingUp;
    }

    @Override
    public PowerUp getPowerUp() {
        return powerUp;
    }

    @Override
    public boolean hasBirdPowerUp() {
        return player.getActivePowerUp() == PowerUpType.BIRD;
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
        obstacles.clear();

        player.setX(PLAYER_X);
        player.setY(PLAYER_Y);
        player.setVy(0);

        gameState = GameState.PLAYING;
    }

    @Override
    public void goToHomescreen(){
        obstacles.clear();
        powerUp = null;
        gameState = GameState.HOME_SCREEN;
        gameScore = 0;
    }

    @Override
    public void pauseGame(){
         this.gameState = GameState.PAUSE;
    }
    @Override
    public void resumeGame(){
         this.gameState = GameState.PLAYING;
    }

    @Override
    public void endGame(){}

    @Override
    public void showInstructions() {
        gameState = GameState.INSTRUCTIONS;
    }

    @Override
    public int getGameScore() {
         return gameScore;
    }

    @Override
    public int getCoinCount() {
         return coinCount;
    }
}
