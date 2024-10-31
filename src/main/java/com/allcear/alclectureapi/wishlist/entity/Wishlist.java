package com.allcear.alclectureapi.wishlist.entity;


import com.allcear.alclectureapi.common.BaseEntity;
import com.allcear.alclectureapi.lecture.entity.Lecture;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Wishlist extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long id;

    private Long studentId;  // 학생 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")  // FK 칼럼
    private Lecture lecture;  // 강의 엔티티

    private int priority;  // 우선순위

    public Wishlist(Long studentId, Lecture lecture, int priority) {
        this.studentId = studentId;
        this.lecture = lecture;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", lecture=" + lecture +
                ", priority=" + priority +
                '}';
    }
}