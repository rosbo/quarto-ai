package edu.ntnu.quartoai.controllers.players;

import com.google.inject.assistedinject.Assisted;
import edu.ntnu.quartoai.models.Action;
import edu.ntnu.quartoai.models.Game;
import edu.ntnu.quartoai.models.Piece;
import edu.ntnu.quartoai.utils.BReader;
import edu.ntnu.quartoai.utils.Logger;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HumanPlayerController extends PlayerController {

    private final Logger logger;
    private final BufferedReader bufferedReader = BReader.get();


    @Inject
    public HumanPlayerController(Logger logger, @Assisted("number") Integer number) {
        super(number, "human");

        this.logger = logger;
    }

    @Override
    public Piece choosePieceToGive(Game game) {
        logger.log("Choose the piece to give");
        logger.log(game.getSet().toString());

        try {
            String input = bufferedReader.readLine();

            return Piece.stringToPeace(input);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid piece");
        }
    }

    @Override
    public Action chooseNextAction(Game game, Piece piece) {
        logger.log("Choose where to place the piece " + piece.toString() + ": x y (zero indexed)");
        int x, y;
        try {
            String input = bufferedReader.readLine();

            Pattern pattern = Pattern.compile("([0-9])");
            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                x = Integer.parseInt(matcher.group());
                if (matcher.find()) {
                    y = Integer.parseInt(matcher.group());
                    return new Action(piece, x, y);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Wrong action");
    }

}
