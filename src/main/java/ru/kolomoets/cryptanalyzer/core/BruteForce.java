package ru.kolomoets.cryptanalyzer.core;

import ru.kolomoets.cryptanalyzer.util.Alphabet;

import java.util.Set;

public class BruteForce {

    private BruteForce() {
    }

    public static String bruteForceDecrypt(String input) {
        int maxKey = getMaxAlphabetSize();

        Set<String> frequentWords = Set.of(
                " и ", " не ", " это ", " что ", " на ", " как ", " ты ",
                " он ", " я ", " в ", " с ", " у ",
                " the ", " and ", " of ", " to ", " in ", " is ",
                " it ", " that ", " you ", " we "
        );
        // Инициализируем переменные для хранения лучшего ключа и текста
        int bestKey = -1;
        int maxMatches = 0;
        String bestDecrypted = input;
        for (int key = 0; key < maxKey; key++) {
            // Расшифровываем текст с текущим ключом
            String decrypted = CaesarCipher.decrypt(input, key);
            // Считаем количество совпадений с частыми словами
            int matches = 0;
            for (String word : frequentWords) {
                // Проверяем наличие слова в расшифрованном тексте
                if (decrypted.contains(word)) {
                    matches++;
                }
            }
            if (matches > maxMatches) {
                maxMatches = matches;
                bestKey = key;
                bestDecrypted = decrypted;
            }
        }
        // Возвращаем результат с лучшим ключом, если он найден
        if (bestKey != -1) {
            System.out.println("Подобран ключ: " + bestKey + " (с " + maxMatches + " совпадениями по ключу)");
            return " [KEY FOUND: " + bestKey + "]\n\n" + bestDecrypted;
        } else {
            System.out.println("Не удалось подобрать ключ.");
            return "Не удалось подобрать ключ.";
        }
    }

    private static int getMaxAlphabetSize() {
        return Alphabet.getMaxAlphabetSize();
    }
}
