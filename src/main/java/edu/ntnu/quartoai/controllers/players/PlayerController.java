package edu.ntnu.quartoai.controllers.players;

import core.Action;
import core.Piece;
import edu.ntnu.quartoai.models.Game;

public abstract class PlayerController {

    private int numberOfThePlayer;

    public PlayerController(int number) {
        this.numberOfThePlayer = number;
    }

    public abstract Piece choosePieceToGive(Game game);

    public abstract Action chooseNextAction(Game game, Piece piece);

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
