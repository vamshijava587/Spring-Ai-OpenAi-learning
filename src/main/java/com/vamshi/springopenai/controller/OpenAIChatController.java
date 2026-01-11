package com.vamshi.springopenai.controller;


import com.vamshi.springopenai.service.OpenAIChatService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class OpenAIChatController {


    private final OpenAIChatService openAIChatService;

    public OpenAIChatController(OpenAIChatService openAIChatService) {
        this.openAIChatService = openAIChatService;
    }


    @GetMapping("/chat")
    public String chat(@RequestBody String message) {
        return openAIChatService.chatWithOpenAILLM(message);
    }

    @PostMapping("/chat/openai")
    public String chat(@RequestBody Map<String, String> payload) {
        return openAIChatService.chatWithOpenAILLM(payload.get("message"));
    }

}