package com.angrybirds.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.angrybirds.Main;

public class launcher
{
    public static void main(String[] args)
    {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Angry Birds");
        config.setWindowedMode(640, 480);
        config.setForegroundFPS(60);
        config.useVsync(true);
        //credits for icon : deviant art
        config.setWindowIcon("assets/ui/angryangry.jpg");

        new Lwjgl3Application(new Main(), config);
    }
}
