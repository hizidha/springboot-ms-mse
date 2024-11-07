package com.devland.assignment.msems.attendee;

import com.devland.assignment.msems.attendee.model.Attendee;
import com.devland.assignment.msems.attendee.model.dto.AttendeeRequestDTO;
import com.devland.assignment.msems.attendee.model.dto.AttendeeResponseDTO;
import com.devland.assignment.msems.attendee.model.dto.AttendeeSimplyResponseDTO;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events/{eventId}")
public class AttendeeController {
    private final AttendeeService attendeeService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/attendees")
    public ResponseEntity<Page<AttendeeSimplyResponseDTO>> getAll(
            @PathVariable Long eventId,
            @RequestParam(value = "name") Optional<String> optionalName,
            @RequestParam(value = "sort", defaultValue = "ASC") String sortString,
            @RequestParam(value = "order_by", defaultValue = "id") String orderBy,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "page", defaultValue = "1") int page
    ) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortString.toUpperCase()), orderBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Attendee> pageAttendees = this.attendeeService.findAll(optionalName, eventId, pageable);
        Page<AttendeeSimplyResponseDTO> attendeeResponseDTOs = pageAttendees.map(Attendee::convertToSimplyResponse);

        return ResponseEntity.ok(attendeeResponseDTOs);
    }

    @PostMapping("/attendees")
    public ResponseEntity<AttendeeResponseDTO> add(
            @PathVariable Long eventId,
            @RequestBody @Valid AttendeeRequestDTO attendeeRequestDTO
    ) {
        Attendee newAttendee = attendeeRequestDTO.convertToEntity();
        Attendee savedAttendee = this.attendeeService.add(eventId, newAttendee);
        AttendeeResponseDTO attendeeResponseDTO = savedAttendee.convertToResponse();

        return ResponseEntity.ok(attendeeResponseDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/attendees/{attendeeId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long eventId,
            @PathVariable Long attendeeId
    ) {
        attendeeService.delete(eventId, attendeeId);

        return ResponseEntity.ok().build();
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/upload-attendees")
    public ResponseEntity<String> fileUpload(
            @PathVariable Long eventId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            List<String> duplicateEmails = this.attendeeService.uploadDataExcel(eventId, file);

            if (!duplicateEmails.isEmpty()) {
                String message = String.join(", ", duplicateEmails);
                return ResponseEntity.ok("The following emails are already registered: " + message);
            } else {
                return ResponseEntity.ok("All attendees added successfully!");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process the file.");
        }
    }
}