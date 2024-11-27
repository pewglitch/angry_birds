package com.angrybirds.seiralize;

import com.angrybirds.screens.gamescreen;
import com.angrybirds.screens.levels.leveltwo;
import com.angrybirds.seiralize.gamestate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.*;

public class savegame2
{
    public static void save(leveltwo screen)
    {
        try
        {
            gamestate2 gameState2 = new gamestate2(screen);
            FileHandle fileHandle = Gdx.files.local("savegame.dat");

            FileOutputStream fileOut = (FileOutputStream) fileHandle.write(false);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(gameState2);

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
