package inf112.rocketman.model.Coins;

import com.badlogic.gdx.math.Rectangle;

public class Coin {
    private Rectangle hitbox;
    private float x, y, width, height, vx;

    protected Coin(float x, float y, float width, float height, float vx) {
        this.hitbox = new Rectangle(x,y,width,height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vx = vx;
    }

    public void update(float dt) {
        x += vx * dt;
        hitbox.set(x,y,width,height);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean isOfScreen(float worldHeight, float margin) {
        return (x + width < margin || y < margin || y + height > worldHeight - margin);
    }

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
}
