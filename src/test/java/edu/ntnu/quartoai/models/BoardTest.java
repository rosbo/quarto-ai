package edu.ntnu.quartoai.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {
    @Test
    public void isGameOver() {
        Board board = new Board();
        Piece p1 = new Piece(true, false, false, false);
        Piece p2 = new Piece(true, true, false, false);
        Piece p3 = new Piece(true, false, true, false);
        Piece p4 = new Piece(true, false, false, true);
        Piece p5 = new Piece(false, true, false, true);
        Piece p6 = new Piece(false, false, true, true);
        board.setPiece(p1, 0, 0);
        board.setPiece(p2, 1, 1);
        board.setPiece(p3, 2, 2);
        board.setPiece(p4, 3, 3);
        board.setPiece(p5, 2, 0);
        board.setPiece(p6, 2, 1);

        assertTrue(board.gameOver());
    }

    @Test
    public void isNotGameOver() {
        Board board = new Board();
        Piece p1 = new Piece(true, false, false, false);
        Piece p2 = new Piece(true, true, false, false);
        Piece p3 = new Piece(true, false, true, false);
        board.setPiece(p1, 0, 0);
        board.setPiece(p2, 1, 1);
        board.setPiece(p3, 2, 2);

        assertFalse(board.gameOver());
    }
}
