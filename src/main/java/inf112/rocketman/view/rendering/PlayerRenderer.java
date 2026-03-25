package inf112.rocketman.view.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import inf112.rocketman.model.Character.TPowah;
import inf112.rocketman.model.Obstacles.Flames.Flame;
import inf112.rocketman.model.Obstacles.IObstacle;
import inf112.rocketman.model.PowerUps.PowerUpType;
import inf112.rocketman.view.TextureProvider;
import inf112.rocketman.view.ViewableRocketManModel;

public class PlayerRenderer {
    private final TextureProvider textures;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    float stateTime;

    private final Animation<TextureRegion> runAnimation;
    private final Animation<TextureRegion> birdAnimation;
    private final Animation<TextureRegion> robotAnimation;


    public PlayerRenderer(TextureProvider textures){
        this.textures = textures;

        runAnimation = new Animation<>(0.1f,
                new TextureRegion(textures.getTexture("TPowah/run1.png")),
                new TextureRegion(textures.getTexture("TPowah/run2.png")),
                new TextureRegion(textures.getTexture("TPowah/run3.png")),
                new TextureRegion(textures.getTexture("TPowah/run4.png")));

        birdAnimation = new Animation<>(0.2f,
                new TextureRegion(textures.getTexture("PowerUps/bird.png")),
                new TextureRegion(textures.getTexture("PowerUps/birdUP.png")));

        robotAnimation = new Animation<>(0.2f,
                new TextureRegion(textures.getTexture("PowerUps/run1.png")),
                new TextureRegion(textures.getTexture("PowerUps/run2.png")),
                new TextureRegion(textures.getTexture("PowerUps/run3.png")),
                new TextureRegion(textures.getTexture("PowerUps/run4.png")));
    }

    public void render(SpriteBatch batch, ViewableRocketManModel model){
        TPowah player = model.getPlayer();
        TextureRegion region = null;
        String playerImg = null;

        boolean spacePressed = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
        stateTime += Gdx.graphics.getDeltaTime();

        if (player.getActivePowerUp() == PowerUpType.BIRD) {
            region = birdAnimation.getKeyFrame(stateTime, true);
        } else if (player.getActivePowerUp() == PowerUpType.ROBOT) {
            if (player.onGround()) {
                region = robotAnimation.getKeyFrame(stateTime,true);
            } else if (player.getMovementInput()){
                playerImg = "PowerUps/fly_flame.png";
            } else {
                playerImg = "PowerUps/fly.png";
            }
        }
        else if (model.usingJetpack()){
            playerImg = "TPowah/jetpack_flames.png";
        } else if (player.onGround()) {
            region = runAnimation.getKeyFrame(stateTime, true);
        } else {
            playerImg = "TPowah/jetpack.png";
        }

        if (region != null) {
            batch.draw(region, player.getX(), player.getY(), player.getWidth(), player.getHeight());
        } else {
            batch.draw(textures.getTexture(playerImg), player.getX(), player.getY(), player.getWidth(), player.getHeight());
        }
    }

    public void renderDebug(SpriteBatch batch, ViewableRocketManModel model) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.end();
    }
}
