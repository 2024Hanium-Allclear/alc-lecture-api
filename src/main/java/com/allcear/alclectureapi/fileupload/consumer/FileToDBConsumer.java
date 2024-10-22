package com.allcear.alclectureapi.fileupload.consumer;

import com.allcear.alclectureapi.fileupload.enums.CommitMode;
import com.allcear.alclectureapi.fileupload.enums.LectureField;
import com.allcear.alclectureapi.lecture.dto.LectureDTO;
import com.allcear.alclectureapi.lecture.entity.Lecture;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//1차 코드 정리 완료 - 2024.09.30 -> JDBC -> JPA & LectureField Enum 추가
//2차 코드 정리 완료 - 2024.10.10 -> 메서드 분리
@Slf4j
public class FileToDBConsumer<K extends Serializable, V extends Serializable> {

    protected KafkaConsumer<K, V> kafkaConsumer;
    protected List<String> topics;

    private LecturesDBHandler lecturesDBHandler;


    public FileToDBConsumer(Properties consumerProps, List<String> topics, LecturesDBHandler lecturesDBHandler) {
        this.kafkaConsumer = new KafkaConsumer<>(consumerProps);
        this.topics = topics;
        this.lecturesDBHandler = lecturesDBHandler; //DB처리 업무처리용객체
    }

    //1: Controller에서 호출
    public void startConsuming(CommitMode commitMode, long durationMillis) {
        initConsumer();  //Kafka Consumer를 초기화하고, 지정된 토픽을 구독
        pollConsumes(durationMillis, commitMode);  //비동기, 동기식에 따라 호출하는 메서드 다름
        close();
    }

    //Kafka Consumer를 초기화하고, 지정된 토픽을 구독
    public void initConsumer() {
        this.kafkaConsumer.subscribe(this.topics);
        shutdownHookToRuntime(this.kafkaConsumer);
    }
    //비동기, 동기식에 따라 호출하는 메서드 다름
    public void pollConsumes(long durationMillis, CommitMode commitMode) {
        if (CommitMode.SYNC == commitMode) {
            pollCommitSync(durationMillis);
        } else {
            pollCommitAsync(durationMillis);
        }
    }

    //이번 프로젝트는 이 메서드 사용(비동기)
    //Kafka로부터 메시지를 지속적으로 소비하는 루프를 실행
    //Kafka 토픽에서 레코드를 폴링하고, 해당 레코드를 처리하고, 오프셋을 비동기적으로 커밋하는 Kafka 소비자 루프
    //비동기식 커밋을 사용하여 성능을 높일 수 있으나, 오프셋 저장 실패 시 문제가 발생할 수 있음
    private void pollCommitAsync(long durationMillis) {
        try {
            while (true) {
                ConsumerRecords<K, V> consumerRecords = this.kafkaConsumer.poll(Duration.ofMillis(durationMillis));
                log.info("consumerRecords count: " + consumerRecords.count());
                if (consumerRecords.count() > 0) {
                    processRecordsSafely(consumerRecords);
                }
                commitOffsetsAsync();
            }
        } catch (WakeupException e) {
            log.error("Wakeup exception has been called", e);
        } catch (Exception e) {
            log.error("Exception during polling: " + e.getMessage(), e);
        } finally {
            commitOffsetsSync(); //소비자를 닫기 전에 남아 있는 오프셋이 동기적으로 커밋되도록 보장
            closeConsumer();
        }
    }
    private void processRecordsSafely(ConsumerRecords<K, V> records) {
        try {
            processRecords(records);
        } catch (Exception e) {
            log.error("Failed to process records: " + e.getMessage(), e);
        }
    }

    //레코드를 처리한 후 .commitAsync을 사용하여 소비자의 오프셋을 비동기적으로 커밋
    private void commitOffsetsAsync() {
        this.kafkaConsumer.commitAsync((offsets, exception) -> {
            if (exception != null) {
                log.error("Offsets {} not completed, error: {}", offsets, exception.getMessage());
            }
        });
    }

    private void commitOffsetsSync() {
        log.info("##### Commit sync before closing");
        try {
            kafkaConsumer.commitSync();
        } catch (Exception e) {
            log.error("Exception during commit sync: " + e.getMessage(), e);
        }
    }

    private void closeConsumer() {
        log.info("Finally consumer is closing");
        close();
    }

    // 동기식으로 메시지를 소비하고 커밋하며, 예외가 발생해도 안전하게 종료
    protected void pollCommitSync(long durationMillis) {
        try {
            while (true) {
                ConsumerRecords<K, V> consumerRecords = this.kafkaConsumer.poll(Duration.ofMillis(durationMillis));
                processRecords(consumerRecords);
                if (consumerRecords.count() > 0) {
                    this.kafkaConsumer.commitSync();
                    log.info("Commit sync has been called");
                }
            }
        } catch (WakeupException e) {
            log.error("Wakeup exception has been called");
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            log.info("##### Commit sync before closing");
            kafkaConsumer.commitSync();
            log.info("Finally consumer is closing");
            close();
        }
    }

    //에플리케이션이 종료될 때 안전하게 KafkaConsumer를 종료하기 위해 wakeup()을 호출하는 스레드를 추가
    private void shutdownHookToRuntime(KafkaConsumer<K, V> kafkaConsumer) {
        Thread mainThread = Thread.currentThread(); //현재 실행 중인 스레드를 가져와 변수에 저장
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Runtime.getRuntime().addShutdownHook()메서드는 새로운 스레드를 추가하여 JVM이 종료될 때 이 스레드가 실행되도록 함.
            log.info("Main program starts to exit by calling wakeup");
            kafkaConsumer.wakeup();
        //kafkaConsumer.wakeup() 메서드는 Kafka Consumer를 깨우는 역할을 합니다. 이 호출은 poll() 메서드가 블록킹되어 있을 경우, 해당 호출을 중단하고 WakeupException을 발생
        //Kafka Consumer가 정상적으로 종료될 수 있도록 함.
            try {
                mainThread.join(); //메인스레드가 종료될때까지 대기,종료 후크가 실행되는 동안 메인 스레드는 종료되지 않고, 모든 작업이 완료될 때까지 기다리게 함.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }


    private Lecture makeLecture(ConsumerRecord<K, V> record) {
        String messageValue = (String) record.value();
        // 키를 문자열로 변환
        String key = record.key() != null ? record.key().toString() : "";
        log.info("Received record with key: " + key + " ########## messageValue: " + messageValue);
        try {
            Map<String, String> lectureData = parseLectureData(messageValue);
            Long lectureId = Long.parseLong(key);
            LectureDTO dto = LectureDTO.fromMap(lectureId, lectureData);
            return LectureDTO.toEntity(dto);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            log.error("Error parsing message: " + messageValue, e);
            return null;
        }
    }

    private Map<String, String> parseLectureData(String messageValue) {
        // 메시지를 올바른 인코딩으로 재인코딩 시도한 후
        List<String> tokens = tokenizeMessage(messageValue);
        // 키-값 형태로 매핑 lectureData에 키 값과 해당 인덱스에 들어가는 토큰 값을 넣음
        Map<String, String> lectureData = new HashMap<>();

        for (LectureField field : LectureField.values()) {
            if (field.getIndex() < tokens.size()) {
                lectureData.put(field.name(), tokens.get(field.getIndex()).trim());
            }
        }
        return lectureData;
    }

    // 메시지를 올바른 인코딩으로 재인코딩 시도
    private List<String> tokenizeMessage(String messageValue) {
        final String regex = "(?<=^|,)(\"(?:[^\"]|\"\")*\"|[^,]*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(messageValue);
        List<String> tokens = new ArrayList<>();

        while (matcher.find()) {
            tokens.add(matcher.group().replaceAll("^\"|\"$", "").replace("\"\"", "\""));
        }
        return tokens;
    }

    //사용 : lecturesDBHandler.insertOrUpdateLecture 호출
    private void processRecords(ConsumerRecords<K, V> records) {
        List<Lecture> lectures = makeLectures(records);
        if (lectures.isEmpty()) {
            log.warn("No valid lectures to insert or update, or lectures list is empty");
            return;
        }

        for (Lecture lecture : lectures) {
            processLectureSafely(lecture);
        }
    }

    private void processLectureSafely(Lecture lecture) {
        try {
            processLectureRecord(lecture);
            this.lecturesDBHandler.insertOrUpdateLecture(lecture);
        } catch (Exception e) {
            log.error("Failed to insert or update lectures in the database", e);
        }
    }
    //수정 날짜랑, 생성날짜 현재로 서정
    public void processLectureRecord(Lecture lecture) {
        LocalDateTime now = LocalDateTime.now();
        lecture.setCreatedDate(now);
        lecture.setModifiedDate(now);
    }


    //사용
    private List<Lecture> makeLectures(ConsumerRecords<K, V> records) {
        List<Lecture> lectures = new ArrayList<>();
        for (ConsumerRecord<K, V> record : records) {
            Lecture lecture = makeLecture(record);
            if (lecture != null) {
                lectures.add(lecture);
            } else {
                log.error("Failed to create Lecture from record: " + record.value());
            }
        }
        return lectures;
    }


    protected void close() {
        this.kafkaConsumer.close();
        this.lecturesDBHandler.close();
    }

}
