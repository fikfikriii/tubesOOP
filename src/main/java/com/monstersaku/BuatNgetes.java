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

public class BuatNgetes {
    private static final List<String> CSV_FILE_PATHS = Collections.unmodifiableList(Arrays.asList(
            "configs/movepool.csv",
            "configs/element-type-effectivity-chart.csv",
            "configs/monsterpool.csv"));

    public static boolean loop = true;
    Player player1;
    Player player2;
    MonsterPool player1MonsterPool;
    MonsterPool player2MonsterPool;

    Scanner scanner = new Scanner(System.in);
    
    public void playGame(){
        inputPlayer();
        System.out.println();
        MonsterPool mnstrPool = new MonsterPool();
        MovePool mvPool = new MovePool();
        EffectivityPool ePool = new EffectivityPool();
        for (String fileName : CSV_FILE_PATHS) {
            try {
                System.out.printf("Filename: %s\n", fileName);
                CSVReader reader = new CSVReader(new File(BackupMain.class.getResource(fileName).toURI()), ";");
                reader.setSkipHeader(true);
                List<String[]> lines = reader.read();
                System.out.println("=========== CONTENT START ===========");
                for (String[] line : lines) {
                    List<String> ls = new ArrayList<String>();
                    for (String word : line) {
                        System.out.printf("%s ", word);
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
                            NormalMove n = new NormalMove(id, name, elementType, accuracy, priority, ammunition, target, basepower);
                            mvPool.addMove(n);
                        } else if (tipe.equals("SPECIAL")){
                            Integer basepower = Integer.parseInt(ls.get(8));
                            SpecialMove s = new SpecialMove(id, name, elementType, accuracy, priority, ammunition, target, basepower);
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
                            StatusMove st = new StatusMove(id, name, elementType, accuracy, priority, ammunition, target, efek, HP, att, def, spAtt, spDef, speed);
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


                    System.out.println();
                }

                System.out.println("=========== CONTENT END ===========");
                System.out.println();

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

    public void turnGame(){
        while(loop){
            System.out.printf("Masukan command player 1: ");
            String command1 = scanner.next();
            System.out.printf("Masukan command player 2: ");
            String command2 = scanner.next();

            if (command1.equalsIgnoreCase("Switch")){
                boolean switched1 = false;
                while(!switched1){
                    // print list monster yang masi hidup player1;
                    // switch monster;
                    command1 = scanner.next();
                    if (command1.equalsIgnoreCase("Move")){
                        switched1 = true;
                    }
                }
            } else if (command1.equalsIgnoreCase("Move")){
                /* if (isSleep(monster player 1)){
                    System.out.println("Monster <nama monster> tidak dapat bergerak!");
                } else {
                    System.out.println("Move mana yang akan dilakukan oleh <nama monster>");
                    // print info move dari monster tersebut;
                    System.out.printf("Masukan kode move dalam integer: ");
                    int moveCode = scanner.nextInt();
                    // sesuaiin moveCode sama code move di monster
                } */
            }
            
            if (command2.equalsIgnoreCase("Switch")){
                boolean switched2 = false;
                while(!switched2){
                    // print list monster yang masi hidup player2;
                    // switch monster;
                    command2 = scanner.next();
                    if (command2.equalsIgnoreCase("Move")){
                        switched2 = true;
                    }
                }
            } else if (command2.equalsIgnoreCase("Move")){
                System.out.println("Move mana yang akan dilakukan oleh <nama monster>");
                // print info move dari monster tersebut;
                System.out.printf("Masukan kode move dalam integer: ");
                int moveCode = scanner.nextInt();
                // sesuaiin moveCode sama code move di monster
            }      
        }
    }

}