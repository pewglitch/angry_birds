package com.angrybirds.screens;

import com.angrybirds.screens.levels.levelthree;
import com.angrybirds.screens.levels.leveltwo;
import com.badlogic.gdx.Screen;
import com.angrybirds.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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

public class losescreen implements Screen
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
    private TextButton b4, b5, b6;
    private Table table1;
    private Label label;
    private Integer score;
    private Integer l;
    public SpriteBatch sb;
    public losescreen(Main game,SpriteBatch sb1,Integer scorex,Integer level)
    {
        this.sb=sb1;
        this.game = game;
        camera = new OrthographicCamera();
        this.score=scorex;
        this.l=level;

        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        stage = new Stage(viewport,sb);
        texture = new Texture("disco.jpeg");
        skin=new Skin(Gdx.files.internal("ui/uiskin.json"));

        table1 = new Table();
        stage.addActor(table1);
    }
    @Override
    public void show()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("metalui/hades.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        BitmapFont font12 = generator.generateFont(parameter);
        generator.dispose();
        Label.LabelStyle lst = new Label.LabelStyle();
        lst.font = font12;
        label = new Label("Level Not Cleared!", lst);
        label.setColor(0, 0f, 0, 1);  // Setting the color to coral

        Label scoreLabel = new Label("Score: " + score.toString(), lst);
        scoreLabel.setColor(0, 0f, 0, 1);  // Setting the color to black

        b4 = new TextButton("Back", skin);
        b5 = new TextButton("Exit", skin);
        b6 = new TextButton("Retry level",skin);

        table1.setFillParent(true);
        table1.top();

        table1.add(b4).left().padTop(20).padLeft(30).width(100).height(40);
        table1.add().expandX();
        table1.add(b5).right().padTop(20).padRight(30).width(100).height(40);
        table1.row();
        table1.add(label).colspan(3).center().padTop(80);
        table1.row();
        table1.add(scoreLabel).colspan(3).center().padTop(20);
        table1.row();
        table1.add(b6).colspan(3).center().padTop(20).padRight(30).width(180).height(70);
        //table1.row();

        b4.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                backgroundMusic.stop();
                game.setScreen(new menu(game));
                //backgroundMusic.stop();
            }
        });

        b5.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backgroundMusic.stop();
                Gdx.app.exit();
                //backgroundMusic.stop();
            }
        });
        b6.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event,float x ,float y)
            {
                backgroundMusic.stop();
                if(l==1)
                {
                    game.setScreen( new gamescreen(game,sb));
                }
                else if(l==2)
                {
                    game.setScreen(new leveltwo(game,sb));
                }
                else
                {
                    game.setScreen(new levelthree(game,sb));
                }
            }
        });
        buttonDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons.png")));
        b4.getStyle().up= buttonDrawable;
        b5.getStyle().up= buttonDrawable;
        b6.getStyle().up= buttonDrawable;
        stage.addActor(table1);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("lose.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
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
