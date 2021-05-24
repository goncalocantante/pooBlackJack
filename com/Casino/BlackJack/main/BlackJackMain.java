package com.Casino.BlackJack.main;

import com.Casino.BlackJack.classes.GameLogic.Game;
import com.Casino.BlackJack.classes.GameModes.DebugMode;
import com.Casino.BlackJack.classes.GameModes.InteractiveMode;
import com.Casino.BlackJack.classes.GameModes.SimulationMode;

/**
 * Main class
 * Used for executing the game
 */
public class BlackJackMain {

    /**
     * Main method, executes the game
     * @param args: input arguments
     */
    public static void main(String[] args) {

        Game game = null;

        if (args.length == 0) {
            System.out.println("Error: No gamemode specified");
            return;
        }



        switch (args[0]) {
            case "-i":
                game = new Game(new InteractiveMode(args));
                break;
            case "-d":
                game = new Game(new DebugMode(args));
                break;
            case "-s":
                game = new Game(new SimulationMode(args));
                break;
            default:
                System.out.println("");
                System.exit(0);
        }

        game.initializeGame();

        while (true) {
            game.playerTurn(); // loop para o dealer fazer a sua jogada
            game.dealerTurn(); // avaliar pontuações e pagar as bets game.finishRound();
            game.finishRound();
        }
    }
}


