package edu.ntnu.quartoai.minimax;

import com.google.inject.Inject;
import edu.ntnu.quartoai.models.Board;
import edu.ntnu.quartoai.models.Piece;
import edu.ntnu.quartoai.models.Set;
import edu.ntnu.quartoai.models.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinimaxCalculator {

    private final StateEvaluator stateEvaluator;

    @Inject
    public MinimaxCalculator(final StateEvaluator stateEvaluator) {
        this.stateEvaluator = stateEvaluator;
    }

    public State alphaBetaDecision(Game game, Piece startingPiece, int depth, int numberOfThePlayer, boolean maximize) {
        Board board = game.getBoard();
        Set set = game.getSet();
        State state = new State(board, set);
        state.setNumberOfThePlayer(numberOfThePlayer);
        state.setPieceChosen(startingPiece);
        if (maximize) {
            maxValue(state, state, Double.MIN_VALUE, Double.MAX_VALUE, depth, (numberOfThePlayer + 1) % 2,
                            numberOfThePlayer, true, startingPiece);
        } else {
            minValue(state, state, Double.MIN_VALUE, Double.MAX_VALUE, depth, (numberOfThePlayer + 1) % 2,
                            numberOfThePlayer, true, startingPiece);
        }
        return state;
    }

    public double maxValue(State root, State state, double alpha, double beta, int depth,
                    int numberOfThePlayerPlayingTheState, int numberOfThePlayerWhoStarted, boolean minimaxPlayerTurn,
                    Piece startingPiece) {
        Board board = state.getBoard();
        Set set = state.getSet();
        if (board.gameOver() || board.getFreePositions().size() == 0 || set.getPieces().size() == 0) {
            double utilityValue = utilityValue(state, numberOfThePlayerWhoStarted, minimaxPlayerTurn);
            return utilityValue;
        } else if (depth == 0) {
            return evaluateIntermediateState(state, numberOfThePlayerWhoStarted);
        } else {
            double v = Double.MIN_VALUE;
            numberOfThePlayerPlayingTheState = (numberOfThePlayerPlayingTheState + 1) % 2;
            List<State> successors = calculateSuccessors(state, startingPiece, numberOfThePlayerPlayingTheState);
            for (State successor : successors) {
                double minimumValueSuccessors = minValue(root, successor, alpha, beta, depth - 1,
                                numberOfThePlayerPlayingTheState, numberOfThePlayerWhoStarted, !minimaxPlayerTurn,
                                startingPiece);
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
    }

    public double minValue(State root, State state, double alpha, double beta, int depth,
                    int numberOfThePlayerPlayingTheState, int numberOfThePlayerWhoStarted, boolean minimaxPlayerTurn,
                    Piece startingPiece) {
        Board board = state.getBoard();
        Set set = state.getSet();
        if (board.gameOver() || board.getFreePositions().size() == 0 || set.getPieces().size() == 0) {
            double utilityValue = utilityValue(state, numberOfThePlayerWhoStarted, minimaxPlayerTurn);
            return utilityValue;
        }else if (depth == 0) {
            return evaluateIntermediateState(state, numberOfThePlayerWhoStarted);
        } else {
            double v = Double.MAX_VALUE;
            numberOfThePlayerPlayingTheState = (numberOfThePlayerPlayingTheState + 1) % 2;
            List<State> successors = calculateSuccessors(state, startingPiece, numberOfThePlayerPlayingTheState);
            for (State successor : successors) {
                double maximumValueOfSuccessor = maxValue(root, successor, alpha, beta, depth - 1,
                                numberOfThePlayerPlayingTheState, numberOfThePlayerWhoStarted, !minimaxPlayerTurn,
                                startingPiece);
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
    }

    public List<State> calculateSuccessors(State state, Piece startingPiece, int numberOfThePlayerPlayingTheState) {
        int x, y;
        List<State> successors = new ArrayList<State>();
        Board board = state.getBoard();
        Set set = state.getSet();
        List<Piece> pieces = set.getPieces();
        Collections.shuffle(pieces);
        List<int[]> positions = board.getFreePositions();
        Collections.shuffle(positions);
        if (startingPiece == null) {
            for (Piece piece : pieces) {
                for (int[] position : positions) {
                    Board newBoard = board.copy();
                    Set newSet = set.copy();
                    x = position[0];
                    y = position[1];
                    if (!newBoard.contains(piece)) {
                        newBoard.setPiece(piece, x, y);
                        newSet.remove(piece);
                        State child = new State(newBoard, newSet);
                        child.setPieceChosen(piece);
                        child.setPositionChosen(position);
                        child.setNumberOfThePlayer(numberOfThePlayerPlayingTheState);
                        successors.add(child);
                    }
                }
            }
        } else {
            // if you already have the next piece you just need to iterate over
            // all the possible positions left
            Piece piece = startingPiece;
            for (int[] position : positions) {
                Board newBoard = board.copy();
                Set newSet = set.copy();
                x = position[0];
                y = position[1];
                if (!newBoard.contains(piece)) {
                    newBoard.setPiece(piece, x, y);
                    newSet.remove(piece);
                    State child = new State(newBoard, newSet);
                    child.setPieceChosen(piece);
                    child.setPositionChosen(position);
                    child.setNumberOfThePlayer(numberOfThePlayerPlayingTheState);
                    successors.add(child);
                }
            }
        }
        state.setSuccessors(successors);
        return successors;
    }

    private double evaluateIntermediateState(State state, int numberOfThePlayerWhoStarted) {
        double eval = stateEvaluator.evaluate(state, numberOfThePlayerWhoStarted);
        state.setUtility(eval);
        return eval;
    }

    private double utilityValue(State state, int numberOfThePlayerWhoStarted, boolean minimaxPlayerTurn) {
        double eval = stateEvaluator.utilityValue(state, numberOfThePlayerWhoStarted);
        eval = minimaxPlayerTurn ? eval : -1*(eval);
        state.setUtility(eval);
        return eval;
    }

}
