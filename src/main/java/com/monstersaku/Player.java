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

    public MonsterPool getSwitchingOption(){
        return this.switchingOption;
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

    public void setAllMonster(MonsterPool mp){
        this.monsters = mp;
    }

    public void setCurrentMonster(Monster m){
        this.currentMonster = m;
    }

    public void setMonsterPool(int i, MonsterPool listMonster){
        try {
            System.out.println("Getting random monster for " + this.name + " ....");
            Thread.sleep(3000);
            List<Monster> copyMonsterPool = listMonster.getListMonster();
            List<Monster> copyMonsterPool2 = listMonster.getListMonster();
            List<Monster> copyList = new ArrayList<Monster>(copyMonsterPool);
            List<Monster> copyList2 = new ArrayList<Monster>(copyMonsterPool2);
            List<Monster> randomized = new ArrayList<Monster>();
            List<Monster> randomized2 = new ArrayList<Monster>();
            for (int j = 0; j < i; j++){
                int index = new Random().nextInt(copyList.size());
                Monster monster = copyList.get(index);
                Monster monster2 = copyList2.get(index);
                randomized.add(monster);
                randomized2.add(monster2);
                copyList.remove(index);
                copyList2.remove(index);
            }
            MonsterPool mp = new MonsterPool();
            MonsterPool mp2 = new MonsterPool();
            mp.setListMonster(randomized);
            mp2.setListMonster(randomized2);
            this.monsters = mp;
            this.switchingOption = mp2;
        } catch (Exception e) {
            System.out.println("Main interrupted");
        }
        System.out.println();
    }

    // print player monster pool
    public void randomMonster(){
        try {
            System.out.println(this.name + " choosing monster....");
            Thread.sleep(1000);
            int index = new Random().nextInt(monsters.getSize());
            currentMonster = (monsters.getListMonster()).get(index);
            this.switchOption();
        } catch(InterruptedException e) {
            System.out.println("Main interrupted");
        }
    }

    public void switchOption(){
        MonsterPool mp = monsters;
        List<Monster> lm = switchingOption.getListMonster();
        lm.remove(this.getCurrentMonster());
        mp.setListMonster(lm);
        this.switchingOption = mp;
    }

    public void getSwitchMonster(int x){
        Monster m = new Monster();
        for (int i = 1; i <= switchingOption.getSize(); i++){
            if (i==x){
                m = (switchingOption.getListMonster()).get(i-1);
                break;
            }
        }
        this.switchingOption.addMonster(this.getCurrentMonster());
        System.out.printf("Successfully changed monster to %s%n", m.getMonsterName());
        this.currentMonster = m;
        this.switchingOption.getListMonster().remove(m);
        System.out.println();
    }

    public void getMonster(int x){
        Monster m = new Monster();
        for (int i = 1; i <= monsters.getSize(); i++){
            if (i==x){
                m = (monsters.getListMonster()).get(i-1);
            }
        }
        System.out.printf("%s sent out %s!%n", this.name, m.getMonsterName());
        this.currentMonster = m;
        this.switchingOption.getListMonster().remove(m);
        System.out.println();
    }

    public void printCurrentMonster(){
        System.out.printf("%s is currently using %s%n", this.getPlayerName(), this.getCurrentMonster().getMonsterName());
    }
}