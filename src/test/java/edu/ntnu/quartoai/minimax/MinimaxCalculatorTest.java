package edu.ntnu.quartoai.minimax;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.ntnu.quartoai.dependencyinjection.QuartoModule;
import edu.ntnu.quartoai.models.Board;
import edu.ntnu.quartoai.models.Game;
import edu.ntnu.quartoai.models.Piece;
import edu.ntnu.quartoai.models.Set;

public class MinimaxCalculatorTest {
    
    private MinimaxCalculator minimaxCalculator;

    @Before
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new QuartoModule(false));
        minimaxCalculator = injector.getInstance(MinimaxCalculator.class);
    }

    @Test
    public void testCalculateSuccessors() {
        Board board = new Board();
        Set set = new Set();
        State state = new State(board, set);
        int numberOfPlayer = 1;
        List<State> succ = new ArrayList<State>();
        Piece piece;
        int[] position;
        /* LEVEL 1*/
        //player 1 choose the piece
        succ = minimaxCalculator.calculateSuccessors(state, null, numberOfPlayer);
        assertEquals(256,succ.size());

        piece = set.getPieces().get(0);
        state.setPieceChosen(piece);
        set.remove(piece);
        //player 2 choose the position
        succ = minimaxCalculator.calculateSuccessors(state, piece , numberOfPlayer);
        assertEquals(16,succ.size());
        position = board.getFreePositions().get(0);
        board.setPiece(piece, position[0], position[1]);
        
        /*LEVEL 2*/
        //player 2 choose the piece
        state.setPieceChosen(null);
        succ = minimaxCalculator.calculateSuccessors(state, null, numberOfPlayer);
        assertEquals((15*15),succ.size());        
        
        piece = set.getPieces().get(0);
        state.setPieceChosen(piece);
        set.remove(piece);
        
        //player 1 choose the position
        succ = minimaxCalculator.calculateSuccessors(state,piece, numberOfPlayer);
        assertEquals(15,succ.size());
        position = board.getFreePositions().get(0);
        board.setPiece(piece, position[0], position[1]);
        
        /*LEVEL 3*/
        //player 1 choose the piece
        state.setPieceChosen(null);
        succ = minimaxCalculator.calculateSuccessors(state, null, numberOfPlayer);
        assertEquals((14*14),succ.size());        
        
        piece = set.getPieces().get(0);
        state.setPieceChosen(piece);
        set.remove(piece);
        
        //player 2 choose the position
        succ = minimaxCalculator.calculateSuccessors(state,piece, numberOfPlayer);
        position = board.getFreePositions().get(0);
        board.setPiece(piece, position[0], position[1]);
    }

    @Test
    public void testAlphaBetaDecisionEasyChoice(){
        Game game = new Game(null);
        Board board = game.getBoard();
        Set set = game.getSet();
        for(int i = 0; i< 3; i++){
            Piece piece = set.getPieces().get(0);
            set.remove(piece);
            int[] position = board.getFreePositions().get(0);
            board.setPiece(piece, position[0], position[1]);
        }
        int numberOfPlayer = 1;
        List<State> succ = new ArrayList<State>();
        Piece piece;
        int[] position;
        boolean maximize  = true;
        
//        State state = minimaxCalculator.alphaBetaDecision(game, 1, numberOfPlayer, maximize);
//        System.out.println(state.printAll());
//        System.out.println("\n*****************");
//        System.out.println("THE NEXT STATE IS " + state.getNext());  
                
    }
    
    @Test
    public void testAlphaBetaDecisionRandomGame(){
        Game game = new Game(null);
        Board board = game.getBoard();
        Set set = game.getSet();
        for(int i = 0; i< 10; i++){
            List<Piece> piecesLeft = set.getPieces();
            Collections.shuffle(piecesLeft);
            Piece piece = piecesLeft.get(0);
            set.remove(piece);
            List<int[]> positions = board.getFreePositions();
            Collections.shuffle(positions);
            int[] position = positions.get(0);
            board.setPiece(piece, position[0], position[1]);
        }
        int numberOfPlayer = 1;
        List<State> succ = new ArrayList<State>();
        Piece piece;
        int[] position;
        boolean maximize  = true;
        
        State state = minimaxCalculator.alphaBetaDecision(game,null, 5, numberOfPlayer, maximize);
        System.out.println(state.printAll());
        System.out.println("\n*****************");
        System.out.println("THE NEXT STATE IS " + state.getNext());
        
    }

}
