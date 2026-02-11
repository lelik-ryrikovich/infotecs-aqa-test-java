package ru.infotecs.process_app.dao.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.infotecs.process_app.dao.ProcessInstanceDao;
import ru.infotecs.process_app.model.ProcessInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс реализации {@link ProcessInstanceDao} в памяти
 */
@Component
@Profile("in-memory")
public class InMemoryProcessInstanceDaoImpl implements ProcessInstanceDao {
    /**
     * Хранилище процессов
     */
    private final Map<Long, ProcessInstance> storage = new ConcurrentHashMap<>();

    /**
     * Генератор идентификаторов
     */
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ProcessInstance save(ProcessInstance instance) {
        if (instance.getId() == null) {
            instance.setId(idGenerator.getAndIncrement());
        }
        storage.put(instance.getId(), instance);

        return instance;
    }

    @Override
    public Optional<ProcessInstance> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<ProcessInstance> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public void updateResult(Long id, String result) {
        ProcessInstance instance = storage.get(id);
        if (instance != null) {
            instance.setResult(result);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }
}
