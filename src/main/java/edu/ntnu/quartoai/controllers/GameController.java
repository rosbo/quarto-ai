package edu.ntnu.quartoai.controllers;

import core.Action;
import core.Board;
import core.Piece;
import core.Set;
import edu.ntnu.quartoai.controllers.players.PlayerController;
import edu.ntnu.quartoai.models.Game;
import edu.ntnu.quartoai.utils.Logger;

import javax.inject.Inject;

public class GameController {

    private Game game;
    private final Logger logger;

    @Inject
    public GameController(Logger logger) {
        this.logger = logger;
    }

    public void playGame(Game game) {
        this.game = game;
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
            playNewRound();
            numberOfRounds++;
            this.game.swapPlayers();
        }
    }

    public void playNewRound() {
        PlayerController playerWhoChooseThePiece = this.game.getPlayers().get(0);
        PlayerController playerWhoMoves = this.game.getPlayers().get(1);

        Board board = this.game.getBoard();
        Set set = this.game.getSet();
        Piece pieceChosen = playerWhoChooseThePiece.choosePieceToGive(this.game);

        logger.log(playerWhoChooseThePiece.toString() + " chooses " + pieceChosen.toString());
        set.remove(pieceChosen);
        Action actionChosen = playerWhoMoves.chooseNextAction(this.game, pieceChosen);
        if (!board.isEmpty(actionChosen.x, actionChosen.y)) {
            try {
                throw new Exception("Wrong position");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        board.setPiece(pieceChosen, actionChosen.x, actionChosen.y);

        logger.log(playerWhoMoves.toString() + " moves it in " + actionChosen.x + "," + actionChosen.y);
        logger.log(board.toString());
    }
}
