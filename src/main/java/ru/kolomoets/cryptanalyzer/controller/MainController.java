package ru.kolomoets.cryptanalyzer.controller;

import ru.kolomoets.cryptanalyzer.core.CaesarCipher;
import ru.kolomoets.cryptanalyzer.in_out.FileService;

import java.nio.file.Path;
import java.util.Scanner;

public class MainController {

    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("Криптоанализарор запущен\n");

        System.out.println("Выберите режим работы");
        System.out.println("1 - шифрование");
        System.out.println("2 - дешиврование (с ключом)");

        System.out.println("Введите номер команды");
        String command = scanner.nextLine();

        switch (command) {
            case "1" -> handleEncryption();
            case "2" -> handleDecryption();
            default -> System.err.println("Неверная команда. Завершение работы.");
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

    // Обработка шифрования — интерактивный режим
    private void handleEncryption() {

        System.out.print("Введите путь к исходному файлу: ");
        String inputPath = scanner.nextLine();

        // Определяем директорию входного файла
        Path path = Path.of(inputPath);
        String parentDir = path.getParent() != null ? path.getParent().toString() : "."; // если файл в текущей директории

        System.out.print("Введите путь для записи зашифрованного текста (оставьте пустым для encrypt.txt): ");
        String outputPath = scanner.nextLine();

        // Если путь не введён, создаём файл encrypt.txt в той же директории
        if (outputPath.isBlank()) {
            outputPath = Path.of(parentDir, "encrypt.txt").toString();
        }

        System.out.print("🗝️ Введите ключ (целое число): ");
        int key = Integer.parseInt(scanner.nextLine());

        encrypt(inputPath, outputPath, key);
        System.out.println("✔️ Текст успешно зашифрован и записан в: " + outputPath);
    }

    private void handleDecryption() {

        System.out.print("Введите путь к исходному файлу: ");
        String inputPath = scanner.nextLine();

        // Определяем директорию входного файла
        Path path = Path.of(inputPath);
        String parentDir = path.getParent() != null ? path.getParent().toString() : "."; // если файл в текущей директории

        System.out.print("Введите путь для записи зашифрованного текста (оставьте пустым для decrypt.txt): ");
        String outputPath = scanner.nextLine();

        // Если путь не введён, создаём файл decrypt.txt в той же директории
        if (outputPath.isBlank()) {
            outputPath = Path.of(parentDir, "decrypt.txt").toString();
        }

        System.out.print("🗝️ Введите ключ (целое число): ");
        int key = Integer.parseInt(scanner.nextLine());

        decrypt(inputPath, outputPath, key);
        System.out.println("✔️ Текст успешно расшифрован и записан в: " + outputPath);
    }
}
