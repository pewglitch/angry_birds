
package com.angrybirds.screens.levels;

import com.angrybirds.birds.yellow;
import com.angrybirds.obstacles.*;
import com.angrybirds.screens.inbtw2;
import com.angrybirds.screens.losescreen;
import com.angrybirds.screens.winscreen;
import com.angrybirds.seiralize.gamestate2;
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
    public int score = 0;
    private int level = 1;
    private Integer coins=0;
    private World world;
    private yellow bird;
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
    public helmetpig h1;
    public metals plan1,plan2,plan3,plan4,plan5,plan6,plan7,plan8;
    private Array<TextureRegion> rbt;
    private float[] rb;
    private static final int TB = 5;
    private static final float BY =50;
    private static final float BSP = 40;
    private static final float BSZ = 40;
    private static final float IX = 50;
    private Body body;
    private float runtime;
    private boolean over=false;
    private catapult cata;
    private float delay = 2.0f;
    private float timer = 0.0f;private float delayTimer = 0;
    private boolean isWaitingForDelay = false;
    private static final float DELAY_SECONDS = 2f;
    private boolean shouldProcessNextBird = false;

    private yellow bird2;

    public leveltwo(Main game, SpriteBatch sb1)
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
        texture = new Texture("level2bg3.png");

        Texture bird22= new Texture("yellow_new.png");
        TextureRegion bird22r=new TextureRegion(bird22);

        bird = new yellow(world, bird22r, 200/PIXELS_TO_METERS, 150/PIXELS_TO_METERS,stage);

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
        levellabel = new Label(String.format("Level: %05d", 2), new Label.LabelStyle(font14, Color.WHITE));

        table1.add(scorelabel).expandX().padTop(10).left().padLeft(20);
        table1.add(levellabel).expandX().padTop(10).right().padRight(20);
        cata = new catapult(130,20);
        p1= new pigs(600,90,world);

        p2= new pigs(700,100,world);
        p5= new pigs(780,395,world);

        p3= new pigs(910,185,world);
        p4= new pigs(950,185,world);



        plan1=new metals(600,40,60,55,0,1.3f,1.3f,world);

        //second pig plank
        plan2=new metals(690,80,40,185,0,1.1f,1.1f,world);
        plan3=new metals(780,65,40,185,0,1.1f,1.1f,world);

        //vertical plank last two pigs
        plan4=new metals(930,80,92,150,0,1f,1f,world);

        //T
        plan5=new metals(790,400,35,130,40,1.3f,1f,world);
        plan7=new metals(730,400,35,140,135,1.3f,1f,world);

        plan6=new metals(750,380,52,230,88,1f,1f,world);

        h1=new helmetpig(840,100,world);



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
                else if ((a == bird && b == p5) || (a == p5 && b == bird)) {
                    p5.oncolide(100);score+=100;
                    over=true;
                }
                else if ((a == bird && b == h1) || (a == h1 && b == bird)) {
                    h1.oncolide(100);score+=100;
                    over=true;
                }
                if ((a == bird && b ==plan1) || (a == plan1 && b == bird)) {
                    plan1.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == plan2) || (a ==plan2 && b == bird)) {
                    plan2.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b == plan3) || (a ==plan3 && b == bird)) {
                    plan3.oncolide(100);score+=100;
                    over=true;
                } else if ((a == bird && b ==plan4) || (a ==plan4 && b == bird)) {
                    plan4.oncolide(100);score+=100;
                    over=true;
                }
                else if ((a == bird && b == plan5) || (a == plan5 && b == bird)) {
                    plan5.oncolide(100);score+=100;
                    over=true;
                }
                else if ((a == bird && b ==plan6) || (a == plan6&& b == bird)) {
                    plan6.oncolide(100);score+=100;
                    over=true;
                }
                else if ((a == bird && b ==plan7) || (a == plan7 && b == bird)) {
                    plan7.oncolide(100);score+=100;
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
        rbt = new Array<>(TB);
        rb = new float[TB];

        for (int i = 0; i < TB; i++)
        {
            rbt.add(bird22r);
            rb[i] = IX + (i * BSP);
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
                backgroundMusic.stop();
                game.setScreen(new inbtw2(game,gety()));
                //backgroundMusic.stop();
            }
        });
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("level2bgm.mp3"));
        backgroundMusic.setLooping(false);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
        stage.addActor(table1);

    }

    private void updateRemainingBirdsDisplay()
    {
        int remainingBirds= TB -count;
        for (int i = 0; i < remainingBirds; i++) {
            rb[i] = IX + (i * BSP);
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
        if (h1.getHealth() > 20) {
            h1.render(game.batch);
        }

        if (plan1.getHealth() > 20) {
            plan1.render(game.batch);
        }
        if (plan2.getHealth() > 20) {
            plan2.render(game.batch);
        }
        if (plan3.getHealth() > 20) {
            plan3.render(game.batch);
        }
        if (plan4.getHealth() > 20) {
            plan4.render(game.batch);
        }
        if (plan5.getHealth() > 20) {
            plan5.render(game.batch);
        }
        if (plan6.getHealth() > 20) {
            plan6.render(game.batch);
        }
        if (plan7.getHealth() > 20) {
            plan7.render(game.batch);
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
        if (h1.getHealth() <= 20 && !h1.sus()) {
            h1.destroy();
        }

        if (plan1.getHealth() <= 20 && !plan1.sus()) {
            plan1.destroy();
        }
        if (plan2.getHealth() <= 20 && !plan2.sus()) {
            plan2.destroy();
        }
        if (plan3.getHealth() <= 20 && !plan3.sus()) {
            plan3.destroy();
        }
        if (plan4.getHealth() <= 20 && !plan4.sus()) {
            plan4.destroy();
        }
        if (plan5.getHealth() <= 20 && !plan5.sus()) {
            plan5.destroy();
        }
        if (plan6.getHealth() <= 20 && !plan6.sus()) {
            plan6.destroy();
        }
        if (plan7.getHealth() <= 20 && !plan7.sus()) {
            plan7.destroy();
        }

        int rbs = TB - count;
        for (int i = 0; i < rbs; i++)
        {
            game.batch.draw(rbt.get(i),
                rb[i]-40,
                BY-10,
                BSZ,
                BSZ);
        }

        Texture nice=new Texture("cata.png");
        game.batch.draw(nice, cata.getX()-20, cata.getY(), 190, 150);

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

            if(count < TB)
            {
                updateRemainingBirdsDisplay();
                checkPigStatus(p1);
                checkPigStatus(p2);
                checkPigStatus(p3);
                checkPigStatus(p4);
                checkPigStatus(p5);
                checkhelmetpig(h1);

                checkplank(plan1);
                checkplank(plan2);
                checkplank(plan3);
                checkplank(plan4);
                checkplank(plan5);
                checkplank(plan6);
                checkplank(plan7);


                bird.reset();
            }
        }

        if (count > TB)
        {
            Gdx.input.setInputProcessor(stage);
        }
        if(count== TB)
        {
            if(score>=500)
            {
                backgroundMusic.stop();
                game.setScreen(new winscreen(game,sb,score,2,sb));
            }
            else
            {
                backgroundMusic.stop();
                game.setScreen(new losescreen(game,sb,score,2));
            }

        }
    }

    public void restoreGameState2(gamestate2 gameState2)
    {
        this.score = gameState2.getScore2();
        this.scorelabel.setText(String.format("Score: %05d", score));

        this.count = gameState2.getBirdsUsed();

        this.p1.setHealth(gameState2.pig1Health);
        this.p2.setHealth(gameState2.pig2Health);
        this.p3.setHealth(gameState2.pig3Health);
        this.p4.setHealth(gameState2.pig4Health);
        this.p5.setHealth(gameState2.pig5Health);
        this.h1.setHealth(gameState2.h1Health);

        this.plan1.setHealth(gameState2.plank1Health);
        this.plan2.setHealth(gameState2.plan2Health);
        this.plan3.setHealth(gameState2.plank3Health);
        this.plan4.setHealth(gameState2.plank4Health);
        this.plan5.setHealth(gameState2.plank5Health);
        this.plan6.setHealth(gameState2.plank6Health);
        this.plan7.setHealth(gameState2.plank7Health);
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
    private void checkhelmetpig(helmetpig h)
    {
        if(h!=null)
        {
            if (h.isOutOfWindow(VIRTUAL_WIDTH, VIRTUAL_HEIGHT) || h.getdead()) {
                score += 100;
                h.destroy();
                h.getregion().setRegion(0, 0, 0, 0);
                scorelabel.setText(String.format("Score: %05d", score));
            }
        }
    }
    private void checkplank(metals plank)
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

    public leveltwo gety(){
        return this;
    }
}

