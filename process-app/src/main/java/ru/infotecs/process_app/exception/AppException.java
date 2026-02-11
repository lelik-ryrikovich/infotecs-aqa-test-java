package ru.infotecs.process_app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Кастомное исключение
 */
@Getter
public abstract class AppException extends RuntimeException {
    private final HttpStatus status;

    /**
     * Конструктор кастомного исключения
     * @param message сообщение
     * @param status http-статус
     */
    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * Конструктор кастомного исключения, которое было вызвано другим исключением
     * @param message сообщение
     * @param status http-статус
     * @param cause исключение, которое вызвало кастомное исключение
     */
    protected AppException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}