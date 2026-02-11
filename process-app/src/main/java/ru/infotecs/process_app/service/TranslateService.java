package ru.infotecs.process_app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.infotecs.process_app.dao.ProcessInstanceDao;
import ru.infotecs.process_app.dao.TranslationProcessDao;
import ru.infotecs.process_app.exception.*;
import ru.infotecs.process_app.model.ProcessType;
import ru.infotecs.process_app.model.TranslationProcess;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Сервис процессов типа {@link ProcessType#TRANSLATE}
 */
@Component
@Slf4j
public class TranslateService implements ProcessHandler {
    /**
     * URL API перевода
     */
    private static final String TRANSLATION_API_URL = "https://translate.plausibility.cloud/api/v1";

    /**
     * Код исходного языка (автоопределение)
     */
    private static final String SOURCE_LANGUAGE_CODE = "auto";

    /**
     * Код целевого языка (изначально русский)
     */
    private static final String TARGET_LANG_CODE = "ru";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ProcessInstanceDao processInstanceDao;

    @Autowired
    private TranslationProcessDao translationProcessDao;

    @Autowired
    private Executor taskExecutor;

    @Override
    public void handle(Long processInstanceId, String inputData) {
        TranslationProcess translationProcess = new TranslationProcess();
        translationProcess.setProcessInstanceId(processInstanceId);
        translationProcess = translationProcessDao.save(translationProcess);

        Long translationProcessId = translationProcess.getId();

        taskExecutor.execute(() -> {
            try {
                String result = translate(inputData);
                processInstanceDao.updateResult(processInstanceId, result);
            } catch (Exception e) {
                log.error("Translation process {} failed for process instance {}", translationProcessId, processInstanceId, e);
                processInstanceDao.updateResult(processInstanceId, e.getMessage());
            } finally {
                translationProcessDao.deleteById(translationProcessId);
            }
        });
    }

    /**
     * Перевод с {@link #SOURCE_LANGUAGE_CODE} автоматически определенного языка на {@link #TARGET_LANG_CODE} русский
     * @param inputData входные данные
     * @return перевод
     */
    public String translate(String inputData) {
        return translate(inputData, SOURCE_LANGUAGE_CODE, TARGET_LANG_CODE);
    }

    /**
     * Перевод с одного языка на другой
     * @param inputData входные данные
     * @param sourceLangCode код исходного языка
     * @param targetLangCode код целевого языка
     * @return перевод
     */
    public String translate(String inputData, String sourceLangCode, String targetLangCode) {
        Map<String, String> languagesMap = LanguageMapInitializerService.getLanguages();

        // Если нет исходного и целевого языка в предложенных
        if (!(languagesMap.containsKey(sourceLangCode) && languagesMap.containsKey(targetLangCode))) {
            throw new LanguageIsNotExistException();
        }

        String encodedText = URLEncoder.encode(inputData, StandardCharsets.UTF_8);
        String url = TRANSLATION_API_URL + "/" + sourceLangCode + "/" + targetLangCode + "/" + encodedText;

        // Попытка выполнить запрос
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Если запрос неуспешный
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new HttpException((HttpStatus) response.getStatusCode());
            }

            // Считываем перевод из ответа
            JsonNode json = objectMapper.readTree(response.getBody());
            JsonNode translation = json.get("translation");
            return URLDecoder.decode(translation.asText(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new TranslationException(e);
        }
    }

    @Override
    public ProcessType getType() {
        return ProcessType.TRANSLATE;
    }
}