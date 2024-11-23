package com.angrybirds.birds;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class red extends InputMultiplexer
{
    private static final float PIXELS_TO_METERS = 100f;
    private static final float BIRD_SIZE = 50f; // Size in pixels
    private boolean markedForDestruction= false;
    private Body body;
    private TextureRegion texture;
    private World world;
    private boolean isDragging = false;
    private boolean isLaunched = false;
    private Vector2 dragStart = new Vector2();
    private Vector2 launchPosition = new Vector2();

    private static final float MAX_DRAG_DISTANCE = 3.0f;
    private static final float LAUNCH_FORCE_MULTIPLIER = 7.0f;
    private Stage stage;
    public red(World world, TextureRegion texture, float x, float y,Stage stag) {
        this.world = world;

        this.texture = texture;
        this.launchPosition.set(x, y);
        this.stage=stag;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(BIRD_SIZE / (2f * PIXELS_TO_METERS));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);

        body.setUserData(this);
        circle.dispose();
    }

    public void render(SpriteBatch batch)
    {
        Vector2 position = body.getPosition();
        float angle = body.getAngle() * MathUtils.radiansToDegrees;

        float screenX = position.x * PIXELS_TO_METERS - BIRD_SIZE/2f;
        float screenY = position.y * PIXELS_TO_METERS - BIRD_SIZE/2f;

        batch.draw(texture,
            screenX, screenY,
            BIRD_SIZE/2f, BIRD_SIZE/2f,
            BIRD_SIZE, BIRD_SIZE,
            1, 1,  // Scale
            angle
        );
    }

    public boolean touchDown(float worldX, float worldY)
    {
        if (!isLaunched && Vector2.dst(worldX, worldY,
            body.getPosition().x, body.getPosition().y) < BIRD_SIZE / PIXELS_TO_METERS) {
            isDragging = true;
            dragStart.set(worldX, worldY);
            body.setType(BodyDef.BodyType.KinematicBody);
            return true;
        }
        return false;
    }

    public void touchDragged(float worldX, float worldY)
    {
        if (isDragging) {
            Vector2 dragCurrent = new Vector2(worldX, worldY);
            Vector2 dragVector = new Vector2(launchPosition).sub(dragCurrent);

            if (dragVector.len() > MAX_DRAG_DISTANCE) {
                dragVector.nor().scl(MAX_DRAG_DISTANCE);
            }

            body.setTransform(
                new Vector2(launchPosition).sub(dragVector),
                body.getAngle()
            );
        }
    }

    public void touchUp(float worldX, float worldY) {
        if (isDragging) {
            isDragging = false;
            isLaunched = true;

            Vector2 dragCurrent = new Vector2(worldX, worldY);
            Vector2 launchVector = new Vector2(launchPosition).sub(dragCurrent);

            if (launchVector.len() > MAX_DRAG_DISTANCE) {
                launchVector.nor().scl(MAX_DRAG_DISTANCE);
            }

            body.setType(BodyDef.BodyType.DynamicBody);
            body.setLinearVelocity(0, 0);
            body.applyLinearImpulse(
                launchVector.scl(LAUNCH_FORCE_MULTIPLIER),
                body.getWorldCenter(),
                true
            );
        }
    }

    public void reset()
    {
        isLaunched = false;
        isDragging = false;
        body.setType(BodyDef.BodyType.DynamicBody);
        body.setTransform(launchPosition, 0);
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
    }

    public boolean isLaunched() {
        return isLaunched;
    }

    public boolean isDragging() {
        return isDragging;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public boolean isStopped()
    {
        return isLaunched && body.getLinearVelocity().len() < 0.1f;
    }
    public void onCollision()
    {
        if (body != null)
        {
            body.setLinearVelocity(new Vector2(0f, -50f)); // Impart some movement
            markedForDestruction = true;
        }
    }

    public void update()
    {
    }

    public Body getBody() {
        return body;
    }
}
