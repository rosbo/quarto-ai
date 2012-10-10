package edu.ntnu.quartoai.controllers;

import java.util.List;
import java.util.Random;

import core.Action;
import core.Board;
import core.Piece;
import core.Set;
import edu.ntnu.quartoai.controllers.players.PlayerController;

public class RandomPlayerController extends PlayerController {

    public RandomPlayerController(int number) {
        super(number);
    }

    @Override
    public Piece choosePieceToGive(Board board, Set set) {
        // TODO Auto-generated method stub
        List<Piece> pieces = set.getPieces();
        Random randomGenerator = new Random();
        int size = pieces.size();
        return pieces.get(randomGenerator.nextInt(size));
    }

    @Override
    public Action chooseNextAction(Board board, Piece piece) {
        // TODO Auto-generated method stub
        Random randomGenerator = new Random();
        List<int[]> freePositions = board.getFreePositions();
        int[] randomPosition = freePositions.get(randomGenerator.nextInt(freePositions.size()));
        return new Action(piece, randomPosition[0], randomPosition[1]);
    }
    
    @Override
    public String toString() {
        return "Player " + super.getNumberOfThePlayer() + " (" + this.getBehaviour() + ")";
    }

    @Override
    public String getBehaviour(){
        return "random";
    }
}
