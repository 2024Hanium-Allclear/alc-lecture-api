package com.allcear.alclectureapi.fileupload.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KafkaTopicConfig {

    @Value("${kafka.topic}")
    private String topicName;

}
