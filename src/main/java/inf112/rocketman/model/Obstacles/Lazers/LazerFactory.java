package inf112.rocketman.model.Obstacles.Lazers;

public interface LazerFactory {
    Lazer newLazer(float worldWidth, float worldHeight, float margin);
}
