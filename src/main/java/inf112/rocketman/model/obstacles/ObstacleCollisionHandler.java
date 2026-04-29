package inf112.rocketman.model.obstacles;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.character.TPowah;
import inf112.rocketman.model.obstacles.flames.Flame;
import inf112.rocketman.model.obstacles.lazers.Lazer;

import java.util.Iterator;

/**
 * This class handles all the collisions with the player and the other objects in the game
 */
public class ObstacleCollisionHandler {
    private final TPowah player;
    private final ObstacleManager obstacleManager;

    public ObstacleCollisionHandler(TPowah player, ObstacleManager obstacleManager) {
        this.player = player;
        this.obstacleManager = obstacleManager;
    }

    /**
     * Handles Collision of all the objects with the player
     *
     * @return returns true if the player overlaps with one of the obstacles
     */
    public boolean handleObstacleCollision() {
        Rectangle playerHitbox = player.getHitBox();

        Iterator<IObstacle> iterator = obstacleManager.getObstacleListReference().iterator();
        while (iterator.hasNext()) {
            IObstacle obstacle = iterator.next();

            // If the lazer is not active, it should just continue.
            if (obstacle instanceof Lazer lazer &&  lazer.getProgressionLevel() != 3) {
                continue;
            }
            Rectangle obstacleHitbox = obstacle.getHitBox();

            if (checkCollision(obstacle, playerHitbox, obstacleHitbox)) {
                return true;
            }

            // If the object is not flame, it checks the collision with rectangles
        }
        return false;
    }

    private boolean checkCollision(IObstacle obstacle, Rectangle playerHitbox, Rectangle obstacleHitbox) {
        if (!(obstacle instanceof Flame)) {

            if (obstacleHitbox == null) {
                return false;
            }

            return playerHitbox.overlaps(obstacleHitbox);
        } else {
            return flameCollision(obstacle);
        }
    }

    /**
     * Handles collision for flame object separate since it is a polygon
     *
     * @param obstacle An object that the player can collide with
     * @return true if the player overlaps with the flame
     */
    private boolean flameCollision(IObstacle obstacle) {
        if (!(obstacle instanceof Flame)) {
            return false;
        }

        Polygon polyHitBox = player.getPolyHitBox();
        return Intersector.overlapConvexPolygons(((Flame) obstacle).getPolygon(), polyHitBox);
    }
}
