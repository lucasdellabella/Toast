package com.kopdb.toast.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kopdb.toast.*;
import com.kopdb.toast.Input.ToastInputAdapter;

/**
 * Created by Richard on 1/5/2017.
 */

public class GameScreen implements Screen {

    private final float REMOVE_TOAST_THRESHOLD = 0;
    private final ToastGame game;
    private BitmapFont font;
    private Toaster toaster;
    private Array<Toast> toasts;
    private ObjectIntMap<String> flickCountByType;
    private float timeToNextToast = 0;
    private int totalFlickCount = 0;


    public GameScreen(ToastGame game)
    {
        this.game = game;
        toaster = new Toaster(new Texture(Gdx.files.internal("toaster.jpg")));
        toasts = new Array<Toast>();
        flickCountByType = new ObjectIntMap<>();
        font = new BitmapFont();

        Gdx.input.setInputProcessor(new ToastInputAdapter(toasts));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ToastGame.getCamera().update();
        ToastGame.getBatch().setProjectionMatrix(ToastGame.getCamera().combined);

        ToastGame.getBatch().begin();
        toaster.draw(ToastGame.getBatch());
        for (int i = 0; i < toasts.size; i++) {
            toasts.get(i).draw(ToastGame.getBatch());
        }

        // draw the counter
        font.draw(game.getBatch(),
                "" + totalFlickCount,
                game.getViewport().getScreenWidth() - 40,
                game.getViewport().getScreenHeight() - 40);
        ToastGame.getBatch().end();

        ToastGame.getWorld().step(delta,6,2);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
        }

        // spawn a toast every second
        timeToNextToast -= delta;
        if (timeToNextToast <= 0) {
            addToast("white");
            timeToNextToast = 1;
        }

        // update and remove toasts
        for (int i = 0; i < toasts.size; i++) {
            Toast current = toasts.get(i);
            Vector2 curPos = current.getBody().getPosition();
            current.update();
            // Remove any toast that runs off the screen
            if (curPos.y / ToastGame.BOX_2D_SCALE
                    + current.getSprite().getHeight() < REMOVE_TOAST_THRESHOLD
                    || curPos.x / ToastGame.BOX_2D_SCALE > game.getViewport().getScreenWidth()
                    || curPos.x / ToastGame.BOX_2D_SCALE < - current.getSprite().getWidth()) {
                current.dispose();
                toasts.removeIndex(i);
                flickCountByType.getAndIncrement(current.getType(), 0, 1);
                totalFlickCount += 1;
            }
        }
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

    private Toast addToast(String toastType)
    {
        Toast toast = new Toast(toastType);
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
