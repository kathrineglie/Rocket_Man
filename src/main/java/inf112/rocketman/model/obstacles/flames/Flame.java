package inf112.rocketman.model.obstacles.flames;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Velocity;
import inf112.rocketman.model.obstacles.Obstacle;

/**
 * Represents a flame obstacle in the game.
 *
 * <p>A flame has a rotation angle and is represented by a polygon
 * for positioning and collision-related geometry.</p>
 */
public class Flame extends Obstacle {
    private final float angle;
    private final Polygon polygon;

    /**
     * Creates a new flame obstacle.
     *
     * @param bounds the bounds of the obstacle
     * @param velocity the velocity of the object
     * @param ground the ground of the player and the objects
     * @param angle the rotation angle of the flame
     */
    public Flame(Rectangle bounds, Velocity velocity, float ground, float angle) {
        super(bounds, velocity, ground);
        this.angle = angle;

        this.polygon = new Polygon(new float[] {
                0, 0,
                0, bounds.height,
                width, bounds.height,
                width, 0
        });

        this.polygon.setOrigin(width/2f, bounds.height/2f);
        this.polygon.setPosition(x - width/2f, y - bounds.height/2f);
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
        Polygon copy = new Polygon(polygon.getVertices().clone());
        copy.setOrigin(polygon.getOriginX(), polygon.getOriginY());
        copy.setPosition(polygon.getX(), polygon.getY());
        copy.setRotation(polygon.getRotation());
        return copy;
    }
}
