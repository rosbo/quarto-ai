package edu.ntnu.quartoai.models;

import java.util.ArrayList;

public class Board {

    Piece[][] board = new Piece[4][4];

    public Board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = null;
            }
        }
    }

    public boolean isEmpty(int x, int y) {
        return board[y][x] == null;
    }

    public void setPiece(Piece piece, int x, int y) {
        board[y][x] = piece;

    }

    public boolean gameOver() {
        ArrayList<Piece> row;
        for (int i = 0; i < 4; i++) {
            row = new ArrayList<Piece>();
            for (int j = 0; j < 4; j++) {
                row.add(board[i][j]); // rows
            }
            if (rowComplete(row)) {
                return true;
            }
        }

        for (int i = 0; i < 4; i++) {
            row = new ArrayList<Piece>();
            for (int j = 0; j < 4; j++) {
                row.add(board[j][i]); // columns
            }
            if (rowComplete(row)) {
                return true;
            }
        }

        row = new ArrayList<Piece>();
        for (int i = 0; i < 4; i++) {
            row.add(board[i][i]); // 1. diagonal
        }
        if (rowComplete(row)) {
            return true;
        }

        row = new ArrayList<Piece>();
        for (int i = 0; i < 4; i++) {
            row.add(board[3 - i][i]); // 2. diagonal
        }
        if (rowComplete(row)) {
            return true;
        }
        return false;
    }

    private boolean rowComplete(ArrayList<Piece> row) {
        for (Piece piece : row) {
            if (piece == null) {
                return false;
            }
        }
        for (int i = 0; i < 4; i++) {
            if ((row.get(0).getAttributes()[i] == row.get(1).getAttributes()[i])
                            && (row.get(1).getAttributes()[i] == row.get(2).getAttributes()[i])
                            && (row.get(2).getAttributes()[i] == row.get(3).getAttributes()[i])) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<int[]> getFreePositions() {
        ArrayList<int[]> freePositions = new ArrayList<int[]>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (isEmpty(j, i)) {
                    int[] pos = { j, i };
                    freePositions.add(pos);
                }
            }
        }
        return freePositions;
    }

    public Piece get(int x, int y) {
        return board[y][x];
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == null) {
                    out += " .\t";
                } else {
                    out += board[i][j] + "\t";
                }
            }
            out += "\n";
        }
        return out;
    }

    public void remove(int x, int y) {
        board[y][x] = null;

    }

    public Board copy() {
        Board b = new Board();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                b.board[i][j] = this.board[i][j];
            }
        }
        return b;
    }

    // added for evaluating the board
    public Piece[][] getBoard() {
        return this.board;
    }

    public boolean contains(Piece piece) {
        boolean contains = false;
        if (piece != null) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (board[i][j] != null && piece.equals(board[i][j])) {
                        contains = true;
                        break;
                    }
                }
            }
        }
        return contains;
    }
}
