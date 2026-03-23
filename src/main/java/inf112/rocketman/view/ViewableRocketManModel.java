package inf112.rocketman.view;

import inf112.rocketman.model.Coins.Coin;
import inf112.rocketman.model.GameState;
import inf112.rocketman.model.Obstacles.IObstacle;
import inf112.rocketman.model.Character.TPowah;
import inf112.rocketman.model.PowerUps.PowerUp;

import java.util.List;

/**
 * A read-only interface representing the parts of the rocketMan game model
 * that are visible to the view components.
 * This interface follows the principle of least privilege, ensuring that
 * renderers can access game data for drawing purposes without being able
 * to modify the game state
 */
public interface ViewableRocketManModel {

    /**
     * @return the current horizontal scroll position of the backgorund.
     */
    float getBackgroundScrollX();

    /**
     * Retrieves the list of all active obstacles currently in the game world.
     *
     * @return a list of objects implementing {@link IObstacle}.
     */
    List<IObstacle> getObstacles();

    /**
     * Retrieves the player character object.
     *
     * @return the {@link TPowah} player instance.
     */
    TPowah getPlayer();

    /**
     * Checks if the player is currently activating the rocket's thrust.
     * this can be used by renderers to decide whether to draw engine flames.
     *
     * @return true if the rocket is thrusting, false otherwise.
     */
    boolean usingJetpack();

    /**
     * Checks if the player is currently on the ground
     *
     * @return true if the player is on the ground
     */
    boolean onGround();


    PowerUp getPowerUp();

    boolean hasBirdPowerUp();

    List<Coin> getCoinList();

    int getCoinCount();

    int getGameScore();

    boolean didCollectPowerUpThisFrame();

    boolean didCollectCoinThisFrame();
}

