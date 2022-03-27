/*
    Class Move
    Tipe: Abstract Class
    Fungsi: Menampung Moveset dari monster
    Keterangan: Tidak ada fungsi perhitungan damage karena memerlukan STATS
*/

public abstract class Move {
    private String name;
    private ElementType elementType;
    private int accuracy;
    private int priority;
    private int ammunition;
    private String target;

    // Konstruktor
    public Move(String name, ElementType elementType, int accuracy, int priority, int ammunition, String target) {
        this.name = name;
        this.elementType =  elementType;
        this.accuracy = accuracy;
        this.priority = priority;
        this.ammunition = ammunition;
        this.target = target;
    }

    // Getter
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
}
