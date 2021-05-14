package com.Casino.gamelogic.main;

import com.Casino.gamelogic.classes.*;

public class BlackJackMain {

    public static void main(String[] args) {

        Game game = null;

        if(args.length == 0) {
            System.out.println("Error: No gamemode specified");
            return;
        }

        switch(args[0]){
            case "-i":
                game = new Game(new InteractiveMode(args));
                break;
            default:
                //quit
        }


        game.initializeGame();

        while(true) {
            game.playerTurn(); // loop para o dealer fazer a sua jogada
            game.dealerTurn(); // avaliar pontuações e pagar as bets game.finishRound();
            game.finishRound();
        }
    }

}
