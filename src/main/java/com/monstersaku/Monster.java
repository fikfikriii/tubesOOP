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
    private int sleepLength;

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
        this.sleepLength = 0;
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
            System.out.println();
            this.isAlive = false;
        }
    }

    public void setCondition(StatusMove move){
        if (this.conditioned){
            System.out.printf("%s is already conditioned.%n", this.name);
        } else {
            switch (move.getStatusEffect()) {
                case "BURN": 
                    System.out.printf("%s was burned.%n", this.name);
                    this.burn = true;
                    this.conditioned = true;
                    break;
                case "POISON": 
                    System.out.printf("%s was poisoned.%n", this.name);
                    this.poison = true;
                    this.poison = true;
                    break;
                case "PARALYZE":
                    System.out.printf("%s was paralyzed.%n", this.name);
                    this.paralyze = true;
                    this.conditioned = true;
                    break;
                case "SLEEP":
                    double test = Math.random() * 7;
                    int sleepLength = (int) test;
                    this.sleepLength = sleepLength;
                    System.out.printf("%s was slept for %d turn.%n", this.name, this.sleepLength);
                    this.sleep = true;
                    this.conditioned = true;
                default:
                    break;
            }
        }
    }

    public void printMove(){
        int i = 1;
        for (Move m : this.getMoves().getListMove()){
            System.out.printf("%d %s (x%d)%n", i, m.getMoveName(), m.getMoveAmmunition());
            i++;
        }
    }

    public void printInfo(){
        System.out.println("--- STATS ---");
        System.out.println("Max HP: " + this.getStats().getMaxHP());
        System.out.println("HP: " + this.getStats().getHP());
        System.out.println("Attack Power: " + this.getStats().getAtt());
        System.out.println("Special Attack Power: " + this.getStats().getSpAtt());
        System.out.println("Defence Power: " + this.getStats().getDef());
        System.out.println("Special Defence Power: " + this.getStats().getSpDef());
        System.out.println("Speed: " + this.getStats().getSpeed());
        System.out.println("--- MOVES ---");
        this.printMove();  
    }
}