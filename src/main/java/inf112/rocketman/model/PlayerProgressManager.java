package inf112.rocketman.model;

import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerProgressManager {

    private final Preferences highscores;
    private final Preferences coins;

    public PlayerProgressManager(Preferences highscores, Preferences coins){
        this.highscores = highscores;
        this.coins = coins;
    }

    /**
     * Adds earned coins to the player's saved total.
     *
     * @param playerName the name of the player
     * @param earnedCoins the number of coins earned in the current game
     */
    public void addCoins(String playerName, int earnedCoins){
        int oldCoins = coins.getInteger(playerName, 0);
        coins.putInteger(playerName, oldCoins + earnedCoins);
        coins.flush();
    }

    /**
     * Gets the total number of saved coins for a player.
     *
     * @param playerName the name of the player
     * @return the player's saved coin total, or 0 if the player has no saved coins
     */
    public int getCoins(String playerName){
        return coins.getInteger(playerName, 0);
    }


    /**
     * Updates the saved highscores
     * This method should make sure that
     * the preference @highScores contain the 5 best scores seen so far
     *
     */
    public void updateHighscores(String playerName, int gameScore){
        Map<String, ?> allScores = highscores.get();

        int oldScore = highscores.getInteger(playerName, 0);
        if (gameScore <= oldScore && allScores.containsKey(playerName)){
            return;
        }

        if (allScores.size() < 5){
            highscores.putInteger(playerName, gameScore);
            highscores.flush();
            return;
        }

        String playerWithLowestScore = null;
        int lowestScore = Integer.MAX_VALUE;

        for (String key : allScores.keySet()){
            int score = highscores.getInteger(key);
            if (score < lowestScore){
                lowestScore = score;
                playerWithLowestScore = key;
            }
        }

        if (gameScore > lowestScore){
            highscores.remove(playerWithLowestScore);
            highscores.putInteger(playerName, gameScore);
            highscores.flush();
        }
    }


    /**
     * returns the highscore preference as a sorted list
     * @return sorted highscore list
     */
    public List<Map.Entry<String,Integer>> getSortedHighScoreList(){
        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>();

        for (String key : highscores.get().keySet()){
            sortedScores.add(Map.entry(key, highscores.getInteger(key)));
        }

        sortedScores.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        return sortedScores;
    }

}
