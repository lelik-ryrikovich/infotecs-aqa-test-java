package ru.infotecs.process_app.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Сущность запущенного процесса перевода
 */
@Entity
@Data
@Table(name = "translation_processes")
public class TranslationProcess {
    /**
     * Идентификатор процесса перевода
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Идентификатор экземпляра процесса
     */
    @Column(name = "process_instance_id")
    private Long processInstanceId;
}
