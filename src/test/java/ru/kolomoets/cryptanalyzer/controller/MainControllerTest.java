package ru.kolomoets.cryptanalyzer.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.kolomoets.cryptanalyzer.core.CaesarCipher;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


class MainControllerTest {

    @TempDir
    Path tempDir;

    @Test
    void testEncryptCommand() throws Exception {
        // 1. Создаём входной файл
        Path inputFile = tempDir.resolve("input.txt");
        Files.writeString(inputFile, "Hello, world!");

        // 2. Выходной файл
        Path outputFile = tempDir.resolve("encrypt.txt");

        // 3. Симулируем пользовательский ввод
        String input = "1\n" + inputFile + "\n\n3\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // 4. Запуск контроллера
        MainController controller = new MainController();
        controller.run();

        // 5. Проверяем, что выходной файл создан
        assertTrue(Files.exists(outputFile));

        // 6. Проверяем содержимое
        String actual = Files.readString(outputFile).trim();
        String expected = CaesarCipher.encrypt("Hello, world!", 3).trim();
        assertEquals(expected, actual);
    }

    @Test
    void testDecryptCommand() throws Exception {
        // 1. Оригинальный текст
        String originalText = "Hello, world!";

        // 2. Шифруем его с ключом 3
        String encrypted = CaesarCipher.encrypt(originalText, 3);

        // 3. Создаём входной файл с зашифрованным текстом
        Path inputFile = tempDir.resolve("encrypted.txt");
        Files.writeString(inputFile, encrypted);

        // 4. Ожидаемый выходной файл
        Path outputFile = tempDir.resolve("decrypt.txt");

        // 5. Симулируем ввод
        String input = "2\n" + inputFile + "\n\n3\n0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        MainController controller = new MainController();
        controller.run();

        //Проверяем, что выходной файл создан
        assertTrue(Files.exists(outputFile));

        //Сравниваем результат с оригиналом
        String actual = Files.readString(outputFile).trim();
        assertEquals(originalText, actual);
    }

}