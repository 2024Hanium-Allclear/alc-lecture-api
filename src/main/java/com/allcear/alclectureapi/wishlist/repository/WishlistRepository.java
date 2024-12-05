package com.allcear.alclectureapi.wishlist.repository;

import com.allcear.alclectureapi.lecture.entity.Lecture;
import com.allcear.alclectureapi.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    // 학생 ID로 위시리스트 항목 찾기
    List<Wishlist> findByStudentId(Long studentId);

    // 강의 코드와 분반으로 강의 찾기
    @Query("SELECT w FROM Wishlist w WHERE w.lecture.lectureCode = :lectureCode AND w.lecture.division = :division")
    List<Lecture> findByLectureCodeAndDivision(@Param("lectureCode") String lectureCode, @Param("division") String division);

    // lectureId와 studentId로 위시리스트 항목 찾기 (삭제 용도)
    @Query("SELECT w FROM Wishlist w WHERE w.lecture.id = :lectureId AND w.studentId = :studentId")
    Optional<Wishlist> findByLectureIdAndStudentId(@Param("lectureId") Long lectureId, @Param("studentId") Long studentId);

    // ID로 위시리스트 항목 삭제
    void deleteById(Long id);

    Optional<Wishlist> findByIdAndStudentId(Long wishlistId, Long studentId);
    Optional<Wishlist> findByLectureAndStudentId(Lecture lecture, Long studentId);
}
