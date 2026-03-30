package inf112.rocketman.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

import java.util.List;
import java.util.Map;

public class GameOverScreen extends AbstractMenuScreen{

    public GameOverScreen(Main game, RocketManController controller) {
        super(game, controller);
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
        float titleY = height / 2f + 140;

        font.draw(batch, layout, titleX, titleY );
        smallFont.setColor(Color.WHITE);

        renderHighScores(width, height);



        float restartY = renderHighScores(width, height);

        GlyphLayout smallLayout = new GlyphLayout(smallFont, "PRESS ENTER TO RESTART");
        smallFont.draw(batch, smallLayout, width / 2f - smallLayout.width / 2f, restartY);

        batch.end();
    }

    private float renderHighScores(float width, float height) {
        GlyphLayout scoreTitle = new GlyphLayout(smallFont, "HIGHSCORES:");
        float titleY = height / 2f + 70;
        smallFont.draw(batch, scoreTitle, width / 2f - scoreTitle.width / 2f, titleY);

        List<Map.Entry<String, Integer>> highScores = controller.getViewableModel().getSortedHighScoreList();

        float startY = titleY - 45;
        float lineSpacing = 60f;

        int numberToShow = Math.min(5, highScores.size());

        for (int i = 0; i < numberToShow; i++) {
            Map.Entry<String, Integer> score = highScores.get(i);
            String text = (i + 1) + ". " + score.getKey() + " - " + score.getValue();

            GlyphLayout scoreLayout = new GlyphLayout(smallFont, text);
            float y = startY - i * lineSpacing;
            smallFont.draw(batch, scoreLayout, width / 2f - scoreLayout.width / 2f, y);
        }

        return startY - numberToShow * lineSpacing - 30;
    }


}
