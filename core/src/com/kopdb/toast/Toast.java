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
    private Vector2 lastPosition;
    private Vector2 newPos;
    private long touchTime;
    private long lastTouchTime;

    public Toast(Texture texture) {

        sprite = new Sprite(texture);
        sprite.setSize(sprite.getWidth() / 4f, sprite.getHeight() / 4f);

        bodyDef = new BodyDef();
        getBodyDef().type = BodyDef.BodyType.DynamicBody;
        getBodyDef().position.set(getSprite().getX() * ToastGame.BOX_2D_SCALE,
                - getSprite().getHeight() * ToastGame.BOX_2D_SCALE);

        shape = new PolygonShape();
        getShape().setAsBox(getSprite().getWidth()/2 * ToastGame.BOX_2D_SCALE, getSprite().getHeight()/2 * ToastGame.BOX_2D_SCALE);

        setBody(ToastGame.getWorld().createBody(getBodyDef()));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = getShape();
        fixtureDef.density = 1f;
        getBody().createFixture(fixtureDef);
        getBody().setType(BodyDef.BodyType.StaticBody);
        getBody().setType(BodyDef.BodyType.DynamicBody);
        getBody().setLinearVelocity(10 * ToastGame.BOX_2D_SCALE, 500 * ToastGame.BOX_2D_SCALE);
        lastPosition = body.getPosition().cpy();
    }

    public void update() {
        if (moveTarget != null) {
            lastPosition = body.getPosition().cpy();
            newPos = body.getPosition().add(new Vector2((moveTarget.x - body.getPosition().x) * 0.2f,
                    (moveTarget.y - body.getPosition().y) * 0.2f)) ;
            body.setTransform(newPos.x, newPos.y, body.getAngle());
        }

        sprite.setPosition(body.getPosition().x / ToastGame.BOX_2D_SCALE,
                body.getPosition().y / ToastGame.BOX_2D_SCALE);
        sprite.setRotation(body.getAngle());
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void checkTouchDown(int x, int y) {
        if (sprite.getBoundingRectangle().contains(x, y)) {
            // save x & y relative bottom left corner of body
            touchOffset = body.getPosition().cpy().scl(1 / ToastGame.BOX_2D_SCALE).sub(x, y);
            // where we want the toast to move to
            moveTarget = new Vector2(x + touchOffset.x, y + touchOffset.y);
            moveTarget.scl(ToastGame.BOX_2D_SCALE);
            // remember moveTarget
            lastPosition = body.getPosition().cpy();
            lastTouchTime = 0;
            touchTime = System.currentTimeMillis();
            body.setLinearVelocity(0, 0);
            body.setType(BodyDef.BodyType.KinematicBody);
        }
    }

    // TODO: use pointers to handle drag off and release?
    public void checkTouchUp(int x, int y) {
        if (moveTarget != null) {
            body.setType(BodyDef.BodyType.DynamicBody);
            Vector2 velocity = newPos.cpy().sub(lastPosition);
            velocity.scl(1000 / (System.currentTimeMillis() - lastTouchTime));
            body.setLinearVelocity(velocity);
            moveTarget = null;
        }
    }

    public void checkTouchDragged(int x, int y) {
        if (moveTarget != null) {
            moveTarget = new Vector2(x + touchOffset.x, y + touchOffset.y);
            moveTarget.scl(ToastGame.BOX_2D_SCALE);
            lastTouchTime = touchTime;
            touchTime = System.currentTimeMillis();
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
