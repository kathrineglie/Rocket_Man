package inf112.rocketman.view.rendering;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.rocketman.model.Coins.Coin;
import inf112.rocketman.view.ViewableRocketManModel;
import inf112.rocketman.view.assets.RocketManAssets;

public class CoinRenderer {

    private final Texture coinTexture;

    public CoinRenderer(RocketManAssets assets) {
        this.coinTexture = assets.getTexture("coin.png");
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