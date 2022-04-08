package com.monstersaku;

import java.util.List;

public class Monster {
    private int id;
    private String name;
    private List<ElementType> elementType;
    private Stats stats;
    private MovePool moves;
    private boolean conditioned;
    private boolean burn;
    private boolean poison;
    private boolean sleep;
    private boolean paralyze;
    private Move currentMove;
    private boolean isAlive;

    public Monster(){}
    
    public Monster(int id, String name, List<ElementType> elementType, Stats stats){
        this.id = id;
        this.name = name;
        this.elementType = elementType;
        this.stats = stats;
        this.conditioned = false;
        this.burn = false;
        this.poison = false;
        this.sleep = false;
        this.paralyze = false;
        this.isAlive = true;
    }

    public Monster(int id, String name, List<ElementType> elementType, Stats stats, MovePool moves){
        this.id = id;
        this.name = name;
        this.elementType = elementType;
        this.stats = stats;
        this.moves = moves;
        this.conditioned = false;
        this.burn = false;
        this.poison = false;
        this.sleep = false;
        this.paralyze = false;
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

    public boolean isBurn(){
        return this.burn;
    }

    public boolean isSleep(){
        return this.sleep;
    }

    public boolean isPoison(){
        return this.poison;
    }

    public boolean isParalyze(){
        return this.paralyze;
    }

    public MovePool getMoves(){
        return this.moves;
    }

    public Move getCurrentMove(){
        return this.currentMove;
    }

    public boolean getIsAlive(){
        return this.isAlive;
    }
    // setter
    public void addMonsterMove(Move m){
        (this.moves).addMove(m);
    }

    public void setMonsterMovePool(MovePool moves){
        this.moves = moves;
    }

    public void setCurrentMove(Move m){
        this.currentMove = m;
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
        for (int i = 0; i < moves.getListMove().size(); i++){
            if (i==x-1){
                m = (Move)moves.getListMove().get(i);
                this.currentMove = m;
                //this.getCurrentMove().moved();
                //System.out.printf("The remaining %s ammunition is %d%n", m.getMoveName(), this.getMoves().getListMove().get(i).getMoveAmmunition());
                break;
            }
        }
        return m;
    }

    public double burned(){
        if (this.conditioned && this.sleep){
            return 0.5;
        } else return 1;
    }

    public void takeDamage(int x){
        System.out.printf("Enemy %s's current HP is %d%n", this.name, this.stats.getHP());
        System.out.printf("Enemy %s takes %d damage%n", this.name, x);
        if (x < this.stats.getHP()){
            this.stats.setHP(this.stats.getHP() - x);
            System.out.printf("Enemy %s's remaining HP is %d%n", this.name, this.stats.getHP());
            System.out.println();
        }
        else {
            System.out.printf("Enemy %s's remaining HP is 0%n", this.name);
            System.out.printf("Enemy %s dead.%n", this.name);
            this.isAlive = false;
        }
    }

    public void printMove(){
        int i = 1;
        for (Move m : this.getMoves().getListMove()){
            System.out.printf("%d %s (x%d)%n", i, m.getMoveName(), m.getMoveAmmunition());
            i++;
        }
    }
}