package com.Casino.BlackJack.classes.GameLogic;

import com.Casino.BlackJack.interfaces.Shoe;
import com.Casino.BlackJack.enumerations.*;

import java.util.ArrayList;
import java.util.Collections;

import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

/**
 * Shoes that can be created from a number of standard decks or read from a file
 */
public class ShoeClass implements Shoe {

    //List of cards in the shoe
    ArrayList<Card> cards = new ArrayList<Card>();

    /**
     *Constructor that creates shoe with the specified number of decks
     * @param ndecks: specified number of decks
     */
    public ShoeClass(int ndecks) {
        this.createShoe(ndecks);
    }

    /**
     * Constructos that creates the shoe, reading it from the specified file
     * @param filePath: specified file
     */
    public ShoeClass(String filePath) {
        this.createShoeFromFile(filePath);
    }

    /**
     * Creates the deck with the specified number of standard decks
     * @param ndecks: number of decks contained in the shoe
     */
    @Override
    public void createShoe(int ndecks) {
        Deck deck;
        for (int i = 0; i < ndecks; i++) {
            deck = new Deck();
            this.cards.addAll(deck.getDeck());
        }
    }

    /**
     * Creates the shoe from file
     * @param fileName: name of the file containing the list of cards
     */
    @Override
    public void createShoeFromFile(String fileName) {

        File file = new File(fileName);
        Scanner myReader = null;
        try {
            myReader = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (myReader.hasNextLine()) {
            String rankStr = "";
            Rank rank;
            rank = Rank.ACE;
            String suit = "";

            // The cards are separated by spaces in the file
            // Splits next line of the file into chunks containing each Card of the Shoe
            // Iterates over each card
            for (String data : myReader.nextLine().split(" "))
            {
                int len = data.length();
                if(data.length() == 3){
                    rankStr = "";
                    suit = "";
                    rankStr += String.valueOf(data.charAt(0));
                    rankStr += String.valueOf(data.charAt(1));
                    //1 char suit
                    suit = String.valueOf(data.charAt(2));
                    this.cards.add(new Card( Suit.valueOf(suit), rank.getRank(rankStr), true));
                }else if(data.length() == 2){
                    rankStr = "";
                    suit = "";
                    rankStr = String.valueOf(data.charAt(0));
                    suit = String.valueOf(data.charAt(1));
                    this.cards.add(new Card(Suit.valueOf(suit), rank.getRank(rankStr), true));
                }else if(data.length() == 0){
                    return;
                }else{
                    System.out.println("Error reading card.");
                    System.exit(0);
                }
            }
        }
        myReader.close();
    }

    /**
     * Gets the size of the shoe
     */
    @Override
    public int getShoeSize() {
        return this.cards.size();
    }

    /**
     * Shuffles the cards in the shoe
     */
    @Override
    public void shuffle() {
        System.out.println("shuffling the shoe...");
        Collections.shuffle(this.cards);
    }

    /**
     * Removes a card (index i) from the shoe
     */
    @Override
    public void removeCard(int i) {
        this.cards.remove(i);
    }

    /**
     * Gets the card from the shoe without removing it
     */
    @Override
    public Card getCard(int i) {
        return this.cards.get(i);
    }

    /**
     * Receives list of cards and adds them to shoe
     * Useful when reshuffling
     */
    @Override
    public void moveAllToShoe(ArrayList<Card> cardsToAdd) {
        this.cards.addAll(cardsToAdd);
    }

    /**
     * Prints all cards in the shoe
     */
    @Override
    public String toString() {
        String cardListOutput = "";
        for (Card aCard : this.cards) {
            cardListOutput += aCard.toString() + "\n";
        }
        return cardListOutput;
    }
}
