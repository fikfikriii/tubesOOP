package com.monstersaku;

/*
    Class Move
    Tipe: Abstract Class
    Fungsi: Menampung Moveset dari monster
    Keterangan: Tidak ada fungsi perhitungan damage karena memerlukan STATS
*/


import java.util.List;
//import java.util.ArrayList;

public class Move {
    private int id;
    private String moveType;
    private String name;
    private ElementType elementType;
    private int accuracy;
    private int priority;
    private int ammunition;
    private String target;

    // Konstruktor

    public Move(){}

    public Move(int id, String moveType, String name, ElementType elementType, int accuracy, int priority, int ammunition, String target) {
        this.id = id;
        this.moveType = moveType;
        this.name = name;
        this.elementType =  elementType;
        this.accuracy = accuracy;
        this.priority = priority;
        this.ammunition = ammunition;
        this.target = target;
    }

    public void print(){
        System.out.println("ID: " + this.id);
        System.out.println("Move Type: " + this.moveType);
        System.out.println("Name: " + this.name);
        System.out.println("Element Type: " + this.elementType);
        System.out.println("Accuracy: " + this.accuracy);
        System.out.println("Priority: " + this.priority);
        System.out.println("Ammuniiton: " + this.ammunition);
        System.out.println("Target: " + this.target);
    }

    // Getter
    public int getMoveID(){
        return this.id;
    }

    public String getMoveName() {
        return this.name;
    }

    public ElementType getMoveElType() {
        return this.elementType;
    }

    public int getMoveAccuracy() {
        return this.accuracy;
    }

    public int getMovePriority() {
        return this.priority;
    }
    
    public int getMoveAmmunition() {
        return this.ammunition;
    }

    public String getTarget() {
        return this.target;
    }

    // setter
    public void setAmmunition(int x){
        this.ammunition = x;
    }

    public void moved(){
        this.ammunition -= 1;
    }

    // other method
    public double getEffectivity(EffectivityPool ep, Monster m){
        double val = 0.0;
        List<Effectivity> le = ep.getListEffecitivty();
        for (Effectivity e : le){
            if (this.elementType == e.getSource()){
                for (ElementType et : m.elementType()){
                    if (e.getTarget() == et){
                        val = e.getEffectivity();
                        break;
                    }
                }
            }
        }

        return val;
    }

    public int totalDamage(Monster self, Monster enemy, EffectivityPool ep){
        double damage = 0;
        double random = 0.85 + Math.random() * (0.15);
        if (this.moveType.equalsIgnoreCase("normal")){
            NormalMove n = (NormalMove) this;
            damage = ((double)n.getBasePower() * ((double)self.getStats().getAtt() / (double)enemy.getStats().getDef()) + 2) * random * getEffectivity(ep, enemy) * self.burned();
        } else if (this.moveType.equalsIgnoreCase("special")){
            SpecialMove n = (SpecialMove) this;
            damage = ((double)n.getBasePower() * ((double)self.getStats().getSpAtt() / (double)enemy.getStats().getSpDef()) + 2) * random * getEffectivity(ep, enemy) * self.burned();
        } else if (this.moveType.equalsIgnoreCase("default")){
            damage = (50.0 * ((double)self.getStats().getAtt() / (double)enemy.getStats().getDef()) + 2) * random * getEffectivity(ep, enemy) * self.burned();
        }
        return (int) damage;
    }

    public int afterDamage(Monster monster) {
        double damage = 0;
        if (monster.isBurn()) {
            damage = monster.getStats().getMaxHP() * 1/8;
        } else if (monster.isPoison()) {
            damage = monster.getStats().getMaxHP() * 1/16;
        }
        return (int) damage;
    }
}