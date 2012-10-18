package edu.ntnu.quartoai.controllers;

import com.google.inject.Inject;
import edu.ntnu.quartoai.controllers.players.PlayerController;
import edu.ntnu.quartoai.models.Game;
import edu.ntnu.quartoai.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class QuartoController {

    private final GameController gameController;
    private final Logger logger;
    private List<PlayerController> players = new ArrayList<PlayerController>();

    @Inject
    public QuartoController(final GameController gameController, final Logger logger) {
        this.gameController = gameController;
        this.logger = logger;
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
                logger.log("Game #" + i + " finished with a tie");
            } else {
                logger.log("Winner of game #" + i + " is " + winner.toString());
                results[winner.getNumberOfThePlayer() - 1]++;
            }
        }

        // Printing the results
        logger.log("******* Results *******");
        PlayerController p1 = players.get(0);
        PlayerController p2 = players.get(1);
        logger.log(results[p1.getNumberOfThePlayer() - 1] + "\t: " + p1);
        logger.log(results[p2.getNumberOfThePlayer() - 1] + "\t: "+ p2);
        logger.log(results[2] + "\t: ties");
    }
}
