package inf112.rocketman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;

import inf112.rocketman._example.view.Painter;
import inf112.rocketman.view.assets.RocketManAssets;
import inf112.rocketman.view.rendering.BackgroundRenderer;
import inf112.rocketman.view.rendering.ObstacleRenderer;
import inf112.rocketman.view.rendering.PlayerRenderer;


public class RocketManView implements Painter {
    private Viewport viewport; // defines screen / world size, aspect ratio and camera
	private SpriteBatch batch; // used for drawing images / textures
	private ShapeRenderer shape; // used for drawing shapes

	private RocketManAssets assets;

	private BackgroundRenderer backgroundRenderer;
	private PlayerRenderer playerRenderer;
	private ObstacleRenderer obstacleRenderer;

	public void create(double worldWidth, double worldHeight) {
		this.viewport = new FitViewport((float) worldWidth, (float) worldHeight);
		this.batch = new SpriteBatch();
		this.shape = new ShapeRenderer();

		assets = new RocketManAssets();
		assets.create();

		backgroundRenderer = new BackgroundRenderer(assets);
		playerRenderer = new PlayerRenderer(assets);
		obstacleRenderer = new ObstacleRenderer(assets);


		Gdx.graphics.setForegroundFPS(60);
	}


	public void dispose() {
		batch.dispose();
		shape.dispose();
		assets.dispose();
	}

	@Override
	public void draw(double x, double y, double w, double h, String textureName) {
		Texture texture = assets.getTexture(textureName);
		if (texture != null) {
			batch.draw(texture, //
					(float) x, //
					(float) y, //
					(float) w, //
					(float) h);
		}
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
	 * @param drawCommands
	 */
	public void render(ViewableRocketManModel model) {
		ScreenUtils.clear(Color.WHITE);

		viewport.apply();
		viewport.getCamera().update();

		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();

		backgroundRenderer.render(batch, viewport, model);
		playerRenderer.render(batch, model);
		obstacleRenderer.render(batch, model);

		batch.end();
	}

	public RocketManAssets getAssets() {
		return assets;
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
