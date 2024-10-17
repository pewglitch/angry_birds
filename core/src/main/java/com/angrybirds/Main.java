package com.angrybirds;

import com.angrybirds.screens.Playscreen;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game
{
    public SpriteBatch batch;
    @Override
    public void create()
    {
        // Prepare your application here.
        batch = new SpriteBatch();
        setScreen(new Playscreen(this));
    }

    @Override
    public void resize(int width, int height)
    {
        // Resize your application here. The parameters represent the new window size.
    }

    @Override
    public void render()
    {
        // Draw your application here.
        super.render();
    }

    @Override
    public void pause()
    {
        // Invoked when your application is paused.
    }

    @Override
    public void resume()
    {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose()
    {
        // Destroy application's resources here.
    }
}
