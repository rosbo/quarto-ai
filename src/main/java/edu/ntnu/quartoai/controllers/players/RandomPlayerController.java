package edu.ntnu.quartoai.controllers.players;

import com.google.inject.assistedinject.Assisted;
import edu.ntnu.quartoai.models.Action;
import edu.ntnu.quartoai.models.Board;
import edu.ntnu.quartoai.models.Piece;
import edu.ntnu.quartoai.models.Set;
import edu.ntnu.quartoai.models.Game;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

public class RandomPlayerController extends PlayerController {
    @Inject
    public RandomPlayerController(@Assisted("number") Integer number) {
        super(number, "random");
    }

    @Override
    public Piece choosePieceToGive(Game game) {
        Set set = game.getSet();
        List<Piece> pieces = set.getPieces();
        Random randomGenerator = new Random();
        int size = pieces.size();
        return pieces.get(randomGenerator.nextInt(size));
    }

    @Override
    public Action chooseNextAction(Game game, Piece piece) {
        Board board = game.getBoard();
        Random randomGenerator = new Random();
        List<int[]> freePositions = board.getFreePositions();
        int[] randomPosition = freePositions.get(randomGenerator.nextInt(freePositions.size()));
        return new Action(piece, randomPosition[0], randomPosition[1]);
    }

}
