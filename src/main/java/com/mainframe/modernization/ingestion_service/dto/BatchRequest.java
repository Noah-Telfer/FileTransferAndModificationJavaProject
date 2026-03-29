package com.mainframe.modernization.ingestion_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchRequest {

    @NotBlank(message = "FileName is required")
    @Size(min = 5, message = "FileName must be at least 5 characters (e.g. a.txt)")
    private String fileName;
}