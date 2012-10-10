package edu.ntnu.quartoai.controllers.players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.google.inject.Inject;
import edu.ntnu.quartoai.models.Game;

public class QuartoController {

    private static final int numberOfGames = 10;
    private List<PlayerController> players = new ArrayList<PlayerController>();
    private final GameController gameController;
    private final PlayerFactory playerFactory;

    @Inject
    public QuartoController(final GameController gameController,final PlayerFactory playerFactory) {
        this.gameController = gameController;
        this.playerFactory = playerFactory;
    }

    public void play() {
        choosePlayers();
        startGame();
    }

    private void choosePlayers() {
        // TODO: change syso into console logger
        // TODO: implement a better command pattern
        System.out.println("WELCOME TO QUARTO GAME");
        System.out.println("\n Choose Player 1: Click 1 for human, 2 for random, 3 for novice, 4 for minmax3");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PlayerController player1 = null;
        PlayerController player2 = null;
        String userInputString = "";
        Integer userInput = null;
        boolean correct = false;
        while (!correct) {
            try {
                userInputString = br.readLine();               
                if (userInputString == null) {
                    System.out.println("Insert a value");
                }                
                userInput = new Integer(userInputString);
                player1 = playerFactory.getPlayer(userInput, 1);
                if (player1 == null) {
                    System.out.println("Wrong value. Insert again");
                } else {
                    player1.setNumberOfThePlayer(1);
                    players.add(player1);
                    System.out.println("You are choosing " + player1.toString());
                    correct = true;
                }
            } catch (IOException e) {
                System.out.println("IO error trying to read you input!");
                System.exit(1);
            }
        }
        System.out.println(player1.toString());
        System.out.println("\n Choose Player 2: Click 1 for human, 2 for random, 3 for novice, 4 for minmax3");

        userInput = null;
        correct = false;
        while (!correct) {
            try {
                userInputString = br.readLine();               
                if (userInputString == null) {
                    System.out.println("Insert a value");
                }                
                userInput = new Integer(userInputString);
                player2 = playerFactory.getPlayer(userInput, 2);
                if (player2 == null) {
                    System.out.println("Wrong value. Insert again");
                } else {
                    player2.setNumberOfThePlayer(2);
                    players.add(player2);
                    System.out.println("You are choosing " + player2.toString());
                    correct = true;
                }

            } catch (IOException e) {
                System.out.println("IO error trying to read you input!");
                System.exit(1);
            }
        }

        System.out.println(player1.toString());
        System.out.println(player2.toString());
    }

    private void startGame() {
        int[] results = new int[3];
        results[0] = 0;
        results[1] = 0;
        results[2] = 0;
        for (int i = 0; i < numberOfGames; i++) {
            Game game = new Game(players);            
            gameController.playGame(game);
            PlayerController winner = game.getWinner();
            if (winner == null) {
                //it's a tie
                results[2]++;
                System.out.println("Game #" + i + " finished with a tie");
            } else {
                System.out.println("Winner of game #" + i + " is " + winner.toString());
                results[winner.getNumberOfThePlayer() - 1]++; // we start
                                                              // indexing the
                                                              // players by one
            }
        }

        // printing the results
        System.out.println("******* results *******");
        System.out.println(results[0] + "\t: 1. player");
        System.out.println(results[1] + "\t: 2. player");
        System.out.println(results[2] + "\t: ties");
    }
}
