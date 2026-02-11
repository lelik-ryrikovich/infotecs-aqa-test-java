package ru.infotecs.process_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.infotecs.process_app.dao.ProcessInstanceDao;
import ru.infotecs.process_app.model.ProcessType;

/**
 * Сервис процессов типа {@link ProcessType#MODIFY}
 */
@Component
public class ModifyService implements ProcessHandler{
    @Autowired
    private ProcessInstanceDao processInstanceDao;

    /**
     * Маленькие гласные буквы (русский язык)
     */
    private static final String LOWER_CASE_VOWELS_RU = "аеёиоуыэюя";

    /**
     * Большие гласные буквы (русский язык)
     */
    private static final String UPPER_CASE_VOWELS_RU = "АЕЁИОУЫЭЮЯ";

    /**
     * Маленькие гласные буквы (английский язык)
     */
    private static final String LOWER_CASE_VOWELS_EN = "aeiouy";

    /**
     * Большие гласные буквы (английский язык)
     */
    private static final String UPPER_CASE_VOWELS_EN = "AEIOUY";

    @Override
    public void handle(Long processInstanceId, String inputData) {
        String reversed = new StringBuilder(inputData).reverse().toString();
        String result = replaceVowels(reversed);
        // Обновляем result
        processInstanceDao.updateResult(processInstanceId, result);
    }

    /**
     * Циклически заменяет в строке все гласные буквы на следующие по алфавиту
     * @param inputStr исходная строка
     * @return строка с циклически замененными гласными буквами на следующие по алфавиту
     */
    private String replaceVowels(String inputStr) {
        StringBuilder result = new StringBuilder();

        for (char c : inputStr.toCharArray()) {
            result.append(getNextVowel(c));
        }

        return result.toString();
    }

    /**
     * Получает следующую по алфавиту гласную букву циклически, если она гласная, иначе вернет ту же букву
     * @param c символ
     * @return следующая гласная буква или та же буква, если она не гласная
     */
    private char getNextVowel(char c) {
        // Проверяем все наборы гласных
        String[] vowelSets = {LOWER_CASE_VOWELS_RU, UPPER_CASE_VOWELS_RU, LOWER_CASE_VOWELS_EN, UPPER_CASE_VOWELS_EN};

        for (String vowels : vowelSets) {
            int idx = vowels.indexOf(c);
            if (idx != -1) {
                return vowels.charAt((idx + 1) % vowels.length());
            }
        }

        return c; // не гласная
    }

    @Override
    public ProcessType getType() {
        return ProcessType.MODIFY;
    }
}
