package com.angrybirds.buttons;

import com.angrybirds.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class taptap
{
    private Skin skin;
    private Stage stage;
    private Table table;

    public taptap()
    {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    public TextButton createButton(String buttonText, final Main game, final Screen nextScreen)
    {
        TextButton button = new TextButton(buttonText,skin);
        button.getStyle().fontColor = Color.CORAL;
        button.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                game.setScreen(nextScreen);
            }
        });

        table.add(button).width(200).height(80).pad(10);
        return button;
    }

    public TextButton createExitButton(String buttonText)
    {
        TextButton button = new TextButton(buttonText, skin);

        button.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });

        table.add(button).width(200).height(80).pad(10);
        return button;
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update viewport
    }
}
