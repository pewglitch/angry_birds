package com.angrybirds.screens;

import com.angrybirds.screens.levels.levelfour;
import com.angrybirds.screens.levels.levelthree;
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
import com.angrybirds.screens.levels.leveltwo;
import static java.lang.Math.max;

public class finalover implements Screen
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
    public finalover(Main game,SpriteBatch sb,Integer socrex)
    {
        this.score=socrex;
        this.game = game;
        constants rx=new constants();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        stage = new Stage(viewport,sb);
        texture = new Texture("finalover.jpg");
        skin=new Skin(Gdx.files.internal("ui/uiskin.json"));

        table1 = new Table();
        stage.addActor(table1);
    }
    @Override
    public void show()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("metalui/funny.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;
        BitmapFont font12 = generator.generateFont(parameter);
        generator.dispose();
        Label.LabelStyle lst=new Label.LabelStyle();
        lst.font=font12;
        Label lrx=new Label("Yayy all levels cleared!",lst);

        lrx.setColor(0, 0f, 0, 1);

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

        Label scoreLabel = new Label("Score: " + score, lst);
        table1.setFillParent(true);
        table1.top();
        table1.padTop(50);
        scoreLabel.setFontScale(2.0f);
        table1.add(b1).padLeft(10).padRight(450).padTop(5).width(200).height(70);
        table1.add(b2).padLeft(10).padRight(10).padTop(5).width(200).height(70);

        table1.row();
        table1.add(lrx).colspan(3).center().padTop(80);
        table1.row();

        constants rx = new constants();
        b1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backgroundMusic.stop();
                game.setScreen(new menu(game));
                //backgroundMusic.stop();
            }
        });

        b2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backgroundMusic.stop();
                Gdx.app.exit();
                //backgroundMusic.stop();
            }
        });

        buttonDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons.png")));
        b1.getStyle().up = buttonDrawable;
        b2.getStyle().up = buttonDrawable;
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("finaloverbgm.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
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
