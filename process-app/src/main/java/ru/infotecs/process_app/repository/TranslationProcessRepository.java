package ru.infotecs.process_app.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.infotecs.process_app.model.TranslationProcess;

@Profile("postgres")
@Repository
public interface TranslationProcessRepository extends JpaRepository<TranslationProcess, Long> {
}
