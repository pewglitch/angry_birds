
package com.angrybirds.screens.levels;

import com.angrybirds.birds.white;
import com.angrybirds.obstacles.*;
import com.angrybirds.screens.losescreen;
import com.angrybirds.screens.menu;
import com.angrybirds.screens.winscreen;
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


public class levelthree implements Screen
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
    private TextButton b1;
    private TextureRegionDrawable buttonDrawable;
    private Music backgroundMusic;
    private int score = 0;
    private int level = 1;
    private Integer coins=0;
    private World world;
    private white bird;
    private final float WORLD_STEP = 1/60f;
    private final int VELOCITY_ITERATIONS = 6;
    private final int POSITION_ITERATIONS = 2;
    private Vector3 touchPoint;
    private boolean debugPhysics = true; // Set to true for debugging
    private Box2DDebugRenderer debugRenderer;
    private final float PIXELS_TO_METERS = 100f;
    private Integer VIRTUAL_WIDTH = 1000;
    private Integer VIRTUAL_HEIGHT = 600;
    private Integer count=0;
    private pigs p1,p2,p3,p4,p5;
    private helmetpig m1,m2,m3,m4,m5;
    private metals metal1,metal2,metal3,metal4,metal5,plank5,plank6;
    private planks plan1, plan2, plan3, plan4,plan5,plan6,plan7,plan8,plan9,plan10;
    private Array<TextureRegion> remainingBirdsTextures;
    private float[] rb;
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
    private boolean isWaitingForDelay = false;
    private static final float DELAY_SECONDS = 2f;
    private boolean shouldProcessNextBird = false;
    public levelthree(Main game, SpriteBatch sb1)
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
        texture = new Texture("level3.jpg");

        Texture birdTexture = new Texture(Gdx.files.internal("white1.png"));
        TextureRegion birdRegion = new TextureRegion(birdTexture);
        bird = new white(world, birdRegion, 208/PIXELS_TO_METERS, 180/PIXELS_TO_METERS,stage);

        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));

        table1 = new Table();
        table1.top();
        table1.setFillParent(true);

        scorelabel = new Label(String.format("Score: %05d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levellabel = new Label(String.format("Level: %05d", 3), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table1.add(scorelabel).expandX().padTop(10).left().padLeft(20);
        table1.add(levellabel).expandX().padTop(10).right().padRight(20);
        cata = new catapult(130,20);
        m1= new helmetpig(650,240,world);
        m2= new helmetpig(700,240,world);

        //between pig
        m3= new helmetpig(690,50,world);

        //last pig
        m4= new helmetpig(920,325,world);

        m5= new helmetpig(880,155,world);

        //between
        plan1 =new planks(690,40,60,52,0,1.3f,1.9f,world);

        //vertical
        plan2 =new planks(630,80,40,180,0,1.1f,2f,world);
        plan3 =new planks(750,77,40,180,0,1.1f,2f,world);

        plan5=new planks(700,250,35,110,0,1.3f,2.5f,world);


        plan6=new planks(700,205,35,200,90,1.3f,1.9f,world);

        //flag
        plan7=new planks(900,210,40,150,0,1.1f,2.3f,world);
        plan8=new planks(930,305,30,120,90,1.1f,2f,world);

        //flag base
        plan4 =new planks(900,120,60,120,90,1.2f,2f,world);


        //last two standing planks
        plan9=new planks(900,40,25,80,0,1.1f,2f,world);
        plan10=new planks(940,40,25,80,0,1.1f,2f,world);





        world.setContactListener(new ContactListener()
        {
            @Override
            public void beginContact(Contact contact)
            {
                Object a = contact.getFixtureA().getBody().getUserData();
                Object b = contact.getFixtureB().getBody().getUserData();

                System.out.println("Contact detected: " + a + " and " + b);

                if ((a == bird && b == m1) || (a == m1 && b == bird)) {
                    m1.oncolide(100);
                    score+=100;
                    over=true;
                } else if ((a == bird && b ==m2) || (a == m2 && b == bird)) {
                    m2.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == m3) || (a == m3 && b == bird)) {
                    m3.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == m4) || (a == m4 && b == bird)) {
                    m4.oncolide(100);score+=100;
                    over=true;
                }
                else if ((a == bird && b == m5) || (a == m5 && b == bird)) {
                    m5.oncolide(100);score+=100;
                    over=true;
                }
                if ((a == bird && b == plan1) || (a == plan1 && b == bird)) {
                    plan1.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == plan2) || (a == plan2 && b == bird)) {
                    plan2.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == plan3) || (a == plan3 && b == bird)) {
                    plan3.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == plan4) || (a == plan4 && b == bird)) {
                    plan4.oncolide(100);score+=100;
                    over=true;
                }
                else if ((a == bird && b == plan5) || (a == plan5 && b == bird)) {
                    plan5.oncolide(100);score+=100;
                    over=true;
                }
                else if ((a == bird && b == plan6) || (a == plan6 && b == bird)) {
                    plan6.oncolide(100);score+=100;
                    over=true;
                }
                else if ((a == bird && b == plan7) || (a == plan7 && b == bird)) {
                    plan7.oncolide(100);score+=100;
                    over=true;
                }
                else if ((a == bird && b == plan8) || (a == plan8 && b == bird)) {
                    plan8.oncolide(100);score+=100;
                    over=true;
                }
                else if ((a == bird && b == plan9) || (a == plan9 && b == bird)) {
                    plan9.oncolide(100);score+=100;
                    over=true;
                }
                else if ((a == bird && b == plan10) || (a == plan10 && b == bird)) {
                    plan10.oncolide(100);score+=100;
                    over=true;
                }

                planks[] plans = {plan1, plan2, plan3, plan4, plan5, plan6, plan7, plan8, plan9, plan10};

                for (planks plan : plans)
                {
                    if ((a == groundshape && b == plan) || (a == plan && b == groundshape))
                    {
                        plan.oncolide(30);
                    }
                }

                helmetpig[] rx={m1,m2,m3,m4,m5};
                for(helmetpig hem:rx)
                {
                    if((a==groundshape && b==hem) || (a==hem && b==groundshape))
                    {
                        hem.oncolide(30);
                    }
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
                game.setScreen(new menu(game));
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

        //above
        world.step(WORLD_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        bird.update();

        camera.update();
        box2DCamera.update();

        //bgbc
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(texture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        m1.render(game.batch);
        m2.render(game.batch);
        m3.render(game.batch);
        m4.render(game.batch);
        m5.render(game.batch);

        plan1.render(game.batch);
        plan2.render(game.batch);
        plan3.render(game.batch);
        plan4.render(game.batch);
        plan5.render(game.batch);
        plan6.render(game.batch);
        plan7.render(game.batch);
        plan8.render(game.batch);
        plan9.render(game.batch);
        plan10.render(game.batch);

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
                checkmetalpigStatus(m1);
                checkmetalpigStatus(m2);
                checkmetalpigStatus(m3);
                checkmetalpigStatus(m4);
                checkmetalpigStatus(m5);

                checkplank(plan1);
                checkplank(plan2);
                checkplank(plan3);
                checkplank(plan4);
                checkplank(plan5);
                checkplank(plan6);
                checkplank(plan7);
                checkplank(plan8);
                checkplank(plan9);
                checkplank(plan10);

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
    private void checkmetalpigStatus(helmetpig pig1)
    {
        if(pig1!=null && !pig1.sus())
        {
            if (pig1.isOutOfWindow(VIRTUAL_WIDTH, VIRTUAL_HEIGHT) || pig1.getdead()) {
                score += 100;
                pig1.destroy();
                pig1.suss=true;
                pig1.setHealth(0);
                pig1.getregion().setRegion(0, 0, 0, 0);
                scorelabel.setText(String.format("Score: %05d", score));
            }
        }
        else
        {
            score+=pig1.getHealth();
            pig1.setHealth(0);
        }
    }
    private void checkplank(planks plank)
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

