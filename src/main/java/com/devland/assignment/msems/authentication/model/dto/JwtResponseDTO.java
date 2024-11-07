package com.devland.assignment.msems.authentication.model.dto;

import lombok.Getter;

@Getter
public class JwtResponseDTO {
    private final String token;
    private final String type = "Bearer";

    public JwtResponseDTO(String jwtToken) {
        this.token = jwtToken;
    }
}