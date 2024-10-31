package com.allcear.alclectureapi.wishlist.controller;

import com.allcear.alclectureapi.wishlist.dto.WishlistRequestDTO;
import com.allcear.alclectureapi.wishlist.dto.WishlistResponseDTO;
import com.allcear.alclectureapi.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private static final Logger logger = LoggerFactory.getLogger(WishlistController.class);

    // 위시리스트에 강의 추가
    @PostMapping("/add")
    public ResponseEntity<String> addLectureToWishlist(
            @RequestBody WishlistRequestDTO wishlistRequestDTO,
            @RequestHeader("studentId") Long studentId) {  // 헤더에서 studentId 받기

        try {
            wishlistService.addLectureToWishlist(studentId, wishlistRequestDTO);
            return ResponseEntity.ok("위시리스트에 강의가 성공적으로 추가되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("서버 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류 발생: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{lectureId}")
    public ResponseEntity<String> deleteLectureFromWishlist(
            @PathVariable Long lectureId,
            @RequestHeader("studentId") Long studentId) {
        try {
            wishlistService.deleteLectureFromWishlist(lectureId, studentId);
            return ResponseEntity.ok("위시리스트에서 강의가 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("서버 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류 발생: " + e.getMessage());
        }
    }

    @PostMapping("/updatePriority/{lectureId}")
    public ResponseEntity<String> updateLecturePriority(
            @PathVariable Long lectureId,
            @RequestBody Map<String, Integer> requestBody,
            @RequestHeader("studentId") Long studentId) {
        Integer newPriority = requestBody.get("newPriority");
        if (newPriority == null) {
            return ResponseEntity.badRequest().body("없음");
        }
        try {
            wishlistService.updateLecturePriority(lectureId, studentId, newPriority);
            return ResponseEntity.ok("강의 우선순위가 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            logger.error("서버 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류 발생: " + e.getMessage());
        }
    }

    // 위시리스트 조회
    @GetMapping("/wishlist")
    public ResponseEntity<List<WishlistResponseDTO>> getWishlist(
            @RequestHeader("studentId") Long studentId) {  // 헤더에서 studentId 받기
        try {
            List<WishlistResponseDTO> wishlist = wishlistService.getWishlistByStudentId(studentId);
            return ResponseEntity.ok(wishlist);
        } catch (Exception e) {
            logger.error("서버 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/findLectureIdByWishlistId/{wishlistId}")
    public ResponseEntity<Long> findLectureIdByWishlistId(
            @PathVariable Long wishlistId,
            @RequestHeader("studentId") Long studentId) {

        try {
            Long lectureId = wishlistService.getLectureIdByWishlistId(wishlistId, studentId);
            if (lectureId != null) {
                return ResponseEntity.ok(lectureId);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            logger.error("서버 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
