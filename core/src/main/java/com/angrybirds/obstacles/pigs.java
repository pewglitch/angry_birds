package com.angrybirds.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class pigs
{
    private Texture pigTexture;
    private float x, y;

    public pigs(float x, float y)
    {
        pigTexture = new Texture("piggy.png");
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
