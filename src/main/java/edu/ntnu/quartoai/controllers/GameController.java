package edu.ntnu.quartoai.controllers;

import edu.ntnu.quartoai.models.Action;
import edu.ntnu.quartoai.models.Board;
import edu.ntnu.quartoai.models.Piece;
import edu.ntnu.quartoai.models.Set;
import edu.ntnu.quartoai.controllers.players.PlayerController;
import edu.ntnu.quartoai.models.Game;
import edu.ntnu.quartoai.utils.Logger;

import javax.inject.Inject;

public class GameController {

    private final Logger logger;

    @Inject
    public GameController(Logger logger) {
        this.logger = logger;
    }

    public void playGame(Game game) {
        int numberOfRounds = 1;
        while (true) {
            if (game.isOver()) {
                game.setWinner(game.getPlayers().get(0));// the first player
                // moved the piece
                break;
            }
            if (game.getSet().isEmpty()) {
                break;
            }
            logger.log("Playing Round # " + numberOfRounds);
            playNewRound(game);
            numberOfRounds++;
            game.swapPlayers();
        }
    }

    private void playNewRound(Game game) {
        PlayerController playerWhoChooseThePiece = game.getPlayers().get(0);
        PlayerController playerWhoMoves = game.getPlayers().get(1);

        Board board = game.getBoard();
        Set set = game.getSet();
        Piece pieceChosen = playerWhoChooseThePiece.choosePieceToGive(game);

        logger.log(playerWhoChooseThePiece.toString() + " chooses " + pieceChosen.toString());
        if (!playerWhoChooseThePiece.isHuman()) {
            logger.logProtocol(pieceChosen.toString());
        }

        set.remove(pieceChosen);
        Action actionChosen = playerWhoMoves.chooseNextAction(game, pieceChosen);
        if (!board.isEmpty(actionChosen.x, actionChosen.y)) {
            throw new IllegalArgumentException("Wrong position");
        }
        board.setPiece(pieceChosen, actionChosen.x, actionChosen.y);

        logger.log(playerWhoMoves.toString() + " moves it in " + actionChosen.x + "," + actionChosen.y);
        logger.log(board.toString());
        if (!playerWhoMoves.isHuman()) {
            logger.logProtocol(actionChosen.x + " " + actionChosen.y);
        }
    }
}
