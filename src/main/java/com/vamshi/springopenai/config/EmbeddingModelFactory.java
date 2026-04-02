package com.vamshi.springopenai.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static org.springframework.ai.model.SpringAIModels.OLLAMA;
import static org.springframework.ai.model.SpringAIModels.OPENAI;

@Service
public class EmbeddingModelFactory {

    private final EmbeddingModel openAiEmbeddingModel;
    private final EmbeddingModel ollamaEmbeddingModel;

    public EmbeddingModelFactory(
            @Qualifier("openAiEmbeddingModel") EmbeddingModel openAiEmbeddingModel,
            @Qualifier("ollamaEmbeddingModel") EmbeddingModel ollamaEmbeddingModel
    ) {
        this.openAiEmbeddingModel = openAiEmbeddingModel;
        this.ollamaEmbeddingModel = ollamaEmbeddingModel;
    }

    public EmbeddingModel getModel(String modelType) {
        return switch (modelType.toLowerCase()) {
            case OPENAI -> openAiEmbeddingModel;
            case OLLAMA -> ollamaEmbeddingModel;
            default -> throw new IllegalArgumentException("Invalid model type");
        };
    }
}