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
        this.coinTexture = assets.getTexture("coin.png");
        this.font = assets.getFont();
        this.layout = new GlyphLayout();
    }

    public void render(SpriteBatch batch, ViewableRocketManModel model) {
        drawCoins(batch, model);
    }

    private void drawCoins(SpriteBatch batch, ViewableRocketManModel model) {
        for (Coin coin : model.getCoinList()) {
            batch.draw(coinTexture, coin.getX(), coin.getY(), coin.getWidth(), coin.getHeight()
            );
        }
    }
}