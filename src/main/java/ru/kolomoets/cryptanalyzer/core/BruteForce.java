package ru.kolomoets.cryptanalyzer.core;

import ru.kolomoets.cryptanalyzer.util.Alphabet;

import java.util.Set;

/**
 * Класс BruteForce реализует метод подбора ключа для шифра Цезаря методом полного перебора (brute force).
 * <p>
 * Алгоритм перебирает все возможные значения ключа, расшифровывает текст,
 * и выбирает расшифровку с наибольшим количеством совпадений по частым словам.
 */

public class BruteForce {

    private BruteForce() {
    }

    /**
     * Пытается расшифровать переданный текст методом перебора всех возможных ключей.
     * Для оценки правильности расшифровки используется набор частых слов.
     *
     * @param input Зашифрованный текст
     * @return Расшифрованный текст с подобранным ключом, или сообщение об ошибке
     */

    public static String bruteForceDecrypt(String input) {
        int maxKey = Alphabet.getMaxAlphabetSize();

        // Часто встречающиеся слова в русском и английском языках
        Set<String> frequentWords = Set.of(
                " и ", " не ", " это ", " что ", " на ", " как ", " ты ",
                " он ", " я ", " в ", " с ", " у ",
                " the ", " and ", " of ", " to ", " in ", " is ",
                " it ", " that ", " you ", " we "
        );
        // Инициализируем переменные для хранения лучшего ключа и текста
        int bestKey = -1;                // Наиболее подходящий ключ
        int maxMatches = 0;             // Максимальное количество совпадений
        String bestDecrypted = input;   // Расшифрованный текст с лучшим ключом

        // Перебор всех возможных ключей
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

            // Сохраняем лучший результат
            if (matches > maxMatches) {
                maxMatches = matches;
                bestKey = key;
                bestDecrypted = decrypted;
            }
        }
        // Возвращаем результат с лучшим ключом, если он найден
        if (bestKey != -1) {
            System.out.println("Подобран ключ: " + bestKey + " (с " + maxMatches + " совпадениями по ключу)");
            return "[KEY FOUND: " + bestKey + "]\n\n" + bestDecrypted;
        } else {
            System.out.println("Не удалось подобрать ключ.");
            return "Не удалось подобрать ключ.";
        }
    }
}
