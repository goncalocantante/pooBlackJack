package com.Casino.gamelogic.main;

import com.Casino.gamelogic.classes.*;

public class BlackJackMain {

    public static void main(String[] args) {

        Game game = new Game();

        game.initializeGame();
        game.playerTurn();
        game.initializeGame();

    }

}
