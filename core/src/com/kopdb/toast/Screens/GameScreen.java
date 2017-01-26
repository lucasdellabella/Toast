package com.kopdb.toast.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kopdb.toast.*;
import com.kopdb.toast.Input.ToastInputAdapter;

/**
 * Created by Richard on 1/5/2017.
 */

public class GameScreen implements Screen {

    private final ToastGame game;

    Toaster toaster;
    OrthographicCamera camera;
    Viewport viewport;
    Array<Toast> toasts;

    float timeToNextToast = 0;

    public GameScreen(ToastGame game)
    {
        this.game = game;

        // Set up camera + viewport
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        toaster = new Toaster(new Texture(Gdx.files.internal("toaster.jpg")));
        toasts = new Array<Toast>();

        Gdx.input.setInputProcessor(new ToastInputAdapter(toasts));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        timeToNextToast -= delta;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
        }

        ToastGame.world.step(delta,6,2);

        if (timeToNextToast <= 0) {
            addToast(new Texture(Gdx.files.internal("whitetoast.png")));
            timeToNextToast = 1;
        }

        for (int i = 0; i < toasts.size; i++) {
            toasts.get(i).update();
        }

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        ToastGame.batch.setProjectionMatrix(camera.combined);

        ToastGame.batch.begin();
        toaster.draw(ToastGame.batch);

        for (int i = 0; i < toasts.size; i++) {
            toasts.get(i).draw(ToastGame.batch);
        }

        ToastGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        for (int i = 0; i < toasts.size; i++) {
            destroyToast(toasts.get(i));
        }
    }

    private Toast addToast(Texture texture)
    {
        Toast toast=new Toast(texture);
        toasts.add(toast);

        return toast;
    }
    private Toast destroyToast(Toast toast)
    {
        toasts.removeValue(toast,true);
        ToastGame.world.destroyBody(toast.getBody());
        toast.dispose();
        return toast;
    }
}
