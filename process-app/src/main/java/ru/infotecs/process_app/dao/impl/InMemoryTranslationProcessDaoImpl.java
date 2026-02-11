package ru.infotecs.process_app.dao.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.infotecs.process_app.dao.TranslationProcessDao;
import ru.infotecs.process_app.model.ProcessInstance;
import ru.infotecs.process_app.model.TranslationProcess;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс реализации {@link TranslationProcessDao} в памяти
 */
@Component
@Profile("in-memory")
public class InMemoryTranslationProcessDaoImpl implements TranslationProcessDao {
    /**
     * Хранилище процессов переводов
     */
    private final Map<Long, TranslationProcess> storage = new ConcurrentHashMap<>();

    /**
     * Генератор идентификаторов
     */
    private final AtomicLong idGenerator = new AtomicLong(1);

    public TranslationProcess save(TranslationProcess process) {
        if (process.getId() == null) {
            process.setId(idGenerator.getAndIncrement());
        }
        storage.put(process.getId(), process);

        return process;
    }

    @Override
    public Optional<TranslationProcess> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<TranslationProcess> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    /*@Override
    public void deleteByProcessInstanceId(Long id) {
        findAll().stream()
                .filter(tp -> tp.getProcessInstanceId().equals(id))
                .forEach(tp -> deleteById(tp.getId()));
    }*/
}
