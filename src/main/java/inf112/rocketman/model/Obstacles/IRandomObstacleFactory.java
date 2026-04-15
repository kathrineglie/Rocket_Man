package inf112.rocketman.model.Obstacles;

public interface IRandomObstacleFactory {
    Obstacle newObstacle(ObstacleType type, float worldWidth, float worldHeight, float ground, float margin, float vx);
}
