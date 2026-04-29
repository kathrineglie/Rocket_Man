package inf112.rocketman.view.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import inf112.rocketman.model.character.ViewableTPowah;
import inf112.rocketman.model.powerups.PowerUpType;
import inf112.rocketman.view.TextureProvider;
import inf112.rocketman.view.ViewableRocketManModel;

/**
 * Renders the player character in the game.
 *
 * <p>This renderer selects the correct animation or texture based  on the
 * player's state, active power-up, and cosmetic settings.</p>
 */
public class PlayerRenderer {
    private final TextureProvider textures;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    float stateTime;

    private final Animation<TextureRegion> runAnimation;
    private final Animation<TextureRegion> birdAnimation;
    private final Animation<TextureRegion> robotAnimation;
    private final Animation<TextureRegion> gravityAnimationDown;
    private final Animation<TextureRegion> gravityAnimationUp;

    private final Animation<TextureRegion> runAnimationPirate;
    private final Animation<TextureRegion> birdAnimationPirate;

    /**
     * Creates a new player renderer.
     *
     * @param textures the texture provider used to access player textures
     */
    public PlayerRenderer(TextureProvider textures) {
        this.textures = textures;

        runAnimationPirate = new Animation<>(0.1f,
                new TextureRegion(textures.getTexture("tpowah/run1pirate.png")),
                new TextureRegion(textures.getTexture("tpowah/run2pirate.png")),
                new TextureRegion(textures.getTexture("tpowah/run3pirate.png")),
                new TextureRegion(textures.getTexture("tpowah/run4pirate.png"))
        );

        birdAnimationPirate = new Animation<>(0.2f,
                new TextureRegion(textures.getTexture("powerups/bird_pirate.png")),
                new TextureRegion(textures.getTexture("powerups/birdUp_pirate.png"))
        );

        robotAnimation = new Animation<>(0.1f,
                new TextureRegion(textures.getTexture("powerups/run1.png")),
                new TextureRegion(textures.getTexture("powerups/run2.png")),
                new TextureRegion(textures.getTexture("powerups/run3.png")),
                new TextureRegion(textures.getTexture("powerups/run4.png"))
        );

        runAnimation = new Animation<>(0.1f,
                new TextureRegion(textures.getTexture("tpowah/run1.png")),
                new TextureRegion(textures.getTexture("tpowah/run2.png")),
                new TextureRegion(textures.getTexture("tpowah/run3.png")),
                new TextureRegion(textures.getTexture("tpowah/run4.png"))
        );

        birdAnimation = new Animation<>(0.2f,
                new TextureRegion(textures.getTexture("powerups/bird.png")),
                new TextureRegion(textures.getTexture("powerups/birdUP.png"))
        );


        gravityAnimationDown = new Animation<>(0.1f,
                new TextureRegion(textures.getTexture("powerups/gravity1.png")),
                new TextureRegion(textures.getTexture("powerups/gravity2.png")),
                new TextureRegion(textures.getTexture("powerups/gravity3.png")),
                new TextureRegion(textures.getTexture("powerups/gravity4.png"))
        );

        gravityAnimationUp = new Animation<>(0.1f,
                new TextureRegion(textures.getTexture("powerups/gravityUP1.png")),
                new TextureRegion(textures.getTexture("powerups/gravityUP2.png")),
                new TextureRegion(textures.getTexture("powerups/gravityUP3.png")),
                new TextureRegion(textures.getTexture("powerups/gravityUP4.png"))
        );
    }

    public void render(SpriteBatch batch, ViewableRocketManModel model) {
        ViewableTPowah player = model.getPlayer();
        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion region = getPlayerRegion(player, model);

        if (region != null) {
            batch.draw(region, player.getX(), player.getY(), player.getWidth(), player.getHeight());
        }
    }

    private TextureRegion getPlayerRegion(ViewableTPowah player, ViewableRocketManModel model) {
        boolean hasPirateHat = model.hasPirateHat();

        if (player.getActivePowerUp() == PowerUpType.BIRD) {
            return getBirdRegion(hasPirateHat);
        }

        if (player.getActivePowerUp() == PowerUpType.ROBOT) {
            return getRobotRegion(player);
        }

        if (player.getActivePowerUp() == PowerUpType.GRAVITY_SUIT) {
            return getGravityRegion(player, model);
        }

        return getNormalRegion(player, model, hasPirateHat);
    }

    private TextureRegion getBirdRegion(boolean hasPirateHat) {
        if (hasPirateHat) {
            return birdAnimationPirate.getKeyFrame(stateTime, true);
        }
        return birdAnimation.getKeyFrame(stateTime, true);
    }

    private TextureRegion getRobotRegion(ViewableTPowah player) {
        if (player.onGround()) {
            return robotAnimation.getKeyFrame(stateTime, true);
        }

        if (player.getMovementInput()) {
            return new TextureRegion(textures.getTexture("powerups/fly_flame.png"));
        }

        return new TextureRegion(textures.getTexture("powerups/fly.png"));
    }

    private TextureRegion getGravityRegion(ViewableTPowah player, ViewableRocketManModel model) {
        if (player.onGround()) {
            return gravityAnimationDown.getKeyFrame(stateTime, true);
        }

        if (player.onCeiling(model.getWorldHeight())) {
            return gravityAnimationUp.getKeyFrame(stateTime, true);
        }

        if (player.isGoingDown()) {
            return new TextureRegion(textures.getTexture("powerups/down.png"));
        }

        return new TextureRegion(textures.getTexture("powerups/up.png"));
    }

    private TextureRegion getNormalRegion(
            ViewableTPowah player,
            ViewableRocketManModel model,
            boolean hasPirateHat
    ) {
        if (model.usingJetpack()) {
            return new TextureRegion(textures.getTexture(
                    hasPirateHat
                            ? "tpowah/jetpack_flames_pirate.png"
                            : "tpowah/jetpack_flames.png"
            ));
        }

        if (player.onGround()) {
            return hasPirateHat
                    ? runAnimationPirate.getKeyFrame(stateTime, true)
                    : runAnimation.getKeyFrame(stateTime, true);
        }

        return new TextureRegion(textures.getTexture(
                hasPirateHat
                        ? "tpowah/jetpack_pirate.png"
                        : "tpowah/jetpack.png"
        ));
    }

    /**
     * Renders debug information for the player.
     *
     * <p>This can be used for draw hitboxes or other debugging visuals.</p>
     *
     * @param batch the sprite batch used for drawing
     * @param model the viewable game model containing player data
     */
    public void renderDebug(SpriteBatch batch, ViewableRocketManModel model) {

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.end();
    }
}
