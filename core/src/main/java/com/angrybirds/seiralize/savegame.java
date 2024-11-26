package com.angrybirds.seiralize;

import com.angrybirds.screens.gamescreen;
import com.angrybirds.seiralize.gamestate;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.*;

public class savegame {
    public static void save(gamescreen screen) {
        try {
            // Create game state object
            gamestate gameState = new gamestate(screen);

            // Use Gdx.files for cross-platform file handling
            FileHandle fileHandle = Gdx.files.local("savegame.dat");

            // Create output streams
            FileOutputStream fileOut = (FileOutputStream) fileHandle.write(false);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // Write the game state
            out.writeObject(gameState);

            // Close streams
            out.close();
            fileOut.close();

            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
