package com.mainframe.modernization.ingestion_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class KafkaConfig {
    @Value("${SPRING_KAFKA_TOPIC_NAME:mainframe-raw-data}")
    private String topicName;

    @Bean
    public NewTopic mainframeRawDataTopic() {
        return TopicBuilder.name(topicName)
                .partitions(3)    // Allows 3 consumers to read in parallel
                .replicas(1)      // 1 is fine for local dev; use 3 in Prod
                .build();
    }
}