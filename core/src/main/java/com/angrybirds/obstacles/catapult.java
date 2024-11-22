package com.angrybirds.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class catapult
{
    private Texture pigTexture;
    private  static float x, y;

    public catapult(float x, float y)
    {
        pigTexture = new Texture("cata.png");
        this.x = x;
        this.y = y;
    }
    public void dispose()
    {
        pigTexture.dispose();
    }

    public static float getX()
    {
        return x;
    }
    public static float getY()
    {
        return y;
    }


}
