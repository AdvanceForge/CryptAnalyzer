package ru.kolomoets.cryptanalyzer.application;


import ru.kolomoets.cryptanalyzer.controller.MainController;

public class Application {

    public static void main(String[] args) {

        MainController controller = new MainController();
        controller.run();
    }
}

