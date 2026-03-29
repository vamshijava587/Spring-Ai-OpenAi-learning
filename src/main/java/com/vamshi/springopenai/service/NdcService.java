package com.vamshi.springopenai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vamshi.springopenai.common.ModelType;
import com.vamshi.springopenai.common.ModuleType;
import com.vamshi.springopenai.config.ChatClientRouter;
import com.vamshi.springopenai.model.NdcRecord;
import com.vamshi.springopenai.model.NdcResponse;
import com.vamshi.springopenai.prompt.PromptLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class NdcService {

    private static final Logger log = LoggerFactory.getLogger(NdcService.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final ChatClientRouter router;
    private final PromptLoader promptLoader;

    public NdcService(ChatClientRouter router, PromptLoader promptLoader) {
        this.router = router;
        this.promptLoader = promptLoader;
    }

    public NdcResponse lookup(String query, ModelType modelType) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("NDC query must not be empty");
        }

        String systemPrompt = promptLoader.getPrompt(ModuleType.NDC);
        long startTime = System.currentTimeMillis();
        log.info("Starting NDC lookup for query: '{}' using model: {}", query, modelType);

        try {
            // Use .content() instead of .entity() — works for ALL models including small Ollama
            String rawJson = router.getClient(modelType)
                    .prompt()
                    .system(systemPrompt)
                    .user(query)
                    .call()
                    .content();

            long duration = System.currentTimeMillis() - startTime;
            log.info("NDC lookup completed in {}ms", duration);

            List<NdcRecord> drugs = parseSmartly(rawJson);
            return NdcResponse.of(drugs);

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            log.error("NDC lookup failed after {}ms. Error: {}", duration, e.getMessage());
            return NdcResponse.of(Collections.emptyList());
        }
    }

    // Handles both response shapes models return:
    // Shape 1 (OpenAI):  { "drugs": [ {...}, {...} ] }
    // Shape 2 (Ollama):  [ {...}, {...} ]
    private List<NdcRecord> parseSmartly(String rawJson) {
        if (rawJson == null || rawJson.isBlank()) return Collections.emptyList();
        try {
            // Strip markdown code fences if model wrapped the JSON
            String clean = rawJson.replaceAll("(?s)```json|```", "").trim();
            JsonNode root = MAPPER.readTree(clean);

            if (root.isArray()) {
                return MAPPER.convertValue(root,
                        MAPPER.getTypeFactory().constructCollectionType(List.class, NdcRecord.class));
            } else if (root.has("drugs")) {
                return MAPPER.convertValue(root.get("drugs"),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, NdcRecord.class));
            }
        } catch (Exception e) {
            log.warn("Failed to parse NDC JSON response: {}", e.getMessage());
        }
        return Collections.emptyList();
    }
}