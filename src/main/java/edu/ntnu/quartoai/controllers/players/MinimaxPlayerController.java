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
                                   final NovicePlayerController novicePlayerController,
                                   @Assisted("number") Integer number, @Assisted("depth") Integer depth) {
        super(number, "minimax" + depth);
        this.depth = depth;
        this.minimaxCalculator = minimaxCalculator;
        this.novicePlayerController = novicePlayerController;
    }

    @Override
    public Piece choosePieceToGive(Game game) {
        Set set = game.getSet();
        if (set.getPieces().size() > 11 || set.getPieces().size() == 0) {
            return novicePlayerController.choosePieceToGive(game);
        }
        State nextState = this.minimaxCalculator.alphaBetaDecision(game, depth,
                getNumberOfThePlayer(), false);
        return nextState.getPieceChosen();
    }

    @Override
    public Action chooseNextAction(Game game, Piece piece) {
        Set set = game.getSet();
        if (set.getPieces().size() > 11 || set.getPieces().size() == 0) {
            return novicePlayerController.chooseNextAction(game, piece);
        }
        State nextState = this.minimaxCalculator.alphaBetaDecision(game, depth,
                getNumberOfThePlayer(), true);
        return new Action(nextState.getPieceChosen(), nextState.getPositionChosen()[0],
                nextState.getPositionChosen()[1]);
    }

}
