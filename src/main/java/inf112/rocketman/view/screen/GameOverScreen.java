package inf112.rocketman.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

import java.util.List;
import java.util.Map;

public class GameOverScreen extends AbstractMenuScreen {

    public GameOverScreen(Main game, RocketManController controller, SpriteBatch batch) {
        super(game, controller, batch);
    }

    @Override
    public void render(float v) {
        controller.handleInput();

        ScreenUtils.clear(0.2f, 0.05f, 0.05f, 1);

        batch.begin();

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        font.setColor(Color.RED);
        GlyphLayout layout = new GlyphLayout(font, "GAME OVER");

        float titleX = width / 2f - layout.width / 2f;
        float titleY = height - 80;
        font.draw(batch, layout, titleX, titleY);

        smallFont.setColor(Color.WHITE);

        float nextY = renderRunSummary(width, titleY);
        renderHighScores(width, nextY);

        GlyphLayout restartLayout = new GlyphLayout(smallFont, "PRESS ENTER TO RESTART");
        smallFont.draw(batch, restartLayout, width / 2f - restartLayout.width / 2f, 160);

        batch.end();
    }

    private float renderRunSummary(float width, float gameOverTitleY) {
        String playerName = controller.getViewableModel().getPlayerName();
        int distance = controller.getViewableModel().getGameScore();
        int coinsThisRun = controller.getViewableModel().getCoinCount();
        int totalCoins = controller.getViewableModel().getSavedCoinsForPlayer(playerName);

        String distanceText = "Distance: " + distance + " m";
        String coinsThisRunText = "Coins this run: " + coinsThisRun;
        String totalCoinsText = "Total coins: " + totalCoins;

        float startY = gameOverTitleY - 90;
        float lineSpacing = 35f;

        GlyphLayout distanceLayout = new GlyphLayout(smallFont, distanceText);
        GlyphLayout coinsRunLayout = new GlyphLayout(smallFont, coinsThisRunText);
        GlyphLayout totalCoinsLayout = new GlyphLayout(smallFont, totalCoinsText);

        smallFont.draw(batch, distanceLayout, width / 2f - distanceLayout.width / 2f, startY);
        smallFont.draw(batch, coinsRunLayout, width / 2f - coinsRunLayout.width / 2f, startY - lineSpacing);
        smallFont.draw(batch, totalCoinsLayout, width / 2f - totalCoinsLayout.width / 2f, startY - 2 * lineSpacing);

        return startY - 2 * lineSpacing - 100;
    }

    private float renderHighScores(float width, float startTopY) {
        GlyphLayout scoreTitle = new GlyphLayout(smallFont, "HIGHSCORES:");
        smallFont.draw(batch, scoreTitle, width / 2f - scoreTitle.width / 2f, startTopY);

        List<Map.Entry<String, Integer>> highScores = controller.getViewableModel().getSortedHighScoreList();

        float startY = startTopY - 50;
        float lineSpacing = 35f;
        int numberToShow = Math.min(5, highScores.size());

        for (int i = 0; i < numberToShow; i++) {
            Map.Entry<String, Integer> score = highScores.get(i);
            String text = (i + 1) + ". " + score.getKey() + " - " + score.getValue();

            GlyphLayout scoreLayout = new GlyphLayout(smallFont, text);
            float y = startY - i * lineSpacing;
            smallFont.draw(batch, scoreLayout, width / 2f - scoreLayout.width / 2f, y);
        }

        return startY - numberToShow * lineSpacing;
    }
}
