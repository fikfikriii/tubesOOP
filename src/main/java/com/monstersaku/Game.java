package com.monstersaku;
import com.monstersaku.util.CSVReader;

import java.lang.System;
import java.util.Scanner;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.lang.Integer;
import java.lang.Thread;

public class Game {
    private static final List<String> CSV_FILE_PATHS = Collections.unmodifiableList(Arrays.asList(
            "configs/movepool.csv",
            "configs/element-type-effectivity-chart.csv",
            "configs/monsterpool.csv"));

    public static boolean loop = true;

    Player player1 = new Player();
    Player player2 = new Player();
    MonsterPool mnstrPool = new MonsterPool();
    MovePool mvPool = new MovePool();
    EffectivityPool ePool = new EffectivityPool();
    
    MonsterPool player1MonsterPool;
    MonsterPool player2MonsterPool;

    Scanner scanner = new Scanner(System.in);

    public Game(){}

    public void playGame(){
        inputPlayer();
        System.out.println();
        
        readConfig();

        // get 6 random monster for each player
        player1.setMonsterPool(6, mnstrPool);
        player2.setMonsterPool(6, mnstrPool);
        System.out.printf("List monster %s: %n", player1.getPlayerName());
        player1.getMonsterPool().printMonster();
        System.out.println();
        System.out.printf("List monster %s: %n", player2.getPlayerName());
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
                            n.print();
                            mvPool.addMove(n);
                        } else if (tipe.equals("SPECIAL")){
                            Integer basepower = Integer.parseInt(ls.get(8));
                            SpecialMove s = new SpecialMove(id, tipe, name, elementType, accuracy, priority, ammunition, target, basepower);
                            s.print();
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
                            st.print();

                            mvPool.addMove(st);
                        }

                    }

                    else if (fileName.equals("configs/element-type-effectivity-chart.csv")){
                        String s1 = ls.get(0);
                        ElementType source = ElementType.valueOf(s1);
                        
                        String s2 = ls.get(1);
                        ElementType target = ElementType.valueOf(s2);

                        Float effectivity = Float.parseFloat(ls.get(2));
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
                        MovePool monsterMovePool = new MovePool();
                        String monsterMove = ls.get(4);
                        String[] monsterMoveEl = monsterMove.split(",");
                        List<String> listMonsterMove = Arrays.asList(monsterMoveEl);
                        for (Move m : mvPool.getListMove()){
                            for (String moveID : listMonsterMove){
                                if (m.getMoveID() == Integer.valueOf(moveID)){
                                    monsterMovePool.addMove(m);
                                }
                            }
                        }

                        // instansiasi
                        Monster m = new Monster(id, name, elementTypes, stats, monsterMovePool);
                        mnstrPool.addMonster(m);
                    }

                }

            } catch (Exception e) {
                // do nothing
            }
        }
    }

    public void inputPlayer(){
        System.out.print("Masukan nama player1: ");
        String p1Name = scanner.next();
        System.out.print("Masukan nama player2: ");
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
            System.out.println("main interrupted");
        }
        System.out.println();
        System.out.println();
    }

    public void wrongInput(){
        System.out.println("Perintah tidak valid. Perhatikan instruksi.");
    }

    public void showGameMenu(){
        System.out.println("GAME SCREEN INSTRUCTIONS: ");
        System.out.println("[Switch] = Mengganti monster");
        System.out.println("[Move] = Melancarkan serangan");
        System.out.println("[MonsterInfo] = Menampilkan informasi monster-monster saku");
        System.out.println("[GameInfo] = Menampilkan informasi permainan (in game monster, turn)");
        System.out.println();
    }

    public void switchMonster(Player p){
        p.printCurrentMonster();
        System.out.printf("Pilihan monsters bagi %s:%n", p.getPlayerName());
        p.switchOption();
        System.out.printf("Masukan pilihan [dalam integer]: ");
        int x = scanner.nextInt();
        p.getSwitchMonster(x);
    }

    public void turnGame(){
        showGameMenu();
        System.out.printf("Masukan command player 1: ");
        String command1 = scanner.next();
        System.out.printf("Masukan command player 2: ");
        String command2 = scanner.next();

        if (command1.equalsIgnoreCase("Switch")){
            switchMonster(player1);
            if (command2.equalsIgnoreCase("Switch")){
                switchMonster(player2);
            }
            else if (command2.equalsIgnoreCase("Move")){
                // check dulu monster player 2 sleep atau engga
                // if sleep then
                // System.out.printf("%s cannot move%n", player2.getCurrentMonster().getMonsterName());
                // if not sleep then
                move(player2);
            }
            else {
                wrongInput();
            }
        }

        else if (command1.equalsIgnoreCase("Move")){
            if (command2.equalsIgnoreCase("Switch")){
                switchMonster(player2);
                // player 1 nyerang
                // check dulu monster player 1 sleep atau engga
                // if sleep then
                // System.out.printf("%s cannot move%n", player1.getCurrentMonster().getMonsterName());
                // if not sleep then
                move(player1);
            }
            else if (command2.equalsIgnoreCase("Move")){
                if (compareMonsterMove(player1.getCurrentMonster(), player2.getCurrentMonster()) == 1){
                    // player 1 nyerang
                    // check dulu monster player 1 sleep atau engga
                    // if sleep then
                    // System.out.printf("%s cannot move%n", player1.getCurrentMonster().getMonsterName());
                    // if not sleep then
                    move(player1);

                    // check dulu monster player 2 sleep atau engga
                    // if sleep then
                    // System.out.printf("%s cannot move%n", player2.getCurrentMonster().getMonsterName());
                    // if not sleep then
                    move(player2);
                } else {
                    // player 2 nyerang
                    // check dulu monster player 2 sleep atau engga
                    // if sleep then
                    // System.out.printf("%s cannot move%n", player2.getCurrentMonster().getMonsterName());
                    // if not sleep then
                    move(player2);

                    // check dulu monster player 1 sleep atau engga
                    // if sleep then
                    // System.out.printf("%s cannot move%n", player1.getCurrentMonster().getMonsterName());
                    // if not sleep then
                    move(player1);
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

    public void move(Player p){
        System.out.printf("Pilihan move bagi %s:%n", p.getCurrentMonster().getMonsterName());
        p.getCurrentMonster().getMoves().printMove();
        System.out.printf("Masukan pilihan move [dalam integer]: ");
        int x = scanner.nextInt();
        Move m = p.getCurrentMonster().getMoveMonster(x);
        System.out.printf("%s menggunakan %s%n", p.getCurrentMonster().getMonsterName(), m.getMoveName());
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