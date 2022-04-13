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

    // Getter
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
    
    // Setter
    public void setMaxHP(int maxHP){
        this.maxHP = maxHP;
    }    

    public void setHP(int x){
        this.HP = x;
    }

    public void setAtt(int att){
        this.att = att;
    }    

    public void setDef(int def){
        this.def = def;
    }    

    public void setSpAtt(int spAtt){
        this.spAtt = spAtt;
    }    

    public void setSpDef(int spDef){
        this.spDef = spDef;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    // Other methods
    public void tes(){
        System.out.println("Berhasil inisiasi");
    }
}
