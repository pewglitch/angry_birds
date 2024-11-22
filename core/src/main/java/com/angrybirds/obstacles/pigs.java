package com.angrybirds.obstacles;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class pigs implements InputProcessor
{
    private float x, y;
    private Texture texture1;
    private Integer ppm = 64;
    private Body body;
    private boolean isDragging = false;
    private Vector2 dragStart = new Vector2();
    private Vector2 dragEnd = new Vector2();

    public pigs(float x, float y, World world, Box2DDebugRenderer rdr)
    {
        texture1 = new Texture("piggy.png");

        BodyDef birdef = new BodyDef();
        birdef.type = BodyDef.BodyType.StaticBody;
        birdef.position.set(x / ppm, y / ppm);

        body = world.createBody(birdef);

        CircleShape shape = new CircleShape();
        shape.setRadius(64 / ppm);

        FixtureDef fixdef = new FixtureDef();
        fixdef.density = 2.5f;
        fixdef.friction = 0.35f;
        fixdef.restitution = 0.75f;
        fixdef.shape = shape;

        body.createFixture(fixdef);
        shape.dispose();

        BodyDef ground = new BodyDef();
        ground.type = BodyDef.BodyType.StaticBody;
        ground.position.set(0f, 0f);

        ChainShape grshape = new ChainShape();
        grshape.createChain(new Vector2[]{new Vector2(-500 / ppm, 0), new Vector2(500 / ppm, 0)});  // Adjust size and scale

        fixdef.shape = grshape;
        fixdef.friction = 0.5f;
        fixdef.restitution = 0.5f;

       // world.createBody(ground).createFixture(fixdef);
    }

    public void render(SpriteBatch batch, float width, float height)
    {
        x = body.getPosition().x * ppm;
        y = body.getPosition().y * ppm;

        batch.draw(texture1, x - width / 2, y - height / 2, width, height);
    }

    @Override
    public boolean keyDown(int keycode) { return false; }

    @Override
    public boolean keyUp(int keycode) { return false; }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isDragging = true;
        dragStart.set(screenX / ppm, screenY / ppm);  // Start dragging
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (isDragging)
        {
            isDragging = false;
            dragEnd.set(screenX / ppm, screenY / ppm);  // Dragging ended

            Vector2 launchForce = dragStart.sub(dragEnd).scl(25);  // Scale the force for a stronger throw
            body.applyLinearImpulse(launchForce, body.getWorldCenter(), true);
        }
        return true;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) { return false; }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDragging)
        {
            body.setTransform(screenX / ppm, screenY / ppm, 0);
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) { return false; }

    @Override
    public boolean scrolled(float v, float v1) { return false; }

    public Body getBody() {
        return body;
    }

    public void dispose() {
        texture1.dispose();
    }
}

