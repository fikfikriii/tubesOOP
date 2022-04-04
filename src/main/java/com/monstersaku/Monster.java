package com.monstersaku;

import java.util.List;

public class Monster {
    private int id;
    private String name;
    private List<ElementType> elementType;
    private Stats stats;
    private MovePool moves;
    private boolean conditioned;
    private String condition;
    private boolean isAlive;

    public Monster(){}
    
    public Monster(int id, String name, List<ElementType> elementType, Stats stats, MovePool moves){
        this.id = id;
        this.name = name;
        this.elementType = elementType;
        this.stats = stats;
        this.moves = moves;
        this.conditioned = false;
        this.isAlive = true;
    }
    
    public MovePool getMoves(){
        return this.moves;
    }

    public void addMonsterMove(Move m){
        (this.moves).addMove(m);
    }

    public void printMonsterMove(){
        moves.printMovePool();
    }

    public int getMonsterID(){
        return this.id;
    }

    public String getMonsterName(){
        return this.name;
    }

    public void printMonsterName(){
        System.out.println("Monster Name: " + this.name);
    }

    public Move getMoveMonster(int x){
        Move m = new Move();
        for (int i = 0; i <= moves.getListMove().size(); i++){
            if (i==x-1){
                m = (Move)moves.getListMove().get(i);
                m.moved();
                break;
            }
        }
        return m;
    }
}