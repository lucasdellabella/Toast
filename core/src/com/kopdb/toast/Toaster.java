package com.kopdb.toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lucasdellabella on 1/5/17.
 */
public class Toaster {

    private Sprite sprite;

    public Toaster(Texture texture)
    {

        sprite = new Sprite(texture);

        // Position toaster
        float toasterHWRatio = ((float) sprite.getTexture().getHeight())
                / sprite.getTexture().getWidth();
        float toasterWidth = Gdx.graphics.getWidth();
        float toasterHeight = toasterWidth * toasterHWRatio;
        sprite.setPosition(0, - (2f/3) * toasterHeight);
        sprite.setSize(toasterWidth, toasterHeight);
        sprite.setOriginCenter();
        sprite.setScale(1.3f, 1.3f);
        sprite.translateX(10);

    }

    public void draw(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    public Sprite getSprite() {
        return sprite;
    }
}
