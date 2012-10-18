package edu.ntnu.quartoai.controllers.players;

import com.google.inject.assistedinject.Assisted;

public interface PlayerControllerFactory {
    RandomPlayerController createRandomPlayerController(@Assisted("number") Integer number);

    NovicePlayerController createNovicePlayerController(@Assisted("number") Integer number);

    HumanPlayerController createHumanPlayerController(@Assisted("number") Integer number);

    MinimaxPlayerController createMinimaxPlayerController(@Assisted("number") Integer number, @Assisted("depth") Integer depth);
}
