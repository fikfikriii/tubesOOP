/*
    Class StatusMove
    Tipe: Class
    Fungsi: Menampung Moveset status dari monster
    Keterangan: Tidak ada fungsi perhitungan damage akibat status karena terkait MONSTER
*/

public class StatusMove extends Move {
    private String statusEffect;
    // private int atEff;
    // private int defEff;
    // private int spAtEff;
    // private int spDefEff;
    // private int spdEff;

    // Konstruktor
    public StatusMove (String name, ElementType elementType, int accuracy, int priority, int ammunition, String target, String statusEffect) {
        super(name, elementType, accuracy, priority, ammunition, target);
        this.statusEffect = statusEffect;
    }

    // Getter
    public String getStatusEffect() {
        return this.statusEffect;
    }
}