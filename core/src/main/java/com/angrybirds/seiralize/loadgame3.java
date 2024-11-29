package com.angrybirds.seiralize;

import com.angrybirds.seiralize.gamestate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.*;

public class loadgame3
{
    public static gamestate3 load()
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

            gamestate3 loadedState = (gamestate3) in.readObject();

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