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

public class Game implements Instruction{
    private static final List<String> CSV_FILE_PATHS = Collections.unmodifiableList(Arrays.asList(
            "configs/movepool.csv",
            "configs/element-type-effectivity-chart.csv",
            "configs/monsterpool.csv"));

    int turn = 0;
    boolean loop = true;

    Player player1 = new Player();
    Player player2 = new Player();

    Player winner = new Player();
    Player loser = new Player();

    MonsterPool mnstrPool = new MonsterPool();
    MonsterPool mnstrPool2 = new MonsterPool();

    List<List<String>> addresses = new ArrayList<List<String>>();

    MovePool mvPool = new MovePool();
    MovePool mvPool2 = new MovePool();
    MovePool mvPool3 = new MovePool();
    MovePool mvPool4 = new MovePool();

    EffectivityPool ePool = new EffectivityPool();

    Scanner scanner = new Scanner(System.in);

    public Game(){}

    public void playGame(){
        inputPlayer();
        System.out.println();
        
        readConfig();
        
        // get 6 random monster for each player
        player1.setMonsterPool(6, mnstrPool);
        player2.setMonsterPool(6, mnstrPool2);

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
        
        while (loop){
            turnGame();
            turn += 1;
            if (player1.getMonsterPool().getListMonster().isEmpty() && player1.getCurrentMonster() == null){
                winner = player2;
                loser = player1;
                printWinner(winner, loser);
                loop = false;
                System.out.println();
                System.out.println("~~~ Returning to main screen ~~~");
                System.out.println();
            } else if (player2.getMonsterPool().getListMonster().isEmpty() && player2.getCurrentMonster() == null){
                winner = player1;
                loser = player2;
                printWinner(winner, loser);
                loop = false;
                System.out.println();
                System.out.println("~~~ Returning to main screen ~~~");
                System.out.println();
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

    public void wrongInputCommand(){
        System.out.println();
        System.out.println("Invalid command. Please read the instruction!");
    }

    public void showHelp(){
        System.out.println();
        System.out.println("GAME SCREEN INSTRUCTIONS: ");
        System.out.println("[Switch] = Switch monster");
        System.out.println("[Move] = Launch attack");
        System.out.println("[MonsterInfo] = Displaying monsters information");
        System.out.println("[GameInfo] = Displaying game information (game information)");
        System.out.println("[Exit] = Exit the game. Returning to main screen");
        System.out.println();
    }

    public void switchMonster(Player p){
        System.out.println();
        p.printCurrentMonster();
        System.out.printf("Monsters option for %s:%n", p.getPlayerName());
        p.getSwitchingOption().printMonster();
        System.out.printf("Enter monster selection [in integer]: ");
        int x = scanner.nextInt();
        p.getSwitchMonster(x);
    }

    public void turnGame(){
        showHelp();
        System.out.printf("Enter %s's command: ", player1.getPlayerName());
        String command1 = scanner.next();
        
        
        if (command1.equalsIgnoreCase("monsterinfo")){
            viewMonster(player1);
            turn -= 1;
        } else if (command1.equalsIgnoreCase("gameinfo")){
            viewGame();
            turn -= 1;
        } else if(command1.equalsIgnoreCase("exit")){
            exit();
        } else if (command1.equalsIgnoreCase("move") || command1.equalsIgnoreCase("switch")){
            System.out.printf("Enter %s's command: ", player2.getPlayerName());
            String command2 = scanner.next();
            System.out.println();
            if (command1.equalsIgnoreCase("switch")){
                if (command2.equalsIgnoreCase("monsterinfo")){
                    viewMonster(player2);
                    turn -= 1;
                } else if (command2.equalsIgnoreCase("gameinfo")){
                    viewGame();
                    turn -= 1;
                } else if (command2.equalsIgnoreCase("exit")){
                    exit();
                } else{
                    switchMonster(player1);
                    if (command2.equalsIgnoreCase("switch")){
                        switchMonster(player2);
                    }
                    else if (command2.equalsIgnoreCase("move")){
                        if (player2.getCurrentMonster().conditioned() && player2.getCurrentMonster().isSleep()){
                            System.out.printf("%s's %s cannot move!%n", player2.getPlayerName(), player2.getCurrentMonster().getMonsterName());
                            player2.getCurrentMonster().aftersleep();
                        } else {
                            if (player2.getCurrentMonster().getMoves().getListMove().isEmpty()){
                                System.out.printf("There are no remaining moves for %s's %s%n", player2.getPlayerName(), player2.getCurrentMonster().getMonsterName());
                                useDefaultMove(player2, player1);
                            } else {
                                moveOption(player2);
                                move(player2, player1);
                            }
                            if (!player1.getCurrentMonster().getIsAlive()){
                                player1.getMonsterPool().remove(player1.getCurrentMonster());
                                player1.setCurrentMonster(null);
                                if (!player1.getMonsterPool().getListMonster().isEmpty()){
                                    System.out.printf("The remaining monster(s) for %s is: %n", player1.getPlayerName());
                                    player1.getMonsterPool().printMonster();
                                    System.out.printf("Enter monster selection [in integer]: ");
                                    int y = scanner.nextInt();
                                    player1.getMonster(y);
                                }
                            }
                        }
                    }
                    else {
                        wrongInputCommand();
                    }
                }
            }
    
            else if (command1.equalsIgnoreCase("move")){
                if (command2.equalsIgnoreCase("monsterinfo")){
                    viewMonster(player2);
                    turn -= 1;
                } else if (command2.equalsIgnoreCase("gameinfo")){
                    viewGame();
                    turn -= 1;
                } else if(command2.equalsIgnoreCase("exit")){
                    exit();
                } else if (command2.equalsIgnoreCase("switch")){
                    switchMonster(player2);
                    if (player1.getCurrentMonster().isSleep()){
                        System.out.printf("%s's %s cannot move!%n", player1.getPlayerName(), player1.getCurrentMonster().getMonsterName());
                        player1.getCurrentMonster().aftersleep();
                    } else {
                        if (player1.getCurrentMonster().getMoves().getListMove().isEmpty()){
                            System.out.printf("There are no remaining moves for %s's %s%n", player1.getPlayerName(), player1.getCurrentMonster().getMonsterName());
                            useDefaultMove(player1, player2);
                        } else {
                            moveOption(player1);
                            move(player1, player2);
                        }
                        if (!player2.getCurrentMonster().getIsAlive()){
                            player2.getMonsterPool().remove(player2.getCurrentMonster());
                            player2.setCurrentMonster(null);
                            if (!player2.getMonsterPool().getListMonster().isEmpty()){
                                System.out.printf("The remaining monster(s) for %s is: %n", player2.getPlayerName());
                                player2.getMonsterPool().printMonster();
                                System.out.printf("Enter monster selection [in integer]: ");
                                int y = scanner.nextInt();
                                player2.getMonster(y);
                            }
                        }
                    }
                } else if (command2.equalsIgnoreCase("move")){
                    if (player1.getCurrentMonster().isSleep() && player2.getCurrentMonster().isSleep()){
                        System.out.printf("%s's %s cannot move!%n", player1.getPlayerName(), player1.getCurrentMonster().getMonsterName());
                        player1.getCurrentMonster().aftersleep();
                        System.out.printf("%s's %s cannot move!%n", player2.getPlayerName(), player2.getCurrentMonster().getMonsterName());
                        player2.getCurrentMonster().aftersleep();
                    }
                    else if (player1.getCurrentMonster().isSleep() && !player2.getCurrentMonster().isSleep()){
                        System.out.printf("%s's %s cannot move!%n", player1.getPlayerName(), player1.getCurrentMonster().getMonsterName());
                        player1.getCurrentMonster().aftersleep();
                        if (player2.getCurrentMonster().getMoves().getListMove().isEmpty()){
                            System.out.printf("There are no remaining moves for %s's %s%n", player2.getPlayerName(), player2.getCurrentMonster().getMonsterName());
                            useDefaultMove(player2, player1);
                        } else {
                            moveOption(player2);
                            move(player2, player1);
                        }
                        if (!player1.getCurrentMonster().getIsAlive()){
                            player1.getMonsterPool().remove(player1.getCurrentMonster());
                            player1.setCurrentMonster(null);
                            if (!player1.getMonsterPool().getListMonster().isEmpty()){
                                System.out.printf("The remaining monster(s) for %s is: %n", player1.getPlayerName());
                                player1.getMonsterPool().printMonster();
                                System.out.printf("Enter monster selection [in integer]: ");
                                int y = scanner.nextInt();
                                player1.getMonster(y);
                            }
                        }
                    }
                    else if (!player1.getCurrentMonster().isSleep() && player2.getCurrentMonster().isSleep()){
                        System.out.printf("%s's %s cannot move!%n", player2.getPlayerName(), player2.getCurrentMonster().getMonsterName());
                        player2.getCurrentMonster().aftersleep();
                        if (player1.getCurrentMonster().getMoves().getListMove().isEmpty()){
                            System.out.printf("There are no remaining moves for %s's %s%n", player1.getPlayerName(), player1.getCurrentMonster().getMonsterName());
                            useDefaultMove(player1, player2);
                        } else {
                            moveOption(player1);
                            move(player1, player2);
                        }
                        if (!player2.getCurrentMonster().getIsAlive()){
                            player2.getMonsterPool().remove(player2.getCurrentMonster());
                            player2.setCurrentMonster(null);
                            if (!player2.getMonsterPool().getListMonster().isEmpty()){
                                System.out.printf("The remaining monster(s) for %s is: %n", player2.getPlayerName());
                                player2.getMonsterPool().printMonster();
                                System.out.printf("Enter monster selection [in integer]: ");
                                int y = scanner.nextInt();
                                player2.getMonster(y);
                            }
                        }
                    }
                    else {
                        if (player1.getCurrentMonster().getMoves().getListMove().isEmpty() && player2.getCurrentMonster().getMoves().getListMove().isEmpty()){
                            System.out.printf("There are no remaining moves for %s's %s%n", player1.getPlayerName(), player1.getCurrentMonster().getMonsterName());
                            useDefaultMove(player1, player2);
                            System.out.printf("There are no remaining moves for %s's %s%n", player2.getPlayerName(), player2.getCurrentMonster().getMonsterName());
                            useDefaultMove(player2, player1);
                        } else if (player1.getCurrentMonster().getMoves().getListMove().isEmpty() && !player2.getCurrentMonster().getMoves().getListMove().isEmpty()){
                            System.out.printf("There are no remaining moves for %s's %s%n", player1.getPlayerName(), player1.getCurrentMonster().getMonsterName());
                            useDefaultMove(player1, player2);
                        } else if (!player1.getCurrentMonster().getMoves().getListMove().isEmpty() && player2.getCurrentMonster().getMoves().getListMove().isEmpty()){
                            System.out.printf("There are no remaining moves for %s's %s%n", player2.getPlayerName(), player2.getCurrentMonster().getMonsterName());
                            useDefaultMove(player2, player1);
                        } else{
                            
                            moveOption(player1); 
                            moveOption(player2);
                            
                            if (compareMonsterMove(player1.getCurrentMonster(), player2.getCurrentMonster()) == 1){
                                move(player1, player2);
                                if (!player2.getCurrentMonster().getIsAlive()){
                                    player2.getMonsterPool().remove(player2.getCurrentMonster());
                                    player2.setCurrentMonster(null);
                                    if (!player2.getMonsterPool().getListMonster().isEmpty()){
                                        System.out.printf("The remaining monster(s) for %s is: %n", player2.getPlayerName());
                                        player2.getMonsterPool().printMonster();
                                        System.out.printf("Enter monster selection [in integer]: ");
                                        int y = scanner.nextInt();
                                        player2.getMonster(y);
                                    }
                                } else {
                                    if (player2.getCurrentMonster().conditioned() && player2.getCurrentMonster().isSleep()){
                                        System.out.printf("%s's %s cannot move!%n", player2.getPlayerName(), player2.getCurrentMonster().getMonsterName());
                                        player2.getCurrentMonster().aftersleep();
                                    } else {
                                        move(player2, player1);
                                        if (!player1.getCurrentMonster().getIsAlive()){
                                            player1.getMonsterPool().remove(player1.getCurrentMonster());
                                            player1.setCurrentMonster(null);
                                            if (!player1.getMonsterPool().getListMonster().isEmpty()){
                                                System.out.printf("The remaining monster(s) for %s is: %n", player1.getPlayerName());
                                                player1.getMonsterPool().printMonster();
                                                System.out.printf("Enter monster selection [in integer]: ");
                                                int y = scanner.nextInt();
                                                player1.getMonster(y);
                                            }
                                        }
                                    }
                                }
    
                            } else {
                                move(player2, player1);
                                if (!player1.getCurrentMonster().getIsAlive()){
                                    player1.getMonsterPool().remove(player1.getCurrentMonster());
                                    player1.setCurrentMonster(null);
                                    if (!player1.getMonsterPool().getListMonster().isEmpty()){
                                        System.out.printf("The remaining monster(s) for %s is: %n", player1.getPlayerName());
                                        player1.getMonsterPool().printMonster();
                                        System.out.printf("Enter monster selection [in integer]: ");
                                        int y = scanner.nextInt();
                                        player1.getMonster(y);
                                    }
                                } else {
                                    if (player1.getCurrentMonster().conditioned() && player1.getCurrentMonster().isSleep()){
                                        System.out.printf("%s's %s cannot move!%n", player1.getPlayerName(), player1.getCurrentMonster().getMonsterName());
                                        player1.getCurrentMonster().aftersleep();
                                    } else {
                                        move(player1, player2);
                                        if (!player2.getCurrentMonster().getIsAlive()){
                                            player2.getMonsterPool().remove(player2.getCurrentMonster());
                                            player2.setCurrentMonster(null);
                                            if (!player2.getMonsterPool().getListMonster().isEmpty()){
                                                System.out.printf("The remaining monster(s) for %s is: %n", player2.getPlayerName());
                                                player2.getMonsterPool().printMonster();
                                                System.out.printf("Enter monster selection [in integer]: ");
                                                int y = scanner.nextInt();
                                                player2.getMonster(y);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    wrongInputCommand();
                }
            }
    
            
        }
        else {
            wrongInputCommand();
        }

    }

    public void useDefaultMove(Player self, Player enemy){
        System.out.printf("%s uses default move!%n", self.getCurrentMonster().getMonsterName());
        enemy.getCurrentMonster().takeDamage(self.getCurrentMonster().defaultDamage(enemy.getCurrentMonster(), ePool));
        if (enemy.getCurrentMonster().conditioned()){
            if (enemy.getCurrentMonster().isBurn()){
                System.out.printf("Enemy %s was hurt by its burn%n", enemy.getCurrentMonster().getMonsterName());
            } else if (enemy.getCurrentMonster().isPoison()){
                System.out.printf("Enemy %s was hurt by its poison%n", enemy.getCurrentMonster().getMonsterName());
            }
            enemy.getCurrentMonster().takeDamage(enemy.getCurrentMonster().afterDamage());
        }
        self.getCurrentMonster().afterDefaultMove();
    }

    public void move(Player self, Player enemy){
        System.out.printf("%s's %s uses %s%n", self.getPlayerName(), self.getCurrentMonster().getMonsterName(), self.getCurrentMonster().getCurrentMove().getMoveName());
        self.getCurrentMonster().getCurrentMove().moved();
        System.out.printf("The remaining %s ammunition is %d%n", self.getCurrentMonster().getCurrentMove().getMoveName(), self.getCurrentMonster().getCurrentMove().getMoveAmmunition());
        if (self.getCurrentMonster().getCurrentMove() instanceof StatusMove){
            StatusMove s = (StatusMove) self.getCurrentMonster().getCurrentMove();
            if (s.getTarget().equalsIgnoreCase("own")){
                self.getCurrentMonster().setCondition(s);
                self.getCurrentMonster().moveStatus(s);
            } else {
                enemy.getCurrentMonster().setCondition(s);
                enemy.getCurrentMonster().moveStatus(s);
                if (enemy.getCurrentMonster().conditioned()){
                    if (enemy.getCurrentMonster().isBurn()){
                        System.out.printf("Enemy %s was hurt by its burn%n", enemy.getCurrentMonster().getMonsterName());
                        enemy.getCurrentMonster().takeDamage(enemy.getCurrentMonster().afterDamage());
                    } else if (enemy.getCurrentMonster().isPoison()){
                        System.out.printf("Enemy %s was hurt by its poison%n", enemy.getCurrentMonster().getMonsterName());
                    }
                }
            }
        } else {
            int x = self.getCurrentMonster().getCurrentMove().totalDamage(self.getCurrentMonster(), enemy.getCurrentMonster(), ePool);
            enemy.getCurrentMonster().takeDamage(x);
            if (enemy.getCurrentMonster().conditioned()){
                if (enemy.getCurrentMonster().isBurn()){
                    System.out.printf("Enemy %s was hurt by its burn%n", enemy.getCurrentMonster().getMonsterName());
                } else if (enemy.getCurrentMonster().isPoison()){
                    System.out.printf("Enemy %s was hurt by its poison%n", enemy.getCurrentMonster().getMonsterName());
                }
                enemy.getCurrentMonster().takeDamage(enemy.getCurrentMonster().afterDamage());
            }
        }

        if (self.getCurrentMonster().getCurrentMove().getMoveAmmunition() == 0){
            self.getCurrentMonster().removeMove(self.getCurrentMonster().getCurrentMove());
        }
    }

    public void moveOption(Player p){
        System.out.printf("Moves option for %s's %s:%n", p.getPlayerName(), p.getCurrentMonster().getMonsterName());
        p.getCurrentMonster().printMove();
        System.out.printf("Enter move selection [in integer]: ");
        int x = scanner.nextInt();
        Move m = p.getCurrentMonster().getMoveMonster(x);
        p.getCurrentMonster().setCurrentMove(m);
        System.out.println();
    }

    
    public int compareMonsterMove(Monster m1, Monster m2){
        if (m1.getCurrentMove().getMovePriority() > m2.getCurrentMove().getMovePriority()){
            return 1;
        } else if (m1.getCurrentMove().getMovePriority() < m2.getCurrentMove().getMovePriority()){
            return 2;
        } else {
            if (m1.getStats().getSpeed() > m2.getStats().getSpeed()){
                return 1;
            } else if (m1.getStats().getSpeed() < m2.getStats().getSpeed()){
                return 2;
            } else{
                double random = Math.random() * 2;
                int value = (int)Math.round(random);
                return value;
            }
        }
    }
    
    public void viewMonster(Player p){
        int i = 1;
        System.out.println();
        System.out.printf("~~~~~ Monsters info for %s: ~~~~~%n", p.getPlayerName());
        System.out.println();
        for (Monster m : p.getMonsterPool().getListMonster()){
            System.out.printf("%d. %s%n", i, m.getMonsterName());
            m.printInfo();
            System.out.println();
            i++;
        }
        System.out.printf("%d. %s%n", i, p.getCurrentMonster().getMonsterName());
        p.getCurrentMonster().printInfo();
        System.out.println();
    }

    public void viewGame(){
        System.out.println();
        System.out.println("~~~~~ Game Information ~~~~~");
        System.out.println();
        System.out.println("Turn number: " + turn);
        System.out.println();
        System.out.printf("%s's current monster: %s%n", player1.getPlayerName(), player1.getCurrentMonster().getMonsterName());
        System.out.printf("%s's unused monster: %n", player1.getPlayerName());
        player1.getSwitchingOption().printMonster();
        System.out.println();
        System.out.printf("%s's current monster: %s%n", player2.getPlayerName(), player2.getCurrentMonster().getMonsterName());
        System.out.printf("%s's unused monster: %n", player2.getPlayerName());
        player2.getSwitchingOption().printMonster();
        System.out.println();
    }

    public void exit(){
        loop = false;
        System.out.println("~ Bye ~");
    }

    public void printWinner(Player winner, Player loser){
        System.out.println();
        System.out.println("~~~~~~~~~~ CONGRATULATIONS! ~~~~~~~~~~");
        System.out.printf("%s defeated %s%n", winner.getPlayerName(), loser.getPlayerName());
        System.out.println();
    }

    // Udah bener. Jangan ada yg diubah
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
                        addresses.add(ls);
                        // ini buat id
                        Integer id = Integer.parseInt(ls.get(0));
                        Integer id2 = Integer.parseInt(ls.get(0));
                        
                        // ini buat type
                        String tipe = ls.get(1);
                        String tipe2 = ls.get(1);

                        // ini buat nama
                        String name = ls.get(2);
                        String name2 = ls.get(2);

                        // ini buat elementType
                        String se = ls.get(3);
                        String se2 = ls.get(3);
                        ElementType elementType = ElementType.valueOf(se);
                        ElementType elementType2 = ElementType.valueOf(se2);
                        

                        // ini buat acc, prior, amun
                        Integer accuracy = Integer.parseInt(ls.get(4));
                        Integer accuracy2 = Integer.parseInt(ls.get(4));
                        Integer priority= Integer.parseInt(ls.get(5));
                        Integer priority2= Integer.parseInt(ls.get(5));
                        Integer ammunition = Integer.parseInt(ls.get(6));
                        Integer ammunition2 = Integer.parseInt(ls.get(6));

                        String target = ls.get(7);
                        String target2 = ls.get(7);

                        if (tipe.equals("NORMAL")){
                            Integer basepower = Integer.parseInt(ls.get(8));
                            Integer basepower2 = Integer.parseInt(ls.get(8));
                            NormalMove n = new NormalMove(id, tipe, name, elementType, accuracy, priority, ammunition, target, basepower);
                            NormalMove n2 = new NormalMove(id2, tipe2, name2, elementType2, accuracy2, priority2, ammunition2, target2, basepower2);
                            mvPool.addMove(n);
                            mvPool2.addMove(n2);
                        } else if (tipe.equals("SPECIAL")){
                            Integer basepower = Integer.parseInt(ls.get(8));
                            Integer basepower2 = Integer.parseInt(ls.get(8));
                            SpecialMove s = new SpecialMove(id, tipe, name, elementType, accuracy, priority, ammunition, target, basepower);
                            SpecialMove s2 = new SpecialMove(id2, tipe2, name2, elementType2, accuracy2, priority2, ammunition2, target2, basepower2);
                            mvPool.addMove(s);
                            mvPool2.addMove(s2);
                        } else if (tipe.equals("STATUS")){
                            String efek = ls.get(8);
                            String efek2 = ls.get(8);
                            String num = ls.get(9);
                            String num2 = ls.get(9);
                            String[] numEl = num.split(",");
                            List<String> listNum = Arrays.asList(numEl);
                            Integer HP = Integer.parseInt(listNum.get(0));
                            Integer att = Integer.parseInt(listNum.get(1));
                            Integer def = Integer.parseInt(listNum.get(2));
                            Integer spAtt = Integer.parseInt(listNum.get(3));
                            Integer spDef = Integer.parseInt(listNum.get(4));
                            Integer speed = Integer.parseInt(listNum.get(5));
                            String[] numEl2 = num2.split(",");
                            List<String> listNum2 = Arrays.asList(numEl2);
                            Integer HP2 = Integer.parseInt(listNum2.get(0));
                            Integer att2 = Integer.parseInt(listNum2.get(1));
                            Integer def2 = Integer.parseInt(listNum2.get(2));
                            Integer spAtt2 = Integer.parseInt(listNum2.get(3));
                            Integer spDef2 = Integer.parseInt(listNum2.get(4));
                            Integer speed2 = Integer.parseInt(listNum2.get(5));
                            StatusMove st = new StatusMove(id, tipe, name, elementType, accuracy, priority, ammunition, target, efek, HP, att, def, spAtt, spDef, speed);
                            StatusMove st2 = new StatusMove(id2, tipe2, name2, elementType2, accuracy2, priority2, ammunition2, target2, efek2, HP2, att2, def2, spAtt2, spDef2, speed2);
                            mvPool.addMove(st);
                            mvPool2.addMove(st2);
                        }
                    }

                    else if (fileName.equals("configs/element-type-effectivity-chart.csv")){
                        ElementType source = ElementType.valueOf(ls.get(0));
                        
                        ElementType target = ElementType.valueOf(ls.get(1));

                        double effectivity = Double.parseDouble(ls.get(2));

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
                        Stats stats2 = new Stats(HP, att, def, spAtt, spDef, speed); // berhasil inisiasi stats
                        
                        // ini buat move bagian 1
                        String monsterMove = ls.get(4);
                        String[] monsterMoveEl = monsterMove.split(",");
                        List<String> listMonsterMove = Arrays.asList(monsterMoveEl);
                        MovePool monsterMovePool = new MovePool();
                        int i = 0;
                        for (Move m : mvPool.getListMove()){
                            for (String moveID : listMonsterMove){
                                if (m.getMoveID() == Integer.valueOf(moveID)){
                                    ArrayList<String> mls = new ArrayList<String>();
                                    List<String> f = addresses.get(i);
                                    for(String word : f){
                                        mls.add(word);
                                    }

                                    Integer idMove = Integer.parseInt(mls.get(0));
                                    
                                    // ini buat type
                                    String tipe = mls.get(1);
            
                                    // ini buat nama
                                    String nameMove = mls.get(2);
            
                                    // ini buat elementType
                                    String se = mls.get(3);
                                    ElementType elementType = ElementType.valueOf(se);                                    
            
                                    // ini buat acc, prior, amun
                                    Integer accuracy = Integer.parseInt(mls.get(4));
                                    Integer priority= Integer.parseInt(mls.get(5));
                                    Integer ammunition = Integer.parseInt(mls.get(6));
            
                                    String target = mls.get(7);
            
                                    if (tipe.equals("NORMAL")){
                                        Integer basepower = Integer.parseInt(mls.get(8));
                                        NormalMove n = new NormalMove(idMove, tipe, nameMove, elementType, accuracy, priority, ammunition, target, basepower);
                                        monsterMovePool.addMove(n);
                                    } else if (tipe.equals("SPECIAL")){
                                        Integer basepower = Integer.parseInt(mls.get(8));
                                        SpecialMove s = new SpecialMove(idMove, tipe, nameMove, elementType, accuracy, priority, ammunition, target, basepower);
                                        monsterMovePool.addMove(s);
                                    } else if (tipe.equals("STATUS")){
                                        String efek = mls.get(8);
                                        String numMove = mls.get(9);
                                        String[] numElMove = numMove.split(",");
                                        List<String> listNumMove = Arrays.asList(numElMove);
                                        Integer HPMove = Integer.parseInt(listNumMove.get(0));
                                        Integer attMove = Integer.parseInt(listNumMove.get(1));
                                        Integer defMove = Integer.parseInt(listNumMove.get(2));
                                        Integer spAttMove = Integer.parseInt(listNumMove.get(3));
                                        Integer spDefMove = Integer.parseInt(listNumMove.get(4));
                                        Integer speedMove = Integer.parseInt(listNumMove.get(5));
                                        StatusMove st = new StatusMove(idMove, tipe, nameMove, elementType, accuracy, priority, ammunition, target, efek, HPMove, attMove, defMove, spAttMove, spDefMove, speedMove);
                                        monsterMovePool.addMove(st);
                                    }
                                }
                            }
                            i++;
                        }                        
                        // instansiasi
                        Monster m = new Monster(id, name, elementTypes, stats);
                        m.setMonsterMovePool(monsterMovePool);
                        mnstrPool.addMonster(m);

                        // ini buat move bagian 2
                        String monsterMove2 = ls.get(4);
                        String[] monsterMoveEl2 = monsterMove2.split(",");
                        List<String> listMonsterMove2 = Arrays.asList(monsterMoveEl2);
                        MovePool monsterMovePool2 = new MovePool();
                        int i2 = 0;
                        for (Move m2 : mvPool2.getListMove()){
                            for (String moveID : listMonsterMove2){
                                if (m2.getMoveID() == Integer.valueOf(moveID)){
                                    ArrayList<String> mls = new ArrayList<String>();
                                    List<String> f = addresses.get(i2);
                                    for(String word : f){
                                        mls.add(word);
                                    }

                                    Integer idMove = Integer.parseInt(mls.get(0));
                                    
                                    // ini buat type
                                    String tipe = mls.get(1);
            
                                    // ini buat nama
                                    String nameMove = mls.get(2);
            
                                    // ini buat elementType
                                    String se = mls.get(3);
                                    ElementType elementType = ElementType.valueOf(se);                                    
            
                                    // ini buat acc, prior, amun
                                    Integer accuracy = Integer.parseInt(mls.get(4));
                                    Integer priority= Integer.parseInt(mls.get(5));
                                    Integer ammunition = Integer.parseInt(mls.get(6));
            
                                    String target = mls.get(7);
            
                                    if (tipe.equals("NORMAL")){
                                        Integer basepower = Integer.parseInt(mls.get(8));
                                        NormalMove n = new NormalMove(idMove, tipe, nameMove, elementType, accuracy, priority, ammunition, target, basepower);
                                        monsterMovePool2.addMove(n);
                                    } else if (tipe.equals("SPECIAL")){
                                        Integer basepower = Integer.parseInt(mls.get(8));
                                        SpecialMove s = new SpecialMove(idMove, tipe, nameMove, elementType, accuracy, priority, ammunition, target, basepower);
                                        monsterMovePool2.addMove(s);
                                    } else if (tipe.equals("STATUS")){
                                        String efek = mls.get(8);
                                        String numMove = mls.get(9);
                                        String[] numElMove = numMove.split(",");
                                        List<String> listNumMove = Arrays.asList(numElMove);
                                        Integer HPMove = Integer.parseInt(listNumMove.get(0));
                                        Integer attMove = Integer.parseInt(listNumMove.get(1));
                                        Integer defMove = Integer.parseInt(listNumMove.get(2));
                                        Integer spAttMove = Integer.parseInt(listNumMove.get(3));
                                        Integer spDefMove = Integer.parseInt(listNumMove.get(4));
                                        Integer speedMove = Integer.parseInt(listNumMove.get(5));
                                        StatusMove st = new StatusMove(idMove, tipe, nameMove, elementType, accuracy, priority, ammunition, target, efek, HPMove, attMove, defMove, spAttMove, spDefMove, speedMove);
                                        monsterMovePool2.addMove(st);
                                    }
                                }
                            }
                            i2++;
                        }
                        // instansiasi
                        Monster m2 = new Monster(id, name, elementTypes, stats2);
                        m2.setMonsterMovePool(monsterMovePool2);
                        mnstrPool2.addMonster(m2);
                    }

                }
                
            } catch (Exception e) {
                // do nothing
            }

        }
        
    }

}