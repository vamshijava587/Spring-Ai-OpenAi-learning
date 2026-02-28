package com.vamshi.springopenai.controller;

import com.vamshi.springopenai.common.ModelType;
import com.vamshi.springopenai.common.ModuleType;
import com.vamshi.springopenai.model.ChatRequest;
import com.vamshi.springopenai.service.MultiModelChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MultiModelController {

    private final MultiModelChatModel multiModelChatModel;

    public MultiModelController(MultiModelChatModel multiModelChatModel) {
        this.multiModelChatModel = multiModelChatModel;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> multiModel(
            @RequestHeader("X-Chat-Model") ModelType model,
            @RequestHeader(value = "X-Chat-Module", defaultValue = "GENERAL") ModuleType module,
            @RequestBody ChatRequest chatRequest) {
        return ResponseEntity.ok(multiModelChatModel.chat(chatRequest.getMessage(), model, module));
    }
}
