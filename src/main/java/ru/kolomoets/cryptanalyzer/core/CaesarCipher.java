package ru.kolomoets.cryptanalyzer.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для шифрования/дешифрования текста шифром Цезаря.
 * Поддерживает русские и английские буквы, цифры и основные символы.
 * Все операции выполняются в нижнем регистре.
 */

public class CaesarCipher {

    private static final String RU = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String ENG = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    /**
     * Поддерживаемые символы пунктуации и пробел
     */
    private static final String SYMBOLS = "!@#$%^&*()_+=-<>?|'\":;\\{}[],./~`";
    private static final String ALPHABET = RU + ENG + DIGITS + SYMBOLS;
    /**
     * Таблица для быстрого поиска индексов символов.
     * Ключ: символ (Character)
     * Значение: его индекс в ALPHABET (Integer)
     * <p>
     * Инициализируется один раз при загрузке класса.
     * Позволяет находить индекс символа за O(1) вместо O(n) у String.indexOf()
     */
    private static final Map<Character, Integer> CHAR_INDEX = createCharToIndexMap();

    /**
     * Шифрует текст с использованием указанного ключа.
     *
     * @param text Исходный текст (может содержать любые символы)
     * @param key  Ключ шифрования (положительное целое число)
     * @return Зашифрованный текст в нижнем регистре. Символы не из алфавита остаются без изменений.
     */
    public String encrypt(String text, int key) {
        return converter(text.toLowerCase(), key);
    }

    /**
     * Дешифрует текст (работает только с нижним регистром)
     *
     * @param text Зашифрованный текст в нижнем регистре
     * @param key  Ключ шифрования
     * @return Расшифрованный текст в нижнем регистре
     */
    public String decrypt(String text, int key) {
        return converter(text, -key);
    }

    private String converter(String text, int shift) {
        // Создаем "билдер" для результата
        StringBuilder result = new StringBuilder();
        // Преобразуем текст в массив символов
        char[] characters = text.toCharArray();
        // Обрабатываем каждый символ по очереди
        for (char currentChar : characters) {
            // Проверяем, есть ли символ в нашем алфавите
            if (CHAR_INDEX.containsKey(currentChar)) {
                // Получаем исходную позицию символа
                int originalPos = CHAR_INDEX.get(currentChar);
                //Вычисляем новую позицию
                int newPos = originalPos + shift;
                //Корректируем позицию, если она вне границ
                while (newPos < 0) {
                    newPos += ALPHABET.length();
                }
                newPos = newPos % ALPHABET.length();
                //Получаем новый символ
                char newChar = ALPHABET.charAt(newPos);
                result.append(newChar);
            } else {
                // Если символа нет в алфавите - оставляем без изменений
                result.append(currentChar);
            }
        }
        return result.toString();
    }


    /**
     * Метод для инициализации таблицы поиска CHAR_INDEX.
     * Проходит по всем символам ALPHABET и заполняет Map.
     *
     * @return Заполненная таблица символов
     */
    private static Map<Character, Integer> createCharToIndexMap() {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < ALPHABET.length(); i++) {
            map.put(ALPHABET.charAt(i), i);
        }
        return map;
    }

}
