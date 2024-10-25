package com.angrybirds.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class planks
{
    private Texture pigTexture;
    private float x, y;

    public planks(float x, float y)
    {
        pigTexture = new Texture("plank.png");
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
