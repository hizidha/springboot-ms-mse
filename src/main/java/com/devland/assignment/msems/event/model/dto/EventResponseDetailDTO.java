package com.devland.assignment.msems.event.model.dto;

import com.devland.assignment.msems.attendee.model.dto.AttendeeSimplyResponseDTO;
import com.devland.assignment.msems.category.model.dto.CategoryEventResponseDTO;
import com.devland.assignment.msems.tag.model.dto.TagEventResponseDTO;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDetailDTO {
    private Long id;
    private String title;
    private String description;
    private String interviewee;
    private String location;
    private LocalDateTime date;
    private int capacity;
    private int sumOfRegisteredAttendee;
    private Set<TagEventResponseDTO> tags;
    private Set<CategoryEventResponseDTO> categories;
    private List<AttendeeSimplyResponseDTO> attendees;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}