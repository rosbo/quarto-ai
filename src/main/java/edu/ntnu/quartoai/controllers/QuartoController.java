package edu.ntnu.quartoai.controllers;

import com.google.inject.Inject;
import edu.ntnu.quartoai.controllers.players.PlayerController;
import edu.ntnu.quartoai.models.Game;

import java.util.ArrayList;
import java.util.List;

public class QuartoController {

    private final GameController gameController;
    private List<PlayerController> players = new ArrayList<PlayerController>();

    @Inject
    public QuartoController(final GameController gameController) {
        this.gameController = gameController;
    }

    public void play(PlayerController player1, PlayerController player2, int numberOfGames) {
        players.add(player1);
        players.add(player2);
        startGame(numberOfGames);
    }

    private void startGame(int numberOfGames) {
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
                results[winner.getNumberOfThePlayer() - 1]++;
            }
        }

        // printing the results
        System.out.println("******* results *******");
        System.out.println(results[0] + "\t: 1. player");
        System.out.println(results[1] + "\t: 2. player");
        System.out.println(results[2] + "\t: ties");
    }
}
