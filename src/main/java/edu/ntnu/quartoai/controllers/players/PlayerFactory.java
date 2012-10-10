package edu.ntnu.quartoai.controllers.players;

import edu.ntnu.quartoai.controllers.RandomPlayerController;

public class PlayerFactory {

    public PlayerController getPlayer(int playerBehaviourString, int numberOfPlayer) {
        switch (playerBehaviourString) {
        case 2:
            return new RandomPlayerController(numberOfPlayer);
        default:
            return null;
        }
    }

}
