package ru.infotecs.process_app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class LanguageMapInitializerService {
    /**
     * Карта доступных языков
     */
    @Getter
    private static final Map<String, String> languages = new ConcurrentHashMap<>();

    /**
     * Указывает, был ли загружен список языков
     */
    private volatile boolean isLoaded = false;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * URL API для получения всех доступных языков
     */
    private static final String GET_ALL_LANGUAGES_API_URL = "https://translate.plausibility.cloud/api/v1/languages";

    /**
     * Загружает все доступные языки в {@link #languages} при запуске приложения
     */
    @EventListener(ApplicationReadyEvent.class)
    public void loadLanguagesOnStartup() {
        // Если языки уже были загружены, то ничего не делаем
        if (isLoaded) {
            return;
        }
        log.info("We start loading languages after Spring is fully initialized..");

        // Попытка получить все языки
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(GET_ALL_LANGUAGES_API_URL, String.class);

            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode languagesNode = rootNode.get("languages");

            if (languagesNode.isArray()) {
                for (JsonNode languageNode : languagesNode) {
                    String code = languageNode.get("code").asText();
                    String name = languageNode.get("name").asText();
                    languages.put(code, name);
                }
            }

            log.info("Languages successfully loaded! Total: " + languages.size());

            log.info("Available languages:");
            languages.forEach((code, name) ->
                    log.info("  - {} ({})", name, code));

            isLoaded = true;

        } catch (Exception e) {
            log.error("Error loading languages!", e);
            loadFallbackLanguages();

            log.info("Available languages:");
            languages.forEach((code, name) ->
                    log.info("  - {} ({})", name, code));

            isLoaded = true;
        }
    }

    /**
     * Загружает резервные языки в {@link #languages}
     */
    private void loadFallbackLanguages() {
        Map<String, String> fallback = Map.of(
                "auto", "Detect",
                "ru", "Russian",
                "en", "English"
        );
        languages.putAll(fallback);
        log.info("We use a reserve list (" + fallback.size() + " languages)");
    }
}
