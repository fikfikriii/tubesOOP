package com.monstersaku;

import java.util.List;
import java.util.Random;

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

    public int afterDamage() {
        double damage = 0;
        if (this.isBurn()) {
            damage = this.getStats().getMaxHP() * 1/8;
        } else if (this.isPoison()) {
            damage = this.getStats().getMaxHP() * 1/16;
        }
        return (int) damage;
    }

    public void moveStatus(){
        System.out.println();
        
        StatusMove s = (StatusMove) this.getCurrentMove();
        
        System.out.printf("~~~ Stats change for enemy's %s ~~~%n", this.name);

        // HP
        System.out.printf("HP before move: %d%n", this.getStats().getHP());
        int hp = (int)(s.getHPEff() * this.getStats().getMaxHP() / 100);
        if (this.getStats().getHP() + hp < this.getStats().getMaxHP()){
            this.getStats().setHP(hp + this.getStats().getHP());
        } else {
            this.getStats().setHP(this.getStats().getMaxHP());
        }
        System.out.printf("HP after move: %d%n", this.getStats().getHP());
        
        // Att
        System.out.printf("Attack before move: %d%n", this.getStats().getAtt());
        int att = (int)(this.getStats().getAtt() * s.getFactor(s.getAttEff()));
        this.getStats().setAtt(att);
        System.out.printf("Attack after move: %d%n", this.getStats().getAtt());
        
        // Def
        System.out.printf("Defense before move: %d%n", this.getStats().getDef());
        int def = (int)(this.getStats().getDef() * s.getFactor(s.getDefEff()));
        this.getStats().setDef(def);
        System.out.printf("Defense after move: %d%n", this.getStats().getDef());
        
        // Sp Att
        System.out.printf("Special attack before move: %d%n", this.getStats().getSpAtt());
        int spAtt = (int)(this.getStats().getSpAtt() * s.getFactor(s.getSpAttEff()));
        this.getStats().setSpAtt(spAtt);
        System.out.printf("Special attack after move: %d%n", this.getStats().getSpAtt());
        
        // Sp Def
        System.out.printf("Special defence before move: %d%n", this.getStats().getSpDef());
        int spDef = (int)(this.getStats().getSpDef() * s.getFactor(s.getSpDefEff()));
        this.getStats().setSpDef(spDef);
        System.out.printf("Special defence after move: %d%n", this.getStats().getSpDef());
       
    }

    public void setCondition(StatusMove move){
        if (this.conditioned){
            System.out.printf("%s is already conditioned.%n", this.name);
            System.out.println();
        } else {
            switch (move.getStatusEffect()) {
                case "BURN": 
                    System.out.printf("Enemy %s was burned.%n", this.name);
                    this.burn = true;
                    this.conditioned = true;
                    break;
                case "POISON": 
                    System.out.printf("Enemy %s was poisoned.%n", this.name);
                    this.poison = true;
                    this.conditioned = true;
                    break;
                case "PARALYZE":
                    System.out.printf("Enemy %s was paralyzed.%n", this.name);
                    System.out.printf("Speed before paralyzed: %d%n", this.getStats().getSpeed());
                    this.getStats().setSpeed((int)this.getStats().getSpeed()/2);
                    System.out.printf("Speed after paralyzed: %d%n", this.getStats().getSpeed());
                    int val = new Random().nextInt(4);
                    if (val == 0){
                        System.out.printf("Enemy %s cannot move for 1 turn%n ", this.name);
                        this.sleep = true;
                        this.sleepLength = 1;
                    }
                    this.paralyze = true;
                    this.conditioned = true;
                    break;
                case "SLEEP":
                    double test = Math.random() * 7;
                    int sleepLength = (int) test;
                    this.sleepLength = sleepLength;
                    System.out.printf("Enemy %s was slept for %d turn.%n", this.name, this.sleepLength);
                    this.sleep = true;
                    this.conditioned = true;
                default:
                    break;
            }
            System.out.println();
        }
    }

    public void printMove(){
        int i = 1;
        for (Move m : this.getMoves().getListMove()){
            System.out.printf("%d %s (x%d)%n", i, m.getMoveName(), m.getMoveAmmunition());
            i++;
        }
    }

    public void removeMove(Move m){
        this.getMoves().getListMove().remove(m);
    }

    public void aftersleep(){
        this.sleepLength -= 1;
        System.out.printf("The remaining sleep length for %s is %d turn(s) %n", this.name, this.sleepLength);
        if (this.sleepLength == 0){
            this.sleep = false;
            this.conditioned = false;
        }
        System.out.println();
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