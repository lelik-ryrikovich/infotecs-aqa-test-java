package ru.infotecs.process_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.infotecs.process_app.dao.TranslationProcessDao;
import ru.infotecs.process_app.exception.ProcessNotFoundByIdException;
import ru.infotecs.process_app.model.ProcessInstance;
import ru.infotecs.process_app.model.ProcessType;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.infotecs.process_app.dao.ProcessInstanceDao;
import ru.infotecs.process_app.model.TranslationProcess;

/**
 * Сервис процессов
 */
@Service
public class ProcessService {

    @Autowired
    private ProcessInstanceDao processInstanceDao;

    @Autowired
    private TranslationProcessDao translationProcessDao;

    /**
     * Обработчики процессов
     */
    private final Map<ProcessType, ProcessHandler> handlers = new HashMap<>();

    // Автоматическая регистрация handlers (Spring инжектит все имплементации)
    @Autowired
    public void setHandlers(List<ProcessHandler> handlerList) {
        for (ProcessHandler handler : handlerList) {
            handlers.put(handler.getType(), handler);
        }
    }

    /**
     * Старт процесса
     * @param processType тип процесса
     * @param inputData строка с входными данными
     * @return идентификатор запущенного экземпляра процесса
     */
    public Long startProcess(ProcessType processType, String inputData, String initiatorIp) {
        ProcessHandler handler = handlers.get(processType);

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setStartTime(LocalDateTime.now());
        processInstance.setInitiatorIp(initiatorIp);
        processInstance.setInputData(inputData);
        processInstance.setResult(null);

        processInstance = processInstanceDao.save(processInstance);

        Long processInstanceId = processInstance.getId();

        // Делегируем обработку конкретному сервису
        handler.handle(processInstanceId, inputData);

        return processInstanceId;
    }

    /**
     * Получение результата выполнения процесса по id экземпляра процесса
     * @param id идентификатор экземпляра процесса
     * @return результат выполнения экземпляра процесса
     */
    public String getResult(Long id) {
        if (processInstanceDao.existsById(id)) {
            return processInstanceDao.findById(id).get().getResult();
        } else {
            throw new ProcessNotFoundByIdException(id);
        }
    }

    /**
     * Получение данных о запущенных экземплярах процессов (result == null или running в translationDao)
     * @return список запущенных экземпляров процессов
     */
    public List<ProcessInstance> getAllRunningProcesses() {
        List<Long> runningTranslateIds = translationProcessDao.findAll().stream()
                .map(TranslationProcess::getProcessInstanceId)
                .toList();

        return processInstanceDao.findAll().stream()
                .filter(inst -> inst.getResult() == null || runningTranslateIds.contains(inst.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Получение всех экземпляров процессов
     * @return список всех экземпляров процессов
     */
    public List<ProcessInstance> getAllProcesses() {
        return processInstanceDao.findAll();
    }

    /**
     * Удаление экземпляра процесса
     * @param id идентификатор экземпляра процесса
     */
    @Transactional
    public void deleteProcess(Long id) {
        if (!processInstanceDao.existsById(id)) {
            throw new ProcessNotFoundByIdException(id);
        }

        // Удаляем связанные running записи
        // можно это не делать, т.к. в TranlsateService они по итогу сами удаляются
        //translationProcessDao.deleteByProcessInstanceId(id);

        processInstanceDao.deleteById(id);
    }
}