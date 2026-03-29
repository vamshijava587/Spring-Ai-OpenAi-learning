package com.vamshi.springopenai.config;

import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatAdvisorConfig {

    @Bean
    public MessageChatMemoryAdvisor messageChatMemoryAdvisor(
            ChatMemoryRepository chatMemoryRepository) {   // Spring AI auto-creates this from JDBC starter

        return MessageChatMemoryAdvisor.builder(
                MessageWindowChatMemory.builder()
                        .chatMemoryRepository(chatMemoryRepository)  // PostgreSQL backed
                        .maxMessages(20)
                        .build()
        ).build();
    }

    @Bean
    public SimpleLoggerAdvisor simpleLoggerAdvisor() {
        return new SimpleLoggerAdvisor();
    }
}
