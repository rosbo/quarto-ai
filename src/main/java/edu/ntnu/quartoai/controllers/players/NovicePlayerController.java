package edu.ntnu.quartoai.controllers.players;

import java.util.List;
import java.util.Random;

import core.Action;
import core.Board;
import core.Piece;
import core.Set;

public class NovicePlayerController extends PlayerController {

    public NovicePlayerController(int number) {
        super(number);
    }

    @Override
    public Piece choosePieceToGive(Board board, Set set) {
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
    public Action chooseNextAction(Board board, Piece piece) {
        Action nextAction = null;
        int x, y;
        List<int[]> freePositions = board.getFreePositions();
        Board boardCopied = board.copy();

        for (int[] position : freePositions) {
            x = position[0];
            y = position[1];
            boardCopied.setPiece(piece, x, y);
            if (boardCopied.gameOver()) {
                nextAction = new Action(piece,x,y);
                break;
            }
            boardCopied.remove(x, y);
        }

        if (nextAction == null) {// random choice
            Random randomGenerator = new Random();
            int size = freePositions.size();
            int[] randomPosition = freePositions.get(randomGenerator.nextInt(size));
            nextAction = new Action(piece,randomPosition[0],randomPosition[1]);
        }
        return nextAction;
    }

    @Override
    public String getBehaviour() {
        return "novice";
    }

}
