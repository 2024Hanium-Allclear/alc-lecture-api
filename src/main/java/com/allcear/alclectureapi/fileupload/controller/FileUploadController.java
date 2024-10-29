package com.allcear.alclectureapi.fileupload.controller;


import com.allcear.alclectureapi.fileupload.config.KafkaConsumerConfig;
import com.allcear.alclectureapi.fileupload.config.KafkaTopicConfig;
import com.allcear.alclectureapi.fileupload.consumer.FileToDBConsumer;
import com.allcear.alclectureapi.fileupload.consumer.LecturesDBHandler;
import com.allcear.alclectureapi.fileupload.enums.CommitMode;
import com.allcear.alclectureapi.fileupload.enums.FileUploadErrorCode;
import com.allcear.alclectureapi.fileupload.exception.EmptyFileExceptionHandler;
import com.allcear.alclectureapi.fileupload.exception.FileMonitoringExceptionHandler;
import com.allcear.alclectureapi.fileupload.exception.FileUploadExceptionHandler;
import com.allcear.alclectureapi.fileupload.exception.KafkaConsumerExceptionHandler;
import com.allcear.alclectureapi.fileupload.producer.FileAppendProducer;
import com.allcear.alclectureapi.lecture.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//1차 코드 정리 완료 - 2024.10.10 -> KafkaTopicConfig, KafkaConsumerConfig 추가 및 메서드 분리
@Controller
@RequestMapping("/api/fileupload")
@CrossOrigin(origins = "*")
public class FileUploadController {
    private final LectureRepository lectureRepository;
    private final FileAppendProducer fileAppendProducer;
    private final KafkaConsumerConfig kafkaConsumerConfig;
    private final KafkaTopicConfig kafkaTopicConfig;

    @Value("${file.upload-dir}")
    private String uploadDir;

    //두 개의 스레드를 사용하는 스레드 풀
    //파일 모니터링과 Kafka Consumer를 별도의 스레드에서 실행하기 위해 사용
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);  // 2개의 스레드를 사용

    public FileUploadController(LectureRepository lectureRepository, KafkaConsumerConfig kafkaConsumerConfig, KafkaTopicConfig kafkaTopicConfig) {
        this.lectureRepository = lectureRepository;
        this.kafkaTopicConfig = kafkaTopicConfig;
        this.fileAppendProducer = new FileAppendProducer(kafkaConsumerConfig, kafkaTopicConfig);
        this.kafkaConsumerConfig = kafkaConsumerConfig;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        //파일 업로드 여부 확인
        validateFile(file);

        try {
            // 파일을 서버의 특정 경로에 저장
            File dest = saveFile(file);
            // 1. 파일 모니터링 시작
            startFileMonitoring(dest);
            // 2. Kafka Consumer 시작
            startKafkaConsumer();

            return ResponseEntity.ok("파일 업로드 성공!");

        } catch (IOException e) {
            e.printStackTrace();
            throw new FileUploadExceptionHandler(FileUploadErrorCode.FILE_UPLOAD_ERROR);
        }
    }

    //파일 업로드 여부 확인
    private void validateFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) { //업로드 된 파일이 없을 때
            throw new EmptyFileExceptionHandler(FileUploadErrorCode.FILE_EMPTY);
        }
    }

    //파일을 서버의 특정 경로에 저장
    private File saveFile(MultipartFile file) throws IOException {
        File dest = new File(uploadDir + file.getOriginalFilename());
        file.transferTo(dest);
        return dest;
    }

    // 1. 파일 모니터링 시작
    private void startFileMonitoring(File dest){
        executorService.submit(() -> {
            try {
                fileAppendProducer.startFileMonitoring(dest.getAbsolutePath());
            } catch (Exception e) {
                // 파일 모니터링 중 예외 처리
                throw new FileMonitoringExceptionHandler(FileUploadErrorCode.FILE_MONITORING_ERROR);
            }
        });
    }

    // 2. Kafka Consumer 시작
    private void startKafkaConsumer() {
        executorService.submit(()->{
            try {
                String topicName = kafkaTopicConfig.getTopicName();
                Properties props = kafkaConsumerConfig.getKafkaConsumerProperties();
                LecturesDBHandler lecturesDBHandler = new LecturesDBHandler(lectureRepository);
                FileToDBConsumer<String, String> fileToDBConsumer = new FileToDBConsumer<>(props, List.of(topicName), lecturesDBHandler);
                fileToDBConsumer.startConsuming(CommitMode.ASYNC, 3000);  // Kafka Consumer 시작
            } catch (Exception e) {
                // Kafka 소비자 시작 중 예외 처리
                throw new KafkaConsumerExceptionHandler(FileUploadErrorCode.KAFKA_CONSUMER_ERROR);
            }
        });
    }
}
