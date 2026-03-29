package com.mainframe.modernization.ingestion_service.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainframeRecord implements Serializable {

    private String recordId;
    private String content;
    private String sourceSystem;
    private LocalDateTime timestamp;
}
