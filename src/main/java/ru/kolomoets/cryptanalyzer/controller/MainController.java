package ru.kolomoets.cryptanalyzer.controller;

import ru.kolomoets.cryptanalyzer.core.BruteForce;
import ru.kolomoets.cryptanalyzer.core.CaesarCipher;
import ru.kolomoets.cryptanalyzer.core.StatisticalAnalyzer;
import ru.kolomoets.cryptanalyzer.exception.FileReadException;
import ru.kolomoets.cryptanalyzer.exception.FileWriteException;
import ru.kolomoets.cryptanalyzer.in_out.FileService;
import ru.kolomoets.cryptanalyzer.util.Alphabet;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class MainController {

    private final Scanner scanner = new Scanner(System.in);

    public void run() {

        System.out.println("Криптоанализатор запущен\n");

        boolean running = true;
        while (running) {

            System.out.println("Выберите режим работы");
            System.out.println("0 - завершение работы");
            System.out.println("1 - шифрование");
            System.out.println("2 - дешифрование (с ключом)");
            System.out.println("3 - взлом (brute force)");
            System.out.println("4 - статистический анализ");

            System.out.println("Введите номер команды");
            String command = scanner.nextLine();

            switch (command) {
                case "0" -> {
                    System.out.println("Программа завершена");
                    running = false;
                }
                case "1" -> handleEncryption();
                case "2" -> handleDecryption();
                case "3" -> handleBruteForce();
                case "4" -> handleStatisticalAnalyzer();
                default -> System.err.println("Неверная команда.");
            }
            System.out.println("\n");
        }
    }

    public void encrypt(String inputPath, String outputPath, int key) {
        // Читаем исходный текст из файла по указанному пути
        String text = FileService.readFile(inputPath);
        // Шифруем прочитанный текст с использованием шифра Цезаря и заданного ключа
        String encrypted = CaesarCipher.encrypt(text, key);
        // Записываем зашифрованный текст в новый файл по указанному пути
        FileService.writeFile(outputPath, encrypted);
    }

    public void decrypt(String inputPath, String outputPath, int key) {
        String text = FileService.readFile(inputPath);
        String decrypted = CaesarCipher.decrypt(text, key);
        FileService.writeFile(outputPath, decrypted);
    }

    public void bruteForce(String inputPath, String outputPath) {
        String text = FileService.readFile(inputPath);
        String decrypted = BruteForce.bruteForceDecrypt(text);
        FileService.writeFile(outputPath, decrypted);
    }

    public void statisticalAnalyze(String inputPath, String outputPath) {
        String text = FileService.readFile(inputPath);
        int key = StatisticalAnalyzer.findProbableKey(text); // Находим ключ
        System.out.println("Подобран ключ: " + key); // Выводим ключ в консоль
        String decrypted = CaesarCipher.decrypt(text, key);
        String result = "[KEY FOUND: " + key + "]\n\n" + decrypted;
        FileService.writeFile(outputPath, result);
    }

    // Обработка шифрования — интерактивный режим
    private void handleEncryption() {
        try {
            System.out.print("Введите путь к исходному файлу: ");
            String inputPath = scanner.nextLine();

            Path path = Path.of(inputPath);
            if (!Files.exists(path)) {
                System.err.println("❌ Файл не существует: " + inputPath);
                return;
            }
            // Определяем директорию входного файла
            String parentDir = path.getParent() != null ? path.getParent().toString() : "."; // если файл в текущей директории

            System.out.print("Введите путь для записи зашифрованного текста (оставьте пустым для encrypt.txt): ");
            String outputPath = scanner.nextLine();

            // Если путь не введён, создаём файл encrypt.txt в той же директории
            if (outputPath.isBlank()) {
                outputPath = Path.of(parentDir, "encrypt.txt").toString();
            }
            System.out.print("🗝️ Введите ключ (целое число): ");

            int key = Integer.parseInt(scanner.nextLine());

            if (isValidKey(key)) {
                encrypt(inputPath, outputPath, key);
                System.out.println("✔️ Текст успешно зашифрован и записан в: " + outputPath);
            }

        } catch (NumberFormatException e) {
            System.err.println("❌ Ключ должен быть целым числом");
        } catch (FileReadException e) {
            System.err.println("❌ Не удалось прочитать файл: " + e.getMessage());
        } catch (FileWriteException e) {
            System.err.println("❌ Не удалось записать файл: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Произошла непредвиденная ошибка. " + e.getMessage());
            e.printStackTrace(); // Выводит стек вызовов для отладки
        }
    }

    private void handleDecryption() {
        try {
            System.out.print("Введите путь к исходному файлу: ");
            String inputPath = scanner.nextLine();

            Path path = Path.of(inputPath);
            if (!Files.exists(path)) {
                System.err.println("❌ Файл не существует: " + inputPath);
                return;
            }
            // Определяем директорию входного файла
            String parentDir = path.getParent() != null ? path.getParent().toString() : "."; // если файл в текущей директории

            System.out.print("Введите путь для записи расшифрованного текста (оставьте пустым для decrypt.txt): ");
            String outputPath = scanner.nextLine();

            // Если путь не введён, создаём файл decrypt.txt в той же директории
            if (outputPath.isBlank()) {
                outputPath = Path.of(parentDir, "decrypt.txt").toString();
            }

            System.out.print("🗝️ Введите ключ (целое число): ");
            int key = Integer.parseInt(scanner.nextLine());
            if (isValidKey(key)) {
                decrypt(inputPath, outputPath, key);
                System.out.println("✔️ Текст успешно расшифрован и записан в: " + outputPath);
            }
        } catch (NumberFormatException e) {
            System.err.println("❌ Ключ должен быть целым числом");
        } catch (FileReadException e) {
            System.err.println("❌ Не удалось прочитать файл: " + e.getMessage());
        } catch (FileWriteException e) {
            System.err.println("❌ Не удалось записать файл: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Произошла непредвиденная ошибка. " + e.getMessage());
            e.printStackTrace(); // Выводит стек вызовов для отладки
        }

    }

    private void handleBruteForce() {
        try {
            System.out.print("Введите путь к зашифрованному файлу: ");
            String inputPath = scanner.nextLine();

            Path path = Path.of(inputPath);
            if (!Files.exists(path)) {
                System.err.println("❌ Файл не существует: " + inputPath);
                return;
            }
            String parentDir = path.getParent() != null ? path.getParent().toString() : ".";

            System.out.println("Введите путь для сохранения результата (оставьте пустым для brute_force.txt): ");
            String outputPath = scanner.nextLine();
            if (outputPath.isBlank()) {
                outputPath = Path.of(parentDir, "brute_force.txt").toString();
            }
            bruteForce(inputPath, outputPath);
            System.out.println("✔️ Brute force завершён. Результат записан в: " + outputPath);
        } catch (FileReadException e) {
            System.err.println("❌ Не удалось прочитать файл: " + e.getMessage());
        } catch (FileWriteException e) {
            System.err.println("❌ Не удалось записать файл: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Произошла непредвиденная ошибка. " + e.getMessage());
            e.printStackTrace(); // Выводит стек вызовов для отладки
        }
    }

    private void handleStatisticalAnalyzer() {
        try {
            System.out.print("Введите путь к зашифрованному файлу: ");
            String inputPath = scanner.nextLine();

            Path path = Path.of(inputPath);
            if (!Files.exists(path)) {
                System.err.println("❌ Файл не существует: " + inputPath);
                return;
            }
            String parentDir = path.getParent() != null ? path.getParent().toString() : ".";

            System.out.print("Введите путь для сохранения результата (оставьте пустым для stat_analyze.txt): ");
            String outputPath = scanner.nextLine();
            if (outputPath.isBlank()) {
                outputPath = Path.of(parentDir, "stat_analyze.txt").toString();
            }
            statisticalAnalyze(inputPath, outputPath);
            System.out.println("✔️ Статистический анализ завершён. Результат записан в: " + outputPath);
        } catch (FileReadException e) {
            System.err.println("❌ Не удалось прочитать файл: " + e.getMessage());
        } catch (FileWriteException e) {
            System.err.println("❌ Не удалось записать файл: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Произошла непредвиденная ошибка. " + e.getMessage());
            e.printStackTrace(); // Выводит стек вызовов для отладки
        }
    }

    private static boolean isValidKey(int key) {
        int maxAlphabetSize = Alphabet.getMaxAlphabetSize();
        if (key <= 0 || key >= maxAlphabetSize) {
            System.err.println("❌ Ошибка: ключ должен быть в диапазоне от 1 до " + (maxAlphabetSize - 1));
            return false;
        }
        return true;
    }
}
