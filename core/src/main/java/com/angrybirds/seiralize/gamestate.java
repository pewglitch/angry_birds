package com.angrybirds.seiralize;

import com.angrybirds.screens.gamescreen;

import java.io.Serializable;

public class gamestate implements Serializable
{
    // Basic game state information
    private int score;
    private int birdsUsed;  // number of birds used

    // Pig states
    private boolean pig1Dead;
    private boolean pig2Dead;
    private boolean pig3Dead;
    private boolean pig4Dead;
    private boolean pig5Dead;

    // Plank states
    private boolean plank1Destroyed;
    private boolean plank2Destroyed;
    private boolean plank3Destroyed;
    private boolean plank4Destroyed;
    private boolean plank5Destroyed;
    private boolean plank6Destroyed;

    // Constructor to capture game state
    public gamestate(gamescreen screen) {
        // Capture basic game state
        this.score = screen.score;
        this.birdsUsed = screen.count;

        // Capture pig states
        this.pig1Dead = screen.p1.getdead();
        this.pig2Dead = screen.p2.getdead();
        this.pig3Dead = screen.p3.getdead();
        this.pig4Dead = screen.p4.getdead();
        this.pig5Dead = screen.p5.getdead();

        // Capture plank states
        this.plank1Destroyed = screen.plank1.getdead();
        this.plank2Destroyed = screen.plank2.getdead();
        this.plank3Destroyed = screen.plank3.getdead();
        this.plank4Destroyed = screen.plank4.getdead();
        this.plank5Destroyed = screen.plank5.getdead();
        this.plank6Destroyed = screen.plank6.getdead();
    }

    // Getters for all fields
    public int getScore() {
        return score;
    }

    public int getBirdsUsed() {
        return birdsUsed;
    }

    public boolean isPig1Dead() {
        return pig1Dead;
    }

    public boolean isPig2Dead() {
        return pig2Dead;
    }

    public boolean isPig3Dead() {
        return pig3Dead;
    }

    public boolean isPig4Dead() {
        return pig4Dead;
    }

    public boolean isPig5Dead() {
        return pig5Dead;
    }

    public boolean isPlank1Destroyed() {
        return plank1Destroyed;
    }

    public boolean isPlank2Destroyed() {
        return plank2Destroyed;
    }

    public boolean isPlank3Destroyed() {
        return plank3Destroyed;
    }

    public boolean isPlank4Destroyed() {
        return plank4Destroyed;
    }

    public boolean isPlank5Destroyed() {
        return plank5Destroyed;
    }

    public boolean isPlank6Destroyed() {
        return plank6Destroyed;
    }
}
