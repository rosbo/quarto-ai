package edu.ntnu.quartoai;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.quartoai.controllers.QuartoController;
import edu.ntnu.quartoai.controllers.players.PlayerController;
import edu.ntnu.quartoai.controllers.players.PlayerFactory;
import edu.ntnu.quartoai.dependencyinjection.QuartoModule;
import edu.ntnu.quartoai.models.PlayerBehavior;
import org.apache.commons.cli.*;

public class Main {
    private static final String defaultNumberOfGames = "10";

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new QuartoModule());
        QuartoController quartoController = injector.getInstance(QuartoController.class);
        PlayerFactory playerFactory = injector.getInstance(PlayerFactory.class);

        Options options = createArgsOptions();

        PosixParser parser = new PosixParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            int numberOfGames = Integer.parseInt(cmd.getOptionValue("n", defaultNumberOfGames));

            PlayerController player1, player2;
            String[] pValues = cmd.getOptionValues("p");

            if (pValues.length != 2) {
                throw new ParseException("You should provide 2 players");
            } else {
                player1 = getPlayerFromString(playerFactory, pValues[0], 1);
                player2 = getPlayerFromString(playerFactory, pValues[1], 2);
            }

            quartoController.play(player1, player2, numberOfGames);
        } catch (ParseException e) {
            System.err.println("Parsing failed: " + e.getMessage());
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("quarto", options);
        }
    }

    private static PlayerController getPlayerFromString(PlayerFactory playerFactory, String pValue,
                                                        int number) throws ParseException {
        PlayerBehavior playerBehavior;
        if (pValue.equals("random")) {
            playerBehavior = PlayerBehavior.RANDOM;
        } else if (pValue.equals("novice")) {
            playerBehavior = PlayerBehavior.NOVICE;
        } else if (pValue.equals("minimax3")) {
            playerBehavior = PlayerBehavior.MINIMAX3;
        } else if (pValue.equals("minimax3")) {
            playerBehavior = PlayerBehavior.MINIMAX4;
        } else {
            throw new ParseException("Invalid player");
        }

        return playerFactory.getPlayer(playerBehavior, number);
    }

    private static Options createArgsOptions() {
        Options options = new Options();

        Option playerOption = OptionBuilder.withArgName("PLAYER PLAYER").hasArgs(2).withDescription("PLAYER can be " +
                "human, " +
                "random, novice, minimax<D>").isRequired(true).create("p");
        options.addOption("n", true, "Number of games to play [default: 0]");
        options.addOption(playerOption);

        return options;
    }
}
