package ru.kolomoets.cryptanalyzer.exception;

/**
 * Исключение, которое выбрасывается при ошибках записи файла.
 * Наследуется от RuntimeException.
 */

public class FileWriteException extends RuntimeException {

    public FileWriteException(String message) {
        super(message);
    }

    /**
     * Конструктор с сообщением и причиной ошибки.
     *
     * @param message текст сообщения ошибки
     * @param cause   причина исключения
     */

    public FileWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}

