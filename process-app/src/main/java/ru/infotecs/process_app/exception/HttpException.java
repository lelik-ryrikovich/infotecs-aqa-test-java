package ru.infotecs.process_app.exception;

import org.springframework.http.HttpStatus;

/**
 * Ошибка HTTP статуса
 */
public class HttpException extends AppException {
    public HttpException(HttpStatus status) {
        super("HTTP ошибка: " + status.value() + " " + status.getReasonPhrase(), status);
    }
}
