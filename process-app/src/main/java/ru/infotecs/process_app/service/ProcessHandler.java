package ru.infotecs.process_app.service;

import ru.infotecs.process_app.model.ProcessType;

/**
 * Обработчик процессов
 */
public interface ProcessHandler {
    /**
     * Обрабатывает процесс
     * @param processInstanceId идентификатор экземпляра процесса
     * @param inputData входные данные
     */
    void handle(Long processInstanceId, String inputData);

    /**
     * Получает тип процесса
     * @return тип процесса
     */
    ProcessType getType();
}