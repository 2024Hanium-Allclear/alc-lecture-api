package com.allcear.alclectureapi.wishlist.service;

import com.allcear.alclectureapi.lecture.entity.Lecture;
import com.allcear.alclectureapi.lecture.repository.LectureRepository;
import com.allcear.alclectureapi.wishlist.dto.WishlistRequestDTO;
import com.allcear.alclectureapi.wishlist.dto.WishlistResponseDTO;
import com.allcear.alclectureapi.wishlist.entity.Wishlist;
import com.allcear.alclectureapi.wishlist.repository.WishlistRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final LectureRepository lectureRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(WishlistService.class);

    @Transactional
    public void addLectureToWishlist(Long studentId, WishlistRequestDTO wishlistRequestDTO) {
        String lectureCode = wishlistRequestDTO.getLectureCode();
        String division = wishlistRequestDTO.getDivision();

        if (lectureCode == null || lectureCode.isEmpty() || division == null || division.isEmpty()) {
            throw new IllegalArgumentException("강의 코드와 분반은 필수입니다.");
        }

        List<Lecture> lectures = lectureRepository.findByLectureCodeAndDivision(lectureCode, division);
        if (lectures.isEmpty()) {
            throw new IllegalArgumentException("해당 강의를 찾을 수 없습니다.");
        }

        Lecture lecture = lectures.get(0);

        Optional<Wishlist> existingWishlist = wishlistRepository.findByLectureAndStudentId(lecture, studentId);
        if (existingWishlist.isEmpty()) {
            Wishlist wishlist = new Wishlist();
            wishlist.setLecture(lecture);
            wishlist.setStudentId(studentId);
            wishlist.setPriority(0);  // 기본 우선순위 설정
            wishlistRepository.save(wishlist);
            logger.info("위시리스트에 강의 추가 완료. 학생 ID: " + studentId);
        } else {
            throw new IllegalArgumentException("이미 위시리스트에 추가된 강의입니다.");
        }
    }

    @Transactional
    public void deleteLectureFromWishlist(Long lectureId, Long studentId) {
        // 강의 ID와 학생 ID로 위시리스트 항목 찾기
        Optional<Wishlist> wishlistOptional = wishlistRepository.findByLectureIdAndStudentId(lectureId, studentId);

        // 위시리스트 항목이 존재하는지 확인
        if (wishlistOptional.isPresent()) {
            wishlistRepository.deleteById(wishlistOptional.get().getId()); // 삭제
            logger.info("위시리스트에서 강의 삭제 완료. 학생 ID: " + studentId + ", 강의 ID: " + lectureId);
        } else {
            logger.error("위시리스트에서 강의를 찾을 수 없습니다. 강의 ID: {}, 학생 ID: {}", lectureId, studentId);
            throw new IllegalArgumentException("해당 강의는 위시리스트에 존재하지 않습니다.");
        }
    }
    @Transactional
    public void updateLecturePriority(Long lectureId, Long studentId, int newPriority) {
        Optional<Wishlist> wishlistOptional = wishlistRepository.findByLectureIdAndStudentId(lectureId, studentId);

        if (wishlistOptional.isPresent()) {
            Wishlist wishlist = wishlistOptional.get();
            wishlist.setPriority(newPriority);
            wishlistRepository.save(wishlist); // 변경사항 저장
            logger.info("위시리스트에서 강의 우선순위 업데이트 완료. 학생 ID: " + studentId + ", 강의 ID: " + lectureId);
        } else {
            logger.error("위시리스트에서 강의를 찾을 수 없습니다. 강의 ID: {}, 학생 ID: {}", lectureId, studentId);
            throw new IllegalArgumentException("해당 강의는 위시리스트에 존재하지 않습니다.");
        }
    }
    @Transactional(readOnly = true)
    public List<WishlistResponseDTO> getWishlistByStudentId(Long studentId) {
        List<Wishlist> wishlists = wishlistRepository.findByStudentId(studentId);
        return wishlists.stream()
                .map(WishlistResponseDTO::from)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public Long getLectureIdByWishlistId(Long wishlistId, Long studentId) {
        Optional<Wishlist> wishlist = wishlistRepository.findByIdAndStudentId(wishlistId, studentId);
        return wishlist.map(w -> w.getLecture().getId()).orElse(null);
    }
}

