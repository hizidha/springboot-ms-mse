package com.devland.assignment.msems.category.model;

import com.devland.assignment.msems.category.model.dto.CategoryEventResponseDTO;
import com.devland.assignment.msems.category.model.dto.CategoryResponseDTO;
import com.devland.assignment.msems.event.model.Event;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToMany(mappedBy = "categories")
    private Set<Event> events;

    public CategoryResponseDTO convertToResponse() {
        return CategoryResponseDTO.builder()
                .id(this.id)
                .name(this.name)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public CategoryEventResponseDTO convertToSimplyResponse() {
        return CategoryEventResponseDTO.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}