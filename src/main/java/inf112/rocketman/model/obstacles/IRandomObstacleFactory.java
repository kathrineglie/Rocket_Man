package inf112.rocketman.model.obstacles;

/**
 * Interface for factories that create random obstacles.
 */
public interface IRandomObstacleFactory {

    /**
     * Creates a new obstacle of the given type.
     *
     * @param type the type of the obstacle to create
     * @param worldWidth the width of the game world
     * @param worldHeight the height of the game world
     * @param ground the ground position
     * @param margin the spawn margin
     * @param vx the horizontal velocity of the obstacle
     * @return a new obstacle
     */
    Obstacle newObstacle(ObstacleType type, float worldWidth, float worldHeight, float ground, float margin, float vx);
}
