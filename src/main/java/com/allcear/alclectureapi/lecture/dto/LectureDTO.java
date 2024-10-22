package com.allcear.alclectureapi.lecture.dto;


import com.allcear.alclectureapi.fileupload.enums.LectureField;
import com.allcear.alclectureapi.lecture.entity.Department;
import com.allcear.alclectureapi.lecture.entity.Lecture;
import com.allcear.alclectureapi.lecture.entity.Professor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class LectureDTO {
    private Long lectureId;
    private Professor professor;
    private Department department;
    private String lectureCode;
    private String division;
    private String lectureClassification;
    private String lectureName;
    private String grade;
    private Integer credit;
    private Integer allowedNumberOfStudents;
    private Integer currentNumberOfStudents;
    private String lectureDayAndRoom;
    private String lectureTime;
    private Integer lectureYear;
    private Integer semester;
    private String syllabus;


    public static LectureDTO fromMap(Long lectureId, Map<String, String> lectureData) {
        return LectureDTO.builder()
                .lectureId(lectureId)
                .professor(new Professor(Long.parseLong(lectureData.get(LectureField.PROFESSOR_ID.name()))))
                .department(new Department(Long.parseLong(lectureData.get(LectureField.DEPARTMENT_ID.name()))))
                .lectureCode(lectureData.get(LectureField.LECTURE_CODE.name()))
                .division(lectureData.get(LectureField.DIVISION.name()))
                .lectureClassification(lectureData.get(LectureField.LECTURE_CLASSIFICATION.name()))
                .lectureName(lectureData.get(LectureField.LECTURE_NAME.name()))
                .grade(lectureData.get(LectureField.GRADE.name()))
                .credit(Integer.parseInt(lectureData.get(LectureField.CREDIT.name())))
                .allowedNumberOfStudents(Integer.parseInt(lectureData.get(LectureField.ALLOWED_NUMBER_OF_STUDENTS.name())))
                .currentNumberOfStudents(Integer.parseInt(lectureData.get(LectureField.CURRENT_NUMBER_OF_STUDENTS.name())))
                .lectureDayAndRoom(lectureData.get(LectureField.LECTURE_DAY_AND_ROOM.name()))
                .lectureTime(lectureData.get(LectureField.LECTURE_TIME.name()))
                .lectureYear(Integer.parseInt(lectureData.get(LectureField.LECTURE_YEAR.name())))
                .semester(Integer.parseInt(lectureData.get(LectureField.SEMESTER.name())))
                .syllabus(lectureData.get(LectureField.SYLLABUS.name()))
                .build();
    }

    public static Lecture toEntity(LectureDTO dto) {
        return Lecture.builder()
                .id(dto.getLectureId())
                .professor(dto.getProfessor())
                .department(dto.getDepartment())
                .lectureCode(dto.getLectureCode())
                .division(dto.getDivision())
                .lectureClassification(dto.getLectureClassification())
                .lectureName(dto.getLectureName())
                .grade(dto.getGrade())
                .credit(dto.getCredit() != null ? dto.getCredit() : 0)
                .allowedNumberOfStudents(dto.getAllowedNumberOfStudents() != null ? dto.getAllowedNumberOfStudents() : 0)
                .currentNumberOfStudents(dto.getCurrentNumberOfStudents() != null ? dto.getCurrentNumberOfStudents() : 0)
                .lectureDayAndRoom(dto.getLectureDayAndRoom())
                .lectureTime(dto.getLectureTime())
                .lectureYear(dto.getLectureYear() != null ? dto.getLectureYear() : 0)
                .semester(dto.getSemester() != null ? dto.getSemester() : 0)
                .syllabus(dto.getSyllabus())
                .delStatus(false) // If needed
                .build();
    }
}
