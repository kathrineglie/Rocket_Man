package inf112.rocketman.model.obstacles;

import inf112.rocketman.model.Velocity;
import inf112.rocketman.model.WorldDimensions;

/**
 * Interface for factories that create random obstacles.
 */
public interface IRandomObstacleFactory {

    /**
     * Creates a new obstacle of the given type.
     *
     * @param type the type of the obstacle to create
     * @param worldDimensions the width and height of the world
     * @param ground the ground position
     * @param margin the spawn margin
     * @param vx the horizontal velocity of the obstacle
     *
     * @return a new obstacle of the specified type
     */
    Obstacle newObstacle(ObstacleType type, WorldDimensions worldDimensions, float ground, float margin, float vx);
}
