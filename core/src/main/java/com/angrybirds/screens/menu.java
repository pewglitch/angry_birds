package com.angrybirds.screens;

import com.angrybirds.Main;
import com.angrybirds.buttons.taptap;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class menu implements Screen
{
    private final Main game;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Texture texture;
    private Music backgroundMusic;
    private final float VIRTUAL_WIDTH = 1000;
    private final float VIRTUAL_HEIGHT = 600;
    private taptap buttonManager;

    public menu(Main game)
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        texture = new Texture("birb.jpg");

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("mainbgm.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.7f);
        backgroundMusic.play();

        SpriteBatch sb = new SpriteBatch();
        buttonManager = new taptap();

        buttonManager.createButton("Play", game, new gamescreen(game,sb));
        buttonManager.createButton("Settings", game, new settings(game));
        buttonManager.createButton("Achievements", game, new achievescreen(game));
        buttonManager.createExitButton("Exit");

    }

    @Override
    public void show()
    {

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

        buttonManager.render();
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
        buttonManager.resize(width, height);
    }

    @Override
    public void pause() {
        // Handle pause logic here if needed
    }

    @Override
    public void resume() {
        // Handle resume logic here if needed
    }

    @Override
    public void hide()
    {
        backgroundMusic.stop();
    }

    @Override
    public void dispose() {
        texture.dispose();
        backgroundMusic.dispose();
        buttonManager.dispose(); // Dispose of button manager resources
    }
}
