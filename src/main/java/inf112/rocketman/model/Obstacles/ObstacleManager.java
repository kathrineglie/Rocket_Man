package inf112.rocketman.model.Obstacles;

import inf112.rocketman.model.Obstacles.Flames.Flame;
import inf112.rocketman.model.Obstacles.Flames.FlameFactory;
import inf112.rocketman.model.Obstacles.IObstacle;
import inf112.rocketman.model.Obstacles.Lazers.Lazer;
import inf112.rocketman.model.Obstacles.Lazers.LazerFactory;
import inf112.rocketman.model.Obstacles.Obstacle;
import inf112.rocketman.model.Obstacles.Rockets.Rocket;
import inf112.rocketman.model.Obstacles.Rockets.RocketFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ObstacleManager {
    private static final int MAX_LAZER_SPAWN_ATTEMPTS = 10;
    private static final int NUM_OBSTACLES = 3;
    private static final float START_OBSTACLE_SPAWN_INTERVAL = 2.5f;

    private final List<IObstacle> obstacles = new ArrayList<>();

    private final RocketFactory rocketFactory;
    private final LazerFactory lazerFactory;
    private final FlameFactory flameFactory;
    private final Random random = new Random();

    private float obstacleTimer = 0f;
    private float obstacleSpawnInterval = START_OBSTACLE_SPAWN_INTERVAL;

    public ObstacleManager(RocketFactory rocketFactory,
                           LazerFactory lazerFactory,
                           FlameFactory flameFactory) {
        this.rocketFactory = rocketFactory;
        this.lazerFactory = lazerFactory;
        this.flameFactory = flameFactory;
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
    public void update(float dt,
                       float worldWidth,
                       float worldHeight,
                       float ground,
                       float margin,
                       int difficulty,
                       float bgSpeed,
                       float rocketSpeed) {

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
            }

            if (obstacle instanceof Lazer lazer && lazer.getProgressionLevel() == 4) {
                iterator.remove();
            }

            if (obstacle instanceof Flame && obstacle.isOfScreen(worldWidth, worldHeight)) {
                iterator.remove();
            }
        }
    }

    /**
     * Returns a random obstacle based on the current difficulty.
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
            case 0 -> flameFactory.newFlame(worldWidth, worldHeight, ground, margin, bgSpeed);
            case 1 -> rocketFactory.newRocket(worldWidth, worldHeight, ground, margin, rocketSpeed);
            case 2 -> {
                Lazer lazer = getNonOverlappingLazer(worldWidth, worldHeight, ground, margin);
                if (lazer != null) {
                    yield lazer;
                } else {
                    yield rocketFactory.newRocket(worldWidth, worldHeight, ground, margin, rocketSpeed);
                }
            }
            default -> throw new RuntimeException("No object was chosen. The random number was: " + randNum);
        };
    }

    /**
     * Checks if the lazer can be spawned without overlapping another active lazer.
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
     * @return a valid lazer, or null if none could be created
     */
    private Lazer getNonOverlappingLazer(float worldWidth,
                                         float worldHeight,
                                         float ground,
                                         float margin) {
        Lazer candidate;
        for (int i = 0; i < MAX_LAZER_SPAWN_ATTEMPTS; i++) {
            candidate = lazerFactory.newLazer(worldWidth, worldHeight, ground, margin);
            if (canSpawnLazer(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    /**
     * Updates obstacle speeds after a difficulty increase.
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

    public List<IObstacle> getObstacles() {
        return new ArrayList<>(obstacles);
    }

    public float getObstacleSpawnInterval() {
        return obstacleSpawnInterval;
    }

    public void setObstacleSpawnInterval(float obstacleSpawnInterval) {
        this.obstacleSpawnInterval = obstacleSpawnInterval;
    }

    public List<IObstacle> getObstacleListReference() {
        return obstacles;
    }
}