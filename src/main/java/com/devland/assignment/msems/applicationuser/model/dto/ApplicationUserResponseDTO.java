package com.devland.assignment.msems.applicationuser.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserResponseDTO {
    private Long id;
    private String fullName;
    private String username;
    private String email;
}