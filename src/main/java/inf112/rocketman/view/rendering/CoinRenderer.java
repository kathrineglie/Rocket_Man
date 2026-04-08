package inf112.rocketman.view.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.rocketman.model.Coins.Coin;
import inf112.rocketman.view.ViewableRocketManModel;
import inf112.rocketman.view.assets.RocketManAssets;

public class CoinRenderer {

    private final Texture coinTexture;
    private final BitmapFont font;
    private final GlyphLayout layout;

    public CoinRenderer(RocketManAssets assets) {
        this.coinTexture = assets.getTexture("TCoin.png");
        this.font = assets.getFont();
        this.layout = new GlyphLayout();
    }

    public void render(SpriteBatch batch, Viewport viewport, ViewableRocketManModel model) {
        drawCoins(batch, model);
        drawHud(batch, viewport, model);
    }

    private void drawCoins(SpriteBatch batch, ViewableRocketManModel model) {
        for (Coin coin : model.getCoinList()) {
            batch.draw(coinTexture, coin.getX(), coin.getY(), coin.getWidth(), coin.getHeight()
            );
        }
    }

    private void drawHud(SpriteBatch batch, Viewport viewport, ViewableRocketManModel model) {
        font.getData().setScale(1.25f);
        font.setColor(Color.WHITE);

        float margin = 15f;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        String scoreText = "Score: " + model.getGameScore() + "M";
        layout.setText(font, scoreText);
        float scoreX = worldWidth - layout.width - margin;
        float scoreY = worldHeight - margin;
        font.draw(batch, layout, scoreX, scoreY);

        font.setColor(Color.ORANGE);
        String coinText = "Coins: " + model.getCoinCount();
        layout.setText(font, coinText);
        float coinX = worldWidth - layout.width - margin;
        float coinY = worldHeight - margin - 40f;
        font.draw(batch, layout, coinX, coinY);
    }
}