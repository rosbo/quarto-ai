package edu.ntnu.quartoai.controllers.players;

import core.Action;
import core.Board;
import core.Piece;
import core.Set;
import edu.ntnu.quartoai.models.Game;

import java.util.List;
import java.util.Random;

public class RandomPlayerController extends PlayerController {

    public RandomPlayerController(int number) {
        super(number);
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


    @Override
    public String getBehaviour() {
        return "random";
    }
}
