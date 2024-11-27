package com.angrybirds.screens;

import com.angrybirds.Main;
import com.angrybirds.screens.levels.leveltwo;
import com.angrybirds.seiralize.savegame;
import com.angrybirds.seiralize.savegame2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class inbtw2 implements Screen
{
    private Main game;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Texture texture,texture2;
    private Music backgroundMusic;
    private TextureRegionDrawable buttonDrawable,buttonDrawable2;
    private final float VIRTUAL_WIDTH = 1000;
    private final float VIRTUAL_HEIGHT = 600;
    private Label label,label1,label2,label3;
    private Skin skin;
    private Stage stage;
    private TextButton b4,b5;
    private Table table;
    private BitmapFont font;
    private TextButton yes,no;
    private leveltwo l2;
    public inbtw2(Main game,leveltwo l)
    {
        this.game = game;
        this.l2 = l;
        camera = new OrthographicCamera();
        table=new Table();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        texture2=new Texture("button1.png");
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        font = new BitmapFont();
        skin.add("default-font", font);
        stage=new Stage(new ScreenViewport());
        table=new Table();
        stage.addActor(table);
        texture=new Texture("inbtw.jpg");
    }
    @Override
    public void show()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("metalui/hades.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;
        BitmapFont font12 = generator.generateFont(parameter);
        generator.dispose();
        Label.LabelStyle lst = new Label.LabelStyle();
        lst.font = font12;

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("metalui/funny.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter par = new FreeTypeFontGenerator.FreeTypeFontParameter();
        BitmapFont font13 = gen.generateFont(par);

        gen.dispose();

        TextButton.TextButtonStyle btn = new TextButton.TextButtonStyle();
        btn.font = font13;
        btn.fontColor = new Color(0.0f, 0.0f, 0.55f, 1);

        TextButton.TextButtonStyle btn2 = new TextButton.TextButtonStyle();
        btn2.font = font13;
        btn2.fontColor = new Color(new Color(Color.GREEN));
        yes=new TextButton("Yes",btn);
        no=new TextButton("No",btn);


        label = new Label("Would you like to save?!", lst);
        label.setColor(0, 0f, 0, 1);
        table.setFillParent(true);
        table.top();
        table.add(label).colspan(3).center().padTop(80);
        table.row();
        table.add(yes).pad(20).width(220).height(90);
        table.add(no).pad(20).width(220).height(90);

        yes.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                savegame2.save(l2);
                constants.levelstate=2;
                game.setScreen(new menu(game));
            }
        });


        no.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new menu(game));
            }
        });
        buttonDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons.png")));
        buttonDrawable2 = new TextureRegionDrawable(new TextureRegion(new Texture("greenbtn.png")));
        yes.getStyle().up = buttonDrawable2;
        no.getStyle().up = buttonDrawable;
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("mainbgm.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.7f);
        backgroundMusic.play();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v)
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(texture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        game.batch.end();

        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
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
        backgroundMusic.stop();
    }

    @Override
    public void dispose()
    {
        texture.dispose();
        backgroundMusic.dispose();
        skin.dispose();
    }
}
