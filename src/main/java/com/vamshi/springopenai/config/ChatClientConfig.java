package com.vamshi.springopenai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class ChatClientConfig {

    @Bean("openAiChatClient")
    public ChatClient openAiChatClient(
            @Qualifier("openAiChatModel") ChatModel chatModel,
            MessageChatMemoryAdvisor memoryAdvisor,
            SimpleLoggerAdvisor loggerAdvisor
    ) {

        return ChatClient.builder(chatModel)
                .defaultAdvisors(memoryAdvisor, loggerAdvisor)
                .build();
    }

    @Bean("ollamaAiChatClient")
    public ChatClient ollamaAiChatClient(
            @Qualifier("ollamaChatModel") ChatModel chatModel,
            MessageChatMemoryAdvisor memoryAdvisor,
            SimpleLoggerAdvisor loggerAdvisor
    ) {

        return ChatClient.builder(chatModel)
                .defaultAdvisors(memoryAdvisor, loggerAdvisor)
                .build();
    }

    @Bean
    @Primary
    public EmbeddingModel embeddingModel(OllamaEmbeddingModel ollamaEmbeddingModel) {
        return ollamaEmbeddingModel;
    }

}