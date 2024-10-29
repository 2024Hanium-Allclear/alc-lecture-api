package com.allcear.alclectureapi.fileupload.producer;


import com.allcear.alclectureapi.fileupload.config.KafkaConsumerConfig;
import com.allcear.alclectureapi.fileupload.config.KafkaTopicConfig;
import com.allcear.alclectureapi.fileupload.enums.CommitMode;
import com.allcear.alclectureapi.fileupload.event.EventHandler;
import com.allcear.alclectureapi.fileupload.event.FileEventHandler;
import com.allcear.alclectureapi.fileupload.event.FileEventSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.io.File;
import java.util.Properties;

//2024-10-14 1차 코드 정리 완료
@Slf4j
//파일 변화를 감지하고 Kafka로 데이터를 전송하는 프로듀서
public class FileAppendProducer {
    private final KafkaConsumerConfig kafkaConsumerConfig;
    private final KafkaTopicConfig kafkaTopicConfig;

    public FileAppendProducer(KafkaConsumerConfig kafkaConsumerConfig, KafkaTopicConfig kafkaTopicConfig) {
        this.kafkaConsumerConfig = kafkaConsumerConfig;
        this.kafkaTopicConfig = kafkaTopicConfig;
    }

    //파일 모니터링
    public void startFileMonitoring(String filePath) {
        String topicName = kafkaTopicConfig.getTopicName();

        Properties props = kafkaConsumerConfig.getKafkaConsumerProperties();
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props);

        Thread fileEventSourceThread = getThread(filePath, kafkaProducer, topicName);

        try {
            fileEventSourceThread.join(); // 파일 모니터링이 끝날 때까지 대기
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            kafkaProducer.close();
        }
    }

    //getThread 메소드를 통해 스레드 생성과 관련된 로직 분리
    private static Thread getThread(String filePath, KafkaProducer<String, String> kafkaProducer, String topicName) {
        ;
        File file = new File(filePath);  // 업로드된 파일 경로 사용

        //파일의 변화를 감지하고 Kafka로 데이터를 전송하는 역할
        //이 핸들러는 파일의 변화를 감지하면, 그 내용을 Kafka의 특정 토픽(file-topic)으로 전송하는 로직을 포함
        EventHandler eventHandler = new FileEventHandler(kafkaProducer, topicName, CommitMode.ASYNC);

        //파일에서 이벤트를 감지하는 객체
        // 여기서는 파일을 10초(10000 밀리초)마다 폴링(polling)하여 파일의 변화를 확인
        FileEventSource fileEventSource = new FileEventSource(10000, file, eventHandler);

        //파일 이벤트 감지 작업을 백그라운드 스레드로 실행하기 위해 새로운 스레드를 생성
        Thread fileEventSourceThread = new Thread(fileEventSource);

        fileEventSourceThread.start(); // 모니터링 시작
        return fileEventSourceThread;
    }
}

