package ru.infotecs.process_app.model;

/**
 * Тип процесса
 */
public enum ProcessType {
    /**
     * Тип процесса переводчика
     */
    TRANSLATE,
    /**
     * Тип процесса, переворачивающий строки наоборот и заменяющий гласные буквы на следующие по алфавиту
     */
    MODIFY
}
