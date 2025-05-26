package ru.kolomoets.cryptanalyzer.core;

import ru.kolomoets.cryptanalyzer.util.Alphabet;
import ru.kolomoets.cryptanalyzer.util.Type;

import java.util.List;

/**
 * Класс CaesarCipher реализует шифр Цезаря.
 * Позволяет зашифровать и расшифровать текст с использованием сдвига по ключу.
 */

public class CaesarCipher {

    private CaesarCipher() {
    }

    /**
     * Метод для шифрования текста с использованием ключа (сдвига).
     * @param input исходный текст
     * @param key сдвиг (ключ)
     * @return зашифрованный текст
     */

    public static String encrypt(String input, int key) {

        StringBuilder result = new StringBuilder();
        for (char ch : input.toCharArray()) {
            // Определяем, к какому алфавиту принадлежит символ
            Type type = Alphabet.detectedType(ch);
            if (type != null) {
                List<Character> alphabet = Alphabet.getAlphabet(type);// Получаем соответствующий алфавит
                // Находим индекс символа в алфавите
                int index = alphabet.indexOf(ch);
                // Вычисляем сдвиг с учетом размера алфавита и возможного выхода за границы
                int shiftedIndex = (index + key) % alphabet.size();
                // Если сдвиг оказался отрицательным — корректируем (чтобы не получить IndexOutOfBounds)
                if (shiftedIndex < 0) {
                    shiftedIndex += alphabet.size();
                }
                // Добавляем символ с новым индексом в результат
                result.append(alphabet.get(shiftedIndex));// Добавляем зашифрованный символ
            } else {
                // Если символ не принадлежит ни одному алфавиту — не шифруем
                result.append(ch);
            }
        }
        return result.toString();
    }

    /**
     * Метод для дешифровки текста с использованием ключа.
     * Просто вызывает метод шифрования с обратным ключом.
     * @param input зашифрованный текст
     * @param key сдвиг (ключ)
     * @return исходный текст
     */

    public static String decrypt(String input, int key) {
        return encrypt(input, -key);
    }
}
