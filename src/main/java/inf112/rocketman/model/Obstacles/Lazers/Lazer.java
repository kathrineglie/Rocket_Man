package inf112.rocketman.model.Obstacles.Lazers;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

import inf112.rocketman.grid.GridCell;
import inf112.rocketman.model.Obstacles.Obstacle;

public class Lazer extends Obstacle {
    public static final float VX = 0;

    public Lazer (float x, float y, float width, float height, float vy) {
        super(x, y, width, height, VX, vy);
    }
    
}
