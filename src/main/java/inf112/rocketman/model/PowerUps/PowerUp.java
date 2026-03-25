package inf112.rocketman.model.PowerUps;

import inf112.rocketman.model.Obstacles.Obstacle;

public class PowerUp extends Obstacle {
    private final PowerUpType type;

    public PowerUp(float x, float y, float width, float height, float vx, PowerUpType type) {
        super(x, y, width, height, vx, 0);
        this.type = type;
    }

    public PowerUpType getType() {
        return type;
    }
}