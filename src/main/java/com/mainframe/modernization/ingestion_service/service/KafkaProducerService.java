package com.mainframe.modernization.ingestion_service.service;

import com.mainframe.modernization.ingestion_service.model.MainframeRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Value("${SPRING_KAFKA_TOPIC_NAME:mainframe-raw-data}")
    private String topicName;

    private final KafkaTemplate<String, MainframeRecord> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, MainframeRecord> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRecord(MainframeRecord record) {
        logger.info("Sending record {} to Kafka topic: {}", record.getRecordId(), topicName);

        // Using the recordId as the 'Key' ensures all updates for 
        // the same record go to the same partition (Ordering Guarantee)
        kafkaTemplate.send(topicName, record.getRecordId(), record);
    }
}
