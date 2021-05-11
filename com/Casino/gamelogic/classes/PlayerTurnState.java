package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.GameState;
import com.Casino.gamelogic.classes.Game;

import java.util.Scanner;

public class PlayerTurnState implements GameState {

    private Game game;

    /**
     * Contructor to set the game of the state to the game being played
     * 
     * @param game: game being played
     */
    public PlayerTurnState(Game game) {
        this.game = game;
    }

    /*
     * @Override public void startState() { System.out.println("Dealing cards...");
     * this.game.dealCards();
     * 
     * char input = '0'; int nHand = 0; Scanner scanner = new Scanner(System.in);
     * 
     * /* while(input != '1') { input = scanner.next().charAt(0); switch (input) {
     * case 'd': input = '1'; break; case 'b': this.game.getPlayer().bet(100, 0);
     * break; default: System.out.println("Invalid Command"); } for (int j = 0; j <
     * this.game.getPlayer().getHands().get(0).getHandSize(); j++) {
     * System.out.println("Cards: " +
     * this.game.getPlayer().getHands().get(0).getCard(j)); }
     * System.out.println("Pile: " + this.game.getPiles());
     * System.out.println("Balance: " + this.game.getPlayer().getBalance()); } / }
     * 
     * @Override public void resolveState() { char input = '0'; int nHand = 0;
     * Scanner scanner = new Scanner(System.in);
     * 
     * for (Hand hand : this.game.getPlayer().getHands()) { while
     * (!hand.isHandClosed()) { for (int j = 0; j < hand.getHandSize(); j++) {
     * System.out.println(hand.getCard(j)); } input = scanner.next().charAt(0);
     * switch (input) { case 'h': this.game.getPlayer().hit(nHand); break; case 's':
     * this.game.getPlayer().stand(nHand); break; default:
     * System.out.println("Invalid Command"); } if (hand.isBust()) {
     * hand.closeHand(); } } nHand++; } }
     * 
     * @Override public void endState() {
     * game.setGameState(game.getDealerTurnState()); }
     */

    @Override
    public void initializeGame() {
        System.out.println("Game has already started, cannot initialize.");
    }

    @Override
    public void playerTurn() {
        // loop de side rules
        // loop do player
        System.out.println("Player is playing.");
        game.setGameState(game.getDealerTurnState());
    }

    @Override
    public void dealerTurn() {
        System.out.println("Not dealers turn, player is still playing.");
    }

    @Override
    public void finishRound() {
        return;
    }
}
