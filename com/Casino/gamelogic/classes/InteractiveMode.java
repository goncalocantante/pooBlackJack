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
        int balance = 0;
        int shoe;
        int shuffle;

        // If there arent's 6 parameters
        if (args.length != 6)
            check = false;

        // checks if min bet, max bet, balance, number of deck and shuffle percentage are ints
        for (int i = 1; i < args.length; i++) {
            try {
                int intValue = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Arguments!");
                System.exit(0);
            }
        }
        minBet = Integer.parseInt(args[1]);
        maxBet = Integer.parseInt(args[2]);
        balance = Integer.parseInt(args[3]);
        System.out.println(balance);
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

        // Sets parameters in game object
        this.game.setParameters(minBet, maxBet, balance, shuffle);
        // Create and shuffles shoe
        this.game.setShoe(new ShoeClass(shoe));
        this.game.getShoe().shuffle();
    }

    @Override
    public String getCommand() {
        if (this.scanner.hasNextLine())
            return this.scanner.nextLine();
        System.out.println("no text to scan");
        return null;
    }
}
