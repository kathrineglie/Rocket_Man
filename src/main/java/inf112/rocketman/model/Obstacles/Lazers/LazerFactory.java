package inf112.rocketman.model.Obstacles.Lazers;

public interface LazerFactory {
    /**
     * Creates a new random instance of a Lazer based on the width, height, ground and margin of the world
     *
     * @param worldWidth Width of the world
     * @param worldHeight Height of the world
     * @param ground Ground level of the world
     * @param margin Margin of the screen
     * @return A new lazer randomized within the world bounds.
     */
    Lazer newLazer(float worldWidth, float worldHeight, float ground, float margin);
}
