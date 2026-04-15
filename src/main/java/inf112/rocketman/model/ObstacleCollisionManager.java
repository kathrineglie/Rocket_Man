package inf112.rocketman.model;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Character.TPowah;
import inf112.rocketman.model.Obstacles.Flames.Flame;
import inf112.rocketman.model.Obstacles.IObstacle;
import inf112.rocketman.model.Obstacles.Lazers.Lazer;
import inf112.rocketman.model.Obstacles.ObstacleManager;

import java.util.Iterator;

/**
 * This class handles all the collisions with the player and the other objects in the game
 */
public class ObstacleCollisionManager {
    private final TPowah player;
    private final ObstacleManager obstacleManager;

    ObstacleCollisionManager(TPowah player, ObstacleManager obstacleManager) {
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
            if (obstacle instanceof Lazer && ((Lazer) obstacle).getProgressionLevel() != 3) {
                continue;
            }

            // If the object is not flame, it checks the collision with rectangles
            if (!(obstacle instanceof Flame)) {
                Rectangle obstacleHitbox = obstacle.getHitBox();

                if (obstacleHitbox == null) {
                    continue;
                }

                if (playerHitbox.overlaps(obstacleHitbox)) {
                    return true;
                }
            } else {
                if (flameCollision(obstacle)) {
                    return true;
                }
            }
        }
        return false;
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
