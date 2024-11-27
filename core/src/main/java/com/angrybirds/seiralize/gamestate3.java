package com.angrybirds.seiralize;

import com.angrybirds.screens.levels.levelthree;
import com.angrybirds.screens.levels.leveltwo;

import java.io.Serializable;

public class gamestate3 implements Serializable
{
    private int score3;
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
    public Integer plank9Health;
    public Integer plank10Health;

    public gamestate3(levelthree screen)
    {
        this.score3 = screen.score;
        this.birdsUsed = screen.count;

        this.pig1Health = screen.m1.getHealth();
        this.pig2Health = screen.m2.getHealth();
        this.pig3Health = screen.m3.getHealth();
        this.pig4Health = screen.m4.getHealth();
        this.pig5Health = screen.m5.getHealth();

        this.plank1Health = screen.ice1.getHealth();
        this.plan2Health = screen.ice2.getHealth();
        this.plank3Health = screen.ice3.getHealth();
        this.plank4Health = screen.ice4.getHealth();
        this.plank5Health = screen.ice5.getHealth();
        this.plank6Health = screen.ice6.getHealth();
        this.plank7Health= screen.ice7.getHealth();
        this.plank9Health= screen.ice9.getHealth();
        this.plank10Health= screen.ice10.getHealth();

    }
    public int getScore3()
    {
        return score3;
    }

    public int getBirdsUsed()
    {
        return birdsUsed;
    }
}
