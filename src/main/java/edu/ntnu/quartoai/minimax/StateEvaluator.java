package edu.ntnu.quartoai.minimax;

import edu.ntnu.quartoai.models.Board;

public class StateEvaluator {

    public double evaluate(State state, int numberOfThePlayer) {
        Board board = state.getBoard();
        double eval = 0.0;
        boolean samePlayer = (numberOfThePlayer) == state.getNumberOfThePlayer();
        if (board.gameOver() && samePlayer) {
            eval = Double.MAX_VALUE; // you are playing this state and winning
        } else if (board.gameOver() && !samePlayer) {
            eval = Double.MIN_VALUE; // the opponent is playing this state and
            // winning
        }
        return eval;
    }
}
