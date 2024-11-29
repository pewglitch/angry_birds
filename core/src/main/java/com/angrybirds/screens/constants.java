package com.angrybirds.screens;

public class constants
{

    public static Integer r1 = 0, r2 = 0, r3 = 0, r4 = 0;
    public static Integer levelstate=-1;
    public static String pathl1="gamebg.jpg",pathl2="level2bg2.png",pathl3="level3bg3.png";
    public constants()
    {
    }

    public Integer getR1() { return r1; }
    public Integer getR2() { return r2; }
    public Integer getR3() { return r3; }
    public Integer getR4() { return r4; }
    public void setR1(Integer r1) { this.r1 = r1; }
    public void setR2(Integer r2) { this.r2 = r2; }
    public void setR3(Integer r3) { this.r3 = r3; }
    public void setR4(Integer r4) { this.r4 = r4; }

    public Integer getLevelstate() {
        return levelstate;
    }

    public void setLevelstate(Integer levelstate) {
        this.levelstate = levelstate;
    }
}
