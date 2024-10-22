package com.allcear.alclectureapi.fileupload.config;

import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Getter
public class KafkaConsumerConfig {
    //카프가 소비자 구성

    @Value("${kafka.topic}")
    private String topicName;

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.group-id}")
    private String groupId;

    @Value("${kafka.enable-auto-commit}")
    private boolean enableAutoCommit;

    @Value("${kafka.key-deserializer}")
    private String keyDeserializer;

    @Value("${kafka.value-deserializer}")
    private String valueDeserializer;

    public Properties getKafkaConsumerProperties() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, String.valueOf(enableAutoCommit));
        return props;
    }
}
