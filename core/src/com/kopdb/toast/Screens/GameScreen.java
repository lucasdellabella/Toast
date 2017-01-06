package com.kopdb.toast.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kopdb.toast.*;

/**
 * Created by Richard on 1/5/2017.
 */

public class GameScreen implements Screen {

    private final ToastGame game;

    SpriteBatch spriteBatch;
    World physicsWorld;
    Sprite toaster;
    Array<Toast> toasts;

    int frames = 0;

    public GameScreen(ToastGame game)
    {
        this.game = game;
        spriteBatch = game.batch;
        physicsWorld = game.world;
        toaster = new Sprite(new Texture(Gdx.files.internal("toaster.jpg")));

        // Position toaster
        float toasterHWRatio = ((float) toaster.getTexture().getHeight())
                / toaster.getTexture().getWidth();
        float toasterWidth = Gdx.graphics.getWidth();
        float toasterHeight = toasterWidth * toasterHWRatio;
        toaster.setPosition(0, - (2f/3) * toasterHeight);
        toaster.setSize(toasterWidth, toasterHeight);
        toaster.setOriginCenter();
        toaster.setScale(1.3f, 1.3f);
        toaster.translateX(10);

        toasts = new Array<Toast>();

        addToast(new Texture(Gdx.files.internal("badlogic.jpg")));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        frames++;
        float height = toaster.getTexture().getHeight() / toaster.getTexture().getWidth() * Gdx.graphics.getWidth() * 1.2f;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
        }

        physicsWorld.step(delta,6,2);


        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        toaster.draw(spriteBatch);

        for (int i = 0; i < toasts.size; i++) {
            toasts.get(i).Render(spriteBatch);
        }

        spriteBatch.end();

        if (frames%100==0) {
            addToast(new Texture(Gdx.files.internal("badlogic.jpg")));
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

    }

    private Toast addToast(Texture texture)
    {
        Toast toast=new Toast(texture);
        toasts.add(toast);
        toast.setBody(physicsWorld.createBody(toast.getBodyDef()));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = toast.getShape();
        fixtureDef.density = 1f;
        toast.getBody().createFixture(fixtureDef);

        toast.getBody().setLinearVelocity(100,1000);

        return toast;
    }
    private Toast destroyToast(Toast toast)
    {
        toasts.removeValue(toast,true);
        physicsWorld.destroyBody(toast.getBody());
        return toast;
    }
}
