package com.vamshi.springopenai.controller;

import com.vamshi.springopenai.common.ModelType;
import com.vamshi.springopenai.model.ChatRequest;
import com.vamshi.springopenai.service.MultiModelChatModel;
import org.springframework.web.bind.annotation.*;

@RestController
public class MultiModelController {

    private final MultiModelChatModel multiModelChatModel;

    public MultiModelController(MultiModelChatModel multiModelChatModel) {
        this.multiModelChatModel = multiModelChatModel;
    }

    @PostMapping("/chat")
    public String multiModel(@RequestHeader("X-Chat-Model") ModelType model, @RequestBody ChatRequest chatRequest) {
        return multiModelChatModel.chat(chatRequest.getMessage(), model);
    }
}
