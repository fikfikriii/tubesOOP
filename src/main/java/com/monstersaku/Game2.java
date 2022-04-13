package com.monstersaku;
import com.monstersaku.util.CSVReader;

import java.lang.System;
import java.util.Scanner;
import java.io.File;
//import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
//import java.util.ListIterator;
import java.util.ArrayList;
import java.lang.Integer;
import java.lang.Thread;

public class Game2 {
    private static final List<String> CSV_FILE_PATHS = Collections.unmodifiableList(Arrays.asList(
            "configs/movepool.csv",
            "configs/element-type-effectivity-chart.csv",
            "configs/monsterpool.csv"));

    public static boolean loop = true;

    Player player1 = new Player();
    Player player2 = new Player();
    MonsterPool mnstrPool = new MonsterPool();
    MovePool mvPool = new MovePool();
    MovePool mvPool2 = new MovePool();
    EffectivityPool ePool = new EffectivityPool();
    
    MonsterPool player1MonsterPool;
    MonsterPool player2MonsterPool;

    Scanner scanner = new Scanner(System.in);

    public Game2(){}

    public void playGame(){
        inputPlayer();
        System.out.println();
        
        readConfig();

        // get 6 random monster for each player
        player1.setMonsterPool(6, mnstrPool);
        player2.setMonsterPool(6, mnstrPool);
        System.out.printf("%s's monsters list: %n", player1.getPlayerName());
        player1.getMonsterPool().printMonster();
        System.out.println();
        System.out.printf("%s's monsters list: %n", player2.getPlayerName());
        player2.getMonsterPool().printMonster();

        // initialize
        initializeBattle();

        // get current monster
        player1.randomMonster();
        player1.printCurrentMonster();
        player2.randomMonster();
        player2.printCurrentMonster();
        
        System.out.println();
        boolean loop = true;
        while (loop){
            turnGame();
        }

    }

    public void readConfig(){
        for (String fileName : CSV_FILE_PATHS) {
            try {
                CSVReader reader = new CSVReader(new File(BackupMain.class.getResource(fileName).toURI()), ";");
                reader.setSkipHeader(true);
                List<String[]> lines = reader.read();
                for (String[] line : lines) {
                    List<String> ls = new ArrayList<String>();
                    for (String word : line) {
                        ls.add(word);
                    }

                    if(fileName.equals("configs/movepool.csv")){
                        // ini buat id
                        Integer id = Integer.parseInt(ls.get(0));
                        
                        // ini buat type
                        String tipe = ls.get(1);

                        // ini buat nama
                        String name = ls.get(2);

                        // ini buat elementType
                        String se = ls.get(3);
                        ElementType elementType = ElementType.valueOf(se);
                        

                        // ini buat acc, prior, amun
                        Integer accuracy = Integer.parseInt(ls.get(4));
                        Integer priority = Integer.parseInt(ls.get(5));
                        Integer ammunition = Integer.parseInt(ls.get(6));

                        String target = ls.get(7);

                        if (tipe.equals("NORMAL")){
                            Integer basepower = Integer.parseInt(ls.get(8));
                            NormalMove n = new NormalMove(id, tipe, name, elementType, accuracy, priority, ammunition, target, basepower);
                            mvPool.addMove(n);
                        } else if (tipe.equals("SPECIAL")){
                            Integer basepower = Integer.parseInt(ls.get(8));
                            SpecialMove s = new SpecialMove(id, tipe, name, elementType, accuracy, priority, ammunition, target, basepower);
                            mvPool.addMove(s);
                        } else if (tipe.equals("STATUS")){
                            String efek = ls.get(8);
                            String num = ls.get(9);
                            String[] numEl = num.split(",");
                            List<String> listNum = Arrays.asList(numEl);
                            Integer HP = Integer.parseInt(listNum.get(0));
                            Integer att = Integer.parseInt(listNum.get(1));
                            Integer def = Integer.parseInt(listNum.get(2));
                            Integer spAtt = Integer.parseInt(listNum.get(3));
                            Integer spDef = Integer.parseInt(listNum.get(4));
                            Integer speed = Integer.parseInt(listNum.get(5));
                            StatusMove st = new StatusMove(id, tipe, name, elementType, accuracy, priority, ammunition, target, efek, HP, att, def, spAtt, spDef, speed);
                            mvPool.addMove(st);
                        }

                    }

                    else if (fileName.equals("configs/element-type-effectivity-chart.csv")){
                        String s1 = ls.get(0);
                        ElementType source = ElementType.valueOf(s1);
                        
                        String s2 = ls.get(1);
                        ElementType target = ElementType.valueOf(s2);

                        Double effectivity = Double.parseDouble(ls.get(2));
                        Effectivity e = new Effectivity(source, target, effectivity);
                        ePool.addEffectivity(e);
                    }
                    
                    else if (fileName.equals("configs/monsterpool.csv")){
                        // ini buat id
                        Integer id = Integer.parseInt(ls.get(0));

                        // ini buat name
                        String name = ls.get(1);

                        // ini buat elementTypes
                        String types = ls.get(2);
                        String[] elements = types.split(",");
                        List<String> listTypes = Arrays.asList(elements);
                        List<ElementType> elementTypes = new ArrayList<ElementType>();
                        for(int j = 0; j < listTypes.size(); j++){
                            String s = listTypes.get(j);
                            ElementType e = ElementType.valueOf(s);
                            elementTypes.add(e);
                        }
                        
                        // ini buat stats
                        String num = ls.get(3);
                        String[] numEl = num.split(",");
                        List<String> listNum = Arrays.asList(numEl);

                        Integer HP = Integer.parseInt(listNum.get(0));
                        Integer att = Integer.parseInt(listNum.get(1));
                        Integer def = Integer.parseInt(listNum.get(2));
                        Integer spAtt = Integer.parseInt(listNum.get(3));
                        Integer spDef = Integer.parseInt(listNum.get(4));
                        Integer speed = Integer.parseInt(listNum.get(5));
                        Stats stats = new Stats(HP, att, def, spAtt, spDef, speed); // berhasil inisiasi stats

                        // ini buat moves
                        reader.setSkipHeader(true);
                        List<String[]> lines2 = reader.read();
                        for (String[] line2 : lines2) {
                            List<String> ls2 = new ArrayList<String>();
                            for (String word : line2) {
                                ls2.add(word);
                            }
                        }
                        Integer id2 = Integer.parseInt(ls.get(0));
                        
                        // ini buat type
                        String tipe = ls.get(1);

                        // ini buat nama
                        String name2 = ls.get(2);

                        // ini buat elementType
                        String se = ls.get(3);
                        ElementType elementType = ElementType.valueOf(se);
                        

                        // ini buat acc, prior, amun
                        Integer accuracy2 = Integer.parseInt(ls.get(4));
                        Integer priority2 = Integer.parseInt(ls.get(5));
                        Integer ammunition2 = Integer.parseInt(ls.get(6));

                        String target = ls.get(7);

                        if (tipe.equals("NORMAL")){
                            Integer basepower = Integer.parseInt(ls.get(8));
                            NormalMove n = new NormalMove(id2, tipe, name2, elementType, accuracy2, priority2, ammunition2, target, basepower);
                            mvPool2.addMove(n);
                        } else if (tipe.equals("SPECIAL")){
                            Integer basepower = Integer.parseInt(ls.get(8));
                            SpecialMove s = new SpecialMove(id2, tipe, name2, elementType, accuracy2, priority2, ammunition2, target, basepower);
                            mvPool2.addMove(s);
                        } else if (tipe.equals("STATUS")){
                            String efek = ls.get(8);
                            String num2 = ls.get(9);
                            String[] numEl2 = num2.split(",");
                            List<String> listNum2 = Arrays.asList(numEl2);
                            Integer HP2 = Integer.parseInt(listNum2.get(0));
                            Integer att2 = Integer.parseInt(listNum2.get(1));
                            Integer def2 = Integer.parseInt(listNum2.get(2));
                            Integer spAtt2 = Integer.parseInt(listNum2.get(3));
                            Integer spDef2 = Integer.parseInt(listNum2.get(4));
                            Integer speed2 = Integer.parseInt(listNum2.get(5));
                            StatusMove st = new StatusMove(id2, tipe, name2, elementType, accuracy2, priority2, ammunition2, target, efek, HP2, att2, def2, spAtt2, spDef2, speed2);
                            mvPool2.addMove(st);
                        }

                        String monsterMove = ls.get(4);
                        String[] monsterMoveEl = monsterMove.split(",");
                        List<String> listMonsterMove = Arrays.asList(monsterMoveEl);
                        MovePool monsterMovePool = new MovePool();
                        for (Move m : mvPool.getListMove()){
                            for (String moveID : listMonsterMove){
                                if (m.getMoveID() == Integer.valueOf(moveID)){
                                    monsterMovePool.addMove(m);
                                }
                            }
                        }
                        
                        // instansiasi
                        Monster m = new Monster(id, name, elementTypes, stats);
                        m.setMonsterMovePool(monsterMovePool);
                        mnstrPool.addMonster(m);
                    }

                }

            } catch (Exception e) {
                // do nothing
            }
        }
    }

    public void inputPlayer(){
        System.out.print("Input Player 1's name: ");
        String p1Name = scanner.next();
        System.out.print("Input Player 2's name: ");
        String p2Name = scanner.next();
        player1.setPlayerID(1);
        player1.setPlayerName(p1Name);
        player2.setPlayerID(2);
        player2.setPlayerName(p2Name);
    }

    public void initializeBattle(){
        System.out.println();
        System.out.printf("Initializing Battle.");
        try {
            for(int n = 0; n< 10; n++) {
             Thread.sleep(200);
             System.out.printf(".");
            }
        } catch(InterruptedException e) {
            System.out.println("Main interrupted");
        }
        System.out.println();
        System.out.println();
    }

    public void wrongInput(){
        System.out.println("Invalid command. Please read the instruction!");
    }

    public void showGameMenu(){
        System.out.println("GAME SCREEN INSTRUCTIONS: ");
        System.out.println("[Switch] = Switch monster");
        System.out.println("[Move] = Launch attack");
        System.out.println("[MonsterInfo] = Displaying monsters information");
        System.out.println("[GameInfo] = Displaying game information (in game monster, turn)");
        System.out.println();
    }

    public void switchMonster(Player p){
        p.printCurrentMonster();
        System.out.printf("Monsters option for %s:%n", p.getPlayerName());
        p.switchOption();
        System.out.printf("Enter monster selection [in integer]: ");
        int x = scanner.nextInt();
        p.getSwitchMonster(x);
    }

    public void turnGame(){
        showGameMenu();
        System.out.printf("Enter player 1's command: ");
        String command1 = scanner.next();
        System.out.printf("Enter player 2's command: ");
        String command2 = scanner.next();

        if (command1.equalsIgnoreCase("Switch")){
            switchMonster(player1);
            if (command2.equalsIgnoreCase("Switch")){
                switchMonster(player2);
            }
            else if (command2.equalsIgnoreCase("Move")){
                if (player2.getCurrentMonster().conditioned() && player2.getCurrentMonster().isSleep()){
                    System.out.printf("%s's %s cannot move!%n", player2.getPlayerName(), player2.getCurrentMonster().getMonsterName());
                } else {
                    moveOption(player2);
                    move(player2, player1);
                }
            }
            else {
                wrongInput();
            }
        }

        else if (command1.equalsIgnoreCase("Move")){
            if (command2.equalsIgnoreCase("Switch")){
                switchMonster(player2);
                if (player1.getCurrentMonster().isSleep()){
                    System.out.printf("%s's %s cannot move!%n", player1.getPlayerName(), player1.getCurrentMonster().getMonsterName());
                } else {
                    moveOption(player1);
                    move(player1, player2);
                }
            }
            else if (command2.equalsIgnoreCase("Move")){
                if (player1.getCurrentMonster().isSleep() && player2.getCurrentMonster().isSleep()){
                    System.out.printf("%s's %s cannot move!%n", player1.getPlayerName(), player1.getCurrentMonster().getMonsterName());
                    System.out.printf("%s's %s cannot move!%n", player2.getPlayerName(), player2.getCurrentMonster().getMonsterName());
                }
                else if (player1.getCurrentMonster().isSleep() && !player2.getCurrentMonster().isSleep()){
                    System.out.printf("%s's %s cannot move!%n", player1.getPlayerName(), player1.getCurrentMonster().getMonsterName());
                    moveOption(player2);
                    move(player2, player1);
                }
                else if (!player1.getCurrentMonster().isSleep() && player2.getCurrentMonster().isSleep()){
                    System.out.printf("%s's %s cannot move!%n", player2.getPlayerName(), player2.getCurrentMonster().getMonsterName());
                    moveOption(player1);
                    move(player1, player2);
                }
                else {
                    moveOption(player1);
                    moveOption(player2);
                    if (compareMonsterMove(player1.getCurrentMonster(), player2.getCurrentMonster()) == 1){
                        move(player1, player2);
                        move(player2, player1);
                    } else {
                        move(player2, player1);
                        move(player1, player2);
                    }
                }
            }

            else {
                wrongInput();
            }
        }

        else {
            wrongInput();
        }
    }

    public void move(Player self, Player enemy){
        System.out.printf("%s's %s uses %s%n", self.getPlayerName(), self.getCurrentMonster().getMonsterName(), self.getCurrentMonster().getCurrentMove().getMoveName());
        int x = self.getCurrentMonster().getCurrentMove().totalDamage(self.getCurrentMonster(), enemy.getCurrentMonster(), ePool);
        enemy.getCurrentMonster().takeDamage(x);
        System.out.println();
    }

    public void moveOption(Player p){
        System.out.printf("Moves option for %s's %s:%n", p.getPlayerName(), p.getCurrentMonster().getMonsterName());
        p.getCurrentMonster().printMove();;
        System.out.printf("Enter move selection [in integer]: ");
        int x = scanner.nextInt();
        Move m = p.getCurrentMonster().getMoveMonster(x);
        p.getCurrentMonster().setCurrentMove(m);
        System.out.println();
        // Move m = p.getCurrentMonster().getMoveMonster(x);
        // return m
        // p.getCurrentMonster().setCurrentMove(m);
        // System.out.printf("%s's %s uses %s%n", p.getPlayerName(), p.getCurrentMonster().getMonsterName(), m.getMoveName());
    }

    public int compareMonsterMove(Monster m1, Monster m2){
        if (m1.getCurrentMove().getMovePriority() > m2.getCurrentMove().getMovePriority()){
            return 1;
        } else if (m1.getCurrentMove().getMovePriority() < m2.getCurrentMove().getMovePriority()){
            return 2;
        } else {
            if (m1.getStats().getSpeed() >= m2.getStats().getSpeed()){
                return 1;
            } else{
                return 2;
            }
        }
    }
    
    //gini kah? :(
    
    // public int damageNormal(NormalMove move, Monster self, Monster enemy) {
    //     double random = 0.85 + Math.random() * (0.15);
    //     double damage = (move.getBasePower() * (self.getAttack() / enemy.getDefense()) + 2) * random * getEffectivity(self, enemy) * self.IsBurn();
    //     return (int) damage;
    // }

    // public int damageSpecial(SpecialMove move, Monster self, Monster enemy) {
    //     double random = 0.85 + Math.random() * (0.15);
    //     double damage = (move.getBasePower() * (self.getSpAttack() / enemy.getSpDefense()) + 2) * random * getEffectivity(self, enemy) * self.IsBurn();
    //     return (int) damage;
    // }
    
    // public int damageDefault(Monster self, Monster enemy) {
    //     double random = 0.85 + Math.random() * (0.15);
    //     double damage = (50 * (self.getAttack() / enemy.getDefense()) + 2) * random * getEffectivity(self, enemy) * self.IsBurn();
    //     return (int) damage;
    // }

    // public int afterDamage(Monster monster) {

    // }

    

}