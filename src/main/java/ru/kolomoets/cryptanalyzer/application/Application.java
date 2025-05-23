package ru.kolomoets.cryptanalyzer.application;


import ru.kolomoets.cryptanalyzer.controller.MainController;

/**
 * Класс Application — точка входа в программу.
 * Здесь запускается основной контроллер приложения.
 */

public class Application {

    public static void main(String[] args) {

        MainController controller = new MainController();
        controller.run();
    }
}

