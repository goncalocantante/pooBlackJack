package com.Casino.BlackJack.enumerations;

/**
 * Enumeration containing the ranks of the cards
 */
public enum Rank {
    TWO(2), THREE(3), FOUR(4),
    FIVE(5), SIX(6), SEVEN(7),
    EIGHT(8), NINE(9), TEN(10),
    JACK(10), KING(10), QUEEN(10), ACE(11);


    private int val;

    /**
     * Constructor that initializes rank
     * @param val
     */
    Rank(int val) {
        this.val = val;
    }

    /**
     * Returns the value of the rank
     * @return val: value of the rank
     */
    public int getVal() {
        return val;
    }

    /**
     * Returns the rank according relative to the specified character
     * @param rank: specified character
     * @return rank: rank
     */
    public Rank getRank(String rank) {
        switch (rank){
            case "2":
                return Rank.valueOf("TWO");
            case "3":
                return Rank.valueOf("THREE");
            case "4":
                return Rank.valueOf("FOUR");
            case "5":
                return Rank.valueOf("FIVE");
            case "6":
                return Rank.valueOf("SIX");
            case "7":
                return Rank.valueOf("SEVEN");
            case "8":
                return Rank.valueOf("EIGHT");
            case "9":
                return Rank.valueOf("NINE");
            case "10":
                return Rank.valueOf("TEN");
            case "J":
                return Rank.valueOf("JACK");
            case "K":
                return Rank.valueOf("KING");
            case "Q":
                return Rank.valueOf("QUEEN");
            case "A":
                return Rank.valueOf("ACE");
            default:
                return null;
        }
    }



}
