package ru.infotecs.process_app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.infotecs.process_app.dao.TranslationProcessDao;
import ru.infotecs.process_app.model.TranslationProcess;
import ru.infotecs.process_app.repository.TranslationProcessRepository;

import java.util.List;
import java.util.Optional;

/**
 * Класс реализации {@link TranslationProcessDao} в Postgres
 */
@Component
@Profile("postgres")
public class PostgresTranslationProcessDaoImpl implements TranslationProcessDao {
    @Autowired
    private TranslationProcessRepository repository;

    @Override
    public TranslationProcess save(TranslationProcess process) {
        return repository.save(process);
    }

    @Override
    public Optional<TranslationProcess> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<TranslationProcess> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /*@Override
    public void deleteByProcessInstanceId(Long id) {
        findAll().stream()
                .filter(tp -> tp.getProcessInstanceId().equals(id))
                .forEach(tp -> deleteById(tp.getId()));
    }*/
}