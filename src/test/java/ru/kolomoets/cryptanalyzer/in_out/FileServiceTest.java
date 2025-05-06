package ru.kolomoets.cryptanalyzer.in_out;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.kolomoets.cryptanalyzer.exception.FileReadException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    @TempDir
    Path tempDir; // Временная директория для тестов

    private Path testFile;

    // @BeforeEach означает, что этот метод выполняется перед каждым тестом
    @BeforeEach
    void setup() throws IOException {
        // Создаем временный файл перед каждым тестом
        testFile = tempDir.resolve("test.txt");
        Files.writeString(testFile, "Привет, мир!\nЭто тестовый файл.");
    }

    @Test
    void testReadFile_Success() {
        // Тест успешного чтения файла
        String content = FileService.readFile(testFile.toString());
        // Проверяем, что прочитанный текст совпадает с ожидаемым
        assertEquals("Привет, мир!\nЭто тестовый файл.\n", content,
                "Содержимое файла должно совпадать с ожидаемым");
    }

    @Test
    void testReadFile_FileNotFound() {
        // Тест чтения несуществующего файла
        String nonexistentFile = tempDir.resolve("nonexistent.txt").toString();
        // Проверяем, что вызов readFile для несуществующего файла выбросит FileReadException
        assertThrows(FileReadException.class, () -> FileService.readFile(nonexistentFile), "Должно выброситься FileReadException для несуществующего файла");
    }

    @Test
    void testWriteFile_Success() throws IOException {
        // Тест успешной записи в файл
        String outputFile = tempDir.resolve("output.txt").toString();
        String content = "Это тестовый вывод.";
        FileService.writeFile(outputFile, content);
        String readContent = Files.readString(Path.of(outputFile));
        assertEquals(content, readContent, "Записанное содержимое должно совпадать с ожидаемым");
    }

    @Test
    void testReadFile_EmptyFile() throws IOException {
        // Тест чтения пустого файла
        Path emptyFile = tempDir.resolve("empty.txt");
        Files.createFile(emptyFile);
        String content = FileService.readFile(emptyFile.toString());
        assertEquals("", content, "Пустой файл должен возвращать пустую строку");
    }
}