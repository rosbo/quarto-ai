package edu.ntnu.quartoai;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.ntnu.quartoai.controllers.QuartoController;
import edu.ntnu.quartoai.dependencyinjection.QuartoModule;

public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new QuartoModule());
        QuartoController quartoController = injector.getInstance(QuartoController.class);
        quartoController.play();
    }
}
