package inf112.rocketman.model;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.controller.ControllableRocketManModel;
import inf112.rocketman.model.coins.*;
import inf112.rocketman.model.difficulty.DifficultyController;
import inf112.rocketman.model.obstacles.*;
import inf112.rocketman.model.character.TPowah;
import inf112.rocketman.model.character.ViewableTPowah;
import inf112.rocketman.model.powerups.*;
import inf112.rocketman.model.progress.PlayerProgressManager;

import inf112.rocketman.view.ViewableRocketManModel;

import java.util.*;

/**
 * The game model is responsible for managing the overall game state and logic.
 *
 * This class updates the player, obstacles, coins, powerups and difficulty progression for the game.
 * It also handles the different game states and when to switch between the different ones.
 * This includes starting, pausing and ending the game
 *
 * The game model acts as the central controller for the game logic, while delegating specific
 * responsibilities to the dedicated manager classes.
 */
public class GameModel implements ViewableRocketManModel, ControllableRocketManModel {
    private final float worldHeight;
    private final float worldWidth;
    private final float margin;

    private GameState gameState = GameState.HOME_SCREEN;

    private String playerName = "";

    private final TPowah player;
    private boolean usingJetpack;
    private static final float PLAYER_X = 150f;
    private static final float PLAYER_Y = 120f;
    private static final float GROUND = 120f;

    private float bgScrollX = 0f;

    private static final float START_GAME_SCORE_TIMER = 0.3f;
    private float scoreTickTimer = START_GAME_SCORE_TIMER;
    private int gameScore = 0;

    private final PlayerProgressManager progressManager;
    private final DifficultyController difficultyManager;
    private final ObstacleCollisionHandler collisionManager;
    private final CoinManager coinManager = new CoinManager(new RandomCoinFactory());
    private final PowerUpManager powerUpManager = new PowerUpManager(new RandomPowerUpFactory());
    private final ObstacleManager obstacleManager = new ObstacleManager(new RandomObstacleFactory());

    public GameModel(float worldWidth, float worldHeight, float margin, Preferences highscores, Preferences coins) {
        float pWidth = worldWidth/13;
        float pHeight= worldHeight/7;
        player = new TPowah(PLAYER_X,PLAYER_Y , pWidth, pHeight, GROUND);
        player.setPowerUp(PowerUpType.NORMAL);

        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.margin = margin;

        this.difficultyManager = new DifficultyController(obstacleManager);
        this.collisionManager = new ObstacleCollisionHandler(player, obstacleManager);
        this.progressManager = new PlayerProgressManager(highscores, coins);
    }

    /**
     * Updates the character, the background, the game state. Everything that has to do with the game logic
     *
     * @param dt  the time passed since the last frame (delta time)
     * @param movingUpward True if the player is currently applying thrust to the rocket.
     */
    public void update (float dt, boolean movingUpward) {
        if (gameState != GameState.PLAYING){
            return;
        }

        difficultyManager.updateDifficulty(gameScore);

        usingJetpack = movingUpward;
        player.update(dt, usingJetpack, worldHeight, margin);

        updateBackground(dt);
        updateObstacles(dt);
        updatePowerUps(dt);
        handlePowerUpCollection();
        handleObstacleHit();
        updateCoins(dt);
        updateScore(dt);
    }

    /**
     * Manages what should happen if there is a collision between the obstacles and the player
     * This does nothing if there is no collision between the player and an obstacle
     */
    private void handleObstacleHit() {
        boolean objectCollision = collisionManager.handleObstacleCollision();
        if (objectCollision && !player.hasPowerUp()) {
            handleGameOver();
        } else if (objectCollision && player.hasPowerUp()){
            obstacleManager.clear();
            player.setPowerUp(PowerUpType.NORMAL);
            player.setVy(0);
        }
    }

    /**
     * This updates the score for the current game session
     * The score will update faster when the difficulty increases.
     * When the difficulty increases, the score interval will decrease in the difficulty manager
     *
     * @param dt the time passed since the last frame (delta time)
     */
    private void updateScore(float dt) {
        if (scoreTickTimer <= 0f) {
            gameScore++;
            scoreTickTimer = difficultyManager.getScoreInterval();
        } else {
            scoreTickTimer -= dt;
        }
    }

    /**
     * Updates the obstacles to the background
     *
     * @param dt the time passed since the last frame (delta time)
     */
    private void updateObstacles(float dt) {
        obstacleManager.update(
                dt,
                worldWidth,
                worldHeight,
                GROUND,
                margin,
                difficultyManager.getDifficulty(),
                difficultyManager.getBgSpeed(),
                difficultyManager.getRocketSpeed()
        );
    }

    /**
     * Updates the powerups to the background
     *
     * @param dt the time passed since the last frame (delta time)
     */
    private void updatePowerUps(float dt) {
        powerUpManager.update(
                dt,
                player,
                worldWidth,
                worldHeight,
                GROUND,
                margin,
                difficultyManager.getBgSpeed()
        );
    }

    /**
     * Updates the coins to the background
     *
     * @param dt the time passed since the last frame (delta time)
     */
    private void updateCoins(float dt) {
        coinManager.update(
                dt,
                getPlayerHitbox(),
                worldWidth,
                worldHeight,
                GROUND,
                margin,
                difficultyManager.getBgSpeed());

    }

    /**
     * Checks if the player is collecting a powerup box
     */
    private void handlePowerUpCollection() {
        if (powerUpManager.checkCollision(player)){
            obstacleManager.clear();
        }
    }

    @Override
    public void setPlayerName(String name){
        this.playerName = name;
    }

    /**
     * Resets the game to its initial state
     */
    private void resetGameState() {
        coinManager.reset();
        obstacleManager.reset();
        powerUpManager.reset();

        player.setPowerUp(PowerUpType.NORMAL);

        bgScrollX = 0f;

        gameScore = 0;
        scoreTickTimer = START_GAME_SCORE_TIMER;

        difficultyManager.resetDifficulty();
    }

    @Override
    public int getSavedCoinsForPlayer(String playerName) {
        return progressManager.getCoins(playerName);
    }

    @Override
    public List<Map.Entry<String, Integer>> getSortedHighScoreList() {
        return progressManager.getSortedHighScoreList();
    }

    @Override
    public List<Coin> getCoinList() {
        return coinManager.getCoinList();
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
     * Updates the background scroll based on the elapsed time
     * and current background speed.
     *
     * @param dt the time passed since the last frame (delta time)
     */
    private void updateBackground(float dt) {
        bgScrollX += difficultyManager.getBgSpeed() * dt;
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

    /**
     * Gets the hitbox of the current player.
     *
     * @return a hitbox of the current player
     */
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
    public ViewableTPowah getPlayer() {
        return player;
    }

    @Override
    public boolean usingJetpack() {
        return usingJetpack;
    }

    @Override
    public boolean onGround() {
        return player.onGround();
    }

    @Override
    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void startNewGame(){
        resetGameState();
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
    public float getMargin() {
        return margin;
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

    /**
     * Checks if the player is currently moving up or is using the jetpack at the movement
     *
     * @return true if the character is using the jetpack or moving upwards
     */
    public boolean isMovingUp() {
         return usingJetpack;
    }

    /**
     * For use in tests. Sets the game core directly.
     */
    protected void setGameScore(int score) {
        this.gameScore = score;
    }


    @Override
    public String getPlayerName() {
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

    protected void setPlayerPowerUp(PowerUpType powerUp) {
        player.setPowerUp(powerUp);
    }
}