package ru.kolomoets.cryptanalyzer.core;

import ru.kolomoets.cryptanalyzer.util.Alphabet;
import ru.kolomoets.cryptanalyzer.util.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticalAnalyzer {

    private StatisticalAnalyzer() {
    }

    /**
     * Главный метод: находит вероятный ключ для дешифровки текста на основе статистики.
     * Предполагается, что самым частым символом в языке является 'о' (рус), 'e' (англ) или ' ' (символы).
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

        // Работаем только с основным алфавитом (mainType)
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
     * Определяет, символы какого алфавита чаще всего встречаются в тексте.
     * Приводит текст к нижнему регистру для корректного определения.
     */
    public static Type detectMainAlphabet(String text) {
        // Создаем словарь для подсчета символов по алфавитам
        Map<Type, Integer> typeCounts = new HashMap<>();

        // Проходим по каждому символу текста
        for (char ch : text.toLowerCase().toCharArray()) {
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
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mainType = entry.getKey();
            }
        }
        return mainType;
    }

    /**
     * Находит самый частый символ в тексте для указанного алфавита.
     * Предполагается, что текст уже приведён к нижнему регистру.
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
     * Возвращает индекс предполагаемого самого частого символа в алфавите.
     * Используется для определения ключа шифрования.
     */
    private static int getExpectedIndex(Type type, List<Character> alphabet) {
        // Возвращаем позицию 'о' для русского алфавита
        if (type == Type.RUSSIAN) return alphabet.indexOf('о');
        // Возвращаем позицию 'e' для английского алфавита
        if (type == Type.ENGLISH) return alphabet.indexOf('e');
        // Возвращаем позицию пробела для символов
        return alphabet.indexOf(' ');
    }
}