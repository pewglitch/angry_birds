package com.angrybirds.seiralize;

import com.angrybirds.screens.levels.leveltwo;

import java.io.Serializable;

public class gamestate2 implements Serializable
{
    private int score2;
    private int birdsUsed;

    public Integer pig1Health;
    public Integer pig2Health;
    public Integer pig3Health;
    public Integer pig4Health;
    public Integer pig5Health;
    public Integer h1Health;

    public Integer plank1Health;
    public Integer plan2Health;
    public Integer plank3Health;
    public Integer plank4Health;
    public Integer plank5Health;
    public Integer plank6Health;
    public Integer plank7Health;

    public gamestate2(leveltwo screen)
    {
        this.score2 = screen.score;
        this.birdsUsed = screen.count;

        this.pig1Health = screen.p1.getHealth();
        this.pig2Health = screen.p2.getHealth();
        this.pig3Health = screen.p3.getHealth();
        this.pig4Health = screen.p4.getHealth();
        this.pig5Health = screen.p5.getHealth();
        this.h1Health= screen.h1.getHealth();

        this.plank1Health = screen.plan1.getHealth();
        this.plan2Health = screen.plan2.getHealth();
        this.plank3Health = screen.plan3.getHealth();
        this.plank4Health = screen.plan4.getHealth();
        this.plank5Health = screen.plan5.getHealth();
        this.plank6Health = screen.plan6.getHealth();
        this.plank7Health= screen.plan7.getHealth();

    }
    public int getScore2()
    {
        return score2;
    }

    public int getBirdsUsed()
    {
        return birdsUsed;
    }
}
