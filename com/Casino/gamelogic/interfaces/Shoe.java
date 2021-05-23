package com.Casino.gamelogic.interfaces;

import com.Casino.gamelogic.classes.Card;
import com.Casino.gamelogic.classes.Deck;
import com.Casino.gamelogic.enumerations.Rank;
import com.Casino.gamelogic.enumerations.Suit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Interface for the shoe that will be used to play
 */
public interface Shoe {

    public void createShoe(int ndecks);

    public void createShoeFromFile(String fileName);

    public void shuffle();

    public void removeCard(int i);

    public Card getCard(int i);

    public void moveAllToShoe(ArrayList<Card> cardsToAdd);

    public int getShoeSize();

    public String toString();
}
