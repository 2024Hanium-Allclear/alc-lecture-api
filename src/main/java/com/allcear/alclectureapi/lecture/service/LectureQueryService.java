package com.allcear.alclectureapi.lecture.service;

import com.allcear.alclectureapi.lecture.dto.LectureNameOnlyResponseDTO;
import com.allcear.alclectureapi.lecture.dto.LectureResponseDTO;
import com.allcear.alclectureapi.lecture.dto.SearchLectureRequestDTO;
import com.allcear.alclectureapi.lecture.entity.Lecture;
import com.allcear.alclectureapi.lecture.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class LectureQueryService {


    private final LectureRepository lectureRepository;


    @Autowired
    public LectureQueryService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    // 학과 ID에 해당하는 개설 과목 조회 - 제목만 (옵션용)
    public List<LectureNameOnlyResponseDTO> findDistinctLectureNamesByDepartmentId(Long departmentId, String grade) {
        List<String> lectureNames = lectureRepository.findDistinctLectureNamesByDepartmentIdAndGrade(departmentId, grade);
        return lectureNames.stream()
                .map(LectureNameOnlyResponseDTO::fromLectureName) // Use static method
                .collect(Collectors.toList());
    }

    // 전체 과목 조회
    public List<LectureNameOnlyResponseDTO> findAllDistinctLectureNamesOrderedByIdAsc() {
        // Convert lecture names to DTOs
        List<String> lectureNames = lectureRepository.findAllDistinctLectureNamesOrderedByIdAsc();
        return lectureNames.stream()
                .map(LectureNameOnlyResponseDTO::fromLectureName)
                .collect(Collectors.toList());
    }

    // 키워드에 맞는 강의 조회
    public List<LectureNameOnlyResponseDTO> findLectureNamesByKeyword(String keyword) {
        List<String> lectureNames =lectureRepository.findLectureNamesByKeyword(keyword);

        return lectureNames.stream()
                .map(LectureNameOnlyResponseDTO::fromLectureName)
                .collect(Collectors.toList());
    }

    //옵션 별 검색
    public List<LectureResponseDTO> searchLectures(SearchLectureRequestDTO searchDTO) {
        List<Lecture> lectures = searchDTO.getSearchOption().search(lectureRepository, searchDTO);
        return lectures.stream()
                .map(LectureResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
