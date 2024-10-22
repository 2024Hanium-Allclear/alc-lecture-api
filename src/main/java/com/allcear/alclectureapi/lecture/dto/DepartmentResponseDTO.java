package com.allcear.alclectureapi.lecture.dto;


import com.allcear.alclectureapi.lecture.entity.Department;
import lombok.*;

@Data
@Getter
@Builder
public class DepartmentResponseDTO {

    private Long id;
    private String name;

    public DepartmentResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DepartmentResponseDTO fromEntity(Department department) {
        if (department == null) {
            return null;
        }
        return DepartmentResponseDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
    }

}
