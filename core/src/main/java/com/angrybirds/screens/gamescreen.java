package com.angrybirds.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.angrybirds.Main;
import com.badlogic.gdx.audio.Music;

public class gamescreen implements Screen {
    private Main game;
    private Texture texture;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Music backgroundMusic;

    private final float VIRTUAL_WIDTH = 1000;
    private final float VIRTUAL_HEIGHT = 600;
    public Stage stage; // Declare stage here
    private Integer score = 0; // Initialize score
    Label scorelabel;
    private Integer level = 1; // Initialize level
    Label levellabel;

    public gamescreen(Main game, SpriteBatch sb) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        // Initialize stage here
        stage = new Stage(viewport, sb); // Initialize the stage

        texture = new Texture("firstload.jpg");
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("mainbgm.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f); // Adjust volume as needed
        backgroundMusic.play();

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        // Initialize score and level labels
        scorelabel = new Label(String.format("%05d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        levellabel = new Label(String.format("%05d", level), new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        // Add labels to the table
        table.add(scorelabel).expand().padTop(10);
        table.add(levellabel).expand().padTop(10);
        stage.addActor(table); // Add table to the stage
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // Set the input processor to the stage
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update and draw the stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height); // Update the viewport
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose(); // Dispose of the stage
        texture.dispose(); // Dispose of the texture
        backgroundMusic.dispose(); // Dispose of the background music
    }
}
