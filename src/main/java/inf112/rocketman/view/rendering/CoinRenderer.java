package inf112.rocketman.view.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.rocketman.model.Coins.Coin;
import inf112.rocketman.view.TextureProvider;
import inf112.rocketman.view.ViewableRocketManModel;

public class CoinRenderer {
    private final TextureProvider textures;
    public CoinRenderer(TextureProvider textures) {
        this.textures = textures;
    }

    public void render(SpriteBatch batch, ViewableRocketManModel model) {
        Texture texture;
        texture = textures.getTexture("TCoin.png");
        for (Coin coin : model.getCoinList()) {
                batch.draw(texture, coin.getX(), coin.getY(), coin.getWidth(), coin.getWidth());
        }
    }
}

