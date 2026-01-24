package com.vamshi.springopenai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class MultiModelChatModel {

    private final ChatClient openAiChatClient;
    private final ChatClient ollamaAiChatClient;

    public MultiModelChatModel(ChatClient openAiChatClient, ChatClient ollamaAiChatClient) {
        this.openAiChatClient = openAiChatClient;
        this.ollamaAiChatClient = ollamaAiChatClient;
    }
    public String chat(String message, String provider) {

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Prompt must not be empty");
        }

        return switch (provider.toLowerCase()) {
            case "openai" ->
                    openAiChatModel(message);

            case "ollama" ->
                    ollamaAiChatClient(message);

            default ->
                     "Unsupported provider: " + provider;
        };
    }

    private String openAiChatModel(String message){
        return openAiChatClient.prompt()
                .call()
                .content();
    }

    private String ollamaAiChatClient(String message){
        return ollamaAiChatClient.prompt()
                .call()
                .content();
    }

}
