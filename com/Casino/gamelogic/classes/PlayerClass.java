package com.Casino.gamelogic.classes;

import java.util.ArrayList;
import java.util.List;
import com.Casino.gamelogic.interfaces.Player;

public abstract class PlayerClass implements Player {
    private String name;
    private int balance;
    private List<ChipClass>[] chips = new List[4];

    public PlayerClass(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

}