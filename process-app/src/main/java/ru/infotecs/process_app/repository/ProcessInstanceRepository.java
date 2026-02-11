package ru.infotecs.process_app.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.infotecs.process_app.model.ProcessInstance;

@Repository
@Profile("postgres")
public interface ProcessInstanceRepository extends JpaRepository<ProcessInstance, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE ProcessInstance p SET p.result = :result WHERE p.id = :id")
    void updateResult(Long id, String result);
}