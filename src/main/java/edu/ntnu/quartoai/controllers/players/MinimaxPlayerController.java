package edu.ntnu.quartoai.controllers.players;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import edu.ntnu.quartoai.minimax.MinimaxCalculator;
import edu.ntnu.quartoai.models.Action;
import edu.ntnu.quartoai.models.Game;
import edu.ntnu.quartoai.models.Piece;
import edu.ntnu.quartoai.models.Set;

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
        if (set.getPieces().size() > 11 || set.getPieces().size() < 1) {
            return novicePlayerController.choosePieceToGive(game);
        } 
        Action nextAction = minimaxCalculator.alphaBeta(game,depth,null);
        return nextAction.piece;
    }

    @Override
    public Action chooseNextAction(Game game, Piece piece) {
        Set set = game.getSet();
        if (set.getPieces().size() > 11 || set.getPieces().size() < 1) {
            return novicePlayerController.chooseNextAction(game, piece);
        }
        return minimaxCalculator.alphaBeta(game, depth, piece);
    }

}
