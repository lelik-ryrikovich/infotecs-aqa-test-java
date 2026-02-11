package ru.infotecs.process_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.infotecs.process_app.dto.ProcessInstanceDto;
import ru.infotecs.process_app.dto.StartProcessRequest;
import ru.infotecs.process_app.model.ProcessInstance;
import ru.infotecs.process_app.service.ProcessService;

import java.util.List;

@RestController
@RequestMapping("/api/process")
@Tag(name = "Process Managment", description = "Управление процессами")
public class ProcessController {
    @Autowired
    private ProcessService processService;

    @Operation(summary = "Запустить процесс")
    @ApiResponse(
            responseCode = "200",
            description = "Успешно",
            content = @Content(schema = @Schema(implementation = Long.class))
    )
    @PostMapping("/start")
    public ResponseEntity<Long> startProcess(@RequestBody StartProcessRequest request, HttpServletRequest httpRequest) {
        String initiatorIp = httpRequest.getRemoteAddr();

        Long id = processService.startProcess(request.getType(), request.getInputData(), initiatorIp);
        return ResponseEntity.ok(id);
    }

    @Operation(
            summary = "Получить результат процесса",
            description = "Получает результат выполнения процесса по id экземпляра процесса"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Успешно",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    @GetMapping("/result/{id}")
    public ResponseEntity<String> getResult(@PathVariable("id") Long id) {
        String result = processService.getResult(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Получить все запущенные процессы")
    @ApiResponse(
            responseCode = "200",
            description = "Успешно",
            content = @Content(schema = @Schema(implementation = ProcessInstanceDto.class))
    )
    @GetMapping("/get/running")
    public ResponseEntity<List<ProcessInstanceDto>> getAllRunningProcesses() {
        List<ProcessInstance> running = processService.getAllRunningProcesses();

        List<ProcessInstanceDto> dtos = running.stream()
                .map(ProcessInstanceDto::fromEntityToDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Получить все процессы")
    @ApiResponse(
            responseCode = "200",
            description = "Успешно",
            content = @Content(schema = @Schema(implementation = ProcessInstanceDto.class))
    )
    @GetMapping("/get/all")
    public ResponseEntity<List<ProcessInstanceDto>> getAllProcesses() {
        List<ProcessInstance> processes = processService.getAllProcesses();

        List<ProcessInstanceDto> dtos = processes.stream()
                .map(ProcessInstanceDto::fromEntityToDto)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Удалить экземпляр процесса")
    @ApiResponse(responseCode = "204", description = "Успешно")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProcess(@PathVariable Long id) {
        processService.deleteProcess(id);
        return ResponseEntity.noContent().build();
    }

}
