package inf112.rocketman.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

public class PauseScreen extends AbstractMenuScreen {

    public PauseScreen(Main game, RocketManController controller) {
        super(game, controller);
    }

    @Override
    public void render(float v) {
        controller.handleInput();

        controller.getView().render(controller.getViewableModel());

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,0.5f);
        shapeRenderer.rect(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float centerX = width / 2f;
        int score = controller.getViewableModel().getGameScore();
        int coins = controller.getViewableModel().getCoinCount();

        font.setColor(3f, 1, 1, 2f);
        drawCentered(font, "PAUSED", centerX, height / 2f + 100);

        smallFont.getData().setScale(1.8f);
        smallFont.setColor(Color.WHITE);
        drawCentered(smallFont, "Press P to resume", centerX, height / 2f);
        drawCentered(smallFont, "Press ESC to QUIT to Menu", centerX, height / 2f - 80);
        drawCentered(smallFont, "Sore: " + score + "M", centerX, height / 2f - 140);
        drawCentered(smallFont, "Coins: " + coins, centerX, height / 2f - 190);
        batch.end();

    }

    private void drawCentered(BitmapFont font, String text, float centerX, float y) {
        GlyphLayout layout = new GlyphLayout(font, text);
        font.draw(batch, layout, centerX - layout.width / 2f, y);
    }
}
