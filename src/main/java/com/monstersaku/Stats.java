package com.monstersaku;

public class Stats {
    private int HP;
    private int maxHP;
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

    // Getter & Setter
    public int getMaxHP(){
        return this.maxHP;
    }

    public void setMaxHP(int maxHP){
        this.maxHP = maxHP;
    }

    public int getHP(){
        return this.HP;
    }

    public int getAtt(){
        return this.att;
    }

    public void setAtt(int att){
        this.att = att;
    }

    public int getDef(){
        return this.def;
    }

    public void setDef(int def){
        this.def = def;
    }

    public int getSpAtt(){
        return this.spAtt;
    }

    public void setSpAtt(int spAtt){
        this.spAtt = spAtt;
    }

    public int getSpDef(){
        return this.spDef;
    }

    public void setSpDef(int spDef){
        this.spDef = spDef;
    }

    public int getSpeed(){
        return this.speed;
    }
    
    public void setSpeed(int speed){
        this.speed = speed;
    }

    // Setter untuk Health Point
    public void setHP(int x){
        this.HP = x;
    }

    public void tes(){
        System.out.println("Berhasil inisiasi");
    }
}
