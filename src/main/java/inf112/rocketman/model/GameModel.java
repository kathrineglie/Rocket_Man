package inf112.rocketman.model;

import com.badlogic.gdx.Preferences;
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

import java.util.*;

public class GameModel implements ViewableRocketManModel, ControllableRocketManModel {
    private final float worldHeight;
    private final float worldWidth;
    private final float margin;

    private GameState gameState = GameState.HOME_SCREEN;
    private final Preferences highscores;

    private String playerName = "";

    private final TPowah player;

    private int difficulty = 1;
    private static final int MAX_DIFFICULTY = 5;

    private static final float PLAYER_X = 150f;
    private static final float PLAYER_Y = 120f;
    private static final float GROUND = 120f;

    private boolean usingJetpack;

    private final Random random = new Random();

    private final List<IObstacle> obstacles = new ArrayList<>();
    private final RocketFactory rocketFactory = new RandomRocketFactory();
    private final LazerFactory lazerFactory = new RandomLazerFactory();
    private final FlameFactory flameFactory = new RandomFlameFactory();
    private final PowerUpFactory powerUpFactory = new RandomPowerUpFactory();
    private final CoinFactory coinFactory = new RandomCoinFactory();

    private static final float START_BG_SPEED = -350f;
    private float bgSpeed = START_BG_SPEED;
    private float bgScrollX = 0f;

    private static final float START_ROCKET_SPEED = -550;
    private float rocketSpeed = START_ROCKET_SPEED;
    private static final float MAX_ROCKET_SPEED = - 1400f;


    private static final int MAX_LAZER_SPAWN_ATTEMPTS = 10;

    private static final int NUM_OBSTACLES = 3;
    private float obstacleTimer = 0f; // Timer that counts down until the next obstacle can spawn
    private static final float START_OBSTACLE_SPAWN_INTERVAL = 2.5f;
    private float obstacleSpawnInterval = START_OBSTACLE_SPAWN_INTERVAL;

    private float coinTimer = 10f;
    private int coinCount = 0;
    private final List<Coin> coinList = new ArrayList<>();

    private static final float START_GAME_SCORE_TIMER = 0.3f;
    private int gameScore = 0;
    private float scoreTickTimer = START_GAME_SCORE_TIMER;
    private float scoreInterval = 0.1f;

    private PowerUp powerUp;
    private float powerUpTimer = 0f;
    private static final float MIN_POWER_UP_SPAWN_INTERVAL = 8f;
    private static final float MAX_POWER_UP_SPAWN_INTERVAL = 30f;

    private boolean collectedPowerUpThisFrame = false;
    private boolean collectedCoinThisFrame = false;

    public GameModel(float worldWidth, float worldHeight, float margin, Preferences highscores) {
        float pWidth = worldWidth/13;
        float pHeight= worldHeight/7;
        player = new TPowah(PLAYER_X,PLAYER_Y , pWidth, pHeight, GROUND);
        player.setPowerUp(PowerUpType.NORMAL);

        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.margin = margin;
        this.highscores = highscores;

        this.powerUpTimer = getRandomPowerUpSpawnInterval();
    }

    /**
     * Updates all of the characters, the background, the game state. Everything that has to do with the game logic
     *
     * @param dt  dt time passed since the last frame
     * @param movingUpward True if the player is currently applying thrust to the rocket.
     */
    public  void update (float dt, boolean movingUpward) {
        if (gameState != GameState.PLAYING){
            return;
        }
        updateDifficulty();
        usingJetpack = movingUpward;
        player.update(dt, usingJetpack, worldHeight);

        collectedPowerUpThisFrame = false;
        collectedCoinThisFrame = false;
        updateBackground(dt);
        updateObstacle(dt);
        updatePowerUp(dt);

        checkPowerUpCollision();
        handleObstacleCollision();
        updateCoins(dt);

        if (scoreTickTimer <= 0f) {
            gameScore++;
            scoreTickTimer = scoreInterval;
        } else {
            scoreTickTimer -= dt;
        }
    }

    @Override
    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }


    /**
     *Returns a random delay before the next power-up can spawn
     * @return a random spawn interval between the minimum and maximum limit
     */
    private float getRandomPowerUpSpawnInterval(){
        return random.nextFloat(MIN_POWER_UP_SPAWN_INTERVAL, MAX_POWER_UP_SPAWN_INTERVAL);
    }

    /**
     * Checks if the player overlaps with the powerup box
     */
    private void checkPowerUpCollision() {
        if (powerUp == null) {
            return;
        }

        Rectangle playerHitbox = player.getHitBox();
        if (playerHitbox.overlaps(powerUp.getHitBox())) {
            player.setPowerUp(powerUp.getType());
            collectedPowerUpThisFrame = true;
            player.setVy(0);
            powerUp = null;
            powerUpTimer = getRandomPowerUpSpawnInterval();
            obstacles.clear();
        }
    }

    /**
     * Handles collision of the different objects and powerups
     */
    private void handleObstacleCollision() {
        Rectangle playerHitbox = player.getHitBox();

        Iterator<IObstacle> iterator = obstacles.iterator();
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
                        obstacles.clear();
                        return;
                    } else {
                        gameState = GameState.GAME_OVER;
                    }
                    return;
                }
            } else {
                if (flameCollision(obstacle)) {
                    iterator.remove();
                    obstacles.clear();
                    return;
                }
            }
        }
    }

    /**
     * Initaliesed the game and resets variables.
     */
    private void initGameState() {
        obstacles.clear();
        coinList.clear();

        bgSpeed = START_BG_SPEED;
        rocketSpeed = START_ROCKET_SPEED;
        bgScrollX = 0f;

        obstacleSpawnInterval = START_OBSTACLE_SPAWN_INTERVAL;
        obstacleTimer = 0f;

        coinTimer = 10f;

        powerUp = null;
        powerUpTimer = getRandomPowerUpSpawnInterval();

        difficulty = 1;

        gameScore = 0;
        scoreTickTimer = START_GAME_SCORE_TIMER;
        scoreInterval = START_GAME_SCORE_TIMER;

        player.setPowerUp(PowerUpType.NORMAL);
        player.setX(PLAYER_X);
        player.setY(PLAYER_Y);
        player.setVy(0);
    }



    /**
     * Updates the saved highscores
     * This method makes sure that the preference @highScores contain the 5 best scores seen so far
     */
    private void updateHighscores(String playerName){
        Map<String, ?> allScores = highscores.get();

        int oldScore = highscores.getInteger(playerName, 0);
        if (gameScore <= oldScore && allScores.containsKey(playerName)){
            return;
        }

        if (allScores.size() < 5){
            highscores.putInteger(playerName, gameScore);
            highscores.flush();
            return;
        }

        String playerWithLowestScore = null;
        int lowestScore = Integer.MAX_VALUE;

        for (String key : allScores.keySet()){
            int score = highscores.getInteger(key);
            if (score < lowestScore){
                lowestScore = score;
                playerWithLowestScore = key;
            }
        }

        if (gameScore > lowestScore){
            highscores.remove(playerWithLowestScore);
            highscores.putInteger(playerName, gameScore);
            highscores.flush();
        }
    }

    @Override
    public List<Map.Entry<String,Integer>> getSortedHighScoreList(){
        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>();

        for (String key : highscores.get().keySet()){
            sortedScores.add(Map.entry(key, highscores.getInteger(key)));
        }

        sortedScores.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        return sortedScores;
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
                gameState = GameState.GAME_OVER;
            }
            return true;
        }
        return false;
    }

    /**
     * Updates coins
     *
     * @param dt dt time passed since the last frame
     */
    private void updateCoins(float dt) {
        coinTimer -= dt;
        if (coinTimer <= 0) {
            coinList.add(coinFactory.newCoin(worldWidth, worldHeight, GROUND, margin, (float) bgSpeed));
            coinTimer = random.nextFloat(3f, 10f);
        }
        Iterator<Coin> iterator = coinList.iterator();
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            coin.update(dt);
            coin.setVX(bgSpeed);

            if (handleCoinCollision(coin)) {
                iterator.remove();
            }

            if (coin.isOfScreen(worldHeight, margin)) {
                iterator.remove();
            }
        }
    }

    /**
     * Handles the collision of a coin and the player.
     *
     * @param coin takes a coin in and checks if it overlaps with the player hitbox
     * @return true if the coin and player hitbox overlaps
     */
    private boolean handleCoinCollision(Coin coin) {
        if (getPlayerHitbox().overlaps(coin.getHitbox())) {
            collectedCoinThisFrame = true;
            coinCount += 1;
            return true;
        }
        return false;
    }

    public List<Coin> getCoinList() {
        return new ArrayList<>(coinList);
    }

    /**
     * Checks if the obstacles are off-screen and removes them if they are
     *
     * @param dt dt time passed since the last frame
     */
    private void updateObstacle(float dt) {
        obstacleTimer -= dt;

        if (obstacleTimer <= 0) {
            obstacles.add(getRandomObstacle());
            obstacleTimer = obstacleSpawnInterval;
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
     * Increases the speed of the game as well as increasing level which adds more obstacles as well as make them sapwn more frequently
     */
    private void increaseDifficulty() {
        // The final time it can take between new obstacles to spawn
        float FINAL_OBSTACLE_SPAWN_INTERVAL = 1f;
        float MAX_BG_SPEED = -1200f;
        float MAX_GAMESCORE_TIMER = 0.4f;

        obstacleSpawnInterval = (float) Math.max(FINAL_OBSTACLE_SPAWN_INTERVAL, obstacleSpawnInterval - 0.4);
        bgSpeed = (float) Math.max(MAX_BG_SPEED, bgSpeed - 70);
        rocketSpeed = (float) Math.max(MAX_ROCKET_SPEED, rocketSpeed - 70);
        scoreInterval = (float) Math.max(MAX_GAMESCORE_TIMER, scoreInterval - 0.05);

        for (IObstacle obstacle : obstacles) {
            if (obstacle instanceof Flame) {
                obstacle.setVX(bgSpeed);
            } else if (obstacle instanceof Rocket) {
                obstacle.setVX(rocketSpeed);
            }
        }

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
     * Gets a random obstacle of the ones listed in the cases
     *
     * @return returns a random obstacle of the specified obstacles
     */
    private Obstacle getRandomObstacle() {
        int randNum = random.nextInt(0, Math.min(difficulty, NUM_OBSTACLES));

        return switch (randNum) {
            case 0 -> flameFactory.newFlame(worldWidth, worldHeight, GROUND, margin, (float) bgSpeed);
            case 1 -> rocketFactory.newRocket(worldWidth, worldHeight, GROUND, margin, rocketSpeed);
            case 2 -> {
                Lazer lazer = getNonOverlappingLazer();
                if (lazer != null) {
                    yield lazer;
                } else {
                    yield rocketFactory.newRocket(worldWidth, worldHeight, GROUND, margin, rocketSpeed);}
            }
            default -> throw new RuntimeException("No object was chosen. The random number was: " + randNum);
        };
    }

    /**
     * Checks if the lazer can be spawned or not. If the new lazer overlaps with existing lazers, it cannot be spawned
     *
     * @param newLazer The new potential lazer
     * @return true if the lazer can be spawned
     */
    private boolean canSpawnLazer(Lazer newLazer) {
        for (IObstacle obstacle : obstacles) {
            if (obstacle instanceof Lazer currLazer && currLazer.getProgressionLevel() != 4) {
                if (currLazer.getHitBox().overlaps(newLazer.getHitBox())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Tries to get a random lazer that is not overlapping with the current lazers in the game
     *
     * @return a new overlapping lazer or null if it could not make a lazer that was not overlapping
     */
    private Lazer getNonOverlappingLazer() {
        Lazer candidate;
        for (int i = 0; i < MAX_LAZER_SPAWN_ATTEMPTS; i++) {
            candidate = lazerFactory.newLazer(worldWidth, worldHeight, GROUND, margin);
            if (canSpawnLazer(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    /**
     * Gets a list of the obstacles
     *
     * @return current obstacles active in the game
     */
    public List<IObstacle> getObstacles() {
        return new ArrayList<>(obstacles);
     }

    /**
     * Updates the power-up system by counting down the spawn timer,
     * spawning a new power-up, and moving or removing the current one.
     *
     * @param dt the time since the last frame
     */
    private void updatePowerUp(float dt){
        if (player.getActivePowerUp() != PowerUpType.NORMAL) {
            return;
        }

        powerUpTimer -= dt;

        if (powerUp == null && powerUpTimer <= 0){
            powerUp = powerUpFactory.newPowerUp(worldWidth, worldHeight, GROUND, margin, bgSpeed);
            powerUpTimer = getRandomPowerUpSpawnInterval();
        }

        if (powerUp != null) {
            powerUp.update(dt);
            powerUp.setVX(bgSpeed);

            if (powerUp.isOfScreen(worldWidth, worldHeight)) {
                powerUp = null;
            }
        }
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
        gameScore = 0;
        coinCount = 0;
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

    @Override
    public boolean didCollectPowerUpThisFrame() {
        return collectedPowerUpThisFrame;
    }

    @Override
    public boolean didCollectCoinThisFrame() {
        return collectedCoinThisFrame;
    }

    public boolean isMovingUp() {
         return usingJetpack;
    }

}
