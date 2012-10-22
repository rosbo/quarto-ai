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
        if (set.getPieces().size() > 10 || set.getPieces().size() <= 1) {
            return novicePlayerController.choosePieceToGive(game);
        } 
        State currentState = this.minimaxCalculator.alphaBetaDecision(game, null, depth, getNumberOfThePlayer(), true);
        State nextState = currentState.getNext();
        if (nextState != null) {
            return nextState.getPieceChosen();
        } else {
            return novicePlayerController.choosePieceToGive(game);//final state
        }
    }

    @Override
    public Action chooseNextAction(Game game, Piece piece) {
        Set set = game.getSet();
        if (set.getPieces().size() > 10 || set.getPieces().size() <= 1) {
            return novicePlayerController.chooseNextAction(game, piece);
        }
        State currentState = this.minimaxCalculator.alphaBetaDecision(game, piece, depth, getNumberOfThePlayer(), true);
        State nextState = currentState.getNext();
        Action action = null;
        if (nextState != null) {
            action = new Action(nextState.getPieceChosen(), nextState.getPositionChosen()[0],
                            nextState.getPositionChosen()[1]);
        } else {
            return novicePlayerController.chooseNextAction(game, piece);//final state
        }
        return action;
    }

}
