package inf112.rocketman.model.Character;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.PowerUps.PowerUpType;

public class TPowah implements ITPowah  {
    private static final int HITBOX_OFFSET = 10;

    private final Rectangle bounds;
    private float vy = 0;
    private final float ground;
    private float ceiling;

    private PowerUpType activePowerUp = PowerUpType.NORMAL;

    private static final float NORMAL_THRUST = 4000f;
    private static final float NORMAL_GRAVITY = -1000f;
    private static final float NORMAL_MAX_VY = 700f;

    private static final float BIRD_FLAP_STRENGTH = 550f;
    private static final float BIRD_GRAVITY = -1000f;
    private static final float BIRD_MAX_FALL_SPEED = 900f;

    private boolean gravityUp = false;

    private boolean robotIsJumping = false;
    private boolean robotGoingDown = false;
    private boolean movementInput = false;

    private static final float ROBOT_BOOST = 20f;
    private static final float ROBOT_MIN_JUMP = 200f;
    private static final float ROBOT_SLOW_GRAVITY = -100f;
    private static final float ROBOT_GRAVITY = -800f;

    private static final float GRAVITY_SUIT_GRAVITY = -1500;


    public TPowah (float x, float y, float width, float height, float ground) {
        this.bounds = new Rectangle(x, y, width, height);
        this.ground = ground;
    }

    @Override
    public void update(float dt, boolean movementInput, float worldHeight) {
        this.movementInput = movementInput;
        if (activePowerUp == PowerUpType.BIRD) {
            updateBird(dt, movementInput, worldHeight);
        } else if (activePowerUp == PowerUpType.ROBOT) {
            updateRobot(dt, movementInput, worldHeight);
        } else if (activePowerUp == PowerUpType.GRAVITY_SUIT) {
            updateGravitySuit(dt, movementInput, worldHeight);
        } else {
            updateNormal(dt, movementInput, worldHeight);
        }
    }

    private void updateNormal(float dt, boolean thrusting, float worldHeight) {
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

    private void updateRobot(float dt, boolean movementInput, float worldHeight) {
        if (!movementInput && onGround()) {
            robotIsJumping = false;
            robotGoingDown = false;
        }

        if (movementInput && onGround() && !robotIsJumping && !robotGoingDown) {
            vy = ROBOT_MIN_JUMP;
            robotIsJumping = true;
        }

        if (movementInput && !onGround() && robotIsJumping && !robotGoingDown) {
            vy += ROBOT_BOOST;
        }

        if ((!movementInput && robotIsJumping) || Math.abs((bounds.y + bounds.height) - worldHeight) < 0.0001f) {
            robotGoingDown = true;
        }

        if (movementInput && robotGoingDown && robotIsJumping) {
            vy  += ROBOT_SLOW_GRAVITY * dt;
        } else {
            vy  += ROBOT_GRAVITY * dt;
        }

        bounds.y += vy * dt;
        keepPlayerInsideBounds(worldHeight);
    }

    private void updateGravitySuit(float dt, boolean movementInput, float worldHeight) {
        ceiling = worldHeight - bounds.height;

        if (gravityUp) {
            vy -= GRAVITY_SUIT_GRAVITY * dt;
        } else {
            vy += GRAVITY_SUIT_GRAVITY * dt;
        }

        bounds.y += vy * dt;

        if (movementInput) {
            gravityUp = !gravityUp;
            vy = 0;
        }

        keepPlayerInsideBounds(worldHeight);
    }

    private void keepPlayerInsideBounds(float worldHeight) {
        if (bounds.y < ground) {
            bounds.y = ground;
            vy = 0;
        }
        if (bounds.y > worldHeight - bounds.height) {
            bounds.y = worldHeight - bounds.height;
            vy = 0;
        }
    }

    public boolean getMovementInput() {
        return movementInput;
    }

    public boolean getRobotIsJumping() {
        return robotIsJumping;
    }

    public boolean getRobotGoingDown() {
        return robotGoingDown;
    }

    public float getRobotGravity() {
        return ROBOT_GRAVITY;
    }

    public float getRobotMinJump() {
        return ROBOT_MIN_JUMP;
    }

    public float getRobotBoost() {
        return ROBOT_BOOST;
    }

    public float getCeiling() {
        return ceiling;
    }

    public boolean isGravityUp() {
        return gravityUp;
    }


    @Override
    public void setPowerUp(PowerUpType powerUp){
        this.activePowerUp = powerUp;
    }

    @Override
    public PowerUpType getActivePowerUp(){
        return activePowerUp;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(bounds.getX() + HITBOX_OFFSET,
                bounds.getY() + HITBOX_OFFSET,
                bounds.getWidth() - HITBOX_OFFSET*2,
                bounds.getHeight() - HITBOX_OFFSET*2);
    }

    @Override
    public Polygon getPolyHitBox() {
        float[] vertices = new float[] {
                0, 0,
                0, bounds.height - HITBOX_OFFSET * 2,
                bounds.width - HITBOX_OFFSET * 2, bounds.height- HITBOX_OFFSET * 2,
                bounds.width - HITBOX_OFFSET * 2, 0
        };

        Polygon polygon = new Polygon(vertices);
        polygon.setPosition(bounds.x, bounds.y);
        return polygon;
    }

    @Override
    public boolean hasPowerUp() {
        return activePowerUp != PowerUpType.NORMAL;
    }

    @Override
    public boolean onGround() {
        return bounds.y == ground;
    }

    @Override
    public boolean onCeiling() {
        return bounds.y == ceiling;
    }

    @Override
    public boolean isGoingUp() {
        return vy > 5f;
    }

    @Override
    public boolean isGoingDown() {
        return vy < -5f;
    }

    @Override
    public float getY() {return bounds.y;}

    @Override
    public float getX() {return bounds.x;}

    @Override
    public float getVY() {return vy;}

    @Override
    public float getWidth() {return bounds.width;}

    @Override
    public float getHeight() {return bounds.height; }

    @Override
    public void setX(float x) {this.bounds.x = x; }

    @Override
    public void setY(float y) {this.bounds.y = y; }

    @Override
    public void setVy(float vy) {this.vy = vy;}
}
