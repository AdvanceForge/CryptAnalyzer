package ru.kolomoets.cryptanalyzer.in_out;

import ru.kolomoets.cryptanalyzer.exception.FileReadException;
import ru.kolomoets.cryptanalyzer.exception.FileWriteException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {

    private FileService() {
    }

    public static String readFile(String input) {

        try {
            return Files.readString(Path.of(input), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileReadException("Ошибка при чтении файла: " + input, e);
        }
    }

    public static void writeFile(String output, String content) {
        try {
            Files.writeString(Path.of(output), content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileWriteException("Ошибка при записи файла: " + output, e);
        }
    }
}
