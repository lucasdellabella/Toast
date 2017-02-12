package com.kopdb.toast.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.kopdb.toast.*;
import com.kopdb.toast.Input.ToastInputAdapter;

/**
 * Created by Richard on 1/5/2017.
 */

public class GameScreen implements Screen {

    private final float REMOVE_TOAST_THRESHOLD = 0;
    private final int NUM_UNIQUE_TOASTS = 4;
    private final ToastGame game;
    private final Texture backgroundImage;
    private BitmapFont font;
    private Array<Toast> toasts;
    private ObjectIntMap<String> flickCountByType;
    private float timeToNextToast = 0;
    private int totalFlickCount = 0;
    //Box2DDebugRenderer debugRenderer;


    public GameScreen(ToastGame game)
    {
        this.game = game;
        backgroundImage = new Texture(Gdx.files.internal("sky.png"));

        toasts = new Array<Toast>();
        flickCountByType = new ObjectIntMap<>();

        ToastGame.getCamera().position.set(ToastGame.getCamera().viewportWidth / 2, ToastGame.getCamera().viewportHeight / 2, 0);

        // Use TTF for font writer
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fatpen" +
                ".ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);

        Gdx.input.setInputProcessor(new ToastInputAdapter(toasts));
        //debugRenderer = new Box2DDebugRenderer();
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
        ToastGame.getBatch().draw(backgroundImage,
                0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (int i = 0; i < toasts.size; i++) {
            toasts.get(i).draw(ToastGame.getBatch());
        }

        // draw the counter
        font.draw(game.getBatch(),
                "" + totalFlickCount,
                game.getViewport().getScreenWidth() / 8,
                game.getViewport().getScreenHeight() * 13 / 14);
        ToastGame.getBatch().end();

        ToastGame.getWorld().step(delta,6,2);
        //Matrix4 debugMatrix=new Matrix4(ToastGame.getCamera().combined);
        //debugMatrix.scale(1/ToastGame.BOX_2D_SCALE, 1/ToastGame.BOX_2D_SCALE,1f);
        //debugRenderer.render(ToastGame.getWorld(), debugMatrix);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new MainMenuScreen(game));
        }

        // spawn a toast every second
        timeToNextToast -= delta;
        if (timeToNextToast <= 0) {
            addToast();
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

    private Toast addToast()
    {
        String toastType;
        int randInt = MathUtils.random(NUM_UNIQUE_TOASTS);

        switch (randInt) {
            case 0:
                toastType = "stash";
                break;
            case 1:
                toastType = "happy";
                break;
            case 2:
                toastType = "chef";
                break;
            case 3:
                toastType = "explorer";
                break;
            default:
                toastType = "happy";
                break;
        }

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
