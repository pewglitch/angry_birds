
package com.angrybirds.screens.levels;

import com.angrybirds.birds.white;
import com.angrybirds.obstacles.*;
import com.angrybirds.screens.*;
import com.angrybirds.seiralize.gamestate3;
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


public class levelthree implements Screen {
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
    public int score = 0;
    private int level = 1;
    private Integer coins = 0;
    private World world;
    private white bird;
    private final float WORLD_STEP = 1 / 60f;
    private final int VELOCITY_ITERATIONS = 6;
    private final int POSITION_ITERATIONS = 2;
    private Vector3 touchPoint;
    private boolean debugPhysics = true; // Set to true for debugging
    private Box2DDebugRenderer debugRenderer;
    private final float PIXELS_TO_METERS = 100f;
    private Integer VIRTUAL_WIDTH = 1000;
    private Integer VIRTUAL_HEIGHT = 600;
    public Integer count = 0;
    private pigs p1, p2, p3, p4, p5;
    public helmetpig m1, m2, m3, m4, m5,m6;
    public golden k1;
    private metals metal1, metal2, metal3, metal4, metal5, plank5, plank6;
    public ice ice1, ice2, ice3, ice4, ice5,ice6, ice7, ice8, ice9, ice10,ice11;
    private Array<TextureRegion> rbt;
    private float[] rb;
    private static final int TB = 5;
    private static final float BY = 50;
    private static final float BSP= 40;
    private static final float BSZ = 40;
    private static final float IX = 50;
    private Body body;
    private float runtime;
    private boolean over = false;
    private catapult cata;
    private float delay = 2.0f;
    private float timer = 0.0f;
    private float delayTimer = 0;
    private boolean isWaitingForDelay = false;
    private static final float DELAY_SECONDS = 2f;
    private boolean shouldProcessNextBird = false;


    public levelthree(Main game, SpriteBatch sb1) {
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
        bodyDef.position.set(0, 0);

        body = world.createBody(bodyDef);

        ChainShape groundshape = new ChainShape();
        groundshape.createChain(new Vector2[]{new Vector2(-5000, 0), new Vector2(5000, 0)});

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundshape;
        fixtureDef.friction = 1000f;
        fixtureDef.restitution = 0.1f;
        world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(groundshape);
        debugRenderer = new Box2DDebugRenderer();

        touchPoint = new Vector3();
        stage = new Stage(viewport, sb);
        texture = new Texture("level2bg3.png");

        Texture birdTexture = new Texture(Gdx.files.internal("white1.png"));
        TextureRegion birdRegion = new TextureRegion(birdTexture);
        bird = new white(world, birdRegion, 200 / PIXELS_TO_METERS, 150 / PIXELS_TO_METERS, stage);

        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));

        table1 = new Table();
        table1.top();
        table1.setFillParent(true);

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("metalui/funny.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter par = new FreeTypeFontGenerator.FreeTypeFontParameter();
        par.size=20;
        BitmapFont font15 = gen.generateFont(par);
        gen.dispose();

        TextButton.TextButtonStyle btn1 = new TextButton.TextButtonStyle();
        btn1.font =font15;
        btn1.fontColor = new Color(0.0f, 0.0f, 0.55f, 1);

        scorelabel = new Label(String.format("Score: %05d", score), new Label.LabelStyle(font15, Color.WHITE));
        levellabel = new Label(String.format("Level: %05d", 3), new Label.LabelStyle(font15, Color.WHITE));

        table1.add(scorelabel).expandX().padTop(10).left().padLeft(20);
        table1.add(levellabel).expandX().padTop(10).right().padRight(20);
        cata = new catapult(130, 20);
        m1 = new helmetpig(650, 240, world);
        m2 = new helmetpig(700, 240, world);

        //between pig
        m3 = new helmetpig(690, 50, world);

        //last pig
        m4 = new helmetpig(800, 100, world);

        m5 = new helmetpig(685, 385, world);

        //first ground plank with pig
        ice11 = new ice(540, 40, 60, 52, 0, 1.1f, 1.3f, world);
        k1= new golden(540,80,world);

        //between
        ice1 = new ice(690, 40, 60, 52, 0, 1.3f, 1.9f, world);

        //vertical
        ice2 = new ice(630, 80, 40, 180, 0, 1.1f, 2f, world);
        ice3 = new ice(745, 77, 40, 180, 0, 1.1f, 2f, world);

        ice5 = new ice(700, 250, 35, 110, 0, 1.3f, 2.5f, world);


        ice6 = new ice(700, 205, 37, 200, 90, 1.3f, 1.3f, world);

        //flag
        ice7 = new ice(700, 370, 43, 155, 95, 1.1f, 1.4f, world);
        /*
        plan8=new planks(900,330,30,120,90,1.1f,2f,world);


         */
        //flag base
        ice4 = new ice(900, 280, 43, 420, 5.5f, 1.2f, 1.5f, world);


        //last two standing planks
        ice9 = new ice(820, 120, 25, 200, 0, 1.1f, 2f, world);
        ice10 = new ice(870, 40, 25, 80, 90, 1.1f, 2f, world);


        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Object a = contact.getFixtureA().getBody().getUserData();
                Object b = contact.getFixtureB().getBody().getUserData();

                System.out.println("Contact detected: " + a + " and " + b);

                if ((a == bird && b == m1) || (a == m1 && b == bird)) {
                    m1.oncolide(100);
                    score += 100;
                    over = true;
                }
                else if((a==bird && b==k1) || (a==k1 && b==bird))
                {
                    k1.oncolide(100);
                    score+=100;
                    over= true;
                }
                else if ((a == bird && b == m2) || (a == m2 && b == bird)) {
                    m2.oncolide(100);
                    score += 100;
                    over = true;
                } else if ((a == bird && b == m3) || (a == m3 && b == bird)) {
                    m3.oncolide(100);
                    score += 100;
                    over = true;
                } else if ((a == bird && b == m4) || (a == m4 && b == bird)) {
                    m4.oncolide(100);
                    score += 100;
                    over = true;
                } else if ((a == bird && b == m5) || (a == m5 && b == bird)) {
                    m5.oncolide(100);
                    score += 100;
                    over = true;
                }
                else if ((a == bird && b == m6) || (a == m6 && b == bird)) {
                    m6.oncolide(100);
                    score += 100;
                    over = true;
                }
                if ((a == bird && b == ice1) || (a == ice1 && b == bird)) {
                    ice1.oncolide(100);
                    score += 100;
                    over = true;
                } else if ((a == bird && b == ice2) || (a == ice2 && b == bird)) {
                    ice2.oncolide(100);
                    score += 100;
                    over = true;
                } else if ((a == bird && b == ice3) || (a == ice3 && b == bird)) {
                    ice3.oncolide(100);
                    score += 100;
                    over = true;
                } else if ((a == bird && b == ice4) || (a == ice4 && b == bird)) {
                    ice4.oncolide(100);
                    score += 100;
                    over = true;
                } else if ((a == bird && b == ice5) || (a == ice5 && b == bird)) {
                    ice5.oncolide(100);
                    score += 100;
                    over = true;
                } else if ((a == bird && b == ice6) || (a == ice6 && b == bird)) {
                    ice6.oncolide(100);
                    score += 100;
                    over = true;
                } else if ((a == bird && b == ice7) || (a == ice7 && b == bird)) {
                    ice7.oncolide(100);
                    score += 100;
                    over = true;
                }
                else if ((a == bird && b == ice8) || (a == ice8 && b == bird)) {
                    ice8.oncolide(100);
                    score += 100;
                    over = true;
                }
                else if ((a == bird && b == ice9) || (a == ice9 && b == bird)) {
                    ice9.oncolide(100);
                    score += 100;
                    over = true;
                } else if ((a == bird && b == ice10) || (a == ice10 && b == bird)) {
                    ice10.oncolide(100);
                    score += 100;
                    over = true;
                }
                else if ((a == bird && b == ice11) || (a == ice11 && b == bird)) {
                    ice11.oncolide(100);
                    score += 100;
                    over = true;
                }
                ice[] ices = {ice1, ice2, ice3, ice4, ice5, ice6, ice7, ice8, ice9, ice10,ice11};

                for (ice plan : ices) {
                    if ((a == groundshape && b == plan) || (a == plan && b == groundshape)) {
                        plan.oncolide(30);
                    }
                }

                helmetpig[] rx = {m1, m2, m3, m4, m5};
                for (helmetpig hem : rx) {
                    if ((a == groundshape && b == hem) || (a == hem && b == groundshape)) {
                        hem.oncolide(30);
                    }
                }

                if ((a == bird && b == groundshape) || (a == groundshape && b == bird)) {
                    over = true;
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
        rbt = new Array<>(TB);
        rb = new float[TB];

        for (int i = 0; i < TB; i++) {
            rbt.add(birdRegion);
            rb[i] = IX + (i * BSP);
        }


        stage.addActor(table1);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 worldCoords = new Vector3(screenX, screenY, 0);
                camera.unproject(worldCoords);
                bird.touchDown(worldCoords.x / PIXELS_TO_METERS, worldCoords.y / PIXELS_TO_METERS);
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
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

    public void show(InputMultiplexer ix) {
        Gdx.input.setInputProcessor(ix);
    }

    @Override
    public void show() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("metalui/funny.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        BitmapFont font12 = generator.generateFont(parameter);
        generator.dispose();

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("metalui/funny.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter par = new FreeTypeFontGenerator.FreeTypeFontParameter();
        par.size = 20;
        BitmapFont font13 = gen.generateFont(par);
        gen.dispose();

        TextButton.TextButtonStyle btn = new TextButton.TextButtonStyle();
        btn.font = font13;
        btn.fontColor = new Color(0.0f, 0.0f, 0.55f, 1);

        b1 =new TextButton("Home", btn);
        b1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                backgroundMusic.stop();
                game.setScreen(new inbtw3(game,getz()));
                //backgroundMusic.stop();
            }
        });

        table1.setFillParent(true);
        table1.top();
        table1.add(b1).padTop(10).padLeft(90).padRight(5).width(120).height(50);
        buttonDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("buttons.png")));
        b1.getStyle().up = buttonDrawable;

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("level3bgm.mp3"));
        backgroundMusic.setLooping(false);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
        stage.addActor(table1);

    }

    private void updateRemainingBirdsDisplay() {
        int rB = TB - count;
        for (int i = 0; i < rB; i++) {
            rb[i] = IX + (i * BSP);
        }
    }

    public void render(float delta) {
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
        if (m1.getHealth() > 20) {
            m1.render(game.batch);
        }
        if (m2.getHealth() > 20) {
            m2.render(game.batch);
        }
        if (m3.getHealth() > 20) {
            m3.render(game.batch);
        }
        if (m4.getHealth() > 20) {
            m4.render(game.batch);
        }
        if (m5.getHealth() > 20) {
            m5.render(game.batch);
        }
        if (k1.getHealth() > 20) {
            k1.render(game.batch);
        }


        if (ice1.getHealth() > 20) {
            ice1.render(game.batch);
        }
        if (ice2.getHealth() > 20) {
            ice2.render(game.batch);
        }
        if (ice3.getHealth() > 20) {
            ice3.render(game.batch);
        }
        if (ice4.getHealth() > 20) {
            ice4.render(game.batch);
        }
        if (ice5.getHealth() > 20) {
            ice5.render(game.batch);
        }
        if (ice6.getHealth() > 20) {
            ice6.render(game.batch);
        }
        if (ice7.getHealth() > 20) {
            ice7.render(game.batch);
        }
        if (ice9.getHealth() > 20) {
            ice9.render(game.batch);
        }
        if (ice10.getHealth() > 20) {
            ice10.render(game.batch);
        }
        if (ice11.getHealth() > 20) {
            ice11.render(game.batch);
        }

        if (m1.getHealth() <= 20 && !m1.sus()) {
            m1.destroy();
        }
        if (m2.getHealth() <= 20 && !m2.sus()) {
            m2.destroy();
        }
        if (m3.getHealth() <= 20 && !m3.sus()) {
            m3.destroy();
        }
        if (m4.getHealth() <= 20 && !m4.sus()) {
            m4.destroy();
        }
        if (m5.getHealth() <= 20 && !m5.sus()) {
            m5.destroy();
        }
        if (k1.getHealth() <= 20 && !k1.sus()) {
            k1.destroy();
        }



        if (ice1.getHealth() <= 20 && !ice1.sus()) {
            ice1.destroy();
        }
        if (ice2.getHealth() <= 20 && !ice2.sus()) {
            ice2.destroy();
        }
        if (ice3.getHealth() <= 20 && !ice3.sus()) {
            ice3.destroy();
        }
        if (ice4.getHealth() <= 20 && !ice4.sus()) {
            ice4.destroy();
        }
        if (ice5.getHealth() <= 20 && !ice5.sus()) {
            ice5.destroy();
        }
        if (ice6.getHealth() <= 20 && !ice6.sus()) {
            ice6.destroy();
        }
        if (ice7.getHealth() <= 20 && !ice7.sus()) {
            ice7.destroy();
        }
        if (ice9.getHealth() <= 20 && !ice9.sus()) {
            ice9.destroy();
        }
        if (ice10.getHealth() <= 20 && !ice10.sus()) {
            ice10.destroy();
        }
        if (ice11.getHealth() <= 20 && !ice11.sus()) {
            ice11.destroy();
        }

        int rbs = TB - count;
        for (int i = 0; i < rbs; i++) {
            game.batch.draw(rbt.get(i),
                rb[i]-50,
                BY-10,
                BSZ,
                BSZ);
        }

        Texture nice = new Texture("cata.png");
        game.batch.draw(nice, cata.getX()-20, cata.getY(), 190, 150);

        bird.render(game.batch);
        game.batch.end();

        stage.act(delta);
        stage.draw();


        debugRenderer.render(world, box2DCamera.combined);


        Vector2 birdPosition = bird.getPosition();
        if ((bird.isLaunched() && (birdPosition.x * PIXELS_TO_METERS > VIRTUAL_WIDTH ||
            birdPosition.x * PIXELS_TO_METERS < 0 ||
            birdPosition.y * PIXELS_TO_METERS < 0 ||
            bird.isStopped())) || over) {
            count++;
            over = false;

            if (count < TB) {
                updateRemainingBirdsDisplay();
                checkmetalpigStatus(m1);
                checkmetalpigStatus(m2);
                checkmetalpigStatus(m3);
                checkmetalpigStatus(m4);
                checkmetalpigStatus(m5);
                kingpigstatus(k1);

                checkplank(ice1);
                checkplank(ice2);
                checkplank(ice3);
                checkplank(ice4);
                checkplank(ice5);
                checkplank(ice6);
                checkplank(ice7);
                checkplank(ice9);
                checkplank(ice10);
                checkplank(ice11);

                bird.reset();
            }
        }

        if (count > TB) {
            Gdx.input.setInputProcessor(stage);
        }
        if (count == TB) {
            if (score >= 500)
            {
                backgroundMusic.stop();
                game.setScreen(new winscreen(game, sb, score, 3, sb));
            }
            else
            {
                backgroundMusic.stop();
                game.setScreen(new losescreen(game, sb, score, 3));
            }

        }
    }

    public void restoreGameState3(gamestate3 gameState3) {
        this.score = gameState3.getScore3();
        this.scorelabel.setText(String.format("Score: %05d", score));

        this.count = gameState3.getBirdsUsed();

        this.m1.setHealth(gameState3.pig1Health);
        this.m2.setHealth(gameState3.pig2Health);
        this.m3.setHealth(gameState3.pig3Health);
        this.m4.setHealth(gameState3.pig4Health);
        this.m5.setHealth(gameState3.pig5Health);
        this.k1.setHealth(gameState3.pig6Health);


        this.ice1.setHealth(gameState3.plank1Health);
        this.ice2.setHealth(gameState3.plan2Health);
        this.ice3.setHealth(gameState3.plank3Health);
        this.ice4.setHealth(gameState3.plank4Health);
        this.ice5.setHealth(gameState3.plank5Health);
        this.ice6.setHealth(gameState3.plank6Health);
        this.ice7.setHealth(gameState3.plank7Health);
        this.ice9.setHealth(gameState3.plank9Health);
        this.ice10.setHealth(gameState3.plank10Health);
        this.ice11.setHealth(gameState3.plank11Health);
    }

    private void checkmetalpigStatus(helmetpig pig1) {
        if (pig1 != null && !pig1.sus()) {
            if (pig1.isOutOfWindow(VIRTUAL_WIDTH, VIRTUAL_HEIGHT) || pig1.getdead()) {
                score += 100;
                pig1.destroy();
                pig1.suss = true;
                pig1.setHealth(0);
                pig1.getregion().setRegion(0, 0, 0, 0);
                scorelabel.setText(String.format("Score: %05d", score));
            }
        } else {
            score += pig1.getHealth();
            pig1.setHealth(0);
        }
    }
    private void kingpigstatus(golden pig1) {
        if (pig1 != null && !pig1.sus()) {
            if (pig1.isOutOfWindow(VIRTUAL_WIDTH, VIRTUAL_HEIGHT) || pig1.getdead()) {
                score += 100;
                pig1.destroy();
                pig1.suss = true;
                pig1.setHealth(0);
                pig1.getregion().setRegion(0, 0, 0, 0);
                scorelabel.setText(String.format("Score: %05d", score));
            }
        } else {
            score += pig1.getHealth();
            pig1.setHealth(0);
        }
    }

    private void checkplank(ice plank) {
        if (plank != null && !plank.sus()) {
            if (plank.isOutOfWindow(VIRTUAL_WIDTH, VIRTUAL_HEIGHT) || plank.getdead()) {
                score += 100;
                plank.destroy();
                plank.suss = true;
                plank.setHealth(0);
                plank.getregion().setRegion(0, 0, 0, 0);
                scorelabel.setText(String.format("Score: %05d", score));
            }
        } else {
            score += plank.getHealth();
            plank.setHealth(0);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }


    public void update(float deltaTime) {
        timer += deltaTime;
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
        stage.dispose();
        texture.dispose();
        backgroundMusic.dispose();
    }

    public levelthree getz() {
        return this;
    }
}

