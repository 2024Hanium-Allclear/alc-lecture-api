package com.allcear.alclectureapi.lecture.repository;


import com.allcear.alclectureapi.lecture.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepository extends JpaRepository<College, Long> {
}

