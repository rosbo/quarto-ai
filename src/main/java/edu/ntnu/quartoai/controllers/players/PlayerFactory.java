package edu.ntnu.quartoai.controllers.players;


public class PlayerFactory {

    public PlayerController getPlayer(int playerBehaviourString, int numberOfPlayer) {
        switch (playerBehaviourString) {
        case 2:
            return new RandomPlayerController(numberOfPlayer);
        case 3:
            return new NovicePlayerController(numberOfPlayer);
        default:
            return null;
        }
    }

}
