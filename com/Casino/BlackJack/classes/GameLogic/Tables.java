package com.Casino.BlackJack.classes.GameLogic;

/**
 * Contains the tables containing the actions of the basic strategy in blackjack
 */
public class Tables {

    /**
     * Creates hard table
     * @return hard: hard table
     */
    public char [][] createHardTable() {
        char [][] hard = {
                {'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H'},
                {'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H'},
                {'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H'},
                {'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H'},
                {'H', 'D', 'D', 'D', 'D', 'H', 'H', 'H', 'H', 'H'},
                {'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'H', 'H'},
                {'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'H'},
                {'H', 'H', 'S', 'S', 'S', 'H', 'H', 'H', 'H', 'H'},
                {'S', 'S', 'S', 'S', 'S', 'H', 'H', 'H', 'H', 'H'},
                {'S', 'S', 'S', 'S', 'S', 'H', 'H', 'H', 'H', 'H'},
                {'S', 'S', 'S', 'S', 'S', 'H', 'H', 'H', 'R', 'H'},
                {'S', 'S', 'S', 'S', 'S', 'H', 'H', 'R', 'R', 'R'},
                {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
                {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
                {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
                {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
                {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
        };
        return hard;

    }

    /**
     * Creates soft table
     * @return soft: soft table
     */
    public char [][] createSoftTable() {
        char [][] soft = {
                {'H', 'H', 'H', 'D', 'D', 'H', 'H', 'H', 'H', 'H'},
                {'H', 'H', 'H', 'D', 'D', 'H', 'H', 'H', 'H', 'H'},
                {'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H'},
                {'H', 'H', 'D', 'D', 'D', 'H', 'H', 'H', 'H', 'H'},
                {'H', 'D', 'D', 'D', 'D', 'H', 'H', 'H', 'H', 'H'},
                {'S', 'd', 'd', 'd', 'd', 'S', 'S', 'H', 'H', 'H'},
                {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
                {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
                {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
        };
        return soft;

    }

    /**
     * Creates pair
     * @return pair: pair table
     */
    public char [][] createPairTable() {
        char [][] pair = {
                {'H', 'H', 'P', 'P', 'P', 'P', 'H', 'H', 'H', 'H'},
                {'H', 'H', 'P', 'P', 'P', 'P', 'H', 'H', 'H', 'H'},
                {'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H', 'H'},
                {'D', 'D', 'D', 'D', 'D', 'D', 'D', 'D', 'H', 'H'},
                {'H', 'P', 'P', 'P', 'P', 'H', 'H', 'H', 'H', 'H'},
                {'P', 'P', 'P', 'P', 'P', 'P', 'H', 'H', 'H', 'H'},
                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                {'P', 'P', 'P', 'P', 'P', 'S', 'P', 'P', 'S', 'S'},
                {'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S', 'S'},
                {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
        };
        return pair;

    }


    /**
     * Gets the appropriate action from the appropriate table according to table dimensions
     * @param tableType: type of table (1 for pair table, 2 for hard table and 3 for soft table)
     * @param playerCards: player's hand value
     * @param dealerCard: dealer's face up card value
     * @return action: Command according to basic strategy
     */
    public String getAction (int tableType, int playerCards, int dealerCard){
        int column;
        int line;
        char action = 'i';

        if (tableType == 1){
            column = playerCards/2 - 2;
            line = dealerCard - 2;
            action = createPairTable()[column][line];
        }
        else if (tableType == 2){
            column = playerCards - 5;
            line = dealerCard - 2;
            action = createHardTable()[column][line];
        }
        else if (tableType == 3) {
            column = playerCards - 13;
            line = dealerCard - 2;
            action = createSoftTable()[column][line];
        }
        return String.valueOf(action);
    }

}