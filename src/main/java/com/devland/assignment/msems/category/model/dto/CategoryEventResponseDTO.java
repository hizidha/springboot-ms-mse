package com.devland.assignment.msems.category.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEventResponseDTO {
    private Long id;
    private String name;
}