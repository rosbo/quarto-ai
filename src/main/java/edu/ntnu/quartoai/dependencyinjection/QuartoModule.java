package edu.ntnu.quartoai.dependencyinjection;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import edu.ntnu.quartoai.controllers.GameController;
import edu.ntnu.quartoai.controllers.QuartoController;
import edu.ntnu.quartoai.controllers.players.PlayerFactory;
import edu.ntnu.quartoai.minimax.MinimaxCalculator;
import edu.ntnu.quartoai.minimax.StateEvaluator;

public class QuartoModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(GameController.class).in(Singleton.class);
        bind(QuartoController.class).in(Singleton.class);
        bind(PlayerFactory.class).in(Singleton.class);
        bind(MinimaxCalculator.class).in(Singleton.class);
        bind(StateEvaluator.class).in(Singleton.class);
    }
}
