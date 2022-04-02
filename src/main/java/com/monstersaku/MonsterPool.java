/*
MonsterPool dipake buat nyimpen objek-objek monster pada monstersaku.
Selain itu, class ini juga bisa dipakai sebagai atributnya Player karena 1 player bisa punya beberapa monsters.
*/

package com.monstersaku;
import java.util.List;
import java.util.ArrayList;

public class MonsterPool {
    private List<Monster> listMonster;

    public MonsterPool(){
        List<Monster> listMonster = new ArrayList<Monster>();
        this.listMonster = listMonster;
    }

    //getter
    public List<Monster> getListMonster(){
        return this.listMonster;
    }

    // setter (menambah objek Monster)
    public void addMonster(Monster m){
        (this.listMonster).add(m);
    }

    public void printMonsterPool(){
        System.out.println("--- Ini adalah list monstersaku ---");
        for (Monster m : listMonster){
            System.out.printf("%d %s%n", m.getMonsterID(), m.getMonsterName());
            m.printMonsterMove();
        }
    }
}
