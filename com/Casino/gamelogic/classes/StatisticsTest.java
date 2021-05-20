package com.Casino.gamelogic.classes;

public class StatisticsTest {


    public static void main(String[] args) {
        StatisticsTest demo = new StatisticsTest();

        demo.statistics();


    }

    public void statistics(){

        System.out.format("%-10s%-10s%-10s\n", "BJ", "P/D", "N1/N2");
        System.out.format("%-10s%-10s%-10s\n", "Win", "", "N3");
        System.out.format("%-10s%-10s%-10s\n", "Lose", "", "N4");
        System.out.format("%-10s%-10s%-10s\n", "Balance", "", "N6(n7%)");


    }
}
