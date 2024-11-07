package com.devland.assignment.msems.tag.model;

import com.devland.assignment.msems.category.model.Category;
import com.devland.assignment.msems.event.model.Event;
import com.devland.assignment.msems.tag.model.dto.TagEventResponseDTO;
import com.devland.assignment.msems.tag.model.dto.TagResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToMany(mappedBy = "tags")
    private Set<Event> events;

    public TagResponseDTO convertToResponse() {
        return TagResponseDTO.builder()
                .id(this.id)
                .name(this.name)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public TagEventResponseDTO convertToSimplyResponse() {
        return TagEventResponseDTO.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}