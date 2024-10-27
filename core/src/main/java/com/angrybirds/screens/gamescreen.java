package com.angrybirds.screens;

import com.angrybirds.obstacles.catapult;
import com.angrybirds.obstacles.pigs;
import com.angrybirds.obstacles.planks;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
import com.angrybirds.birds.yellow;
import com.angrybirds.birds.black;
import com.angrybirds.birds.red;
import com.angrybirds.birds.white;

public class gamescreen implements Screen
{
    private Main game;
    private Texture texture;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Music backgroundMusic;

    private final float VIRTUAL_WIDTH = 1000;
    private final float VIRTUAL_HEIGHT = 600;
    public Stage stage;
    private Integer score = 0;
    private Integer level = 1;

    Label scorelabel;
    Label levellabel;
    SpriteBatch sb;
    private Skin skin;
    private TextButton b1, b2, b3, b4, b5, b6;
    private Table table1;
    private Label label;
    private pigs p1,p2,p3,p4,p5;
    private planks box1,box2;
    private catapult cata;
    private Texture red1;
    private Texture black1,white1;
    private TextureRegionDrawable buttonDrawable;
    private red red_bird;
    private yellow yellow_bird;
    private black black_bird;
    private white white_bird;




    public gamescreen(Main game, SpriteBatch sb1)
    {
        this.game = game;
        this.sb=sb1;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        red_bird=new red(160,120);
        yellow_bird=new yellow(100,20);
        black_bird=new black(150,25);
        white_bird=new white(30,20);

        stage = new Stage(viewport, sb);
        texture = new Texture("gamebg.jpg");

        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));

        table1 = new Table();
        table1.top();
        table1.setFillParent(true);

        scorelabel = new Label(String.format("Score: %05d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        levellabel = new Label(String.format("Level: %05d", level), new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        table1.add(scorelabel).expandX().padTop(10).left().padLeft(20);
        table1.add(levellabel).expandX().padTop(10).right().padRight(20);
        p1 = new pigs(710, 320);
        p2 = new pigs(760, 320);
        p3 = new pigs(690, 270);
        p4 = new pigs(740, 270);
        p5 = new pigs(790, 270);
        box1 = new planks(640, 225);

        cata = new catapult(130 , 20);

        stage.addActor(table1);
    }

    @Override
    public void show()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("metalui/funny.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size=20;
        BitmapFont font12=generator.generateFont(parameter);
        generator.dispose();

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("metalui/funny.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter par = new FreeTypeFontGenerator.FreeTypeFontParameter();
        par.size=20;
        BitmapFont font13 = gen.generateFont(par);
        gen.dispose();

        TextButton.TextButtonStyle btn = new TextButton.TextButtonStyle();
        btn.font =font13;
        btn.fontColor = new Color(0.0f, 0.0f, 0.55f, 1);

        b1= new TextButton("Home", btn);

        table1.setFillParent(true);
        table1.top();
        table1.add(b1).padTop(10).padLeft(90).padRight(5).width(120).height(50);
        buttonDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons.png")));
        b1.getStyle().up = buttonDrawable;
        b1.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(new menu(game));
                backgroundMusic.stop();
            }
        });
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("gametheme.mp3"));
        backgroundMusic.setLooping(false);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
        stage.addActor(table1);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(texture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        p1.render(game.batch, 50, 50);
        p2.render(game.batch, 50, 50);
        p3.render(game.batch, 50, 50);
        p4.render(game.batch, 50, 50);
        p5.render(game.batch, 50, 50);

        red_bird.render(game.batch, 50, 50);
        white_bird.render(game.batch, 50, 50);
        yellow_bird.render(game.batch, 50, 50);
        black_bird.render(game.batch, 50, 50);


        box1.render(game.batch, 260, 60);
        cata.render(game.batch, 180, 180);

        game.batch.end();

        stage.act(delta);
        stage.draw();


        float restrictedY = 800;
        float restrictedHeight = 400;
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched())
        {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            touchY = Gdx.graphics.getHeight() - touchY;

            if (touchX >= 0 && touchX <= 500 && touchY >= 0 && touchY <= 400)
            {
                game.setScreen(new winscreen(game, sb));
                backgroundMusic.stop();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched())
        {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();

            touchY = Gdx.graphics.getHeight() - touchY;

            if (touchX >= 500 && touchX <= 1000 && touchY >= 0 && touchY <= 400)
            {
                game.setScreen(new losescreen(game, sb));
                backgroundMusic.stop();
            }
        }


    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        texture.dispose();
        backgroundMusic.dispose();
    }
}
