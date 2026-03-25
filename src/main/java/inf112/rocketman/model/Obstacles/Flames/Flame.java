package inf112.rocketman.model.Obstacles.Flames;
import java.util.Iterator;

import com.badlogic.gdx.math.Polygon;
import inf112.rocketman.grid.GridCell;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Obstacles.Obstacle;

public class Flame extends Obstacle {
    private float angle;
    private Polygon polygon;

    protected Flame(float x, float y, float width, float length, float vx, float vy, float angle) {
        super(x, y, width, length, vx, vy);
        this.angle = angle;

        this.polygon = new Polygon(new float[] {
                0, 0,
                0, length,
                width, length,
                width, 0
        });

        this.polygon.setOrigin(width/2f, length/2f);
        this.polygon.setPosition(x - width/2f, y - length/2f);
        this.polygon.setRotation(angle);
    }

    public void setSpeed(float speed){
        this.vx = speed;
    }

    @Override
    public Rectangle getHitBox() {
        return null;
    }

    @Override
    public void update(float dt) {
        this.x += vx * dt;
        this.y += vy * dt;
        polygon.setPosition(this.x - width/2f, this.y - height/2f);
        polygon.setRotation(angle);
    }

    public float getAngle() {
        return angle;
    }

    public Polygon getPolygon() {
        return polygon;
    }

}
