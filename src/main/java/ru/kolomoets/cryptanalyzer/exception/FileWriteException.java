package ru.kolomoets.cryptanalyzer.exception;

public class FileWriteException extends RuntimeException {

    public FileWriteException(String message) {
        super(message);
    }

    public FileWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}

