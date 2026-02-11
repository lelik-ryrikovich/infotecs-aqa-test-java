package ru.infotecs.process_app.dto;

import lombok.Data;
import ru.infotecs.process_app.model.ProcessInstance;

import java.time.LocalDateTime;

@Data
public class ProcessInstanceDto {
    private Long id;
    private LocalDateTime startTime;
    private String initiatorIp;
    private String inputData;
    private String result;

    public static ProcessInstanceDto fromEntityToDto(ProcessInstance entity) {
        ProcessInstanceDto dto = new ProcessInstanceDto();
        dto.setId(entity.getId());
        dto.setStartTime(entity.getStartTime());
        dto.setInitiatorIp(entity.getInitiatorIp());
        dto.setInputData(entity.getInputData());
        dto.setResult(entity.getResult());
        return dto;
    }
}
