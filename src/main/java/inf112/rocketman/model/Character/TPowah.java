package inf112.rocketman.model.Character;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.PowerUps.PowerUpType;

public class TPowah {
    private static final float GROUND_Y = 120f;
    private static final int HITBOX_OFFSET = 10;

    private final Rectangle bounds;
    private float vy = 0;

    private PowerUpType activePowerUp = PowerUpType.NORMAL;

    private static final float NORMAL_THRUST = 4000f;
    private static final float NORMAL_GRAVITY = -1000f;
    private static final float NORMAL_MAX_VY = 700f;

    private static final float BIRD_FLAP_STRENGTH = 550f;
    private static final float BIRD_GRAVITY = -1000f;
    private static final float BIRD_MAX_FALL_SPEED = 900f;


    public TPowah (float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void update(float dt, boolean movementInput, float worldHeight) {
        if (activePowerUp == PowerUpType.BIRD) {
            updateBird(dt, movementInput, worldHeight);
        } else {
            updateNormal(dt, movementInput, worldHeight);
        }
    }

    private void updateNormal(float dt, boolean thrusting, float worldHeight){
        float ay = NORMAL_GRAVITY + (thrusting ? NORMAL_THRUST : 0f);
        vy += ay * dt;

        if (vy > NORMAL_MAX_VY) vy = NORMAL_MAX_VY;
        if (vy < - NORMAL_MAX_VY) vy = -NORMAL_MAX_VY;

        bounds.y += vy * dt;

        keepPlayerInsideBounds(worldHeight);
    }

    private void updateBird(float dt, boolean flap, float worldHeight){
        if (flap){
            vy = BIRD_FLAP_STRENGTH;
        }

        vy  += BIRD_GRAVITY * dt;

        if (vy < -BIRD_MAX_FALL_SPEED){
            vy = -BIRD_MAX_FALL_SPEED;
        }

        bounds.y += vy * dt;
        keepPlayerInsideBounds(worldHeight);
    }


    private void keepPlayerInsideBounds(float worldHeight){
        if (bounds.y < GROUND_Y) {
            bounds.y = GROUND_Y;
            vy = 0;
        }
        if (bounds.y > worldHeight - bounds.height) {
            bounds.y = worldHeight - bounds.height;
            vy = 0;
        }
    }

    public void setPowerUp(PowerUpType powerUp){
        this.activePowerUp = powerUp;
    }

    public PowerUpType getActivePowerUp(){
        return activePowerUp;
    }

    /**
     * The actual size of the character
     *
     * @return
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Returns the hitbox for the character which is a bit smaller due to hitbox offset
     *
     * @return
     */
    public Rectangle getHitBox() {
        return new Rectangle(bounds.getX() + HITBOX_OFFSET,
                bounds.getY() + HITBOX_OFFSET,
                bounds.getWidth() - HITBOX_OFFSET*2,
                bounds.getHeight() - HITBOX_OFFSET*2);
    }

    /**
     * Returns the hitbox as a polygon
     *
     * @return the hitbox for the player as a polygon
     */
    public Polygon getPolyHitBox() {
        float[] vertices = new float[] {
                0, 0,
                0, bounds.height,
                bounds.width, bounds.height,
                bounds.width, 0
        };

        Polygon polygon = new Polygon(vertices);
        polygon.setPosition(bounds.x, bounds.y);
        return polygon;
    }

    public float getY() {return bounds.y;}

    public float getX() {return bounds.x;}

    public float getVY() {return vy;}

    public float getWidth() {return bounds.width;}

    public float getHeight() {return bounds.height; }

    public void setX(float x) {this.bounds.x = x; }

    public void setY(float y) {this.bounds.y = y; }

    public void setVy(float vy) {this.vy = vy;}
}
