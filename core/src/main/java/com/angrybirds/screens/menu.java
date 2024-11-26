package com.angrybirds.screens;

import com.angrybirds.Main;
import com.angrybirds.buttons.taptap;
import com.angrybirds.screens.levels.levels;
import com.angrybirds.seiralize.gamestate;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.angrybirds.seiralize.loadgame;

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
    private TextButton b1,b2,b3,b4,b5;
    private Table table1;
    private SpriteBatch sb;
    private TextureRegionDrawable buttonDrawable;
    private Stage stage;
    public menu(Main game)
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        texture = new Texture("loadscreen.jpg");
        this.table1=new Table();
        SpriteBatch sbx=new SpriteBatch();
        this.sb=sbx;
        buttonManager = new taptap();
        this.stage=new Stage(new ScreenViewport());

//        buttonManager.createButton("Play", game, new levels(game,sb));  // Redirect to `levels`
//        buttonManager.createButton("Settings", game, new settings(game));  // Redirect to `settings`
//        buttonManager.createButton("Achievements", game, new achievescreen(game));  // Redirect to `achievescreen`
//        buttonManager.createExitButton("Exit");

    }

    @Override
    public void show()
    {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("mainbgm.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.7f);
        backgroundMusic.play();

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

        TextButton.TextButtonStyle btn = new TextButton.TextButtonStyle();
        btn.font = font13;
        btn.fontColor = new Color(0.0f, 0.0f, 0.55f, 1);

        b1 = new TextButton("Play", btn);
        b2 = new TextButton("Settings", btn);
        b3 = new TextButton("Achievements", btn);
        b4 = new TextButton("Exit", btn);
        b5 = new TextButton("Continue",btn);
        table1.setFillParent(true);
        table1.top();
        table1.padTop(50);

        table1.add(b1).pad(20).width(220).height(90);
        table1.add(b2).pad(20).width(220).height(90);
        table1.add(b3).pad(20).width(220).height(90);
        table1.add(b4).pad(20).width(220).height(90);
        table1.row();
        table1.add(b5).colspan(4).padTop(280).width(220).height(90);


        constants rx = new constants();
        b1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new levels(game, sb));
            }
        });


        b2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new settings(game));
            }
        });
        b3.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new achievescreen(game));
            }
        });

        b4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        b5.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                gamestate loadedState = loadgame.load();
                if (loadedState != null)
                {
                    gamescreen newScreen = new gamescreen(game, sb);
                    newScreen.restoreGameState(loadedState);
                    game.setScreen(newScreen);
                }
                else
                {
                    game.setScreen(new continuescreen(game));
                }

            }
        });

        buttonDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons.png")));
        b1.getStyle().up = buttonDrawable;
        b2.getStyle().up = buttonDrawable;
        b3.getStyle().up = buttonDrawable;
        b4.getStyle().up = buttonDrawable;
        b5.getStyle().up = buttonDrawable;
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
        buttonManager.dispose();
    }
}
