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
<<<<<<< Updated upstream
    public SpecialMove (String name, ElementType elementType, int accuracy, int priority, int ammunition, String target, int basepower) {
        super(name, elementType, accuracy, priority, ammunition, target);
=======
    public SpecialMove (int id, String moveType, String name, ElementType elementType, int accuracy, int priority, int ammunition, String target, int basepower) {
        super(id, moveType, name, elementType, accuracy, priority, ammunition, target);
>>>>>>> Stashed changes
        this.basepower = basepower;
    }

    // Getter
    public int getBasePower() {
        return this.basepower;
    }

    public void print(){
        super.print();
        System.out.println("Base power: " + this.basepower);
        System.out.println();
    }
}
