package com.monstersaku;
/*
    Class Move
    Tipe: Abstract Class
    Fungsi: Menampung Moveset dari monster
    Keterangan: Tidak ada fungsi perhitungan damage karena memerlukan STATS
*/

public class Move {
    private int id;
    private String name;
    private ElementType elementType;
    private int accuracy;
    private int priority;
    private int ammunition;
    private String target;

    // Konstruktor
    public Move(){}

    public Move(int id, String name, ElementType elementType, int accuracy, int priority, int ammunition, String target) {
        this.id = id;
        this.name = name;
        this.elementType =  elementType;
        this.accuracy = accuracy;
        this.priority = priority;
        this.ammunition = ammunition;
        this.target = target;
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
    public void moved(){
        this.ammunition -= 1;
    }
}
