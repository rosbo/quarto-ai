package edu.ntnu.quartoai.utils;

public class NormalLogger implements Logger {
    public void log(String message) {
        System.out.println(message);
    }

    public void logProtocol(String message) {
    }
}
