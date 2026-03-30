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



        GlyphLayout smallLayout = new GlyphLayout(smallFont, "PRESS ENTER TO RESTART");
        smallFont.draw(batch, smallLayout, width / 2f - smallLayout.width / 2f, height / 2f);

        batch.end();
    }

    private void renderHighScores(float width, float height){
        GlyphLayout scoreTitle = new GlyphLayout(smallFont, "HIGHSCORES:");
        smallFont.draw(batch, scoreTitle, width / 2f - scoreTitle.width / 2f, height / 2f + 70);

        List<Map.Entry<String, Integer>> highScores = controller.getViewableModel().getSortedHighScoreList();

        float startY = height /2f + 30;
        float lineSpacing = 30f;

        for (int i=1; i < (highScores.size() +1); i++){
            Map.Entry<String, Integer> score = highScores.get(i);
            String text = i + "." + score.getKey() + " - " + score.getValue();

            GlyphLayout scoreLayout = new GlyphLayout(smallFont, text);

            smallFont.draw(batch, scoreLayout, width /2f - scoreLayout.width / 2f, startY - i * lineSpacing);
        }

    }

}
