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

    public Integer l1=0;
    public Integer l2=0;
    public Integer l3=0;

    private Integer sx=0;

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
        constants rx=new constants();
        l1=rx.getR1();
        l2=rx.getR2();
        l3=rx.getR3();
        sx=rx.getR4();
        achieve();
    }

    public void achieve()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("metalui/funny.TTF"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 50;
        BitmapFont font12 = generator.generateFont(parameter);
        generator.dispose();
        Label.LabelStyle lst = new Label.LabelStyle();
        lst.font = font12;
        label = new Label("Achievements", lst);
        label.setColor(0, 0, 0, 1);

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("metalui/funny.TTF"));
        FreeTypeFontParameter par = new FreeTypeFontParameter();
        par.size = 25;
        BitmapFont font13 = gen.generateFont(par);
        gen.dispose();
        Label.LabelStyle lst1 = new Label.LabelStyle();
        lst1.font = font13;

        label1 = new Label("Highest Score", lst1);
        label2 = new Label("Coins earned", lst1);
        label3 = new Label("Current level", lst1);

        // Set all labels to black color
        label1.setColor(0, 0, 0, 1);
        label2.setColor(0, 0, 0, 1);
        label3.setColor(0, 0, 0, 1);

        // Create level labels with colors
        Label labellevel1 = new Label("Level 1: " + l1, lst1);
        Label labellevel2 = new Label("Level 2: " + l2, lst1);
        Label labellevel3 = new Label("Level 3: " + l3, lst1);
        Label labellevel4 = new Label("Secret mission: " + sx, lst1);

        labellevel1.setColor(0, 0, 0, 1);
        labellevel2.setColor(0, 0, 0, 1);
        labellevel3.setColor(0, 0, 0, 1);
        labellevel4.setColor(0, 0, 0, 1);

        // Position labels
        float y = VIRTUAL_HEIGHT - label.getHeight() * label.getFontScaleY() - 190;
        float margin = 60f;
        float aw = VIRTUAL_WIDTH - 2 * margin;
        float spl = aw / 3;

        float hc = (VIRTUAL_WIDTH - label.getWidth() * label.getFontScaleX()) / 2;
        float tm = VIRTUAL_HEIGHT - label.getHeight() * label.getFontScaleY() - 50;

        label.setPosition(hc, tm);
        label1.setPosition(margin, y);
        label2.setPosition(margin + 1.25f * spl, y);
        label3.setPosition(margin + 2.5f * spl, y);

        // Position level labels below
        float levelLabelY = y - 50; // Start below `label1`
        float labelSpacing = 40f;   // Spacing between level labels

        labellevel1.setPosition(margin, levelLabelY);
        labellevel2.setPosition(margin, levelLabelY - labelSpacing);
        labellevel3.setPosition(margin, levelLabelY - 2 * labelSpacing);
        labellevel4.setPosition(margin, levelLabelY - 3 * labelSpacing);

        // Add button and table
        buttonDrawable = new TextureRegionDrawable(new TextureRegion(texture2));
        BitmapFont sizefont = skin.getFont("default-font");
        sizefont.getData().setScale(1.5f);

        TextButton.TextButtonStyle tbt = new TextButton.TextButtonStyle();
        tbt.font = font13;
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

        // Add actors to stage
        stage.addActor(label);
        stage.addActor(label1);
        stage.addActor(label2);
        stage.addActor(label3);
        stage.addActor(labellevel1);
        stage.addActor(labellevel2);
        stage.addActor(labellevel3);
        stage.addActor(labellevel4);
        stage.addActor(table);

        // Set button listeners
        b4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new menu(game));
            }
        });

        b5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
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
