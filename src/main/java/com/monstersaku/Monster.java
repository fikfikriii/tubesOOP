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
    private Move currentMove;

    public Monster(){}
    
    public Monster(int id, String name, List<ElementType> elementType, Stats stats, MovePool moves){
        this.id = id;
        this.name = name;
        this.elementType = elementType;
        this.stats = stats;
        this.moves = moves;
        this.conditioned = false;
    }
    
    // getter
    public int getMonsterID(){
        return this.id;
    }

    public String getMonsterName(){
        return this.name;
    }

    public Stats getStats(){
        return this.stats;
    }

    public List<ElementType> elementType(){
        return this.elementType;
    }
    public boolean conditioned(){
        return this.conditioned;
    }

    public String condition(){
        return this.condition;
    }

    public MovePool getMoves(){
        return this.moves;
    }

    public Move getCurrentMove(){
        return this.currentMove;
    }

    // setter
    public void addMonsterMove(Move m){
        (this.moves).addMove(m);
    }

    // other methods
    public void printMonsterMove(){
        moves.printMovePool();
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

    public float isBurn(){
        if (this.conditioned && this.condition.equalsIgnoreCase("burn")){
            return 0.5f;
        } else return 1;
    }
}