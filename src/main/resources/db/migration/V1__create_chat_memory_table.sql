CREATE TABLE SPRING_AI_CHAT_MEMORY (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       conversation_id VARCHAR(255) NOT NULL,
                                       content TEXT,
                                       type VARCHAR(50),
                                       timestamp BIGINT
);