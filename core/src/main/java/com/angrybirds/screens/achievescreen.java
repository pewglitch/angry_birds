package com.angrybirds.screens;

import com.angrybirds.Main;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class achievescreen implements Screen
{
    private Main game;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Texture texture;
    private Music backgroundMusic;
    private final float VIRTUAL_WIDTH = 1000;
    private final float VIRTUAL_HEIGHT = 600;
    private Label label,label1,label2,label3;
    private Skin skin;
    private Stage stage;

    public achievescreen(Main game)
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        texture = new Texture("achievement.png");
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage=new Stage(new ScreenViewport());
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("mainbgm.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.7f);
        backgroundMusic.play();
        achieve();
    }

    public void achieve()
    {
        label = new Label("Achievements", skin);
        label.setFontScale(5);
        label.setColor(0, 0,0, 1);

        label1=new Label("Highest Score",skin);
        label2=new Label("Coins earned",skin);
        label3=new Label("Current level",skin);
        label1.setFontScale(2);
        label2.setFontScale(2);
        label3.setFontScale(2);
        label1.setColor(0, 0,0, 1);
        label2.setColor(0, 0,0, 1);
        label3.setColor(0, 0,0, 1);


        float y=VIRTUAL_HEIGHT-label.getHeight()*label.getFontScaleY()-190;
        float margin=60f;
        float aw=VIRTUAL_WIDTH-2*margin;
        float spl=aw/3;

        float hc=(VIRTUAL_WIDTH-label.getWidth()*label.getFontScaleX())/2;
        float tm=VIRTUAL_HEIGHT-label.getHeight()*label.getFontScaleY()-50;

        label.setPosition(hc,tm);
        label1.setPosition(margin,y);
        label2.setPosition(margin+1.25f*spl,y);
        label3.setPosition(margin+2.5f*spl,y);

        stage.addActor(label);
        stage.addActor(label1);
        stage.addActor(label2);
        stage.addActor(label3);


    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);
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

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
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
        skin.dispose();
    }
}
