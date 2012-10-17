package edu.ntnu.quartoai.minimax;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import core.Action;
import core.Board;
import core.Piece;
import core.Set;
import edu.ntnu.quartoai.models.Game;

public class MinimaxCalculator {

    private final StateEvaluator stateEvaluator;

    @Inject
    public MinimaxCalculator(final StateEvaluator stateEvaluator) {
        this.stateEvaluator = stateEvaluator;
    }

    public Piece alphaBetaDecisionForChoosingThePieceToGive(Game game, int depth) {
        Board board = game.getBoard();
        Set set = game.getSet();
        State state = new State(board, set);

        minValue(state, Double.MIN_VALUE, Double.MAX_VALUE, depth);

        return state.getNext().getPieceChosen();
    }

    public double maxValue(State state, double alpha, double beta, int depth) {
        Board board = state.getBoard();
        Set set = state.getSet();
        if (board.gameOver() || board.getFreePositions().size() == 0 || set.getPieces().size() == 0 || depth == 0) {
            return evalState(state); // return utility value
        }
        double v = Double.NEGATIVE_INFINITY;
        List<State> successors = calculateSuccessors(state);
        for (State successor : successors) {
            double minimumValueSuccessors = minValue(successor, alpha, beta, depth--);
            if (minimumValueSuccessors > v) {
                v = minimumValueSuccessors;
                state.setNext(successor);
            }
            if (v >= beta) {
                return v;
            }
            alpha = Math.max(alpha, v);
        }
        return v;
    }

    public double minValue(State state, double alpha, double beta, int depth) {
        Board board = state.getBoard();
        Set set = state.getSet();
        if (board.gameOver() || board.getFreePositions().size() == 0 || set.getPieces().size() == 0 || depth == 0) {
            return evalState(state); // return utility value
        }
        double v = Double.POSITIVE_INFINITY;
        List<State> successors = calculateSuccessors(state);
        for (State successor : successors) {
            double maximumValueOfSuccessor = maxValue(successor, alpha, beta, depth--);
            if (maximumValueOfSuccessor < v) {
                v = maximumValueOfSuccessor;
                state.setNext(successor);
            }
            if (v <= alpha) {
                return v;
            }
            beta = Math.min(beta, v);
        }
        return v;
    }

    public List<State> calculateSuccessors(State state) {
        int x, y;
        List<State> successors = new ArrayList<State>();
        Board board = state.getBoard();
        Set set = state.getSet();
        List<Piece> pieces = set.getPieces();
        List<int[]> positions = board.getFreePositions();
        if (state.getPieceChosen() == null) {
            for (Piece piece : pieces) {
                for (int[] position : positions) {
                    Board newBoard = board.copy();
                    Set newSet = set.copy();
                    x = position[0];
                    y = position[1];
                    newBoard.setPiece(piece, x, y);
                    newSet.remove(piece);
                    State child = new State(newBoard, newSet);
                    child.setPieceChosen(piece);
                    child.setPositionChosen(position);
                    successors.add(child);
                }
            }
        } else {
            // if you already have the next piece you just need to iterate over
            // all the possible positions left
            Piece piece = state.getPieceChosen();
            for (int[] position : positions) {
                Board newBoard = board.copy();
                Set newSet = set.copy();
                x = position[0];
                y = position[1];
                newBoard.setPiece(piece, x, y);
                newSet.remove(piece);
                State child = new State(newBoard, newSet);
                child.setPieceChosen(piece);
                child.setPositionChosen(position);
                successors.add(child);
            }
        }
        state.setSuccessors(successors);
        return successors;
    }

    public double evalState(State state) {
        double eval = stateEvaluator.evaluate(state);
        state.setUtility(eval);
        return eval;
    }

    public Action alphaBetaDecisionGivenPiece(Game game, Piece piece, int depth, boolean maximize) {
        Board board = game.getBoard();
        Set set = game.getSet();
        State state = new State(board, set);
        state.setPieceChosen(piece);

        maxValue(state, Double.MIN_VALUE, Double.MAX_VALUE, depth);

        State nextState = state.getNext();
        int x = nextState.getPositionChosen()[0];
        int y = nextState.getPositionChosen()[1];
        return new Action(piece, x, y);
    }
}
