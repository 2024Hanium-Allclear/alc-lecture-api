package com.allcear.alclectureapi.fileupload.event;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.ExecutionException;


@Slf4j
public class FileEventHandler implements EventHandler{

    //생성자
    private  KafkaProducer<String,String> kafkaProducer;
    private String topicName;
    private boolean sync;

    public FileEventHandler(KafkaProducer<String, String> kafkaProducer, String topicName, boolean sync) {
        this.kafkaProducer = kafkaProducer;
        this.topicName = topicName;
        this.sync = sync;
    }

    //메시지를 하나씩 보내는것
    @Override
    public void onMessage(MessageEvent messageEvent) throws InterruptedException, ExecutionException {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, messageEvent.key, messageEvent.value);

        if(this.sync){ //sync일 때
            RecordMetadata recordMetadata = this.kafkaProducer.send(producerRecord).get();
            //메시지가 브로커에 전송될 때까지 대기
            //메시지가 성공적으로 전송되면, RecordMetadata 객체를 반환하여 메시지의 메타데이터(파티션, 오프셋, 타임스탬프 등)를 로그에 기록합니다.

            log.info("\n ###### record metadata received ##### \n" +
                    "partition:" + recordMetadata.partition() +"\n" +
                    "offset:" + recordMetadata.offset() + "\n" +
                    "timestamp:" + recordMetadata.timestamp());
        }else {
            //async일 때
            //전송 완료 후의 콜백 함수를 지정하여 결과를 처리
            //메시지가 성공적으로 전송되면, 콜백 함수 내에서 메타데이터를 로그에 기록
            kafkaProducer.send(producerRecord, (metadata, exception) -> {
                if (exception == null) {
                    log.info("\n ###### record metadata received ##### \n" +
                            "partition:" + metadata.partition() + "\n" +
                            "offset:" + metadata.offset() + "\n" +
                            "timestamp:" + metadata.timestamp());
                } else {
                    log.error("exception error from broker " + exception.getMessage());
                }
            });
        }
    }
}
