package com.vamshi.springopenai.service;

import com.vamshi.springopenai.common.ModelType;
import com.vamshi.springopenai.common.ModuleType;
import com.vamshi.springopenai.config.ChatClientRouter;
import com.vamshi.springopenai.prompt.PromptLoader;
import org.springframework.stereotype.Service;


@Service
public class MultiModelChatModel {

    private final ChatClientRouter router;
    private final PromptLoader promptLoader;

    public MultiModelChatModel(ChatClientRouter router, PromptLoader promptLoader) {
        this.router = router;
        this.promptLoader = promptLoader;
    }

    public String chat(String message, ModelType modelType, ModuleType moduleType) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Prompt must not be empty");
        }

        String systemPrompt = promptLoader.getPrompt(moduleType);

        return router.getClient(modelType)
                .prompt()
                .system(systemPrompt)
                .user(message)
                .call()
                .content();
    }
}
