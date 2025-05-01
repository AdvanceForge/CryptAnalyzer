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

        for (int key = 1; key < maxKey; key++) {
            String decrypted = CaesarCipher.decrypt(input, key);
            for (String word : frequentWords) {
                if (decrypted.contains(word)) {
                    System.out.println("Подобран ключ: " + key);
                    return " [KEY FOUND: " + key + "]\n\n" + decrypted;
                }
            }
        }
        return "Не удалось подобрать ключ.";
    }

    private static int getMaxAlphabetSize() {
        return Alphabet.getMaxAlphabetSize();
    }
}
