package com.angrybirds.seiralize;

import com.angrybirds.screens.gamescreen;
import com.angrybirds.screens.levels.levelthree;
import com.angrybirds.screens.levels.leveltwo;
import com.angrybirds.seiralize.gamestate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.*;

public class savegame3
{
    public static void save(levelthree screen)
    {
        try
        {
            gamestate3 gameState3 = new gamestate3(screen);
            FileHandle fileHandle = Gdx.files.local("savegame.dat");

            FileOutputStream fileOut = (FileOutputStream) fileHandle.write(false);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(gameState3);

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
