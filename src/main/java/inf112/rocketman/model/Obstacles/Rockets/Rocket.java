package inf112.rocketman.model.Obstacles.Rockets;

import inf112.rocketman.model.Obstacles.Obstacle;

/**
 * Represents a rocket obstacle that becomes active after a short delay.
 *
 * <p>Before activation, the rocket waits for its spawn timer to finish.
 * When active, it starts moving like a normal obstacle.</p>
 */
public class Rocket extends Obstacle {

    private static boolean active = false;
    private float spawnTimer = 1.5f;

    /**
     * Creates a new rocket obstacle.
     *
     * @param x the x-coordinate of the rocket
     * @param y the y-coordinate of the rocket
     * @param width the width of the rocket
     * @param height the height of the rocket
     * @param vx the horizontal velocity
     * @param vy the vertical velocity
     */
    public Rocket(float x, float y, float width, float height, float vx, float vy) {
        super(x, y, width, height, vx, vy);
    }

    @Override
    public void update(float dt) {
        if (!active) {
            spawnTimer -= dt;
            if (spawnTimer <= 0) {
                setState(true);
            }
        } else {
            super.update(dt);
        }
    }

    /**
     * Sets whether rockets are active
     *
     * @param state true if rockets should be active, false otherwise.
     */
    public static void setState(boolean state) {
        active = state;
    }

    /**
     * Checks whether the rocket is active.
     *
     * @return true if the rocket is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Returns the remaining spawn time before the rocket becomes active.
     *
     * @return the remaining spawn timer
     */
    public float getSpawnTimer() {
        return spawnTimer;
    }
}
