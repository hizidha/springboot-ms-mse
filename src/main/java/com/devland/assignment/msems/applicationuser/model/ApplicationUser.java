package com.devland.assignment.msems.applicationuser.model;

import com.devland.assignment.msems.applicationuser.model.dto.ApplicationUserResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String password;

    private String fullName;

    private String profilePicturePath;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public ApplicationUserResponseDTO convertToResponse() {
        return ApplicationUserResponseDTO.builder()
                .id(this.id)
                .email(this.email)
                .username(this.username)
                .fullName(this.fullName)
                .build();
    }
}