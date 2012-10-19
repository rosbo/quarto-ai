package edu.ntnu.quartoai.dependencyinjection;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;
import edu.ntnu.quartoai.controllers.GameController;
import edu.ntnu.quartoai.controllers.QuartoController;
import edu.ntnu.quartoai.controllers.players.NovicePlayerController;
import edu.ntnu.quartoai.controllers.players.PlayerController;
import edu.ntnu.quartoai.controllers.players.PlayerControllerFactory;
import edu.ntnu.quartoai.minimax.MinimaxCalculator;
import edu.ntnu.quartoai.minimax.StateEvaluator;
import edu.ntnu.quartoai.utils.Logger;
import edu.ntnu.quartoai.utils.LoggerProvider;

public class QuartoModule extends AbstractModule {
    private final Boolean tournamentProtocol;

    public QuartoModule(Boolean tournamentProtocol) {
        this.tournamentProtocol = tournamentProtocol;
    }

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(PlayerController.class, NovicePlayerController.class).build
                (PlayerControllerFactory.class));

        bind(GameController.class).in(Singleton.class);
        bind(QuartoController.class).in(Singleton.class);
        bind(MinimaxCalculator.class).in(Singleton.class);
        bind(StateEvaluator.class).in(Singleton.class);

        bind(Boolean.class).annotatedWith(Names.named("log")).toInstance(tournamentProtocol);
        bind(Logger.class).toProvider(LoggerProvider.class).in(Singleton.class);
    }
}
