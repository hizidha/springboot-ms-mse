package com.devland.assignment.msems.attendee;

import com.devland.assignment.msems.attendee.model.Attendee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    int countByEvent_Id(Long eventId);

    Optional<Attendee> findByIdAndEvent_Id(Long attendeeId, Long eventId);

    Optional<Attendee> findByEmailAndEvent_Id(String email, Long eventId);

    Page<Attendee> findAllByEvent_Id(Long eventId, Pageable pageable);

    Page<Attendee> findAllByEvent_IdAndNameContainsIgnoreCase(Long eventId, String name, Pageable pageable);
}