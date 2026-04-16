package inf112.rocketman.model.Coins;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CoinManager {
    private static final float START_COIN_TIMER = 10f;
    private static final float MIN_COIN_SPAWN_INTERVAL = 3f;
    private static final float MAX_COIN_SPAWN_INTERVAL = 10f;

    private final CoinFactory coinFactory;
    private final Random random;

    private final List<Coin> coinList = new ArrayList<>();
    private float coinTimer = START_COIN_TIMER;
    private int coinCount = 0;
    private boolean collectedCoinThisFrame = false;

    public CoinManager(CoinFactory coinFactory) {
        this.coinFactory = coinFactory;
        this.random = new Random();
    }

    /**
     * Updates coin spawning, movement and collection.
     *
     * @param dt time since last frame
     * @param playerHitbox player hitbox used for collision
     * @param worldWidth world width
     * @param worldHeight world height
     * @param ground ground level
     * @param margin margin used for spawning/off-screen checks
     * @param bgSpeed current background speed
     */
    public void update(float dt,
                       Rectangle playerHitbox,
                       float worldWidth,
                       float worldHeight,
                       float ground,
                       float margin,
                       float bgSpeed) {

        collectedCoinThisFrame = false;

        coinTimer -= dt;
        if (coinTimer <= 0f) {
            coinList.add(coinFactory.newCoin(worldWidth, worldHeight, ground, margin, bgSpeed));
            coinTimer = random.nextFloat(MIN_COIN_SPAWN_INTERVAL, MAX_COIN_SPAWN_INTERVAL);
        }

        Iterator<Coin> iterator = coinList.iterator();
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            coin.update(dt);
            coin.setVX(bgSpeed);

            if (playerHitbox.overlaps(coin.getHitbox())) {
                coinCount++;
                collectedCoinThisFrame = true;
                iterator.remove();
                continue;
            }

            if (coin.isOfScreen(worldHeight, margin)) {
                iterator.remove();
            }
        }
    }

    /**
     * Resets coin state for a new game.
     */
    public void reset() {
        coinList.clear();
        coinTimer = START_COIN_TIMER;
        coinCount = 0;
        collectedCoinThisFrame = false;
    }

    public List<Coin> getCoinList() {
        return new ArrayList<>(coinList);
    }

    public int getCoinCount() {
        return coinCount;
    }

    public boolean didCollectCoinThisFrame() {
        return collectedCoinThisFrame;
    }

    protected void addCoinForTesting(Coin coin) {
        coinList.add(coin);
    }
}