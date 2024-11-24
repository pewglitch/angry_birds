package com.angrybirds.obstacles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;

public class planks
{
    private static final float PIXELS_TO_METERS = 100f; // Convert pixels to meters

    private Body body;
    private TextureRegion texture; // This will hold the texture to be drawn on the plank
    private World world;
    private int health;
    private float sx;
    private float sy;
    private float h, w, angle;
    private boolean dead=false;

    public planks(float x, float y, float width, float length,float angle,float scx,float scy,World wor)
    {
        this.world = wor;
        this.h = length;
        this.w = width;
        this.health = 130;

        Texture plankTexture = new Texture("plank.png");
        this.texture = new TextureRegion(plankTexture);

        this.sx=scx;
        this.sy=scy;
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / PIXELS_TO_METERS, y / PIXELS_TO_METERS);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        float angleInRadians = (float) Math.toRadians(angle);

        PolygonShape poly = new PolygonShape();
        poly.setAsBox(w / 2f / PIXELS_TO_METERS, h / 2f / PIXELS_TO_METERS);

        FixtureDef fixDef = new FixtureDef();
        fixDef.density = 5f;
        fixDef.friction = 10f;
        fixDef.restitution = 0.1f;
        fixDef.shape = poly;

        body.createFixture(fixDef);
        body.setUserData(this);

        body.setTransform(body.getPosition(), angleInRadians);
    }

    public void render(SpriteBatch batch)
    {
        Vector2 position = body.getPosition();
        float posx=position.x * PIXELS_TO_METERS - w / 2;
        float posy=position.y * PIXELS_TO_METERS - h / 2;
        float angleInDegrees = (float) Math.toDegrees(body.getAngle());
        batch.draw(texture,posx,posy,w/2,h/2,w,h,sx,sy,angleInDegrees);
    }

    public Integer gethealth()
    {
        return health;
    }
    public void takeDamage(int damage)
    {
        health -= damage;
        if (health <= 0)
        {
            destroy();
        }
    }
    public TextureRegion getregion()
    {
        return texture;
    }

    public boolean isOutOfWindow(float virtualWidth, float virtualHeight)
    {
        Vector2 plankPosition = body.getPosition();
        float plankScreenX = plankPosition.x * PIXELS_TO_METERS;
        float plankScreenY = plankPosition.y * PIXELS_TO_METERS;

        return (plankScreenX > virtualWidth || plankScreenX < 0 ||
            plankScreenY > virtualHeight || plankScreenY < 0);
    }

    public void destroy()
    {
        world.destroyBody(body);
    }

    public boolean isAlive() {
        return health > 0 && body != null;
    }
    public void oncolide(Integer damage)
    {
        health-=damage;
        if(health<30)
        {
            dead=true;
        }
    }
    public boolean getdead()
    {
        return dead;
    }
    public Body getBody()
    {
        return body;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
}
