package com.angrybirds.screens;

import com.angrybirds.Main;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;


public class achievescreen implements Screen
{
    private Main game;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Texture texture,texture2;
    private Music backgroundMusic;
    private TextureRegionDrawable buttonDrawable;
    private final float VIRTUAL_WIDTH = 1000;
    private final float VIRTUAL_HEIGHT = 600;
    private Label label,label1,label2,label3;
    private Skin skin;
    private Stage stage;
    private TextButton b4,b5;
    private Table table;
    private BitmapFont font;


    public achievescreen(Main game)
    {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        texture = new Texture("achievement.png");
        texture2=new Texture("button1.png");
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        font = new BitmapFont();
        skin.add("default-font", font);
        stage=new Stage(new ScreenViewport());
        table=new Table();

        achieve();
    }

    public void achieve()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("metalui/opensans.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 12;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
        Label.LabelStyle lst = new Label.LabelStyle();
        lst.font = font12;
        label = new Label("Achievements", lst);
        label.setFontScale(5);
        label.setColor(0, 0,0, 1);

        label1=new Label("Highest Score", lst);
        label2=new Label("Coins earned", lst);
        label3=new Label("Current level", lst);
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
        buttonDrawable = new TextureRegionDrawable(new TextureRegion(texture2));

        BitmapFont sizefont = skin.getFont("default-font");
        sizefont.getData().setScale(1.5f);

        TextButton.TextButtonStyle tbt = new TextButton.TextButtonStyle();
        tbt.font = sizefont;
        tbt.up = buttonDrawable;
        tbt.down = buttonDrawable;
        tbt.fontColor = new Color(0f, 0f, 139f / 255f, 1f);
        b4 = new TextButton("Back", tbt);
        b5 = new TextButton("Exit", tbt);
        table.setFillParent(true);
        table.top();

        table.add(b4).width(100).height(40).pad(10).padRight(380);
        table.add(b5).width(100).height(40).pad(10).padLeft(380);
        table.row();
        table.add(label).padTop(40).colspan(2).center();
        table.row();

        table.add(label1).padTop(25);
        table.add(label2).padTop(25);
        table.add(label3).padTop(25);

        b4.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
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

        stage.addActor(label);
        stage.addActor(label1);
        stage.addActor(label2);
        stage.addActor(label3);
        stage.addActor(table);


    }

    @Override
    public void show()
    {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("mainbgm.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.7f);
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
