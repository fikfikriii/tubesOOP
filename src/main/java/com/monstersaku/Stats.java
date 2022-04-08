package com.monstersaku;

public class Stats {
    private int maxHP;
    private int HP;
    private int att;
    private int def;
    private int spAtt;
    private int spDef;
    private int speed;

    public Stats(int HP, int att, int def, int spAtt, int spDef, int speed){
        this.maxHP = HP;
        this.HP = HP;
        this.att = att;
        this.def = def;
        this.spAtt = spAtt;
        this.spDef = spDef;
        this.speed = speed;
    }

    // getter
    public int getMaxHP(){
        return this.maxHP;
    }

    public int getHP(){
        return this.HP;
    }

    public int getAtt(){
        return this.att;
    }

    public int getDef(){
        return this.def;
    }

    public int getSpAtt(){
        return this.spAtt;
    }

    public int getSpDef(){
        return this.spDef;
    }

    public int getSpeed(){
        return this.speed;
    }

    // setter
    public void setHP(int x){
        this.HP = x;
    }

    public void tes(){
        System.out.println("Berhasil inisiasi");
    }
}
