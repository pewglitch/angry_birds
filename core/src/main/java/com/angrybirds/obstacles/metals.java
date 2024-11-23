package com.angrybirds.obstacles;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class metals
{
    private static final float PIXELS_TO_METERS = 100f;
    private static final float PLANK_SIZE = 50f;
    private static final int INITIAL_HEALTH = 100;
    private static final int DAMAGE_PER_HIT = 40;
    private static final int DESTROY_THRESHOLD = 30;

    private Body body;
    private TextureRegion texture;
    private World world;
    private int health;
    private boolean markedForDestruction = false;

    private float h, w, angle;

    public metals(float x, float y, float h, float w, float angle, World world)
    {
        this.world = world;
        this.health = INITIAL_HEALTH;
        this.h = h;
        this.w = w;
        this.angle = angle;

        setupTexture();

        createPhysicsBody(x, y);
    }

    private void setupTexture()
    {
        Texture plankTexture = new Texture("metal1.png");
        this.texture = new TextureRegion(plankTexture);
        this.texture.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.texture.setRegionWidth(plankTexture.getWidth() * 2);
        this.texture.setRegionHeight((int) (plankTexture.getHeight() * 3));
        this.texture.flip(false, true);
    }

    private void createPhysicsBody(float x, float y)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x / PIXELS_TO_METERS, y / PIXELS_TO_METERS);

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(PLANK_SIZE / (2f * PIXELS_TO_METERS));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 10400.0f;
        fixtureDef.friction = 4000f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);
        body.setUserData(this);
        circle.dispose();
    }

    public void render(SpriteBatch batch) {
        if (!isAlive()) return;

        Vector2 position = body.getPosition();
        float screenX = position.x * PIXELS_TO_METERS - PLANK_SIZE / 2f;
        float screenY = position.y * PIXELS_TO_METERS - PLANK_SIZE / 2f;

        batch.draw(texture,
            screenX, screenY,
            PLANK_SIZE / 2f, PLANK_SIZE / 2f,
            PLANK_SIZE, PLANK_SIZE,
            h, w, angle
        );
    }

    public void update()
    {
        if (markedForDestruction && body != null)
        {
            System.out.println("removed meta");
            world.destroyBody(body);
        }
    }

    public void handleCollision() {
        if (!isAlive()) return;

        health -= DAMAGE_PER_HIT;
        System.out.println("Metal health reduced to: " + health);

        if (health <= DESTROY_THRESHOLD)
        {
            markedForDestruction = true;
            System.out.println("Metal marked for destruction");
        }
    }

    public boolean isAlive()
    {
        return health > 0 && body != null && !markedForDestruction;
    }

    public int getHealth() {
        return health;
    }

    public boolean isOutOfWindow(float virtualWidth, float virtualHeight)
    {
        if (body == null) return true;

        Vector2 position = body.getPosition();
        float screenX = position.x * PIXELS_TO_METERS;
        float screenY = position.y * PIXELS_TO_METERS;

        return (screenX > virtualWidth || screenX < 0 ||
            screenY > virtualHeight || screenY < 0);
    }
}
