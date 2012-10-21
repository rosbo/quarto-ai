package edu.ntnu.quartoai.minimax;

import edu.ntnu.quartoai.models.Board;
import edu.ntnu.quartoai.models.Piece;
import edu.ntnu.quartoai.models.Set;

import java.util.ArrayList;
import java.util.List;

public class State {
    private Board board;
    private Set set;
    private List<State> successors;
    private double utility;
    private Piece pieceChosen;
    private int[] positionChosen;
    private State next;
    private int numberOfThePlayer;

    public int getNumberOfThePlayer() {
        return numberOfThePlayer;
    }

    public void setNumberOfThePlayer(int numberOfThePlayer) {
        this.numberOfThePlayer = numberOfThePlayer;
    }

    public State(Board board, Set set) {
        this.board = board;
        this.set = set;
        this.successors = new ArrayList<State>();
        this.utility = 0.0;
        this.pieceChosen = null;
        this.positionChosen = null;
        this.next = null;
        this.numberOfThePlayer = -1;        
    }    

    public List<State> getSuccessors() {
        return successors;
    }

    public void setSuccessors(List<State> successors) {
        this.successors = successors;
    }

    public Piece getPieceChosen() {
        return pieceChosen;
    }

    public void setPieceChosen(Piece pieceChosen) {
        this.pieceChosen = pieceChosen;
    }

    public int[] getPositionChosen() {
        return positionChosen;
    }

    public void setPositionChosen(int[] positionChosen) {
        this.positionChosen = positionChosen;
    }

    public List<State> addSuccessor(State node) {
        this.successors.add(node);
        return this.successors;
    }

    public Board getBoard() {
        return board;
    }

    public Set getSet() {
        return set;
    }

    @Override
    public String toString() {
        String result = "State: \n Board:\n" + this.board.toString() + "Set:\n" + set.toString() + "\nPiece Chosen:" + pieceChosen+ "\nTurn of player #" +
                +(numberOfThePlayer+1) +"\nUtility:"+ utility + "\nSuccessors:" + this.successors.size()+"\n";
        return result;
    }

    public String printAll() {
        String result = this.toString();
        for (State node : this.successors) {
            result = result + node.printAll() +"\n";
        }
        return result;
    }

    public double getUtility() {
        return utility;
    }

    public void setUtility(double utility) {
        this.utility = utility;
    }

    public State getNext() {
        return next;
    }

    public void setNext(State next) {
        this.next = next;
    }

}
