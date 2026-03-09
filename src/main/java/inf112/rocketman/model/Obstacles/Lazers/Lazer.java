package inf112.rocketman.model.Obstacles.Lazers;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

import inf112.rocketman.model.Obstacles.Obstacle;

public class Lazer extends Obstacle {
    private float harmlessLazerCount = 1.5f;
    private float warningLazerCount = 1.5f;
    private float activeLazerCount = 2f;
    private int progression = 1;
    protected Lazer(float x, float y, float width, float height, float vx, float vy) {
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

    public int getProgressionLevel() {
        return progression;
    }

    public boolean isFinished() {
        return progression == 4;
    }
}
