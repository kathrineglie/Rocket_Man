package inf112.rocketman.model.Obstacles.Rockets;

public interface RocketFactory {
    Rocket newRocket(float worldWidth, float worldHeight, float ground, float margin, float bgSpeed);
}
