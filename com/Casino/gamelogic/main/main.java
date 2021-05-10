package com.Casino.gamelogic.main;

import com.Casino.gamelogic.classes.Card;
import com.Casino.gamelogic.classes.Hand;
import com.Casino.gamelogic.enumerations.Rank;
import com.Casino.gamelogic.enumerations.Suit;
import com.Casino.gamelogic.interfaces.GameState;
import com.Casino.gamelogic.classes.Game;

public class main {

    public static void main(String[] args) {
        Game game = new Game();

        while (!game.getCurrentState().equals(game.getRoundEndState())) {
            game.getCurrentState().startState();
            game.getCurrentState().resolveState();
            game.getCurrentState().endState();

        }
    }
}
