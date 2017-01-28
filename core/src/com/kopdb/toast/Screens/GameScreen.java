package com.kopdb.toast.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kopdb.toast.*;
import com.kopdb.toast.Input.ToastInputAdapter;

/**
 * Created by Richard on 1/5/2017.
 */

public class GameScreen implements Screen {

    private final int REMOVE_TOAST_THRESHOLD = -8;
    private final ToastGame game;
    Toaster toaster;
    Viewport viewport;
    Array<Toast> toasts;

    float timeToNextToast = 0;

    public GameScreen(ToastGame game)
    {
        this.game = game;
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
            //game.setScreen(new GameScreen(game));
        }

        ToastGame.getWorld().step(delta,6,2);

        if (timeToNextToast <= 0) {
            addToast(new Texture(Gdx.files.internal("whitetoast.png")));
            timeToNextToast = 1;
        }

        for (int i = 0; i < toasts.size; i++) {
            Toast current = toasts.get(i);
            Vector2 curPos = current.getBody().getPosition();
            current.update();
            // Remove any toast that runs off the screen
            if (curPos.y < REMOVE_TOAST_THRESHOLD
                    || curPos.x / ToastGame.BOX_2D_SCALE > game.getViewport().getScreenWidth()
                    || curPos.x / ToastGame.BOX_2D_SCALE < - current.getSprite().getWidth()) {
                current.dispose();
                toasts.removeIndex(i);
            }
        }

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ToastGame.getCamera().update();
        ToastGame.getBatch().setProjectionMatrix(ToastGame.getCamera().combined);

        ToastGame.getBatch().begin();
        toaster.draw(ToastGame.getBatch());

        for (int i = 0; i < toasts.size; i++) {
            toasts.get(i).draw(ToastGame.getBatch());
        }

        ToastGame.getBatch().end();
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
        ToastGame.getWorld().destroyBody(toast.getBody());
        toast.dispose();
        return toast;
    }
}
