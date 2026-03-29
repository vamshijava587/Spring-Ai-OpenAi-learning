package com.vamshi.springopenai.controller;

import com.vamshi.springopenai.common.ModelType;
import com.vamshi.springopenai.model.ChatRequest;
import com.vamshi.springopenai.model.NdcResponse;
import com.vamshi.springopenai.service.NdcService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ndc")
public class NdcController {

    private final NdcService ndcService;

    public NdcController(NdcService ndcService) {
        this.ndcService = ndcService;
    }

    @PostMapping("/lookup")
    public ResponseEntity<NdcResponse> lookup(
            @RequestHeader("X-Chat-Model") ModelType model,
            @RequestBody ChatRequest request) {
        return ResponseEntity.ok(ndcService.lookup(request.getMessage(), model));
    }
}