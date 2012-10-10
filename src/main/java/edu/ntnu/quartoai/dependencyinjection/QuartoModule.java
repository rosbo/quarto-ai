package edu.ntnu.quartoai.dependencyinjection;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import edu.ntnu.quartoai.controllers.players.GameController;
import edu.ntnu.quartoai.controllers.players.PlayerFactory;
import edu.ntnu.quartoai.controllers.players.QuartoController;

public class QuartoModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GameController.class).in(Singleton.class);
        bind(QuartoController.class).in(Singleton.class);
        bind(PlayerFactory.class).in(Singleton.class);
    }
}
