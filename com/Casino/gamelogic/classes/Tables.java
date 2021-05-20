package com.Casino.gamelogic.classes;


public class Tables {


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


    //table = 1 for pair, = 2 for hard and = 3 for soft
    public String getAction (int tableType, int playerCards, int dealerCard){
        int column;
        int line;
        char action = 'i';

        if (tableType == 1){
            column = playerCards - 2;
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


    public static void main (String[] args) {
        Tables table = new Tables();


        System.out.println((table.getAction(2, 9, 6)));

    }

}