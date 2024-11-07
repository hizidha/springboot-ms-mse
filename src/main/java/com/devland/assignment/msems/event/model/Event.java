package com.devland.assignment.msems.event.model;

import com.devland.assignment.msems.attendee.model.Attendee;
import com.devland.assignment.msems.attendee.model.dto.AttendeeSimplyResponseDTO;
import com.devland.assignment.msems.category.model.Category;
import com.devland.assignment.msems.category.model.dto.CategoryEventResponseDTO;
import com.devland.assignment.msems.event.model.dto.EventAttendeeResponseDTO;
import com.devland.assignment.msems.event.model.dto.EventResponseDTO;
import com.devland.assignment.msems.event.model.dto.EventResponseDetailDTO;
import com.devland.assignment.msems.tag.model.Tag;
import com.devland.assignment.msems.tag.model.dto.TagEventResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String interviewee;

    private String location;

    private LocalDateTime date;

    private int capacity;

    private int attendeeCount;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToMany
    @JoinTable(
            name = "event_categories",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "event_tags",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @OneToMany(
            mappedBy = "event",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Attendee> attendees;

    public EventResponseDTO convertToResponse() {
        Set<CategoryEventResponseDTO> categoryEventResponseDTOs = this.categories.stream()
                .map(Category::convertToSimplyResponse)
                .collect(Collectors.toSet());

        Set<TagEventResponseDTO> tagEventResponseDTOs = this.tags.stream()
                .map(Tag::convertToSimplyResponse)
                .collect(Collectors.toSet());

        return EventResponseDTO.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .interviewee(this.interviewee)
                .capacity(this.capacity)
                .sumOfRegisteredAttendee(this.attendeeCount)
                .location(this.location)
                .date(this.date)
                .categories(categoryEventResponseDTOs)
                .tags(tagEventResponseDTOs)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public EventResponseDetailDTO convertToDetailResponse() {
        Set<CategoryEventResponseDTO> categoryEventResponseDTOs = this.categories.stream()
                .map(Category::convertToSimplyResponse)
                .collect(Collectors.toSet());

        Set<TagEventResponseDTO> tagEventResponseDTOs = this.tags.stream()
                .map(Tag::convertToSimplyResponse)
                .collect(Collectors.toSet());

        List<AttendeeSimplyResponseDTO> attendeeEventResponseDTO = this.attendees.stream()
                .map(Attendee::convertToSimplyResponse)
                .toList();

        return EventResponseDetailDTO.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .interviewee(this.interviewee)
                .capacity(this.capacity)
                .sumOfRegisteredAttendee(this.attendeeCount)
                .location(this.location)
                .date(this.date)
                .categories(categoryEventResponseDTOs)
                .tags(tagEventResponseDTOs)
                .attendees(attendeeEventResponseDTO)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public EventAttendeeResponseDTO convertToAttendeeResponse() {
        return EventAttendeeResponseDTO.builder()
                .id(this.id)
                .title(this.title)
                .interviewee(this.interviewee)
                .capacity(this.capacity)
                .sumOfRegisteredAttendee(this.attendeeCount)
                .location(this.location)
                .date(this.date)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}