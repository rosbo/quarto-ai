package edu.ntnu.quartoai.controllers.players;

import core.Action;
import core.Piece;
import edu.ntnu.quartoai.models.Game;

public abstract class PlayerController {
    private final String behavior;
    private final int numberOfThePlayer;

    protected PlayerController(int number, String behavior) {
        this.numberOfThePlayer = number;
        this.behavior = behavior;
    }

    @Override
    public String toString() {
        return "Player " + getNumberOfThePlayer() + " (" + behavior + ")";
    }

    public abstract Piece choosePieceToGive(Game game);

    public abstract Action chooseNextAction(Game game, Piece piece);

    public int getNumberOfThePlayer() {
        return numberOfThePlayer;
    }

    public boolean isHuman(){
        return behavior.equals("human");
    }
}
