package ru.infotecs.process_app.dto;

import lombok.Data;
import ru.infotecs.process_app.model.ProcessType;

@Data
public class StartProcessRequest {
    /**
     * Тип процесса
     */
    private ProcessType type;
    /**
     * Строка с входными данными (можно использовать русские символы
     * и пробелы)
     */
    private String inputData;
}
