package com.devland.assignment.msems.category.model.dto;

import com.devland.assignment.msems.category.model.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {
    private Long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @NotBlank(message = "Category Name is required")
    private String name;

    public Category convertToEntity() {
        return Category.builder()
                .id(this.id)
                .name(this.name)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}