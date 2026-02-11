package ru.infotecs.process_app.dao;

import ru.infotecs.process_app.model.ProcessInstance;

import java.util.List;
import java.util.Optional;

/**
 * DAO процессов
 */
public interface ProcessInstanceDao {
    /**
     * Сохраняет процесс
     * @param instance процесс
     * @return процесс
     */
    ProcessInstance save(ProcessInstance instance);

    /**
     * Находит процесс по id
     * @param id идентификатор
     * @return процесс
     */
    Optional<ProcessInstance> findById(Long id);

    /**
     * Находит все процессы
     * @return список всех процессов
     */
    List<ProcessInstance> findAll();

    /**
     * Удаляет процесс по id
     * @param id идентификатор
     */
    void deleteById(Long id);

    /**
     * Обновляет результат процесса
     * @param id идентификатор процесса
     * @param result результат
     */
    void updateResult(Long id, String result);

    /**
     * Проверяет существование процесса по id
     * @param id идентификатор
     * @return true, если процесс существует
     */
    boolean existsById(Long id);
}
