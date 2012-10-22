package edu.ntnu.quartoai.minimax;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.ntnu.quartoai.models.Board;
import edu.ntnu.quartoai.models.Piece;

public class StateEvaluator {

    public double utilityValue(State state, int numberOfThePlayer, String player) {
        Board board = state.getBoard();
        if (board.gameOver() && player.equals("MAX")) {
            return Double.MAX_VALUE; // you are playing this state and winning
        } else if (board.gameOver() && player.equals("MIN")) {
            return Double.MIN_VALUE;
        }

        return 0; // tie
    }

    /*
     * Evaluate an intermediate state
     */
    public double evaluate(State state, int numberOfThePlayer) {
        Board board = state.getBoard();
        Piece pieceChosen = state.getPieceChosen();
        double eval = 0.0;
        List<Piece[]> pieceGroups = group(board);
        // checks how many common couples are there
        for (Piece[] pieceGroup : pieceGroups) {

            if (pieceGroup[0] != null && pieceGroup[1] != null) {
                eval += commonAttributes(pieceGroup[0], pieceGroup[1]);
            }
            if (pieceGroup[0] != null && pieceGroup[2] != null) {
                eval += commonAttributes(pieceGroup[0], pieceGroup[2]);
            }
            if (pieceGroup[1] != null && pieceGroup[2] != null) {
                eval += commonAttributes(pieceGroup[1], pieceGroup[2]);
            }
            if (pieceGroup[0] != null && pieceGroup[3] != null) {
                eval += commonAttributes(pieceGroup[0], pieceGroup[3]);
            }
            if (pieceGroup[1] != null && pieceGroup[3] != null) {
                eval += commonAttributes(pieceGroup[1], pieceGroup[3]);
            }
            if (pieceGroup[2] != null && pieceGroup[3] != null) {
                eval += commonAttributes(pieceGroup[2], pieceGroup[3]);
            }
        }
        eval *= 10;
        // checks the triplets
        for (Piece[] pieceGroup : pieceGroups) {
            boolean condition = pieceGroup[0] != null
                            && pieceGroup[1] != null
                            && pieceGroup[2] != null
                            && pieceChosen != null
                            && (pieceGroup[0].equals(pieceChosen) || pieceGroup[1].equals(pieceChosen) || pieceGroup[2]
                                            .equals(pieceChosen));
            boolean samePlayer = numberOfThePlayer == state.getNumberOfThePlayer();

            // be careful, in the next turn the opposite could win!
            if (condition && samePlayer) {
                eval -= 100;
            }
            // it's very good if the opposite fills a line of three this way,
            // you could win next turn
            if (condition && !samePlayer) {
                eval += 100;
            }

        }

        // Not all the position have the same importance
        eval += evaluatePosition(state);
        return eval;
    }

    public boolean shareAttribute(Piece[] pieces) {
        boolean result = false;
        for (int i = 0; i < pieces.length - 1; i++) {
            for (int j = i + 1; j < pieces.length; j++) {
                for (int k = 0; k < 4; k++) {
                    if (pieces[i].getAttributes()[k] == pieces[k].getAttributes()[k]) {
                        result = true;
                        break;
                    }
                }
                if (result) {
                    break;
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }

    /*
     * Not all position have the same importance corners > center > borders
     */
    private double evaluatePosition(State state) {
        int[] positionChosen = state.getPositionChosen();
        int x = positionChosen[0];
        int y = positionChosen[1];
        double[][] positionValues = { { 20, 10, 10, 20 }, { 10, 15, 15, 10 }, { 10, 15, 15, 10 }, { 20, 10, 10, 20 } };
        return positionValues[x][y];
    }

    private int commonAttributes(Piece piece1, Piece piece2) {
        int numberOfCommonAttributes = 0;
        if (piece1 != null && piece2 != null) {
            boolean[] attr1 = piece1.getAttributes();
            boolean[] attr2 = piece2.getAttributes();
            for (int i = 0; i < 4; i++) {
                if (attr1[i] == attr2[i]) {
                    numberOfCommonAttributes++;
                }
            }
        }
        return numberOfCommonAttributes;
    }

    private List<Piece[]> group(Board board) {
        Piece[][] boardAsArray = board.getBoard();
        List<Piece[]> pieceArray = new ArrayList<Piece[]>();
        List<Piece> pieces = new ArrayList<Piece>();

        for (int i = 0; i < 4; i++) {// row
            Piece[] tmpPieces = { boardAsArray[i][0], boardAsArray[i][1], boardAsArray[i][2], boardAsArray[i][3] };
        }
        for (int j = 0; j < 4; j++) {// coloumn
            Piece[] tmpPieces = { boardAsArray[0][j], boardAsArray[1][j], boardAsArray[2][j], boardAsArray[3][j] };
            pieceArray.add(tmpPieces);
        }
        Piece[] tmpPieces = { boardAsArray[0][0], boardAsArray[1][1], boardAsArray[2][2], boardAsArray[3][3] };
        pieceArray.add(tmpPieces);// diagonal
        Piece[] tmpPiecesDiagonal = { boardAsArray[0][3], boardAsArray[1][2], boardAsArray[2][1], boardAsArray[3][0] };
        pieceArray.add(tmpPiecesDiagonal);// codiagonal
        return pieceArray;

    }

}
