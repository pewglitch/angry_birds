
package com.angrybirds.screens;

import com.angrybirds.obstacles.catapult;
import com.angrybirds.obstacles.metals;
import com.angrybirds.obstacles.pigs;
import com.angrybirds.obstacles.planks;
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


public class leveltwo implements Screen
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
    private red bird;
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
    private metals metal1,metal2,metal3,metal4,metal5,metal6,metal7;
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

    public leveltwo(Main game, SpriteBatch sb1)
    {
        this.game = game;
        this.sb = sb1;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false, VIRTUAL_WIDTH / PIXELS_TO_METERS, VIRTUAL_HEIGHT / PIXELS_TO_METERS);

        world = new World(new Vector2(0, 0), true);

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
        texture = new Texture("level2bg3.png");

        Texture birdTexture = new Texture(Gdx.files.internal("red1.png"));
        TextureRegion birdRegion = new TextureRegion(birdTexture);
        bird = new red(world, birdRegion, 208/PIXELS_TO_METERS, 180/PIXELS_TO_METERS,stage);

        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));

        table1 = new Table();
        table1.top();
        table1.setFillParent(true);

        scorelabel = new Label(String.format("Score: %05d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        levellabel = new Label(String.format("Level: %05d", 2), new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        table1.add(scorelabel).expandX().padTop(10).left().padLeft(20);
        table1.add(levellabel).expandX().padTop(10).right().padRight(20);
        cata = new catapult(130,20);
        p1= new pigs(600,100,world);
        p2= new pigs(730,253,world);
        p3= new pigs(870,185,world);
        p4= new pigs(910,185,world);

        metal1=new metals(600,40,60,50,0,1.1f,1.6f,world);

        //second pig plank
        metal2=new metals(700,80,40,140,150,1.1f,1.3f,world);
        metal7=new metals(740,77,40,140,25,1.1f,1.3f,world);

        //vertical plank last two pigs
        metal3=new metals(890,80,80,150,0,1.1f,1.2f,world);

        //horizontal planks
        metal5=new metals(730,205,35,110,90,1.3f,1.2f,world);
        metal6=new metals(730,192,35,110,90,1.3f,1.2f,world);

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
                }
                if ((a == bird && b ==metal1) || (a == metal1 && b == bird)) {
                    metal1.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == metal2) || (a == metal2 && b == bird)) {
                    metal2.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == metal3) || (a ==metal3 && b == bird)) {
                    metal3.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b ==metal5) || (a ==metal5 && b == bird)) {
                    metal5.oncolide(100);score+=100;
                    over=true;
                }
                else if ((a == bird && b == metal6) || (a == metal6 && b == bird)) {
                    metal6.oncolide(100);score+=100;
                    over=true;
                }
                else if ((a == bird && b == metal7) || (a == metal7 && b == bird)) {
                    metal7.oncolide(100);score+=100;
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
        p1.render(game.batch);
        p2.render(game.batch);
        p3.render(game.batch);
        p4.render(game.batch);

        metal1.render(game.batch);
        metal2.render(game.batch);
        metal3.render(game.batch);
        metal5.render(game.batch);
        metal6.render(game.batch);
        metal7.render(game.batch);

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

                checkmetal(metal1);
                checkmetal(metal2);
                checkmetal(metal3);
                checkmetal(metal5);
                checkmetal(metal6);
                checkmetal(metal7);

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
    private void checkPigStatus(pigs pig)
    {
        if(pig!=null)
        {
            if (pig.isOutOfWindow(VIRTUAL_WIDTH, VIRTUAL_HEIGHT) || pig.getdead()) {
                score += 100;
                pig.destroy();
                pig.getregion().setRegion(0, 0, 0, 0);
                scorelabel.setText(String.format("Score: %05d", score));
            }
        }
    }
    private void checkmetal(metals metal)
    {
        if(metal!=null)
        {
            if (metal.isOutOfWindow(VIRTUAL_WIDTH, VIRTUAL_HEIGHT) || metal.getdead())
            {
                score += 100;
                metal.destroy();
                metal.getregion().setRegion(0, 0, 0, 0);
                scorelabel.setText(String.format("Score: %05d", score));
            }
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
        if (timer >= delay)
        {
            timer = 0.0f;

            if (count < TOTAL_BIRDS) {
                updateRemainingBirdsDisplay();
                checkPigStatus(p1);
                checkPigStatus(p2);
                checkPigStatus(p3);
                checkPigStatus(p4);

                checkmetal(metal1);
                checkmetal(metal2);
                checkmetal(metal3);
                checkmetal(metal4);
                checkmetal(metal5);
                checkmetal(metal6);


                bird.reset();
            }
        }
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

