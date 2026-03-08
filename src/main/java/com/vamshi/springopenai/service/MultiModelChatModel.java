package com.vamshi.springopenai.service;

import com.vamshi.springopenai.common.ModelType;
import com.vamshi.springopenai.common.ModuleType;
import com.vamshi.springopenai.config.ChatClientRouter;
import com.vamshi.springopenai.prompt.PromptLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
public class MultiModelChatModel {

    private final ChatClientRouter router;
    private final PromptLoader promptLoader;

    public MultiModelChatModel(ChatClientRouter router, PromptLoader promptLoader) {
        this.router = router;
        this.promptLoader = promptLoader;
    }

    public String chat(String message, ModelType modelType,
                       ModuleType moduleType, String conversationId) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Prompt must not be empty");
        }

        String resolvedId = (conversationId != null && !conversationId.isBlank())
                ? conversationId
                : UUID.randomUUID().toString();

        String systemPrompt = promptLoader.getPrompt(moduleType);

        return router.getClient(modelType)
                .prompt()
                .system(systemPrompt)
                .user(message)
                .advisors(advisor ->
                        advisor.param("chat_memory_conversation_id", resolvedId))
                .call()
                .content();
    }

    // NEW method — streaming
    public Flux<String> stream(String message, ModelType modelType,
                               ModuleType moduleType, String conversationId) {
        String resolvedId = (conversationId != null && !conversationId.isBlank())
                ? conversationId : UUID.randomUUID().toString();

        String systemPrompt = promptLoader.getPrompt(moduleType);

        return router.getClient(modelType)
                .prompt()
                .system(systemPrompt)
                .user(message)
                .advisors(advisor ->
                        advisor.param("chat_memory_conversation_id", resolvedId))
                .stream()           // <-- only change from .call()
                .content();         // returns Flux<String> instead of String
    }
}