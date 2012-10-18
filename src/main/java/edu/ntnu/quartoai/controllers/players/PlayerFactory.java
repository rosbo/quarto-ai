package edu.ntnu.quartoai.controllers.players;

import com.google.inject.Inject;
import edu.ntnu.quartoai.models.PlayerBehavior;


public class PlayerFactory {
    private final PlayerControllerFactory playerControllerFactory;

    @Inject
    public PlayerFactory(final PlayerControllerFactory playerControllerFactory) {
        this.playerControllerFactory = playerControllerFactory;
    }

    public PlayerController getPlayer(PlayerBehavior playerBehaviour, int playerNumber) {
        switch (playerBehaviour) {
            case RANDOM:
                return playerControllerFactory.createRandomPlayerController(playerNumber);
            case NOVICE:
                return playerControllerFactory.createNovicePlayerController(playerNumber);
            case MINIMAX3:
                return playerControllerFactory.createMinimaxPlayerController(playerNumber, 3);
            case MINIMAX4:
                return playerControllerFactory.createMinimaxPlayerController(playerNumber, 4);
            default:
                return null;
        }
    }

}
