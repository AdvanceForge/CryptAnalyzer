package ru.kolomoets.cryptanalyzer.core;

import ru.kolomoets.cryptanalyzer.util.Alphabet;
import ru.kolomoets.cryptanalyzer.util.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для статистического анализа зашифрованного текста.
 * Используется метод частотного анализа: предположение, что
 * самый часто встречающийся символ в тексте соответствует
 * самому частому символу языка (например, 'о' или 'О' в русском,
 * 'e' или 'E' в английском).
 *
 */

public class StatisticalAnalyzer {

    private StatisticalAnalyzer() {
    }

    /**
     * Главный метод анализа: определяет вероятный ключ шифра Цезаря на основе статистики.
     *
     * @param text Зашифрованный текст
     * @return Вероятный ключ
     * @throws IllegalArgumentException если невозможно определить алфавит текста
     * @throws IllegalStateException если не удалось вычислить ключ
     */

    public static int findProbableKey(String text) {
        // Определяем основной алфавит текста (например, RUSSIAN, ENGLISH или SYMBOLS)
        Type mainType = detectMainAlphabet(text);
        // Если алфавит не определен, выбрасываем ошибку
        if (mainType == null) {
            throw new IllegalArgumentException("Текст не содержит символов из известных алфавитов.");
        }
        // Создаем словарь для хранения ключа (хотя теперь он будет содержать только один ключ)
        Map<Type, Integer> potentialKeys = new HashMap<>();

        // Ищем самый часто встречающийся символ в тексте, но только среди символов выбранного алфавита
        Character mostFrequent = findMostFrequentChar(text, mainType);
        // Если символ найден, продолжаем
        if (mostFrequent != null) {
            // Получаем список символов для основного алфавита
            List<Character> alphabet = Alphabet.getAlphabet(mainType);
            // Находим позицию самого частого символа в алфавите
            int mostFrequentIndex = alphabet.indexOf(mostFrequent);
            // Находим позицию ожидаемого частого символа ('о', 'e' или ' ')
            int expectedIndex = getExpectedIndex(mainType, alphabet);

            // Проверяем, что обе позиции найдены
            if (mostFrequentIndex != -1 && expectedIndex != -1) {
                // Вычисляем ключ как разницу между позициями с учетом циклического сдвига
                int key = (mostFrequentIndex - expectedIndex + alphabet.size()) % alphabet.size();
                // Сохраняем ключ для основного алфавита
                potentialKeys.put(mainType, key);
            }
            System.out.println("Преобладающий алфавит: " + mainType);
            System.out.println("Самый частый символ: " + "'" + mostFrequent + "'");
        }

        // Проверяем, нашли ли мы ключ
        if (!potentialKeys.isEmpty()) {
            // Возвращаем ключ для основного алфавита
            return potentialKeys.get(mainType);
        }

        // Если ключ не найден, выбрасываем ошибку
        throw new IllegalStateException("Не удалось определить ключ.");
    }

    /**
     * Определяет основной алфавит текста — тот, символы которого встречаются чаще всего.
     * Например, может определить, что в тексте преобладает русский, английский или набор символов.
     *
     * @param text Исходный текст для анализа
     * @return Тип алфавита (Type), который встречается чаще всего в тексте,
     *         или null, если текст не содержит известных символов
     */

    public static Type detectMainAlphabet(String text) {
        // Создаем словарь для подсчета символов по алфавитам
        Map<Type, Integer> typeCounts = new HashMap<>();

        // Проходим по каждому символу текста
        for (char ch : text.toCharArray()) {
            // Определяем тип алфавита для символа (RUSSIAN, ENGLISH, SYMBOLS)
            Type type = Alphabet.detectedType(ch);
            // Если тип определен, увеличиваем счетчик для этого алфавита
            if (type != null) {
                typeCounts.put(type, typeCounts.getOrDefault(type, 0) + 1);
            }
        }

        // Если словарь пуст, возвращаем null (текст не содержит известных символов)
        if (typeCounts.isEmpty()) {
            return null;
        }

        // Находим алфавит с наибольшим количеством символов
        Type mainType = null;
        int maxCount = 0;
        for (Map.Entry<Type, Integer> entry : typeCounts.entrySet()) {
            // Получаем количество символов для текущего алфавита
            if (entry.getValue() > maxCount) {
                // Если текущее количество символов больше максимума, обновляем максимум
                maxCount = entry.getValue();
                // Запоминаем текущий алфавит как основной
                mainType = entry.getKey();
            }
        }
        return mainType;
    }
    /**
     * Находит самый часто встречающийся символ в тексте для указанного алфавита.
     *
     * @param text Исходный текст для анализа
     * @param type Тип алфавита, по которому ищем самый частый символ
     * @return Символ, который встречается чаще всего в данном алфавите,
     *         или null, если таких символов в тексте нет
     */

    public static Character findMostFrequentChar(String text, Type type) {
        // Создаем словарь для подсчета частоты символов
        Map<Character, Integer> charCount = new HashMap<>();
        // Проходим по каждому символу текста
        for (char ch : text.toCharArray()) {
            // Если символ принадлежит нужному алфавиту, увеличиваем его счетчик
            if (Alphabet.detectedType(ch) == type) {
                charCount.put(ch, charCount.getOrDefault(ch, 0) + 1);
            }
        }

        // Ищем символ с максимальной частотой
        Character mostFrequent = null;
        int maxCount = 0;
        for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }
        return mostFrequent;
    }

    /**
     * Возвращает индекс предполагаемого самого частого символа в алфавите с учётом регистра.
     * Для русского — ожидается символ 'о' или 'О',
     * для английского — 'e' или 'E',
     * для символов — пробел ' '.
     *
     * @param type Тип алфавита с регистром
     * @param alphabet Список символов алфавита
     * @return Индекс предполагаемого самого частого символа,
     *         либо -1, если символ не найден
     */

    private static int getExpectedIndex(Type type, List<Character> alphabet) {
        // Возвращаем позицию 'о' для русского алфавита
        if (type == Type.RUSSIAN_LOWER) return alphabet.indexOf('о');
        if (type == Type.RUSSIAN_UPPER) return alphabet.indexOf('О');
        // Возвращаем позицию 'e' для английского алфавита
        if (type == Type.ENGLISH_LOWER) return alphabet.indexOf('e');
        if (type == Type.ENGLISH_UPPER) return alphabet.indexOf('E');
        // Возвращаем позицию пробела для символов
        return alphabet.indexOf(' ');
    }
}