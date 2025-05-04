package ru.kolomoets.cryptanalyzer.in_out;

import ru.kolomoets.cryptanalyzer.exception.FileReadException;
import ru.kolomoets.cryptanalyzer.exception.FileWriteException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {

    private FileService() {
    }

    public static String readFile(String input) {

        Path path = Path.of(input);
        // Проверяем, существует ли файл
        if (!Files.exists(path)) {
            throw new FileReadException("Файл не существует: " + input);
        }
        // Читаем файл построчно с буфферизацией
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new FileReadException("Ошибка при чтении файла: " + input, e);
        }
        return text.toString();
    }

    public static void writeFile(String output, String content) {
        Path path = Path.of(output);
        // Записываем содержимое с буфферизацией
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(content);
        } catch (IOException e) {
            throw new FileWriteException("Ошибка при записи файла: " + output, e);
        }
    }
}
