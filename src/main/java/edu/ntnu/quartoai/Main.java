package edu.ntnu.quartoai;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.quartoai.controllers.QuartoController;
import edu.ntnu.quartoai.controllers.players.PlayerController;
import edu.ntnu.quartoai.controllers.players.PlayerControllerFactory;
import edu.ntnu.quartoai.dependencyinjection.QuartoModule;
import org.apache.commons.cli.*;

import java.io.IOException;

public class Main {
    private static final String defaultNumberOfGames = "10";

    public static void main(String[] args) throws IOException {
        Options options = createArgsOptions();

        PosixParser parser = new PosixParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            int numberOfGames = Integer.parseInt(cmd.getOptionValue("n", defaultNumberOfGames));
            Boolean isTournamentProtocol = cmd.hasOption("g");

            Injector injector = Guice.createInjector(new QuartoModule(isTournamentProtocol));
            QuartoController quartoController = injector.getInstance(QuartoController.class);
            PlayerControllerFactory playerControllerFactory = injector.getInstance(PlayerControllerFactory.class);

            PlayerController player1, player2;
            String[] pValues = cmd.getOptionValues("p");

            if (pValues.length != 2) {
                throw new ParseException("You should provide 2 players");
            } else {
                player1 = getPlayerFromString(playerControllerFactory, pValues[0], 1);
                player2 = getPlayerFromString(playerControllerFactory, pValues[1], 2);
            }

            quartoController.play(player1, player2, numberOfGames, isTournamentProtocol);
        } catch (ParseException e) {
            System.err.println("Parsing failed: " + e.getMessage());
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("quarto", options);
        }
    }

    private static PlayerController getPlayerFromString(PlayerControllerFactory playerControllerFactory, String pValue,
                                                        int number) throws ParseException {
        if (pValue.equals("random")) {
            return playerControllerFactory.createRandomPlayerController(number);
        } else if (pValue.equals("novice")) {
            return playerControllerFactory.createNovicePlayerController(number);
        } else if (pValue.equals("minimax3")) {
            return playerControllerFactory.createMinimaxPlayerController(number, 3);
        } else if (pValue.equals("minimax4")) {
            return playerControllerFactory.createMinimaxPlayerController(number, 4);
        } else if (pValue.equals("human")) {
            return playerControllerFactory.createHumanPlayerController(number);
        } else {
            throw new ParseException("Invalid player");
        }
    }

    private static Options createArgsOptions() {
        Options options = new Options();

        Option playerOption = OptionBuilder.withArgName("PLAYER PLAYER").hasArgs(2).withDescription("PLAYER can be " +
                "human, random, novice, minimax < D > ").isRequired(true).create("p");

        options.addOption("n", true, "Number of games to play [default: " + defaultNumberOfGames + "]");
        options.addOption("g", false, "Tournament protocol mode");
        options.addOption(playerOption);

        return options;
    }
}
