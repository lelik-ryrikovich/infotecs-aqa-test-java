package ru.infotecs.process_app.exception;

import org.springframework.http.HttpStatus;

public class ProcessNotFoundByIdException extends AppException {
    public ProcessNotFoundByIdException(Long processId) {
        super("Процесс с id = " + processId + " не найден", HttpStatus.NOT_FOUND);
    }
}
