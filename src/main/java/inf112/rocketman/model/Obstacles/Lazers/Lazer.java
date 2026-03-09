package inf112.rocketman.model.Obstacles.Lazers;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

import inf112.rocketman.grid.GridCell;
import inf112.rocketman.model.Obstacles.Obstacle;

public class Lazer extends Obstacle {
    public float spawnTimer = 1.5f;
    private boolean active = false;
    protected Lazer(float x, float y, float width, float height, float vx, float vy) {
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
