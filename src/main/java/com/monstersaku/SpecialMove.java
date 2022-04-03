/*
    Class SpecialMove
    Tipe: Class
    Fungsi: Menampung Moveset special dari monster
    Keterangan: Tidak ada fungsi perhitungan damage karena memerlukan STATS
*/

// There isn't any different from NormalMove
package com.monstersaku;

public class SpecialMove extends Move {
    private int basepower;
    // Asumsi: semua special move target ke lawan (bukan self)

    // Konstruktor
    public SpecialMove (int id, String name, ElementType elementType, int accuracy, int priority, int ammunition, String target, int basepower) {
        super(id, name, elementType, accuracy, priority, ammunition, target);
        this.basepower = basepower;
    }

    // Getter
    public int getBasePower() {
        return this.basepower;
    }
}
