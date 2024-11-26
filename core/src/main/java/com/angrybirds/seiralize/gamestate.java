package com.angrybirds.seiralize;

import com.angrybirds.screens.gamescreen;

import java.io.Serializable;

public class gamestate implements Serializable
{
    private int score;
    private int birdsUsed;

    public Integer pig1Health;
    public Integer pig2Health;
    public Integer pig3Health;
    public Integer pig4Health;
    public Integer pig5Health;

    public Integer plank1Health;
    public Integer plank2Health;
    public Integer plank3Health;
    public Integer plank4Health;
    public Integer plank5Health;
    public Integer plank6Health;

    public gamestate(gamescreen screen)
    {
        this.score = screen.score;
        this.birdsUsed = screen.count;

        this.pig1Health = screen.p1.getHealth();
        this.pig2Health = screen.p2.getHealth();
        this.pig3Health = screen.p3.getHealth();
        this.pig4Health = screen.p4.getHealth();
        this.pig5Health = screen.p5.getHealth();

        this.plank1Health = screen.plank1.getHealth();
        this.plank2Health = screen.plank2.getHealth();
        this.plank3Health = screen.plank3.getHealth();
        this.plank4Health = screen.plank4.getHealth();
        this.plank5Health = screen.plank5.getHealth();
        this.plank6Health = screen.plank6.getHealth();

    }
    public int getScore()
    {
        return score;
    }

    public int getBirdsUsed()
    {
        return birdsUsed;
    }
}
