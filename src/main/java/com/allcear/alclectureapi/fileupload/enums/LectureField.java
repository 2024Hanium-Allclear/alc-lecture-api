package com.allcear.alclectureapi.fileupload.enums;
public enum LectureField {

    DEPARTMENT_ID(0),
    LECTURE_CODE(1),
    LECTURE_NAME(2),
    DIVISION(3),
    LECTURE_CLASSIFICATION(4),
    PROFESSOR_ID(5),
    CREDIT(6),
    ALLOWED_NUMBER_OF_STUDENTS(7),
    CURRENT_NUMBER_OF_STUDENTS(8),
    GRADE(9),
    LECTURE_DAY_AND_ROOM(10),
    LECTURE_TIME(11),
    LECTURE_YEAR(12),
    SEMESTER(13),
    SYLLABUS(14);

    private final int index;

    LectureField(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
