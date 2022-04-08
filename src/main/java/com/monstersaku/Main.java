package com.monstersaku;

import java.util.Scanner;

public class Main {
    public static String gameState = "MainScreen";

    public static void main(String[] args) {
        boolean loopflag = true;
        Scanner input = new Scanner(System.in);
        Game game = new Game();

        System.out.println("=============");
        System.out.println("MONSTER SAKU");
        System.out.println("=============");
        System.out.println();
        Main.showMainScreenHelp();

        while(loopflag){
            System.out.printf("Masukan perintah: ");
            String commandTemp = input.next();

            // 1. Start
            if (commandTemp.equalsIgnoreCase("s") || commandTemp.equalsIgnoreCase("Start") || commandTemp.equalsIgnoreCase("1")){
                gameState = "Game";
                game.playGame();
                // play game
            } 
            else if (commandTemp.equalsIgnoreCase("h") || commandTemp.equalsIgnoreCase("Help") || commandTemp.equalsIgnoreCase("2")){
                showMainScreenHelp();
            }
            else if (commandTemp.equalsIgnoreCase("e") || commandTemp.equalsIgnoreCase("Exit") || commandTemp.equalsIgnoreCase("3")){
                System.out.println("==================================");
                System.out.println("THANK YOU FOR PLAYING MONSTER SAKU");
                System.out.println("==================================");
            }
            else{
                wrongInputCommand();
                showMainScreenHelp();
            }
        }

        input.close();
    }

    public static void clearConsole(){
        try{
            String os = System.getProperty("os.name");
            if (os.contains("Windows")){
                Runtime.getRuntime().exec("cls");
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static void showMainScreenHelp(){
        System.out.println("MAIN SCREEN INSTRUCTIONS:");
        System.out.println("1. [Start / S / 1] = Play Game");
        System.out.println("2. [Help / H / 2] = Show Main Screen Instruction");
        System.out.println("3. [Exit / E / 3] = Exit The Game\n");
        System.out.println("Tes");
    }

    public static void wrongInputCommand(){
        System.out.println("Wrong Input Command. Please read the main screen instructions!");
    } 
}