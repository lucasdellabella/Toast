package com.kopdb.toast;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Richard Kopelow on 1/5/2017.
 */
public class Toast implements Disposable
{
    private Sprite sprite;
    private BodyDef bodyDef;
    private PolygonShape shape;
    private Body body;

    public Toast(Texture texture) {

        sprite = new Sprite(texture);

        bodyDef = new BodyDef();
        getBodyDef().type = BodyDef.BodyType.DynamicBody;
        getBodyDef().position.set(getSprite().getX(), getSprite().getY());

        shape = new PolygonShape();
        getShape().setAsBox(getSprite().getWidth()/2, getSprite().getHeight()/2);
    }

    public void update() {
        getSprite().setPosition(getBody().getPosition().x, getBody().getPosition().y);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public BodyDef getBodyDef() {
        return bodyDef;
    }

    public PolygonShape getShape() {
        return shape;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }
}
