package com.devland.assignment.msems.event;

import com.devland.assignment.msems.event.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByTitleAndIntervieweeAndDateAndLocation(String title,
                                                                String interviewee,
                                                                LocalDateTime dateTime,
                                                                String location);

    @Query("""
        SELECT DISTINCT e FROM Event e
        JOIN e.categories c
        JOIN e.tags t
        WHERE (LOWER(e.title) LIKE LOWER(CONCAT('%', ?1, '%'))
               OR LOWER(e.description) LIKE LOWER(CONCAT('%', ?1, '%')))
          AND (?2 IS NULL OR c.name IN ?2)
          AND (?3 IS NULL OR t.name IN ?3)
    """)
    Page<Event> findAll(String search, List<String> categoryNames, List<String> tagNames, Pageable pageable);
}