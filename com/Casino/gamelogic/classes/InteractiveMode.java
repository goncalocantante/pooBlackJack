package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.Mode;

import java.util.Scanner;

public class InteractiveMode implements Mode {

    String[] args;
    ShoeClass shoe;
    Game game;
    Scanner scanner;

    public InteractiveMode(String[] arguments) {
        args = arguments;
        scanner = new Scanner(System.in);
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
        int shoe;
        int shuffle;

        // If there arent's 6 parameters
        if (args.length != 6)
            check = false;

        // If parameters aren't ints (except for the mode parameter)
        for (int i = 0; i < args.length; i++) {
            if (!args[0].matches(".*\\d.*"))
                check = false;
        }
        minBet = Integer.parseInt(args[1]);
        maxBet = Integer.parseInt(args[2]);
        balance = Integer.parseInt(args[3]);
        shoe = Integer.parseInt(args[4]);
        shuffle = Integer.parseInt(args[5]);

        // Check if min-bet and max-bet parameters are correct
        if (minBet < 1 || maxBet < 10 * minBet || maxBet > 20 * maxBet || balance < 50 * minBet)
            check = false;

        // Check if shoe and shuffle parameters are correct
        if (shoe < 4 || shoe > 8 || shuffle < 10 || shuffle > 100)
            check = false;

        if (!check) {
            System.out.println("Invalid Arguments!");
            scanner.close();
            System.exit(0);
        }
        // quit

        // Sets parameters in game object
        this.game.setParameters(minBet, maxBet, balance, shuffle);
        // Create shoe
        this.game.setShoe(new ShoeClass(shoe));
    }

    @Override
    public String getCommand() {
        if (this.scanner.hasNextLine())
            return this.scanner.nextLine();
        System.out.println("no text to scan");
        return null;
    }
}
