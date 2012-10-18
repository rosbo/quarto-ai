package edu.ntnu.quartoai.models;

import java.util.ArrayList;

public class Set {

    ArrayList<Piece> pieces;

    public Set() {
        pieces = new ArrayList<Piece>();
        for (int i = 0; i < 16; i++) {
            String binaryString = "000" + Integer.toBinaryString(i);
            binaryString = binaryString.substring(binaryString.length() - 4, binaryString.length());
            pieces.add(new Piece(binaryString.charAt(0) == '0', binaryString.charAt(1) == '0',
                    binaryString.charAt(2) == '0', binaryString.charAt(3) == '0'));
        }
    }

    public Set(ArrayList<Piece> pieceList) {
        this.pieces = pieceList;
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < pieces.size(); i++) {
            if(i != 0){
                out += ", ";
            }
            out += pieces.get(i) + " ";
        }
        return out;
    }

    public boolean contrains(Piece piece) {
        return pieces.contains(piece);
    }

    public boolean remove(Piece piece) {
        return pieces.remove(piece);
    }

    public boolean isEmpty() {
        return pieces.isEmpty();
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public Set copy() {
        ArrayList<Piece> pieceList = new ArrayList<Piece>();
        pieceList.addAll(pieces);
        return new Set(pieceList);
    }

}
