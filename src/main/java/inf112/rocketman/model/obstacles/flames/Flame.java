package inf112.rocketman.model.obstacles.flames;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.obstacles.Obstacle;

/**
 * Represents a flame obstacle in the game.
 *
 * <p>A flame has a rotation angle and is represented by a polygon
 * for positioning and collision-related geometry.</p>
 */
public class Flame extends Obstacle {
    private float angle;
    private Polygon polygon;

    /**
     * Creates a new flame obstacle.
     *
     * @param x the x-coordinate of the flame
     * @param y the y-coordinate of the flame
     * @param width the width of the flame
     * @param length the length of the flame
     * @param vx the horizontal velocity
     * @param vy the vertical velocity
     * @param angle the rotation angle of the flame
     */
    public Flame(float x, float y, float width, float length, float vx, float vy, float angle) {
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

    /**
     * Returns the rotation angle of the flame.
     *
     * @return the flame angle
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Returns the polygon representing the flame.
     *
     * @return the flame polygon
     */
    public Polygon getPolygon() {
        return polygon;
    }

}
