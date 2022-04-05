package com.monstersaku;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.lang.Thread;

public class Player {
    private int id;
    private String name;
    private MonsterPool monsters;
    private MonsterPool switchingOption;
    private Monster currentMonster;

    public Player(){}

    public Player(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    // getter
    public int getPlayerID(){
        return this.id;
    }
    
    public MonsterPool getMonsterPool(){
        return this.monsters;
    }

    public Monster getCurrentMonster(){
        return this.currentMonster;
    }

    public String getPlayerName(){
        return this.name;
    }

    // setter
    public void setPlayerID(int id){
        this.id = id;
    }

    public void setPlayerName(String name){
        this.name = name;
    }

    public void setMonsterPool(int i, MonsterPool listMonster){
        try {
            System.out.println("Getting random monster for " + this.name + " ....");
            Thread.sleep(3000);
            List<Monster> copyMonsterPool = listMonster.getListMonster();
            List<Monster> copyList = new ArrayList<Monster>(copyMonsterPool);
            List<Monster> randomized = new ArrayList<Monster>();
            for (int j = 0; j < i; j++){
                int index = new Random().nextInt(copyList.size());
                Monster monster = copyList.get(index);
                randomized.add(monster);
                copyList.remove(index);
            }
            MonsterPool mp = new MonsterPool();
            mp.setListMonster(randomized);
            this.monsters = mp;
        } catch (Exception e) {
            System.out.println("Main interrupted");
        }
        System.out.println();
    }

    // print player monster pool

    public void randomMonster(){
        try {
            System.out.println(this.name + " choosing monster....");
            Thread.sleep(1500);
            int index = new Random().nextInt(monsters.getSize());
            currentMonster = (monsters.getListMonster()).get(index);
        } catch(InterruptedException e) {
            System.out.println("Main interrupted");
        }
        
    }

    public void switchOption(){
        MonsterPool mp = monsters;
        List<Monster> lm = monsters.getListMonster();
        lm.remove(this.getCurrentMonster());
        mp.setListMonster(lm);
        this.switchingOption = mp;
        mp.printMonster();
    }

    public void getSwitchMonster(int x){
        Monster m = new Monster();
        for (int i = 1; i <= switchingOption.getSize(); i++){
            if (i==x){
                m = (switchingOption.getListMonster()).get(i-1);
            }
        }
        this.switchingOption.addMonster(this.getCurrentMonster());
        System.out.printf("Berhasil mengganti monster menjadi %s%n", m.getMonsterName());
        this.currentMonster = m;
        System.out.println();
    }

    public void printCurrentMonster(){
        System.out.printf("Saat ini %s sedang menggunakan %s%n", this.getPlayerName(), this.getCurrentMonster().getMonsterName());
        System.out.println();
    }
}