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
//        Random rnd = new Random();
        // double eval = rnd.nextDouble();
        double eval = 0.0;
        List<Piece[]> pieceGroups = group(board);
        for (Piece[] pieceGroup : pieceGroups) {

            if (pieceGroup[0] != null && pieceGroup[1] != null) {
                eval += commonAttributes(pieceGroup[0],pieceGroup[1]);
            }
            if (pieceGroup[0] != null && pieceGroup[2] != null) {
                eval += commonAttributes(pieceGroup[0],pieceGroup[2]);
            }
            if (pieceGroup[1] != null && pieceGroup[2] != null) {
                eval += commonAttributes(pieceGroup[1],pieceGroup[2]);
            }
            if (pieceGroup[0] != null && pieceGroup[3] != null) {
                eval += commonAttributes(pieceGroup[0],pieceGroup[3]);
            }
            if (pieceGroup[1] != null && pieceGroup[3] != null) {
                eval += commonAttributes(pieceGroup[1],pieceGroup[3]);
            }
            if (pieceGroup[2] != null && pieceGroup[3] != null) {
                eval += commonAttributes(pieceGroup[2],pieceGroup[3]);
            }
        }
        
        eval *= 10;
        eval += evaluatePosition(state);
        return eval;
    }
    
    private double evaluatePosition(State state){
        int[] positionChosen = state.getPositionChosen();
        int x = positionChosen[0];
        int y = positionChosen[1];
        double[][] positionValues = {{15,10,10,15},{10,20,20,10},{10,20,20,10},{15,10,10,15}};
        return positionValues[x][y];
    }

    private int commonAttributes(Piece piece1, Piece piece2) {
        int numberOfCommonAttributes = 0;
        if (piece1 != null && piece2 != null) {
            boolean[] attr1 = piece1.getAttributes();
            boolean[] attr2 = piece2.getAttributes();
            for(int i = 0; i< 4; i++){
                if(attr1[i]==attr2[i]){
                    numberOfCommonAttributes++;
                }
            }
        }
        return numberOfCommonAttributes;
    }

    private boolean rowComplete(ArrayList<Piece> row) {
        for (Piece piece : row) {
            if (piece == null) {
                return false;
            }
        }
        for (int i = 0; i < 4; i++) {
            if ((row.get(0).getAttributes()[i] == row.get(1).getAttributes()[i])
                            && (row.get(1).getAttributes()[i] == row.get(2).getAttributes()[i])
                            && (row.get(2).getAttributes()[i] == row.get(3).getAttributes()[i])) {
                return true;
            }
        }
        return false;
    }

    private List<Piece[]> group(Board board) {
        Piece[][] boardAsArray = board.getBoard();
        List<Piece[]> pieceArray = new ArrayList<Piece[]>();
        List<Piece> pieces = new ArrayList<Piece>();

        for (int i = 0; i < 4; i++) {// row
            Piece[] tmpPieces = { boardAsArray[i][0], boardAsArray[i][1], boardAsArray[i][2], boardAsArray[i][3] };
            // pieceArray.add(Arrays.);
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
