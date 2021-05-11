package com.Casino.gamelogic.main;

import com.Casino.gamelogic.classes.*;

public class BlackJackMain {

    public static void main(String[] args) {

        Game game = new Game();

        System.out.println("it worked");

        /*
         * game.getShoe().shuffle();
         * 
         * game.getPlayerTurnState().startState();
         * game.getPlayerTurnState().resolveState();
         */

        game.initializeGame();
        game.playerTurn();
        // game.dealerTurn();
        // game.finishRound();

    }

}
