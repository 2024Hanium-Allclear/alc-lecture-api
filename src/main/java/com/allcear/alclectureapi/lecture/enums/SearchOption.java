package com.allcear.alclectureapi.lecture.enums;

import com.allcear.alclectureapi.lecture.dto.SearchLectureRequestDTO;
import com.allcear.alclectureapi.lecture.entity.Lecture;
import com.allcear.alclectureapi.lecture.repository.LectureRepository;

import java.util.List;

public enum SearchOption {

    DEPARTMENT{
        @Override
        public List<Lecture> search(LectureRepository lectureRepository, SearchLectureRequestDTO searchLectureRequestDTO){

            return lectureRepository.findByDepartmentNameAndGradeAndLectureName(
                    searchLectureRequestDTO.getQuery(),
                    searchLectureRequestDTO.getGrade(),
                    searchLectureRequestDTO.getLectureName()
            );
        }
    },
    KEYWORD {
        @Override
        public List<Lecture> search(LectureRepository lectureRepository, SearchLectureRequestDTO searchDTO) {
            return lectureRepository.findByLectureNameContaining(searchDTO.getQuery());
        }
    },
    LECTURE_CODE {
        @Override
        public List<Lecture> search(LectureRepository lectureRepository, SearchLectureRequestDTO searchDTO) {
            return lectureRepository.findByLectureCodeAndDivision(
                    searchDTO.getQuery(),
                    searchDTO.getDivision()
            );
        }
    };

    public abstract List<Lecture> search(LectureRepository lectureRepository, SearchLectureRequestDTO searchDTO);
}