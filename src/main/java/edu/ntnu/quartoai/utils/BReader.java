package edu.ntnu.quartoai.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BReader {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static BufferedReader get(){
        return bufferedReader;
    }
}
