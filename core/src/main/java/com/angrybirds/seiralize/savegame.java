package com.angrybirds.seiralize;

import com.angrybirds.screens.gamescreen;
import com.angrybirds.seiralize.gamestate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.*;

public class savegame
{
    public static void save(gamescreen screen)
    {
        try
        {
            gamestate gameState = new gamestate(screen);
            FileHandle fileHandle = Gdx.files.local("savegame.dat");

            FileOutputStream fileOut = (FileOutputStream) fileHandle.write(false);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(gameState);

            out.close();
            fileOut.close();

            System.out.println("Game saved successfully!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
