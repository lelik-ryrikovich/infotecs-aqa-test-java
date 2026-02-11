package ru.infotecs.process_app.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Экземпляр процесса
 */
@Entity
@Data
@Table(name = "process_instances")
public class ProcessInstance {
    /**
     * Идентификатор процесса
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Время старта
     */
    @Column(name = "start_time")
    private LocalDateTime startTime;
    /**
     * IP инициатора
     */
    @Column(name = "initiator_ip")
    private String initiatorIp;
    /**
     * Входные данные
     */
    @Column(name = "input_data")
    private String inputData;
    /**
     * Результат
     */
    @Column(name = "result")
    private String result;
}
