package edu.ntnu.quartoai.utils;

import edu.ntnu.quartoai.utils.Logger;
import edu.ntnu.quartoai.utils.NormalLogger;
import edu.ntnu.quartoai.utils.TournamentLogger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

public class LoggerProvider implements Provider<Logger> {
    private final Boolean tournamentProtocol;

    @Inject
    public LoggerProvider(final @Named("log") Boolean isTournamentProtocol) {
        tournamentProtocol = isTournamentProtocol;
    }

//    @Override
    public Logger get() {
        if (tournamentProtocol) {
            return new TournamentLogger();
        } else {
            return new NormalLogger();
        }
    }
}
