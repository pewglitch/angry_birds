package com.angrybirds.seiralize;

import com.angrybirds.seiralize.gamestate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.*;

public class loadgame
{
    public static gamestate load()
    {
        try
        {
            FileHandle fileHandle = Gdx.files.local("savegame.dat");

            if (!fileHandle.exists())
            {
                System.out.println("No saved game found.");
                return null;
            }

            FileInputStream fileIn = (FileInputStream) fileHandle.read();
            ObjectInputStream in = new ObjectInputStream(fileIn);

            gamestate loadedState = (gamestate) in.readObject();

            in.close();
            fileIn.close();

            System.out.println("Game loaded successfully!");
            return loadedState;
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
