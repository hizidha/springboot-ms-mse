package com.devland.assignment.msems.event;

import com.devland.assignment.msems.event.exception.EventAlreadyExistException;
import com.devland.assignment.msems.event.exception.EventNotFoundException;
import com.devland.assignment.msems.event.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Page<Event> findAll(String search, List<String> categoryNames, List<String> tagNames, Pageable pageable) {
        return this.eventRepository.findAll(search, categoryNames, tagNames, pageable);
    }

    public Event findBy(Long id) {
        return this.eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event with ID " + id + " not found"));
    }

    public Event create(Event newEvent) {
        Optional<Event> existingEvent = this.eventRepository
                .findByTitleAndIntervieweeAndDateAndLocation(
                        newEvent.getTitle(),
                        newEvent.getInterviewee(),
                        newEvent.getDate(),
                        newEvent.getLocation()
                );

        if (existingEvent.isPresent()) {
            throw new EventAlreadyExistException("Event with title " + newEvent.getTitle() + " already exist");
        }

        return this.eventRepository.save(newEvent);
    }

    public Event update(Event updatedEvent) {
        Event existingEvent = this.findBy(updatedEvent.getId());
        updatedEvent.setId(existingEvent.getId());

        return this.eventRepository.save(updatedEvent);
    }

    public void delete(Long id) {
        this.eventRepository.deleteById(this.findBy(id).getId());
    }
}