package edu.ntnu.quartoai.models;

import core.Board;
import core.Set;
import edu.ntnu.quartoai.controllers.players.PlayerController;

import java.util.List;

public class Game {

    private List<PlayerController> players;
    private Board board;
    private Set set;
    private PlayerController winner;

    public Game(List<PlayerController> players) {
        this.players = players;
        this.board = new Board();
        this.set = new Set();
        this.winner = null;
    }

    public boolean isOver() {
        return this.board.gameOver();
    }

    public List<PlayerController> getPlayers() {
        return players;
    }

    public List<PlayerController> swapPlayers() {
        PlayerController player1 = this.players.get(0);
        this.players.remove(0);
        PlayerController player2 = this.players.get(0);
        this.players.remove(0);
        this.players.add(player2);
        this.players.add(player1);
        return this.players;
    }

    public Set getSet() {
        return set;
    }

    public Board getBoard() {
        return this.board;
    }

    public PlayerController getWinner() {
        return winner;
    }

    public void setWinner(PlayerController winner) {
        this.winner = winner;
    }
}
