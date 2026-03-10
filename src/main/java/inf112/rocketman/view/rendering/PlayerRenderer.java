package inf112.rocketman.view.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import inf112.rocketman.model.Character.TPowah;
import inf112.rocketman.model.Obstacles.Flames.Flame;
import inf112.rocketman.model.Obstacles.IObstacle;
import inf112.rocketman.view.TextureProvider;
import inf112.rocketman.view.ViewableRocketManModel;
import inf112.rocketman.model.PowerUps.Bird;

public class PlayerRenderer {
    private final TextureProvider textures;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public PlayerRenderer(TextureProvider textures){
        this.textures = textures;
    }

    public void render(SpriteBatch batch, ViewableRocketManModel model){
        if (model.hasBirdPowerUp()) {
            Bird bird = model.getBird();
            if (bird != null) {
                batch.draw(
                        textures.getTexture("PowerUps/Bird.png"),
                        bird.getX(),
                        bird.getY(),
                        bird.getWidth(),
                        bird.getHeight()
                );
            }
            return;
        }

        TPowah player = model.getPlayer();

        String playerImg;

        if (model.isThrusting()){
            playerImg = "TPowah/TPowahFlames.png";
        } else{
            playerImg = "TPowah/TPowah.png";
        }

        batch.draw(textures.getTexture(playerImg), player.getX(), player.getY(), player.getWidth(), player.getHeight());

    }

    public void renderDebug(SpriteBatch batch, ViewableRocketManModel model) {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        // Tegn spillerens hitbox
        Polygon pPoly = model.getPlayer().getPolyHitBox();
        shapeRenderer.polygon(pPoly.getTransformedVertices());

        // Tegn flammene sine hitboxer
        for (IObstacle obs : model.getObstacles()) {
            if (obs instanceof Flame) {
                shapeRenderer.polygon(((Flame) obs).getPolygon().getTransformedVertices());
            }
        }
        shapeRenderer.end();
    }
}
