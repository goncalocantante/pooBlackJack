package com.Casino.BlackJack.classes.GameModes;

import com.Casino.BlackJack.classes.GameLogic.Game;
import com.Casino.BlackJack.classes.GameLogic.ShoeClass;
import com.Casino.BlackJack.interfaces.Mode;

import java.util.Scanner;

public class InteractiveMode implements Mode {

    String[] args;
    Game game;
    Scanner scanner;

    /**
     * Constructor that initializes Interactive mode
     * @param arguments: input arguments of the game
     */
    public InteractiveMode(String[] arguments) {
        args = arguments;
        scanner = new Scanner(System.in);
    }

    /**
     * Creates shoe and initializes parameters min-bet, max-bet, balance and shuffle
     * @param game: game being played
     */
    @Override
    public void InitializeShoeAndParameters(Game game) {
        this.game = game;
        // Initialized as true and set to false if parameters aren't correct
        int minBet;
        int maxBet;
        int balance = 0;
        int shoe;
        int shuffle;

        // If there arent's 6 parameters exit
        if (args.length != 6){
            System.out.println("Error: incorrect number of arguments");
            System.exit(0);
        }

        // checks if min bet, max bet, balance, number of deck and shuffle percentage are ints
        for (int i = 1; i < args.length; i++) {
            try {
                int intValue = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                System.out.println("Error: invalid parameters");
                System.exit(0);
            }
        }

        minBet = Integer.parseInt(args[1]);
        maxBet = Integer.parseInt(args[2]);
        balance = Integer.parseInt(args[3]);
        shoe = Integer.parseInt(args[4]);
        shuffle = Integer.parseInt(args[5]);



        // Check if min-bet and max-bet parameters are correct
        if (minBet < 1 || maxBet < 10 * minBet || maxBet > 20 * maxBet || balance < 50 * minBet){
            System.out.println("Error: invalid parameters");
            System.exit(0);
        }

        // Check if shoe and shuffle parameters are correct
        if (shoe < 4 || shoe > 8 || shuffle < 10 || shuffle > 100){
            System.out.println("Error: invalid parameters");
            System.exit(0);
        }

        // Sets parameters in game object
        this.game.setParameters(minBet, maxBet, balance, shuffle, -1);
        // Create and shuffles shoe
        this.game.setShoe(new ShoeClass(shoe));
        this.game.getShoe().shuffle();
    }

    /**
     * Receives input command from the player and returns it
     * @param nHand: index of hand being played
     * @return command: player's input command
     */
    @Override
    public String getCommand(int nHand) {
        if (this.scanner.hasNextLine())
            return this.scanner.nextLine();
        System.out.println("Error: no text to scan");
        return null;
    }
}
