package ru.infotecs.process_app.exception;

import org.springframework.http.HttpStatus;

/**
 * Ошибка перевода
 */
public class TranslationException extends AppException {
    public TranslationException(Throwable cause) {
        super("Ошибка перевода: " + cause.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }
}
