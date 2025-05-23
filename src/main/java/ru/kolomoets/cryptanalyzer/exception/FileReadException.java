package ru.kolomoets.cryptanalyzer.exception;

/**
 * Исключение, которое выбрасывается при ошибках чтения файла.
 * Наследуется от RuntimeException.
 */

public class FileReadException extends RuntimeException{

    public FileReadException(String message){
        super(message);
    }

    public FileReadException(String message, Throwable cause){
        super(message, cause);
    }

}
