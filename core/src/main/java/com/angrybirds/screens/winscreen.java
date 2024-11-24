package com.angrybirds.screens;

import com.angrybirds.screens.levels.levelfour;
import com.angrybirds.screens.levels.levelthree;
import com.angrybirds.screens.levels.leveltwo;
import com.badlogic.gdx.Screen;
import com.angrybirds.Main;
import com.angrybirds.buttons.taptap;
import com.angrybirds.screens.levels.levels;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

import static java.lang.Math.max;

public class winscreen implements Screen
{
    private Main game;
    private Texture texture;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Music backgroundMusic;

    private final float VIRTUAL_WIDTH = 1000;
    private final float VIRTUAL_HEIGHT = 600;
    public Stage stage;
    private TextureRegionDrawable buttonDrawable;

    private Skin skin;
    private TextButton b1,b2,b3,b4;
    private Table table1;
    private Label label;
    private Integer score;
    private Integer l;
    private SpriteBatch sb;
    public winscreen(Main game,SpriteBatch sb,Integer socrex,Integer level,SpriteBatch sx)
    {
        this.sb=sx;
        this.l=level;
        this.score=socrex;
        this.game = game;
        constants rx=new constants();
        if (level == 1) {
            rx.setR1(Math.max(rx.getR1(), socrex));
        } else if (level == 2) {
            rx.setR2(Math.max(rx.getR2(), socrex));
        } else if (level == 3) {
            rx.setR3(Math.max(rx.getR3(), socrex));
        } else if (level == 4) {
            rx.setR4(Math.max(rx.getR4(), socrex));
        }
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        stage = new Stage(viewport,sb);
        texture = new Texture("happy.png");
        skin=new Skin(Gdx.files.internal("ui/uiskin.json"));

        table1 = new Table();
        stage.addActor(table1);
    }
    @Override
    public void show()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("metalui/funny.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        BitmapFont font12 = generator.generateFont(parameter);
        generator.dispose();

        Label.LabelStyle lst = new Label.LabelStyle();
        lst.font = font12;

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("metalui/funny.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter par = new FreeTypeFontGenerator.FreeTypeFontParameter();
        par.size = 20;

        BitmapFont font13 = gen.generateFont(par);
        gen.dispose();
        lst.fontColor = new Color(0.0f, 0.0f, 0.55f, 1);
        TextButton.TextButtonStyle btn = new TextButton.TextButtonStyle();
        btn.font = font13;
        btn.fontColor = new Color(0.0f, 0.0f, 0.55f, 1);

        b1 = new TextButton("Home", btn);
        b2 = new TextButton("Exit", btn);
        b3 = new TextButton("Next Level", btn);
        b4 = new TextButton("Replay Level", btn);
        Label scoreLabel = new Label("Score: " + score, lst);
        table1.setFillParent(true);
        table1.top();
        table1.padTop(50);
        scoreLabel.setFontScale(2.0f);
        table1.add(b1).padLeft(30).padRight(10).padTop(20).width(220).height(90);
        table1.add(b2).padLeft(10).padRight(10).padTop(20).width(220).height(90);
        table1.add(b3).padLeft(10).padRight(10).padTop(20).width(220).height(90);
        table1.add(b4).padLeft(10).padRight(30).padTop(20).width(220).height(90);

        table1.row();
        table1.add(scoreLabel).colspan(10).padTop(30).center();

        constants rx = new constants();
        b1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new menu(game));
                //backgroundMusic.stop();
            }
        });

        b2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                //backgroundMusic.stop();
            }
        });
        b3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (l == 1) {
                    game.setScreen(new leveltwo(game,sb));
                } else if (l == 2) {
                    game.setScreen(new levelthree(game,sb));
                } else if (l == 3) {
                    game.setScreen(new levelfour(game,sb));
                } else if (l == 4) {

                }
                //backgroundMusic.stop();
            }
        });


        b4.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (l == 1) {
                    game.setScreen(new gamescreen(game,sb));
                } else if (l == 2) {
                    game.setScreen(new leveltwo(game,sb));
                } else if (l == 3) {
                    game.setScreen(new levelthree(game,sb));
                } else if (l == 4) {
                    game.setScreen(new levelfour(game,sb));
                }
                //backgroundMusic.stop();
            }
        });

        buttonDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons.png")));
        b1.getStyle().up = buttonDrawable;
        b2.getStyle().up = buttonDrawable;
        b3.getStyle().up = buttonDrawable;
        b4.getStyle().up = buttonDrawable;

        stage.addActor(table1);
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
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
