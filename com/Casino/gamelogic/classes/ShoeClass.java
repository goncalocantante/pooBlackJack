package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.Shoe;
import com.Casino.gamelogic.classes.Deck;
import com.Casino.gamelogic.enumerations.*;
import com.Casino.gamelogic.classes.Card;

import java.util.ArrayList;
import java.util.Collections;

import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class ShoeClass implements Shoe {

    ArrayList<Card> cards = new ArrayList<Card>();

    public ShoeClass(int ndecks) {
        this.createShoe(ndecks);
    }

    public ShoeClass() {
        this.createShoeFromFile();
    }

    /**
     * Creates the deck with the specified number of decks
     * 
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

    public void createShoeFromFile() {
        Rank rank;
        Suit suit;

        File file = new File("shoe-file.txt");
        Scanner myReader = new Scanner(file);

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);

        }
        myReader.close();

        // criar carta
        // Card cardToAdd = new Card(rank, suit, false);
        // adicionar carta
        // this.cards.add(cardToAdd);

    }

    public Rank returnRank(String rank) {

        switch (rank) {
            case "2":
                return Rank.TWO;
                break;
            case "3":
                return Rank.THREE;
                break;
            case "4":
                return Rank.FOUR;
                break;
            case "5":
                return Rank.FIVE;
                break;
            case "6":
                return Rank.SIX;
                break;
            case "7":
                return Rank.SEVEN;
                break;
            case "8":
                return Rank.EIGHT;
                break;
            case "9":
                return Rank.NINE;
                break;
            case "10":
                return Rank.TEN;
                break;
            case "J":
                return Rank.JACK;
                break;
            case "K":
                return Rank.KING;
                break;
            case "Q":
                return Rank.QUEEN;
                break;
            case "A":
                return Rank.ACE;
                break;
            default:

        }

    }

    /**
     * Shuffles the cards in the shoe
     */
    @Override
    public void shuffle() {
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
     * 
     * Useful when reshuffling
     */
    @Override
    public void moveAllToShoe(ArrayList<Card> cardsToAdd) {
        this.cards.addAll(cardsToAdd);
        return;
    }

    /**
     * Gets the size of the shoe
     */
    @Override
    public int getShoeSize() {
        return this.cards.size();
    }

    /**
     * Prints all cards in the shoe
     */
    public String toString() {
        String cardListOutput = "";
        for (Card aCard : this.cards) {
            cardListOutput += aCard.toString() + "\n";
        }
        return cardListOutput;
    }
}
