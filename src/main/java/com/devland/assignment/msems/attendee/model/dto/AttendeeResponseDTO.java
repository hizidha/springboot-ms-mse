package com.devland.assignment.msems.attendee.model.dto;

import com.devland.assignment.msems.event.model.dto.EventAttendeeResponseDTO;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendeeResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private EventAttendeeResponseDTO event;
    private Timestamp registeredOn;
}