package com.angrybirds.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private TextureRegionDrawable buttonDrawable;

    private final float VIRTUAL_WIDTH = 1000;
    private final float VIRTUAL_HEIGHT = 600;

    private Skin skin;
    private TextButton b1,b2,b3,b4,b5,b6;
    private Table table1,table2,table3;
    private Stage stage;
    private Label label;
    private BitmapFont font;

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

    public void button_show() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("metalui/opensans.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size=60;
        BitmapFont font12=generator.generateFont(parameter);
        generator.dispose();
        Label.LabelStyle lst=new Label.LabelStyle();
        lst.font=font12;
        label = new Label("Change Scenery",lst);
        label.setColor(0, 0, 0, 1);

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("metalui/opensans.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter par = new FreeTypeFontGenerator.FreeTypeFontParameter();
        par.size=30;
        BitmapFont font13 = gen.generateFont(par);
        gen.dispose();

        TextButton.TextButtonStyle btn = new TextButton.TextButtonStyle();
        btn.font =font13;
        btn.fontColor = new Color(0.0f, 0.0f, 0.55f, 1);

        b1 = new TextButton("Mountains", btn);
        b2 = new TextButton("Beach",btn);
        b3 = new TextButton("Halloween",btn);
        b4 = new TextButton("Back",btn);
        b5 = new TextButton("Exit",btn);

        table1.setFillParent(true);
        table1.top();

        table1.add(b4).left().padTop(20).padLeft(30).width(100).height(40);
        table1.add().expandX();
        table1.add(b5).right().padTop(20).padRight(30).width(100).height(40);
        table1.row();
        table1.add(label).colspan(3).center().padTop(80);
        table1.row();

        table1.add(b1).padTop(50).padLeft(70).padRight(5).width(150).height(50);
        table1.add(b2).padTop(50).padLeft(5).padRight(5).width(150).height(50);
        table1.add(b3).padTop(50).padLeft(5).padRight(70).width(150).height(50);
        table1.row();

        Texture imgTexture1=new Texture(Gdx.files.internal("ridhhin.png"));
        Texture imgTexture2=new Texture(Gdx.files.internal("img.png"));
        Texture imgTexture3=new Texture(Gdx.files.internal("halloween.jpg"));

        Image i1=new Image(imgTexture1);
        Image i2=new Image(imgTexture2);
        Image i3=new Image(imgTexture3);
        table1.add(i1).padTop(35).padLeft(70).padRight(5).width(150).height(120);
        table1.add(i2).padTop(35).padLeft(5).padRight(5).width(150).height(120);
        table1.add(i3).padTop(35).padLeft(5).padRight(70).width(150).height(120);

        b4.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new menu(game));
            }
        });

        b5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        buttonDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("button1.png")));
        b1.getStyle().up = buttonDrawable;
        b2.getStyle().up = buttonDrawable;
        b3.getStyle().up = buttonDrawable;
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
