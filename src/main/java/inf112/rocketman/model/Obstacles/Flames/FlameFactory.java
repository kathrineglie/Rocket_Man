package inf112.rocketman.model.Obstacles.Flames;

public interface FlameFactory {
    Flame newFlame(float worldWidth, float worldHeight, float margin, float vx);
} 