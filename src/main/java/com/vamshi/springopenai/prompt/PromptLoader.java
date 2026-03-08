package com.vamshi.springopenai.prompt;

import com.vamshi.springopenai.common.ModuleType;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PromptLoader {

    private static final Logger log = LoggerFactory.getLogger(PromptLoader.class);
    private static final String PROMPT_PATH = "prompts/";

    // ConcurrentHashMap for thread-safe hot reload
    private final ConcurrentHashMap<ModuleType, String> promptCache = new ConcurrentHashMap<>();

    @Value("${prompts.reload.enabled:false}")
    private boolean reloadEnabled;

    @PostConstruct
    public void loadAll() {
        for (ModuleType module : ModuleType.values()) {
            loadPrompt(module);
        }
        log.info("All prompts loaded successfully: {}", promptCache.keySet());
    }

    // Hot reload every 60 seconds
    @Scheduled(fixedDelayString = "${prompts.reload.interval-ms:60000}")
    public void reloadAll() {

        if (!reloadEnabled) {
            log.debug("Prompt reload is disabled via properties.");
            return;
        }

        log.info("Hot reloading prompts...");
        for (ModuleType module : ModuleType.values()) {
            loadPrompt(module);
        }
        log.info("Prompts reloaded successfully.");
    }

    private void loadPrompt(ModuleType module) {
        String filePath = PROMPT_PATH + module.getPromptFile() + ".st";
        try {
            ClassPathResource resource = new ClassPathResource(filePath);
            String content = resource.getContentAsString(StandardCharsets.UTF_8);
            promptCache.put(module, content);
            log.debug("Loaded prompt for module: {}", module);
        } catch (IOException e) {
            log.error("Failed to load prompt for module: {} from file: {}", module, filePath, e);
            // Keep existing cached value if reload fails — don't break running app
            promptCache.putIfAbsent(module, "You are a helpful assistant.");
        }
    }

    public String getPrompt(ModuleType moduleType) {
        return promptCache.getOrDefault(moduleType, promptCache.get(ModuleType.GENERAL));
    }
}