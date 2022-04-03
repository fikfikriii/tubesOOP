// Kode coba-coba utk Move
package com.monstersaku;

public class DriverMove {
    public static void main(String[] args) {
        NormalMove pukul = new NormalMove(1, "Pukul", ElementType.NORMAL, 100, 1, 10, "ENEMY", 50);

        System.out.println("Nama: " + pukul.getMoveName());
        System.out.println("Type: " + pukul.getMoveElType());
        System.out.println("Accuracy: " + pukul.getMoveAccuracy());
        System.out.println("Priority " + pukul.getMovePriority());
        System.out.println("Ammunition: " + pukul.getMoveAmmunition());
        System.out.println("Target: " + pukul.getTarget());
        System.out.println("Base Power: " + pukul.getBasePower());
    }
}
