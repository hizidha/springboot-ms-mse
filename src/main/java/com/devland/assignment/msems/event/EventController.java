package com.devland.assignment.msems.event;

import com.devland.assignment.msems.category.CategoryService;
import com.devland.assignment.msems.category.model.Category;
import com.devland.assignment.msems.event.model.Event;
import com.devland.assignment.msems.event.model.dto.EventRequestDTO;
import com.devland.assignment.msems.event.model.dto.EventResponseDTO;
import com.devland.assignment.msems.event.model.dto.EventResponseDetailDTO;
import com.devland.assignment.msems.tag.TagService;
import com.devland.assignment.msems.tag.model.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final TagService tagService;
    private final CategoryService categoryService;
    private final EventService eventService;

    @GetMapping()
    public ResponseEntity<Page<EventResponseDTO>> getAll(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "categoriesName", required = false) List<String> categoryNames,
            @RequestParam(value = "tagsName", required = false) List<String> tagNames,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortString.toUpperCase()), orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Event> pageEvents = this.eventService.findAll(search, categoryNames, tagNames, pageable);
        Page<EventResponseDTO> eventResponseDTOs = pageEvents.map(Event::convertToResponse);

        return ResponseEntity.ok(eventResponseDTOs);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDetailDTO> getOne(
            @PathVariable("id") Long id
    ) {
        Event existingEvent = this.eventService.findBy(id);
        EventResponseDetailDTO eventResponseDTO = existingEvent.convertToDetailResponse();

        return ResponseEntity.ok(eventResponseDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping()
    public ResponseEntity<EventResponseDTO> create(
            @RequestBody @Valid EventRequestDTO eventRequestDTO
    ) {
        Set<Category> categories = this.categoryService.getCategories(eventRequestDTO);
        Set<Tag> tags = this.tagService.getTags(eventRequestDTO);
        Event newEvent = eventRequestDTO.convertToEntity(categories, tags);

        Event savedClassroom = this.eventService.create(newEvent);
        EventResponseDTO eventResponseDTO = savedClassroom.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(eventResponseDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid EventRequestDTO eventRequestDTO
    ) {
        Set<Category> categories = this.categoryService.getCategories(eventRequestDTO);
        Set<Tag> tags = this.tagService.getTags(eventRequestDTO);
        Event updatedEvent = eventRequestDTO.convertToEntity(categories, tags);

        updatedEvent.setId(id);
        Event savedTag = this.eventService.update(updatedEvent);
        EventResponseDTO tagResponseDTO = savedTag.convertToResponse();

        return ResponseEntity.ok().body(tagResponseDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id
    ) {
        this.eventService.delete(id);

        return ResponseEntity.ok().build();
    }
}