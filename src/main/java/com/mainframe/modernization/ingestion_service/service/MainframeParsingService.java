package com.mainframe.modernization.ingestion_service.service;

import com.mainframe.modernization.ingestion_service.model.MainframeRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
public class MainframeParsingService {

    @Value("${APP_INGESTION_PATH:/tmp/}")
    private String inputDir; 

    public void processMainframeFile(String fileName) {
        Path path = Paths.get(inputDir + fileName);

        // 'try-with-resources' ensures the file handle is CLOSED even if an error occurs
        try (Stream<String> lines = Files.lines(path)) {
            
            lines.forEach(line -> {
                // 1. Create your DTO (Model)
                MainframeRecord record = new MainframeRecord(
                    UUID.randomUUID().toString(), // Generate a unique ID
                    line,                         // The raw text from the file
                    "MAINFRAME_SYSTEM_A",
                    LocalDateTime.now()
                );

                // 2. Logic to send to Kafka goes here next...
                log.info("Parsed record: {}, {}", record.getRecordId(), record.getContent());

                // Send Record to Kafka
            });

        } catch (IOException e) {
            log.error("Critical Error: Could not read file " + fileName, e);
            throw new RuntimeException("Failed to process file: " + fileName, e);
            // In a real app, you might throw a custom exception here for the Controller to catch
        }
    }
}