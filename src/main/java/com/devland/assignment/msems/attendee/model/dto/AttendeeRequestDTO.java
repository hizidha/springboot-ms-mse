package com.devland.assignment.msems.attendee.model.dto;

import com.devland.assignment.msems.attendee.model.Attendee;
import com.devland.assignment.msems.event.model.Event;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendeeRequestDTO {
    private Long id;
    private Timestamp registeredOn;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email should be valid")
    @Size(max = 50, message = "Email should not exceed 50 characters")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+?[0-9]{9,14}$", message = "Phone number must be between 9-14 digits")
    private String phone;

    public Attendee convertToEntity() {
        return Attendee.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .event(new Event())
                .registeredOn(this.registeredOn)
                .build();
    }
}