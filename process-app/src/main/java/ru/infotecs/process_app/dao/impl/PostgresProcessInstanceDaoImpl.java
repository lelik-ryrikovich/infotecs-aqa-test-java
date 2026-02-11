package ru.infotecs.process_app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.infotecs.process_app.dao.ProcessInstanceDao;
import ru.infotecs.process_app.model.ProcessInstance;
import ru.infotecs.process_app.repository.ProcessInstanceRepository;

import java.util.List;
import java.util.Optional;

/**
 * Класс реализации {@link ProcessInstanceDao} в Postgres
 */
@Component
@Profile("postgres")
public class PostgresProcessInstanceDaoImpl implements ProcessInstanceDao {
    @Autowired
    private ProcessInstanceRepository repository;

    @Override
    public ProcessInstance save(ProcessInstance instance) {
        return repository.save(instance);
    }

    @Override
    public Optional<ProcessInstance> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ProcessInstance> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateResult(Long id, String result) {
        repository.updateResult(id, result);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
