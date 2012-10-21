package edu.ntnu.quartoai.minimax;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.ntnu.quartoai.models.Board;
import edu.ntnu.quartoai.models.Piece;

public class StateEvaluator {

    /*
     * Returns the utility value of a final state *
     */
    // public double utilityValue(State state, int numberOfThePlayer) {
    // Board board = state.getBoard();
    //
    // boolean samePlayer = (numberOfThePlayer % 2) ==
    // state.getNumberOfThePlayer();
    // System.out.println(samePlayer + "Number of the player is " +
    // (numberOfThePlayer % 2)
    // + "while the state player is " + state.getNumberOfThePlayer());
    // if (board.gameOver() && samePlayer) {
    // return Double.MAX_VALUE; // you are playing this state and winning
    // // eval = 10;
    // // System.out.println("MAX VALUE");
    // }
    // if (board.gameOver() && !samePlayer) {
    // return Double.MIN_VALUE; // the opponent is playing this state and
    // // winning
    // // eval = 10;
    // // System.out.println("MAX VALUE");
    // }
    // return 1; //tie
    // }

    public double utilityValue(State state, int numberOfThePlayer) {
        Board board = state.getBoard();

        if (board.gameOver()) {
            return Double.MAX_VALUE; // you are playing this state and winning
            // eval = 10;
            // System.out.println("MAX VALUE");
        }

        return 1; // tie
    }

    /*
     * Evaluate an intermediate state
     */
    public double evaluate(State state, int numberOfThePlayer) {
        Board board = state.getBoard();
        Random rnd = new Random();
        double eval = rnd.nextDouble();

        List<Piece[]> pieceGroups = group(board);
        for (Piece[] pieceGroup : pieceGroups) {
            for (Piece piece : pieceGroup) {
                if (piece != null) {
                    piece.getAttributes();
                }
            }
        }
        
        return eval;
    }

    private List<Piece[]> group(Board board) {
        Piece[][] boardAsArray = board.getBoard();
        List<Piece[]> pieces = new ArrayList<Piece[]>();

        for (int i = 0; i < 4; i++) {
            Piece[] tmpPieces = { boardAsArray[i][0], boardAsArray[i][1], boardAsArray[i][2], boardAsArray[i][3] };
            pieces.add(tmpPieces);
        }
        for (int j = 0; j < 4; j++) {
            Piece[] tmpPieces = { boardAsArray[0][j], boardAsArray[1][j], boardAsArray[2][j], boardAsArray[3][j] };
            pieces.add(tmpPieces);
        }
        Piece[] tmpPieces = { boardAsArray[0][0], boardAsArray[1][1], boardAsArray[2][2], boardAsArray[3][3] };
        pieces.add(tmpPieces);
        Piece[] tmpPiecesDiagonal = { boardAsArray[0][3], boardAsArray[1][2], boardAsArray[2][1], boardAsArray[3][0] };
        pieces.add(tmpPiecesDiagonal);
        return pieces;

    }

}
