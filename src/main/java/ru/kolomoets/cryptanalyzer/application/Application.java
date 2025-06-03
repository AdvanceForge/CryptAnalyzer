package ru.kolomoets.cryptanalyzer.application;


import ru.kolomoets.cryptanalyzer.controller.MainController;
import ru.kolomoets.cryptanalyzer.view.GraphicalUI;

import javax.swing.*;
import java.util.Scanner;

/**
 * Класс Application — точка входа в программу.
 * Здесь запускается основной контроллер приложения.
 */

public class Application {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите режим:");
        System.out.println("1 - Консольный");
        System.out.println("2 - Графический");

        String command = scanner.nextLine();

        switch (command) {
            case "1" -> {
                MainController controller = new MainController();
                controller.run();
            }
            case "2" -> SwingUtilities.invokeLater(() -> new GraphicalUI().createAndShow());
            default -> System.out.println("❌ Неверный выбор. Запуск отменён.");
        }
    }
}

