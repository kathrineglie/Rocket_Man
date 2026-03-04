package inf112.rocketman.model.Obstacles.Rockets;

import inf112.rocketman.model.Obstacles.Obstacle;

public class Rocket extends Obstacle {

    private  float spawnTimer = 1.5f;
    private boolean active = false;
    protected Rocket(float x, float y, float width, float height, float vx, float vy) {
        super(x, y, width, height, vx, vy);
    }

    @Override
    public void update(float dt) {
        if (!active) {
            spawnTimer -= dt;
            if (spawnTimer <= 0) {
                active = true;
            }
        } else {
            super.update(dt);
        }
    }

    public boolean isActive() {
        return active;
    }
}
