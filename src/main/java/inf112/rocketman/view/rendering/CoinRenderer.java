package inf112.rocketman.view.rendering;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.rocketman.model.coins.Coin;
import inf112.rocketman.view.ViewableRocketManModel;
import inf112.rocketman.view.assets.RocketManAssets;

/**
 * Renders coins in the game world
 */
public class CoinRenderer {

    private final Texture coinTexture;

    /**
     * Creates a new coin renderer.
     *
     * @param assets the asset manager used to load the coin texture
     */
    public CoinRenderer(RocketManAssets assets) {
        this.coinTexture = assets.getTexture("obstacles/coin.png");
    }

    /**
     * Renders all coins currently visible in the model.
     *
     * @param batch the sprite batch used for drawing
     * @param model the viewable game model containing the coins
     */
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