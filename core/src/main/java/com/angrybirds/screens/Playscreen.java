package com.angrybirds.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.angrybirds.Main;
import com.badlogic.gdx.audio.Music;

public class Playscreen implements Screen
{
    private Main game;
    private Texture texture;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Music backgroundMusic;

    private final float VIRTUAL_WIDTH = 1000;
    private final float VIRTUAL_HEIGHT = 600;

    public Playscreen(Main game)
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        texture = new Texture("firstload.jpg");

    }

    @Override
    public void show()
    {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("startcoc.mp3"));
        backgroundMusic.setLooping(false);
        backgroundMusic.setVolume(1f);
        backgroundMusic.play();
    }

    @Override
    public void render(float delta)
    {

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(texture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched())
        {
            game.setScreen(new menu(game));
            backgroundMusic.stop();
        }
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        texture.dispose();
    }
}
