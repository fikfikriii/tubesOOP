/*
MonsterPool dipake buat nyimpen objek-objek monster pada monstersaku.
Selain itu, class ini juga bisa dipakai sebagai atributnya Player karena 1 player bisa punya beberapa monsters.
*/

package com.monstersaku;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

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

    public int getSize(){
        return listMonster.size();
    }

    // setter (menambah objek Monster)
    public void setListMonster(List<Monster> lm){
        this.listMonster = lm;
    }
    public void addMonster(Monster m){
        (this.listMonster).add(m);
    }

    public void printMonsterPool(){
        for (Monster m : listMonster){
            System.out.printf("%d %s%n", m.getMonsterID(), m.getMonsterName());
        }
    }

    public void remove(Monster m){
        this.listMonster.remove(m);
    }

    public void printMonster(){
        int i = 1;
        for (Monster m : listMonster){
            System.out.printf("%d %s%n", i, m.getMonsterName());
            i++;
        }
    }

    public List<Monster> pickRandom(int i, MonsterPool listMonster){
        Random randomGenerator = new Random();
        List<Monster> copyMonsterPool = listMonster.getListMonster();
        List<Monster> copyList = new ArrayList<Monster>(copyMonsterPool);
        List<Monster> randomized = new ArrayList<Monster>();
        for (int j = 0; j < i; j++){
            int index = randomGenerator.nextInt(copyList.size());
            Monster monster = copyList.get(index);
            randomized.add(monster);
            copyList.remove(index);
        }
        return randomized;
    }
}