package edu.ntnu.quartoai.minimax;

import com.google.inject.Inject;
import edu.ntnu.quartoai.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinimaxCalculator {

    private final StateEvaluator stateEvaluator;

    @Inject
    public MinimaxCalculator(final StateEvaluator stateEvaluator) {
        this.stateEvaluator = stateEvaluator;
    }

    public Action alphaBeta(Game game, int depth, Piece pieceChosen) {
        Board board = game.getBoard();
        Set set = game.getSet();
        State state = new State(board, set);
        state.setPieceChosen(pieceChosen);

        double v = Double.MIN_VALUE;
        List<State> successors = calculateSuccessors(state);
        for (State successor : successors) {
            double minimumValueAmongSuccessors = minValue(successor, depth - 1, Double.MIN_VALUE, Double.MAX_VALUE,
                    "MIN");
            v = Math.max(v, minimumValueAmongSuccessors);
            successor.setCurrentValue(v);
        }

        State nextState = null;
        for (State successor : successors) {
            if (successor.getCurrentValue() == v) {
                nextState = successor;
                break;
            }
        }

        return new Action(nextState.getPieceChosen(), nextState.getPositionChosen()[0],
                nextState.getPositionChosen()[1]);
    }

    private double minValue(State state, int depth, double alpha, double beta, String player) {

        if (terminalTest(state)) {
            return Double.MAX_VALUE; // function utility
        }
        if (depth == 0) {
            return evaluate(state, player);
        }
        double v = Double.MAX_VALUE;
        List<State> successors = calculateSuccessors(state);
        for (State successor : successors) {
            double maximumValueAmongSuccessors = maxValue(successor, depth - 1, alpha, beta, "MAX");
            if (maximumValueAmongSuccessors < v) {
                v = maximumValueAmongSuccessors;
            }
            if (v <= alpha) {
                return v;
            }
            beta = Math.min(beta, v);
        }
        return v;
    }

    private double maxValue(State state, int depth, double alpha, double beta, String player) {

        if (terminalTest(state)) {
            return Double.MIN_VALUE; // function utility
        }
        if (depth == 0) {
            return evaluate(state, player);
        }
        double v = Double.MIN_VALUE;
        List<State> successors = calculateSuccessors(state);
        for (State successor : successors) {
            double minimumValueAmongSuccessors = minValue(successor, depth - 1, alpha, beta, "MAX");
            if (minimumValueAmongSuccessors > v) {
                v = minimumValueAmongSuccessors;
            }
            if (v >= beta) {
                return v;
            }
            alpha = Math.max(alpha, v);
        }
        return v;
    }

    public boolean terminalTest(State state) {
        Board board = state.getBoard();
        Set set = state.getSet();
        return board.gameOver() || board.getFreePositions().size() == 0 || set.getPieces().size() == 0;
    }

    public List<State> calculateSuccessors(State state) {
        Board board = state.getBoard();
        Set set = state.getSet();
        List<State> successors = new ArrayList<State>();
        List<Piece> piecesLeft = set.getPieces();
        Collections.shuffle(piecesLeft);
        List<int[]> positionsLeft = board.getFreePositions();

        /*if (state.getPieceChosen() == null) {
            for (Piece piece : piecesLeft) {
                for (int[] position : positionsLeft) {
                    int x = position[0];
                    int y = position[1];
                    Board newBoard = board.copy();
                    Set newSet = set.copy();
                    if (!newBoard.contains(piece)) {
                        newBoard.setPiece(piece, x, y);
                        newSet.remove(piece);
                        State child = new State(newBoard, newSet);
                        child.setPieceChosen(piece);
                        child.setPositionChosen(position);
                        successors.add(child);
                    }
                }
            }
        } else {*/
            Piece pieceChosen = state.getPieceChosen();
            for (int[] position : positionsLeft) {
                int x = position[0];
                int y = position[1];
                if (!board.contains(pieceChosen)) {
                    set.remove(pieceChosen);
                    for(Piece pieceToGive : set.getPieces()){
                        Board newBoard = board.copy();
                        Set newSet = set.copy();
                        newBoard.setPiece(pieceChosen, x, y);
                        State child = new State(newBoard, newSet);
                        child.setPieceChosen(pieceToGive);
                        child.setPositionChosen(position);
                        successors.add(child);
                    }
                }
            }
        //}
        return successors;
    }

    public double evaluate(State state, String player) {
        double eval = this.stateEvaluator.evaluate(state, player);
        if (player.equals("MIN")) {
            eval = eval * (-1);
        }
        return eval;
    }

}
