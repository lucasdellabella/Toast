package com.kopdb.toast;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kopdb.toast.Screens.MainMenuScreen;

import javax.swing.text.View;

public class ToastGame extends Game {
	public static float BOX_2D_SCALE = 0.01f;//Multiply going in divide going out
	private static SpriteBatch batch;
	private static World world;
	private static OrthographicCamera camera;
	private static Viewport viewport;

    @Override
	public void create () {
		batch = new SpriteBatch();
		world = new World(new Vector2(0, -98f * BOX_2D_SCALE), true);
		// Set up camera + viewport

		camera = new OrthographicCamera();
		getCamera().setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), getCamera());
		viewport.apply();
		getCamera().position.set(getCamera().viewportWidth / 2, getCamera().viewportHeight / 2, 0);

		this.setScreen(new MainMenuScreen(this));
	}

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static World getWorld() {
        return world;
    }

    public static OrthographicCamera getCamera() {
        return camera;
    }

    public static Viewport getViewport() {
        return viewport;
    }

	@Override
	public void dispose () {
		getBatch().dispose();
	}
}
