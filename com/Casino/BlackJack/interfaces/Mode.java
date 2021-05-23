package com.Casino.BlackJack.interfaces;

import com.Casino.BlackJack.classes.GameLogic.Game;

public interface Mode {

    void InitializeShoeAndParameters(Game game);

    String getCommand(int nHand);
}
