package com.monstersaku;

import java.util.Scanner;

public class Main implements Instruction{
    public static String gameState = "MainScreen";

    public Main(){}
    public static void main(String[] args) {
        Main m = new Main();
        boolean loopflag = true;
        Scanner input = new Scanner(System.in);
        Game game = new Game();

        System.out.println("=============");
        System.out.println("MONSTER SAKU");
        System.out.println("=============");
        System.out.println();
        
        while(loopflag){
            m.showHelp();
            System.out.printf("Enter command: ");
            String commandTemp = input.next();

            // 1. Start
            if (commandTemp.equalsIgnoreCase("s") || commandTemp.equalsIgnoreCase("Start") || commandTemp.equalsIgnoreCase("1")){
                gameState = "Game";
                game.playGame();
                // play game
            } 
            else if (commandTemp.equalsIgnoreCase("h") || commandTemp.equalsIgnoreCase("Help") || commandTemp.equalsIgnoreCase("2")){
                m.showHelp();
            }
            else if (commandTemp.equalsIgnoreCase("e") || commandTemp.equalsIgnoreCase("Exit") || commandTemp.equalsIgnoreCase("3")){
                m.exit();
                loopflag = false;
            }
            else{
                m.wrongInputCommand();
                m.showHelp();
            }
        }

        input.close();
    }

    public void showHelp(){
        System.out.println("MAIN SCREEN INSTRUCTIONS:");
        System.out.println("1. [Start / S / 1] = Play Game");
        System.out.println("2. [Help / H / 2] = Show Main Screen Instruction");
        System.out.println("3. [Exit / E / 3] = Exit The Game\n");
    }

    public void wrongInputCommand(){
        System.out.println("Wrong Input Command. Please read the main screen instructions!");
    }

    public void exit(){
        System.out.println("==================================");
        System.out.println("THANK YOU FOR PLAYING MONSTER SAKU");
        System.out.println("==================================");
    }
}