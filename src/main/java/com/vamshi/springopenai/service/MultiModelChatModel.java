package com.vamshi.springopenai.service;

import com.vamshi.springopenai.common.ModelType;
import com.vamshi.springopenai.config.ChatClientRouter;
import org.springframework.stereotype.Service;

@Service
public class MultiModelChatModel {

    private final ChatClientRouter router;

    public MultiModelChatModel(ChatClientRouter router) {
        this.router = router;
    }

    public String chat(String message, ModelType modelType) {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Prompt must not be empty");
        }
        return router.getClient(modelType)
                .prompt()
                .user(message)
                .call()
                .content();
    }

}
