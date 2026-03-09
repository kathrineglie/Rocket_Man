package inf112.rocketman.model.Obstacles.Flames;
import java.util.Iterator;

import com.badlogic.gdx.math.Polygon;
import inf112.rocketman.grid.GridCell;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Obstacles.Obstacle;

public class Flame extends Obstacle {
    private float angle;
    private Polygon polygon;

    protected Flame(float x, float y, float width, float height, float vx, float vy, float angle) {
        super(x, y, width, height, vx, vy);
        this.angle = angle;

        this.polygon = new Polygon(new float[] {
                -height/2f, -width/2f,
                height/2f, -width/2f,
                height/2f, width/2f,
                -height/2f, width/2f
        });

        this.polygon.setOrigin(0, 0);
        this.polygon.setPosition(x, y);
        this.polygon.setRotation(angle);
    }

    @Override
    public void update(float dt) {
        x = vx * dt;
    }

    public float getAngle() {
        return angle;
    }

    public void setPolygon(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        this.polygon = new Polygon(new float[] {
                x1, y1,
                x2, y2,
                x3, y3,
                x4, y4
        });
    }

    public Polygon getPolygon() {
        return polygon;
    }

}
