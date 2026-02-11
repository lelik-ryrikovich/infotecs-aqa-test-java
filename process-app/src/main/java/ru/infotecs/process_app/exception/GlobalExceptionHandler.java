package ru.infotecs.process_app.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Глобальный обработчик исключений, возникающих в контроллерах
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * Обработка пользовательских исключений
     * @param ex пользовательское исключение
     * @return http ответ
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleAppException(AppException ex) {
        log.error("Application error", ex);

        return ResponseEntity
                .status(ex.getStatus())
                .body(Map.of("code", ex.getStatus(), "error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        log.error("Unexpected error", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", ex.getMessage()));
    }
}
