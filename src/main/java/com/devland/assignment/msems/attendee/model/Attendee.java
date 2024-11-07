package com.devland.assignment.msems.attendee.model;

import com.devland.assignment.msems.attendee.model.dto.AttendeeResponseDTO;
import com.devland.assignment.msems.attendee.model.dto.AttendeeSimplyResponseDTO;
import com.devland.assignment.msems.event.model.Event;
import com.devland.assignment.msems.event.model.dto.EventAttendeeResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "attendees")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    @CreationTimestamp
    private Timestamp registeredOn;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    public AttendeeResponseDTO convertToResponse() {
        EventAttendeeResponseDTO eventAttendeeResponseDTO = this.event.convertToAttendeeResponse();

        return AttendeeResponseDTO.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .event(eventAttendeeResponseDTO)
                .registeredOn(this.registeredOn)
                .build();
    }

    public AttendeeSimplyResponseDTO convertToSimplyResponse() {
        return AttendeeSimplyResponseDTO.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .registeredOn(this.registeredOn)
                .build();
    }
}