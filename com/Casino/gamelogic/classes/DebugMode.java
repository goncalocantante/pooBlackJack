package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.Mode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DebugMode implements Mode {
    String[] args;
    ShoeClass shoe;
    Game game;
    Scanner scanner;

    public DebugMode(String[] arguments) {
        args = arguments;
    }

    /**
     * Recebe e valida os parametros de input do programa
     * Regista os parametros min bet, max bet e balance nos respetivos atributos do Game game
     * Lê o nome dos ficheiros do shoe e dos comandos do terminal
     * Inicialia o shoe
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

        // incorrect number of arguments
        if (args.length != 6)
            check = false;

        // checks if min bet, max bet and balance are ints
        for (int i = 1; i < (args.length-2); i++) {
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
        if (minBet < 1 || maxBet < 10 * minBet || maxBet > 20 * maxBet || balance < 50 * minBet)
            check = false;


        if (!check) {
            System.out.println("Invalid Arguments!");
            scanner.close();
            System.exit(0);
        }

        // Sets parameters in game object
        this.game.setParameters(minBet, maxBet, balance, 100);
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
            cmd = String.valueOf(this.scanner.next());
            // If bet amount is specified
            if((cmd.equals("b")) && this.scanner.hasNextInt()){
                int nextI = this.scanner.nextInt();
                if(nextI == 2){
                    // interpret as bet command followed by double command
                }else {
                    cmd += " " + String.valueOf(nextI);
                }
            }
            return cmd;
        }else{
            System.out.println("No more commands");
            System.exit(0);
        }
        return null;
    }
}
