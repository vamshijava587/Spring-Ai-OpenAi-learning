package com.vamshi.springopenai.controller;

import com.vamshi.springopenai.common.ModelType;
import com.vamshi.springopenai.common.ModuleType;
import com.vamshi.springopenai.model.ChatRequest;
import com.vamshi.springopenai.service.MultiModelChatModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1")
public class MultiModelChatController {

    private final MultiModelChatModel multiModelChatModel;

    public MultiModelChatController(MultiModelChatModel multiModelChatModel) {
        this.multiModelChatModel = multiModelChatModel;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(
            @RequestHeader("X-Chat-Model") ModelType model,
            @RequestHeader("X-Chat-Module") ModuleType module,
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @RequestBody ChatRequest request) {
        return ResponseEntity.ok(
                multiModelChatModel.chat(request.getMessage(), model, module, sessionId)
        );
    }

    // ADD this endpoint alongside existing /chat
    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(
            @RequestHeader("X-Chat-Model") ModelType model,
            @RequestHeader("X-Chat-Module") ModuleType module,
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @RequestParam String message) {       // GET with query param for SSE
        return multiModelChatModel.stream(message, model, module, sessionId);
    }
}