package com.devland.assignment.msems.attendee;

import com.devland.assignment.msems.attendee.exception.*;
import com.devland.assignment.msems.attendee.model.Attendee;
import com.devland.assignment.msems.event.EventService;
import com.devland.assignment.msems.event.model.Event;
import org.apache.poi.ss.usermodel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final EventService eventService;
    private final AttendeeRepository attendeeRepository;

    public Page<Attendee> findAll(Optional<String> optionalName, Long eventId, Pageable pageable) {
        if (optionalName.isPresent()) {
            return this.attendeeRepository
                    .findAllByEvent_IdAndNameContainsIgnoreCase(eventId, optionalName.get(), pageable);
        }

        return this.attendeeRepository.findAllByEvent_Id(eventId, pageable);
    }

    public Attendee add(Long eventId, Attendee attendee) {
        Event existingEvent = eventService.findBy(eventId);

        int eventCapacity = existingEvent.getCapacity();
        int eventAttendeeCount = existingEvent.getAttendeeCount();
        int attendeeCount = attendeeRepository.countByEvent_Id(eventId);

        if (attendeeCount >= eventCapacity && eventAttendeeCount >= attendeeCount) {
            throw new EventCapacityNotAvailableException("Event capacity not available");
        }

        Optional<Attendee> existingAttendee = attendeeRepository.findByEmailAndEvent_Id(attendee.getEmail(), eventId);
        if (existingAttendee.isPresent()) {
            throw new EmailAlreadyRegisteredException("Email already registered for this event");
        }

        existingEvent.setCapacity(existingEvent.getCapacity());
        existingEvent.setAttendeeCount(existingEvent.getAttendeeCount() + 1);
        this.eventService.update(existingEvent);

        attendee.setEvent(existingEvent);

        return attendeeRepository.save(attendee);
    }

    public void delete(Long eventId, Long attendeeId) {
        Attendee attendee = attendeeRepository.findByIdAndEvent_Id(attendeeId, eventId)
                .orElseThrow(() -> new AttendeeNotRegisteredException("Attendee not registered for this event"));

        Event existingEvent = this.eventService.findBy(eventId);
        existingEvent.setAttendeeCount(existingEvent.getAttendeeCount() - 1);
        this.eventService.update(existingEvent);

        attendeeRepository.delete(attendee);
    }

    public List<String> uploadDataExcel(Long eventId, MultipartFile fileExcel) throws IOException {
        List<String> duplicateEmails = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(fileExcel.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Attendee attendee = new Attendee();
                attendee.setName(getStringCellValue(row.getCell(0)));
                attendee.setEmail(getStringCellValue(row.getCell(1)));
                attendee.setPhone(getStringCellValue(row.getCell(2)));

                try {
                    this.add(eventId, attendee);
                } catch (EmailAlreadyRegisteredException e) {
                    duplicateEmails.add(attendee.getEmail());
                }
            }
        }

        return duplicateEmails;
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) return "";

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}