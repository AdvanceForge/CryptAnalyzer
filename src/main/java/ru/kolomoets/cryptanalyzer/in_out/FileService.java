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

    /**
     * Приватный конструктор, чтобы предотвратить создание экземпляров класса.
     */

    private FileService() {
    }

    /**
     * Читает весь текст из файла по указанному пути.
     *
     * @param input путь к входному файлу
     * @return содержимое файла в виде строки
     * @throws FileReadException в случае ошибки чтения файла
     */

    public static String readFile(String input) {
        Path path = Path.of(input);
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

    /**
     * Записывает строковое содержимое в файл по указанному пути.
     *
     * @param output  путь к выходному файлу
     * @param content содержимое для записи в файл
     * @throws FileWriteException в случае ошибки записи файла
     */

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
