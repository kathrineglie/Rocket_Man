package inf112.rocketman.model;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.controller.ControllableRocketManModel;
import inf112.rocketman.model.Coins.Coin;
import inf112.rocketman.model.Coins.CoinManager;
import inf112.rocketman.model.Coins.RandomCoinFactory;
import inf112.rocketman.model.Obstacles.Flames.Flame;
import inf112.rocketman.model.Obstacles.Flames.RandomFlameFactory;
import inf112.rocketman.model.Obstacles.IObstacle;
import inf112.rocketman.model.Obstacles.Lazers.Lazer;
import inf112.rocketman.model.Obstacles.Lazers.RandomLazerFactory;
import inf112.rocketman.model.Obstacles.ObstacleManager;
import inf112.rocketman.model.Obstacles.Rockets.RandomRocketFactory;
import inf112.rocketman.model.Character.TPowah;
import inf112.rocketman.model.PowerUps.*;
import inf112.rocketman.view.ViewableRocketManModel;

import java.util.*;

public class GameModel implements ViewableRocketManModel, ControllableRocketManModel {
    private final float worldHeight;
    private final float worldWidth;
    private final float margin;

    private GameState gameState = GameState.HOME_SCREEN;
    private String playerName = "";

    private final TPowah player;

    private int difficulty = 1;
    private static final int MAX_DIFFICULTY = 5;

    private static final float PLAYER_X = 150f;
    private static final float PLAYER_Y = 120f;
    private static final float GROUND = 120f;

    private boolean usingJetpack;

    private static final float START_BG_SPEED = -350f;
    private float bgSpeed = START_BG_SPEED;
    private float bgScrollX = 0f;

    private static final float START_ROCKET_SPEED = -550;
    private float rocketSpeed = START_ROCKET_SPEED;
    private static final float MAX_ROCKET_SPEED = - 1400f;

    private static final float START_GAME_SCORE_TIMER = 0.3f;
    private int gameScore = 0;
    private float scoreTickTimer = START_GAME_SCORE_TIMER;
    private float scoreInterval = 0.1f;

    private final PlayerProgressManager progressManager;
    private final CoinManager coinManager = new CoinManager(new RandomCoinFactory());
    private final PowerUpManager powerUpManager = new PowerUpManager(new RandomPowerUpFactory());
    private final ObstacleManager obstacleManager = new ObstacleManager(new RandomRocketFactory(), new RandomLazerFactory(), new RandomFlameFactory());

    public GameModel(float worldWidth, float worldHeight, float margin, Preferences highscores, Preferences coins) {
        float pWidth = worldWidth/13;
        float pHeight= worldHeight/7;
        player = new TPowah(PLAYER_X,PLAYER_Y , pWidth, pHeight, GROUND);
        player.setPowerUp(PowerUpType.NORMAL);

        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.margin = margin;

        this.progressManager = new PlayerProgressManager(highscores, coins);

    }

    /**
     * Updates all of the characters, the background, the game state. Everything that has to do with the game logic
     *
     * @param dt  dt time passed since the last frame
     * @param movingUpward True if the player is currently applying thrust to the rocket.
     */
    public void update (float dt, boolean movingUpward) {
        if (gameState != GameState.PLAYING){
            return;
        }

        updateDifficulty();
        usingJetpack = movingUpward;
        player.update(dt, usingJetpack, worldHeight);

        updateBackground(dt);
        obstacleManager.update(dt, worldWidth, worldHeight, GROUND, margin, difficulty, bgSpeed, rocketSpeed);
        powerUpManager.update(dt, player, worldWidth, worldHeight, GROUND, margin, bgSpeed);

        if (powerUpManager.checkCollision(player)){
            obstacleManager.clear();
        }

        handleObstacleCollision();

        coinManager.update(dt, getPlayerHitbox(), worldWidth, worldHeight, GROUND, margin, bgSpeed);

        if (scoreTickTimer <= 0f) {
            gameScore++;
            scoreTickTimer = scoreInterval;
        } else {
            scoreTickTimer -= dt;
        }

    }

    @Override
    public void setPlayerName(String name){

        this.playerName = name;
    }


    /**
     * Handles collision of the different objects and powerups
     */
    private void handleObstacleCollision() {
        Rectangle playerHitbox = player.getHitBox();

        Iterator<IObstacle> iterator = obstacleManager.getObstacleListReference().iterator();
        while (iterator.hasNext()) {
            IObstacle obstacle = iterator.next();
            if (obstacle instanceof Lazer && ((Lazer) obstacle).getProgressionLevel() != 3) {
                continue;
            }

            if (!(obstacle instanceof Flame)) {
                Rectangle obstacleHitbox = obstacle.getHitBox();

                if (obstacleHitbox == null) {
                    continue;
                }

                if (playerHitbox.overlaps(obstacleHitbox)) {
                    if (player.hasPowerUp()) {
                        deactivatePowerUp();
                        iterator.remove();
                        obstacleManager.clear();
                        return;
                    } else {
                        handleGameOver();
                    }
                    return;
                }
            } else {
                if (flameCollision(obstacle)) {
                    iterator.remove();
                    obstacleManager.clear();
                    return;
                }
            }
        }
    }

    /**
     * Initaliesed the game and resets variables.
     */
    private void initGameState() {
        coinManager.reset();
        obstacleManager.reset();

        player.setPowerUp(PowerUpType.NORMAL);

        bgSpeed = START_BG_SPEED;
        rocketSpeed = START_ROCKET_SPEED;
        bgScrollX = 0f;


        powerUpManager.reset();

        difficulty = 1;

        gameScore = 0;
        scoreTickTimer = START_GAME_SCORE_TIMER;
        scoreInterval = START_GAME_SCORE_TIMER;
        difficulty = 1;
    }

    @Override
    public int getSavedCoinsForPlayer(String playerName) {
        return progressManager.getCoins(playerName);
    }


    @Override
    public List<Map.Entry<String, Integer>> getSortedHighScoreList() {
        return progressManager.getSortedHighScoreList();
    }

    /**
     * Handles collision for flame object separate since it is a polygon
     *
     * @param obstacle An object that the player can collide with
     */
    private boolean flameCollision(IObstacle obstacle) {
        if (!(obstacle instanceof Flame)) {
            return false;
        }

        Polygon polyHitBox = player.getPolyHitBox();

        if (Intersector.overlapConvexPolygons(((Flame) obstacle).getPolygon(), polyHitBox)) {
            if (player.hasPowerUp()) {
                deactivatePowerUp();
            } else {
                handleGameOver();
            }
            return true;
        }
        return false;
    }


    @Override
    public List<Coin> getCoinList() {
        return coinManager.getCoinList();
    }


    /**
     * Increases the speed of the game as well as increasing level which adds more obstacles as well as make them sapwn more frequently
     */
    private void increaseDifficulty() {
        // The final time it can take between new obstacles to spawn
        float FINAL_OBSTACLE_SPAWN_INTERVAL = 1f;
        float MAX_BG_SPEED = -1200f;
        float MAX_GAMESCORE_TIMER = 0.4f;

        float newSpawnInterval = Math.max(FINAL_OBSTACLE_SPAWN_INTERVAL, obstacleManager.getObstacleSpawnInterval() - 0.4f);
        obstacleManager.setObstacleSpawnInterval(newSpawnInterval);
        bgSpeed = (float) Math.max(MAX_BG_SPEED, bgSpeed - 70);
        rocketSpeed = (float) Math.max(MAX_ROCKET_SPEED, rocketSpeed - 70);
        scoreInterval = (float) Math.max(MAX_GAMESCORE_TIMER, scoreInterval - 0.05);

        obstacleManager.updateObstacleSpeeds(bgSpeed, rocketSpeed);

        difficulty ++;
    }

    /**
     * Updates the obstacle timer depending on how far you are in the game
     */
    private void updateDifficulty() {
        if (gameScore > difficulty * 100 && difficulty != MAX_DIFFICULTY) {
            increaseDifficulty();
        }
    }


    /**
     * Gets a list of the obstacles
     *
     * @return current obstacles active in the game
     */
    @Override
    public List<IObstacle> getObstacles() {
        return obstacleManager.getObstacles();
     }


    /**
     * Deactivates the current powerup
     */
    private void deactivatePowerUp(){
         player.setPowerUp(PowerUpType.NORMAL);
         player.setVy(0);
    }

    /**
     * Updates the background to the delta time
     *
     * @param dt
     */
    private void updateBackground(float dt) {
        bgScrollX += bgSpeed * dt;
        if (worldWidth > 0) {
            bgScrollX = bgScrollX % worldWidth;
        } else {
            bgScrollX = 0;
        }
    }

    @Override
    public boolean hasGravitySuitPowerUp() {
        return PowerUpType.GRAVITY_SUIT == player.getActivePowerUp();
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
    public PowerUp getPowerUp() {
        return powerUpManager.getPowerUp();
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
    public boolean usingJetpack() {
        return usingJetpack;
    }

    @Override
    public boolean onGround() {
        return player.getY() == GROUND;
    }

    @Override
    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void startNewGame(){
        initGameState();
        gameState = GameState.PLAYING;
    }

    @Override
    public void goToHomescreen(){
        obstacleManager.clear();
        powerUpManager.clear();
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
        return coinManager.getCoinCount();
    }


    @Override
    public boolean didCollectPowerUpThisFrame() {
        return powerUpManager.didCollectPowerUpThisFrame();
    }

    @Override
    public boolean didCollectCoinThisFrame() {
        return coinManager.didCollectCoinThisFrame();
    }

    public boolean isMovingUp() {
         return usingJetpack;
    }

    /**
     * For use in tests. Sets the games core directly.
     */
    protected void setGameScore(int score){
        this.gameScore = score;
    }

    /**
     * Trigger game over manually for testing of highscores.
     */
    protected void triggerGameOver(){
        handleGameOver();
    }

    @Override
    public String getPlayerName(){
        return playerName;
    }

    /**
     * Ends the current game and saves the player's progress.
     */
    private void handleGameOver() {
        gameState = GameState.GAME_OVER;
        progressManager.updateHighscores(playerName, gameScore);
        progressManager.addCoins(playerName, coinManager.getCoinCount());
    }

    public boolean hasPirateHat() {
        return progressManager.getCoins(playerName) + coinManager.getCoinCount() >= 10;
    }
}
