package com.vamshi.springopenai.controller;

import com.vamshi.springopenai.model.ChatRequest;
import com.vamshi.springopenai.service.MultiModelChatModel;
import org.springframework.web.bind.annotation.*;

@RestController
public class MultiModelController {

    private final MultiModelChatModel multiModelChatModel;

    public MultiModelController(MultiModelChatModel multiModelChatModel) {
        this.multiModelChatModel = multiModelChatModel;
    }

    @PostMapping("/chat/{model}")
    public String multiModel(@RequestBody ChatRequest chatRequest ,@RequestHeader String chatModel){
        return multiModelChatModel.chat(chatRequest.getMessage(),chatModel);
    }
}
