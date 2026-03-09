package inf112.rocketman.model.Obstacles.Flames;

public interface FlameFactory {

    /**
     * Makes a new random flame object
     *
     * @param worldWidth // The width of the screen
     * @param worldHeight // The height of the screen
     * @param margin // The margin og the screen
     * @param vx // The speed of the background
     * @return
     */
    Flame newFlame(float worldWidth, float worldHeight, float margin, float vx);
} 