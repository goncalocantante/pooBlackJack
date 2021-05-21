package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.enumerations.Rank;
import com.Casino.gamelogic.interfaces.Mode;

import java.util.Locale;
import java.util.Scanner;

public class SimulationMode implements Mode {

    String[] args;
//    ShoeClass shoe;
    Game game;
    Scanner scanner;
    int previousBet = 0;
    boolean betOrDeal = true;

    public SimulationMode(String[] arguments) {
        args = arguments;
        scanner = new Scanner(System.in);
    }

    public void InitializeShoeAndParameters(Game game) {
        this.game = game;
        // Initialized as true and set to false if parameters aren't correct
        boolean check = true;
        int minBet;
        int maxBet;
        int balance = 0;
        int shoe;
        int shuffle;
        int nShuffle;
        String Basic;
        String BasicAce;
        String HiLo;
        String HiLoAce;


        // If there arent's 6 parameters
        if (args.length != 8) {
            check = false;
            System.out.println("andre");
        }
        // If parameters aren't ints (except for the mode parameter)
        for (int i = 0; i < args.length -2; i++) {
            if (!args[0].matches(".*\\D.*")) {
                check = false;
            }
        }

        minBet = Integer.parseInt(args[1]);
        maxBet = Integer.parseInt(args[2]);
        balance = Integer.parseInt(args[3]);
        shoe = Integer.parseInt(args[4]);
        shuffle = Integer.parseInt(args[5]);
        nShuffle = Integer.parseInt(args[6]);
        Basic = "BS";
        BasicAce = "BS-AF";
        HiLo = "HL";
        HiLoAce = "HL-AF";


        // Check if min-bet and max-bet parameters are correct
        if (minBet < 1 || maxBet < 10 * minBet || maxBet > 20 * maxBet || balance < 50 * minBet) {
            check = false;
        }

        // Check if shoe and shuffle parameters are correct
        if (shoe < 4 || shoe > 8 || shuffle < 10 || shuffle > 100) {
            check = false;
        }
        if (!args[7].equals(Basic) && !args[7].equals(BasicAce) && !args[7].equals(HiLo) && !args[7].equals(HiLoAce)) {
            check = false;
        }
        if (!check) {
            scanner.close();
            System.exit(0);
        }
        // quit

        // Sets parameters in game object
        this.game.setParameters(minBet, maxBet, balance, shuffle, nShuffle);
        // Create shoe
        this.game.setShoe(new ShoeClass(shoe));
    }

    @Override
    public String getCommand(int nHand) {

        String action = "";

        int cardsInShoe = Integer.parseInt(args[5])*52;
        //if what we want is the bet string
        //check if the betting strategy is Ace Five or Standard Bet Strategy and return
        if (this.game.getPlayer().getHand(0).isEmpty() && betOrDeal){
            if (args[7].contains("AF")){
                betOrDeal = false;
                return this.game.aceFiveBet();
            }
            else {
                betOrDeal = false;
                return this.game.StandardBetStrategy();
            }
        } else if (this.game.getPlayer().getHand(0).isEmpty() && !betOrDeal){
            return "d";
        }
        betOrDeal = true;
        //checks if the strategy is Hi Lo
        //If it is, bet accordingly
        //if it isn't or if case doesn't exist, then basic
        if (args[7].contains("HL")){
            action = this.game.HiLo(nHand, cardsInShoe);
            if (!action.equals("basic")){
                return action;
            }
        }
        if (args[7].contains("BS") || action.equals("basic")){
            action = this.game.basicStrategy((nHand));
            return action;
        }

        return "Not correct betting strategy";
    }

}