package com.angrybirds.obstacles;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class helmetpig
{
    private static final float PIXELS_TO_METERS = 100f;
    private static final float PIG_SIZE = 55f; // Size in pixels

    private Body body;
    private TextureRegion texture;
    private World world;
    private int health;
    public boolean dead=false;
    public helmetpig(float x, float y, World world)
    {
        this.world = world;
        this.health = 150;
        Texture pigTexture = new Texture("metalpig.png");
        this.texture = new TextureRegion(pigTexture);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PIXELS_TO_METERS, y / PIXELS_TO_METERS);

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(PIG_SIZE / (2.4f * PIXELS_TO_METERS));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);
        body.setUserData(this);
        body.setGravityScale(0.5f);
        circle.dispose();
    }
    public Integer gethealth()
    {
        return health;
    }
    public void render(SpriteBatch batch)
    {
        Vector2 position = body.getPosition();
        float angle = body.getAngle() * (180f / (float)Math.PI);

        float screenX = position.x * PIXELS_TO_METERS - PIG_SIZE / 2f;
        float screenY = position.y * PIXELS_TO_METERS - PIG_SIZE / 2f;

        batch.draw(texture,
            screenX, screenY,
            PIG_SIZE / 2f, PIG_SIZE / 2f,
            PIG_SIZE, PIG_SIZE,
            1, 1,  // Scale
            angle
        );
    }

    // Method to take damage
    public void takeDamage(int damage)
    {
        health -= damage;
        if (health <= 0) {
            destroy();
        }
    }

    public boolean isOutOfWindow(float virtualWidth, float virtualHeight)
    {
        Vector2 pigPosition = body.getPosition();
        float pigScreenX = pigPosition.x * PIXELS_TO_METERS;
        float pigScreenY = pigPosition.y * PIXELS_TO_METERS;

        return (pigScreenX > virtualWidth ||
            pigScreenX < 0 ||
            pigScreenY > virtualHeight ||
            pigScreenY < 0);
    }
    public void destroy()
    {
        world.destroyBody(body);
    }
    public void oncolide(Integer damage)
    {
        health-=damage;
        if(health<=20)
        {
            dead=true;
        }
    }
    public boolean getdead()
    {
        return dead;
    }
    public boolean isAlive() {
        return health > 0 && body != null;
    }

    public TextureRegion getregion()
    {
        return texture;
    }

    public Body getBody() {
        return body;
    }


    public Vector2 getPosition() {
        return body.getPosition();
    }
}
