package inf112.rocketman.model.Coins;

import com.badlogic.gdx.math.Rectangle;

public class Coin {
    private Rectangle hitbox;
    private float x, y, width, height, vx;
    private float GROUND = 170f;
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

    public void setSpeed(float speed){
        this.vx = speed;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean isOfScreen(float worldWidth, float worldHeight) {
        return (x + width < 0 || y + height < GROUND || y > worldHeight);
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
