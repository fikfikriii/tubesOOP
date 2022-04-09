package com.monstersaku;

import java.util.Random;

public class StatusCondition extends Stats{
    private boolean isBurn = false;
    private boolean isPoison = false;
    private boolean isSleep = false;
    private boolean isParalyze = false;

    public StatusCondition(int HP, int maxHP, int att, int def, int spAtt, int spDeff, int speed){
        super(HP, maxHP, att, def, spAtt, spDeff, speed);
        this.isBurn = false;
        this.isPoison = false;
        this.isSleep = false;
        this.isParalyze = false;
    }
    // Getter atribut
    public boolean getBurn(){
        return this.isBurn;
    }
    public boolean getPoison(){
        return this.isPoison;
    }
    public boolean getSleep(){
        return this.isSleep;
    }
    public boolean getParalyze(){
        return this.isParalyze;
    }
    
    // Counting
    public int burned(){
        return (Integer.valueOf(Math.floor((super.getmaxHP() / 8))));
    }

    public int poisoned(){
        return (Integer.valueOf(Math.floor((super.getmaxHP() / 16))));
    }

    public int slept(){
        return Random.nextInt(sleep);
    }
    public int paralyzed(){
        return (super.getSpeed() / 2);
    }
}
