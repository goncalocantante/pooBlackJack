package com.Casino.gamelogic.main;

import com.Casino.gamelogic.classes.*;

public class BlackJackMain {

    public static void main(String[] args) {

        Game game = new Game();

        game.initializeGame();

        // loop para o jogador pedir mais cartas
        // jogador acaba este estado bust ou stand
        game.playerTurn();
        // loop para o dealer fazer a sua jogada
        game.dealerTurn();
        // avaliar pontuações e pagar as bets
        game.finishRound();

    }

}
