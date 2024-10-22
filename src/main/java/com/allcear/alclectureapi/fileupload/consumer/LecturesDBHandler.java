package com.allcear.alclectureapi.fileupload.consumer;


import com.allcear.alclectureapi.lecture.entity.Lecture;
import com.allcear.alclectureapi.lecture.repository.LectureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Slf4j
public class LecturesDBHandler {
    //jpa로 변경
    private final LectureRepository lectureRepository;

    public LecturesDBHandler(LectureRepository lectureRepository){
        this.lectureRepository = lectureRepository;
    }

    @Transactional
    public void insertOrUpdateLecture(Lecture lecture) {
        // 해당 강의 아이디가 존재하는지 확인
        if (lectureRepository.existsById(lecture.getId())) {
            log.info("Lecture with ID: {} already exists. Updating existing record.", lecture.getId());
            updateLecture(lecture);
        } else {
            log.info("Lecture with ID: {} does not exist. Inserting new record.", lecture.getId());
            insertLecture(lecture);
        }
    }
    private void insertLecture(Lecture lecture) {
        lecture.setCreatedDate(LocalDateTime.now());
        lecture.setModifiedDate(LocalDateTime.now());
        lectureRepository.save(lecture);
    }

    private void updateLecture(Lecture lecture) {
        lecture.setModifiedDate(LocalDateTime.now());
        lectureRepository.save(lecture);
    }

    public void close() {
        log.info("Closing LecturesDBHandler - No explicit close required with JPA");
    }
}