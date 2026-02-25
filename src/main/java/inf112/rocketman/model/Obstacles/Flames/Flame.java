package inf112.rocketman.model.Obstacles.Flames;
import java.util.Iterator;

import inf112.rocketman.grid.GridCell;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Obstacles.Obstacle;

public class Flame extends Obstacle {

    protected Flame(float x, float y, float width, float height, float vx, float vy) {
        super(x, y, width, height, vx, vy);
    }
}
