package inf112.rocketman.model.Character;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.PowerUps.PowerUpType;
import inf112.rocketman.model.movement.*;

public class TPowah implements ITPowah  {
    private static final int HITBOX_OFFSET = 10;

    private final Rectangle bounds;
    private float vy = 0;
    private final float ground;

    private PowerUpType activePowerUp = PowerUpType.NORMAL;

    private MovementBehavior movementBehavior;
    private boolean movementInput = false;


    public TPowah (float x, float y, float width, float height, float ground) {
        this.bounds = new Rectangle(x, y, width, height);
        this.ground = ground;
        this.movementBehavior = new NormalMovement();
    }

    @Override
    public void update(float dt, boolean movementInput, float worldHeight) {
        this.movementInput = movementInput;
        movementBehavior.update(this, dt, movementInput, worldHeight);
    }

    public void keepPlayerInsideBounds(float worldHeight) {
        if (bounds.y < ground) {
            bounds.y = ground;
            vy = 0;
        }
        if (bounds.y > worldHeight - bounds.height) {
            bounds.y = worldHeight - bounds.height;
            vy = 0;
        }
    }

    @Override
    public void setPowerUp(PowerUpType powerUp){
        this.activePowerUp = powerUp;
        switch (powerUp) {
            case BIRD -> movementBehavior = new BirdMovement();
            case ROBOT -> movementBehavior = new RobotMovement();
            case GRAVITY_SUIT -> movementBehavior = new GravitySuitMovement();
            default -> movementBehavior = new NormalMovement();
        }
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

    public boolean getMovementInput() {
        return movementInput;
    }

    public boolean onCeiling(float worldHeight) {
        return (bounds.y == worldHeight - bounds.height);
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
