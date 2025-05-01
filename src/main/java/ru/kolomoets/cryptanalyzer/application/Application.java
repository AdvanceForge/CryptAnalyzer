package ru.kolomoets.cryptanalyzer.application;


import ru.kolomoets.cryptanalyzer.controller.MainController;
import ru.kolomoets.cryptanalyzer.exception.FileReadException;
import ru.kolomoets.cryptanalyzer.exception.FileWriteException;


public class Application {

    public static void main(String[] args) {

        try {
            MainController controller = new MainController();
            controller.run();
        } catch (FileReadException | FileWriteException e) {
            System.out.println("❌ Ошибка при работе с файлом: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("❌ Произошла непредвиденная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

