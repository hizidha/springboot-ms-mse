package com.devland.assignment.msems.event.model.dto;

import com.devland.assignment.msems.attendee.model.Attendee;
import com.devland.assignment.msems.category.model.Category;
import com.devland.assignment.msems.event.model.Event;
import com.devland.assignment.msems.tag.model.Tag;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class EventRequestDTO {
    private Long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @NotBlank(message = "Event Title is required")
    private String title;

    @NotBlank(message = "Event Description is required")
    private String description;

    @NotBlank(message = "Event Interviewee is required")
    private String interviewee;

    @NotBlank(message = "Event Location is required")
    private String location;

    @NotNull(message = "Event Date is required")
    @Future(message = "Event Date must be in the future")
    private LocalDateTime date;

    @NotNull(message = "Event Capacity is required")
    @Positive(message = "Event Capacity must be positive number or not zero")
    private int capacity;

    private Set<Long> categoriesIds;
    private Set<Long> tagsIds;

    public Event convertToEntity(Set<Category> categories, Set<Tag> tags) {
        List<Attendee> attendees = List.of();

        return Event.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .interviewee(this.interviewee)
                .location(this.location)
                .date(this.date)
                .capacity(this.capacity)
                .categories(categories)
                .tags(tags)
                .attendees(attendees)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}