package com.allcear.alclectureapi.lecture.dto;


import com.allcear.alclectureapi.lecture.enums.SearchOption;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchLectureRequestDTO {
    private SearchOption searchOption;
    private String query;       // 검색어 (학과 이름, 키워드, 학수번호 등)
    private String grade;       // 학년 (옵션)
    private String lectureName;
    private String division; // 분반 (옵션)

}
