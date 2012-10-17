package edu.ntnu.quartoai.minimax;
import core.*;

public class StateEvaluator {
    
    public double evaluate(State state){
        Board board = state.getBoard();
        Set set = state.getSet();
        double eval = 0.0;
        if(board.gameOver()){
            eval = Double.MAX_VALUE;
        }
        return eval;
    }
}
