package inf112.rocketman.model;

import inf112.rocketman.model.Obstacles.ObstacleManager;

/**
 * This class manages the game's difficulty progression over time
 *
 * It is responsible for increasing difficulty based on the player's current score
 * by adjusting parameteres such as background speed, obstacle speed and score interval
 *
 * It ensures that the game becomes progressively mroe challenging as well as stopping
 * at a maximum.
 */
public class DifficultyManager {
    private static final float START_BG_SPEED = -350f;
    private static final int START_DIFFICULTY = 1;
    private static final float START_ROCKET_SPEED = -550;
    private static final float START_SCORE_INTERVAL = 0.4f;

    private static final int MAX_DIFFICULTY = 5;
    private static final float MAX_ROCKET_SPEED = - 1400f;
    private static final float MAX_OBSTACLE_SPAWN_INTERVAL = 1f;
    private static final float MAX_BG_SPEED = -1200f;
    private static final float MIN_GAME_SCORE_INTERVAL = 0.1f;

    private static final float DISTANCE_BETWEEN_LEVELS = 100f;
    private static final float BACKGROUND_SPEED_INCREASE = 70f;
    private static final float SCORE_INTERVAL_DECREASE = 0.5f;

    private int difficulty;
    private float bgSpeed;
    private float rocketSpeed;
    private float scoreInterval;

    private final ObstacleManager obstacleManager;

    DifficultyManager(ObstacleManager obstacleManager) {
        this.difficulty = START_DIFFICULTY;
        this.bgSpeed = START_BG_SPEED;
        this.rocketSpeed = START_ROCKET_SPEED;
        this.scoreInterval = START_SCORE_INTERVAL;

        this.obstacleManager = obstacleManager;
    }

    /**
     * Increases the speed of the game as well as increasing level which adds more obstacles as well as make them sapwn more frequently
     */
    private void increaseDifficulty() {
        float newSpawnInterval = Math.max(MAX_OBSTACLE_SPAWN_INTERVAL, obstacleManager.getObstacleSpawnInterval() - 0.4f);
            obstacleManager.setObstacleSpawnInterval(newSpawnInterval);
        bgSpeed = Math.max(MAX_BG_SPEED, bgSpeed - BACKGROUND_SPEED_INCREASE);
        rocketSpeed =  Math.max(MAX_ROCKET_SPEED, rocketSpeed - BACKGROUND_SPEED_INCREASE);
        scoreInterval = (float) Math.max(MIN_GAME_SCORE_INTERVAL, scoreInterval - SCORE_INTERVAL_DECREASE);

        obstacleManager.updateObstacleSpeeds(bgSpeed, rocketSpeed);
        difficulty++;
    }

    /**
     * Updates the obstacle timer depending on how far you are in the game
     */
    public void updateDifficulty(int gameScore) {
        if (gameScore > difficulty * DISTANCE_BETWEEN_LEVELS && difficulty != MAX_DIFFICULTY) {
            increaseDifficulty();
        }
    }

    /**
     * Resets the difficulty. This is used when the player is starting a new game session.
     */
    public void resetDifficulty() {
        difficulty = 1;
        bgSpeed = START_BG_SPEED;
        rocketSpeed = START_ROCKET_SPEED;
        scoreInterval = START_SCORE_INTERVAL;
    }

    public int getDifficulty() {return difficulty; }

    public float getBgSpeed() {return bgSpeed; }

    public float getRocketSpeed() {return rocketSpeed; }

    public float getScoreInterval() {return scoreInterval; }

}
