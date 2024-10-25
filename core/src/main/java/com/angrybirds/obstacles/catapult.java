package com.angrybirds.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class catapult
{
    private Texture pigTexture;
    private float x, y;

    public catapult(float x, float y)
    {
        pigTexture = new Texture("cata.png");
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch, float width, float height)
    {
        batch.draw(pigTexture, x, y, width, height);
    }

    public void dispose()
    {
        pigTexture.dispose();
    }
}
