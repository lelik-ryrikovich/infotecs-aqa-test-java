package ru.infotecs.process_app.exception;

import org.springframework.http.HttpStatus;

/**
 * Ошибка, указывающая о несуществовании исходного или целевого языка
 */
public class LanguageIsNotExistException extends AppException {
    public LanguageIsNotExistException() {
        super("Указанный язык для перевода или для исходного текста не существует", HttpStatus.BAD_REQUEST);
    }
}