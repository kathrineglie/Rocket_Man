package inf112.rocketman.model.PowerUps;

import inf112.rocketman.model.Obstacles.Obstacle;

public class PowerUp extends Obstacle {
    private final PowerUpType type;

    private static final float DEFAULT_WIDTH = 60f;
    private static final float DEFAULT_HEIGHT = 60f;
    private static final float DEFAULT_SPEED_X = -250f;

    public PowerUp(float x, float y, PowerUpType type) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_SPEED_X, 0);
        this.type = type;
    }

    public PowerUpType getType() {
        return type;
    }
}