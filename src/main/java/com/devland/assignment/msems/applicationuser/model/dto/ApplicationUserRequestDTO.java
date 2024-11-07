package com.devland.assignment.msems.applicationuser.model.dto;

import com.devland.assignment.msems.applicationuser.model.ApplicationUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserRequestDTO {
    private Long id;
    private String profilePicturePath;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[\\w-.]+@[\\w-]+\\.[\\w-]{2,}$", message = "Email should be valid")
    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Full Name is required")
    private String fullName;

    public ApplicationUser convertToEntity() {
        return ApplicationUser.builder()
                .id(this.id)
                .email(this.email)
                .username(this.username)
                .password(this.password)
                .fullName(this.fullName)
                .profilePicturePath(this.profilePicturePath)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}