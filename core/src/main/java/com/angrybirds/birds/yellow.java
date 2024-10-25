package com.angrybirds.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class yellow{
    private float x,y;
    private Texture texture1;

    public yellow(float x, float y){
        texture1=new Texture("yellow_new.png");
        this.x=x;
        this.y=y;
    }


    public void render(SpriteBatch batch, float width, float height)
    {
        batch.draw(texture1, x, y, width, height);
    }

    public void dispose()
    {
        texture1.dispose();
    }
}
