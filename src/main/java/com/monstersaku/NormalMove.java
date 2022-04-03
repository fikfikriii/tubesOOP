/*
    Class NormalMove
    Tipe: Class 
    Fungsi: Menampung Moveset normal dari monster
    Keterangan: Tidak ada fungsi perhitungan damage karena memerlukan STATS
*/
package com.monstersaku;

public class NormalMove extends Move {
    private int basepower;

    // Konstruktor
    public NormalMove (int id, String name, ElementType elementType, int accuracy, int priority, int ammunition, String target, int basepower) {
        super(id, name, elementType, accuracy, priority, ammunition, target);
        this.basepower = basepower;
    }

    // Getter
    public int getBasePower() {
        return this.basepower;
    }
}
