package ru.kolomoets.cryptanalyzer.core;

import ru.kolomoets.cryptanalyzer.util.Alphabet;
import ru.kolomoets.cryptanalyzer.util.Type;

import java.util.List;

public class CaesarCipher {

    public String encrypt(String input, int key) {
        StringBuilder result = new StringBuilder();
        for (char ch : input.toLowerCase().toCharArray()) {
            // Определяем, к какому алфавиту принадлежит символ
            Type type = Alphabet.detectedType(ch);
            if (type != null) {
                List<Character> alphabet = Alphabet.getAlphabet(type);
                int index = alphabet.indexOf(ch);
                // Вычисляем сдвиг с учетом размера алфавита и возможного выхода за границы
                int shiftedIndex = (index + key) % alphabet.size();
                // Если сдвиг оказался отрицательным — корректируем (чтобы не получить IndexOutOfBounds)
                if (shiftedIndex < 0) {
                    shiftedIndex += alphabet.size();
                }
                result.append(alphabet.get(shiftedIndex));
            } else {
                // Если символ не принадлежит ни одному алфавиту — не шифруем
                result.append(ch);
            }
        }
        return result.toString();
    }

    // Дешифровка — это просто сдвиг в обратную сторону
    public String decrypt(String input, int key) {
        return encrypt(input, -key);
    }

}
