package com.angrybirds.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.angrybirds.Main;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class settings implements Screen
{
    private Main game;
    private Texture texture;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Music backgroundMusic;

    private final float VIRTUAL_WIDTH = 1000;
    private final float VIRTUAL_HEIGHT = 600;

    private Skin skin;
    private TextButton b1,b2,b3,b4,b5,b6;
    private Table table1,table2,table3;
    private Stage stage;
    private Label label;

    public settings(Main game)
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        texture= new Texture("background.png");
        skin=new Skin(Gdx.files.internal("ui/uiskin.json"));
        table1=new Table();
        table2=new Table();
        table3=new Table();
        stage=new Stage(new ScreenViewport());

        button_show();
    }

    public void button_show()
    {
        label = new Label("Change Scenery", skin);
        label.setFontScale(3);
        label.setColor(0, 0, 0, 1);

        b1 = new TextButton("Mountains", skin);
        b2 = new TextButton("Beach", skin);
        b3 = new TextButton("Halloween", skin);
        b4 = new TextButton("Back",skin);
        b5 = new TextButton("Exit",skin);
        table1.setFillParent(true);
        table1.center();
        table1.add(label).padBottom(50).colspan(5).center();
        table1.row();
        table1.add(b1).width(150).height(80).pad(5);
        table1.add(b2).width(150).height(80).pad(5);
        table1.add(b3).width(150).height(80).pad(5);
        table1.add(b4).width(150).height(80).pad(5);
        table1.add(b5).width(150).height(80).pad(5);
        b4.addListener(new ClickListener()
        {
           @Override
           public void clicked(InputEvent event,float x,float y)
           {
               game.setScreen(new menu(game));
           }
        });
        b5.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });

        stage.addActor(table1);
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
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
