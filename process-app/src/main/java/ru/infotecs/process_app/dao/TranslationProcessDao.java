package ru.infotecs.process_app.dao;

import ru.infotecs.process_app.model.TranslationProcess;

import java.util.List;
import java.util.Optional;

/**
 * DAO процессов перевода
 */
public interface TranslationProcessDao {

    /**
     * Сохраняет процесс перевода
     * @param process процесс перевода
     * @return процесс перевода
     */
    TranslationProcess save(TranslationProcess process);

    /**
     * Находит процесс перевода по id
     * @param id идентификатор
     * @return процесс перевода
     */
    Optional<TranslationProcess> findById(Long id);

    /**
     * Находит все процессы перевода
     * @return список всех процессов перевода
     */
    List<TranslationProcess> findAll();

    /**
     * Удаляет процесс перевода по id
     * @param id идентификатор процесса перевода
     */
    void deleteById(Long id);

    //void deleteByProcessInstanceId(Long id);

    /**
     * Удаляет процесс перевода по id процесса
     * @param id идентификатор процесса
     */
    /*default void deleteByProcessInstanceId(Long id) {
        findAll().stream()
                .filter(tp -> tp.getProcessInstanceId().equals(id))
                .forEach(tp -> deleteById(tp.getId()));
    }*/
}
