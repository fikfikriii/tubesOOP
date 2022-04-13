/*
    Class StatusMove
    Tipe: Class
    Fungsi: Menampung Moveset status dari monster
    Keterangan: Tidak ada fungsi perhitungan damage akibat status karena terkait MONSTER
*/
package com.monstersaku;

public class StatusMove extends Move {
    private String statusEffect;
    private int hpEff;
    private int attEff;
    private int defEff;
    private int spAttEff;
    private int spDefEff;
    private int spdEff;

    // Konstruktor
    public StatusMove (int id, String moveType, String name, ElementType elementType, int accuracy, int priority, int ammunition, String target, String statusEffect, int hpEff, int attEff, int defEff, int spAttEff, int spDefEff, int spdEff) {
        super(id, moveType, name, elementType, accuracy, priority, ammunition, target);
        this.statusEffect = statusEffect;
        this.hpEff = hpEff;
        this.attEff = attEff;
        this.defEff = defEff;
        this.spAttEff = spAttEff;
        this.spDefEff = spDefEff;
        this.spdEff =  spdEff;
    }

    // Getter
    public String getStatusEffect() {
        return this.statusEffect;
    }

    public int getHPEff(){
        return this.hpEff;
    }

    public int getAttEff(){
        return this.attEff;
    }

    public int getDefEff(){
        return this.defEff;
    }

    public int getSpAttEff(){
        return this.spAttEff;
    }

    public int getSpDefEff(){
        return this.spDefEff;
    }

    public int getSpdEff(){
        return this.spdEff;
    }

    // Other methods
    public void print(){
        super.print();
        System.out.println("---- Status move stats ----");
        System.out.println("Status Effect: " + this.statusEffect);
        System.out.println("HP Effect: " + this.hpEff);
        System.out.println("Att Effect: " + this.attEff);
        System.out.println("Def Effect: " + this.defEff);
        System.out.println("spAttEff Effect: " + this.spAttEff);
        System.out.println("spDefEff Effect: " + this.spDefEff);
        System.out.println("spd Effect: " + this.spdEff);
        System.out.println();
    }
}
