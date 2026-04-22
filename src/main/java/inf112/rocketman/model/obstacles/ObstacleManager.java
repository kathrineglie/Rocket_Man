package inf112.rocketman.model.obstacles;

import inf112.rocketman.model.obstacles.flames.Flame;
import inf112.rocketman.model.obstacles.lazers.Lazer;
import inf112.rocketman.model.obstacles.rockets.Rocket;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ObstacleManager {
    private static final int MAX_LAZER_SPAWN_ATTEMPTS = 10;
    private static final int NUM_OBSTACLES = 3;
    private static final float START_OBSTACLE_SPAWN_INTERVAL = 2.5f;

    private final List<IObstacle> obstacles = new ArrayList<>();
    private final IRandomObstacleFactory obstacleFactory;
    private final Random random = new Random();

    private float obstacleTimer = 0f;
    private float obstacleSpawnInterval = START_OBSTACLE_SPAWN_INTERVAL;

    public ObstacleManager(IRandomObstacleFactory obstacleFactory) {
        this.obstacleFactory = obstacleFactory;
    }

    /**
     * Updates obstacle spawning, movement and cleanup.
     *
     * @param dt time since last frame
     * @param worldWidth world width
     * @param worldHeight world height
     * @param ground ground level
     * @param margin margin
     * @param difficulty current difficulty
     * @param bgSpeed current background speed
     * @param rocketSpeed current rocket speed
     */
    public void update(float dt, float worldWidth, float worldHeight, float ground, float margin, int difficulty, float bgSpeed, float rocketSpeed) {
        obstacleTimer -= dt;

        if (obstacleTimer <= 0f) {
            obstacles.add(getRandomObstacle(worldWidth, worldHeight, ground, margin, difficulty, bgSpeed, rocketSpeed));
            obstacleTimer = obstacleSpawnInterval;
        }

        Iterator<IObstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            IObstacle obstacle = iterator.next();
            obstacle.update(dt);

            if (obstacle instanceof Rocket && obstacle.isOfScreen(worldWidth, worldHeight)) {
                iterator.remove();
            } else if (obstacle instanceof Lazer lazer && lazer.getProgressionLevel() == 4) {
                iterator.remove();
            } else if (obstacle instanceof Flame && obstacle.isOfScreen(worldWidth, worldHeight)) {
                iterator.remove();
            }
        }
    }

    /**
     * Gets a random obstacle based on the current difficulty.
     *
     * @param worldWidth the width of the game world
     * @param worldHeight the height of the game world
     * @param ground the ground level
     * @param margin the screen margin
     * @param difficulty the current difficulty level
     * @param bgSpeed the current background speed
     * @param rocketSpeed the current rocket speed
     * @return a random obstacle
     */
    private Obstacle getRandomObstacle(float worldWidth,
                                       float worldHeight,
                                       float ground,
                                       float margin,
                                       int difficulty,
                                       float bgSpeed,
                                       float rocketSpeed) {

        int randNum = random.nextInt(0, Math.min(difficulty, NUM_OBSTACLES));

        return switch (randNum) {
            case 0 -> obstacleFactory.newObstacle(
                    ObstacleType.FLAME,
                    worldWidth,
                    worldHeight,
                    ground,
                    margin,
                    bgSpeed
            );
            case 1 -> obstacleFactory.newObstacle(
                    ObstacleType.ROCKET,
                    worldWidth,
                    worldHeight,
                    ground,
                    margin,
                    rocketSpeed
            );
            case 2 -> {
                Lazer lazer = getNonOverlappingLazer(worldWidth, worldHeight, ground, margin);
                if (lazer != null) {
                    yield lazer;
                } else {
                    yield obstacleFactory.newObstacle(
                            ObstacleType.ROCKET,
                            worldWidth,
                            worldHeight,
                            ground,
                            margin,
                            rocketSpeed
                    );
                }
            }
            default -> throw new RuntimeException("No obstacle was chosen. The random number was: " + randNum);
        };
    }

    /**
     * Checks if a new lazer can be spawned without overlapping an existing lazer.
     *
     * @param newLazer the new potential lazer
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
     * Tries to create a lazer that does not overlap with existing lazers.
     *
     * @param worldWidth the width of the game world
     * @param worldHeight the height of the game world
     * @param ground the ground level
     * @param margin the screen margin
     * @return a non-overlapping lazer, or null if none could be made
     */
    private Lazer getNonOverlappingLazer(float worldWidth,
                                         float worldHeight,
                                         float ground,
                                         float margin) {
        Obstacle candidate;

        for (int i = 0; i < MAX_LAZER_SPAWN_ATTEMPTS; i++) {
            candidate = obstacleFactory.newObstacle(
                    ObstacleType.LAZER,
                    worldWidth,
                    worldHeight,
                    ground,
                    margin,
                    0
            );

            if (candidate instanceof Lazer lazer && canSpawnLazer(lazer)) {
                return lazer;
            }
        }
        return null;
    }

    /**
     * Updates the speed of already spawned obstacles after difficulty increases.
     *
     * @param bgSpeed the current background speed
     * @param rocketSpeed the current rocket speed
     */
    public void updateObstacleSpeeds(float bgSpeed, float rocketSpeed) {
        for (IObstacle obstacle : obstacles) {
            if (obstacle instanceof Flame) {
                obstacle.setVX(bgSpeed);
            } else if (obstacle instanceof Rocket) {
                obstacle.setVX(rocketSpeed);
            }
        }
    }

    /**
     * Clears all active obstacles.
     */
    public void clear() {
        obstacles.clear();
    }

    /**
     * Resets obstacle state for a new game.
     */
    public void reset() {
        obstacles.clear();
        obstacleTimer = 0f;
        obstacleSpawnInterval = START_OBSTACLE_SPAWN_INTERVAL;
    }

    /**
     * Gets a copy of the active obstacles.
     *
     * @return a copy of the obstacle list
     */
    public List<IObstacle> getObstacles() {
        return new ArrayList<>(obstacles);
    }

    /**
     * Returns the actual obstacle list.
     * Used temporarily while collision handling is still in GameModel.
     *
     * @return the obstacle list reference
     */
    public List<IObstacle> getObstacleListReference() {
        return new ArrayList<>(obstacles);
    }

    /**
     * Gets the current obstacle spawn interval.
     *
     * @return the obstacle spawn interval
     */
    public float getObstacleSpawnInterval() {
        return obstacleSpawnInterval;
    }

    /**
     * Sets the obstacle spawn interval.
     *
     * @param obstacleSpawnInterval the new obstacle spawn interval
     */
    public void setObstacleSpawnInterval(float obstacleSpawnInterval) {
        this.obstacleSpawnInterval = obstacleSpawnInterval;
    }


    /**
     * Adds an obstacle directly to the manager.
     * Intended for use in tests only.
     *
     * @param obstacle the obstacle to add
     */
    protected void addObstacleForTesting(IObstacle obstacle) {
        obstacles.add(obstacle);
    }



}