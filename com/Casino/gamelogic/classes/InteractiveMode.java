package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.Mode;

import java.util.Scanner;

public class InteractiveMode implements Mode {

    String[] args;
    ShoeClass shoe;
    Game game;
    Scanner scanner;

    public InteractiveMode(String[] arguments){
        args = arguments;
        scanner = new Scanner(System.in);
    }

    public void InitializeShoeAndParameters(Game game) {
        this.game = game;
        boolean check = true;
        int minBet;
        int maxBet;
        int balance;
        int shoe;
        int shuffle;

        if(args.length != 6)
            check = false;

        for(int i=0; i<args.length; i++){
            if(!args[0].matches(".*\\d.*"))
                check = false;
        }
        minBet = Integer.parseInt(args[1]);
        maxBet = Integer.parseInt(args[2]);
        balance = Integer.parseInt(args[3]);
        shoe =  Integer.parseInt(args[4]);
        shuffle = Integer.parseInt(args[5]);

        if(minBet < 1 || maxBet < 10 * minBet || maxBet > 20 * maxBet || balance < 50 * minBet)
            check = false;

        if(shoe < 4 || shoe > 8 || shuffle < 10 || shuffle > 100)
            check = false;

        if(!check)
            //quit

        this.game.setParameters(minBet, maxBet, balance, shuffle);
        int nDecks = Integer.parseInt(args[4]);;
        this.game.setShoe(new ShoeClass(nDecks));


    }

    @Override
    public String getCommand() { return scanner.nextLine();}

}
