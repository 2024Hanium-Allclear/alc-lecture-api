package com.allcear.alclectureapi.lecture.repository;


import com.allcear.alclectureapi.lecture.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
