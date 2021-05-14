package com.Casino.gamelogic.interfaces;

import com.Casino.gamelogic.classes.Game;

public interface Mode {

    void InitializeShoeAndParameters(Game game);

    String getCommand();

}
