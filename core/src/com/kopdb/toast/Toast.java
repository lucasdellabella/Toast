package com.kopdb.toast;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
    private Vector2 moveTarget;
    private Vector2 touchOffset;

    public Toast(Texture texture) {

        sprite = new Sprite(texture);

        bodyDef = new BodyDef();
        getBodyDef().type = BodyDef.BodyType.DynamicBody;
        getBodyDef().position.set(getSprite().getX(), getSprite().getY());

        shape = new PolygonShape();
        getShape().setAsBox(getSprite().getWidth()/2, getSprite().getHeight()/2);
    }

    public void update() {
        sprite.setPosition(getBody().getPosition().x, getBody().getPosition().y);

        if (moveTarget != null) {
            Vector2 newPos = body.getPosition().lerp(moveTarget, 0.1f);
            body.setTransform(newPos.x, newPos.y, body.getAngle());
        }
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void checkTouchDown(int x, int y) {
        if (sprite.getBoundingRectangle().contains(x, y)) {
            touchOffset = body.getPosition().cpy().sub(x, y);
            moveTarget = new Vector2(x + touchOffset.x, y + touchOffset.y);
            body.setLinearVelocity(0, 0);
            body.setType(BodyDef.BodyType.KinematicBody);
        }
    }

    // TODO: use pointers to handle drag off and release?
    public void checkTouchUp(int x, int y) {
        if (sprite.getBoundingRectangle().contains(x, y)) {
            moveTarget = null;
            body.setType(BodyDef.BodyType.DynamicBody);
        }
    }

    public void checkTouchDragged(int x, int y) {
        if (sprite.getBoundingRectangle().contains(x, y)) {
            moveTarget = new Vector2(x + touchOffset.x, y + touchOffset.y);
        }
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
