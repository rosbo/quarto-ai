package edu.ntnu.quartoai.controllers.players;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import edu.ntnu.quartoai.models.Action;
import edu.ntnu.quartoai.models.Piece;
import edu.ntnu.quartoai.models.Set;
import edu.ntnu.quartoai.minimax.MinimaxCalculator;
import edu.ntnu.quartoai.minimax.State;
import edu.ntnu.quartoai.models.Game;

public class MinimaxPlayerController extends PlayerController {

    private int depth;
    private final MinimaxCalculator minimaxCalculator;
    private final NovicePlayerController novicePlayerController;

    @Inject
    public MinimaxPlayerController(final MinimaxCalculator minimaxCalculator,
                    final NovicePlayerController novicePlayerController, @Assisted("number") Integer number,
                    @Assisted("depth") Integer depth) {
        super(number, "minimax" + depth);
        this.depth = depth;
        this.minimaxCalculator = minimaxCalculator;
        this.novicePlayerController = novicePlayerController;
    }

    @Override
    public Piece choosePieceToGive(Game game) {
        Set set = game.getSet();
        if (set.getPieces().size() > 10 || set.getPieces().size() == 1) {
            return novicePlayerController.choosePieceToGive(game);
        }
        State currentState = this.minimaxCalculator.alphaBetaDecision(game, null, depth, getNumberOfThePlayer(), false);
        State nextState = currentState.getNext();
        Piece piece = null;
        if (nextState != null) {
            piece = nextState.getPieceChosen();
        } else {// the current state is a goal, has no successors
            piece = currentState.getPieceChosen();
        }
        return piece;
    }

    @Override
    public Action chooseNextAction(Game game, Piece piece) {
        Set set = game.getSet();
        if (set.getPieces().size() > 10 || set.getPieces().size() == 1) {
            return novicePlayerController.chooseNextAction(game, piece);
        }
        State currentState = this.minimaxCalculator.alphaBetaDecision(game, piece, depth, getNumberOfThePlayer(), true);
        State nextState = currentState.getNext();
        Action action = null;
        if (nextState != null) {
            action = new Action(nextState.getPieceChosen(), nextState.getPositionChosen()[0],
                            nextState.getPositionChosen()[1]);
        } else {
           action = new Action(currentState.getPieceChosen(), currentState.getPositionChosen()[0],
                            currentState.getPositionChosen()[1]);
        }
        return action;
    }

}
