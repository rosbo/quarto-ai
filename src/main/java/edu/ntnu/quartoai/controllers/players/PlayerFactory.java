package edu.ntnu.quartoai.controllers.players;

import com.google.inject.Inject;

import edu.ntnu.quartoai.minimax.MinimaxCalculator;


public class PlayerFactory {
    
    private final MinimaxCalculator minimaxCalculator;
    
    @Inject
    public PlayerFactory(final MinimaxCalculator minimaxCalculator){
        this.minimaxCalculator = minimaxCalculator;
    }

    public PlayerController getPlayer(int playerBehaviourString, int numberOfThePlayer) {
        switch (playerBehaviourString) {
        case 2:
            return new RandomPlayerController(numberOfThePlayer);
        case 3:
            return new NovicePlayerController(numberOfThePlayer);
        case 4:
            return new MinimaxPlayerController(numberOfThePlayer,3,minimaxCalculator,new NovicePlayerController(numberOfThePlayer));
        case 5:
            return new MinimaxPlayerController(numberOfThePlayer,4,minimaxCalculator,new NovicePlayerController(numberOfThePlayer));
        default:
            return null;
        }
    }

}
