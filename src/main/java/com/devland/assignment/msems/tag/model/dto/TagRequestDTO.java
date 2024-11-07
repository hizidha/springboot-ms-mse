package com.devland.assignment.msems.tag.model.dto;

import com.devland.assignment.msems.tag.model.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDTO {
    private Long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @NotBlank(message = "Tag Name is required")
    private String name;

    public Tag convertToEntity() {
        return Tag.builder()
                .id(this.id)
                .name(this.name)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}