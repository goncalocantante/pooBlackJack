package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.Chip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChipClass implements Chip {

    private int value;
    private String color;

    /**
     * Constructor that sets the value and color of the chip
     * 
     * @param value: value of the chip
     * @param color: color of the chip
     */
    public ChipClass(int value, String color) {
        this.value = value;
        this.color = color;
    }

    /**
     * Returns the value of the chip
     * 
     * @return Value: value of the chip
     */
    @Override
    public int getValue() {
        return this.value;
    }

    /**
     * Returns the color of the chip
     * 
     * @return Color: color of the chip
     */
    @Override
    public String getColor() {
        return this.color;
    }

    /**
     * Creates the desired number of chips with the desired color
     * 
     * @param color:  desired color of the chips
     * @param amount: amount of chips to create
     * @return chips: List of chips containing the created chips
     */
    public static List<ChipClass> createChips(String color, int amount) {
        ChipClass buff = new ChipClass(0, null);

        if (amount < 1) {
            return null;
        }

        switch (color) {
            case "WHITE":
                buff.value = 1;
                buff.color = "WHITE";
                return Collections.nCopies(amount, buff);
            case "RED":
                buff.value = 5;
                buff.color = "RED";
                return Collections.nCopies(amount, buff);
            case "GREEN":
                buff.value = 25;
                buff.color = "GREEN";
                return Collections.nCopies(amount, buff);
            case "BLACK":
                buff.value = 100;
                buff.color = "BLACK";
                return Collections.nCopies(amount, buff);
            default:
                return null;
        }
    }

    /**
     * Converts the input value to chips following these rules in a way that you get
     * a balanced set of chips
     * 
     * @param amount: amount of $ to convert to chips
     * @return chips: size 4 array of Lists of ChipClass objects, each row
     *         containing the white, red, green and black chips respectively
     */
    public static List<ChipClass>[] convertToChips(int amount) {
        List<ChipClass>[] buff = new List[4];
        for (List<ChipClass> i : buff) {
            i = null;
        }
        if (amount < 15) {
            buff[0] = ChipClass.createChips("WHITE", amount);
            return buff;
        } else if (amount < 50) {
            buff[0] = ChipClass.createChips("WHITE", (10 + amount % 5));
            buff[1] = ChipClass.createChips("RED", (amount - 10) / 5);
            return buff;
        } else if (amount <= 200) {
            buff[0] = ChipClass.createChips("WHITE", (10 + amount % 5));
            buff[1] = ChipClass.createChips("RED", (3 + (amount % 25) / 5));
            buff[2] = ChipClass.createChips("GREEN", (amount - 25) / 25);
            return buff;
        } else {
            buff[0] = ChipClass.createChips("WHITE", (10 + amount % 5));
            buff[1] = ChipClass.createChips("RED", (8 + (amount % 25) / 5));
            buff[2] = ChipClass.createChips("GREEN", (2 + (amount % 100) / 25));
            buff[3] = ChipClass.createChips("GREEN", (amount - 100) / 100);
            return buff;
        }
    }

    /**
     * Só mm para testar a função do convertToChips
     * 
     * @param args
     */
    public static void main(String[] args) {
        List<ChipClass>[] buff = new List[4];
        int a = 0;
        int b = 0;

        buff = ChipClass.convertToChips(273);

        for (List<ChipClass> i : buff) {
            for (ChipClass j : buff[a]) {
                b++;
            }
            System.out.println(b);
            a++;
            b = 0;
        }
    }
}