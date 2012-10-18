package edu.ntnu.quartoai.controllers.players;

import com.google.inject.assistedinject.Assisted;
import edu.ntnu.quartoai.models.Action;
import edu.ntnu.quartoai.models.Board;
import edu.ntnu.quartoai.models.Piece;
import edu.ntnu.quartoai.models.Set;
import edu.ntnu.quartoai.models.Game;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

public class NovicePlayerController extends PlayerController {

    @Inject
    public NovicePlayerController(@Assisted("number") Integer number) {
        super(number, "novice");
    }

    @Override
    public Piece choosePieceToGive(Game game) {
        Board board = game.getBoard();
        Set set = game.getSet();
        Piece pieceToGive = null;
        int x, y;
        List<int[]> freePositions = board.getFreePositions();
        List<Piece> piecesLeft = set.getPieces();
        Board boardCopied = board.copy();
        for (Piece piece : piecesLeft) {
            for (int[] position : freePositions) {
                x = position[0];
                y = position[1];
                boardCopied.setPiece(piece, x, y);
                if (!boardCopied.gameOver()) {
                    pieceToGive = piece;
                    break;
                }
                boardCopied.remove(x, y);
            }
        }
        if (pieceToGive == null) {// random choice
            Random randomGenerator = new Random();
            int size = piecesLeft.size();
            pieceToGive = piecesLeft.get(randomGenerator.nextInt(size));
        }
        return pieceToGive;
    }

    @Override
    public Action chooseNextAction(Game game, Piece piece) {
        Board board = game.getBoard();
        Action nextAction = null;
        int x, y;
        List<int[]> freePositions = board.getFreePositions();
        Board boardCopied = board.copy();

        for (int[] position : freePositions) {
            x = position[0];
            y = position[1];
            boardCopied.setPiece(piece, x, y);
            if (boardCopied.gameOver()) {
                nextAction = new Action(piece, x, y);
                break;
            }
            boardCopied.remove(x, y);
        }

        if (nextAction == null) {// random choice
            Random randomGenerator = new Random();
            int size = freePositions.size();
            int[] randomPosition = freePositions.get(randomGenerator.nextInt(size));
            nextAction = new Action(piece, randomPosition[0], randomPosition[1]);
        }
        return nextAction;
    }

}
