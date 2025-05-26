package ru.kolomoets.cryptanalyzer.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс Alphabet предоставляет фиксированные алфавиты
 * для различных типов символов:
 * - Русский алфавит
 * - Английский алфавит
 * - Специальные символы
 * <p>
 * Каждый алфавит представлен как список символов для сохранения порядка
 * и как множество для быстрого определения принадлежности символа.
 */

public class Alphabet {

    private Alphabet() {
    }

    // Алфавиты в виде списков для получения индекса (порядка символов)
    private static final List<Character> RUSSIAN_LOWER_LIST = List.of(
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р',
            'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я');

    private static final List<Character> RUSSIAN_UPPER_LIST = List.of(
            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З',
            'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р',
            'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я');

    private static final List<Character> ENGLISH_LOWER_LIST = List.of(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    );

    private static final List<Character> ENGLISH_UPPER_LIST = List.of(
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z');


    private static final List<Character> SYMBOLS_LIST = List.of(
            ' ', '!', '@', '#', '$', '%', '^', '&',
            '*', '(', ')', '_', '+', '=', '-',
            '<', '>', '?', '|', '\"', ':', ';',
            '\\', '{', '}', '[', ']', ',', '/', '~', '`');//31

    // Для быстрого определения принадлежности символа используем HashSet
    private static final Set<Character> RUSSIAN_LOWER_SET = new HashSet<>(RUSSIAN_LOWER_LIST);
    private static final Set<Character> RUSSIAN_UPPER_SET = new HashSet<>(RUSSIAN_UPPER_LIST);
    private static final Set<Character> ENGLISH_LOWER_SET = new HashSet<>(ENGLISH_LOWER_LIST);
    private static final Set<Character> ENGLISH_UPPER_SET = new HashSet<>(ENGLISH_UPPER_LIST);
    private static final Set<Character> SYMBOLS_SET = new HashSet<>(SYMBOLS_LIST);

    /**
     * Определяет, к какому алфавиту принадлежит символ.
     * Возвращает тип алфавита (enum Type), если найден, иначе — null.
     */
    public static Type detectedType(char ch) {
        if (RUSSIAN_LOWER_SET.contains(ch)) return Type.RUSSIAN_LOWER;
        if (RUSSIAN_UPPER_SET.contains(ch)) return Type.RUSSIAN_UPPER;
        if (ENGLISH_LOWER_SET.contains(ch)) return Type.ENGLISH_LOWER;
        if (ENGLISH_UPPER_SET.contains(ch)) return Type.ENGLISH_UPPER;
        if (SYMBOLS_SET.contains(ch)) return Type.SYMBOLS;
        return null;
    }

    /**
     * Возвращает список символов, соответствующий указанному типу алфавита.
     *
     * @param type тип алфавита
     * @return список символов в нужном алфавите
     */

    public static List<Character> getAlphabet(Type type) {
        return switch (type) {
            case RUSSIAN_LOWER -> RUSSIAN_LOWER_LIST;
            case RUSSIAN_UPPER -> RUSSIAN_UPPER_LIST;
            case ENGLISH_LOWER -> ENGLISH_LOWER_LIST;
            case ENGLISH_UPPER -> ENGLISH_UPPER_LIST;
            case SYMBOLS -> SYMBOLS_LIST;
        };
    }


    /**
     * Возвращает максимальный размер среди всех поддерживаемых алфавитов.
     * Полезно для определения диапазона возможных ключей шифра.
     *
     * @return максимальное количество символов в любом из алфавитов
     */

    public static int getMaxAlphabetSize() {
        int maxSize = 0;
        for (Type type : Type.values()) {
            List<Character> alphabet = getAlphabet(type);
            if (alphabet.size() > maxSize) {
                maxSize = alphabet.size();
            }
        }
        return maxSize;
    }

}
