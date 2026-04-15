package inf112.rocketman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import inf112.rocketman.view.assets.RocketManAssets;
import inf112.rocketman.view.rendering.*;


public class RocketManView {
    private Viewport viewport; // defines screen / world size, aspect ratio and camera
	private SpriteBatch batch; // used for drawing images / textures
	private ShapeRenderer shape; // used for drawing shapes

	private RocketManAssets assets;

	private BackgroundRenderer backgroundRenderer;
	private PlayerRenderer playerRenderer;
	private ObstacleRenderer obstacleRenderer;
	private PowerUpRenderer powerUpRenderer;
	private CoinRenderer coinRenderer;
	private HudRenderer hudRenderer;

	public void create(double worldWidth, double worldHeight) {
		this.viewport = new FitViewport((float) worldWidth, (float) worldHeight);
		this.batch = new SpriteBatch();
		this.shape = new ShapeRenderer();

		assets = new RocketManAssets();
		assets.create();

		coinRenderer = new CoinRenderer(assets);
		backgroundRenderer = new BackgroundRenderer(assets);
		playerRenderer = new PlayerRenderer(assets);
		obstacleRenderer = new ObstacleRenderer(assets);
		powerUpRenderer = new PowerUpRenderer(assets);
		hudRenderer = new HudRenderer(assets);

		Gdx.graphics.setForegroundFPS(60);
	}


	public void dispose() {
		batch.dispose();
		shape.dispose();
		assets.dispose();
	}

	/**
	 * Example:
	 *
	 * {@snippet :
	 * appView.render(painter -> {
	 * 	painter.draw(x, y, w, h, tex1);
	 * 	painter.draw(x, y, w, h, tex2);
	 * });
	 * }
	 *
	 * TODO: probably easier to just accept a list of objects to draw.
	 *
	 * See lectures for better examples.
	 *
	 * Doing it this way has the advantage of making sure that SpriteBatch's begin()
	 * and end() methods are called properly.
	 *
	 * @param model
	 */
	public void render(ViewableRocketManModel model) {
		Color background = new Color(246f/255f, 136f/255f, 197f/255f, 1f);
		ScreenUtils.clear(background);

		viewport.apply();
		viewport.getCamera().update();

		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();

		backgroundRenderer.render(batch, viewport, model);
		playerRenderer.render(batch, model);
		obstacleRenderer.render(batch, model);
		powerUpRenderer.render(batch, model);
		coinRenderer.render(batch,model);
		hudRenderer.render(batch, viewport, model);

		batch.end();
		playerRenderer.renderDebug(batch, model);
	}

	public RocketManAssets getAssets() {
		return assets;
	}

	public Vector2 getMouseWorldPosition() {
		Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		viewport.unproject(mouse);
		return new Vector2(mouse.x, mouse.y);
	}

	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	public double worldWidth() {
		return viewport.getWorldWidth();
	}

	public double worldHeight() {
		return viewport.getWorldHeight();
	}

	public float getWorldWidth(){
		return viewport.getWorldWidth();
	}

	public float getWorldHeight(){
		return viewport.getWorldHeight();
	}

}
