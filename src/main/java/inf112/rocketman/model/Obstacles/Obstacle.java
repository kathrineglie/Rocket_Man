package inf112.rocketman.model.Obstacles;

import com.badlogic.gdx.math.Rectangle;

public class Obstacle implements IObstacle {
    private float x, y;
    private float width, height;
    private float vx, vy; // the velocity of the object

    private Rectangle hitbox = new Rectangle();

    protected Obstacle (float x, float y,
                     float width, float height,
                     float vx, float vy) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vx = vx;
        this.vy = vy;
        this.hitbox.set(x, y, width, height);
    }

    // Get methods:
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    //
    @Override
    public void update(float dt) {
        x += vx * dt;
        y += vy * dt;
    }

    @Override
    public Rectangle getHitBox() {
        hitbox.set(x, y, width, height);
        return hitbox;
    }

    @Override
    public boolean isOfScreen(float worldWidth, float worldHeight) {
        return (x + width < 0 || x > worldWidth || y + height < 0 || y > worldHeight);
    }
}
