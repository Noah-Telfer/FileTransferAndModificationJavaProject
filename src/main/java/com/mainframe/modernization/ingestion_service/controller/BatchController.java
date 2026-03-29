package com.mainframe.modernization.ingestion_service.controller;

import com.mainframe.modernization.ingestion_service.service.MainframeParsingService;
import com.mainframe.modernization.ingestion_service.dto.BatchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/batch")
@Slf4j
public class BatchController {

    @Autowired
    private MainframeParsingService parsingService;

// Trigger this with: POST http://localhost:8080/api/v1/ingestion/start?fileName=data.txt
    @PostMapping("/start")
    public ResponseEntity<String> startBatch(@Valid @RequestBody BatchRequest batchRequest) {
        log.info("Received request to process file: {}", batchRequest.getFileName());

        // We trigger the service logic here
        try {
            parsingService.processMainframeFile(batchRequest.getFileName());
            return ResponseEntity.ok("Batch processing started for: " + batchRequest.getFileName());
        } catch (Exception e) {
            log.error("Failed to start batch processing", e);
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
