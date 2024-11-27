
package com.angrybirds.screens;

import com.angrybirds.obstacles.catapult;
import com.angrybirds.obstacles.pigs;
import com.angrybirds.obstacles.planks;
import com.angrybirds.seiralize.gamestate;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.angrybirds.Main;
import com.badlogic.gdx.audio.Music;
import com.angrybirds.birds.red;


public class gamescreen implements Screen
{
    private final Main game;
    private final SpriteBatch sb;
    private Sprite sprite_plank;
    private OrthographicCamera camera;
    private OrthographicCamera box2DCamera; // Separate camera for Box2D
    private FitViewport viewport;
    private Stage stage;
    private Texture texture;
    private Texture plank_texture;
    private Skin skin;
    private Table table1;
    private Label scorelabel;
    private Label levellabel;
    private TextButton b1,b2;
    private TextureRegionDrawable buttonDrawable;
    private Music backgroundMusic;
    public int score = 0;
    public int level = 1;
    private Integer coins=0;
    private World world;
    public red bird;
    private final float WORLD_STEP = 1/60f;
    private final int VELOCITY_ITERATIONS = 6;
    private final int POSITION_ITERATIONS = 2;
    private Vector3 touchPoint;
    private boolean debugPhysics = true; // Set to true for debugging
    private Box2DDebugRenderer debugRenderer;
    private final float PIXELS_TO_METERS = 100f;
    private Integer VIRTUAL_WIDTH = 1000;
    private Integer VIRTUAL_HEIGHT = 600;
    public Integer count=0;
    public pigs p1,p2,p3,p4,p5;
    public planks plank1,plank2,plank3,plank4,plank5,plank6;
    private Array<TextureRegion> remainingBirdsTextures;
    public float[] rb;
    private static final int TOTAL_BIRDS = 5;
    private static final float BIRD_DISPLAY_Y = 50;
    private static final float BIRD_DISPLAY_SPACING = 40;
    private static final float BIRD_DISPLAY_SIZE = 40;
    private static final float INITIAL_X_POSITION = 50;
    private Body body;
    private float runtime;
    private boolean over=false;
    private catapult cata;
    private float delay = 2.0f;
    private float timer = 0.0f;private float delayTimer = 0;

    public gamescreen(Main game, SpriteBatch sb1)
    {
        this.game = game;
        this.sb = sb1;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false, VIRTUAL_WIDTH / PIXELS_TO_METERS, VIRTUAL_HEIGHT / PIXELS_TO_METERS);

        world = new World(new Vector2(0, -9.8f), true);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);

        body = world.createBody(bodyDef);

        ChainShape groundshape=new ChainShape();
        groundshape.createChain(new Vector2[]{new Vector2(-5000,0),new Vector2(5000,0)});

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape=groundshape;
        fixtureDef.friction=1000f;
        fixtureDef.restitution=0.1f;
        world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(groundshape);
        debugRenderer = new Box2DDebugRenderer();

        touchPoint = new Vector3();
        stage = new Stage(viewport, sb);
        texture = new Texture("gamebg.jpg");

        Texture birdTexture = new Texture(Gdx.files.internal("red1.png"));
        TextureRegion birdRegion = new TextureRegion(birdTexture);
        bird = new red(world, birdRegion, 208/PIXELS_TO_METERS, 180/PIXELS_TO_METERS,stage);

        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));

        table1 = new Table();
        table1.top();
        table1.setFillParent(true);

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("metalui/funny.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter par = new FreeTypeFontGenerator.FreeTypeFontParameter();
        par.size=20;
        BitmapFont font14 = gen.generateFont(par);
        gen.dispose();

        TextButton.TextButtonStyle btn1 = new TextButton.TextButtonStyle();
        btn1.font =font14;
        btn1.fontColor = new Color(0.0f, 0.0f, 0.55f, 1);

        scorelabel = new Label(String.format("Score: %05d", score), new Label.LabelStyle(font14, Color.WHITE));
        levellabel = new Label(String.format("Level: %05d", level), new Label.LabelStyle(font14, Color.WHITE));

        table1.add(scorelabel).expandX().padTop(10).left().padLeft(20);
        table1.add(levellabel).expandX().padTop(10).right().padRight(20);
        cata = new catapult(130,20);
        p1= new pigs(600,180,world);
        p2= new pigs(700,250,world);
        p3= new pigs(850,200,world);
        p4= new pigs(890,200,world);
        p5= new pigs(870,240,world);

        plank1=new planks(600,85,50,130,0,1.3f,2.1f,world);

        //second pig plank
        plank4=new planks(700,110,50,187,0,1.3f,2.2f,world);

        //vertical plank last pigs
        plank2=new planks(830,85,30,85,0,1.3f,2.3f,world);
        plank3=new planks(910,85,30,85,0,1.3f,2.3f,world);

        //horizontal planks
        plank5=new planks(870,160,37,130,90,1.4f,2.1f,world);
        plank6=new planks(870,37,37,130,90,1.4f,2.1f,world);

        world.setContactListener(new ContactListener()
        {
            @Override
            public void beginContact(Contact contact)
            {
                Object a = contact.getFixtureA().getBody().getUserData();
                Object b = contact.getFixtureB().getBody().getUserData();

                System.out.println("Contact detected: " + a + " and " + b);

                if ((a == bird && b == p1) || (a == p1 && b == bird)) {
                    p1.oncolide(100);
                    score+=100;
                    over=true;
                } else if ((a == bird && b == p2) || (a == p2 && b == bird)) {
                    p2.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == p3) || (a == p3 && b == bird)) {
                    p3.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == p4) || (a == p4 && b == bird)) {
                    p4.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == p5) || (a == p5 && b == bird)) {
                    p5.oncolide(100);score+=100;
                    over=true;
                }

                if ((a == bird && b == plank1) || (a == plank1 && b == bird)) {
                    plank1.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == plank2) || (a == plank2 && b == bird)) {
                    plank2.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == plank3) || (a == plank3 && b == bird)) {
                    plank3.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == plank4) || (a == plank4 && b == bird)) {
                    plank4.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == plank5) || (a == plank5 && b == bird)) {
                    plank5.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == plank6) || (a == plank6 && b == bird)) {
                    plank6.oncolide(100);score+=100;
                    over=true;
                }

                if ((a == bird && b == groundshape) || (a == groundshape && b == bird))
                {
                    over=true;
                }
            }

            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
        remainingBirdsTextures = new Array<>(TOTAL_BIRDS);
        rb = new float[TOTAL_BIRDS];

        for (int i = 0; i < TOTAL_BIRDS; i++)
        {
            remainingBirdsTextures.add(birdRegion);
            rb[i] = INITIAL_X_POSITION + (i * BIRD_DISPLAY_SPACING);
        }


        stage.addActor(table1);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new InputAdapter()
        {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button)
            {
                Vector3 worldCoords = new Vector3(screenX, screenY, 0);
                camera.unproject(worldCoords);
                bird.touchDown(worldCoords.x / PIXELS_TO_METERS, worldCoords.y / PIXELS_TO_METERS);
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer)
            {
                Vector3 worldCoords = new Vector3(screenX, screenY, 0);
                camera.unproject(worldCoords);
                bird.touchDragged(worldCoords.x / PIXELS_TO_METERS, worldCoords.y / PIXELS_TO_METERS);
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                Vector3 worldCoords = new Vector3(screenX, screenY, 0);
                camera.unproject(worldCoords);
                bird.touchUp(worldCoords.x / PIXELS_TO_METERS, worldCoords.y / PIXELS_TO_METERS);
                return true;
            }
        });
        show(multiplexer);
    }
    public gamescreen getx()
    {
        return this;
    }
    public void show(InputMultiplexer ix)
    {
        Gdx.input.setInputProcessor(ix);
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
                game.setScreen(new inbtw(game,getx()));
                backgroundMusic.stop();
            }
        });
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("gametheme.mp3"));
        backgroundMusic.setLooping(false);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
        stage.addActor(table1);

    }

    private void updateRemainingBirdsDisplay()
    {
        int remainingBirds=TOTAL_BIRDS-count;
        for (int i = 0; i < remainingBirds; i++) {
            rb[i] = INITIAL_X_POSITION + (i * BIRD_DISPLAY_SPACING);
        }
    }
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(WORLD_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        bird.update();

        camera.update();
        box2DCamera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(texture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        if (p1.getHealth() > 20) {
            p1.render(game.batch);
        }
        if (p2.getHealth() > 20) {
            p2.render(game.batch);
        }
        if (p3.getHealth() > 20) {
            p3.render(game.batch);
        }
        if (p4.getHealth() > 20) {
            p4.render(game.batch);
        }
        if (p5.getHealth() > 20) {
            p5.render(game.batch);
        }

        if (plank1.getHealth() > 20) {
            plank1.render(game.batch);
        }
        if (plank2.getHealth() > 20) {
            plank2.render(game.batch);
        }
        if (plank3.getHealth() > 20) {
            plank3.render(game.batch);
        }
        if (plank4.getHealth() > 20) {
            plank4.render(game.batch);
        }
        if (plank5.getHealth() > 20) {
            plank5.render(game.batch);
        }
        if (plank6.getHealth() > 20) {
            plank6.render(game.batch);
        }

        if (p1.getHealth() <= 20 && !p1.sus()) {
            p1.destroy();
        }
        if (p2.getHealth() <= 20 && !p2.sus()) {
            p2.destroy();
        }
        if (p3.getHealth() <= 20 && !p3.sus()) {
            p3.destroy();
        }
        if (p4.getHealth() <= 20 && !p4.sus()) {
            p4.destroy();
        }
        if (p5.getHealth() <= 20 && !p5.sus()) {
            p5.destroy();
        }

        if (plank1.getHealth() <= 20 && !plank1.sus()) {
            plank1.destroy();
        }
        if (plank2.getHealth() <= 20 && !plank2.sus()) {
            plank2.destroy();
        }
        if (plank3.getHealth() <= 20 && !plank3.sus()) {
            plank3.destroy();
        }
        if (plank4.getHealth() <= 20 && !plank4.sus()) {
            plank4.destroy();
        }
        if (plank5.getHealth() <= 20 && !plank5.sus()) {
            plank5.destroy();
        }
        if (plank6.getHealth() <= 20 && !plank6.sus()) {
            plank6.destroy();
        }


        int remainingBirds = TOTAL_BIRDS - count;
        for (int i = 0; i < remainingBirds; i++)
        {
            game.batch.draw(remainingBirdsTextures.get(i),
                rb[i],
                BIRD_DISPLAY_Y,
                BIRD_DISPLAY_SIZE,
                BIRD_DISPLAY_SIZE);
        }

        Texture nice=new Texture("cata.png");
        game.batch.draw(nice, cata.getX(), cata.getY(), 180, 180);

        bird.render(game.batch);
        game.batch.end();

        stage.act(delta);
        stage.draw();

        debugRenderer.render(world, box2DCamera.combined);

        Vector2 birdPosition=bird.getPosition();
        if ((bird.isLaunched() && (birdPosition.x * PIXELS_TO_METERS > VIRTUAL_WIDTH ||
            birdPosition.x * PIXELS_TO_METERS < 0 ||
            birdPosition.y * PIXELS_TO_METERS < 0 ||
            bird.isStopped())) || over)
        {
            count++;
            over=false;

            if(count < TOTAL_BIRDS)
            {
                updateRemainingBirdsDisplay();
                checkPigStatus(p1);
                checkPigStatus(p2);
                checkPigStatus(p3);
                checkPigStatus(p4);

                checkplankStatus(plank1);
                checkplankStatus(plank2);
                checkplankStatus(plank3);
                checkplankStatus(plank4);
                checkplankStatus(plank5);
                checkplankStatus(plank6);
                bird.reset();
            }
        }

        if (count >TOTAL_BIRDS)
        {
            Gdx.input.setInputProcessor(stage);
        }
        if(count==TOTAL_BIRDS)
        {
            if(score>=500)
            {
                game.setScreen(new winscreen(game,sb,score,1,sb));
            }
            else
            {
                game.setScreen(new losescreen(game,sb,score,1));
            }
        }
    }
    public void restoreGameState(gamestate gameState)
    {
        this.score = gameState.getScore();
        this.scorelabel.setText(String.format("Score: %05d", score));

        this.count = gameState.getBirdsUsed();

        this.p1.setHealth(gameState.pig1Health);
        this.p2.setHealth(gameState.pig2Health);
        this.p3.setHealth(gameState.pig3Health);
        this.p4.setHealth(gameState.pig4Health);
        this.p5.setHealth(gameState.pig5Health);

        this.plank1.setHealth(gameState.plank1Health);
        this.plank2.setHealth(gameState.plank2Health);
        this.plank3.setHealth(gameState.plank3Health);
        this.plank4.setHealth(gameState.plank4Health);
        this.plank5.setHealth(gameState.plank5Health);
        this.plank6.setHealth(gameState.plank6Health);

    }
    private void checkPigStatus(pigs pig)
    {
        if(pig!=null && !pig.sus())
        {
            if (pig.isOutOfWindow(VIRTUAL_WIDTH, VIRTUAL_HEIGHT) || pig.getdead()) {
                score += 100;
                pig.destroy();
                pig.suss=true;
                pig.setHealth(0);
                pig.getregion().setRegion(0, 0, 0, 0);
                scorelabel.setText(String.format("Score: %05d", score));
            }
        }
        else
        {
            score+=pig.getHealth();
            pig.setHealth(0);
        }
    }
    private void checkplankStatus(planks plank)
    {
        if(plank!=null && !plank.sus())
        {
            if (plank.isOutOfWindow(VIRTUAL_WIDTH, VIRTUAL_HEIGHT) || plank.getdead())
            {
                score += 100;
                plank.destroy();
                plank.suss=true;
                plank.setHealth(0);
                plank.getregion().setRegion(0, 0, 0, 0);
                scorelabel.setText(String.format("Score: %05d", score));
            }
        }
        else
        {
            score+=plank.getHealth();
            plank.setHealth(0);
        }
    }
    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }


    public void update(float deltaTime)
    {
        timer += deltaTime;

    }

    @Override
    public void pause()
    {

    }

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

