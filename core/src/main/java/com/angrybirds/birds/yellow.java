package com.angrybirds.birds;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class yellow extends InputMultiplexer
{
    private static final float PIXELS_TO_METERS = 100f;
    private static final float BIRD_SIZE = 50f;
    private boolean markedForDestruction = false;
    private Body body;
    private TextureRegion texture;
    private World world;
    private boolean isDragging = false;
    private boolean isLaunched = false;
    private Vector2 dragStart = new Vector2();
    private Vector2 launchPosition = new Vector2();

    private static final float Mdd = 1.5f;//max drag distance
    private static final float Lf = 20.0f;//launch force
    private static final float g = 0.5f;
    private static final float ad = 1.0f;
    private static final float dc = 0.3f;
    private static final float BIRD_MASS = 0.8f;
    private Stage stage;

    public yellow(World world, TextureRegion texture, float x, float y, Stage stag)
    {
        this.world = world;
        this.texture = texture;
        this.launchPosition.set(x, y);
        this.stage = stag;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        bodyDef.bullet = true;
        bodyDef.fixedRotation = false;

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(BIRD_SIZE / (2f * PIXELS_TO_METERS));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = BIRD_MASS;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.3f;

        body.createFixture(fixtureDef);
        body.setGravityScale(g);
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
            1.3f, 1.4f,
            angle
        );
    }

    public boolean touchDown(float worldX, float worldY)
    {
        if (!isLaunched && Vector2.dst(worldX, worldY,
            body.getPosition().x, body.getPosition().y) < BIRD_SIZE / PIXELS_TO_METERS)
        {
            isDragging = true;
            dragStart.set(worldX, worldY);
            body.setType(BodyDef.BodyType.KinematicBody);
            return true;
        }
        return false;
    }

    public void touchDragged(float worldX, float worldY)
    {
        if (isDragging)
        {
            Vector2 dragCurrent = new Vector2(worldX, worldY);
            Vector2 dragVector = new Vector2(launchPosition).sub(dragCurrent);

            float angle = MathUtils.atan2(dragVector.y, dragVector.x);

            if (dragVector.len() > Mdd) {
                dragVector.nor().scl(Mdd);
            }

            body.setTransform(
                new Vector2(launchPosition).sub(dragVector),
                angle
            );
        }
    }

    public void touchUp(float worldX, float worldY)
    {
        if (isDragging) {
            isDragging = false;
            isLaunched= true;

            Vector2 dragCurrent = new Vector2(worldX, worldY);
            Vector2 launchVector = new Vector2(launchPosition).sub(dragCurrent);

            float angle = MathUtils.atan2(launchVector.y, launchVector.x);

            if (launchVector.len() > Mdd) {
                launchVector.nor().scl(Mdd);
            }

            body.setType(BodyDef.BodyType.DynamicBody);
            body.setLinearVelocity(0, 0);

            float velocity = launchVector.len() * Lf;

            float x_v = velocity * MathUtils.cos(angle) * 1.2f;
            float y_v= velocity * MathUtils.sin(angle) * 1.2f;

            body.setLinearVelocity(x_v,y_v);

            body.applyLinearImpulse(
                new Vector2(0,0),
                body.getWorldCenter(),
                true
            );
        }
    }

    public void update()
    {
        if (isLaunched && !isDragging)
        {
            Vector2 velocity = body.getLinearVelocity();
            float speed = velocity.len();

            if (speed>0.1f)
            {
                float area=MathUtils.PI *(BIRD_SIZE / (2f * PIXELS_TO_METERS)) * (BIRD_SIZE / (2f * PIXELS_TO_METERS));
                float dm =0.5f*ad*speed*speed*dc*area;

                Vector2 df=new Vector2(velocity).nor().scl(-dm);
                body.applyForceToCenter(df, true);
                body.applyForceToCenter(new Vector2(0, -g * 0.9f), true);
            }
        }
    }

    public void reset()
    {
        isLaunched= false;
        isDragging = false;
        body.setType(BodyDef.BodyType.DynamicBody);
        body.setTransform(launchPosition, 0);
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);

        body.setType(BodyDef.BodyType.StaticBody);
        body.setGravityScale(1);
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
            body.setLinearVelocity(new Vector2(0f, -50f));
            markedForDestruction = true;
        }
    }

    public Body getBody()
    {
        return body;
    }
}


