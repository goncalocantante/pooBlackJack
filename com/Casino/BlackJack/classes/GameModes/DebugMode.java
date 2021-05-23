package com.Casino.BlackJack.classes.GameModes;

import com.Casino.BlackJack.classes.GameLogic.Game;
import com.Casino.BlackJack.classes.GameLogic.ShoeClass;
import com.Casino.BlackJack.interfaces.Mode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DebugMode implements Mode {
    //Input arguments
    String[] args;
    ShoeClass shoe;
    Game game;
    Scanner scanner;

    /**
     * Constructor to initialize  DebugMode
     * @param arguments: input arguments of the game
     */
    public DebugMode(String[] arguments) {
        args = arguments;
    }

    /**
     * Receives and validates the program's input parameters
     * Passes these parameters to the Game object
     * Initializes the game's shoe by reading it from the specified file
     * @param game: game being played
     */
    public void InitializeShoeAndParameters(Game game) {
        this.game = game;
        int minBet;
        int maxBet;
        int balance;
        String shoeFilePath;

        // incorrect number of arguments
        if (args.length != 6) {
            System.out.println("Error: incorrect number of arguments");
            System.exit(0);
        }

        // checks if min bet, max bet and balance are ints
        for (int i = 1; i < (args.length-2); i++) {
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


        // Reads shoe and cmd file names
        shoeFilePath = args[4];
        // opens cmd commands file
        File file = new File(args[5]);
        try {
            this.scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Check if min-bet and max-bet parameters are correct
        if (minBet < 1 || maxBet < 10 * minBet || maxBet > 20 * maxBet || balance < 50 * minBet) {
            System.out.println("Error: invalid parameters");
            System.exit(0);
        }

        // Sets parameters in game object
        this.game.setParameters(minBet, maxBet, balance, 100, -1);
        // Create shoe from file
        this.game.setShoe(new ShoeClass(shoeFilePath));
    }

    /**
     * Lê do ficheiro a próxima ação a tomar pelo jogador
     * @return string com a ação do jogador
     */
    @Override
    public String getCommand(int nHand) {
        String cmd = "";
        if(this.scanner.hasNext()){

            //Read next command
            cmd = String.valueOf(this.scanner.next());
            // If bet amount is specified
            if((cmd.equals("b")) && this.scanner.hasNextInt()){
                cmd += " " + String.valueOf(this.scanner.nextInt());
            }

            return cmd;
        }else{
            System.out.println("No more commands");
            System.exit(0);
        }
        return null;
    }
}
