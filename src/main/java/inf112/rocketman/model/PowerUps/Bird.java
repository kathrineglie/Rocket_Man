package inf112.rocketman.model.PowerUps;


import com.badlogic.gdx.math.Rectangle;

public class Bird {
    private final Rectangle bounds;
    private float vy = 0f;
    private final float groundY;

    private final float flapStrength = 550f;
    private final float maxFallSpeed = 900f;
    private final float gravity = -1000f;

    public Bird(float x, float y, float width, float height, float groundY){
        this.bounds = new Rectangle(x, y, width, height);
        this.groundY = groundY;
    }


    public void update(float dt, boolean flap, float worldHeight) {
        if (flap) {
            vy = flapStrength;
        }

        vy += gravity * dt;

        if (vy < -maxFallSpeed) {
            vy = -maxFallSpeed;
        }

        bounds.y += vy * dt;

        if (bounds.y < groundY) {
            bounds.y = groundY;
            vy = 0;
        }

        if (bounds.y > worldHeight - bounds.height) {
            bounds.y = worldHeight - bounds.height;
            vy = 0;
        }
    }

    public float getX() { return bounds.x; }
    public float getY() { return bounds.y; }
    public float getWidth() { return bounds.width; }
    public float getHeight() { return bounds.height; }

}
