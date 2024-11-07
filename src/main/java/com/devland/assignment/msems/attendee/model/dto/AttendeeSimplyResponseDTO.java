package com.devland.assignment.msems.attendee.model.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendeeSimplyResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Timestamp registeredOn;
}