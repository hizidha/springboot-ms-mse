package com.devland.assignment.msems.tag.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagEventResponseDTO {
    private Long id;
    private String name;
}