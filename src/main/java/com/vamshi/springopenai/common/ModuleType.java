package com.vamshi.springopenai.common;

public enum ModuleType {
    MEDICAL("medical"),
    SYMPTOM_CHECKER("symptom_checker"),
    DIET_NUTRITION("diet_nutrition"),
    MENTAL_HEALTH("mental_health"),
    LEGAL("legal"),
    FINANCE("finance"),
    GENERAL("general");

    private final String promptFile;

    ModuleType(String promptFile) {
        this.promptFile = promptFile;
    }

    public String getPromptFile() {
        return promptFile;
    }
}