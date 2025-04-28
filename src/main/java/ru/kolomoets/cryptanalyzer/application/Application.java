package ru.kolomoets.cryptanalyzer.application;

import ru.kolomoets.cryptanalyzer.controller.MainController;
import ru.kolomoets.cryptanalyzer.core.CaesarCipher;

public class Application {

    public static void main( String[] args ) {
        CaesarCipher cipher1 = new CaesarCipher();
        String original1 = "Привет, World! 123";
        String encrypted1 = cipher1.encrypt(original1, 3); // "тлйзёч, zruog! 456"
        String decrypted1 = cipher1.decrypt(encrypted1, 3); // "привет, world! 123"
        System.out.println(encrypted1);
        System.out.println(decrypted1);

        CaesarCipher cipher = new CaesarCipher();

        System.out.println("=== Тест шифра Цезаря ===");

        // 1. Тест базового шифрования
        String original = "привет мир! 123";
        int key = 5;
        System.out.println("\n1. Базовый тест:");
        testEncryption(cipher, original, key);

        // 2. Тест границ алфавита
        System.out.println("\n2. Тест границ алфавита:");
        testBoundaries(cipher);

        // 3. Тест больших ключей
        System.out.println("\n3. Тест большого ключа (key=100):");
        testEncryption(cipher, "абв", 100);

        // 4. Тест регистра
        System.out.println("\n4. Тест регистра:");
        String mixedCase = "Привет World";
        String encrypted = cipher.encrypt(mixedCase, 3);
        System.out.println("Оригинал: " + mixedCase);
        System.out.println("Зашифровано: " + encrypted);
    }

    private static void testEncryption(CaesarCipher cipher, String text, int key) {
        System.out.println("Оригинал: " + text);

        String encrypted = cipher.encrypt(text, key);
        System.out.println("Зашифровано (" + key + "): " + encrypted);

        String decrypted = cipher.decrypt(encrypted, key);
        System.out.println("Расшифровано: " + decrypted);

        boolean success = text.equals(decrypted);
        System.out.println(success ? "✓ Успех" : "✗ Ошибка");
    }

    private static void testBoundaries(CaesarCipher cipher) {
        // Первый символ алфавита
        System.out.println("'а' + 3: " + cipher.encrypt("а", 3));

        // Последний символ (предположим, это 'я')
        System.out.println("'я' + 4: " + cipher.encrypt("я", 4));

        // Спецсимволы
        System.out.println("'!' + 1: " + cipher.encrypt("!", 1));
    }
}


