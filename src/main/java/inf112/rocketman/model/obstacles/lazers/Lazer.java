package inf112.rocketman.model.obstacles.lazers;

import inf112.rocketman.model.obstacles.Obstacle;

/**
 * Represents a lazer obstacle with multiple progression stages.
 *
 * <p>The lazer goes through harmless, warning, and active phases
 * before it starts moving normally.</p>
 */
public class Lazer extends Obstacle {
    private float harmlessLazerCount = 1.5f;
    private float warningLazerCount = 1.5f;
    private float activeLazerCount = 2f;
    private int progression = 1;

    /**
     * Creates a new laser obstacle.
     *
     * @param x the x-coordinate of the lazer
     * @param y the y-coordinate of the lazer
     * @param width the width of the lazer
     * @param height the height of the lazer
     * @param vx the horizontal velocity
     * @param vy the vertical velocity
     */
    public Lazer(float x, float y, float width, float height, float vx, float vy) {
        super(x, y, width, height, vx, vy);
    }

    @Override
    public void update(float dt) {
        if (progression == 1) {
            harmlessLazerCount -= dt;
            if (harmlessLazerCount <= 0) {
                progression = 2;
            }
        } else if (progression == 2) {
            warningLazerCount -= dt;
            if (warningLazerCount <= 0) {
                progression = 3;
            }
        } else if (progression == 3) {
            activeLazerCount -= dt;
            if (activeLazerCount <= 0) {
                progression = 4;
            }
        } else {
            super.update(dt);
        }
    }

    /**
     * Sets the current progression level of the lazer.
     *
     * @param level the new progression level
     */
    public void setProgressionLevel(int level) {
        progression = level;
    }

    /**
     * Returns the current progression level of the lazer.
     *
     * @return the current progression level
     */
    public int getProgressionLevel() {
        return progression;
    }

    /**
     * Checks whether the lazer has finished its staged sequence.
     *
     * @return true if the lazer has reached its final stage, false otherwise
     */
    public boolean isFinished() {
        return progression == 4;
    }
}
