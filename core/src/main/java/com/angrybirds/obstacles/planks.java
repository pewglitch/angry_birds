package com.angrybirds.obstacles;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class planks
{
    private static final float PIXELS_TO_METERS = 100f;
    private static final float PLANK_SIZE = 50f; // Size in pixels

    private Body body;
    private TextureRegion texture;
    private World world;
    private int health;

    private float h,w,angle;

    public planks(float x, float y,float h,float w,float angle, World world)
    {
        this.world = world;
        this.health = 100;
        this.h=h;
        this.w=w;
        this.angle=angle;
        Texture planktexture = new Texture("plank.png");
        this.texture = new TextureRegion(planktexture);

        this.texture.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.texture.setRegionWidth(planktexture.getWidth()*2);
        this.texture.setRegionHeight((int) (planktexture.getHeight()*3));

        this.texture.flip(false,true);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PIXELS_TO_METERS, y/PIXELS_TO_METERS);

        body = world.createBody(bodyDef);


        CircleShape circle = new CircleShape();
        circle.setRadius(PLANK_SIZE / (2f * PIXELS_TO_METERS));

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

        float screenX = position.x * PIXELS_TO_METERS - PLANK_SIZE / 2f;
        float screenY = position.y * PIXELS_TO_METERS - PLANK_SIZE / 2f;


        batch.draw(texture,
            screenX, screenY,
            PLANK_SIZE / 2f, PLANK_SIZE / 2f,
            PLANK_SIZE, PLANK_SIZE, h,w,angle  // Scale
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

    public boolean isOutOfWindow(float virtualWidth, float virtualHeight) {
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
        body = null;
    }


    public boolean isAlive() {
        return health > 0 && body != null;
    }


    public Body getBody() {
        return body;
    }


    public Vector2 getPosition() {
        return body.getPosition();
    }
}
