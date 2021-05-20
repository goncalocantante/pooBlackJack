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
        this.game.setParameters(minBet, maxBet, balance, shuffle);
        // Create shoe
        this.game.setShoe(new ShoeClass(shoe));
    }

    public void setPreviousBet (int lastBet) {
        this.previousBet = lastBet;
    }

    @Override
    public String getCommand(int nHand) {

        String Action;
        //if what we want is the bet string
        //check if the betting strategy is Ace Five or Standard Bet Strategy and return
        if (this.game.getPlayer().getHand(0).isEmpty() && betOrDeal){
            if (args[7].contains("AF")){
                betOrDeal = false;
                return aceFiveBet();
            }
            else {
                betOrDeal = false;
                return StandardBetStrategy();
            }
        } else if (this.game.getPlayer().getHand(0).isEmpty() && !betOrDeal){
            return "d";
        }
        betOrDeal = true;
        //checks if the strategy is Hi Lo
        //If it is, bet accordingly
        //if it isn't or if case doesn't exist, then basic
        if (args[7].contains("HL")){
            Action = HiLo(nHand);
            if (!Action.equals("basic")){
                return Action;
            }
        }
        if (args[7].contains("BS")){
            Action = basicStrategy((nHand));
            return Action;
        }

        return "Not correct betting strategy";
    }

    public String basicStrategy (int nHand) {

        Tables table = new Tables();

        String Action = "";
        int playerValue;
        int dealerValue;
        playerValue = this.game.getPlayer().getHand(nHand).handValue();
        dealerValue = this.game.getDealer().getCard(1).cardValue();

        if (this.game.getPlayer().getHand(nHand).canSplit()){
            Action = String.valueOf(table.getAction (1, playerValue, dealerValue));
        }
        else if (!this.game.getPlayer().getHand(nHand).isSoft()){
            Action = String.valueOf(table.getAction (2, playerValue, dealerValue));
        }
        else if (this.game.getPlayer().getHand(nHand).isSoft()){
            Action = String.valueOf(table.getAction (3, playerValue, dealerValue));
        }
        if (Action.equals("D")){
            if (this.game.getPlayer().canDouble(nHand)){
                Action = "2";
            } else {
                Action = "h";
            }
        }
        else if (Action.equals("d")){
            if (this.game.getPlayer().canDouble(nHand)){
                Action = "2";
            } else {
                Action = "s";
            }
        }
        else if (Action.equals("R")){
            if (this.game.getPlayer().canSurrender(nHand)){
                Action = "u";
            } else {
                Action = "s";
            }
        }
        Action = Action.toLowerCase(Locale.ROOT);
        return Action;
    }

    //bets certain amount according to Ace Five strategy
    public String aceFiveBet () {
        int minBet = Integer.parseInt(args[1]);
        int maxBet = Integer.parseInt(args[2]);
        int counter = 0;
        int aceFive = 0;
        int toBet;


        for (counter = 0; counter < this.game.getDiscardPile().size(); counter++){
            if (this.game.getDiscardPile().get(counter).equals(Rank.FIVE)) {
                aceFive++;
            } else if (this.game.getDiscardPile().get(counter).equals(Rank.ACE)) {
                aceFive--;
            }
        }

        toBet = previousBet*2;
        if (toBet > maxBet){
            toBet = maxBet;
        }

        if (aceFive <= 1){
            this.previousBet = minBet;
            return ("b " + minBet);
        }
        else {
            this.previousBet = toBet;
            return ("b " + toBet);
        }
    }

    //amount to bet when we are not using the Ave Five strategy
    public String StandardBetStrategy() {
        int minBet = Integer.parseInt(args[1]);
        int maxBet = Integer.parseInt(args[2]);
        int toBet;

        //if it is the first bet of the game, then use minBet
        if (game.getPlayer().getLastResult() == -5){
            this.previousBet = minBet;
            return ("b " + minBet);
        }

        //get next bet by seeing last results
        toBet = game.getPlayer().getLastResult()*minBet + previousBet;
        //if this exceeds maxBet or smaller than minBet, then set them as the true bet
        if (toBet < minBet){
            toBet = minBet;
        }
        else if (toBet > maxBet){
            toBet = maxBet;
        }
        this.previousBet = toBet;
        return ("b " + toBet);

    }


    public String HiLo(int nHand) {
        int runningCount = 0;
        int cardsDiscarded = 0;
        int decksRemaining = 0;
        int trueCount = 0;
        int cardsInShoe = Integer.parseInt(args[5])*52;
        String toReturn;

        for (cardsDiscarded = 0; cardsDiscarded < this.game.getDiscardPile().size(); cardsDiscarded++){
            switch (this.game.getDiscardPile().get(runningCount).getRank()){
                case TWO:
                case THREE:
                case FOUR:
                case FIVE:
                case SIX:
                    runningCount++;
                case TEN:
                case JACK:
                case KING:
                case QUEEN:
                case ACE:
                    runningCount--;
            }

        }

        decksRemaining = (cardsInShoe - cardsDiscarded)/52;
        trueCount = runningCount/decksRemaining;

        toReturn = fab4(nHand, trueCount);
        if (!toReturn.equals("basic")){
            return toReturn;
        }
        return (illustrious18(nHand, trueCount));
    }

    public String illustrious18 (int nHand, int trueCount){
        int playerHand = this.game.getPlayer().getHand(nHand).handValue();
        int dealerHand = this.game.getDealer().handValue();

        if (playerHand == 16 && dealerHand == 10){
            return standOrHit(trueCount, 0);
        } else if (playerHand == 15 && dealerHand == 10){
            return standOrHit(trueCount, 4);
        } else if (playerHand == 20 && dealerHand == 5){
            return splitOrStand(trueCount, 5);
        } else if (playerHand == 20 && dealerHand == 6){
            return splitOrStand(trueCount, 4);
        } else if (playerHand == 10 && dealerHand == 10){
            return doubleOrHit(trueCount, 4);
        } else if (playerHand == 12 && dealerHand == 3){
            return standOrHit(trueCount, 2);
        } else if (playerHand == 12 && dealerHand == 2){
            return standOrHit(trueCount, 3);
        } else if (playerHand == 11 && dealerHand == 11){
            return doubleOrHit(trueCount, 1);
        } else if (playerHand == 9 && dealerHand == 2){
            return doubleOrHit(trueCount, 1);
        } else if (playerHand == 10 && dealerHand == 11){
            return doubleOrHit(trueCount, 4);
        } else if (playerHand == 9 && dealerHand == 7){
            return doubleOrHit(trueCount, 3);
        } else if (playerHand == 16 && dealerHand == 9){
            return standOrHit(trueCount, 5);
        } else if (playerHand == 13 && dealerHand == 2){
            return standOrHit(trueCount, -1);
        } else if (playerHand == 12 && dealerHand == 4){
            return standOrHit(trueCount, 0);
        } else if (playerHand == 12 && dealerHand == 5){
            return standOrHit(trueCount, -2);
        } else if (playerHand == 12 && dealerHand == 6){
            return standOrHit(trueCount, -1);
        }else if (playerHand == 13 && dealerHand == 3){
            return standOrHit(trueCount, -2);
        }
        return "basic";
    }

    public String standOrHit (int trueCount, int indexNumber) {

        if (trueCount >= indexNumber){
            return "s";
        }
        else{
            return "h";
        }
    }

    public String splitOrStand (int trueCount, int indexNumber) {

        if (trueCount >= indexNumber){
            return "p";
        }
        else{
            return "s";
        }
    }

    public String doubleOrHit (int trueCount, int indexNumber) {

        if (trueCount >= indexNumber){
            return "2";
        }
        else{
            return "h";
        }
    }

    public String fab4 (int nHand, int trueCount){
        int playerHand = this.game.getPlayer().getHand(nHand).handValue();
        int dealerHand = this.game.getDealer().handValue();

        if (playerHand == 14 && dealerHand == 10){
            return surrenderOrBasic(trueCount, 3);
        } else if (playerHand == 15 && dealerHand == 9){
            return surrenderOrBasic(trueCount, 2);
        } else if (playerHand == 15 && dealerHand == 11){
            return surrenderOrBasic(trueCount, 1);
        } else if (playerHand == 15 && dealerHand == 10){
            if (trueCount <= 0 && trueCount >= 3){
                return "u";
            } else if (trueCount >= 4){
                return "s";
            }
            else {
                return "h";
            }
        }
        return "basic";

    }

    public String surrenderOrBasic (int trueCount, int indexNumber){

        if (trueCount >= indexNumber){
            return "u";
        }
        else{
            return "basic";
        }
    }
}