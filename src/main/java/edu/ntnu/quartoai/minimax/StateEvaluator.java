package edu.ntnu.quartoai.minimax;

import java.util.ArrayList;

import core.*;

public class StateEvaluator {

    public double evaluate(State state, int numberOfThePlayer) {
        Board board = state.getBoard();
        Set set = state.getSet();
        double eval = 0.0;
        boolean samePlayer = (numberOfThePlayer) == state.getNumberOfThePlayer();
        if (board.gameOver() && samePlayer) {
            eval = Double.MAX_VALUE; // you are playing this state and winning
        } else if (board.gameOver() && !samePlayer) {
            eval = Double.MIN_VALUE; // the opponent is playing this state and
                                     // winning
//        } else {
//            Piece[][] boardAsArray = board.getBoard();
//            int i, j, k;
//            
//            for (i = 0; i < 4; i++) {
//                
//                }
//            }
        }
        return eval;
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
}
