package com.angrybirds.screens.levels;

import com.angrybirds.screens.gamescreen;
import com.angrybirds.screens.menu;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.angrybirds.Main;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class levels implements Screen {
    private Main game;
    private Texture texture;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Music backgroundMusic;
    private TextureRegionDrawable buttonDrawable;
    private BitmapFont font;

    private final float VIRTUAL_WIDTH = 1000;
    private final float VIRTUAL_HEIGHT = 600;

    private Skin skin;
    private TextButton b1,b2,b3,b4,b5,b6;
    private Table table1,table2,table3;
    private Stage stage;
    private Label label;
    private SpriteBatch sb;

    public levels(Main game,SpriteBatch sbx)
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        texture= new Texture("levelsbg.png");
        skin=new Skin(Gdx.files.internal("ui/uiskin.json"));
        font = new BitmapFont();
        skin.add("default-font", font);
        table1=new Table();
        table2=new Table();
        this.sb=sbx;
        table3=new Table();
        stage=new Stage(new ScreenViewport());
    }

    @Override
    public void show()
    {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("mainbgm.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("metalui/opensans.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size =60;
        parameter.color=Color.YELLOW;
        BitmapFont font12 = generator.generateFont(parameter);
        generator.dispose();
        Label.LabelStyle lst = new Label.LabelStyle();
        lst.font = font12;
        label = new Label("Choose Levels", lst);


        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("metalui/opensans.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter par = new FreeTypeFontGenerator.FreeTypeFontParameter();
        par.size=25;
        par.color=Color.YELLOW;
        BitmapFont font13 = gen.generateFont(par);
        gen.dispose();

        TextButton.TextButtonStyle btn1 = new TextButton.TextButtonStyle();
        btn1.font = font13;

        b1 = new TextButton("Level 1", btn1);
        b2 = new TextButton("Level 2", btn1);
        b3 = new TextButton("Level 3", btn1);
        b4 = new TextButton("Back", btn1);
        b5 = new TextButton("Exit", btn1);


        table1.setFillParent(true);
        table1.top();

        table1.add(b4).left().padTop(20).padLeft(30).width(100).height(40);
        table1.add().expandX();
        table1.add(b5).right().padTop(20).padRight(30).width(100).height(40);
        table1.row();
        table1.add(label).colspan(3).center().padTop(80);
        table1.row();

        table1.add(b1).padTop(50).padLeft(70).padRight(5).width(120).height(50);
        table1.add(b2).padTop(50).padLeft(5).padRight(5).width(120).height(50);
        table1.add(b3).padTop(50).padLeft(5).padRight(70).width(120).height(50);
        table1.row();
        b1.addListener(new ClickListener()
        {
           @Override
           public void clicked(InputEvent event,float x,float y)
           {
               backgroundMusic.stop();
               game.setScreen(new gamescreen(game,sb));

           }
        });
        b2.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event,float x,float y)
            {
                backgroundMusic.stop();
                game.setScreen(new gamescreen(game,sb));

            }
        });
        b3.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event,float x,float y)
            {
                backgroundMusic.stop();
                game.setScreen(new gamescreen(game,sb));
            }
        });
        b4.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backgroundMusic.stop();
                game.setScreen(new menu(game));
            }
        });

        b5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backgroundMusic.stop();
                Gdx.app.exit();
            }
        });
        Gdx.input.setInputProcessor(stage);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("button1.png")));
        b1.getStyle().up = buttonDrawable;
        b2.getStyle().up = buttonDrawable;
        b3.getStyle().up = buttonDrawable;
        stage.addActor(table1);
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
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        texture.dispose();
        backgroundMusic.dispose();
    }
}
