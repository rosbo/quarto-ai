package edu.ntnu.quartoai.utils;

public class TournamentLogger implements Logger {
    public void log(String message) {
    }

//    @Override
    public void logProtocol(String message) {
        System.out.println(message);
    }
}
