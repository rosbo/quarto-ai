package edu.ntnu.quartoai.controllers.players;

import core.*;

public abstract class PlayerController {

    private int numberOfThePlayer;

    public PlayerController(int number) {
        this.numberOfThePlayer = number;
    }

    public abstract Piece choosePieceToGive(Board board, Set set);

    public abstract Action chooseNextAction(Board board, Piece piece);

    public String getBehaviour(){
        return "";
    }

    public int getNumberOfThePlayer() {
        return numberOfThePlayer;
    }

    public void setNumberOfThePlayer(int numberOfThePlayer) {
        this.numberOfThePlayer = numberOfThePlayer;
    }
    
    @Override
    public String toString() {
        return "Player " + getNumberOfThePlayer() + " (" + this.getBehaviour() + ")";
    }

}
