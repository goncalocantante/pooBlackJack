package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.Mode;

import java.util.Scanner;

public class DebugMode implements Mode {
    String cmdFile;
    String[] args;
    ShoeClass shoe;
    Game game;
    Scanner scanner;

    public DebugMode(String[] arguments) {
        args = arguments;
    }

    /**
     * Creates shoe and initializes parameters min-bet, max-bet, balance and shuffle
     * 
     * @param game
     */
    public void InitializeShoeAndParameters(Game game) {
        this.game = game;
        // Initialized as true and set to false if parameters aren't correct
        boolean check = true;
        int minBet;
        int maxBet;
        int balance;
        String shoeFilePath;

        //TODO checkar as verificações

        // If there arent's 6 parameters
        if (args.length != 6)
            check = false;

        // If parameters aren't ints (except for the mode parameter)
        for (int i = 0; i < args.length; i++) {
            if (!args[0].matches(".*\\D.*"))
                check = false;
        }
        minBet = Integer.parseInt(args[1]);
        maxBet = Integer.parseInt(args[2]);
        balance = Integer.parseInt(args[3]);
        // Reads shoe and cmd file names
        shoeFilePath = args[4];
        // opens cmd commands file
        cmdFile = args[5];
        this.scanner = new Scanner(cmdFile);

        // Check if min-bet and max-bet parameters are correct
        if (minBet < 1 || maxBet < 10 * minBet || maxBet > 20 * maxBet || balance < 50 * minBet)
            check = false;


        if (!check) {
            // exit
            System.out.println("Invalid Arguments!");
            scanner.close();
            System.exit(0);
        }

        // Sets parameters in game object
        this.game.setParameters(minBet, maxBet, balance, 100);
        // Create shoe from file
        this.game.setShoe(new ShoeClass(shoeFilePath));
    }

    @Override
    public String getCommand() {
        String cmd = "";
        if(this.scanner.hasNext()){
            cmd = String.valueOf(this.scanner.next().charAt(0));

            // If bet amount is specified
            if((cmd == "b") && (this.scanner.hasNextInt())){
                cmd += " " + String.valueOf(this.scanner.nextInt());
                System.out.println("Get command inside: " + cmd);
            }

            System.out.println("Get command: " + cmd);

            return cmd;
        }else{
            System.out.println("no text to scan");
        }


        return null;
    }
}
