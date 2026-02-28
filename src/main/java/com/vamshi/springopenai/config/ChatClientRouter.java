package com.vamshi.springopenai.config;

import com.vamshi.springopenai.common.ModelType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class ChatClientRouter {

    private final ChatClient openAiChatClient;
    private final ChatClient ollamaAiChatClient;

    public ChatClientRouter(
            @Qualifier("openAiChatClient") ChatClient openAiChatClient,
            @Qualifier("ollamaAiChatClient") ChatClient ollamaAiChatClient) {
        this.openAiChatClient = openAiChatClient;
        this.ollamaAiChatClient = ollamaAiChatClient;
    }

    public ChatClient getClient(ModelType modelType) {
        return switch (modelType) {
            case OPENAI -> openAiChatClient;
            case OLLAMA -> ollamaAiChatClient;
        };
    }
}
