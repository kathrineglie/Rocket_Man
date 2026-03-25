package inf112.rocketman.model.Obstacles;

import com.badlogic.gdx.math.Rectangle;

public class Obstacle implements IObstacle {
    protected float x, y;
    protected float width, height;
    protected float vx, vy; // the velocity of the object
    protected float HITBOX_OFFSET = 3;
    protected float GROUND = 170f;

    protected Rectangle hitbox = new Rectangle();

    protected Obstacle (float x, float y,
                     float width, float height,
                     float vx, float vy) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vx = vx;
        this.vy = vy;

        this.hitbox.set(x + HITBOX_OFFSET,
                y + HITBOX_OFFSET,
                width - 2*HITBOX_OFFSET,
                height - 2*HITBOX_OFFSET);
    }

    //
    @Override
    public void update(float dt) {
        x += vx * dt;
        y += vy * dt;

        hitbox.set(x + HITBOX_OFFSET,
                y + HITBOX_OFFSET,
                width - 2*HITBOX_OFFSET,
                height - 2*HITBOX_OFFSET);
    }

    @Override
    public Rectangle getHitBox() {
        return hitbox;
    }

    @Override
    public boolean isOfScreen(float worldWidth, float worldHeight) {
        return (x + width < 0 || y + height < GROUND || y > worldHeight);
    }

    @Override
    public float getOffSet() {
        return HITBOX_OFFSET;
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

    public float getVX() {return vx; }

    @Override
    public void setVX(float bgSpeed) {
        vx = bgSpeed;
    }
}
