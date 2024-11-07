package com.devland.assignment.msems.event.model.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventAttendeeResponseDTO {
    private Long id;
    private String title;
    private String interviewee;
    private int capacity;
    private int sumOfRegisteredAttendee;
    private String location;
    private LocalDateTime date;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}