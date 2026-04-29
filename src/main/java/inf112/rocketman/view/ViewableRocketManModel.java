package inf112.rocketman.view;

import inf112.rocketman.model.WorldDimensions;
import inf112.rocketman.model.coins.Coin;
import inf112.rocketman.model.obstacles.IObstacle;
import inf112.rocketman.model.character.ViewableTPowah;
import inf112.rocketman.model.powerups.PowerUp;

import java.util.List;
import java.util.Map;

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
     * @return the {@link ViewableTPowah} player instance.
     */
    ViewableTPowah getPlayer();

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

    /**
     * Returns the current power-up in the game world.
     *
     * @return the current power-up, or {@code null} if no power-up is active
     */
    PowerUp getPowerUp();

    /**
     * Returns the current power-up in the game world.
     *
     * @return the current power-up, or {@code null} if no power-up is active
     */
    boolean hasBirdPowerUp();

    /**
     * Returns the list of coins currently in the game world.
     *
     * @return a list of active coins
     */
    List<Coin> getCoinList();

    /**
     * Returns the list of coins currently in the game world.
     *
     * @return a list of active coins
     */
    int getCoinCount();

    /**
     * Returns the current game score.
     *
     * @return the current score
     */
    int getGameScore();

    /**
     * Checks whether a power-up was collected during the current frame.
     *
     * @return true if a power-up was collected this frame, false otherwise
     */
    boolean didCollectPowerUpThisFrame();

    /**
     * Checks whether a coin was collected during the current frame.
     *
     * @return true if a coin was collected this frame, false otherwise
     */
    boolean didCollectCoinThisFrame();

    /**
     * Checks whether the player currently has the pirate hat cosmetic.
     *
     * @return true if the player has the pirate hat, false otherwise
     */
    boolean hasPirateHat();
    /**
     * returns the highscore preference as a sorted list
     * @return sorted highscore list
     */
    List<Map.Entry<String,Integer>> getSortedHighScoreList();


    /**
     * Gets the total number of saved coins for the given player.
     *
     * @param playerName the name of the player
     * @return the player's saved coin total
     */
    int getSavedCoinsForPlayer(String playerName);

    /**
     * Gets the name of the current plauer
     *
     * @return the current player's name
     */
    String getPlayerName();

    /**
     * Checks if the player has the gravity suit powerup
     *
     * @return true if the player has the gravity suit powerup
     */
    boolean hasGravitySuitPowerUp();

    /**
     * Gets the dimensions of the game
     * @return the dimensions of the game
     */

    WorldDimensions getWorldDimensions();

    /**
     * Returns the margin size used to define the playable area boundaries.
     *
     * @return the margin distance from the screen edges
     */
    float getMargin();
}

