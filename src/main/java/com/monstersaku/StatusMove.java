package com.monstersaku;
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
    private int atEff;
    private int defEff;
    private int spAtEff;
    private int spDefEff;
    private int spdEff;

    // Konstruktor
    public StatusMove (int id, String name, ElementType elementType, int accuracy, int priority, int ammunition, String target, String statusEffect, int hpEff, int attEff, int defEff, int spAtEff, int spDefEff, int spdEff) {
        super(id, name, elementType, accuracy, priority, ammunition, target);
        this.statusEffect = statusEffect;
        this.hpEff = hpEff;
        this.atEff = atEff;
        this.defEff = defEff;
        this.spAtEff = spAtEff;
        this.spDefEff = spDefEff;
        this.spdEff =  spdEff;
    }

    // Getter
    public String getStatusEffect() {
        return this.statusEffect;
    }
}
